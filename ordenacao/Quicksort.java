import java.util.Random;
import java.util.Arrays;

public class Quicksort {

    // Variável para contar as comparações totais
    public static long totalComparacoes = 0;

    public static void Quicksort(int esq, int dir, int[] array) {
        int i = esq, j = dir;
        int pivo = QuickSortRandomPivot(esq, dir, array); // troque aqui para testar outras estratégias

        while (true) {
            totalComparacoes++;              // conta a verificação (i <= j)
            if (i > j) break;                // última verificação que falha também é contada

            // -------- CORREÇÃO: contar TODAS as checagens dos laços internos --------
            while (true) {
                totalComparacoes++;          // conta a condição (array[i] < pivo)
                if (!(array[i] < pivo)) break;
                i++;
            }

            while (true) {
                totalComparacoes++;          // conta a condição (array[j] > pivo)
                if (!(array[j] > pivo)) break;
                j--;
            }

            totalComparacoes++;              // conta o if (i <= j)
            if (i <= j) {
                swap(i, j, array);
                i++;
                j--;
            }
        }

        if (esq < j) Quicksort(esq, j, array);  // Recursão na parte esquerda
        if (i < dir) Quicksort(i, dir, array);  // Recursão na parte direita
    }

    public static void swap(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Pivô = primeiro elemento
    public static int QuickSortFirstPivot(int esq, int dir, int[] array) {
        return array[esq];
    }

    // Pivô = último elemento
    public static int QuickSortLastPivot(int esq, int dir, int[] array) {
        return array[dir];
    }

    // Pivô = elemento aleatório entre [esq, dir]
    public static int QuickSortRandomPivot(int esq, int dir, int[] array) {
        Random random = new Random();
        int num = random.nextInt(dir - esq + 1) + esq;
        return array[num];
    }

    // Pivô = mediana entre início, meio e fim (retorna o VALOR)
    public static int QuickSortMedianOfThree(int esq, int dir, int[] array) {
        int meio = (esq + dir) / 2;

        int a = array[esq];
        int b = array[meio];
        int c = array[dir];

        if ((a >= b && a <= c) || (a <= b && a >= c)) return a;
        else if ((b >= a && b <= c) || (b <= a && b >= c)) return b;
        else return c;
    }

    public static void main(String[] args) {
        int tam = 1000; // Tamanho do vetor
        int[] vetor = new int[tam];

        // Random random = new Random(); -> para o vetor aleatório
        /*for (int i = 0; i < tam; i++) {
            vetor[i] = i + 1;
        } _. para o vetor ordenado */ 

        //vetor quase ordenado
        for (int i = 0; i < tam; i++) {
            vetor[i] = i + 1;
        }
        vetor[1] = 50;
        vetor[7] = 26;
        vetor[51] = 1;
        vetor[89] = 0;
        vetor[63] = 200;
        vetor[200] = 200;
        vetor[76] = 1;
        vetor[350] = 29;
        vetor[498] = 100;

        // Vetor antes de ordenar
        System.out.println("Vetor antes de ordenar:");
        System.out.println(Arrays.toString(vetor));

        // -------- CORREÇÃO: tempo em ns e também mostrando em ms --------
        long startTime = System.nanoTime();
        Quicksort(0, tam - 1, vetor);       // aqui você troca a função de pivô se quiser
        long endTime = System.nanoTime();

        long durationNs = endTime - startTime;
        double durationMs = durationNs / 1_000_000.0;

        // Vetor depois de ordenar
        System.out.println("Vetor depois de ordenar:");
        System.out.println(Arrays.toString(vetor));

        // Imprime o número total de comparações
        System.out.println("Número total de comparações: " + totalComparacoes);

        // Imprime o tempo de execução
        System.out.println("Tempo de execução (ns): " + durationNs);
        System.out.println("Tempo de execução (ms): " + durationMs);
    }
}
