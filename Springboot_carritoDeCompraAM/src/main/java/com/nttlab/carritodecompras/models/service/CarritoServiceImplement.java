package com.nttlab.carritodecompras.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttlab.carritodecompras.models.dao.iCarritoDAO;
import com.nttlab.carritodecompras.models.dao.iProductoDAO;
import com.nttlab.carritodecompras.models.dao.iUsuarioDAO;
import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.entity.Usuario;

@Service
public class CarritoServiceImplement implements iCarritoService{
	
	@Autowired
    public iCarritoDAO carritoDao;
	
	@Autowired
    public iProductoDAO productodao;

	
	@Autowired
    public iUsuarioDAO usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Carrito> findByUser(String usuario) {
		return (List<Carrito>) carritoDao.findByUsuario(usuario);
	}
	@Override
	@Transactional
	public void addProduct(long idProducto, String usuario) {

		var productofind = productodao.findById(idProducto);
		var producto = productofind.get();

		var carritoActual = carritoDao.findByUsuario(usuario);
		
		boolean crear = true;
		Carrito carritonuevo = null;
		for(var carrito : carritoActual) {
			if(carrito.getProducto().equals(producto)) {
				crear = false;
				carritonuevo = carrito;
				break;
			}
		}
		if(crear) {
			carritonuevo = new Carrito(1, producto, usuario);
		}else {
			carritonuevo.setCantidad(carritonuevo.getCantidad()+1);
		}
		
		carritoDao.save(carritonuevo);
		
	}

	
	
	@Override
	@Transactional
	public void quitarProducto(long idProducto, String usuario) {

		var productofind = productodao.findById(idProducto);
		var producto = productofind.get();
		var carritoActual = carritoDao.findByUsuario(usuario);
		Carrito carritonuevo = null;
		boolean existe = false;
		for(var carrito : carritoActual) {
			if(carrito.getProducto().getId().equals(producto.getId())) {
				existe = true;
				carritonuevo = carrito;
				break;
			}
		}
		
		if(existe) {
			carritonuevo.setCantidad(carritonuevo.getCantidad()-1);
			if(carritonuevo.getCantidad() == 0) {
				carritoDao.delete(carritonuevo);	
			}else {
				carritoDao.save(carritonuevo);
			}

		}
	}
	
	@Override
	@Transactional
	public void eliminarProducto(long idProducto, String usuario) {

		var productofind = productodao.findById(idProducto);
		var producto = productofind.get();

		var carritoActual = carritoDao.findByUsuario(usuario);
		for(var carrito : carritoActual) {
			if(carrito.getProducto().equals(producto)) {
				carritoDao.delete(carrito);	
				break;
			}
		}
	}
	@Override
	@Transactional
	public void deleteAllByUsuario(String usuario) {
		
		carritoDao.deleteAllByUsuario(usuario);
		
	}

	@Override
	@Transactional
	public void deleteAllByProducto(Producto producto) {
		carritoDao.deleteAllByProducto(producto);
	}
	
	
	
}
