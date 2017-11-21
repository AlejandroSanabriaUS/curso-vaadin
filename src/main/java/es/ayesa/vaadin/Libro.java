package es.ayesa.vaadin;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Libro implements Serializable, Cloneable {
	private Long id;
	private String nombre;
	private Cliente prestadoA;
	
	public boolean isPrestado() {
		return prestadoA != null;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( this == obj ) {
			return true;
		}
		
		if(this.id == null) {
			return false;
		}
		
		if( obj instanceof Libro && obj.getClass().equals( getClass() ) ) {
			return this.id.equals(((Libro) obj).id );
		}
		
		return false;
	}
	
	@Override
	public Libro clone() throws CloneNotSupportedException {
		return (Libro) super.clone();
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		
		hash = 53 * hash + (id == null ? 0 : id.hashCode());
		
		return hash ;
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

	public Cliente getPrestadoA() {
		return prestadoA;
	}

	public void setPrestadoA(Cliente prestadoA) {
		this.prestadoA = prestadoA;
	}

}
