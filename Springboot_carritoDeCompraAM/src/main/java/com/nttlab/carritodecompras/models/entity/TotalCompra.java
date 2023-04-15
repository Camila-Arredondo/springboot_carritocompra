package com.nttlab.carritodecompras.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "total_compra")
public class TotalCompra implements Serializable  {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer numeroOrden;
    private double cantidad;
 
    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    
    public TotalCompra() {
		
	}

	public TotalCompra(double cantidad, Producto producto, Integer numeroOrden) {
		super();
		this.cantidad = cantidad;
		this.producto = producto;
		this.numeroOrden = numeroOrden;
	}

	public TotalCompra(double cantidad, Producto producto) {
		super();
		this.cantidad = cantidad;
		this.producto = producto;
	}



	public Integer getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(Integer numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}




    
    
    
}
