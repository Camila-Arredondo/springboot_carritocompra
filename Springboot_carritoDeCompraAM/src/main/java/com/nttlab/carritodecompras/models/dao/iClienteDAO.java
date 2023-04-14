package com.nttlab.carritodecompras.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nttlab.carritodecompras.models.entity.Cliente;

@Repository
public interface iClienteDAO extends CrudRepository<Cliente, Long> {

	public Cliente findByUsername(String username);

	public Cliente findByEmail(String email);

}
