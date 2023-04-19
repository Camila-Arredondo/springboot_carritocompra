package com.nttlab.carritodecompras.controllers;
/*package com.nttlab.carritodecompras.controllers;
 
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nttlab.carritodecompras.models.entity.Carrito;
import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.service.JpaUserDetailService;
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
	

	@GetMapping(value = "/carrito", produces = "application/json")
	public ResponseEntity<Map<String, Object>> listCarrito() {
		var carrito = productoService.findAll();
		Map<String, Object> response = new HashMap<>();
		response.put("cantidad", carrito.size());
		response.put("data", carrito);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	@DeleteMapping(value = "/productos/eliminar/{idProducto}" , produces = "application/json")
	public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable(value = "idProducto") Long idProducto username) {
		Carrito carrito = carritoService.eliminarProducto(idProducto, username);
		Map<String, Object> response = new HashMap<>();

		if (carrito == null) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			carritoService.findByUser(username);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	

	
	
	
	
	
	
	
	
	@GetMapping(value = "/agregar/{id}/{redirect}")
	public String agregarCarrito(@PathVariable(value = "id") Long id,@PathVariable(value = "redirect") String redirect, Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		
		carritoService.addProduct(id, user.getUsername());
		return "redirect:/"+redirect;
	}
	
	@GetMapping(value = "/quitar/{id}/{redirect}")
	public String quitarProductoCarrito(@PathVariable(value = "id") Long id,@PathVariable(value = "redirect") String redirect, Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		
		carritoService.quitarProducto(id, user.getUsername());
		return "redirect:/"+redirect;
	}
	
	@GetMapping(value = "/productos/eliminar/{idProducto}" , produces = "application/json")
	public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable(value = "idProducto") Long idProducto username) {
		Carrito carrito = carritoService.eliminarProducto(idProducto, username);
		Map<String, Object> response = new HashMap<>();

		if (carrito == null) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			carritoService.findByUser(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/pagar")
	public String VenderCarrito(Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		
		
		
		
		
		
		var productosCarrito = carritoSerice.findByUser(usuario);
		
		
		
		
		int idVenta = totalcompraService.addTotales(productosCarrito);
		
		carritoSerice.deleteAllByUsuario(usuario);
		return "redirect:/ventas/"+idVenta;








	}
}
*/