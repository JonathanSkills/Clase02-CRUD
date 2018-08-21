package dto;

public class Producto {
    private String cod;
    private String nombre;
    private double precio;
    private int cant;

    public Producto() {
    }

    public Producto(String cod, String nombre, double precio, int cant) {
        this.cod = cod;
        this.nombre = nombre;
        this.precio = precio;
        this.cant = cant;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
