//Informações ===== Usada para ordenar
//Ideia: encontra o menor elemento e coloca na posição correta; repete até acabar.
//Complexidade: theta de n ao quadrado para comparações e theta de n para movimentações

import java.util.Arrays;  

public class Selecao{
    public static void main(String []args){
        //Scanner scanner = new Scanner(System.in);

        int[] vetor = new int[10]; // vetor de inteiros com 10 posições
        //Percorrer o vetor colocando numeros aleatórios
        for (int i = 0; i < vetor.length; i++) vetor[i] = (int)(Math.random() * 100); //gera entre 0 e 99

        int contadorcomp = 0;
        int contadormov = 0;

        int n = vetor.length;

        //Algoritmo de seleção
        for(int i=0; i<(n-1); i++){ //faz até n-1 pois o ultimo estará ordenado
            int menor = i; //procura o menor indice da parte nao ordenada
            for(int j=i+1; j<n; j++){ //aqui percorre todos a partir do menor
                contadorcomp++;
                if(vetor[menor] > vetor[j]){
                    menor = j;
                }
            }
            System.out.println(Arrays.toString(vetor));
            contadormov += 3; //3 movimentos
            if(menor != i) swap(vetor, menor, i);
        }

        System.out.println(contadorcomp);
        System.out.println(contadormov);
        System.out.println(Arrays.toString(vetor));

    }

    // método swap
    public static void swap(int[] v, int i, int j) {
        int temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }
}