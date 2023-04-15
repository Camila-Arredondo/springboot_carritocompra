package com.nttlab.carritodecompras.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nttlab.carritodecompras.models.dao.iRoleDAO;
import com.nttlab.carritodecompras.models.dao.iUsuarioDAO;
import com.nttlab.carritodecompras.models.entity.Role;
import com.nttlab.carritodecompras.models.entity.Usuario;

import jakarta.transaction.Transactional;



@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService{

	@Autowired
	private iUsuarioDAO usuarioDao;
	
	
	@Autowired
	private iRoleDAO roleDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailService.class);
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error de acceso: no existe el usuario [" + username + "]");
			throw new UsernameNotFoundException("Username: [" + username + "] No existe en nuestros registros.");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for(Role r: usuario.getRoles()) {
			logger.info("Rol: " + r.getAuthority());
			authorities.add(new SimpleGrantedAuthority(r.getAuthority()));
		}
		
		if(authorities.isEmpty()) {
			logger.error("Error de acceso: usuario [" + username + "] NO tiene roles asignados.");
			throw new UsernameNotFoundException("Error de acceso: usuario [" + username + "] NO tiene roles asignados.");
		}
		

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isActive(), true, true, true, authorities);
	}
	
	@Transactional
	public Usuario findByUsername(String username) {
		
		return  usuarioDao.findByUsername(username);
		
	}

	@Transactional
	public String createUser(Usuario usuario) {
		
		var existe = usuarioDao.findByUsername(usuario.getUsername());
		
		if(existe == null) {
			usuario.setActive(true);
			var usuariocreado = usuarioDao.save(usuario);	
			Role role = new Role("ROLE_USER", usuariocreado);
			var newrole = roleDao.save(role);
			return "OK";
		}
		
		return "Ya existe un usario registrado con el nombre " + usuario.getUsername();
	
	}


}
