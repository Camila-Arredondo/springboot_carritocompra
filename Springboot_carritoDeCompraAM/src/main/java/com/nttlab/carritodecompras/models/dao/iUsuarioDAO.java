package com.nttlab.carritodecompras.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.nttlab.carritodecompras.models.entity.Usuario;


public interface iUsuarioDAO extends CrudRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);

}