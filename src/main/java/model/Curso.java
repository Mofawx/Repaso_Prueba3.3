package model;

/**
 * Clase abstracta: atributos comunes y polimorfismo.
 */
public abstract class Curso implements Inscribible {
    private String codigo;
    private String nombre;
    private double precioBase;

    protected Curso(String codigo, String nombre, double precioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String v) {
        codigo = v;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String v) {
        nombre = v;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double v) {
        precioBase = v;
    }

    public abstract String getTipo();

    public abstract String getDetalle();
}
