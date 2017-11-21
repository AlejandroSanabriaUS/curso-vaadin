package es.ayesa.vaadin;

import java.util.function.Consumer;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial", "deprecation" })
public class ClienteView extends VerticalLayout implements View {
	private ClienteService clienteService = ClienteService.getInstancia();
	private Grid<Cliente> grid = new Grid<>(Cliente.class);
	private TextField filterText = new TextField();
	private ClienteFormBack form = new ClienteFormBack(this);
	private Navigator navigator;
	private Libro libro;
	
	public static final String NAME = "";
	
	private ValueChangeListener<Cliente> setCliente = evento -> {
		if (evento.getValue() == null) {
			form.setVisible(false);
		} else {
			form.setCliente(evento.getValue());
		}
	};
	
	private Registration gridEventRegistration;

	public ClienteView( Navigator navigator ) {
		this.navigator = navigator;
		
		form.setVisible(false);

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setColumns("nombre", "apellido", "email");

		actualizarTabla();

		Button borrarFiltro = new Button(FontAwesome.TIMES);
		borrarFiltro.setDescription("Borrar filtro");
		borrarFiltro.addClickListener(e -> filterText.clear());

		filterText.setPlaceholder("Filtrar por nombre:");
		filterText.addValueChangeListener(e -> actualizarTabla());
		filterText.setValueChangeMode(ValueChangeMode.LAZY);

		CssLayout filtrado = new CssLayout();
		filtrado.addComponents(filterText, borrarFiltro);
		filtrado.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		HorizontalLayout main = new HorizontalLayout();
		main.addComponents(grid, form);
		main.setSizeFull();
		grid.setSizeFull();
		form.setSizeFull();
		main.setExpandRatio(grid, 2);
		main.setExpandRatio(form, 1);

		gridEventRegistration = grid.asSingleSelect().addValueChangeListener(setCliente);

		HorizontalLayout botonera = new HorizontalLayout();

		Button nuevoCliente = new Button("Nuevo cliente");
		nuevoCliente.addClickListener(evento -> {
			grid.asSingleSelect().clear();
			form.setCliente(new Cliente());
		});

		Button navegar = new Button( "Libros" );
		navegar.addClickListener(event -> this.navigator.navigateTo( LibrosView.NAME ) );
		
		botonera.addComponents(filtrado, nuevoCliente, navegar );

		this.addComponents(botonera, main);
	}
	
	public void actualizarTabla() {
		grid.setItems( clienteService.findAll( filterText.getValue() ) );
	}

	public void setLibro(Libro libro, Consumer<Libro> callback) {
		this.libro = libro;
		gridEventRegistration.remove();
		
		gridEventRegistration = grid.asSingleSelect().addValueChangeListener( evento -> {
			this.libro.setPrestadoA( evento.getValue() );
			
			this.setVisible(false);
			gridEventRegistration.remove();
			gridEventRegistration = grid.asSingleSelect().addValueChangeListener(setCliente);
			
			callback.accept(this.libro);
		} );
		
		this.setVisible(true);
	}
}
