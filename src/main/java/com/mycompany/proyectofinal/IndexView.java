
package com.mycompany.proyectofinal;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.function.BiConsumer;

@Route("")
public class IndexView extends HorizontalLayout{
    
    public Hotel hotel;
    
    public IndexView(){
           
        hotel = new Hotel();
        
        
        
            VerticalLayout contenedorHabitaciones = new VerticalLayout();

            var titulo = new H1("Hotel universitario");
            contenedorHabitaciones.add(titulo);
            List<Integer> primeros_pisos = Arrays.asList(2, 3, 4,5,6);
            contenedorHabitaciones.add(pintarHabitacionesPorPiso(primeros_pisos));

                Scroller scroller = new Scroller(contenedorHabitaciones);
                //scroller.setMaxWidth(5000, Unit.PIXELS);
                //scroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);


        add(scroller);
        CheckoutView();
        /*Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Prueba de título");
        dialog.setModal(false);
        dialog.open();*/
        
    }
    
    public void CheckoutView() {
        // Crear el título del Card
        H3 titulo = new H3("Checkout habitación XXX");

        // Detalles del cliente
        TextField nombreCliente = new TextField("Nombre del Cliente");
        nombreCliente.setReadOnly(true);
        TextField idCliente = new TextField("Identificación");
        idCliente.setReadOnly(true);
        FormLayout detallesCliente = new FormLayout(nombreCliente, idCliente);

        // Listado de servicios comprados
        VerticalLayout listaServicios = new VerticalLayout();
        listaServicios.add(new Label("Servicios Comprados:"));
        listaServicios.add(new Label("1. Servicio de limpieza"));
        listaServicios.add(new Label("2. Servicio de lavandería"));
        listaServicios.add(new Label("3. Desayuno incluido"));
        // Agrega más servicios según sea necesario

        // Campo para ingresar número de noches y botón para generar factura
        NumberField numeroNoches = new NumberField("Número de Noches");
        Button generarFacturaButton = new Button("Generar Factura" /*event -> mostrarFacturaDialogo(nombreCliente.getValue(), idCliente.getValue(), numeroNoches.getValue())*/);
        generarFacturaButton.getStyle().set("margin-top", "35px");
        HorizontalLayout generarFacturaLayout = new HorizontalLayout(numeroNoches, generarFacturaButton);

        // Card principal
        VerticalLayout card = new VerticalLayout(titulo, detallesCliente, listaServicios, generarFacturaLayout);
        card.getStyle().set("border", "1px solid #ccc"); // Borde para simular Card
        card.getStyle().set("border-radius", "8px");
        card.getStyle().set("padding", "20px");
        card.getStyle().set("width", "600px");
        card.getStyle().set("height", "900px");
        card.getStyle().set("box-shadow", "2px 2px 10px rgba(0, 0, 0, 0.1)");

        // Añadir el Card al layout principal
        add(card);
    }
    
    public ArrayList<Button> listProductosVenta(Habitacion habitacion, BiConsumer<ClickEvent<Button>, Producto> callback){
        ArrayList btnList = new ArrayList<>();
        
        if(habitacion.minibar != null){
            for (int i = 0; i < habitacion.minibar.productos.size(); i++) {
               Producto producto = habitacion.minibar.productos.get(i);
               Button boton = new Button(producto.getNombre());
                      boton.addClickListener(event -> callback.accept(event, producto));
               btnList.add(boton);
           }
          
        }else{
            btnList.add(new Button("BATA",(event) -> callback.accept(event, new Producto(Minibar.BATA,70000,"MINIBAR") )));
        }
         
         Restaurante restaurante = new Restaurante();
         btnList.add(new Button("DESAYUNO",event -> callback.accept(event, Restaurante.desayuno )));
         btnList.add(new Button("ALMUERZO",event -> callback.accept(event, Restaurante.almuerzo )));
         btnList.add(new Button("CENA",event -> callback.accept(event, Restaurante.cena )));
         btnList.add(new Button("SERVICIO_HABITACION",event -> callback.accept(event, Restaurante.servicio_habitacion )));
         
        return btnList;
    }
    
    public VerticalLayout pintarHabitacionesPorPiso(List<Integer> pisos ){
        
        VerticalLayout pisosLayout = new VerticalLayout();
        
        
        for (Integer piso : pisos) {
            FlexLayout container = new FlexLayout();
            container.setMaxWidth(500,Unit.PIXELS);
            container.getStyle().set("flex-wrap", "wrap"); 

            
            for (int i = 0; i < hotel.recepcion.habitaciones_list.size(); i++) {
                if(piso.equals(hotel.recepcion.habitaciones_list.get(i).getPiso()) ){
                    Habitacion habitacion = hotel.recepcion.habitaciones_list.get(i);
                    Button habitacionBtn  = new Button("Habitacion: "+habitacion.getNumero()+" "+habitacion.getTipo());
                    if(habitacion.estaOcupada()){
                        habitacionBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
                    }else{
                        habitacionBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    }
                    habitacionBtn.addClickListener( clicEvent -> {
                         
                        if(habitacion.estaOcupada()){
                            
                            /* Dialogo para cuando está ocupada */
                            Dialog dialogOcupada = new Dialog();
                            dialogOcupada.setHeaderTitle("Habitación "+habitacion.getNumero()+" "+habitacion.getTipo()+" | OCUPADA");
                            dialogOcupada.add(new Text("Cliente: "+habitacion.getNombre()+" | "+habitacion.getIdCliente()));
                            
                            Button cancelBtn = new Button("Cancelar",  new Icon(VaadinIcon.BAN), (e) -> dialogOcupada.close());
                            Button agregaProductoBtn = new Button("Agregar Producto a la cuenta", new Icon(VaadinIcon.PLUS) , (e) ->{
                                dialogOcupada.close();
                                Dialog dialogAdd = new Dialog();
                                dialogAdd.setHeaderTitle("Habitación "+habitacion.getNumero()+" "+habitacion.getTipo()+" | OCUPADA");
                                
                                    VerticalLayout containerProductosCuenta = new VerticalLayout();
                                
                                containerProductosCuenta.add(new Text("Cliente: "+habitacion.getNombre()+" | "+habitacion.getIdCliente()));
                                
                                Text totalText = new Text("  Total cuenta: $"+habitacion.getTotalProductos() );
                                
                                containerProductosCuenta.add(totalText);
                                
                                ArrayList btnList = new ArrayList<Button>();
                                
                                btnList = listProductosVenta(habitacion,(event,producto) -> {
                                   boolean agregar =  habitacion.agregarProducto(producto.getTipo(), producto.getNombre(), habitacion);
                                   if(agregar){
                                       mostrarNotificacion("bien", "Agregado correctamente");
                                       if(producto.getTipo().equals("MINIBAR")){
                                           Button thisButton = (Button) event.getSource();
                                           remove(thisButton);
                                       }
                                   }else{
                                       mostrarNotificacion("error", "El producto no se puede agregar, no está en el minivar o disponible");
                                   }
                                   totalText.setText(" Total cuenta: $"+habitacion.getTotalProductos() );
                                   
                                });
                                
                                containerProductosCuenta.add(btnList);
                                
                                dialogAdd.add(containerProductosCuenta);
                                Button cerrar = new Button("Cancelar",new Icon(VaadinIcon.BAN), (ev) -> dialogAdd.close());
                                dialogAdd.getFooter().add( cerrar );
                                dialogAdd.open();
                            } );
                            Button hacerCheckoutBtn = new Button("Hacer checkout", new Icon(VaadinIcon.CHECK_CIRCLE));
                            
                            dialogOcupada.getFooter().add(cancelBtn);
                            dialogOcupada.getFooter().add(agregaProductoBtn);
                            dialogOcupada.getFooter().add(hacerCheckoutBtn);
                            dialogOcupada.open();
                            
                            
                        }else{
                            Dialog dialog = new Dialog();
                            dialog.setHeaderTitle("Habitación "+habitacion.getNumero()+" "+habitacion.getTipo()+" Disponible");
                            dialog.add(new Text("Precio noche: $"+habitacion.getPrecioNoche()+ " | "));
                            dialog.add(new Text(habitacion.getDetalles()));

                            Button cancelBtn = new Button("Cancelar",  new Icon(VaadinIcon.BAN));
                            cancelBtn.addClickListener((e) -> dialog.close());
                            
                            Button ocuparHabitacion = new Button("Ocupar habitación", (e) -> {
                                dialog.close();
                                Dialog dialogForm = new Dialog();
                                dialogForm.setHeaderTitle("CheckIn Habitación "+habitacion.getNumero()+" "+habitacion.getTipo());
                                dialogForm.add(formCheckIn(habitacion, habitacionBtn,dialogForm));
                                dialogForm.open();
                            } );
                            
                            dialog.getFooter().add(cancelBtn);
                            dialog.getFooter().add(ocuparHabitacion);
                            dialog.open();
                        }
                        
                        
                        //UI.getCurrent().getPage().reload();
                    } );
                    habitacionBtn.getStyle().set("margin", "5px"); // Espacio entre botones
                    container.add(habitacionBtn );
                }
            }
            pisosLayout.add(new H3("Piso: "+piso));
            pisosLayout.add(container);
        }
        
        
        return pisosLayout;
    }
    
    public VerticalLayout formCheckIn(Habitacion habitacion, Button btnHabitacion , Dialog dialogForm ) {
        
        
        VerticalLayout container = new VerticalLayout();
        TextField nombreField = new TextField("Nombre");
        TextField identificacionField = new TextField("Identificación");
        nombreField.setRequired(true);
        identificacionField.setRequired(true);

        Button guardarButton = new Button("Checkin Usuario");

        guardarButton.addClickListener(event -> {
            String nombre = nombreField.getValue();
            String identificacion = identificacionField.getValue();
            
            if(nombre.equals("") || identificacion.equals("")){
                mostrarNotificacion("error", "Los datos del cliente son requeridos");
            }else{
               hotel.recepcion.checkIn(nombre, identificacion, habitacion);
               dialogForm.close();
               btnHabitacion.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);               
               mostrarNotificacion("correcto", "Habitación ocupada correctamente");
            }
            
        });

        container.add(nombreField);
        container.add(identificacionField);
        container.add(guardarButton);
        
        return container;
    }
    
    public void mostrarNotificacion(String tipo,String mensaje){
        Notification notification = new Notification();
        notification.addThemeVariants(tipo.equals("error") ? NotificationVariant.LUMO_ERROR : NotificationVariant.LUMO_SUCCESS );
        notification.add(new Text(mensaje));
        notification.setDuration(3000);
        notification.open();
    }
    
}
