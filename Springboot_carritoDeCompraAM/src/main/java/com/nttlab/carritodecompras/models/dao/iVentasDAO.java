package com.nttlab.carritodecompras.models.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.nttlab.carritodecompras.models.entity.Usuario;
import com.nttlab.carritodecompras.models.entity.Ventas;
public interface iVentasDAO extends CrudRepository<Ventas, Long> {

	
    public List<Ventas> findByUsuario(String usuario);
	public Ventas findByIdAndUsuario(long id, String usuario);
}
