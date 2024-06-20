package base_de_datos_jdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ConsultaIngenieros extends JFrame {
    private JTextField txtIDIng;
    private JTextField txtEspecialidad;
    private JTextField txtCargo;
    private JTable tablaIngenieros;
    private DefaultTableModel modelo;
    private JLabel lblResultado;
    private JFrame mainApp; // Referencia a la ventana principal

    public ConsultaIngenieros(JFrame mainApp) {
        super("Gestión de Ingenieros");
        this.mainApp = mainApp;

        // Componentes de la interfaz
        JLabel lblIDIng = new JLabel("ID Ingeniero:");
        txtIDIng = new JTextField(20);
        txtIDIng.setEditable(false); // ID no editable
        JLabel lblEspecialidad = new JLabel("Especialidad:");
        txtEspecialidad = new JTextField(20);
        JLabel lblCargo = new JLabel("Cargo:");
        txtCargo = new JTextField(20);

        JButton btnCrear = new JButton("Crear Nuevo Ingeniero");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnAtras = new JButton("Atrás");

        // Acción del botón Crear
        btnCrear.addActionListener(e -> crearIngeniero());

        // Acción del botón Modificar
        btnModificar.addActionListener(e -> modificarIngeniero());

        // Acción del botón Eliminar
        btnEliminar.addActionListener(e -> eliminarIngeniero());

        // Acción del botón Atrás
        btnAtras.addActionListener(e -> {
            // Cerrar la ventana de gestión de ingenieros y mostrar la ventana de bienvenida
            this.setVisible(false);
            mainApp.setVisible(true);
        });

        // Definición de columnas para la tabla
        String[] columnas = {"ID Ingeniero", "Especialidad", "Cargo"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaIngenieros = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaIngenieros);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Acción al seleccionar un ingeniero en la tabla
        tablaIngenieros.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tablaIngenieros.getSelectedRow() != -1) {
                llenarCamposDesdeTabla(tablaIngenieros.getSelectedRow());
            }
        });

        // Etiqueta para mostrar el número de resultados encontrados
        lblResultado = new JLabel("Ingenieros almacenados:");
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
        panelSuperior.add(txtIDIng, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperior.add(lblEspecialidad, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtEspecialidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelSuperior.add(lblCargo, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtCargo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelSuperior.add(btnCrear, gbc);
        gbc.gridx = 1;
        panelSuperior.add(btnModificar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
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

        // Listar ingenieros al inicio
        listarIngenieros();
    }

    // Método para listar todos los ingenieros en la tabla
    private void listarIngenieros() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String consulta = "SELECT * FROM ingeniero";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            modelo.setRowCount(0); // Limpia la tabla antes de llenarla
            int rowCount = 0;
            while (resultado.next()) {
                Object[] fila = {
                    resultado.getInt("IDIng"),
                    resultado.getString("Especialidad"),
                    resultado.getString("Cargo")
                };
                modelo.addRow(fila);
                rowCount++;
            }
            lblResultado.setText("Ingenieros almacenados: " + rowCount);
            conexion.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar los ingenieros: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para llenar los campos de texto desde la tabla
    private void llenarCamposDesdeTabla(int fila) {
        txtIDIng.setText(modelo.getValueAt(fila, 0).toString());
        txtEspecialidad.setText(modelo.getValueAt(fila, 1).toString());
        txtCargo.setText(modelo.getValueAt(fila, 2).toString());
    }

    // Método para crear un nuevo ingeniero en la base de datos
    private void crearIngeniero() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String insercion = "INSERT INTO ingeniero (Especialidad, Cargo) VALUES (?, ?)";
            PreparedStatement statement = conexion.prepareStatement(insercion);
            statement.setString(1, txtEspecialidad.getText().trim());
            statement.setString(2, txtCargo.getText().trim());
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(this, "Ingeniero creado exitosamente.");
            }
            conexion.close();
            listarIngenieros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear el ingeniero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para modificar un ingeniero existente en la base de datos
    private void modificarIngeniero() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String actualizacion = "UPDATE ingeniero SET Especialidad = ?, Cargo = ? WHERE IDIng = ?";
            PreparedStatement statement = conexion.prepareStatement(actualizacion);
            statement.setString(1, txtEspecialidad.getText().trim());
            statement.setString(2, txtCargo.getText().trim());
            statement.setInt(3, Integer.parseInt(txtIDIng.getText().trim()));
            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Ingeniero modificado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el ingeniero para modificar.");
            }
            conexion.close();
            listarIngenieros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar el ingeniero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para eliminar un ingeniero de la base de datos
    private void eliminarIngeniero() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String eliminacion = "DELETE FROM ingeniero WHERE IDIng = ?";
            PreparedStatement statement = conexion.prepareStatement(eliminacion);
            statement.setInt(1, Integer.parseInt(txtIDIng.getText().trim()));
            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(this, "Ingeniero eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el ingeniero para eliminar.");
            }
            conexion.close();
            listarIngenieros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el ingeniero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
