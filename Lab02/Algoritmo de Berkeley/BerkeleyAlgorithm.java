import java.util.ArrayList;
import java.util.List;

public class BerkeleyAlgorithm {

    public static void main(String[] args) {
        List<Integer> clocks = new ArrayList<>();
        clocks.add(5); // Ejemplo de reloj del nodo 1
        clocks.add(7); // Ejemplo de reloj del nodo 2
        clocks.add(9); // Ejemplo de reloj del nodo 3
        
        synchronizeClocks(clocks);
        
        System.out.println("Relojes sincronizados:");
        for (int i = 0; i < clocks.size(); i++) {
            System.out.println("Nodo " + (i+1) + ": " + clocks.get(i));
        }
    }

    public static void synchronizeClocks(List<Integer> clocks) {
        int sum = 0;
        for (int clock : clocks) {
            sum += clock;
        }
        int average = sum / clocks.size();

        // Ajustar los relojes de cada nodo
        for (int i = 0; i < clocks.size(); i++) {
            int adjustedTime = clocks.get(i) + (average - clocks.get(i)) / 2;
            clocks.set(i, adjustedTime);
        }
    }
}
