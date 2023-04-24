package com.nttlab.carritodecompras.models.service;
import org.springframework.dao.DataAccessException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttlab.carritodecompras.models.dao.iVentasDAO;
import com.nttlab.carritodecompras.models.entity.Usuario;
import com.nttlab.carritodecompras.models.entity.Ventas;
@Service
public class VentasImplement implements iVentasService {
    @Autowired
    public iVentasDAO ventasDao;

    public List<Ventas> findByUsuario(String usuario){
        return ventasDao.findByUsuario(usuario);
    }

    public Ventas findByIdAndUsuario(long id, String usuario) {
        return ventasDao.findByIdAndUsuario(id, usuario);
    }
}
