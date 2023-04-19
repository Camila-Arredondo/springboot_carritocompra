package com.nttlab.carritodecompras.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttlab.carritodecompras.models.dao.iCategoriaDAO;
import com.nttlab.carritodecompras.models.entity.Categoria;

@Service
public class CategoriaServiceImplement implements iCategoriaService {

	@Autowired
	public iCategoriaDAO categoriaDao;
	
	@Override
	@Transactional(readOnly = true)
	public Categoria findById(Long id) {
		
		return categoriaDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria findByNombre(String nombre) {
		return categoriaDao.findByNombre(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> findAll() {
		return (List<Categoria>)categoriaDao.findAll();
	}

	@Override
	@Transactional()
	public Categoria crearCategoria(Categoria categoria) {
		return categoriaDao.save(categoria);
	}

	@Override
	@Transactional()
	public void delete(Long id) {
		categoriaDao.deleteById(id);
		
	}

	@Override
	@Transactional()
	public Categoria update(Categoria categoria) {
		return categoriaDao.save(categoria);
	}

}
