package app;

import java.sql.*;

public class VentaProducto {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmtActualizarInventario = null;
        PreparedStatement pstmtRegistrarVenta = null;

        try {
            // Obtener conexión usando la clase Database
            conn = Database.getConnection();
            
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }
            
            // Desactivar el modo de autocommit
            conn.setAutoCommit(false);

            // Preparar las sentencias SQL
            String sqlActualizarInventario = "UPDATE productos SET cantidad = cantidad - ? WHERE id = ? AND cantidad >= ?";
            String sqlRegistrarVenta = "INSERT INTO ventas (producto_id, cantidad, total) VALUES (?, ?, ?)";

            pstmtActualizarInventario = conn.prepareStatement(sqlActualizarInventario);
            pstmtRegistrarVenta = conn.prepareStatement(sqlRegistrarVenta);

            // Definir los parámetros de la venta
            int productoId = 1;
            int cantidadVendida = 5;
            double precioUnitario = 10.00;

            // Ejecutar la transacción
            // 1. Actualizar inventario
            pstmtActualizarInventario.setInt(1, cantidadVendida);
            pstmtActualizarInventario.setInt(2, productoId);
            pstmtActualizarInventario.setInt(3, cantidadVendida);
            int filasActualizadas = pstmtActualizarInventario.executeUpdate();

            if (filasActualizadas == 0) {
                throw new SQLException("No hay suficiente inventario para realizar la venta.");
            }

            // 2. Registrar la venta
            pstmtRegistrarVenta.setInt(1, productoId);
            pstmtRegistrarVenta.setInt(2, cantidadVendida);
            pstmtRegistrarVenta.setDouble(3, cantidadVendida * precioUnitario);
            pstmtRegistrarVenta.executeUpdate();

            // Si llegamos aquí sin excepciones, hacemos commit
            conn.commit();
            System.out.println("Venta realizada con éxito.");

        } catch (SQLException e) {
            // Si ocurre un error, hacemos rollback
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Se ha realizado un rollback de la transacción.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (pstmtActualizarInventario != null) pstmtActualizarInventario.close();
                if (pstmtRegistrarVenta != null) pstmtRegistrarVenta.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
