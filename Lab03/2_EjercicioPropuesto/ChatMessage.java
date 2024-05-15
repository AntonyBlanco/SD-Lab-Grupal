package LabSD3;

import java.io.*;
/*
 * Esta clase define los diferentes tipos de mensajes que se intercambiarán entre
 * los Clientes y el Servidor.
 * Cuando se comunica desde un Cliente Java a un Servidor Java, es mucho más fácil pasar objetos Java,
 * no es necesario contar bytes ni esperar un avance de línea al final del mensaje.
 */

public class ChatMessage implements Serializable {

	// Los diferentes tipos de mensajes enviados por el Cliente
	// WHOISIN para recibir la lista de usuarios conectados
	// MESSAGE un mensaje de texto ordinario
	// LOGOUT para desconectarse del Servidor
	static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
	private int type;
	private String message;
	
	// Constructor
	ChatMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	int getType() {
		return type;
	}

	String getMessage() {
		return message;
	}
}
