import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
// Clase principal que extiende JFrame para crear una ventana de consulta de ingenieros por proyecto
public class ConsultaIngenierosProyecto extends JFrame {
    private JTextField txtProyecto; // Campo de texto para ingresar el nombre del proyecto
    private JTable tablaResultados; // Tabla para mostrar los resultados de la consulta
    private DefaultTableModel modelo; // Modelo de tabla para manejar los datos
    private JLabel lblResultado; // Etiqueta para mostrar el número de resultados encontrados
    public ConsultaIngenierosProyecto(String titulo) {
        super(titulo);
        // Componentes de la interfaz
        JLabel lblProyecto = new JLabel("Proyecto:");
        txtProyecto = new JTextField(20);
        JButton btnConsultar = new JButton("Consultar");
        // Acción del botón Consultar
        btnConsultar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String proyecto = txtProyecto.getText().trim();
            if (!proyecto.isEmpty()) {
                consultarIngenierosPorProyecto(proyecto);
            } else {
                JOptionPane.showMessageDialog(ConsultaIngenierosProyecto.this,
                "Ingrese un nombre de proyecto válido.", "Error",
                JOptionPane.ERROR_MESSAGE);
            }
        }
        });
        // Definición de columnas para la tabla
        String[] columnas = {"ID", "Especialidad", "Cargo"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Etiqueta para mostrar el número de resultados encontrados
        lblResultado = new JLabel("Resultados encontrados:");
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.add(lblResultado, BorderLayout.NORTH);
        panelResultados.add(scrollPane, BorderLayout.CENTER);
        // Panel superior con los componentes de entrada (etiqueta, campo de texto y botón)

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(lblProyecto);
        panelSuperior.add(txtProyecto);
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
    // Método para consultar ingenieros por proyecto en la base de datos
    private void consultarIngenierosPorProyecto(String proyecto) {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/mydb2",
            "root", "1234");
            String consulta = "SELECT i.IDIng, i.Especialidad, i.Cargo " +
            "FROM ingeniero i " +
            // Une la tabla ingeniero con la tabla asignación utilizando la columna IDIng.
            "JOIN asignacion a ON i.IDIng = a.IDIng " +
            // Une la tabla asignación con la tabla proyecto utilizando la columna IDProy.
            "JOIN proyecto p ON a.IDProy = p.IDProy " +
            // Filtra las filas resultantes donde el nombre del proyecto (p.Nombre) es igual al buscado
            "WHERE p.Nombre = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, proyecto);
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
                resultado.getInt("IDIng"),
                resultado.getString("Especialidad"),
                resultado.getString("Cargo")
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
                ConsultaIngenierosProyecto ventana = new ConsultaIngenierosProyecto("Consulta de Ingenieros por Proyecto");
                ventana.setVisible(true);
            }
        });
    }
}
