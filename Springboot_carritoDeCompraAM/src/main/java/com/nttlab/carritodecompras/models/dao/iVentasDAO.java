package com.nttlab.carritodecompras.models.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.nttlab.carritodecompras.models.entity.Usuario;
import com.nttlab.carritodecompras.models.entity.Ventas;
public interface iVentasDAO extends CrudRepository<Ventas, Long> {

	
    List<Ventas> findByUsuario(Usuario usuario);
	Ventas findByIdAndUsuario(long id, Usuario usuario);
}
