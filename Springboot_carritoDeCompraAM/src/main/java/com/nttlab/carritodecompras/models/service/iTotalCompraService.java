package com.nttlab.carritodecompras.models.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.Ventas;
@Service
public interface iTotalCompraService {
	public void addTotales(List<Carrito> carrito);

}
