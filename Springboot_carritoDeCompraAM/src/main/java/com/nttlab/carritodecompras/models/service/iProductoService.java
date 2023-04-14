package com.nttlab.carritodecompras.models.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nttlab.carritodecompras.models.entity.Producto;

@Repository
public interface iProductoService {

	public List<Producto> findAll();

	public Producto findByNombre(String nombre);

	public Producto findByCategoria(String categoria);

	public Producto findByDescripcion(String descripcion);

	public Producto findOne(Long id);

	public void delete(Long id);
	
	
}
