import java.util.*;
public class Pratica2 {
    public static void main( String []args){
        Scanner sc = new Scanner(System.in);
        int n,k;
        n = sc.nextInt();
        k = sc.nextInt();

        //executar até 00
        while(n != 0 && k!=0){
            int[] vetor = new int[n]; //inicializados ja com 0
            //ler os numeros mas alocar sua frequencia
            for(int i=0; i<n; i++){
                int num;
                num = sc.nextInt();
                vetor[num-1] += 1; //aumenta a frequencia
            }

            //no final, se passar de k, contador mais mais
            int contador = 0;
            for(int i=0; i<n; i++){
                if(vetor[i] >= k) contador++;
            }

            System.out.println(contador);
            n = sc.nextInt();
            k = sc.nextInt();

        }
    }
}