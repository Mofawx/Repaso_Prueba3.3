package dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

import model.*;

/**
 * Una sola colección JSON; discriminador tipo para reconstruir las subclases.
 */
public class CursoArchivoDAO implements CursoDAO {
    private final Path ruta = Paths.get("datos", "cursos_capacitacion.json");

    private static class Registro {
        String codigo, nombre, tipo, detalle;
        double precioBase;
    }

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<Registro> leer() {
        if (!Files.exists(ruta)) return new ArrayList<>();
        try (Reader r = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            Type tipo = new TypeToken<List<Registro>>() {
            }.getType();
            List<Registro> datos = gson.fromJson(r, tipo);
            return datos == null ? new ArrayList<>() : datos;
        } catch (IOException e) {
            throw new PersistenciaException("No se pudo leer JSON", e);
        }
    }

    private void escribir(List<Registro> datos) {
        try {
            Files.createDirectories(ruta.getParent());
            try (Writer w = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
                gson.toJson(datos, w);
            }
        } catch (IOException e) {
            throw new PersistenciaException("No se pudo guardar JSON", e);
        }
    }

    private Registro convertir(Curso v) {
        Registro r = new Registro();
        r.codigo = v.getCodigo();
        r.nombre = v.getNombre();
        r.precioBase = v.getPrecioBase();
        r.tipo = v.getTipo();
        r.detalle = v.getDetalle();
        return r;
    }

    private Curso reconstruir(Registro r) {
        if ("CursoPresencial".equals(r.tipo)) return new CursoPresencial(r.codigo, r.nombre, r.precioBase, r.detalle);
        if ("CursoOnline".equals(r.tipo)) return new CursoOnline(r.codigo, r.nombre, r.precioBase, r.detalle);
        throw new PersistenciaException("Tipo desconocido: " + r.tipo);
    }

    @Override
    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        for (Registro r : leer()) lista.add(reconstruir(r));
        return lista;
    }

    @Override
    public void guardar(Curso item) {
        List<Registro> datos = leer();
        for (Registro r : datos)
            if (r.codigo.equals(item.getCodigo())) throw new PersistenciaException("Código duplicado");
        datos.add(convertir(item));
        escribir(datos);
    }

    @Override
    public void actualizar(Curso item) {
        List<Registro> datos = leer();
        boolean encontrado = false;
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).codigo.equals(item.getCodigo())) {
                datos.set(i, convertir(item));
                encontrado = true;
                break;
            }
        }
        if (!encontrado) throw new PersistenciaException("Código inexistente");
        escribir(datos);
    }

    @Override
    public void eliminar(String codigo) {
        List<Registro> datos = leer();
        boolean quitado = false;
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).codigo.equals(codigo)) {
                datos.remove(i);
                quitado = true;
                break;
            }
        }
        if (!quitado) throw new PersistenciaException("Código inexistente");
        escribir(datos);
    }
}
