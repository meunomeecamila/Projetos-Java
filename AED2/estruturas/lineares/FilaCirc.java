//Informações:
//estrutura em fila onde o primeiro a entrar é o primeiro a sair. Quando chega no fim, volta pro começo
//exemplo de uso: impressora, caixa
//complexidade: O(n)

public class FilaCirc {
    public static void main( String []args){
        int[] fila = new int[5];
        int inicio = 0, fim = 0, qtd = 0;

        //enqueue -> inserir (no fim)
        for(int i=0; i<5; i++){
            fila[fim] = i+1;
            //ao invés de fim++, pega o mod pra caso esteja no final, voltar pro inicio
            fim = (fim+1) % fila.length;
            qtd++;
            System.out.println("Inseriu o " + (i+1));
        }

        //dequeue -> remover (no inicio)
        for(int i=0; i<5; i++){
            int remov = fila[inicio];
            //ao invés de inicio++, pega o mod pra caso esteja no final, voltar pro inicio
            inicio = (inicio+1) % fila.length;
            qtd--;
            System.out.println("Removeu o " + remov);
        }
        
    }
}