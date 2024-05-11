import java.util.ArrayList;
import java.util.List;

public class LamportClock {
    private int clock;

    public LamportClock() {
        this.clock = 0;
    }

    // Método para incrementar el reloj y devolver su valor
    public synchronized int tick() {
        this.clock++;
        return this.clock;
    }

    // Método para actualizar el reloj basado en el tiempo recibido
    public synchronized void update(int receivedTime) {
        // Asegurarse de que el reloj sea al menos tan grande como el tiempo recibido más uno
        this.clock = Math.max(this.clock, receivedTime) + 1;
    }

    // Método para obtener el tiempo actual del reloj
    public int getTime() {
        return this.clock;
    }

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        LamportClock clock = new LamportClock();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Crear un evento y obtener su tiempo Lamport
                    int time = clock.tick();

                    System.out.println("Thread " + Thread.currentThread().getId() + " created event with Lamport time " + time);

                    try {
                        // Simular alguna actividad esperando un tiempo aleatorio
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Simular la recepción de un evento y actualizar el tiempo del reloj
                    int receivedTime = clock.tick();
                    System.out.println("Thread " + Thread.currentThread().getId() + " received event with Lamport time " + receivedTime);
                    clock.update(receivedTime);
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Esperar a que todos los threads terminen
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Imprimir el tiempo final del reloj de Lamport
        System.out.println("Final Lamport time: " + clock.getTime());
    }
}

