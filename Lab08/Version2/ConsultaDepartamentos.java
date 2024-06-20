package base_de_datos_jdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ConsultaDepartamentos extends JFrame {
    private JTextField txtIDDpto;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtFax;
    private JTable tablaDepartamentos;
    private DefaultTableModel modelo;
    private JLabel lblResultado;
    private JFrame mainApp; // Referencia a la ventana principal

    public ConsultaDepartamentos(JFrame mainApp) {
        super("Gestión de Departamentos");
        this.mainApp = mainApp;

        // Componentes de la interfaz
        JLabel lblIDDpto = new JLabel("ID Departamento:");
        txtIDDpto = new JTextField(20);
        txtIDDpto.setEditable(false); // ID no editable
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(20);
        JLabel lblTelefono = new JLabel("Teléfono:");
        txtTelefono = new JTextField(20);
        JLabel lblFax = new JLabel("Fax:");
        txtFax = new JTextField(20);

        JButton btnCrear = new JButton("Crear Nuevo Departamento");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnAtras = new JButton("Atrás");

        // Acción del botón Crear
        btnCrear.addActionListener(e -> crearDepartamento());

        // Acción del botón Modificar
        btnModificar.addActionListener(e -> modificarDepartamento());

        // Acción del botón Eliminar
        btnEliminar.addActionListener(e -> eliminarDepartamento());

        // Acción del botón Atrás
        btnAtras.addActionListener(e -> {
            // Cerrar la ventana de gestión de departamentos y mostrar la ventana de bienvenida
            this.setVisible(false);
            mainApp.setVisible(true);
        });

        // Definición de columnas para la tabla
        String[] columnas = {"ID Departamento", "Nombre", "Teléfono", "Fax"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaDepartamentos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaDepartamentos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Acción al seleccionar un departamento en la tabla
        tablaDepartamentos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tablaDepartamentos.getSelectedRow() != -1) {
                llenarCamposDesdeTabla(tablaDepartamentos.getSelectedRow());
            }
        });

        // Etiqueta para mostrar el número de resultados encontrados
        lblResultado = new JLabel("Departamentos almacenados:");
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
        panelSuperior.add(lblIDDpto, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtIDDpto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperior.add(lblNombre, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelSuperior.add(lblTelefono, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtTelefono, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelSuperior.add(lblFax, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtFax, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelSuperior.add(btnCrear, gbc);
        gbc.gridx = 1;
        panelSuperior.add(btnModificar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
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

        // Listar departamentos al inicio
        listarDepartamentos();
    }

    // Método para listar todos los departamentos en la tabla
    private void listarDepartamentos() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String consulta = "SELECT * FROM departamentos";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            modelo.setRowCount(0); // Limpia la tabla antes de llenarla
            int rowCount = 0;
            while (resultado.next()) {
                Object[] fila = {
                    resultado.getInt("IDDpto"),
                    resultado.getString("Nombre"),
                    resultado.getString("Telefono"),
                    resultado.getString("Fax")
                };
                modelo.addRow(fila);
                rowCount++;
            }
            lblResultado.setText("Departamentos almacenados: " + rowCount);
            conexion.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar los departamentos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para llenar los campos de texto desde la tabla
    private void llenarCamposDesdeTabla(int fila) {
        txtIDDpto.setText(modelo.getValueAt(fila, 0).toString());
        txtNombre.setText(modelo.getValueAt(fila, 1).toString());
        txtTelefono.setText(modelo.getValueAt(fila, 2).toString());
        txtFax.setText(modelo.getValueAt(fila, 3).toString());
    }

    // Método para crear un nuevo departamento en la base de datos
    private void crearDepartamento() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String insercion = "INSERT INTO departamentos (Nombre, Telefono, Fax) VALUES (?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(insercion);
            statement.setString(1, txtNombre.getText().trim());
            statement.setString(2, txtTelefono.getText().trim());
            statement.setString(3, txtFax.getText().trim());
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(this, "Departamento creado exitosamente.");
            }
            conexion.close();
            listarDepartamentos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear el departamento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para modificar un departamento existente en la base de datos
    private void modificarDepartamento() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String actualizacion = "UPDATE departamentos SET Nombre = ?, Telefono = ?, Fax = ? WHERE IDDpto = ?";
            PreparedStatement statement = conexion.prepareStatement(actualizacion);
            statement.setString(1, txtNombre.getText().trim());
            statement.setString(2, txtTelefono.getText().trim());
            statement.setString(3, txtFax.getText().trim());
            statement.setInt(4, Integer.parseInt(txtIDDpto.getText().trim()));
            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Departamento modificado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el departamento para modificar.");
            }
            conexion.close();
            listarDepartamentos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar el departamento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para eliminar un departamento de la base de datos
    private void eliminarDepartamento() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String eliminacion = "DELETE FROM departamentos WHERE IDDpto = ?";
            PreparedStatement statement = conexion.prepareStatement(eliminacion);
            statement.setInt(1, Integer.parseInt(txtIDDpto.getText().trim()));
            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(this, "Departamento eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el departamento para eliminar.");
            }
            conexion.close();
            listarDepartamentos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el departamento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}


