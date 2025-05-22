package com.techlab;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.productos.Producto;
import com.techlab.servicios.PedidoService;
import com.techlab.servicios.ProductoService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductoService productoService = new ProductoService();
        PedidoService pedidoService = new PedidoService();


        // ⭐️ ¡Aquí invocás la magia para cargar productos!
        productoService.agregarProductosDeEjemplo();

        // Luego sigue el menú...

        int opcion;
        do {
            System.out.println("""
    ╔═════════════════════════════════════════════╗
    ║      ⚔️🛡️ MERCADO DE AVENTUREROS 🛡️⚔️       ║
    ║        Sistema de Gestión - TECHLAB         ║
    ╠═════════════════════════════════════════════╣
    ║  1) Añadir nuevo artículo al inventario     ║
    ║  2) Mostrar todos los artículos             ║
    ║  3) Buscar / Modificar un artículo          ║
    ║  4) Eliminar artículo del inventario        ║
    ║  5) Forjar un pedido de suministros         ║
    ║  6) Consultar pedidos realizados            ║
    ║  7) Abandonar el mercado                    ║
    ╚═════════════════════════════════════════════╝
        🧙‍♂️ Ingrese su elección, noble mercader:
    """);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("📜 Ingrese el nombre del artículo místico: ");
                    String nombre = scanner.nextLine();
                    System.out.print("\uD83D\uDCB0 Ingrese el valor en monedas de oro: ");
                    double precio = scanner.nextDouble();
                    System.out.print("\uD83C\uDF92 Ingrese la cantidad disponible en el almacén: ");
                    int stock = scanner.nextInt();
                    productoService.agregarProducto(nombre, precio, stock);
                    System.out.println("✅ El artículo ha sido registrado en el inventario sagrado.\n");
                }
                case 2 -> {
                    System.out.println("📦 Inventario de artículos disponibles en el mercado:");
                    productoService.listarProductos().forEach(System.out::println);
                    System.out.println();
                }
                case 3 -> {
                    System.out.println("""
                        🧭 Has accedido al Oráculo de los Objetos.
                         ¿Qué deseas realizar, noble comerciante?
                
                           1) 📜 Consultar un artículo sin modificarlo
                           2) 🛠️ Consultar y modificar el artículo
                           Ingrese su elección:
                          """);

                    int opcionConsulta = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer

                    int id;
                    while (true) {
                        System.out.print("🔎 Indica el ID del artículo que buscas en el grimorio: ");
                        try {
                            id = scanner.nextInt();
                            scanner.nextLine(); // limpiar buffer
                            break; // Salir del bucle si es válido
                        } catch (InputMismatchException e) {
                            System.out.println("⚠️ ¡Ese no es un número válido! Intenta nuevamente.");
                            scanner.nextLine(); // limpiar entrada incorrecta
                        }
                    }

                    Producto p = productoService.buscarPorId(id);

                    if (p != null) {
                        System.out.println("📖 He aquí los detalles del objeto encantado:\n" + p);

                        if (opcionConsulta == 2) {
                            System.out.print("💰 Nuevo precio: ");
                            double nuevoPrecio = scanner.nextDouble();

                            System.out.print("🎒 Nuevo stock: ");
                            int nuevoStock = scanner.nextInt();
                            scanner.nextLine();

                            p.setPrecio(nuevoPrecio);
                            p.setStock(nuevoStock);

                            System.out.println("✅ ¡Artículo actualizado con éxito!\n");
                        }
                    } else {
                        System.out.println("⚠️ No se encontró ningún artefacto con ese ID.\n");
                    }
                }
                case 4 -> {
                    System.out.print("\uD83D\uDDE1\uFE0F Ingrese el ID del artículo que desea eliminar del mercado: ");
                    int idEliminar = scanner.nextInt();
                    productoService.eliminarProducto(idEliminar);
                    System.out.println("⚰️ El artículo ha sido removido del plano terrenal.\n");
                }
                case 5 -> {
                    List<Producto> seleccionados = new ArrayList<>();
                    List<Integer> cantidades = new ArrayList<>();
                    String continuar;

                    do {
                        int id = 0;
                        boolean idValido = false;

                        while (!idValido) {
                            try {
                                System.out.print("🎯 Ingrese el ID del artículo a añadir al pedido: ");
                                id = scanner.nextInt();
                                scanner.nextLine(); // Limpiar buffer
                                idValido = true;
                            } catch (InputMismatchException e) {
                                System.out.println("⚠️ ¡Ese no es un número válido! Por favor, introduce un número de ID.");
                                scanner.nextLine(); // Limpiar entrada inválida
                            }
                        }

                        Producto p = productoService.buscarPorId(id);

                        if (p != null) {
                            int cantidad = 0;
                            boolean cantidadValida = false;

                            while (!cantidadValida) {
                                try {
                                    System.out.print("🔢 Ingrese la cantidad deseada: ");
                                    cantidad = scanner.nextInt();
                                    scanner.nextLine();
                                    cantidadValida = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("⚠️ Valor inválido. La cantidad debe ser un número entero.");
                                    scanner.nextLine();
                                }
                            }

                            seleccionados.add(p);
                            cantidades.add(cantidad);
                        } else {
                            System.out.println("⚠️ No se ha hallado tal artículo en el reino. Intenta con otro ID.");
                        }

                        System.out.print("➕ ¿Desea añadir otro artículo al pedido? (s/n): ");
                        continuar = scanner.nextLine().trim().toLowerCase();

                        if (!continuar.equals("s") && !continuar.equals("n")) {
                            do {
                                System.out.print("➕ ¿Desea añadir otro artículo al pedido? (s/n): ");
                                continuar = scanner.nextLine().trim().toLowerCase();

                                if (!continuar.equals("s") && !continuar.equals("n")) {
                                    System.out.println("⚠️ Comando no reconocido por los sabios del mercado. Solo se aceptan 's' o 'n'.");
                                }
                            } while (!continuar.equals("s") && !continuar.equals("n"));
                        }

                    } while (continuar.equalsIgnoreCase("s"));

                    try {
                        var nuevoPedido = pedidoService.crearPedido(seleccionados, cantidades);
                        System.out.println("🧾 El pedido ha sido forjado con éxito:");
                        System.out.println(nuevoPedido + "\n");
                    } catch (StockInsuficienteException e) {
                        System.out.println("🚫 El pedido no pudo completarse: " + e.getMessage() + "\n");
                    }
                }
                case 6 -> {
                    System.out.println("📜 Registro de pedidos realizados en el gremio:");
                    pedidoService.listarPedidos().forEach(pedido -> {
                        System.out.println(pedido);
                        System.out.println("─────────────────────────────────────────────");
                    });
                    System.out.println();
                    }
                case 7 -> System.out.println("🕯️ Cerrando el mercado... Que los dioses del código te acompañen.");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 7);
    }

    }


