import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class FuncionesInterfaces {
    
    public boolean Adicionar(java.awt.event.ActionEvent evt, List<JTextField> textFieldList,boolean bandera) {
        bandera = habilitarFields(textFieldList, 0,textFieldList.size() );        
        
        bandera = true;
        return bandera;
    }
    public void InactivarActivarEliminar(java.awt.event.ActionEvent evt,  JTable jTable1, List<JTextField> textFieldList, List<JButton> buttonList, Map<Integer, Integer> fieldNumEnTabla, String caso, int [] posFieldsEnTabla, List<JComboBox<String>> comboBoxList, String [] Consultas, String [] campoDeConsulta, int [] posComboboxEnTabla) {
        if (jTable1.getSelectedRow() != -1 && jTable1.getSelectedColumn() != -1) {
            int row = jTable1.getSelectedRow();    
            //desabilita todos los fields
            deshabilitarFields(textFieldList, 0, (textFieldList.size()-1));
            //inactiva los comboBox
            deshabilitarCombobox(comboBoxList, 0, comboBoxList.size());
            //habilita el ultimo field
            habilitarField(textFieldList, (textFieldList.size()-1));
            //LLena los combobox
            llenarOpcionesDeComboboxs(comboBoxList, Consultas, campoDeConsulta, jTable1, row, posComboboxEnTabla);    
            for (int i = 0; i < (textFieldList.size()-1); i++) {	
            	JTextField textField = textFieldList.get(i);
            	System.out.println(textField.getText());
            	textField.setText(""+jTable1.getValueAt(row, posFieldsEnTabla[i]));
            }
            JTextField textField = textFieldList.get(textFieldList.size()-1);
            textField.setText(caso);
        }
        else {
        	JOptionPane.showMessageDialog(null, "No hay celda seleccionada en la grilla.");
        }
    }

    public void Modificar(java.awt.event.ActionEvent evt,  JTable jTable1, List<JTextField> textFieldList, List<JButton> buttonList, int [] posFieldsEnTabla, List<JComboBox<String>> comboBoxList, String [] Consultas, String [] campoDeConsulta, int [] posComboboxEnTabla) {
    	if (jTable1.getSelectedRow() != -1 && jTable1.getSelectedColumn() != -1) {
    		
    		int row = jTable1.getSelectedRow();
    		int numColumnas = jTable1.getModel().getColumnCount();
    		if (jTable1.getValueAt(row, (numColumnas-1)).equals("*") || jTable1.getValueAt(row, (numColumnas-1)).equals("I")) {
    			JOptionPane.showMessageDialog(null, "Elemento NO MODIFICABLE");
    		}
    		else {
    			habilitarFields(textFieldList, 1, textFieldList.size());
    			llenarOpcionesDeComboboxs(comboBoxList, Consultas, campoDeConsulta, jTable1, row, posComboboxEnTabla);
    			//inactiva los comboBox
    			habilitarCombobox(comboBoxList, 0, comboBoxList.size());

    			for (int i = 0; i < textFieldList.size(); i++) {
    				JTextField textField = textFieldList.get(i);
    				textField.setText("" + jTable1.getValueAt(row, posFieldsEnTabla[i]));
    			}    
                //buttonList.get(4).setEnabled(false);
                //buttonList.get(5).setEnabled(false);
    		}
    	}    
    	else {
        	JOptionPane.showMessageDialog(null, "No hay celda seleccionada en la grilla.");
    	}
    }
    //Deselecciona la tabla, cooloca en blanco los fields y los deshabilita
    public void cancelar(java.awt.event.ActionEvent evt, JTable jTable1, List<JTextField> textFieldList, List<JButton> buttonList, List<JComboBox<String>> comboBoxList) {
        jTable1.clearSelection();
        vaciarFields(textFieldList, 0, (textFieldList.size()));
        deshabilitarFields(textFieldList, 0, (textFieldList.size()));
        vaciarCombobox(comboBoxList);
        deshabilitarCombobox(comboBoxList, 0, comboBoxList.size());
        //buttonList.get(4).setEnabled(true);
        //buttonList.get(5).setEnabled(true);
        System.out.println("LLEGA AL FIN DE LA FUNCION CANCELAR");
    }
    public boolean actualizar(java.awt.event.ActionEvent evt, List<JTextField> textFieldList, Map<Integer, Integer> fieldNumEnTabla, List<JComboBox<String>> comboBoxList, Map<Integer, Integer> comboBoxdNumEnTabla, String[]campos, String[]camposDeTexto, String[]camposNumericos,String tabla, boolean bandera, String [] tipoDeDatosFields) {
        //Creacion de arreglos
        int n=0;
        int s=0;
        for (int i = 0; i < tipoDeDatosFields.length; i++) {
            if(tipoDeDatosFields[i].equals("n")){
                n++;
            }else{
                s++;
            }
        }

        String[] texto = new String[s];
        int [] numericos = new int[n];
        int cantTextos = 0;
        int cantValores = 0;
        
        //Obtiene el codigo
        JTextField textField0 = textFieldList.get(0);
        String codigo = textField0.getText();
        
        //separar valores numericos de strings
        for (int i = 0; i < tipoDeDatosFields.length; i++) {
            System.out.println(""+i);
            if(tipoDeDatosFields[i].equals("n") && fieldNumEnTabla.containsKey(i)){
                JTextField textField = textFieldList.get(fieldNumEnTabla.get(i));
                String v = textField.getText();
                int valor = Integer.parseInt(v);
                numericos[cantValores] = valor;
                cantValores++;
                System.out.println("Numerico de Field");
            }else if (tipoDeDatosFields[i].equals("s") && fieldNumEnTabla.containsKey(i)) {
                System.out.println("Texto de field");
                JTextField textField = textFieldList.get(fieldNumEnTabla.get(i));
                String text = textField.getText();
                texto[cantTextos] = text;
                cantTextos++;
                
            }else if (tipoDeDatosFields[i].equals("n") && comboBoxdNumEnTabla.containsKey(i)) {
                System.out.println("Numerico de ComboBox");
                JComboBox<String> comboBox = comboBoxList.get(comboBoxdNumEnTabla.get(i));
                String elementoSeleccionado = (String) comboBox.getSelectedItem();
                numericos[cantValores] = Integer.parseInt(elementoSeleccionado);
                cantValores++;
                
            }else if (tipoDeDatosFields[i].equals("s") && comboBoxdNumEnTabla.containsKey(i)) {
                System.out.println("Texto de comboBox");
                JComboBox<String> comboBox = comboBoxList.get(comboBoxdNumEnTabla.get(i));
                String elementoSeleccionado = (String) comboBox.getSelectedItem();
                texto[cantTextos] = elementoSeleccionado;
                cantTextos++;
            }
        }
        if(bandera){
            Funciones.insert(camposDeTexto,camposNumericos ,texto,numericos,tabla);
            bandera = false;
        }else{
            Funciones.crudActualizarElemento(camposDeTexto, camposNumericos, texto, numericos, codigo, tipoDeDatosFields[0], tabla);
            bandera = false;    
        }
        //VERIFICACION DE DATOS EN LA CONSOLA
        for (int i = 0; i < numericos.length; i++) {
            System.out.println(camposNumericos[i]+" :"+numericos[i]);
        }
        for (int i = 0; i < texto.length; i++) {
            System.out.println(camposDeTexto[i]+" :"+texto[i]);
        }
        
        System.out.println("cant n" + n);
        System.out.println("cant s" + s);
       
        System.out.println("***************");
        for (int i = 0; i < numericos.length; i++) {
            System.out.println(camposNumericos[i]+" es "+numericos[i]);
        }
        System.out.println("=**************");
        for (int i = 0; i < texto.length; i++) {
            System.out.println(camposDeTexto[i]+" es " + camposDeTexto[i]);
        }
        System.out.println("***************");
        System.out.println("FIELDS Y COMBOBOX");
        for (int i = 0; i < textFieldList.size(); i++) {
            System.out.println("Field "+ i+" "+textFieldList.get(i).getText());
        }
        for (int i = 0; i < comboBoxList.size(); i++) {
            System.out.println("Combobox "+ i+" " + comboBoxList.get(i).getSelectedItem());
        }
        System.out.println("FIELDS Y COMBOBOZX--Fin");
        return bandera;
    }
    
    //============FUNCIONES COMPONENTES==================
    //FUNCIONES CON FIELDS------------------
    //HABILITACION DE LOS FIELDS
    public static boolean habilitarFields(List<JTextField> textFieldList, int a, int b) {
        // Lógica para manejar la acción del botón Adicionar aquí...    LLLLLLLL    
        for (int i = a; i < b; i++) {
            JTextField textField = textFieldList.get(i);
            textField.setEnabled(true);
        }
        return true; 
    }
    public static boolean deshabilitarFields(List<JTextField> textFieldList, int a, int b) {       
        for (int i = a; i < b; i++) {
            JTextField textField = textFieldList.get(i);
            textField.setEnabled(false);
        }
        return false; 
    }
    
    
    public static void deshabilitarField(List<JTextField> textFieldList, int numField) {
        JTextField textField = textFieldList.get(numField);
        textField.setEnabled(false); 
    }
    public static void habilitarField(List<JTextField> textFieldList, int numField) {
        JTextField textField = textFieldList.get(numField);
        textField.setEnabled(true); 
    }
    
    public static void vaciarFields(List<JTextField> textFieldList, int a, int b) {
        for (int i = a; i < b; i++) {
            JTextField textField = textFieldList.get(i);
            System.out.println(textField.getText()+"causita");
            textField.setText("");
        }
    }
    
    
    //FUNCIONES CON LA GRILLA
    public ArrayList<String> obtenerNombresCabeceras(JTable table) {
        int columnCount = table.getColumnCount();
        ArrayList<String> headerNames = new ArrayList<>();

        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            String columnName = table.getColumnName(columnIndex);
            headerNames.add(columnName);
        }

        return headerNames;
    }
    
    
    
    
    //FUNCIONES CON COMBOBOX------------------
    
    
    public static boolean llenarOpcionesDeComboboxs(List<JComboBox<String>> comboBoxList, String [] Consultas, String [] campoDeConsulta, JTable jTable1, int row, int [] posComboboxEnTabla) {       
        for (int i = 0; i < comboBoxList.size(); i++) {
            JComboBox<String> comboBox = comboBoxList.get(i);
            comboBox.removeAllItems();
            comboBox.setModel(new DefaultComboBoxModel<>(Funciones.obtenerLista(Consultas[i], campoDeConsulta[i]))); // Agregar las nuevas opciones
            comboBox.setSelectedItem(""+jTable1.getValueAt(row, posComboboxEnTabla[i]));
        }
        return true; 
    }
    public static boolean llenarListaDeCombobox(List<JComboBox<String>> comboBoxList, String [] Consultas, String [] campoDeConsulta, JTable jTable1, int [] posComboboxEnTabla) {      
        for (int i = 0; i < Consultas.length; i++) {
            JComboBox<String> comboBox = comboBoxList.get(i);
            comboBox.removeAllItems();
            comboBox.setModel(new DefaultComboBoxModel<>(Funciones.obtenerLista(Consultas[i], campoDeConsulta[i])));
            comboBox.setSelectedItem("");
        }
        return true; 
    }
    
    public static boolean vaciarCombobox(List<JComboBox<String>> comboBoxList) {
               
        for (int i = 0; i < comboBoxList.size(); i++) {
            JComboBox<String> comboBox = comboBoxList.get(i);
            comboBox.removeAllItems(); 
        }
        return true; 
    }
    
    public static boolean deshabilitarCombobox(List<JComboBox<String>> comboBoxList, int a, int b) {
               
        for (int i = a; i < b; i++) {
            JComboBox<String> comboBox = comboBoxList.get(i);
            comboBox.setEnabled(false); 
        }
        return true;  
    }
    public static boolean habilitarCombobox(List<JComboBox<String>> comboBoxList, int a, int b) {
               
        for (int i = a; i < b; i++) {
            JComboBox<String> comboBox = comboBoxList.get(i);
            comboBox.setEnabled(true); 
        }
        return true;  
    }
}
