package com.techlab.pedidos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contador = 1;
    private int id;
    private List<LineaPedido> lineas;

    public Pedido() {
        this.id = contador++;
        this.lineas = new ArrayList<>();
    }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public double calcularTotal() {
        return lineas.stream().mapToDouble(LineaPedido::calcularSubtotal).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pedido #" + id + "\n");
        for (LineaPedido lp : lineas) {
            sb.append(lp.getProducto().getNombre())
                    .append(" x ")
                    .append(lp.getCantidad())
                    .append(" = $")
                    .append(lp.calcularSubtotal())
                    .append("\n");
        }
        sb.append("Total: $").append(calcularTotal());
        return sb.toString();
    }
}
