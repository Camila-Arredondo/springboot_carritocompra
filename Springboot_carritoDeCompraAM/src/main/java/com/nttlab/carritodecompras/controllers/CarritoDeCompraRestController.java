package com.nttlab.carritodecompras.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.service.iCarritoService;
import com.nttlab.carritodecompras.models.service.iProductoService;
import com.nttlab.carritodecompras.models.service.iTotalCompraService;

@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
public class CarritoDeCompraRestController {
	
	@Autowired
	private iProductoService productoService;
	@Autowired
	private iCarritoService carritoService;
	@Autowired
	private iTotalCompraService totalcompraService;

	
	@PostMapping(value = {"/carrito"}, produces = "application/json")
	public ResponseEntity<?> ObtenerCarrito(@RequestBody Carrito carrito){
		
		Map<String, Object> response = new HashMap<>();
		response.put("carrito", carritoService.findByUser(carrito.getUsuario()));
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
	
	@PostMapping(value = {"/carrito/agregar"}, produces = "application/json")
	public ResponseEntity<?> agregarProducto(@RequestBody Carrito carrito){
		carritoService.addProduct(carrito.getProductoid(), carrito.getUsuario());
		Map<String, Object> response = new HashMap<>();
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
	@PostMapping(value = {"/carrito/quitar"}, produces = "application/json")
	public ResponseEntity<?> quitarProducto(@RequestBody Carrito carrito){
		carritoService.quitarProducto(carrito.getProductoid(), carrito.getUsuario());
		Map<String, Object> response = new HashMap<>();
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
	
	@PostMapping(value = {"/carrito/eliminar"}, produces = "application/json")
	public ResponseEntity<?> eliminarProducto(@RequestBody Carrito carrito){
		carritoService.eliminarProducto(carrito.getProductoid(), carrito.getUsuario());
		Map<String, Object> response = new HashMap<>();
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
	@PostMapping(value = {"/carrito/limpiarcarrito"}, produces = "application/json")
	public ResponseEntity<?> limpiarCarrito(@RequestBody Carrito carrito){
		carritoService.deleteAllByUsuario(carrito.getUsuario());
		Map<String, Object> response = new HashMap<>();
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
}
