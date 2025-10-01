public class PilhaFlex {
    private Celula topo;

    public Pilha() {
        topo = null;
    }


    //Atv 07 - inserir de uma pilha
    public void inserir(int x) {
        Celula tmp = new Celula(x);
        tmp.prox = topo;
        topo = tmp;
        tmp = null;
    }

    public int remover() throws Exception {
        if (topo == null) throw new Exception("Erro ao remover!");
        int resp = topo.elemento;
        Celula tmp = topo;
        topo = topo.prox;
        tmp.prox = null;
        tmp = null;
        return resp;
    }

    public void mostrar() {
        System.out.print("[ ");
        for (Celula i = topo; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public int getMax() throws Exception {
        if (topo == null) throw new Exception("Pilha vazia");
        int max = topo.elemento;
        for (Celula i = topo.prox; i != null; i = i.prox) {
            if (i.elemento > max) max = i.elemento;
        }
        return max;
    }

    public void mostraPilha() {
        mostraPilha(topo);
    }

    private void mostraPilha(Celula i) {
        if (i != null) {
            mostraPilha(i.prox);
            System.out.println(i.elemento);
        }
    }

    public int somaIterativa() {
        int soma = 0;
        for (Celula i = topo; i != null; i = i.prox) {
            soma += i.elemento;
        }
        return soma;
    }

    //Atv 01 - método recursivo que retorna os elementos
    public int somaRecursiva() {
        //método apenas chama o privado
        return somaRecursiva(topo);
    }
    
    private int somaRecursiva(Celula i) {
        int soma = 0;
        if(i == null) return 0;
        else return i.elemento + somaRecursiva(i.prox);
    }

    //Atv 02 - método iterativo que retorna o maior elemento
    public int getMaiorIterativo() throws Exception {
        return maiorIterativo();
    }
    
    private int maiorIterativo() throws Exception {
        if (topo == null) throw new Exception("Pilha vazia");
        int maior = topo.elemento;
        for(Celula i = topo.prox; i!= null; i=i.prox){
            if(i.elemento > maior) maior = i.elemento;
        }
        return maior;
    }

    //Atv 03 - método recursivo que retorna o maior elemento
    public int getMaiorRecursivo() throws Exception {
        if (topo == null) throw new Exception("Pilha vazia");
        return maiorRecursivo(topo);
    }

    private int maiorRecursivo(Celula i) throws Exception{
        if(i.prox == null) return i.elemento; //se chegou no ultimo, retorna seu valor
        int maior = maiorRecursivo(i.prox); //recursivo
        if(i.elemento > maior){ //comparacao
            return i.elemento;
        } 
        else return maior;
    }

    //Atv 04 - método recursivo que mostra os elementos na ordem de remoção
    public void getMostrarRemocao() throws Exception {
        if (topo == null) throw new Exception("Pilha vazia");
        mostrarRemocao(topo);
        System.out.println("Fim da Remoção");
    }

    private void mostrarRemocao(Celula i) throws Exception {
        if(i == null) return;
        else{
            System.out.println("Removido: " + i.elemento);
            //remover();
            mostrarRemocao(i.prox);
        }
    }

    //Atv 05 - método recursivo que mostra os elementos na ordem de inserção
    //obs: imprimir na volta da recursão
    public void getMostrarInsercao() throws Exception {
        if (topo == null) throw new Exception("Pilha vazia");
        mostrarInsercao(topo);
        System.out.println("Fim da Inserção");
    }

    private void mostrarInsercao(Celula i) throws Exception {
        if(i == null) return;
        else{
            mostrarInsercao(i.prox);
            //inserir();
            System.out.println("Inserido: " + i.elemento);
        }
    }

    //Atv 06 - método iterativo que mostra os elementos na ordem de inserção
    public int getTam(){
        int tam = 0;
        for(Celula i = topo; i != null; i=i.prox) tam++;
        return tam;
    }

    public void getMostrarInsercaoI() throws Exception{
        if (topo == null) throw new Exception("Pilha vazia");
        mostrarInsercaoI();
        System.out.println("Fim da Inserção");
    }

    private void mostrarInsercaoI() throws Exception {
        //criar um novo vetor
        int n = getTam();
        int []vetor = new int[n];
        int j=0; 

        //guardar elementos em um vetor
        for(Celula i = topo; i != null; i=i.prox){
            vetor[j] = i.elemento; 
            j++;
        }

        //imprimir o vetor de trás pra frente
        for(int k=n-1; k>=0; k--){
            System.out.println("Inserido: " + vetor[k]);
        }
    }
    

    public static void main(String[] args) throws Exception {
        Pilha p = new Pilha(); //cria o ponteiro e atribui ele a um novo objeto da classe

        System.out.println("Inserir elementos");
        p.inserir(3);
        p.inserir(1);
        p.inserir(5);
        p.inserir(2);
        p.mostrar();    

        p.getMostrarInsercaoI();
        //System.out.println(p.getMostrarRemocao());
    }
}

class Celula {
    int elemento;
    Celula prox;


    //Atv 07 - Construtor de uma pilha
    Celula(int x) {
        this.elemento = x;
        this.prox = null;
    }
}
