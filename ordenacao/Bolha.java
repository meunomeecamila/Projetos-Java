//Informações ===== Usada para ordenar
//Ideia: compara dois e sobe o maior até o final
//Complexidade: theta de n ao quadrado para comparações e theta de n ao quadrado para movimentações

import java.util.Arrays;

public class Bolha {
    public static void main (String []args){

        int[] vetor = new int[10];
        int n = vetor.length;
        for (int i = 0; i < n; i++) vetor[i] = (int)(Math.random() * 100); //gera entre 0 e 99

        for(int i=0; i<n-1; i++){ //para cada item do vetor, compara com vizinhos
            for(int j=0; j<n-1-i; j++){
                if (vetor[j] > vetor[j+1]) {
                    //compara e troca, compara e troca...
                    swap(vetor, j, j+1);
                }
                System.out.println(Arrays.toString(vetor));
            }
        }

    }

    // método swap
    public static void swap(int[] v, int i, int j) {
        int temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }
}