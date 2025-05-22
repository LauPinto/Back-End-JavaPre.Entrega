package com.techlab.servicios;

import com.techlab.productos.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private List<Producto> productos = new ArrayList<>();

    public void agregarProductosDeEjemplo() {
        productos.add(new Producto("Espada de Dante", 200000, 2));
        productos.add(new Producto("Armadura de Brusef Amelion", 500000, 3));
        productos.add(new Producto("Potas de maná", 250, 10));

    }

    public void agregarProducto(String nombre, double precio, int stock) {
        productos.add(new Producto(nombre, precio, stock));
    }

    public List<Producto> listarProductos() {
        return productos;
    }

    public Producto buscarPorId(int id) {
        return productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public Producto buscarPorNombre(String nombre) {
        return productos.stream().filter(p -> p.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    public void eliminarProducto(int id) {
        productos.removeIf(p -> p.getId() == id);
    }
}
