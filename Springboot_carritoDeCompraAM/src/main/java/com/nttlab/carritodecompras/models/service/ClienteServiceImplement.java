package com.nttlab.carritodecompras.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nttlab.carritodecompras.models.dao.iClienteDAO;
import com.nttlab.carritodecompras.models.entity.Cliente;

public class ClienteServiceImplement implements iClienteService{

	@Autowired
	private iClienteDAO clienteDao;
	
	
	@Transactional()
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}
	
	@Transactional()
	public Cliente findByEmail(String email) {
		Cliente c = clienteDao.findByEmail(email);
		if(c != null) {
			return c;
		}
		return null;
	}

	@Override
	public Cliente findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
