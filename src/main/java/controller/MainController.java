package controller;

import dao.*;
import model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;

/**
 * Controlador: eventos de la vista; nunca escribe SQL ni JSON.
 */
public class MainController {
    @FXML
    private TextField txtCodigo, txtNombre, txtPrecio, txtDetalle;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private TableView<Curso> tabla;
    @FXML
    private TableColumn<Curso, String> colCodigo, colNombre, colTipo, colDetalle;
    @FXML
    private TableColumn<Curso, Number> colPrecio, colFinal;
    @FXML
    private Label lblEstado, lblDetalle;
    private final CursoDAO dao = new CursoMySQLDAO();

    @FXML
    public void initialize() {
        cmbTipo.getItems().addAll("CursoPresencial", "CursoOnline");
        cmbTipo.getSelectionModel().selectFirst();
        cmbTipo.setOnAction(e -> actualizarEtiqueta());
        actualizarEtiqueta();
        colCodigo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCodigo()));
        colNombre.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));
        colTipo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getTipo()));
        colDetalle.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDetalle()));
        colPrecio.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().getPrecioBase()));
        colFinal.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().calcularValorFinal()));
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, ant, act) -> {
            if (act != null) mostrar(act);
        });
        cargar();
    }

    private void actualizarEtiqueta() {
        lblDetalle.setText("Sala".equals(cmbTipo.getValue()) ? "Sala" : "Plataforma");
    }

    private Curso leerFormulario() {
        String codigo = txtCodigo.getText().trim(), nombre = txtNombre.getText().trim(), detalle = txtDetalle.getText().trim();
        if (codigo.isEmpty() || nombre.isEmpty() || detalle.isEmpty())
            throw new IllegalArgumentException("Complete los campos");
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        if (precio < 0) throw new IllegalArgumentException("Precio negativo");
        return "CursoPresencial".equals(cmbTipo.getValue()) ? new CursoPresencial(codigo, nombre, precio, detalle) : new CursoOnline(codigo, nombre, precio, detalle);
    }

    private void mostrar(Curso v) {
        txtCodigo.setText(v.getCodigo());
        txtNombre.setText(v.getNombre());
        txtPrecio.setText(String.valueOf(v.getPrecioBase()));
        txtDetalle.setText(v.getDetalle());
        cmbTipo.setValue(v.getTipo());
    }

    @FXML
    private void guardar() {
        try {
            dao.guardar(leerFormulario());
            cargar();
            lblEstado.setText("Registro agregado");
        } catch (Exception e) {
            error(e);
        }
    }

    @FXML
    private void actualizar() {
        try {
            dao.actualizar(leerFormulario());
            cargar();
            lblEstado.setText("Registro actualizado");
        } catch (Exception e) {
            error(e);
        }
    }

    @FXML
    private void eliminar() {
        try {
            Curso v = tabla.getSelectionModel().getSelectedItem();
            if (v == null) throw new IllegalArgumentException("Seleccione un registro");
            dao.eliminar(v.getCodigo());
            limpiar();
            cargar();
            lblEstado.setText("Registro eliminado");
        } catch (Exception e) {
            error(e);
        }
    }

    @FXML
    private void limpiar() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtDetalle.clear();
        cmbTipo.getSelectionModel().selectFirst();
        tabla.getSelectionModel().clearSelection();
    }

    @FXML
    private void cargar() {
        try {
            tabla.setItems(FXCollections.observableArrayList(dao.listar()));
            lblEstado.setText("Persistencia: ");
        } catch (Exception e) {
            error(e);
        }
    }

    private void error(Exception e) {
        lblEstado.setText("Error: " + e.getMessage());
        new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    }
}
