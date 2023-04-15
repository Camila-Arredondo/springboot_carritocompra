package com.nttlab.carritodecompras.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nttlab.carritodecompras.models.entity.TotalCompra;
public interface iTotalCompraDAO extends CrudRepository<TotalCompra, Long> {


	List<TotalCompra> findByNumeroOrden(int numero_orden);
}
