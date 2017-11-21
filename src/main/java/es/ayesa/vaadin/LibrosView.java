package es.ayesa.vaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial", "deprecation" })
public class LibrosView extends VerticalLayout implements View {
	private LibroService libroService = LibroService.getInstancia();
	private Grid<Libro> libros = new Grid<>(Libro.class);
	private TextField filterText = new TextField();
	private Navigator navigator;

	public static final String NAME = "libro";
	private ClienteView clienteView;

	public LibrosView(Navigator navigator) {
		this.navigator = navigator;
		clienteView = new ClienteView(navigator);
		clienteView.setVisible(false);

		libros.setSelectionMode(SelectionMode.SINGLE);
		libros.setColumns("nombre");
		Column<Libro, String> prestado = libros
				.addColumn(libro -> libro.getPrestadoA() == null ? "" : libro.getPrestadoA().toString());
		prestado.setCaption("Prestado");

		Column<Libro, String> accion = libros.addColumn(libro -> {
			return libro.isPrestado() ? "Devolver" : "Prestar";
		},
				new ButtonRenderer<>(evento -> {
					Libro libro = evento.getItem();
					if (libro.isPrestado()) {
						libro.setPrestadoA(null);
						libroService.guardar(libro);
						actualizarTabla();
					} else {
						clienteView.setLibro(libro, this::callBack);
					}
				}));
		accion.setCaption("Acción");
		accion.setMinimumWidthFromContent(false);

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

		libros.setSizeFull();

		HorizontalLayout botonera = new HorizontalLayout();

		Button navegar = new Button("Clientes");
		navegar.addClickListener(event -> this.navigator.navigateTo(ClienteView.NAME));

		botonera.addComponents(filtrado, navegar);

		this.addComponents(botonera, libros, clienteView);
	}

	public void actualizarTabla() {
		libros.setItems(libroService.findAll(filterText.getValue()));
	}

	public void callBack(Libro libro) {
		libroService.guardar(libro);
		actualizarTabla();
	}
}
