package com.nttlab.carritodecompras.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nttlab.carritodecompras.models.entity.Producto;
import com.nttlab.carritodecompras.models.service.JpaUserDetailService;
import com.nttlab.carritodecompras.models.service.iCarritoService;
import com.nttlab.carritodecompras.models.service.iProductoService;

@Controller
@RequestMapping("/productos")
public class ProductosController {

	@Autowired
	private iProductoService productoService;
	@Autowired
	private JpaUserDetailService userService;
	@Autowired
	private iCarritoService carritoSerice;
	
	
	@GetMapping(value = "")
	public String productoCatálogo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		var usuario = userService.findByUsername(user.getUsername());

		
		var productosCarrito = carritoSerice.findByUser(usuario);
		var productos = productoService.findAll();

		for(var producto: productos) {
			for(var carrito: productosCarrito) {
				if(producto.equals(carrito.getProducto())) {
					producto.cantidad = carrito.getCantidad();
				}
			}
		}
		
		model.addAttribute("titulo", "SpringCart");
		model.addAttribute("productos", productos);
		return "productos/listaProductos";
	}

	@GetMapping(value = "/eliminar/{id}")
	public String eliminarAlumno(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Producto producto = productoService.findById(id);
		if (producto == null) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("error", "El producto buscado no se encuentra en nuestros registros");
			return "redirect:/productos";
		} else {
			productoService.deleteById(id);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("success", "Producto eliminado del carrito");
			return "redirect:/productos";
		}
	}
	
	@GetMapping(value = "/nuevo")
	public String crearproducto(Model model, RedirectAttributes flash) {
		model.addAttribute("titulo", "SpringCart");
		return "/productos/newproduct";
	}
	
	@PostMapping(value="/nuevo")
	public String CrearUsuario(@ModelAttribute("productoForm") Producto producto,
			org.springframework.ui.Model model,
			Principal principal,
			RedirectAttributes redirectAttributes, RedirectAttributes flash) {
		
		
		productoService.crearProducto(producto);
		flash.addFlashAttribute("success", "Producto " + producto.getNombre() + " creado correctamente");

		return "redirect:/productos";
	}
}