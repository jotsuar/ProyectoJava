
package com.mycompany.proyectofinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class Hotel {
    
    public Recepcion recepcion;
    
    public Hotel(){
        recepcion = new Recepcion();
    }
}


class Restaurante{
   
    public static Producto desayuno;
    public static Producto almuerzo;
    public static Producto cena;
    public static Producto servicio_habitacion;
   
    public Restaurante(){
        this.desayuno = new Producto("DESAYUNO", 15000, "RESTAURANTE");
        this.almuerzo = new Producto("ALMUERZO", 25000, "RESTAURANTE");
        this.cena = new Producto("CENA", 20000, "RESTAURANTE");
        this.servicio_habitacion = new Producto("SERVICIO_HABITACION", 5000, "RESTAURANTE");
    }
   
}

class Producto {
    private String nombre;
    private double precio;    
    private String tipo;


    public Producto(String nombre, double precio, String tipo) {
        this.nombre = nombre;
        this.precio = precio;        
        this.tipo = tipo;

    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
   
    public String getTipo() {
        return tipo;
    }
}

class Habitacion {
    private int numero;
    private int piso;
    private boolean ocupada;
    private String tipo;
    private String nombreCliente;
    private double precioNoche;
    public Minibar minibar;
    private String idCliente;
    private String detalles;
   
    public List<Producto> productos_usados;


    public Habitacion(int numero, int piso, String tipo, double precioNoche, String detalles, Minibar minibar) {
        this.productos_usados = new ArrayList<>();

        this.numero = numero;
        this.piso = piso;
        this.tipo = tipo;
        this.precioNoche = precioNoche;
        this.minibar = minibar;
        this.ocupada = false;
        this.detalles = detalles;
    }

    public void ocupar(String nombreCliente, String idCliente) {
        this.nombreCliente = nombreCliente;
        this.idCliente = idCliente;
        ocupada = true;
    }

    public void desocupar() {
        ocupada = false;
    }

    public boolean estaOcupada() {
        return ocupada;
    }
    
    public String getNombre(){
        return nombreCliente;
    }
    
    public String getDetalles(){
        return detalles;
    }
    
    public String getIdCliente(){
        return idCliente;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public double calcularCostoMinibar() {
        return minibar != null ? minibar.calcularCosto() : 0;
    }

    public int getNumero() {
        return numero;
    }

    public int getPiso() {
        return piso;
    }

    public String getTipo() {
        return tipo;
    }
    
    public double getTotalProductos(){
        double totalProductos = 0;
        for (Producto producto : productos_usados) {
            totalProductos += producto.getPrecio();
        }
        return totalProductos;
    }
    
    public boolean agregarProducto(String tipo,String Producto,Habitacion habitacion){
        
        boolean res = false;
        
        if (habitacion != null && habitacion.estaOcupada()) {
           
           if(tipo.equals("MINIBAR") ){
               
               for (int j = 0; j < habitacion.minibar.productos.size(); j++) {
                   if(habitacion.minibar.productos.get(j).getNombre().equals(Producto)){
                       habitacion.productos_usados.add(habitacion.minibar.productos.get(j));
                       habitacion.minibar.productos.remove(j);
                       res = true;
                       break;
                   }
               }
               
           }else{
                switch (Producto) {
                   case "DESAYUNO":
                       habitacion.productos_usados.add(Restaurante.desayuno);
                       break;
                   case "CENA":
                       habitacion.productos_usados.add(Restaurante.cena);
                       break;
                   case "ALMUERZO":
                       habitacion.productos_usados.add(Restaurante.almuerzo);
                       break;
                   case "SERVICIO_HABITACION":
                       habitacion.productos_usados.add(Restaurante.servicio_habitacion);
                       break;
               }
               res = true;
           }
           
        } else {
            System.out.println("La habitación no está ocupada.");
        }
        return res;
    }
}

class Minibar {
    private static final double PRECIO_LICOR = 25000;
    private static final double PRECIO_VINO = 50000;
    private static final double PRECIO_KIT_ASEO = 9000;
    private static final double PRECIO_AGUA = 3500;
    private static final double PRECIO_GASEOSA = 3000;
    private final static double PRECIO_BATA = 70000;
    
    private int licores;
    private int agua;
    private int kitsAseo;
    private int gaseosas;
    private int vino;
    private int batas;
   
    public final String LICORES = "LICORES";
    public final String AGUA = "AGUA";
    public final String KITASEO = "KITASEO";
    public final String GASEOSAS = "GASEOSAS";
    public final String VINO = "VINO";
    public final static String BATA = "BATA";
   
    public List<Producto> productos;


    public Minibar(int licores, int agua, int kitsAseo, int gaseosas, int vino, int batas) {
        this.licores = licores;
        this.agua = agua;
        this.kitsAseo = kitsAseo;
        this.gaseosas = gaseosas;
        this.vino = vino;
        this.batas = batas;
       
        this.productos = new ArrayList<>();

       
        for (int i = 0; i < licores; i++) {
            productos.add( new Producto(LICORES,PRECIO_LICOR,"MINIBAR") );
        }
       
        for (int i = 0; i < agua; i++) {
            productos.add( new Producto(AGUA,PRECIO_AGUA,"MINIBAR") );
        }
       
        for (int i = 0; i < kitsAseo; i++) {
            productos.add( new Producto(KITASEO,PRECIO_KIT_ASEO,"MINIBAR") );
        }
       
        for (int i = 0; i < kitsAseo; i++) {
            productos.add( new Producto(GASEOSAS,PRECIO_GASEOSA,"MINIBAR") );
        }
       
        for (int i = 0; i < vino; i++) {
            productos.add( new Producto(VINO,PRECIO_VINO,"MINIBAR") );
        }
        
        for (int i = 0; i < batas; i++) {
            productos.add( new Producto(BATA,PRECIO_BATA,"MINIBAR") );
        }
       
    }
   

    public double calcularCosto() {
        return licores * PRECIO_LICOR + agua * PRECIO_AGUA + kitsAseo * PRECIO_KIT_ASEO +
               gaseosas * PRECIO_GASEOSA + vino * PRECIO_VINO;
    }
}

class Factura {
    private String nombreHuesped;
    private String idHuesped;
    private Habitacion habitacion;
    private int diasEstadia;
    private double total;

    public Factura( Habitacion habitacion, int diasEstadia) {
        this.nombreHuesped = habitacion.getNombre();
        this.idHuesped = habitacion.getIdCliente();
        this.habitacion = habitacion;
        this.diasEstadia = diasEstadia;
        this.total = (diasEstadia * habitacion.getPrecioNoche()) ;
       
        for (Producto producto : habitacion.productos_usados) {
            total += producto.getPrecio();
        }
    }
    
    public double getTotal(){
        return total;
    }

    public void generarFactura() {
        System.out.println("Factura:");
        System.out.println("Huésped: " + nombreHuesped + " (ID: " + idHuesped + ")");
        System.out.println("Habitación No.: " + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo());
        System.out.println("Días de estadía: " + diasEstadia);
        System.out.println("Costo por noche: " + habitacion.getPrecioNoche());
       
        System.out.println("Resumen del carrito:");
        for (Producto producto : habitacion.productos_usados) {
            System.out.println("- " + producto.getNombre() + ": $" + producto.getPrecio());
        }
       
        System.out.println("Total: " + total);
    }
}

class Recepcion {
    public Map<Integer, Habitacion> habitaciones;
    public List<Habitacion> habitaciones_list;

    public Recepcion() {
        habitaciones = new HashMap<>();
        this.habitaciones_list = new ArrayList<>();
        inicializarHabitaciones();
    }

    private void inicializarHabitaciones() {
        // Crear 30 habitaciones sencillas (10 por piso en pisos 2 al 4)
        for (int piso = 2; piso <= 4; piso++) {
            for (int i = 1; i <= 10; i++) {
                int numero = piso * 100 + i;
                //habitaciones.put(numero, new Habitacion(numero, piso, "Sencilla", 200000, "Tiene 1 cama doble o dos sencillas", null));
                habitaciones_list.add(new Habitacion(numero, piso, "Sencilla", 200000, "Tiene 1 cama doble o dos sencillas", null));
            }
        }

        // Crear 10 habitaciones ejecutivas en piso 5
        for (int i = 1; i <= 10; i++) {
            int numero = 500 + i;
            Minibar minibarEjecutiva = new Minibar(4, 2, 1, 2, 0,0);
           
            for (Producto producto : minibarEjecutiva.productos) {
                System.out.println("- " + producto.getNombre() + ": $" + producto.getPrecio() + " indice "+ producto );
            }
            //habitaciones.put(numero, new Habitacion(numero, 5, "Ejecutiva", 350000, "Tiene 1 cama doble o dos sencillas", minibarEjecutiva));
            habitaciones_list.add(new Habitacion(numero, 5, "Ejecutiva", 350000, "Tiene 1 cama queen o dos semidobles, además tiene minibar compuesto por 4 botellas de licor, dos botellas de agua, 1 kit de aseo personal, 2 gaseosas.", minibarEjecutiva));
        }

        // Crear 5 suites en piso 6
        for (int i = 1; i <= 5; i++) {
            int numero = 600 + i;
            Minibar minibarSuite = new Minibar(4, 0, 3, 4, 1,2);
            //habitaciones.put(numero, new Habitacion(numero, 6, "Suite", 500000,"Tiene 1 cama King, o una cama queen y una semidoble. Además tiene un minibar compuesto por una botella de vino, 4 botellas de licor, 3 kit de aseo personal, 4 gaseosas. Tiene también un juego de 2 batas de baño. ", minibarSuite));
            habitaciones_list.add(new Habitacion(numero, 6, "Suite", 500000,"Tiene 1 cama King, o una cama queen y una semidoble. Además tiene un minibar compuesto por una botella de vino, 4 botellas de licor, 3 kit de aseo personal, 4 gaseosas. Tiene también un juego de 2 batas de baño. ", minibarSuite));
        }
    }
   
    public void checkIn(String nombreHuesped, String idHuesped, Habitacion habitacion) {
        if (habitacion != null && !habitacion.estaOcupada()) {
            habitacion.ocupar(nombreHuesped,idHuesped);
            System.out.println("Check-in exitoso para " + nombreHuesped + " en la habitación " + habitacion.getNumero());
        } else {
            System.out.println("La habitación no está disponible.");
        }
    }

    public void checkOut(Habitacion habitacion, int diasEstadia) {
        if (habitacion != null && habitacion.estaOcupada()) {
            Factura factura = new Factura(habitacion, diasEstadia);
            factura.generarFactura();
            habitacion.desocupar();
        } else {
            System.out.println("La habitación no está ocupada.");
        }
    }
}