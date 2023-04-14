package com.nttlab.carritodecompras.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nttlab.carritodecompras.models.entity.Cliente;
import com.nttlab.carritodecompras.models.service.iClienteService;

import jakarta.validation.Valid;

@RestController
public class ClienteController {

	@Autowired
	private iClienteService clienteService;

	@GetMapping("/nuevo")
	public String crearCliente(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Regístrate");
		return "nuevo";
	}

	@PostMapping(value = "/formulario")
	public String guardarCliente(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Rellena el formulario");
			return "formulario";
		}

		else if (clienteService.findByEmail(cliente.getUsername()) != null) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "Email ya registrado en nuestro sistema.");
			return "redirect:/nuevo";
		} else {

			clienteService.save(cliente);
			status.setComplete();
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Cliente registrado satisfactoriamente.");
			return "redirect:/listar";
		}
	}
}