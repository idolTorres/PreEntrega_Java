public class Producto {
    private static int contador = 1;
    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String categoria;

    public Producto(String nombre, double precio, String descripcion, String categoria) {
        this.id = contador++;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public boolean coincideId(int idBusqueda) {
        return this.id == idBusqueda;
    }
}
