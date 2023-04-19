package com.nttlab.carritodecompras.models.service;

import java.util.List;

import com.nttlab.carritodecompras.models.entity.Categoria;

public interface iCategoriaService {
	
	public Categoria findById(Long id);
	
	public Categoria findByNombre(String nombre);
	
	public List<Categoria> findAll();
	
	public Categoria crearCategoria(Categoria categoria);
	
	public void delete(Long id);
	
	public Categoria update(Categoria categoria);

}
