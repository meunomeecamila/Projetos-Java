public class CountingSort {

    public static void countingSort(int[] arr, int maxValor) {
        int[] cont = new int[maxValor + 1];
        int[] saida = new int[arr.length];

        // conta a frequência de cada número
        for (int i = 0; i < arr.length; i++) {
            cont[arr[i]]++;
        }

        // acumula (cada posição diz quantos elementos são <= a ela)
        for (int i = 1; i <= maxValor; i++) {
            cont[i] += cont[i - 1];
        }

        // constrói array de saída (do fim pro começo para ser estável)
        for (int i = arr.length - 1; i >= 0; i--) {
            saida[cont[arr[i]] - 1] = arr[i];
            cont[arr[i]]--;
        }

        // copia de volta
        for (int i = 0; i < arr.length; i++) {
            arr[i] = saida[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 2, 8, 3, 3, 1};
        countingSort(arr, 8);

        for (int x : arr) System.out.print(x + " ");
    }
}
