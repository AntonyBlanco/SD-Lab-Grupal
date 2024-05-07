package Anexo2;

public class Consumidor extends Thread {
	// Atributos
	private CubbyHole cubbyhole; // Referencia al almacén compartido
	private int numero; // Número identificador del consumidor

	// Constructor
	public Consumidor(CubbyHole c, int numero) {
		cubbyhole = c; // Asigna el almacén compartido
		this.numero = numero; // Asigna el número identificador del consumidor
	}

	// Método run() que se ejecutará cuando el hilo del consumidor comience
	public void run() {
		int value = 0; // Variable para almacenar el valor obtenido del almacén
		// Bucle para consumir 10 valores del almacén
		for (int i = 0; i < 10; i++) {
			value = cubbyhole.get(); // Obtiene un valor del almacén compartido utilizando el método get()
			// Imprime un mensaje indicando el valor obtenido y el número de consumidor
			System.out.println("Consumidor #" + this.numero + " obtiene: " + value);
		}
	}
}
