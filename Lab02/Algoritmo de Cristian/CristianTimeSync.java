// Para manejar operaciones relacionadas con el tiempo
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CristianTimeSync {
    // Método que simula la solicitud de tiempo al servidor
    public static Instant getTimeFromServer() {
        // Simulamos una respuesta del servidor con el tiempo actual
        return Instant.now();
    }

    // Método para calcular la diferencia de tiempo entre el servidor y el cliente
    public static long calculateTimeDifference(Instant serverTime, Instant clientTime) {
        // Calculamos la diferencia de tiempo entre el servidor y el cliente
        return ChronoUnit.MILLIS.between(serverTime, clientTime);
    }

    // Método para sincronizar el reloj del cliente con el tiempo del servidor
    public static Instant synchronizeClientClock(Instant serverTime, long timeDifference) {
        // Ajustamos el tiempo del cliente sumando la diferencia calculada
        return serverTime.plusMillis(timeDifference);
    }

    public static void main(String[] args) {
        // Simulamos una solicitud de tiempo al servidor
        Instant serverTime = getTimeFromServer();
        System.out.println("Tiempo del servidor: " + serverTime);

        // Simulamos el tiempo local del cliente
        Instant clientTime = Instant.now();
        System.out.println("Tiempo del cliente (antes de sincronización): " + clientTime);

        // Calculamos la diferencia de tiempo entre el servidor y el cliente
        long timeDifference = calculateTimeDifference(serverTime, clientTime);
        System.out.println("Diferencia de tiempo entre servidor y cliente: " + timeDifference + " milisegundos");

        // Sincronizamos el reloj del cliente con el tiempo del servidor
        Instant synchronizedTime = synchronizeClientClock(serverTime, timeDifference);
        System.out.println("Tiempo del cliente (después de sincronización): " + synchronizedTime);
    }
}
