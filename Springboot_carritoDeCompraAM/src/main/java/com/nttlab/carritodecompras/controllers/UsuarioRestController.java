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
import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.entity.Usuario;
import com.nttlab.carritodecompras.models.service.iCarritoService;
import com.nttlab.carritodecompras.models.service.iProductoService;
import com.nttlab.carritodecompras.models.service.iUsuarioService;



@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")

public class UsuarioRestController {

	@Autowired
	private iUsuarioService usuarioService;
	
	@GetMapping(value = {"/usuario"}, produces = "application/json")
	public ResponseEntity<?> getAllUsuario(){
		List<Usuario> usuarios = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuarios = usuarioService.findAll();
			if(usuarios.isEmpty()) {
				response.put("mensaje", "No hay usuarios registrados en la base de datos");
				response.put("producto", usuarios);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			}
			response.put("mensaje", "Actualmente la base de datos cuenta con " + usuarios.size() + " registros");
			response.put("producto", usuarios);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	
	
	@GetMapping(value = "/usuario/{username}", produces = "application/json")
	public ResponseEntity<?> getUsuarioById(@PathVariable(value = "username", required = false) String username){
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = usuarioService.findByUsername(username);
			if(usuario == null) {
				response.put("mensaje","El usuario : " + username + " no existen en la base de datos.");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@DeleteMapping(value = "/usuario/{username}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable(value = "username") String username) {
		Usuario usuario = usuarioService.findByUsername(username);
		Map<String, Object> response = new HashMap<>();

		if (usuario == null) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			usuarioService.delete(usuario.getId());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	
	@PostMapping(value = "/usuario", produces = "application/json")
	public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody Usuario usuario) {
		Map<String, Object> response = new HashMap<>();
		try {
			for (Usuario a : usuarioService.findAll()) {
				if (a.getUsername().equalsIgnoreCase(usuario.getUsername())) {
					response.put("mensaje", "Error al realizar el registro del usuario. El nombre indicado ya existe en nuestros registros.");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
			Usuario usuarioNuevo = usuarioService.crearUsuario(usuario);
			response.put("mensaje", "Usuario registrado satisfactoriamente.");
			response.put("usuario", usuarioNuevo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PatchMapping(value = "/usuario/{username}", produces = "application/json")
	public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario, @PathVariable String username){
		Usuario usuarioActualizado = null;
		Usuario usuarioActual = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioActual = usuarioService.findByUsername(username);
			if(usuarioActual == null) {
				response.put("mensaje", "No se pudo completar el proceso de actualización ya que el usuario (ID " + username + ") no existe en nuestros registros");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			
			List<Usuario> usuarios =  usuarioService.findAll();
			for(Usuario a: usuarios) {
				if(usuarioActual.getId() != a.getId()) {
					if(a.getUsername().equalsIgnoreCase(usuario.getUsername())) {
						response.put("mensaje", "Error al realizar el registro del usuario. El usuario indicado ya existe en nuestros registros.");
						return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);	
					}
				}
			}
			
			usuarioActual.setUsername(usuario.getUsername());
			
			usuarioActualizado = usuarioService.crearUsuario(usuario);
			response.put("mensaje", "El producto modificado satisfactoriamente.");
			response.put("producto", usuarioActualizado);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
			
		}
		catch(DataAccessException ex) {
			response.put("mensaje", "Error al realizar el proceso de edición del usuario.");
			response.put("error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
}
