package com.nttlab.carritodecompras.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.service.JpaUserDetailService;
import com.nttlab.carritodecompras.models.service.iCarritoService;
import com.nttlab.carritodecompras.models.service.iProductoService;


@CrossOrigin(origins = {"http://localhost:8088"})
@RestController
@RequestMapping(value = "/api")
public class ProductosRestController {

	@Autowired
	private iProductoService productoService;
	@Autowired
	private JpaUserDetailService userService;
	@Autowired
	private iCarritoService carritoSerice;
	
	
	@GetMapping(value = "/productos")
	public ResponseEntity<?> productoCatálogo() {
		var productos = productoService.findAll();

		Map<String, Object> response = new HashMap<>();
		response.put("cantidad", productos.size());
		response.put("data", productos);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}

	@GetMapping(value = "productos/eliminar/{id}")
	public ResponseEntity<?>  eliminarAlumno(@PathVariable(value = "id") Long id) {
		Producto producto = productoService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (producto == null) {
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

		} else {
			productoService.deleteById(id);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}
	}

	
	@PostMapping(value= "/alumnos", produces = "application/json")
	public ResponseEntity<?> createAlumno(@RequestBody Producto producto){
		Map<String, Object> response = new HashMap<>();
		response.put("newproduct", productoService.crearProducto(producto));
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	}
	@RequestMapping(value = "/productos", 
			  produces = "application/json", 
			  method=RequestMethod.POST)	
	public ResponseEntity<?> CrearProducto(@RequestBody Producto producto) {
		Map<String, Object> response = new HashMap<>();

		response.put("newproduct", productoService.crearProducto(producto));
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}