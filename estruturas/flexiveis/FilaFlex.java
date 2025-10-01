// Obs: fila implementada de forma simples pra estudos, sem throw exception
// Métodos implementados até agora: Inserir, remover e mostrar

public class FilaFlex {

    private Celula primeiro; 
    private Celula ultimo;

    public Fila(){
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserir(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public int remover(){
        //remover da fila sempre remove o primeiro
        Celula tmp = primeiro;
        primeiro = primeiro.prox; //pula pro que não é o nó cabeça
        int elemento = primeiro.elemento; //elemento a ser removido
        tmp.prox = null;
        tmp = null; 
        return elemento;
    }

    public void mostrar(){
        //mostrar os elementos
        System.out.println("Início da remoção: ");
        for(Celula i = primeiro.prox; i != null; i=i.prox){
            System.out.println(i.elemento);
        }
    }

}

class Celula {
    int elemento;
    Celula prox;

    Celula(int x) {
        this.elemento = x;
        this.prox = null;
    }
}
