package es.ayesa.vaadin;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;

@SuppressWarnings("serial")
public class ClienteFormBack extends ClienteForm {
	private ClienteService clienteService = ClienteService.getInstancia();
	private Binder<Cliente> binder = new Binder<>( Cliente.class );
	private Cliente cliente;
	private ClienteView padre;
	
	public ClienteFormBack( ClienteView clienteView ) {
		this.padre = clienteView;
		
		guardar.setClickShortcut( KeyCode.ENTER );
		estado.setItems( ClienteEstado.values() );
		
		binder.bindInstanceFields( this );
		
		guardar.addClickListener( e -> this.guardar() );
		borrar.addClickListener( e -> this.borrar() );
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		binder.setBean( cliente );
		
		borrar.setVisible( cliente.getId() != null );
		setVisible( true );
		nombre.selectAll();
	}

	private void borrar() {
		clienteService.borrar( cliente );
		padre.actualizarTabla();
		setVisible( false );
	}

	private void guardar() {
		clienteService.guardar( cliente );
		padre.actualizarTabla();
		setVisible( false );
	}
	
}
