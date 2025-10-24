import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Producto> productosDB = obtenerInstrumentosMusicales();
    private static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🎵 Bienvenido a la tienda de instrumentos musicales 🎸🥁🎹");
        menu:
        while (true) {
            System.out.println("""
                    ==========================================
                    Ingrese el número equivalente a la opción:
                    0 - Finaliza el programa
                    1 - Crear un nuevo instrumento
                    2 - Listar instrumentos
                    3 - Buscar por nombre
                    4 - Editar instrumento
                    5 - Borrar instrumento
                    6 - Filtrar por precio máximo
                    ==========================================
                    """);
            int opcionUsuario = entrada.nextInt();
            entrada.nextLine(); // limpiar buffer

            switch (opcionUsuario) {
                case 1 -> crearProducto(productosDB);
                case 2 -> listarProductos(productosDB);
                case 3 -> buscarProductoPorNombre(productosDB);
                case 4 -> editarProducto(productosDB);
                case 5 -> borrarProducto(productosDB);
                case 6 -> filtroPorPrecio(productosDB);
                case 0 -> {
                    System.out.println("🎶 ¡Gracias por visitar nuestra tienda!");
                    break menu;
                }
                default -> System.out.println("⚠️ Opción incorrecta, intente nuevamente.");
            }
        }
    }

    // CREATE
    public static void crearProducto(ArrayList<Producto> productos) {
        System.out.println("🆕 Creando nuevo instrumento...");

        System.out.print("Ingrese el nombre: ");
        String nombre = entrada.nextLine();

        System.out.print("Ingrese el precio: ");
        double precio = entrada.nextDouble();
        entrada.nextLine();

        System.out.print("Ingrese una breve descripción: ");
        String descripcion = entrada.nextLine();

        System.out.print("Ingrese la categoría (Cuerdas, Percusión, Viento, etc.): ");
        String categoria = entrada.nextLine();

        productos.add(new Producto(nombre, precio, descripcion, categoria));
        System.out.println("✅ Instrumento agregado correctamente.");
        pausa();
    }

    // READ
    public static void listarProductos(ArrayList<Producto> productos) {
        System.out.println("==============================================================================================");
        System.out.println("                         🎸 LISTA DE INSTRUMENTOS MUSICALES DISPONIBLES 🎷                    ");
        System.out.println("==============================================================================================");

        if (productos.isEmpty()) {
            System.out.println("⚠️ No hay instrumentos para mostrar.");
        } else {
            System.out.printf("| %-3s | %-30s | %-10s | %-15s | %-25s |%n",
                    "ID", "Nombre", "Precio", "Categoría", "Descripción");
            System.out.println("----------------------------------------------------------------------------------------------");

            for (Producto producto : productos) {
                System.out.printf("| %3d | %-30s | $%9.2f | %-15s | %-25s |%n",
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getCategoria(),
                        acortarDescripcion(producto.getDescripcion(), 25));
            }
        }

        System.out.println("==============================================================================================");
        pausa();
    }

    private static String acortarDescripcion(String descripcion, int maxLength) {
        if (descripcion.length() <= maxLength) return descripcion;
        return descripcion.substring(0, maxLength - 3) + "...";
    }

    // SEARCH
    public static void buscarProductoPorNombre(ArrayList<Producto> productos) {
        System.out.print("🔍 Ingrese parte del nombre del instrumento: ");
        String busqueda = entrada.nextLine();
        ArrayList<Producto> encontrados = new ArrayList<>();

        for (Producto producto : productos) {
            if (estaIncluido(producto.getNombre(), busqueda)) {
                encontrados.add(producto);
            }
        }

        listarProductos(encontrados);
    }

    // UPDATE
    public static void editarProducto(List<Producto> productos) {
        Producto producto = obtenerProductoPorId(productos);
        if (producto == null) {
            System.out.println("⚠️ No se encontró el instrumento.");
            pausa();
            return;
        }

        System.out.println("Instrumento seleccionado: " + producto.getNombre());
        System.out.print("Ingrese el nuevo nombre: ");
        String nuevoNombre = entrada.nextLine();

        producto.setNombre(nuevoNombre);
        System.out.printf("✅ El nombre cambió de '%s' a '%s'%n", producto.getNombre(), nuevoNombre);
        pausa();
    }

    // DELETE
    public static void borrarProducto(List<Producto> productos) {
        Producto producto = obtenerProductoPorId(productos);
        if (producto == null) {
            System.out.println("⚠️ No se pudo borrar el instrumento.");
            pausa();
            return;
        }

        productos.remove(producto);
        System.out.println("🗑️ Instrumento eliminado correctamente.");
        pausa();
    }

    // FILTER
    public static void filtroPorPrecio(List<Producto> productos) {
        System.out.print("Ingrese el precio máximo: ");
        double precioFiltro = entrada.nextDouble();
        entrada.nextLine();

        ArrayList<Producto> filtrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getPrecio() <= precioFiltro) {
                filtrados.add(producto);
            }
        }

        listarProductos(filtrados);
    }

    // UTILIDADES
    public static Producto obtenerProductoPorId(List<Producto> productos) {
        System.out.print("Ingrese el ID del instrumento: ");
        int idBusqueda = entrada.nextInt();
        entrada.nextLine();

        for (Producto producto : productos) {
            if (producto.coincideId(idBusqueda)) return producto;
        }

        System.out.println("No se encontró ningún instrumento con ID " + idBusqueda);
        return null;
    }

    public static boolean estaIncluido(String nombreCompleto, String nombreParcial) {
        return formatoBusqueda(nombreCompleto).contains(formatoBusqueda(nombreParcial));
    }

    public static String formatoBusqueda(String texto) {
        return texto.trim().toLowerCase();
    }

    public static void pausa() {
        System.out.println("\nPulse ENTER para continuar...");
        entrada = new Scanner(System.in);
        entrada.nextLine();
        for (int i = 0; i < 15; ++i) System.out.println();
    }

    // BASE DE DATOS SIMULADA
    public static ArrayList<Producto> obtenerInstrumentosMusicales() {
        ArrayList<Producto> productos = new ArrayList<>();

        productos.add(new Producto("Guitarra Fender Stratocaster", 1899.99,
                "Guitarra eléctrica de cuerpo sólido y tono brillante.", "Cuerdas"));
        productos.add(new Producto("Bajo Yamaha TRBX304", 599.99,
                "Bajo eléctrico versátil de 4 cuerdas.", "Cuerdas"));
        productos.add(new Producto("Teclado Roland FP-30X", 799.99,
                "Piano digital con 88 teclas sensibles y conectividad Bluetooth.", "Teclados"));
        productos.add(new Producto("Batería Pearl Export EXX", 999.99,
                "Set de batería acústica con hardware completo.", "Percusión"));
        productos.add(new Producto("Micrófono Shure SM58", 129.99,
                "Micrófono dinámico ideal para voces en vivo.", "Audio"));
        productos.add(new Producto("Violín Stentor Student II", 249.99,
                "Violín clásico para estudiantes, incluye arco y estuche.", "Cuerdas"));
        productos.add(new Producto("Saxo Alto Yamaha YAS-280", 1399.99,
                "Saxofón de estudio con sonido equilibrado y ergonómico.", "Viento"));
        productos.add(new Producto("Amplificador Marshall DSL40CR", 849.99,
                "Amplificador de válvulas de 40W con gran presencia.", "Amplificación"));
        productos.add(new Producto("Pedal Boss DS-1 Distortion", 99.99,
                "Pedal de distorsión clásico para guitarra eléctrica.", "Efectos"));
        productos.add(new Producto("Cajón Flamenco Schlagwerk CP404", 189.99,
                "Cajón profesional de madera con gran respuesta sonora.", "Percusión"));

        return productos;
    }
}
