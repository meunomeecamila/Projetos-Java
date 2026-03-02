public class HeapSort {

    public static void heapSort(int[] arr) {
        int n = arr.length;

        // constroi heap (reorganiza o array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // extrai elementos da heap um por um
        for (int i = n - 1; i > 0; i--) {
            // move raiz (maior elemento) para o fim
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // chama heapify na heap reduzida
            heapify(arr, i, 0);
        }
    }

    // garante a propriedade de max-heap
    public static void heapify(int[] arr, int n, int i) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        if (esq < n && arr[esq] > arr[maior]) maior = esq;
        if (dir < n && arr[dir] > arr[maior]) maior = dir;

        if (maior != i) {
            int troca = arr[i];
            arr[i] = arr[maior];
            arr[maior] = troca;

            // recursivamente aplica no sub-heap
            heapify(arr, n, maior);
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        heapSort(arr);

        for (int x : arr) System.out.print(x + " ");
    }
}
