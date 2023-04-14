package com.nttlab.carritodecompras.models.service;

import java.util.*;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttlab.carritodecompras.models.dao.iTotalCompraDAO;
import com.nttlab.carritodecompras.models.dao.iVentasDAO;
import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.TotalCompra;
import com.nttlab.carritodecompras.models.entity.Ventas;
@Service
public class TotalCompraServiceImplement implements iTotalCompraService {
	@Autowired
	public iTotalCompraDAO totalCarritoDao;

	@Autowired
	public iVentasDAO ventasDao;

	@Override
	@Transactional()
	public void addTotales(List<Carrito> carrito) {
		Ventas ventaNew = new Ventas(new Date());
		var venta = ventasDao.save(ventaNew);
		List<TotalCompra> compras = new ArrayList<>();
		for(var car : carrito) {
			var compra = car.toTotalCompra();
			compra.setNumeroOrden(venta.getId());
			compras.add(compra);
		}
		
		totalCarritoDao.saveAll(compras);
	}

}
