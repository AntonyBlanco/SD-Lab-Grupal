package base_de_datos_jdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ConsultaIngenierosProyecto extends JFrame {
    private JTextField txtIDProy;
    private JTextField txtIDDpto;
    private JTextField txtNombre;
    private JTextField txtFecInicio;
    private JTextField txtFecTermino;
    private JTable tablaProyectos;
    private DefaultTableModel modelo;
    private JLabel lblResultado;
    private JFrame mainApp; // Referencia a la ventana principal

    public ConsultaIngenierosProyecto(JFrame mainApp) {
        super("Gestión de Proyectos");
        this.mainApp = mainApp;

        // Componentes de la interfaz
        JLabel lblIDProy = new JLabel("ID Proyecto:");
        txtIDProy = new JTextField(20);
        txtIDProy.setEditable(false); // ID no editable
        JLabel lblIDDpto = new JLabel("ID Departamento:");
        txtIDDpto = new JTextField(20);
        JLabel lblNombre = new JLabel("Nombre Proyecto:");
        txtNombre = new JTextField(20);
        JLabel lblFecInicio = new JLabel("Fecha Inicio:");
        txtFecInicio = new JTextField(20);
        JLabel lblFecTermino = new JLabel("Fecha Término:");
        txtFecTermino = new JTextField(20);

        JButton btnCrear = new JButton("Crear Nuevo Proyecto");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnAtras = new JButton("Atrás");

        // Acción del botón Crear
        btnCrear.addActionListener(e -> crearProyecto());

        // Acción del botón Modificar
        btnModificar.addActionListener(e -> modificarProyecto());

        // Acción del botón Eliminar
        btnEliminar.addActionListener(e -> eliminarProyecto());

        // Acción del botón Atrás
        btnAtras.addActionListener(e -> {
            // Cerrar la ventana de gestión de proyectos y mostrar la ventana de bienvenida
            this.setVisible(false);
            mainApp.setVisible(true);
        });

        // Definición de columnas para la tabla
        String[] columnas = {"ID Proyecto", "ID Departamento", "Nombre", "Fecha Inicio", "Fecha Término"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaProyectos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaProyectos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Acción al seleccionar un proyecto en la tabla
        tablaProyectos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tablaProyectos.getSelectedRow() != -1) {
                llenarCamposDesdeTabla(tablaProyectos.getSelectedRow());
            }
        });

        // Etiqueta para mostrar el número de resultados encontrados
        lblResultado = new JLabel("Proyectos almacenados:");
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
        panelSuperior.add(lblIDProy, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtIDProy, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperior.add(lblIDDpto, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtIDDpto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelSuperior.add(lblNombre, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelSuperior.add(lblFecInicio, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtFecInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelSuperior.add(lblFecTermino, gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtFecTermino, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelSuperior.add(btnCrear, gbc);
        gbc.gridx = 1;
        panelSuperior.add(btnModificar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
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

        // Listar proyectos al inicio
        listarProyectos();
    }

    // Método para listar todos los proyectos en la tabla
    private void listarProyectos() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String consulta = "SELECT * FROM proyecto";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            modelo.setRowCount(0); // Limpia la tabla antes de llenarla
            int rowCount = 0;
            while (resultado.next()) {
                Object[] fila = {
                    resultado.getInt("IDProy"),
                    resultado.getInt("IDDpto"),
                    resultado.getString("Nombre"),
                    resultado.getString("Fec_Inicio"),
                    resultado.getString("Fec_Termino")
                };
                modelo.addRow(fila);
                rowCount++;
            }
            lblResultado.setText("Proyectos almacenados: " + rowCount);
            conexion.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar los proyectos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para llenar los campos de texto desde la tabla
    private void llenarCamposDesdeTabla(int fila) {
        txtIDProy.setText(modelo.getValueAt(fila, 0).toString());
        txtIDDpto.setText(modelo.getValueAt(fila, 1).toString());
        txtNombre.setText(modelo.getValueAt(fila, 2).toString());
        txtFecInicio.setText(modelo.getValueAt(fila, 3).toString());
        txtFecTermino.setText(modelo.getValueAt(fila, 4).toString());
    }

    // Método para crear un nuevo proyecto en la base de datos
    private void crearProyecto() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String insercion = "INSERT INTO proyecto (IDDpto, Nombre, Fec_Inicio, Fec_Termino) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(insercion);
            statement.setInt(1, Integer.parseInt(txtIDDpto.getText().trim()));
            statement.setString(2, txtNombre.getText().trim());
            statement.setString(3, txtFecInicio.getText().trim());
            statement.setString(4, txtFecTermino.getText().trim());
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(this, "Proyecto creado exitosamente.");
            }
            conexion.close();
            listarProyectos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear el proyecto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para modificar un proyecto existente en la base de datos
    private void modificarProyecto() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String actualizacion = "UPDATE proyecto SET IDDpto = ?, Nombre = ?, Fec_Inicio = ?, Fec_Termino = ? WHERE IDProy = ?";
            PreparedStatement statement = conexion.prepareStatement(actualizacion);
            statement.setInt(1, Integer.parseInt(txtIDDpto.getText().trim()));
            statement.setString(2, txtNombre.getText().trim());
            statement.setString(3, txtFecInicio.getText().trim());
            statement.setString(4, txtFecTermino.getText().trim());
            statement.setInt(5, Integer.parseInt(txtIDProy.getText().trim()));
            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Proyecto modificado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el proyecto para modificar.");
            }
            conexion.close();
            listarProyectos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar el proyecto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para eliminar un proyecto de la base de datos
    private void eliminarProyecto() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2", "root", "");
            String eliminacion = "DELETE FROM proyecto WHERE IDProy = ?";
            PreparedStatement statement = conexion.prepareStatement(eliminacion);
            statement.setInt(1, Integer.parseInt(txtIDProy.getText().trim()));
            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(this, "Proyecto eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el proyecto para eliminar.");
            }
            conexion.close();
            listarProyectos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el proyecto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}




