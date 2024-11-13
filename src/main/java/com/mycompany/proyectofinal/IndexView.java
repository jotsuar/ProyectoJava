
package com.mycompany.proyectofinal;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
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
            container.getStyle().set("flex-wrap", "wrap"); // Permite que los botones se dispongan en múltiples líneas horizontales

            
            for (int i = 0; i < hotel.recepcion.habitaciones_list.size(); i++) {
                if(piso.equals(hotel.recepcion.habitaciones_list.get(i).getPiso()) ){
                    Habitacion habitacion = hotel.recepcion.habitaciones_list.get(i);
                    Button habitacionBtn  = new Button("Habitacion: "+habitacion.getNumero());
                    if(habitacion.estaOcupada()){
                        habitacionBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
                    }else{
                        habitacionBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    }
                    habitacionBtn.addClickListener( clicEvent -> {
                         
                        if(habitacion.estaOcupada()){
                            Notification notification = new Notification();
                            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            notification.add(new Text("Ya se encuentra ocupada la habitacion"));
                            notification.open();
                        }else{
                            hotel.recepcion.checkIn("Jhonatan Suarez", "2991991", habitacion);
                            habitacionBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);  
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
    
}
