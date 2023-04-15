package com.nttlab.carritodecompras.models.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nttlab.carritodecompras.models.entity.Usuario;
import com.nttlab.carritodecompras.models.entity.Ventas;
@Service
public interface iVentasService {
	public List<Ventas> findByUsuario(Usuario usuario);
	public Ventas findByIdAndUsuario(long id, Usuario usuario);
}
