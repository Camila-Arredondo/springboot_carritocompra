package com.nttlab.carritodecompras.models.service;

import java.util.List;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.entity.Usuario;

public interface iCarritoService {
	public List<Carrito> findByUser(Usuario usuario);
	
	public void addProduct(Producto producto, Usuario usuario);
	public void quitarProducto(Producto producto, Usuario usuario);
	public void eliminarProducto(Producto producto, Usuario usuario);


}
