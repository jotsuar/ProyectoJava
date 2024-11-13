/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication


public class ProyectoFinal {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {       
        SpringApplication.run(ProyectoFinal.class, args);
    }

}
