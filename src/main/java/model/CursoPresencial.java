package model;

/**
 * Subclase concreta con atributo propio.
 */
public class CursoPresencial extends Curso {
    private String sala;

    public CursoPresencial(String codigo, String nombre,
                           double precioBase, String sala) {
        super(codigo, nombre, precioBase);
        this.sala = sala;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String v) {
        sala = v;
    }

    @Override
    public String getTipo() {
        return "CursoPresencial";
    }

    @Override
    public String getDetalle() {
        return sala;
    }

    /**
     * Regla de negocio: precio sin recargo.
     */
    @Override
    public double calcularValorFinal() {
        return getPrecioBase() * (1 + 0);
    }
}
