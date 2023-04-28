package util;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AVL<Integer> avl = new AVL<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });

        Scanner reader = new Scanner(System.in);
        int numCases = reader.nextInt();
        for (int i = 0; i < numCases; i++) {
            int action = reader.nextInt();
            int value = reader.nextInt();

            if (action == 1) {//agregar
                avl.add(value);
                System.out.println(avl.printByLevel());
            } else { //es 2 entonces eliminar
                avl.delete(value);
                System.out.println(avl.printByLevel());
            }

            reader.nextLine(); //Para terminar de leer la linea
        }
    }
}
