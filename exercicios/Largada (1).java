import java.util.*;
public class Largada {
    public static void main( String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()){
        int n = sc.nextInt();

        int[] largada = new int[n];
        int[] chegada = new int[n];

        //pegar os valores
        for (int i = 0; i < n; i++) largada[i] = sc.nextInt();
        for (int i = 0; i < n; i++) chegada[i] = sc.nextInt();

        //variável maxId que vai guardar o maior número de identificação de carro encontrado no vetor largada
        //percorre todos os elementos do vetor largada
        //em cada volta recebe o valor de um carro q largou
        int maxId = 0;
        for (int x : largada) if (x > maxId) maxId = x;

        int[] pos = new int[maxId + 1];     // pos[carro] = onde largou
        for (int i = 0; i < n; i++) pos[largada[i]] = i;

        long ultrapassagens = 0;
        for (int i = 0; i < n; i++) {
            int a = chegada[i];
            for (int j = i + 1; j < n; j++) {
                int b = chegada[j];
                if (pos[a] > pos[b]) ultrapassagens++; // par invertido
            }
        }

        System.out.println(ultrapassagens);
        }
    }
}
