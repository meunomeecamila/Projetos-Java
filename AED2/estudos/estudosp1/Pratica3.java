import java.io.IOException;
import java.util.*;

public class Pratica3 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //numero de rankings disponiveis
        int m = sc.nextInt(); //numero de jogadores em cada ranking

        while(m != 0 && n!=0){

        //criar um vetor de até 10000 espaços e a matriz
        int[] freq = new int[10001];
        
        //percorrer colocando a frequencia de aparecimento de cada jogador por semana
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                int num = sc.nextInt();
                freq[num] += 1;
            }
        }
        
        //analisar a maior frequencia e a segunda maior percorrendo o vetor
        int max1 = 0;
        for (int id = 1; id <= 10000; id++) {
            if (freq[id] > max1) max1 = freq[id];
        }

        int max2 = 0;
        for (int id = 1; id <= 10000; id++) {
            if (freq[id] < max1 && freq[id] > max2) {
            max2 = freq[id];
            }
        }

        //agora que temos a frequencia do max2, imprimir todos os valores com essa frequencia
        for(int i=0; i<10001; i++){
            if(freq[i] == max2) System.out.print(i + " ");
        }
        System.out.println();

        n = sc.nextInt();
        m = sc.nextInt();

        }


        sc.close();
    }
 
}