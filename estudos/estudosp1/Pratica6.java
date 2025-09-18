import java.util.*;
public class Pratica6{

    //operação de adicionar um numero no topo
    public static int push(int topo, int num, int[] pilha){
        topo++;
        pilha[topo] = num;
        return topo;
    }

    //operação de remover o ultimo
    public static int pop(int topo){
        if(topo == -1) System.out.println("EMPTY");
        else {
            topo--;
        }
        return topo;
    }

    //operação de busca do minimo
    public static void min(int topo, int[] pilha){
        if(topo == -1) { System.out.println("EMPTY"); return;}
        int menor = pilha[topo];
        for(int i=topo-1; i>=0; i--){
            if(pilha[i] < menor){
                menor = pilha[i];
            }
        }
        System.out.println(menor);
        return;
    }

    
    public static void main(String []args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //numero de operações

        int[] pilha = new int[n];
        int topo = -1;

        while(n > 0){
            //escanear a operação e o numero
            String op = sc.next();
            //push
            if(op.charAt(0) == 'P' && op.charAt(1) == 'U'){
                int num = sc.nextInt();
                topo = push(topo,num,pilha);
            }

            //min
            else if(op.charAt(0) == 'M' && op.charAt(1) == 'I'){
                min(topo,pilha);
            }

            //pop
            else if(op.charAt(0) == 'P' && op.charAt(1) == 'O'){
                topo = pop(topo);
            }
            
            n--;
        }

        
    }
}