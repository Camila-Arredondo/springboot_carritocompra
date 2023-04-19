package com.nttlab.carritodecompras.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttlab.carritodecompras.models.dao.iProductoDAO;
import com.nttlab.carritodecompras.models.dao.iUsuarioDAO;
import com.nttlab.carritodecompras.models.entity.Usuario;

@Service

public class UsuarioServiceImplement implements iUsuarioService {

	@Autowired
	public iUsuarioDAO usuarioDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>)usuarioDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		return usuarioDAO.findByUsername(username);
	}

	@Override
	@Transactional()
	public void delete(Long id) {
		usuarioDAO.deleteById(id);
		
	}

	@Override	 
	@Transactional()
	public Usuario crearUsuario(Usuario usuario) {
		return usuarioDAO.save(usuario);
	}

}
