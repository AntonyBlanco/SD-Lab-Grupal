package LabSD3;

import java.net.*;
import java.io.*;
import java.util.*;


//El Cliente que puede ejecutarse como consola
public class Client  {
	
	// Notificación
	private String notif = " *** ";
	// para E/S
	private ObjectInputStream sInput;		// para leer del socket
	private ObjectOutputStream sOutput;		// para escribir en el socket
	private Socket socket;					// objeto de socket
	private String server, username;	// servidor y nombre de usuario
	private int port;					//puerto

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 *  Constructor para establecer las siguientes cosas
	 *  servidor: la dirección del servidor
	 *  puerto: el número de puerto
	 *  username: el nombre de usuario
	 */
	Client(String server, int port, String username) {
		this.server = server;
		this.port = port;
		this.username = username;
	}
	

	//Para iniciar el chat
	
	public boolean start() {
		// intentar conectarse al servidor
		try {
			socket = new Socket(server, port);
		} 
		// Manejador de excepciones si falla
		catch(Exception ec) {
			display("Error connecting to server:" + ec);
			return false;
		}
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		/* Creando ambos flujos de datos */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// Crea el Hilo para escuchar desde el servidor
		new ListenFromServer().start();
		// Envía nuestro nombre de usuario al servidor este es el único mensaje que enviaremos como String. Todos los demás mensajes serán objetos ChatMessage
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// éxito, informamos al llamador que funcionó
		return true;
	}
	 //Para enviar un mensaje a la consola
	private void display(String msg) {
		System.out.println(msg);
	}
	
	//Para enviar un mensaje al servidor

	void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	//Cuando algo sale mal
	//Cierra los flujos de entrada/salida y desconecta

	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {}
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {}
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {}
			
	}
	/*
	 * Para iniciar el Cliente en modo consola use uno de los siguientes comandos
	 * > java Cliente
	 * > java Cliente username
	 * > java Cliente username númeroDePuerto
	 * > java Cliente username númeroDePuerto direcciónDelServidor en el símbolo del sistema
	 * Si el númeroDePuerto no está especificado, se usa 1500
	 * Si la direcciónDelServidor no está especificada, se usa "localHost"
	 * Si el nombre de usuario no está especificado, se usa "Anónimo"
	 */
	public static void main(String[] args) {
		// valores predeterminados si no se ingresan
		int numeroDePuerto = 1500;
		String direccionDelServidor = "localhost";
		String nombreDeUsuario = "Anónimo";
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Ingrese el nombre de usuario: ");
		nombreDeUsuario = scan.nextLine();

		//Diferentes casos según la longitud de los argumentos.
		switch(args.length) {
			case 3:
				// para > javac Cliente nombreDeUsuario númeroDePuerto direcciónDelServidor
				direccionDelServidor = args[2];
			case 2:
				// para > javac Cliente nombreDeUsuario númeroDePuerto
				try {
					numeroDePuerto = Integer.parseInt(args[1]);
				}
				catch(Exception e) {
					System.out.println("Número de puerto inválido.");
					System.out.println("El uso es: > java Cliente [nombreDeUsuario] [númeroDePuerto] [direcciónDelServidor]");
					return;
				}
			case 1: 
				// para > javac Cliente nombreDeUsuario
				nombreDeUsuario = args[0];
			case 0:
				// para > java Cliente
				break;
			// si el número de argumentos es inválido
			default:
				System.out.println("El uso es: > java Cliente [nombreDeUsuario] [númeroDePuerto] [direcciónDelServidor]");
			return;
		}
		// crear el objeto Cliente
		Client cliente = new Client(direccionDelServidor, numeroDePuerto, nombreDeUsuario);
		// intentar conectarse al servidor y regresar si no está conectado
		if(!cliente.start())
			return;
		
		System.out.println("\n¡Hola.! Bienvenido al chatroom.");
		System.out.println("Instrucciones:");
		System.out.println("1. Simplemente escriba el mensaje para enviarlo a todos los clientes activos");
		System.out.println("2. Escriba '@nombreDeUsuario<espacio>suMensaje' sin comillas para enviar un mensaje al cliente deseado");
		System.out.println("3. Escriba 'WHOISIN' sin comillas para ver la lista de clientes activos");
		System.out.println("4. Escriba 'LOGOUT' sin comillas para cerrar la sesión del servidor");
		
		// bucle infinito para obtener la entrada del usuario
		while(true) {
			System.out.print("> ");
			// leer mensaje del usuario
			String msg = scan.nextLine();
			// cerrar la sesión si el mensaje es LOGOUT
			if(msg.equalsIgnoreCase("LOGOUT")) {
				cliente.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
				break;
			}
			// mensaje para verificar quiénes están presentes en la sala de chat
			else if(msg.equalsIgnoreCase("WHOISIN")) {
				cliente.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));				
			}
			// mensaje de texto regular
			else {
				cliente.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
			}
		}
		// cerrar recurso
		scan.close();
		// cliente completó su trabajo. desconectar cliente.
		cliente.disconnect();	
	}

	//Una clase que espera el mensaje del servidor

	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					// leer el mensaje del flujo de datos de entrada
					String msg = (String) sInput.readObject();
					// imprimir el mensaje
					System.out.println(msg);
					System.out.print("> ");
				}
				catch(IOException e) {
					display(notif + "El servidor ha cerrado la conexión: " + e + notif);
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
