package com.nttlab.carritodecompras.models.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.entity.Usuario;

@Repository
public interface iUsuarioService {

	public List<Usuario> findAll();


	public Usuario findByUsername(String username);

	public void delete(Long id);
	public Usuario crearUsuario(Usuario usuario);
	
}
