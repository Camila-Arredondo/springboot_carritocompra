package com.nttlab.carritodecompras.models.service;

import java.util.*;

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
	public int addTotales(List<Carrito> carrito) {
		if(carrito.size() != (0)) {
			Ventas ventaNew = new Ventas(new Date(), carrito.get(0).getUsuario());
			var venta = ventasDao.save(ventaNew);
			List<TotalCompra> compras = new ArrayList<>();
			for(var car : carrito) {
				var compra = car.toTotalCompra();
				compra.setNumeroOrden(venta.getId());
				compras.add(compra);
			}
			
			totalCarritoDao.saveAll(compras);
			return ventaNew.getId();
		}
		
		return -1;
		 
	}

	@Override
	public List<TotalCompra> findByNumeroOrden(int numero_orden) {
		
		return totalCarritoDao.findByNumeroOrden(numero_orden);
	}

}
