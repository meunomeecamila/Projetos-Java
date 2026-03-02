import java.util.Arrays;  

public class Insercao {
    public static void main (String[] args){
        
        int[] vetor = new int[10];
        int n = vetor.length;
        for (int i = 0; i < n; i++) vetor[i] = (int)(Math.random() * 100); // gera entre 0 e 99

        System.out.println("Vetor inicial:");
        System.out.println(Arrays.toString(vetor));

        // Algoritmo de Inserção em ordem decrescente
        for (int i = 1; i < n; i++) {
            int chave = vetor[i];
            int j = i - 1;

            // aqui só muda o sinal: < em vez de >
            while (j >= 0 && vetor[j] < chave) {
                vetor[j + 1] = vetor[j];
                j--;
            }
            vetor[j + 1] = chave; // insere a chave na posição correta

            System.out.println(Arrays.toString(vetor)); // debug passo a passo
        }

        System.out.println("Ordenado (decrescente):");
        System.out.println(Arrays.toString(vetor));
    }
}
