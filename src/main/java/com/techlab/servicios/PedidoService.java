package com.techlab.servicios;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Producto;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private List<Pedido> pedidos = new ArrayList<>();

    public Pedido crearPedido(List<Producto> productosSeleccionados, List<Integer> cantidades) throws StockInsuficienteException {
        Pedido pedido = new Pedido();

        for (int i = 0; i < productosSeleccionados.size(); i++) {
            Producto p = productosSeleccionados.get(i);
            int cantidad = cantidades.get(i);

            if (cantidad > p.getStock()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto: " + p.getNombre());
            }

            p.setStock(p.getStock() - cantidad);
            pedido.agregarLinea(new LineaPedido(p, cantidad));
        }

        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}
