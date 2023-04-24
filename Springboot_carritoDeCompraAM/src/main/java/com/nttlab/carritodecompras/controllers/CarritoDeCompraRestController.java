package com.nttlab.carritodecompras.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

	
	@GetMapping(value = {"/carrito"}, produces = "application/json")
	public ResponseEntity<?> ObtenerCarrito(@RequestHeader("username") String username){
		
		Map<String, Object> response = new HashMap<>();
		response.put("carrito", carritoService.findByUser(username));
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
	
	@GetMapping(value = {"/carrito/{accion}/{id}"}, produces = "application/json")
	public ResponseEntity<?> accionCarrito(
			@PathVariable(value = "id", required = true) Long id,
			@PathVariable(value = "accion", required = true) String accion,
			@RequestHeader("username") String username
			){
		
		
		if(accion.equals("agregar")) {
			carritoService.addProduct(id, username);
		}else
			if(accion.equals("quitar")) {
			carritoService.quitarProducto(id, username);
		}else
			if(accion.equals("eliminar")) {
			carritoService.eliminarProducto(id, username);
		}else {
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", "Accion " + accion + " no soportada");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		Map<String, Object> response = new HashMap<>();
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
	
	@DeleteMapping(value = {"/carrito"}, produces = "application/json")
	public ResponseEntity<?> limpiarCarrito(@RequestHeader("username") String username){
		carritoService.deleteAllByUsuario(username);
		Map<String, Object> response = new HashMap<>();
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
}
