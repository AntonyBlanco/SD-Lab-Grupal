import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Departamentos extends JFrame {

    private JTextField IDDpto = new JTextField(3);
    private JTextField Nombre = new JTextField(30);
    private JTextField Telefono = new JTextField(30);
    private JTextField Fax = new JTextField(2);
            
    private DefaultTableModel model = new DefaultTableModel(new Object[]{
        "IDDpto",
        "Nombre",
        "Telefono",
        "Fax",}, 0);
    
    private JTable table = new JTable(model);

    
    //ESTRUCTURAS DE NECESIDAD
    //Bandera
    boolean bandera = false;
    //Elección
    
    // Instancia de FuncionesMedicosFrame
    private FuncionesInterfaces funciones = new FuncionesInterfaces();
    
    List<JButton> buttonList = new ArrayList<>();
    List<JTextField> textFieldList = new ArrayList<>();
    List<JComboBox<String>> comboBoxList = new ArrayList<>();
    Map<Integer, Integer> fieldNumEnTabla = new HashMap<>();
    Map<Integer, Integer> comboBoxdNumEnTabla = new HashMap<>();
    
    String [] tipoDeDatosFields = {"n","s","s","s"};
    
    int [] posFieldsEnTabla = {0,1,2,3,4,5,7,8};
    int [] posComboboxEnTabla = {6};
    
    String [] Consultas = {
        "SELECT DISTINCT EspCod FROM ESPECIALIDAD", 
    };
    String [] campoDeConsulta = {
        "EspCod",
    };
    
    String[] campos = {
        "IDDpto",
        "Nombre",
        "Telefono",
        "Fax"
    };
    String[]camposDeTexto ={
        "Nombre",
        "Telefono",
        "Fax",
    };
    String[]camposNumericos = {
        "IDDpto"
    };
    String tabla = "departamentos";
    
    public Departamentos() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Registro de DEPARTAMENTOS"));
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 248, 255)); // Color Azul claro

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addFieldLabel(inputPanel, "ID del departamento", IDDpto, constraints, 0);
        addFieldLabel(inputPanel, "Nombre del departamento", Nombre, constraints, 1);
        addFieldLabel(inputPanel, "Telefono del departamento", Telefono, constraints, 2);
        addFieldLabel(inputPanel, "FAX del departamento", Fax, constraints, 3);

        add(inputPanel, BorderLayout.NORTH);
        // HashMap
        // Se agrega la posición en la tabla de cada field
        for (int i = 0; i < posFieldsEnTabla.length; i++) {
            fieldNumEnTabla.put(posFieldsEnTabla[i], i);
        }

        // Se agrega la posición en la tabla de cada comboBox
        for (int i = 0; i < posComboboxEnTabla.length; i++) {
            comboBoxdNumEnTabla.put(posComboboxEnTabla[i], i);
        }
        
        //TABLA
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Tabla_DEPARTAMENTOS"));
        tablePanel.setBackground(new Color(240, 248, 255)); // Color Azul claro
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        Funciones.actualizarTabla(model, tabla);

        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4));
        buttonPanel.setBackground(new Color(230, 230, 250)); // Color Lila

        //Integrando botones con addButton
        addButton(buttonPanel, "Adicionar");
        addButton(buttonPanel, "Modificar");
        addButton(buttonPanel, "Eliminar"); 
        addButton(buttonPanel, "Actualizar"); 
        addButton(buttonPanel, "Salir");

        add(buttonPanel, BorderLayout.SOUTH);
        
        Funciones.actualizarTabla(model, tabla);
        FuncionesInterfaces.deshabilitarFields(textFieldList, 0,textFieldList.size());
        FuncionesInterfaces.deshabilitarCombobox(comboBoxList, 0, comboBoxList.size());

        pack();
        setPreferredSize(new Dimension(600, 400)); // Ajusta el tamaño preferido de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void addFieldLabel(JPanel panel, String labelText, JTextField textField, GridBagConstraints constraints, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(label, constraints);

        constraints.gridx = 1;
        panel.add(textField, constraints);
        textFieldList.add(textField);

        constraints.gridx = 0;
        constraints.gridy = row + 1;
    }
  
    private void addButton(JPanel panel, String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(new Color(51, 204, 255)); // Color Azul claro
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));

        // Llama a los métodos de FuncionesMedicosFrame en los manejadores de eventos de los botones
        switch (buttonText) {
            case "Adicionar":
                button.addActionListener(e -> {
                    bandera = funciones.Adicionar(e, textFieldList, bandera);
                    //FuncionesInterfaces.llenarListaDeCombobox(comboBoxList,Consultas,campoDeConsulta,table,posComboboxEnTabla);
                   // FuncionesInterfaces.habilitarCombobox(comboBoxList, 0, comboBoxList.size());
                    Funciones.actualizarTabla(model, tabla);
                    bandera = true;
                    
                }); 
                break;
            case "Modificar":
                button.addActionListener(e -> {funciones.Modificar(e,table,textFieldList,buttonList,posFieldsEnTabla,comboBoxList, Consultas, campoDeConsulta, posComboboxEnTabla);
                    System.out.println("PRESIONAS MODIFICAR");
                });
                break;
            case "Eliminar":
                button.addActionListener(e -> {funciones.InactivarActivarEliminar(e,table,textFieldList,buttonList,fieldNumEnTabla, "*", posFieldsEnTabla, comboBoxList, Consultas, campoDeConsulta, posComboboxEnTabla);
                });
                break;
            case "Actualizar":
                button.addActionListener(e -> {
                    bandera = funciones.actualizar(e,textFieldList,fieldNumEnTabla,comboBoxList,comboBoxdNumEnTabla, campos, camposDeTexto, camposNumericos, tabla, bandera, tipoDeDatosFields); 
                    Funciones.actualizarTabla(model, tabla);
                    funciones.cancelar(e,table,textFieldList, buttonList, comboBoxList); 
                    bandera=false;
                });
                break;

            case "Salir":
                System.out.println("MedicosFrame.addButton()");
                button.addActionListener(e -> {
                	this.setVisible(false);
                	;});
                break;
        }
        panel.add(button);
        buttonList.add(button);
    }

    public static void main(String[] args) {
        new Departamentos().setVisible(true);
    }
}
    
