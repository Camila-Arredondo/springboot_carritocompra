package com.nttlab.carritodecompras.models.entity;

import java.io.Serializable;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, name="username",unique=true)
	private String username;


	@Email(message = "Formato ingresado no es válido")
	@NotEmpty(message = "Este campo debe ser ingresado")
	@Column(name = "email", unique = true)
	private String email;

	@Column(length = 255)
	private String password;

	private boolean active;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Role> roles;

	public Cliente() {

	}

	public Cliente(Long id, String nombre, String email, String password) {
		super();
		this.id = id;
		
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id  + ", email=" + email + ", password=" + password + "]";
	}

	private static final long serialVersionUID = 1L;

	

}
