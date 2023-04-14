package com.nttlab.carritodecompras.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nttlab.carritodecompras.models.entity.Producto;

public interface iProductoDAO extends CrudRepository<Producto, Long> {
	@Query("select a from Producto a where a.nombre like %?1%")

	public Producto findOne(Long id);

	public Producto findByNombre(String nombre);

	public Producto findByCategoria(String categoria);

	public Producto findByDescripcion(String descripcion);
}