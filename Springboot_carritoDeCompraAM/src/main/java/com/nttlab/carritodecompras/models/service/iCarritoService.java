package com.nttlab.carritodecompras.models.service;

import java.util.List;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.entity.Usuario;

public interface iCarritoService {
	public List<Carrito> findByUser(String usuario);
	
	public void addProduct(long idProducto, String username);
	public void quitarProducto(long idProducto, String username);
	public void eliminarProducto(long idProducto, String username);

	public void deleteAllByUsuario(String usuario);
	public void deleteAllByProducto(Producto producto);

}
