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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nttlab.carritodecompras.models.entity.Categoria;
import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.service.iCarritoService;
import com.nttlab.carritodecompras.models.service.iCategoriaService;
import com.nttlab.carritodecompras.models.service.iProductoService;



@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
public class CategoriaRestController {

	@Autowired
	private iCategoriaService categoriaService;

	
	@GetMapping(value = {"/categoria"}, produces = "application/json")
	public ResponseEntity<?> getAllCategoria(){
		List<Categoria> categoria = null;
		Map<String, Object> response = new HashMap<>();
		try {
			categoria = categoriaService.findAll();
			if(categoria.isEmpty()) {
				response.put("mensaje", "No hay categorias registradas en la base de datos");
				response.put("categoria", categoria);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			}
			response.put("mensaje", "Actualmente la base de datos cuenta con " + categoria.size() + " registros");
			response.put("categoria", categoria);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping(value = "/categoria/{id}", produces = "application/json")
	public ResponseEntity<?> getCategoriaById(@PathVariable(value = "id", required = false) Long id){
		Categoria categoria = null;
		Map<String, Object> response = new HashMap<>();
		try {
			categoria = categoriaService.findById(id);
			if(categoria == null) {
				response.put("mensaje","La categoria ID: " + id + " no existe en la base de datos.");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@DeleteMapping(value = "/categoria/{id}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> eliminarCategoria(@PathVariable(value = "id") Long id) {
		Categoria categoria = categoriaService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (categoria == null) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			categoriaService.delete(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/categoria", produces = "application/json")
	public ResponseEntity<Map<String, Object>> crearProducto(@RequestBody Categoria categoria) {
		Map<String, Object> response = new HashMap<>();
		try {
			for (Categoria a : categoriaService.findAll()) {
				if (a.getNombre().equalsIgnoreCase(categoria.getNombre())) {
					response.put("mensaje", "Error al realizar el registro de la categoría. La categoría indicada ya existe en nuestros registros.");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
			Categoria categoriaNuevo = categoriaService.crearCategoria(categoria);
			response.put("mensaje", "Categoría registrada satisfactoriamente.");
			response.put("categoria", categoriaNuevo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping(value = "/categoria/{id}", produces = "application/json")
	public ResponseEntity<?> updateCategoria(@RequestBody Categoria categoria, @PathVariable Long id){
		Categoria categoriaActualizado = null;
		Categoria categoriaActual = null;
		Map<String, Object> response = new HashMap<>();
		try {
			categoriaActual = categoriaService.findById(id);
			if(categoriaActual == null) {
				response.put("mensaje", "No se pudo completar el proceso de actualización ya que la categoría (ID " + id + ") no existe en nuestros registros");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			
			List<Categoria> categorias =  categoriaService.findAll();
			for(Categoria a: categorias) {
				if(categoriaActual.getId() != a.getId()) {
					if(a.getNombre().equalsIgnoreCase(categoria.getNombre())) {
						response.put("mensaje", "Error al realizar el registro del producto. El producto indicado ya existe en nuestros registros.");
						return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);	
					}
				}
			}
			
			
			
			if(!(categoria.getNombre() == (null))) {
				categoriaActual.setNombre(categoria.getNombre());
			}
			
			categoriaActualizado = categoriaService.crearCategoria(categoriaActual);
			response.put("mensaje", "La categoría se ha modificado satisfactoriamente.");
			response.put("categoria", categoriaActualizado);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
			
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar el proceso de edición la categoria.");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
}
	