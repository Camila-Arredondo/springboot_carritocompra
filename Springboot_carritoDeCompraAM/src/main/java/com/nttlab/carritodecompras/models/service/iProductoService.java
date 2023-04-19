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

	public Producto crearProducto(Producto producto);

	public void deleteById(Long id);
	public Producto findById(Long id);


	
	
}
