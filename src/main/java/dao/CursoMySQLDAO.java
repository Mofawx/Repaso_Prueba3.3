package dao;

import conexion.ConexionBD;
import model.Curso;
import model.CursoOnline;
import model.CursoPresencial;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CursoMySQLDAO implements CursoDAO{

    /// control +  i
    @Override
    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        try{
            Connection conexion = ConexionBD.getConnection();
            String query = "SELECT " +
                    "    m.codigo, " +
                    "    m.nombre, " +
                    "    m.precio_base, " +
                    "    m.detalle AS sala, " +
                    "    t.descripcion AS tipo " +
                    "FROM cursos m " +
                    "JOIN tipos_curso t " +
                    "    ON m.id_tipo = t.id_tipo " +
                    "ORDER BY m.codigo";
            PreparedStatement bus = conexion.prepareStatement(query);
            ResultSet rs = bus.executeQuery();
            while(rs.next()){
                String tipo = rs.getString("tipo");
                // como no sabemos el tipo de objetos declaramos un objeto generico (clase padre)
                Curso m;
                if ("CursoPresencial".equals(tipo)) {
                    m = new CursoPresencial(rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getDouble("precio_base"),
                            rs.getString("sala"));
                } else if ("CursoOnline".equals(tipo)) {
                    m = new CursoOnline(rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getDouble("precio_base"),
                            rs.getString("sala"));
                } else {
                    throw new PersistenciaException("Tipo de curso desconocido en la base de datos.");
                }
                // agregamos el objeto que leimos de la fila al ArrayList que posteriormente es retornado por este método
                lista.add(m);
            }

        }catch (Exception e){
            System.out.println("Error al listar "+e.getMessage());
        }
        return lista;
    }

    private int tipoId(Curso c){
        if (c instanceof CursoPresencial) return 1;
        if (c instanceof CursoOnline) return 2;

        System.out.println("NO esta definido el model");
        return 0;
    }


    @Override
    public void guardar(Curso item) {
        try{
            // Abrir conexion con BD
            Connection conexion = ConexionBD.getConnection();
            /// crea la query
            String query = "INSERT INTO cursos(codigo,nombre," +
                    "precio_base,detalle,id_tipo) VALUES(?,?,?,?,?)";
            /// Prepara el estamento
            PreparedStatement ins = conexion.prepareStatement(query);
           /// reemplaza los marcadores ---> ?
            ins.setString(1, item.getCodigo());
            ins.setString(2, item.getNombre());
            ins.setDouble(3, item.getPrecioBase());
            ins.setString(4, item.getDetalle());
            ins.setInt(5, tipoId(item));
           /// ejecuta la query!!!!
            ins.executeUpdate();
        } catch (PersistenciaException p){
            System.out.println("Error al sqL en al agregar "+p.getMessage());
        } catch (Exception e) {
            System.out.println("Error al agregar "+e.getMessage());
        }
    }

    @Override
    public void actualizar(Curso item) {

    }

    @Override
    public void eliminar(String codigo) {
        try{
            Connection conexion = ConexionBD.getConnection();
            String query = "delete from cursos where codigo=?";
            PreparedStatement del = conexion.prepareStatement(query);
            del.setString(1,codigo);
            del.executeUpdate();
        } catch (PersistenciaException p){
            System.out.println("Error al sqL al eliminar "+p.getMessage());
        } catch (Exception e) {
            System.out.println("Error al eliminar "+e.getMessage());
        }

    }
}
