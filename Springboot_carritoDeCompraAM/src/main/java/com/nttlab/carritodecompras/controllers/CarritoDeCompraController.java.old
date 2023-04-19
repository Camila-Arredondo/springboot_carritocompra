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
import com.nttlab.carritodecompras.models.service.iTotalCompraService;
@Controller
@RequestMapping("/carrito")
public class CarritoDeCompraController {
	
	@Autowired
	private JpaUserDetailService userService;
	@Autowired
	private iProductoService productoService;
	@Autowired
	private iCarritoService carritoSerice;
	@Autowired
	private iTotalCompraService totalcompraService;
	

	@GetMapping(value = "")
	public String listCarrito(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		var usuario = userService.findByUsername(user.getUsername());
		
		
		var productosCarrito = carritoSerice.findByUser(usuario);
		double total = 0;
		
		
		for(var carrito : productosCarrito) {
			total += (carrito.getProducto().getPrecio() * carrito.getCantidad());
		}
		
		
		
		model.addAttribute("productosCarrito", productosCarrito);
		model.addAttribute("total", total);

		return "productos/carrito";

	}
	
	
	@GetMapping(value = "/agregar/{id}/{redirect}")
	public String agregarCarrito(@PathVariable(value = "id") Long id,@PathVariable(value = "redirect") String redirect, Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		
		
		carritoSerice.addProduct(id, user.getUsername());
		
		return "redirect:/"+redirect;

	}
	@GetMapping(value = "/quitar/{id}/{redirect}")
	public String quitarProductoCarrito(@PathVariable(value = "id") Long id,@PathVariable(value = "redirect") String redirect, Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		
		
		
		carritoSerice.quitarProducto(id, user.getUsername());
		
		return "redirect:/"+redirect;

	}
	
	@GetMapping(value = "/eliminar/{id}/{redirect}")
	public String eliminarProductoCarrito(@PathVariable(value = "id") Long id,@PathVariable(value = "redirect") String redirect, Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		

		carritoSerice.eliminarProducto(id, user.getUsername());
		
		return "redirect:/"+redirect;

	}
	
	
	@GetMapping(value = "/vender")
	public String VenderCarrito(Model model, RedirectAttributes flash) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		var usuario = userService.findByUsername(user.getUsername());
		
		var productosCarrito = carritoSerice.findByUser(usuario);

		int idVenta = totalcompraService.addTotales(productosCarrito);
		
		carritoSerice.deleteAllByUsuario(usuario);
		return "redirect:/ventas/"+idVenta;

	}
}
