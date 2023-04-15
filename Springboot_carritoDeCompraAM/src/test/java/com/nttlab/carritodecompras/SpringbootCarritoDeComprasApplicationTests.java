package com.nttlab.carritodecompras;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nttlab.carritodecompras.models.entity.Producto;

import com.nttlab.carritodecompras.models.service.iProductoService;

@SpringBootTest

class SpringbootCarritoDeComprasApplicationTests {

	@Autowired
	private iProductoService productoService;

	@Test
	void listarProductos() {
		List<Producto> productos = productoService.findAll();

		assertThat(productos.size() == 10);
	}
	
	@Test
	void eliminarProductos(){
		
		productoService.deleteById(1L);
		Producto producto = productoService.findById(1L);
		
		assertThat(producto == null);
		
	}
	
	@Test
	void buscarProductoNombre(){
		
		var producto = productoService.findByNombre("Arroz LaFuente");
		
		assertThat(producto.nombre == "Arroz LaFuente");
		
	}
	
	@Test
	void crearProducto(){
		
		var producto = new Producto(
				"ProductoPrueba",
				"Descripcion de prueba",
				"Categoria de prueba",
				10000
				);
		
		productoService.crearProducto(producto);
		
		var productoValidar = productoService.findByNombre("ProductoPrueba");
		
		assertThat(productoValidar.nombre == "ProductoPrueba");
		
	}
	
	@Test
	void buscarPorCAtegoria(){
		
		var producto = productoService.findByCategoria("Confitería");
		
		assertThat(producto.categoria == "chocolate trencito");
		
		
	}
}