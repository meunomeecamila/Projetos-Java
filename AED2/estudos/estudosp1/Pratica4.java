import java.io.IOException;
import java.util.*;

public class Pratica4 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        while(n != 0){
            //fazer os vetores
            int[] vetor = new int[n];
            int[] descart = new int[n-1];
            int dcount = 0;
            int remain = 0;

            //montar a pilha
            for(int i=0; i<n; i++){
                int a = i+1;
                vetor[i] = a;
            }

            int tam = n;

            //fazer a operação
            while(tam >= 2){
                //jogar fora a carta do topo = primeira carta
                descart[dcount] = vetor[0];
                dcount++;

                //mover todo mundo um pra frente
                for(int j=0; j<tam-1; j++){
                    vetor[j] = vetor[j+1];
                }

                //tirar o vetor de cima e colocar la embaixo
                int baixo = vetor[0];
                int j;
                for(j=0; j<tam-2; j++){
                    vetor[j] = vetor[j+1];
                }

                vetor[j] = baixo;
                tam--;

            }

            //no final, oq sobrou será o remain
            remain = vetor[0];

            //printar oq descartou
            System.out.print("Discarded cards:");
            for(int i=0; i<dcount; i++){
                System.out.print(" " + descart[i]);
                if(i != dcount-1) System.out.print(",");
            }
            System.out.println();

            //printar oq sobrou
            System.out.print("Remaining card: " + remain);
            System.out.println();
            

            n = sc.nextInt();
        }
 
    }
 
}