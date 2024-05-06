package Anexo2;

public class Productor extends Thread {
	// Atributos
	private CubbyHole cubbyhole; // Referencia al almacén compartido
	private int numero; // Número identificador del productor

	// Constructor
	public Productor(CubbyHole c, int numero) {
		cubbyhole = c; // Asigna el almacén compartido
		this.numero = numero; // Asigna el número identificador del productor
	}

	// Método run() que se ejecutará cuando el hilo del productor comience
	public void run() {
		// Bucle para producir 10 valores
		for (int i = 0; i < 10; i++) {
			cubbyhole.put(i); // Coloca un valor en el almacén compartido utilizando el método put()
			System.out.println("Productor #" + this.numero + " pone: " + i); // Imprime un mensaje indicando el valor producido y el número de productor
			try {
				sleep((int)(Math.random() * 100)); // El hilo se duerme durante un tiempo aleatorio
			} catch (InterruptedException e) { } // Captura y maneja la excepción InterruptedException
		}
	}
}
