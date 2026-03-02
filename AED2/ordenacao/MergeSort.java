public class MergeSort {

    public static void mergeSort(int[] arr, int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;

            // ordena metade esquerda
            mergeSort(arr, esq, meio);

            // ordena metade direita
            mergeSort(arr, meio + 1, dir);

            // intercala as duas metades
            merge(arr, esq, meio, dir);
        }
    }

    public static void merge(int[] arr, int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[esq + i];
        for (int j = 0; j < n2; j++) R[j] = arr[meio + 1 + j];

        int i = 0, j = 0, k = esq;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        mergeSort(arr, 0, arr.length - 1);

        for (int x : arr) System.out.print(x + " ");
    }
}
