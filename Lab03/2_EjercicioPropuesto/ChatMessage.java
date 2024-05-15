package LabSD3;

import java.io.*;

/*
 * Esta clase define los diferentes tipos de mensajes que se intercambiarán entre
 * los clientes y el servidor.
 * Cuando se comunica desde un Cliente Java a un Servidor Java, es mucho más fácil pasar
 * objetos Java, no es necesario contar bytes ni esperar un salto de línea al final del marco.
 */
public class ChatMessage implements Serializable {
	// Los diferentes tipos de mensajes enviados por el Cliente
	// WHOISIN: Para recibir la lista de usuarios conectados
	// MESSAGE: Para un mensaje de texto ordinario
	// LOGOUT: Para desconectarse del Servidor
	static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
	private int type; // Tipo de mensaje
	private String message; // Contenido del mensaje

	// Constructor
	ChatMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}

	// Método para obtener el tipo de mensaje
	int getType() {
		return type;
	}

	// Método para obtener el contenido del mensaje
	String getMessage() {
		return message;
	}
}
