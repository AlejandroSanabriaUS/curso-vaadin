package es.ayesa.vaadin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LibroService {
	private static LibroService instancia;
	private final Map<Long, Libro> libros = new HashMap<>();
	private long siguienteId = 0;
	
	private LibroService() {}
	
	public static LibroService getInstancia() {
		if( instancia == null ) {
			instancia = new LibroService();
			instancia.generarDatos();
		}
		
		return instancia;
	}

	private void generarDatos() {
		if( findAll().isEmpty() ) {
			final String[] nombres = new String[] { "Headfirst Java", "Thinking in Java", "Modern Java EE Parterns", "Java Antipaterns", "High Performance Hibernate", "Clean Code" };
			
			for( String nombre : nombres ) {
				Libro Libro = new Libro();
				Libro.setNombre( nombre );
				
				guardar( Libro );
			}
		}
	}

	public List<Libro> findAll() {
		return findAll( null );
	}

	public List<Libro> findAll(String cadena) {
		return libros.values().stream().filter( contacto -> {
			return ( cadena == null || cadena.isEmpty() ) ||  
					 contacto.getNombre().toLowerCase().contains( cadena.toLowerCase() );
		}).collect(Collectors.toList());
	}
	
	public long count() {
		return libros.size();
	}
	
	public void borrar( Libro Libro ) {
		libros.remove( Libro.getId() );
	}

	public void guardar(Libro libro) {
		if( libro == null ) {
			return;
		} else {
			if( libro.getId() == null ) {
				libro.setId(siguienteId++);
			}
		}
		
		try {
			libro = libro.clone();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		libros.put( libro.getId(), libro );
	}

}
