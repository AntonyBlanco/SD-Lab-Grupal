import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class ConsultaProyectosDepartamento extends JFrame {
    private JTextField txtDepartamento; // Campo de texto para ingresar el nombre del departamento
    private JTable tablaResultados; // Tabla para mostrar los resultados
    private DefaultTableModel modelo; // Modelo de tabla para manejar los datos
    private JLabel lblResultado; // Etiqueta para mostrar el número de resultados encontrados
    public ConsultaProyectosDepartamento(String titulo) {
        super(titulo); // Llama al constructor de la clase JFrame para establecer el título de la ventana
        // Componentes de la interfaz
        JLabel lblDepartamento = new JLabel("Departamento:");
        txtDepartamento = new JTextField(20);
        JButton btnConsultar = new JButton("Consultar");
        // Acción del botón Consultar
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String departamento = txtDepartamento.getText().trim();
                if (!departamento.isEmpty()) {
                    consultarProyectosPorDepartamento(departamento);
                } else {
                    JOptionPane.showMessageDialog(ConsultaProyectosDepartamento.this,
                    "Ingrese un nombre de departamento válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Definición de columnas para la tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Fecha"};
        modelo = new DefaultTableModel(columnas, 0); // Inicializa el modelo de tabla sin filas
        tablaResultados = new JTable(modelo); // Crea la tabla con el modelo especificado
        JScrollPane scrollPane = new JScrollPane(tablaResultados); // Panel de desplazamiento para la tabla
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Establece un borde vacío al panel
        // Etiqueta para mostrar el número de resultados encontrados
        lblResultado = new JLabel("Resultados encontrados:");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER); // Alineación centrada
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.add(lblResultado, BorderLayout.NORTH);
        panelResultados.add(scrollPane, BorderLayout.CENTER);
        // Panel superior con los componentes de entrada (etiqueta, campo de texto y botón)
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(lblDepartamento);
        panelSuperior.add(txtDepartamento);
        panelSuperior.add(btnConsultar);
        // Panel principal que contiene el panel superior y el panel de resultados
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelResultados, BorderLayout.CENTER);
        // Configuración de la ventana principal
        getContentPane().add(panel);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // Método para consultar proyectos por departamento en la base de datos
    private void consultarProyectosPorDepartamento(String departamento) {
        try {
            Connection conexion =
            DriverManager.getConnection("jdbc:mysql://localhost/mydb2", "root", "1234");
            String consulta = "SELECT IDProy, Nombre, Fec_Inicio, Fec_Termino " +
            "FROM proyecto " +
            "WHERE IDDpto = (SELECT IDDpto FROM departamentos WHERE Nombre = ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, departamento);
            ResultSet resultado = statement.executeQuery(); // Ejecuta la consulta SQL
            llenarTabla(resultado); // Llena la tabla con los resultados obtenidos
            conexion.close(); // Cierra la conexión a la base de datos
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar la base de datos: " +
            e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    // Método para llenar la tabla con los resultados de la consulta
    private void llenarTabla(ResultSet resultado) throws SQLException {
        modelo.setRowCount(0); // Limpia la tabla antes de llenarla
        int rowCount = 0;
        while (resultado.next()) {
            // Obtiene los valores de cada columna y los agrega como una nueva fila en el modelo de tabla
            Object[] fila = {
                resultado.getInt("IDProy"),
                resultado.getString("Nombre"),
                resultado.getString("Fec_Inicio"),
                resultado.getString("Fec_Termino")
            };
            modelo.addRow(fila); // Agrega la fila al modelo de tabla
            rowCount++;
        }
        // Actualiza el texto de la etiqueta con el número de resultados encontrados
        lblResultado.setText("Resultados encontrados: " + rowCount);
    }
    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConsultaProyectosDepartamento ventana = new
                ConsultaProyectosDepartamento("Consulta de Proyectos por Departamento");
                ventana.setVisible(true);
            }
        });
    }
}
