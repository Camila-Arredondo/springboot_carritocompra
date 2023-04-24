package com.nttlab.carritodecompras.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.CrearVenta;
import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.entity.Ventas;
import com.nttlab.carritodecompras.models.service.iCarritoService;
import com.nttlab.carritodecompras.models.service.iProductoService;
import com.nttlab.carritodecompras.models.service.iTotalCompraService;
import com.nttlab.carritodecompras.models.service.iVentasService;

@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
public class VentasRestController {

	@Autowired
	private iCarritoService carritoService;
	@Autowired
	private iVentasService ventasService;
	@Autowired
	private iTotalCompraService totalcompraService;


	@GetMapping(value = {"/ventas"}, produces = "application/json")
	public ResponseEntity<?> ObtenerVentas(@RequestHeader("username") String username){
		Map<String, Object> response = new HashMap<>();
		response.put("ventas", ventasService.findByUsuario(username));
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}

	
	@GetMapping(value = "/ventas/{id}", produces = "application/json")
	public ResponseEntity<?> getVentasById(
			@PathVariable(value = "id", required = true) Long id, 
			@RequestHeader("username") String username){
		Ventas ventas = null;
		Map<String, Object> response = new HashMap<>();
		try {
			ventas = ventasService.findByIdAndUsuario(id,username);
			var detalleVenta = totalcompraService.findByNumeroOrden(ventas.getId());
			if(ventas == null) {
				response.put("mensaje","La venta  ID: " + id + " no existen en la base de datos.");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			response.put("venta", ventas);
			response.put("detalleventa", detalleVenta);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/ventas", produces = "application/json")
	public ResponseEntity<Map<String, Object>> crearVenta (@RequestHeader("username") String username) {
		Map<String, Object> response = new HashMap<>();
		try {
			
			if (username == null) {
				var productosCarrito = carritoService.findByUser(username);
				response.put("mensaje", "No se puede generar una venta sin envio de forma correcta el usuario");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
			  } 
			
			var carrito = carritoService.findByUser(username);
			
			if(carrito.size()== 0) {
				response.put("mensaje", "No se puede generar una venta sin elementos en el carrito");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
			}
		
			int idVenta = totalcompraService.addTotales(carrito);
			carritoService.deleteAllByUsuario(username);
			response.put("nr_venta", idVenta);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

		}	catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	
	}
	
}
