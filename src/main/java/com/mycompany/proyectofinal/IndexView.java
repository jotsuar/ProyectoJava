
package com.mycompany.proyectofinal;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Arrays;

@Route("")
public class IndexView extends VerticalLayout{
    
    public Hotel hotel;
    
    public IndexView(){
           
        hotel = new Hotel();
        
        var titulo = new H1("Hotel universitario");
        add(titulo);
        
        VerticalLayout layout = new VerticalLayout();
        List<Integer> primeros_pisos = Arrays.asList(2, 3, 4,5,6);
        layout.add(pintarHabitacionesPorPiso(primeros_pisos));
       
            Scroller scroller = new Scroller(layout);
            //scroller.setMaxWidth(5000, Unit.PIXELS);
            //scroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);

        
        add(scroller);
        
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
                            mostrarNotificacion("error", "Ya se encuentra ocupada la habitación");
                        }else{
                            Dialog dialog = new Dialog();
                            dialog.setHeaderTitle("Habitación "+habitacion.getNumero()+" "+habitacion.getTipo()+" Disponible");

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
