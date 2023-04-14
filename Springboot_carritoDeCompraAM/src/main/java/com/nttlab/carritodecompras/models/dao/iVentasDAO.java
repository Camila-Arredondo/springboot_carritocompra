package com.nttlab.carritodecompras.models.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.TotalCompra;
import com.nttlab.carritodecompras.models.entity.Ventas;
public interface iVentasDAO extends CrudRepository<Ventas, Long> {

	
	
}
