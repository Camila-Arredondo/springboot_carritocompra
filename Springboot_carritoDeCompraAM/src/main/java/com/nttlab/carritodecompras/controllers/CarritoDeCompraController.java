package com.nttlab.carritodecompras.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.nttlab.carritodecompras.models.service.JpaUserDetailService;
import com.nttlab.carritodecompras.models.service.iCarritoService;
import com.nttlab.carritodecompras.models.service.iProductoService;
@Controller
@RequestMapping("/carrito")
public class CarritoDeCompraController {
	
	@Autowired
	private JpaUserDetailService userService;
	@Autowired
	private iProductoService productoService;
	@Autowired
	private iCarritoService carritoSerice;

	
	
	
	
	@GetMapping(value = "/agregar/{id}")
	public String agregarCarrito(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		var usuario = userService.findByUsername(user.getUsername());
		
		var producto = productoService.findOne(id);
		
		carritoSerice.addProduct(producto, usuario);
		
		return "redirect:/productos";

	}
	@GetMapping(value = "/quitar/{id}")
	public String quitarProductoCarrito(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		var usuario = userService.findByUsername(user.getUsername());
		
		var producto = productoService.findOne(id);
		
		carritoSerice.quitarProducto(producto, usuario);
		
		return "redirect:/productos";

	}
	
	@GetMapping(value = "/eliminar/{id}")
	public String eliminarProductoCarrito(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		var usuario = userService.findByUsername(user.getUsername());
		
		var producto = productoService.findOne(id);
		
		carritoSerice.eliminarProducto(producto, usuario);
		
		return "redirect:/productos";

	}
}
