//Informações:
//estrutura em stack onde o último a entrar é o primeiro a sair
//exemplo de uso: ctrl+z (desfazer ações)
//complexidade: O(n)

public class Pilha {
    public static void main(String []args){
        int[] pilha = new int[5];
        int topo = -1;

        //push -> inserir 1,2,3,4,5
        for(int i=0; i<5; i++){
            topo++;
            pilha[topo] = i+1; 
            System.out.println("Inseriu o " + (i+1));
        }

        //pop -> remover
        for(int i=0; i<5; i++){
            int remov = pilha[topo];
            topo--;
            System.out.println("Removeu o " + remov);
            //nesse caso é possível retornar o removido
        }
    }
}