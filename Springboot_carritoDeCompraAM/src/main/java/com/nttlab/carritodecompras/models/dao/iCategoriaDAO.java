package com.nttlab.carritodecompras.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.nttlab.carritodecompras.models.entity.Categoria;
import com.nttlab.carritodecompras.models.entity.Producto;

public interface iCategoriaDAO extends CrudRepository <Categoria , Long > {
	

	public Categoria findByNombre(String nombre);

}
