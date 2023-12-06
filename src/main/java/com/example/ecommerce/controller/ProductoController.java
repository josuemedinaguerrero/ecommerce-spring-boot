package com.example.ecommerce.controller;

import com.example.ecommerce.model.Producto;
import com.example.ecommerce.model.Usuario;
import com.example.ecommerce.service.ProductoServiceImpl;
import com.example.ecommerce.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

   private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

   @Autowired
   private ProductoServiceImpl productoService;

   @Autowired
   private UploadFileService uploadFileService;

   @GetMapping
   public String show(Model model) {
      model.addAttribute("productos", productoService.findAll());
      return "productos/show";
   }

   @GetMapping("/create")
   public String create() {
      return "productos/create";
   }

   @PostMapping("/save")
   public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
      LOGGER.info("Este es el objeto producto {}", producto);

      Usuario usuario = Usuario.builder()
            .id(1)
            .nombre("")
            .username("")
            .email("")
            .direccion("")
            .telefono("")
            .tipo("")
            .password("")
            .build();

      producto.setUsuario(usuario);

      if (producto.getId() == null) { // Se crea el producto
         String nombreImagen = uploadFileService.saveImage(file);
         producto.setImagen(nombreImagen);
      } else {

      }

      productoService.save(producto);
      return "redirect:/productos";
   }

   @GetMapping("/edit/{id}")
   public String edit(@PathVariable Integer id, Model model) {
      Producto producto;
      Optional<Producto> optionalProducto = productoService.get(id);
      producto = optionalProducto.get();

      LOGGER.info("Producto buscado: {}", producto);
      model.addAttribute("producto", producto);

      return "productos/edit";
   }

   @PostMapping("/update")
   public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
      if (file.isEmpty()) {
         Producto p;
         p = productoService.get(producto.getId()).get();
         producto.setImagen(p.getImagen());
      } else {
         Producto p = productoService.get(producto.getId()).get();

         if (!p.getImagen().equals("default.jpg")) {
            uploadFileService.deleteImage(p.getImagen());
         }

         String nombreImagen = uploadFileService.saveImage(file);
         producto.setImagen(nombreImagen);
      }

      productoService.update(producto);
      return "redirect:/productos";
   }

   @GetMapping("/delete/{id}")
   public String delete(@PathVariable Integer id) {
      Producto p = productoService.get(id).get();

      if (!p.getImagen().equals("default.jpg")) {
         uploadFileService.deleteImage(p.getImagen());
      }

      productoService.delete(id);
      return "redirect:/productos";
   }

}
