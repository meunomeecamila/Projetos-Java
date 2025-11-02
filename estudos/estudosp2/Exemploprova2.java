import java.util.*;

public class Sorveteiro {
    int inicio; 
    int fim;

    public static void main(String []args){
        Scanner sc = new Scanner(System.in);

        int p; //metros da praia
        p = sc.nextInt();
        int s; //quantidade de sorveteiros
        s = sc.nextInt();

        Sorveteiro []sorvets = new Sorveteiro[s];
            
        //ler s vezes o inicio e o fim
        //guardar no vetor
        for(int i=0; i<s; i++){
            int u; //inicio
            u = sc.nextInt();

            int v; //fim
            v = sc.nextInt();

            sorvets[i] = new Sorveteiro();
            sorvets[i].inicio = u;
            sorvets[i].fim = v;
        }

        int inicio = sorvets[0].inicio; //inicio do primeiro intervalo
        int fim = sorvets[0].fim; //fim do primeiro intervalo

        //percorrer e comparar cada sorveteiro
        for(int i=1; i<s; i++){

            //se o próximo sorveteiro começar antes do fim do grupo atual
            if(sorvets[i].inicio <= fim){
                //então há interseção e o grupo continua

                //se o fim do próximo for maior, estende o grupo
                if(sorvets[i].fim > fim){
                    fim = sorvets[i].fim;
                }
            }
            else {
                //caso contrário, não há mais interseção com o grupo atual
                //então imprime o grupo que terminou
                System.out.println(inicio + " " + fim);

                //reinicia um novo grupo com o próximo sorveteiro
                inicio = sorvets[i].inicio;
                fim = sorvets[i].fim;
            }
        }

        //após o loop, imprime o último grupo acumulado
        System.out.println(inicio + " " + fim);

        sc.close();
    }
}
