public class CubbyHoleSinSincronizacion {
    private int contents; // Variable para almacenar el valor en el almacén
    private boolean available = false; // Indica si el almacén tiene contenido disponible

    // Método para poner un valor en el almacén
    public void put(int value) {
        contents = value; // Coloca el valor en el almacén sin verificar si está disponible o no
        available = true; // Marca el contenido como disponible
    }

    // Método para obtener un valor del almacén
    public int get() {
        while (!available) {
            // Espera hasta que el productor ponga un valor en el almacén
        }
        available = false; // Marca el contenido como no disponible
        return contents; // Retorna el valor obtenido
    }
}
