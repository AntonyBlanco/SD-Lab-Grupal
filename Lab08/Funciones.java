import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
public class Funciones {	
	public static void actualizar(DefaultTableModel model, String tabla) {

		Connection conn = null;
		String url, user, pass;
	    try {
	    	Class.forName("oracle.jdbc.OracleDriver");
			url = "jdbc:mysql://localhost/mydb2";
			user = "root";
			pass = "1234";
			conn = DriverManager.getConnection(url, user, pass);
			if(!conn.isClosed()){
		        System.out.println("Database connection working TCP/IP...");
		        try{
		        	String seleccion = "SELECT * FROM "+tabla+"";
			        PreparedStatement Select_Datos = conn.prepareStatement(seleccion);
			        ResultSet rs = Select_Datos.executeQuery();
			        int rowCount = model.getRowCount();
			        for (int i = rowCount - 1; i >= 0; i--) {
			            model.removeRow(i);
			        }
			        while(rs.next()) {
			        	
			            System.out.println("ConNum: "+ rs.getInt(1));
			            System.out.println("ConNom: "+ rs.getString(2));
			            System.out.println("ConEstReg: "+ rs.getString(3));
			            model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3)});
			        }
			        rs.close();
			        Select_Datos.close();
			        }catch (SQLException e){
			        	System.out.println("Exception: " + e.getMessage());
			        }
		      }
	    }catch (SQLException | IllegalArgumentException | SecurityException | ClassNotFoundException e){
	    	System.out.println("Excesssption: " + e.getMessage());
	    }
	}
	public static void crudActualizarElemento(String[]camposDeTexto, String[] camposNumericos , String[] texto, int [] numericos, String codigo, String codigoTipo, String tabla){
	    Connection conn = null;
		String url, user, pass;
	    try{
	    	//Class.forName("oracle.jdbc.OracleDriver");
			url = "jdbc:mysql://localhost/mydb2";
			user = "root";
			pass = "1234";
			conn = DriverManager.getConnection(url, user, pass);
			if(!conn.isClosed()){
		        System.out.println("Database connection working TCP/IP...5555");
		        try{
		            String update = "UPDATE "+tabla+" SET ";
		            update += camposDeTexto[0];
		            for (int i = 1; i < camposDeTexto.length; i++) {
		                update += " = ?, "+camposDeTexto[i];
		            }
		            for (int i = 1; i < camposNumericos.length; i++) {
		                update += " = ?, "+camposNumericos[i];
		            }
		            if(codigoTipo.equals("n")){
		            	update += " = ? WHERE " + camposNumericos[0] + " = ? ";
		            }else{
		            	update += " = ? WHERE " + camposDeTexto[0] + " = ? ";
		            }
		            System.out.println("Consulta: "+ update);
		            PreparedStatement Update_Datos = conn.prepareStatement(update);
		            int contador = 1;
		            //Insertando strings
		            for (int i = 0; i < texto.length; i++) {
		            	Update_Datos.setString(contador, texto[i]);
		            	contador++;  
		            }
		            //insertando enteros
		            for (int i = 1; i < numericos.length; i++) {
		            	Update_Datos.setInt(contador, numericos[i]);
		            	contador++;
		            }
		            if(codigoTipo.equals("n")){
			            int codigoEnEntero = Integer.parseInt(codigo);
			            Update_Datos.setInt(contador, codigoEnEntero);
		            }else{
			            Update_Datos.setString(contador, codigo);
		            }
			        int count = Update_Datos.executeUpdate();
			        System.out.println("Datos Actualizados: " + count);
			        Update_Datos.close();
		        }catch (SQLException e){
		          System.out.println("Exception: " + e.getMessage());
		        }
			}
	      
	    }catch (SQLException| IllegalArgumentException 
	        | SecurityException  e){
	    	System.out.println("Exception: " + e.getMessage());
	    }
	}
	public static void delete(DefaultTableModel model, String tabla,String campos[], int num) {
		Connection conn = null;
		String url, user, pass;
	    try{
	    	Class.forName("oracle.jdbc.OracleDriver");
			url = "jdbc:mysql://localhost/mydb2";
			user = "root";
			pass = "1234";
			conn = DriverManager.getConnection(url, user, pass);
			if(!conn.isClosed()){
		        System.out.println("Database connection working TCP/IP...");
		        try{
		          String delete = "UPDATE "+tabla+" SET "+campos[2]+" = ? WHERE "+campos[0]+" =? ";
		          PreparedStatement Delete_Datos = conn.prepareStatement(delete);
		          Delete_Datos.setString(1, "*");
		          Delete_Datos.setInt(2, num);
		          int count = Delete_Datos.executeUpdate();
		          System.out.println("Datos Eliminado: " + count);
		          Delete_Datos.close();
		          actualizar(model, tabla);
		        }catch (SQLException e){
		          System.out.println("Exception: " + e.getMessage());
		        }
			}
	    }catch (SQLException | IllegalArgumentException 
	         | SecurityException | ClassNotFoundException e){
	    	System.out.println("Exception: " + e.getMessage());
	    }
	}
	public static void insert(String[]camposDeTexto, String[] camposNumericos , String[] texto, int [] numericos, String tabla) {
		Connection conn = null;
		String url, user, pass;
	    try{
	    	//Class.forName("oracle.jdbc.OracleDriver");
			url = "jdbc:mysql://localhost/mydb2";
			user = "root";
			pass = "1234";
			conn = DriverManager.getConnection(url, user, pass);
			if(!conn.isClosed()){
		        System.out.println("Database connection working TCP/IP...");
		        try{
		            String insertDatos = "INSERT INTO "+tabla+" (";
		            insertDatos += camposDeTexto[0];
		            for (int i = 1; i < camposDeTexto.length; i++) {
		                insertDatos +=  ", "+camposDeTexto[i];
		            }
		            for (int i = 0; i < camposNumericos.length; i++) {
		                insertDatos += ", "+camposNumericos[i];
		            }
		            insertDatos += ") VALUES (?";
		            for (int i = 0; i < (camposDeTexto.length + camposNumericos.length-1); i++) {
		                insertDatos += ",?";
		            }
		            insertDatos += ")";
		            System.out.println("insert: "+ insertDatos);
		            PreparedStatement Insert_Datos = conn.prepareStatement(insertDatos);
		            
		            int contador = 1;
		            //Insertando strings
		            for (int i = 0; i < texto.length; i++) {
		            	Insert_Datos.setString(contador, texto[i]);
		            	contador++; 
		            }
		            //insertando enteros
		            for (int i = 0; i < numericos.length; i++) {
		            	Insert_Datos.setInt(contador, numericos[i]);
		            	contador++;
		            }

		            int count = Insert_Datos.executeUpdate();
		            System.out.println("Datos Innsertados: "+ count);
		            Insert_Datos.close();
		        }catch (SQLException e){
		        	System.out.println("Exceptiddon: " + e.getMessage());
		        }
			} 
	    }catch (SQLException  e){
	    	System.out.println("Exception: " + e.getMessage());
	    }
	}
	public static String[] obtenerLista(String consulta, String NombreDelCampo) {
		
	    // Lógica para actualizar la tabla de la GUI aquí...
		Connection conn = null;
		String url, user, pass;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    String[] array = null;
	    try {
	    	Class.forName("oracle.jdbc.OracleDriver");
			url = "jdbc:mysql://localhost/mydb2";
			user = "root";
			pass = "1234";
			conn = DriverManager.getConnection(url, user, pass);
			if (!conn.isClosed()) {
				System.out.println("Database connection working TCP/IP...5555");
				try {
			        String selectQuery = consulta;
			        statement = conn.prepareStatement(selectQuery);
			        resultSet = statement.executeQuery();
			        ArrayList<String> lista = new ArrayList<>();
			        while (resultSet.next()) {
			            String campo = resultSet.getString(NombreDelCampo);
			            lista.add(campo);
			        }
			        array = lista.toArray(new String[lista.size()]);
				} catch (SQLException e) {
			        System.out.println("Exception: " + e.getMessage());
				}
			}
	    } catch (ClassNotFoundException | SQLException e) {
	    	System.out.println("Exception: " + e.getMessage());
	    } finally {
	    	try {
		        if (resultSet != null) {
		        	resultSet.close();
		        }
		        if (statement != null) {
		        	statement.close();
		        }
		        if (conn != null) {
		        	
		        	conn.close();
		        }
	    	} catch (SQLException e) {
		        System.out.println("Exception: " + e.getMessage());
	    	}
	    }
	    return array;
	  }
	public static void actualizarTabla(DefaultTableModel model, String tabla) {
		Connection conn = null;
		String url, user, pass;
	    try {
			url = "jdbc:mysql://localhost/mydb2";
			user = "root";
			pass = "1234";
			conn = DriverManager.getConnection(url, user, pass);
	        if (!conn.isClosed()) {
	            System.out.println("ConexiÃ³n a la base de datos exitosa...");
				System.out.println("PRUEBA DE QUE PASASSSSS...");
	            try {
	                String seleccion = "SELECT * FROM " + tabla;
	                Statement selectDatos = conn.createStatement();
	                ResultSet rs = selectDatos.executeQuery(seleccion);
	                int rowCount = model.getRowCount();
	                for (int i = rowCount - 1; i >= 0; i--) {
	                    model.removeRow(i);
	                }
	                while (rs.next()) {
	                    // Agregar los datos a la tabla modelo
	                    Object[] rowData = new Object[model.getColumnCount()];
	                    for (int i = 1; i <= model.getColumnCount(); i++) {
	                        rowData[i - 1] = rs.getObject(i);
	                    }
	                    model.addRow(rowData);
	                }
	                rs.close();
	                selectDatos.close();
	            } catch (SQLException e) {
	                System.out.println("Excepción: " + e.getMessage());
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("ExcepciÃ³n: " + e.getMessage());
	    }
	}

}
