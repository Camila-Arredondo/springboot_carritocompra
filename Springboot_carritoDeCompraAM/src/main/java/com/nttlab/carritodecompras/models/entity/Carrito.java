package com.nttlab.carritodecompras.models.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name = "carrito")
public class Carrito implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double cantidad;
    @Transient
	private Integer productoid;
    private String usuario;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    public Carrito() {
		
	}
    
    
	public Carrito(double cantidad, Producto producto, String usuario) {
		super();
		this.cantidad = cantidad;
		this.producto = producto;
		this.usuario = usuario;
	}

	public Carrito(Integer productoid, String usuario) {
		super();
		this.productoid = productoid;
		this.usuario = usuario;

	}
	public Integer getProductoid() {
		return productoid;
	}


	public void setProductoid(Integer productoid) {
		this.productoid = productoid;
	}


	public Integer getId() {
		return id;
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

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public TotalCompra toTotalCompra() {
		return new TotalCompra(
				this.cantidad,
				this.producto
				);
	}


//	@Override
//	public String toString() {
//		return "Carrito [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio + ", total="
//				+ total + "]";
//	}
	
	private static final long serialVersionUID = 1L;

}
