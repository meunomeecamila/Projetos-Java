public class ShellsortSimples {

    public static void shellsort(int[] array) {
        int n = array.length;

        // Começa com um gap (intervalo) grande e vai diminuindo
        for (int gap = n / 2; gap > 0; gap /= 2) {

            // Faz a ordenação por inserção, mas com o intervalo "gap"
            for (int i = gap; i < n; i++) {
                int temp = array[i];
                int j = i;

                // Move os elementos que estão "gap" posições atrás
                while (j >= gap && array[j - gap] > temp) {
                    array[j] = array[j - gap];
                    j -= gap;
                }

                array[j] = temp;
            }
        }
    }

    // Teste simples
    public static void main(String[] args) {
        int[] v = {9, 8, 3, 7, 5, 6, 4, 1};
        shellsort(v);
        for (int num : v) {
            System.out.print(num + " ");
        }
    }
}
