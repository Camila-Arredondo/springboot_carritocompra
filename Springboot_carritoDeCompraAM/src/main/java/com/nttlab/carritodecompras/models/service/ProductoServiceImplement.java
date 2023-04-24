package com.nttlab.carritodecompras.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttlab.carritodecompras.models.dao.iProductoDAO;
import com.nttlab.carritodecompras.models.entity.Producto;

@Service
public class ProductoServiceImplement implements iProductoService {

	@Autowired
	public iProductoDAO productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional()
	public Producto findByNombre(String nombre) {
		Producto a = productoDao.findByNombre(nombre);
		if (a != null) {
			return a;
		}
		return null;
	}

	@Override
	@Transactional()
	public Producto findByCategoria(String categoria) {
		return productoDao.findByCategoria(categoria);
	}

	@Override
	@Transactional()
	public Producto findByDescripcion(String descripcion) {
		return productoDao.findByDescripcion(descripcion);
	}

	@Override
	public void deleteById(Long id) {
		productoDao.deleteById(id);

	}

	@Override 
	public Producto crearProducto(Producto producto) {
		
		return productoDao.save(producto);
		
	}

}
