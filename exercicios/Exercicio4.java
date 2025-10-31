public class Celula {
    public int elemento;
    public Celula prox;

    //construtor
    public Celula(int x){
        this(x,null);
    }

    public Celula(int x, Celula prox){
        this.elemento = x;
        this.prox = prox;
    }
}

public class Lista {
    public Celula primeiro;
    public Celula ultimo;

    public Lista(){
        this.primeiro = this.ultimo = new Celula(0); //no cabeca
    }
}

public class CelulaMat {
    public int elemento;
    public CelulaMat ant;
    public CelulaMat prox;
    public CelulaMat inf;
    public CelulaMat sup;
    public Lista lista; // cada célula tem sua própria lista

    CelulaMat(int x){
        this.elemento = x;
        this.ant = this.prox = this.inf = this.sup = null;
        this.lista = new Lista(); // cria uma nova lista para essa célula
    }
}

public class Matriz {
    public CelulaMat inicio;
    public Lista lista;

    public Matriz(){
        this.inicio = new CelulaMat(0); //nó cabeça
        this.lista = new Lista();
    }

    public void construir(int l, int c){
        //criar a primeira linha
        CelulaMat j = inicio; //ja criado no construtor

        for(int k = 1; k<c; k++){
            //percorrer as linhas criando e conectando
            j.prox = new CelulaMat(0);
            j.prox.ant = j; //conecta o ant 

            j=j.prox;
        }

        //criar as demais linhas
        CelulaMat cima = inicio;

        for(int k=1; k<l; k++){
            CelulaMat baixo = new CelulaMat(0);
            cima.inf = baixo;
            baixo.sup = cima;

            //ligar horizontalmente
            CelulaMat a = baixo;
            CelulaMat b = cima.prox;

            for(int x=1; x<c; x++){
                a.prox = new CelulaMat(0);
                a.prox.ant = a;
                a = a.prox;

                a.sup = b;
                b.inf = a;
                b = b.prox;
            }

            cima = cima.inf;
        }
    }
}













