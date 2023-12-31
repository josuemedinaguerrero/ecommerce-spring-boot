package com.example.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String nombre;
   private String descripcion;
   private String imagen;
   private double precio;
   private int cantidad;

   @ManyToOne
   private Usuario usuario;

   @Override
   public String toString() {
      return "Producto{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", descripcion='" + descripcion + '\'' +
            ", imagen='" + imagen + '\'' +
            ", precio=" + precio +
            ", cantidad=" + cantidad +
            ", usuario=" + usuario +
            '}';
   }

}
