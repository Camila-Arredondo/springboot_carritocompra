package com.nttlab.carritodecompras.models.service;

import org.springframework.stereotype.Repository;

import com.nttlab.carritodecompras.models.entity.Cliente;

@Repository
public interface iClienteService {
	
	public Cliente save(Cliente cliente);

	
	public Cliente findByEmail(String email);
	
	public Cliente findByUsername(String username);
}
