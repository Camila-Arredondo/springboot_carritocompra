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
@Table(name = "carrito")
public class Carrito implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //private String nombre;
    private double cantidad;
    //private double precio;
    //private double total;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
   // @OneToMany(mappedBy = "carrito")
  //  private List<Carrito> detalle;
    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    public Carrito() {
		
	}
    
    
	//public Carrito(Integer id, String nombre, double cantidad, double precio, double total) {
	public Carrito(double cantidad, Producto producto, Usuario usuario) {

		super();
		//this.id = id;
		//this.nombre = nombre;
		this.cantidad = cantidad;
		this.producto = producto;
		this.usuario = usuario;
		//this.precio = precio;
		//this.total = total;
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

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
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
