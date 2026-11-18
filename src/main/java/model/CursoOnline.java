package model;

/**
 * Subclase concreta con atributo propio.
 */
public class CursoOnline extends Curso {
    private String plataforma;

    public CursoOnline(String codigo, String nombre,
                       double precioBase, String plataforma) {
        super(codigo, nombre, precioBase);
        this.plataforma = plataforma;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String v) {
        plataforma = v;
    }

    @Override
    public String getTipo() {
        return "CursoOnline";
    }

    @Override
    public String getDetalle() {
        return plataforma;
    }

    /**
     * Regla de negocio: recargo de 10 %.
     */
    @Override
    public double calcularValorFinal() {
        return getPrecioBase() * (1 + 0.10);
    }
}
