//Informações:
//estrutura em fila onde o primeiro a entrar é o primeiro a sair
//exemplo de uso: impressora, caixa
//complexidade: O(n) inserir/remover x O(n) organizar = O(n ao quadrado)

public class Fila {
    public static void main(String []args){
        int[] fila = new int[5];
        int tamanho = 0;

        //enqueue -> inserir (no fim)
        for(int i=0; i<5; i++){
            //obs: podemos adicionar uma condição de caso esteja cheia
            fila[tamanho] = i+1; //numero inserido
            tamanho++;
            System.out.println("Inseriu o " + (i+1));
        }

        //dequeue -> remover (no inicio)
        for(int k=0; k<5; k++){
            //obs: podemos adicionar uma condição de caso esteja vazia
            int remov = fila[0]; //removeu o 1
            tamanho--;
            System.out.println("Removeu o " + remov);

            // desloca todo mundo 1 posição para a esquerda
            for (int i = 0; i < tamanho; i++) {
                fila[i] = fila[i+1];
            }
        }
   
    }
}