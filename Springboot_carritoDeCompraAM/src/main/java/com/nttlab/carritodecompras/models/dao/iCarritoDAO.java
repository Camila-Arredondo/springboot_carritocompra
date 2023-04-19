package com.nttlab.carritodecompras.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.Usuario;

public interface iCarritoDAO extends CrudRepository<Carrito, Integer> {
	
	public List <Carrito> findByUsuario(String usuario);
	public void deleteAllByUsuario(String usuario);

}
