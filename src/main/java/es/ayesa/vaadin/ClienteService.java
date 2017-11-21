package es.ayesa.vaadin;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ClienteService {
	private static ClienteService instancia;
	private final Map<Long, Cliente> contactos = new HashMap<>();
	private long siguienteId = 0;
	
	private ClienteService() {}
	
	public static ClienteService getInstancia() {
		if( instancia == null ) {
			instancia = new ClienteService();
			instancia.generarDatos();
		}
		
		return instancia;
	}

	private void generarDatos() {
		if( findAll().isEmpty() ) {
			final String[] nombres = new String[] { "Cristina Barea", "Adrian Cardona", "Ramon Chavez", "Pablo Feas", "Daniel Fernandez", "Jorge Fontalba", "Jose Gelo", "Jose Gonzalez" };
			Random random = new Random(0);
			
			for( String nombreCompleto : nombres ) {
				String[] separados = nombreCompleto.split(" ");
				Cliente cliente = new Cliente();
				cliente.setNombre(separados[0]);
				cliente.setApellido(separados[1]);
				cliente.setEmail(separados[0] + "@" + separados[1] + ".com");
				cliente.setEstado(ClienteEstado.values()[random.nextInt( ClienteEstado.values().length )]);
				long diasVida = 0 - random.nextInt(365 * 15 + 365 * 60);
				cliente.setFechaNacimiento(LocalDate.now().plusDays(diasVida));
				
				guardar( cliente );
			}
		}
	}

	public List<Cliente> findAll() {
		return findAll( null );
	}

	public List<Cliente> findAll(String cadena) {
		return contactos.values().stream().filter( contacto -> {
			return ( cadena == null || cadena.isEmpty() ) ||  
					( contacto.getNombre().toLowerCase().contains(cadena.toLowerCase()) || contacto.getApellido().toLowerCase().contains(cadena.toLowerCase()) );
		}).collect(Collectors.toList());
	}
	
	public long count() {
		return contactos.size();
	}
	
	public void borrar( Cliente cliente ) {
		contactos.remove( cliente.getId() );
	}

	public void guardar(Cliente cliente) {
		if( cliente == null ) {
			return;
		} else {
			if( cliente.getId() == null ) {
				cliente.setId(siguienteId++);
			}
		}
		
		try {
			cliente = cliente.clone();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		contactos.put( cliente.getId(), cliente );
	}
}
