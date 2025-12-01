import java.util.*;

public class Protocolo {

    //função de comparar
    public static boolean compara(String a, String b){
        int aa = Integer.parseInt(a);
        int bb = Integer.parseInt(b);

        if(aa > bb) return true;
        else return false;
    }
    
    //função de swap
    public static void swap(String []vetor, int a, int b){
        String tmp = vetor[a];
        vetor[a] = vetor[b];
        vetor[b] = tmp;
    }
    
    
    public static void main(String []args){
        Scanner sc = new Scanner(System.in);
        int bit = sc.nextInt();

        while(bit == 1){
            //escanear os pacotes
            String []vetor = new String[1000];
            int i=0;

            //ler os pacotes
            while(sc.hasNext()){
                if(sc.hasNextInt()) {
                    int num = sc.nextInt();
                    bit = 0;
                    break;
                }
                String naoligo = sc.next(); //ignora a palavra "Package"
                String pacote = sc.next();
                vetor[i] = pacote;
                i++;
            }

            //acabaram os pacotes, ordená-los
            for(int j=0; j<i-1; j++){
                for(int k=0; k < i-1-j; k++){
                    if(compara(vetor[k], vetor[k+1]) == true){
                        swap(vetor, k, k+1);
                    }
                }
            }

            //depois que ordenar, printar
            for(int a=0; a<i; a++){
                System.out.println("Package " + vetor[a]);
            }

            System.out.println();

            
            if(sc.hasNext()) bit = 1;
        }
        
        //sc.close;
    }
}
