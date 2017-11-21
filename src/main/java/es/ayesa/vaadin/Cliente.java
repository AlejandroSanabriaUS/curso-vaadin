package es.ayesa.vaadin;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class Cliente implements Serializable, Cloneable {
	private Long id;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private String email;
	private ClienteEstado estado;
	
	@Override
	public Cliente clone() throws CloneNotSupportedException {
		return (Cliente) super.clone();
	}
	
	@Override
	public int hashCode() {
		int hash = 5;
		
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		
		return hash ;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( this == obj ) {
			return true;
		}
		
		if(this.id == null) {
			return false;
		}
		
		if( obj instanceof Cliente && obj.getClass().equals( getClass() ) ) {
			return this.id.equals(((Cliente) obj).id );
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return nombre + " " + apellido;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ClienteEstado getEstado() {
		return estado;
	}
	public void setEstado(ClienteEstado estado) {
		this.estado = estado;
	}
}
