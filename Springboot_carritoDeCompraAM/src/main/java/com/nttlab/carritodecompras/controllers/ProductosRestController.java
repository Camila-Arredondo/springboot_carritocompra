package com.nttlab.carritodecompras.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.service.iCarritoService;
import com.nttlab.carritodecompras.models.service.iProductoService;



@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
public class ProductosRestController {
	@Autowired
	private iCarritoService carritoSerice;

	@Autowired
	private iProductoService productoService;
	
	@GetMapping(value = {"/productos"}, produces = "application/json")
	public ResponseEntity<?> getAllProducto(
			@RequestHeader("username") String username
			){
		List<Producto> productos = null;
		Map<String, Object> response = new HashMap<>();
		try {
			
			
			var productosCarrito = carritoSerice.findByUser(username);
			productos = productoService.findAll();

			for(var producto: productos) {
				for(var carrito: productosCarrito) {
					if(producto.equals(carrito.getProducto())) {
						 producto.setCantidad(carrito.getCantidad());
					}
				}
			}
			
			if(productos.isEmpty()) {
				response.put("mensaje", "No hay productos registrados en la base de datos");
				response.put("producto", productos);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			}
			response.put("mensaje", "Actualmente la base de datos cuenta con " + productos.size() + " registros");
			response.put("producto", productos);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping(value = "/productos/{id}", produces = "application/json")
	public ResponseEntity<?> getAlumnoById(@PathVariable(value = "id", required = false) Long id, @RequestHeader("username") String username){
		Producto productos = null;
		Map<String, Object> response = new HashMap<>();
		try {
			productos = productoService.findById(id);
			if(productos == null) {
				response.put("mensaje","El producto ID: " + id + " no existen en la base de datos.");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Producto>(productos, HttpStatus.OK);
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@DeleteMapping(value = "/productos/{id}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable(value = "id") Long id) {
		Producto producto = productoService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (producto == null) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			carritoSerice.deleteAllByProducto(producto);
			productoService.deleteById(id);
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/productos", produces = "application/json")
	public ResponseEntity<Map<String, Object>> crearProducto(@RequestBody Producto producto) {
		Map<String, Object> response = new HashMap<>();
		try {
			for (Producto a : productoService.findAll()) {
				if (a.getNombre().equalsIgnoreCase(producto.getNombre())) {
					response.put("mensaje", "Error al realizar el registro del producto. El nombre indicado ya existe en nuestros registros.");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
			Producto productoNuevo = productoService.crearProducto(producto);
			response.put("mensaje", "Producto registrado satisfactoriamente.");
			response.put("producto", productoNuevo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping(value = "/productos/{id}", produces = "application/json")
	public ResponseEntity<?> updateAlumno(@RequestBody Producto producto, @PathVariable Long id){
		Producto productoActualizado = null;
		Producto productoActual = null;
		Map<String, Object> response = new HashMap<>();
		try {
			productoActual = productoService.findById(id);
			if(productoActual == null) {
				response.put("mensaje", "No se pudo completar el proceso de actualización ya que el producto (ID " + id + ") no existe en nuestros registros");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			
			List<Producto> productos =  productoService.findAll();
			for(Producto a: productos) {
				if(productoActual.getId() != a.getId()) {
					if(a.getNombre().equalsIgnoreCase(producto.getNombre())) {
						response.put("mensaje", "Error al realizar el registro del producto. El producto indicado ya existe en nuestros registros.");
						return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);	
					}
				}
			}
			
			
			
			if(!(producto.getNombre() == (null))) {
				productoActual.setNombre(producto.getNombre());
			}
			if(!(producto.getDescripcion() == (null))) {
				productoActual.setDescripcion(producto.getDescripcion());
			}
			if(!(producto.getCategoria() == (null))) {
				productoActual.setCategoria(producto.getCategoria());
			}
			if(!(producto.getPrecio() == (null))) {
				productoActual.setPrecio(producto.getPrecio() );
			}
			
			productoActualizado = productoService.crearProducto(productoActual);
			response.put("mensaje", "El producto modificado satisfactoriamente.");
			response.put("producto", productoActualizado);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
			
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar el proceso de edición del producto.");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
}
	