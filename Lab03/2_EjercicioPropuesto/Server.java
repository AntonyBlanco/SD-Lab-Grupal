package LabSD3;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

// El servidor que se puede ejecutar como consola
public class Server {
	// Un ID único para cada conexión
	private static int uniqueId;
	// un ArrayList para mantener la lista de Clientes
	private ArrayList<ClientThread> al;
	// para mostrar la hora
	private SimpleDateFormat sdf;
	// el número de puerto para escuchar conexiones
	private int port;
	// para verificar si el servidor está en funcionamiento
	private boolean keepGoing;
	// notificación
	private String notif = " *** ";
	
	// Constructor que recibe el puerto para escuchar conexiones como parámetro
	
	public Server(int port) {
		// el puerto
		this.port = port;
		// para mostrar hh:mm:ss
		sdf = new SimpleDateFormat("HH:mm:ss");
		// un ArrayList para mantener la lista de Clientes
		al = new ArrayList<ClientThread>();
	}
	
	public void start() {
		keepGoing = true;
		// crear socket servidor y esperar solicitudes de conexión 
		try 
		{
			// el socket utilizado por el servidor
			ServerSocket serverSocket = new ServerSocket(port);

			// bucle infinito para esperar conexiones (hasta que el servidor esté activo)
			while(keepGoing) 
			{
				display("Server waiting for Clients on port " + port + ".");
				
				// aceptar conexión si es solicitada por el cliente
				Socket socket = serverSocket.accept();
				// romper si el servidor se detiene
				if(!keepGoing)
					break;
				// si el cliente está conectado, crear su hilo
				ClientThread t = new ClientThread(socket);
				// agregar este cliente a la lista
				al.add(t);
				
				t.start();
			}
			// intentar detener el servidor
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
					// cerrar todos los flujos de datos y el socket
					tc.sInput.close();
					tc.sOutput.close();
					tc.socket.close();
					}
					catch(IOException ioE) {
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}
	
	// para detener el servidor
	protected void stop() {
		keepGoing = false;
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
		}
	}
	
	// Mostrar un evento en la consola
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		System.out.println(time);
	}
	
	// para transmitir un mensaje a todos los Clientes
	private synchronized boolean broadcast(String message) {
		// agregar marca de tiempo al mensaje
		String time = sdf.format(new Date());
		
		// para verificar si el mensaje es privado, es decir, mensaje de cliente a cliente
		String[] w = message.split(" ",3);
		
		boolean isPrivate = false;
		if(w[1].charAt(0)=='@') 
			isPrivate=true;
		
		
		// si es un mensaje privado, enviar el mensaje solo al nombre de usuario mencionado
		if(isPrivate==true)
		{
			String tocheck=w[1].substring(1, w[1].length());
			
			message=w[0]+w[2];
			String messageLf = time + " " + message + "\n";
			boolean found=false;
			// recorremos en orden inverso para encontrar el nombre de usuario mencionado
			for(int y=al.size(); --y>=0;)
			{
				ClientThread ct1=al.get(y);
				String check=ct1.getUsername();
				if(check.equals(tocheck))
				{
					// intentar escribir en el Cliente, si falla, eliminarlo de la lista
					if(!ct1.writeMsg(messageLf)) {
						al.remove(y);
						display("Disconnected Client " + ct1.username + " removed from list.");
					}
					// nombre de usuario encontrado y mensaje entregado
					found=true;
					break;
				}
				
				
				
			}
			// usuario mencionado no encontrado, devolver false
			if(found!=true)
			{
				return false; 
			}
		}
		// si el mensaje es un mensaje de difusión
		else
		{
			String messageLf = time + " " + message + "\n";
			// mostrar mensaje
			System.out.print(messageLf);
			
			// recorremos en orden inverso en caso de que tengamos que eliminar un Cliente
			// porque se ha desconectado
			for(int i = al.size(); --i >= 0;) {
				ClientThread ct = al.get(i);
				// intentar escribir en el Cliente, si falla, eliminarlo de la lista
				if(!ct.writeMsg(messageLf)) {
					al.remove(i);
					display("Disconnected Client " + ct.username + " removed from list.");
				}
			}
		}
		return true;
		
		
	}

	// si el cliente envió un mensaje de LOGOUT para salir
	synchronized void remove(int id) {
		
		String disconnectedClient = "";
		// escaneamos la lista de arrays hasta que encontramos el ID
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// si se encuentra, eliminarlo
			if(ct.id == id) {
				disconnectedClient = ct.getUsername();
				al.remove(i);
				break;
			}
		}
		broadcast(notif + disconnectedClient + " has left the chat room." + notif);
	}
	
	/*
	 *  Para ejecutar como una aplicación de consola
	 * > java Server
	 * > java Server portNumber
	 * Si el número de puerto no se especifica, se usa 1500
	 */ 
	public static void main(String[] args) {
		// iniciar el servidor en el puerto 1500 a menos que se especifique un número de puerto
		int portNumber = 1500;
		switch(args.length) {
			case 1:
				try {
					portNumber = Integer.parseInt(args[0]);
				}
				catch(Exception e) {
					System.out.println("Número de puerto inválido.");
					System.out.println("El uso es: > java Server [portNumber]");
					return;
				}
			case 0:
				break;
			default:
				System.out.println("El uso es: > java Server [portNumber]");
				return;
				
		}
		// crear un objeto de servidor y iniciarlo
		Server server = new Server(portNumber);
		server.start();
	}

	// Una instancia de este hilo se ejecutará para cada cliente
	class ClientThread extends Thread {
		// el socket para recibir mensajes del cliente
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// mi ID único (más fácil para desconexión)
		int id;
		// el nombre de usuario del Cliente
		String username;
		// objeto de mensaje para recibir mensaje y su tipo
		ChatMessage cm;
		// marca de tiempo
		String date;

		// Constructor
		ClientThread(Socket socket) {
			// un ID único
			id = ++uniqueId;
			this.socket = socket;
			// Crear ambos flujos de datos
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				// leer el nombre de usuario
				username = (String) sInput.readObject();
				broadcast(notif + username + " has joined the chat room." + notif);
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			catch (ClassNotFoundException e) {
			}
            date = new Date().toString() + "\n";
		}
		
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		// bucle infinito para leer y reenviar mensaje
		public void run() {
			// para hacer un bucle hasta LOGOUT
			boolean keepGoing = true;
			while(keepGoing) {
				// leer un String (que es un objeto)
				try {
					cm = (ChatMessage) sInput.readObject();
				}
				catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				// obtener el mensaje del objeto ChatMessage recibido
				String message = cm.getMessage();

				// diferentes acciones basadas en el tipo de mensaje
				switch(cm.getType()) {

				case ChatMessage.MESSAGE:
					boolean confirmation =  broadcast(username + ": " + message);
					if(confirmation==false){
						String msg = notif + "Lo siento. No existe ese usuario." + notif;
						writeMsg(msg);
					}
					break;
				case ChatMessage.LOGOUT:
					display(username + " disconnected with a LOGOUT message.");
					keepGoing = false;
					break;
				case ChatMessage.WHOISIN:
					writeMsg("Lista de usuarios conectados en " + sdf.format(new Date()) + "\n");
					// enviar lista de clientes activos
					for(int i = 0; i < al.size(); ++i) {
						ClientThread ct = al.get(i);
						writeMsg((i+1) + ") " + ct.username + " desde " + ct.date);
					}
					break;
				}
			}
			// si sale del bucle, se desconecta y se elimina de la lista de clientes
			remove(id);
			close();
		}
		
		// cerrar todo
		private void close() {
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}

		// escribir un String en el flujo de salida del Cliente
		private boolean writeMsg(String msg) {
			// si el Cliente aún está conectado, enviar el mensaje a él
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// escribir el mensaje en el flujo
			try {
				sOutput.writeObject(msg);
			}
			// si ocurre un error, no abortar solo informar al usuario
			catch(IOException e) {
				display(notif + "Error enviando mensaje a " + username + notif);
				display(e.toString());
			}
			return true;
		}
	}
}
