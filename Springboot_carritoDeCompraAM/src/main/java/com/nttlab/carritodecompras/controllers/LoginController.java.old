package com.nttlab.carritodecompras.controllers;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nttlab.carritodecompras.models.entity.Usuario;
import com.nttlab.carritodecompras.models.service.JpaUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Controller
public class LoginController {
	@Autowired
	private JpaUserDetailService jpauserdetailservice;

	@GetMapping(value="/login")
	public String login(@RequestParam(value="error",required = false) String error, 
			@RequestParam(value="logout", required=false) String logout,
			org.springframework.ui.Model model,
			Principal principal,
			RedirectAttributes redirectAttributes) {
		
		if(principal != null) {
			redirectAttributes.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente");		
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("danger","Error en el login. Nombre de usuario o contraseña incorrectos. Por favor vuelva a intentarlo!!!");
		}
		
		if(logout != null) {
			model.addAttribute("success","Se ha cerrado la sessión exitosamente!!!");
		}
		model.addAttribute("titulo");

		return "login/login";
		
	}
	
	@GetMapping(value="/registrar")
	public String Registrar(@RequestParam(value="error",required = false) String error, 
			@RequestParam(value="logout", required=false) String logout,
			org.springframework.ui.Model model,
			Principal principal,
			RedirectAttributes redirectAttributes) {
		
		
		model.addAttribute("titulo");

		return "form";
		
	}
	
	@PostMapping(value="/registrar/crearusuario")
	public String CrearUsuario(@ModelAttribute("usuarioForm") Usuario usuario,
			org.springframework.ui.Model model,
			Principal principal,
			RedirectAttributes redirectAttributes) {
	
		
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(usuario.getPassword());

		usuario.setPassword(encodedPassword);
		System.out.println(usuario.getPassword());

		String respuesta = jpauserdetailservice.createUser(usuario);
		
		if(respuesta.equals("OK")) {
			redirectAttributes.addFlashAttribute("info", "Se ha creado el usuario con exito");		
			return "redirect:/login";
			
		}else  {
			redirectAttributes.addFlashAttribute("danger", respuesta);		
			return "redirect:/registrar";
		}
		
		
		
		
	}
}
