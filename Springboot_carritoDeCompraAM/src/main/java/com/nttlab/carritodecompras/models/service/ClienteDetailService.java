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

import com.nttlab.carritodecompras.models.dao.iClienteDAO;

import com.nttlab.carritodecompras.models.entity.Cliente;
import com.nttlab.carritodecompras.models.entity.Role;

import jakarta.transaction.Transactional;

@Service("ClienteDetailService")
public class ClienteDetailService implements UserDetailsService {

	@Autowired
	private iClienteDAO clienteDao;

	private Logger logger = LoggerFactory.getLogger(ClienteDetailService.class);

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Cliente cliente = clienteDao.findByUsername(username);

		if (cliente == null) {
			logger.error("Error de acceso: no existe el email [" + username + "]");
			throw new UsernameNotFoundException("Username: [" + username + "] No existe en nuestros registros.");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		for (Role r : cliente.getRoles()) {
			logger.info("Rol: " + r.getAuthority());
			authorities.add(new SimpleGrantedAuthority(r.getAuthority()));
		}

		if (authorities.isEmpty()) {
			logger.error("Error de acceso: usuario [" + username + "] NO tiene roles asignados.");
			throw new UsernameNotFoundException("Error de acceso: usuario [" + username + "] NO tiene roles asignados.");
		}

		return new User(cliente.getUsername(), cliente.getPassword(), cliente.isActive(), true, true, true, authorities);
	}

	@Transactional
	public Cliente findByUsername(String username) {

		return clienteDao.findByUsername(username);

	}
}
