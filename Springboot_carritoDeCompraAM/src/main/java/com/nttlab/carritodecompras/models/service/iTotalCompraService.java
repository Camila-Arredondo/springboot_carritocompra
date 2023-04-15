package com.nttlab.carritodecompras.models.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.TotalCompra;

@Service
public interface iTotalCompraService {
	int addTotales(List<Carrito> carrito);
	List<TotalCompra> findByNumeroOrden(int numero_orden);
}
