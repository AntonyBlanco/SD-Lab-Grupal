package base_de_datos_jdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ConsultaAsignaciones extends JFrame {
    private JComboBox<Integer> cmbIDIng;
    private JComboBox<Integer> cmbIDProy;
    private JTable tablaAsignaciones;
    private DefaultTableModel modelo;
    private JLabel lblResultado;
    private JFrame mainApp; // Referencia a la ventana principal

    public ConsultaAsignaciones(JFrame mainApp) {
        super("Gestión de Asignaciones");
        this.mainApp = mainApp;

        // Componentes de la interfaz
        JLabel lblIDIng = new JLabel("ID Ingeniero:");
        cmbIDIng = new JComboBox<>();
        JLabel lblIDProy = new JLabel("ID Proyecto:");
        cmbIDProy = new JComboBox<>();

        JButton btnCrear = new JButton("Asignar Ingeniero a Proyecto");
        JButton btnModificar = new JButton("Modificar Asignación");
        JButton btnEliminar = new JButton("Eliminar Asignación");
        JButton btnAtras = new JButton("Atrás");

        // Acción del botón Crear
        btnCrear.addActionListener(e -> crearAsignacion());

        // Acción del botón Modificar
        btnModificar.addActionListener(e -> modificarAsignacion());

        // Acción del botón Eliminar
        btnEliminar.addActionListener(e -> eliminarAsignacion());

        // Acción del botón Atrás
        btnAtras.addActionListener(e -> {
            // Cerrar la ventana de gestión de asignaciones y mostrar la ventana de bienvenida
            this.setVisible(false);
            mainApp.setVisible(true);
        });

        // Definición de columnas para la tabla
        String[] columnas = {"ID Proyecto", "ID Ingeniero"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaAsignaciones = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaAsignaciones);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Acción al seleccionar una asignación en la tabla
        tablaAsignaciones.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tablaAsignaciones.getSelectedRow() != -1) {
                llenarCamposDesdeTabla(tablaAsignaciones.getSelectedRow());
            }
        });

        // Etiqueta para mostrar el número de resultados encontrados
        lblResultado = new JLabel("Asignaciones almacenadas:");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.add(lblResultado, BorderLayout.NORTH);
        panelResultados.add(scrollPane, BorderLayout.CENTER);

        // Panel superior con los componentes de entrada (etiqueta, campo de texto y botones)
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Añadir componentes uno sobre otro
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(lblIDIng, gbc);
        gbc.gridx = 1;
        panelSuperior.add(cmbIDIng, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperior.add(lblIDProy, gbc);
        gbc.gridx = 1;
        panelSuperior.add(cmbIDProy, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelSuperior.add(btnCrear, gbc);
        gbc.gridx = 1;
        panelSuperior.add(btnModificar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelSuperior.add(btnEliminar, gbc);
        gbc.gridx = 1;
        panelSuperior.add(btnAtras, gbc);

        // Panel principal que contiene el panel superior y el panel de resultados
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelResultados, BorderLayout.CENTER);

        // Configuración de la ventana principal
        getContentPane().add(panel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Listar asignaciones al inicio
        listarAsignaciones();
        cargarIngenierosYProyectos();
    }

    // Método para listar todas las asignaciones en la tabla
    private void listarAsignaciones() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String consulta = "SELECT * FROM asignacion";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            modelo.setRowCount(0); // Limpia la tabla antes de llenarla
            int rowCount = 0;
            while (resultado.next()) {
                Object[] fila = {
                    resultado.getInt("IDProy"),
                    resultado.getInt("IDIng")
                };
                modelo.addRow(fila);
                rowCount++;
            }
            lblResultado.setText("Asignaciones almacenadas: " + rowCount);
            conexion.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar las asignaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para llenar los campos de selección desde la tabla
    private void llenarCamposDesdeTabla(int fila) {
        cmbIDProy.setSelectedItem(modelo.getValueAt(fila, 0));
        cmbIDIng.setSelectedItem(modelo.getValueAt(fila, 1));
    }

    // Método para cargar los IDs de ingenieros y proyectos en los combo boxes
    private void cargarIngenierosYProyectos() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            
            // Cargar IDs de ingenieros
            String consultaIngenieros = "SELECT IDIng FROM ingeniero";
            PreparedStatement statementIngenieros = conexion.prepareStatement(consultaIngenieros);
            ResultSet resultadoIngenieros = statementIngenieros.executeQuery();
            while (resultadoIngenieros.next()) {
                cmbIDIng.addItem(resultadoIngenieros.getInt("IDIng"));
            }
            
            // Cargar IDs de proyectos
            String consultaProyectos = "SELECT IDProy FROM proyecto";
            PreparedStatement statementProyectos = conexion.prepareStatement(consultaProyectos);
            ResultSet resultadoProyectos = statementProyectos.executeQuery();
            while (resultadoProyectos.next()) {
                cmbIDProy.addItem(resultadoProyectos.getInt("IDProy"));
            }
            
            conexion.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar ingenieros y proyectos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para crear una nueva asignación en la base de datos
    private void crearAsignacion() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String insercion = "INSERT INTO asignacion (IDProy, IDIng) VALUES (?, ?)";
            PreparedStatement statement = conexion.prepareStatement(insercion);
            statement.setInt(1, (Integer) cmbIDProy.getSelectedItem());
            statement.setInt(2, (Integer) cmbIDIng.getSelectedItem());
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(this, "Asignación creada exitosamente.");
            }
            conexion.close();
            listarAsignaciones();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear la asignación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para modificar una asignación existente en la base de datos
    private void modificarAsignacion() {
        try {
            int selectedRow = tablaAsignaciones.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una asignación para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idProyOriginal = (Integer) modelo.getValueAt(selectedRow, 0);
            int idIngOriginal = (Integer) modelo.getValueAt(selectedRow, 1);

            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String actualizacion = "UPDATE asignacion SET IDProy = ?, IDIng = ? WHERE IDProy = ? AND IDIng = ?";
            PreparedStatement statement = conexion.prepareStatement(actualizacion);
            statement.setInt(1, (Integer) cmbIDProy.getSelectedItem());
            statement.setInt(2, (Integer) cmbIDIng.getSelectedItem());
            statement.setInt(3, idProyOriginal);
            statement.setInt(4, idIngOriginal);
            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Asignación modificada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la asignación para modificar.");
            }
            conexion.close();
            listarAsignaciones();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar la asignación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para eliminar una asignación de la base de datos
    private void eliminarAsignacion() {
        try {
            int selectedRow = tablaAsignaciones.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una asignación para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idProy = (Integer) modelo.getValueAt(selectedRow, 0);
            int idIng = (Integer) modelo.getValueAt(selectedRow, 1);

            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String eliminacion = "DELETE FROM asignacion WHERE IDProy = ? AND IDIng = ?";
            PreparedStatement statement = conexion.prepareStatement(eliminacion);
            statement.setInt(1, idProy);
            statement.setInt(2, idIng);
            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(this, "Asignación eliminada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la asignación para eliminar.");
            }
            conexion.close();
            listarAsignaciones();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la asignación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
