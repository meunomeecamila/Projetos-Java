// Obs: fila implementada de forma simples pra estudos, sem throw exception
// Métodos implementados até agora: Inserir, remover e mostrar, maior, devolver o terceiro, devolver soma, inverter a ordem,
//devolver pares AND multiplos de cinco, retornar o cabeça, inserir sem o ultimo

public class FilaFlex {

    private Celula primeiro; 
    private Celula ultimo;

    public Fila(){
        primeiro = new Celula();   // nó-cabeça (sentinela)
        ultimo = primeiro;
    }

    public void inserir(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    //Exercícios resolvidos ===============
    //Ex_R01: Função de remover
    public int remover(){
        //remover da fila sempre remove o primeiro
        Celula tmp = primeiro;
        primeiro = primeiro.prox; //pula pro que não é o nó cabeça
        int elemento = primeiro.elemento; //elemento a ser removido
        tmp.prox = null;
        tmp = null; 
        return elemento;
    }

    //Ex_R02: Método de encontrar o maior
    public int maior(){
        // se a fila estiver vazia após o sentinela, devolve o menor int
        if (primeiro.prox == null) return Integer.MIN_VALUE;
        int max = primeiro.prox.elemento;
        for (Celula i = primeiro.prox.prox; i != null; i = i.prox) {
            if (i.elemento > max) max = i.elemento;
        }
        return max;
    }

    //Ex_R03: Método para retornar o terceiro elemento supondo que este existe
    public int terceiro(){
        // supondo que existe, então há pelo menos 3 nós depois do sentinela
        return primeiro.prox.prox.prox.elemento;
    }

    //Ex_R04: Método que retorna a soma dos elementos
    public int soma(){
        int s = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            s += i.elemento;
        }
        return s;
    }

    //Ex_R05: Método que inverta a ordem dos elementos
    public void inverter(){
        // reverte apenas a parte útil (depois do sentinela)
        Celula prev = null;
        Celula curr = primeiro.prox;
        while (curr != null) {
            Celula next = curr.prox;
            curr.prox = prev;
            prev = curr;
            curr = next;
        }
        // 'prev' agora é o primeiro elemento real
        primeiro.prox = prev;

        // atualiza 'ultimo'
        ultimo = primeiro;
        while (ultimo.prox != null) ultimo = ultimo.prox;
    }

    //Ex_R06: Método que conta a quantidade de pares AND multiplos de cinco
    public int contarParesEMultiplosDeCinco(){
        int cont = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            // par e múltiplo de 5 -> múltiplo de 10
            if (i.elemento % 10 == 0) cont++;
        }
        return cont;
    }

    //Ex_R07: Implemente o método Celula toFila(Celula topo) que recebe o topo de uma pilha flexível
    // e retorna o nó-cabeça de uma fila flexível com os elementos na ordem em que foram inseridos na pilha.
    public static Celula toFila(Celula topo){
        // cria fila com nó-cabeça
        Celula cabeca = new Celula();
        Celula fim = cabeca;

        // Precisamos da ordem de inserção da pilha (da base para o topo).
        // Estratégia: coleta os valores e insere em ordem reversa do percurso (topo -> base).
        java.util.ArrayList<Integer> vals = new java.util.ArrayList<>();
        for (Celula p = topo; p != null; p = p.prox) vals.add(p.elemento);

        for (int k = vals.size() - 1; k >= 0; k--) {
            fim.prox = new Celula(vals.get(k));
            fim = fim.prox;
        }
        return cabeca; // retorna o nó-cabeça da nova fila
    }

    //Ex_R08: Implemente a fila sem a existencia do ponteiro ultimo inicialmente
    // (variante de inserção caminhando até o final a cada operação)
    public void inserirSemUltimo(int x){
        Celula i = primeiro;
        while (i.prox != null) i = i.prox;
        i.prox = new Celula(x);
        // se quiser manter 'ultimo' coerente, atualiza:
        ultimo = i.prox;
    }

    //Atividades a serem feitas =============

    //Atv 01: implementação do método mostrar
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

    // construtor para nó-cabeça (sentinela)
    Celula() {}

    Celula(int x) {
        this.elemento = x;
        this.prox = null;
    }
}

// ===========================
// Atv 03 -> Fila flexível SEM nó cabeça
// ===========================
class FilaFlexSemCabeca {
    private Celula frente; // primeiro nó real
    private Celula tras;   // último nó real

    public FilaFlexSemCabeca() {
        frente = null;
        tras   = null;
    }

    // inserir no fim
    public void inserir(int x){
        Celula nova = new Celula(x);
        if (frente == null) {       // fila vazia
            frente = nova;
            tras   = nova;
        } else {
            tras.prox = nova;
            tras = nova;
        }
    }

    // remover do início
    public int remover(){
        if (frente == null) {       // sem exceptions neste projeto
            return Integer.MIN_VALUE;
        }
        int resp = frente.elemento;
        Celula tmp = frente;
        frente = frente.prox;
        if (frente == null) tras = null; // ficou vazia
        tmp.prox = null; tmp = null;
        return resp;
    }

    // mostrar do início ao fim
    public void mostrar(){
        System.out.println("Fila (sem nó cabeça):");
        for (Celula i = frente; i != null; i = i.prox) {
            System.out.println(i.elemento);
        }
    }

    // util: checar vazia
    public boolean isVazia(){
        return frente == null;
    }
}

// ===========================
// Atv 04 -> Pilha flexível SEM nó cabeça
// ===========================
class PilhaFlexSemCabeca {
    private Celula topo; // topo aponta para o primeiro nó real

    public PilhaFlexSemCabeca() {
        topo = null;
    }

    // push
    public void inserir(int x){
        Celula nova = new Celula(x);
        nova.prox = topo;
        topo = nova;
    }

    // pop
    public int remover(){
        if (topo == null) {         // sem exceptions neste projeto
            return Integer.MIN_VALUE;
        }
        int resp = topo.elemento;
        Celula tmp = topo;
        topo = topo.prox;
        tmp.prox = null; tmp = null;
        return resp;
    }

    // mostrar do topo até a base
    public void mostrar(){
        System.out.println("Pilha (sem nó cabeça):");
        for (Celula i = topo; i != null; i = i.prox) {
            System.out.println(i.elemento);
        }
    }

    // util: checar vazia
    public boolean isVazia(){
        return topo == null;
    }
}

