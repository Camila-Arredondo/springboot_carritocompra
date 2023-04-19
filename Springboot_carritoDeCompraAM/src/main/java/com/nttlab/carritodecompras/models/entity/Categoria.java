package com.nttlab.carritodecompras.models.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "categoria")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;

	
	public Categoria() {

	}

	public Categoria(String nombre) {
		super();
		this.nombre = nombre;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}

	private static final long serialVersionUID = 1L;
	
	
	
}

