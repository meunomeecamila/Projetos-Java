//Informações:
//estrutura em lista sequencial (array)
//permite acesso direto a qualquer posição pelo índice
//exemplo de uso: nomes de chamada, vetores
//complexidade: O(n)

public class Lista {
    public static void main(String []args){
        int[] lista = {10,20,30,40};
        int tamanho = lista.length;
        
        for(int i=0; i<lista.length; i++){
            System.out.println(lista[i]);
        }

        // queremos remover o elemento da posição 2 (que é o 30)
        int pos = 2;
        int removido = lista[pos];

        // desloca os elementos da direita uma posição para a esquerda
        for (int j = pos; j < tamanho - 1; j++) {
            lista[j] = lista[j + 1];
        }
        
        tamanho--;
    }
}