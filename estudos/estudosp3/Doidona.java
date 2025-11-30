//ESTRUTURA DOIDONA ==================
//Fases da estrutura
//*Fase 1 (T1): Hash normal com função de mapeamento 
//*Fase 2 (T2): Se der colisão, faz % 3 que vai dar 0,1 ou 2. 
//   caso seja 0, vai para a T3: Hash normal com área de reserva em Árvore 1
//   caso seja 1, vai para uma lista flexível
//   caso seja 2, vai para Árvore 2

//Fazer as estruturas

public class T1 {
    public int [] tabela;
    public int tam; 
    final int NULO = -1;

    private T2 t2; // referência para T2

    //construtor
    public T1(int tam, T2 t2) {
        this.tam = tam;
        this.t2 = t2;
        this.tabela = new int[tam];

        for(int i=0; i<tam; i++){
            tabela[i] = NULO;
        }
    }

    //função de mapeamento
    public int h(int x){
        return x % tam;
    }

    //inserir
    public boolean inserir(int x){
        int pos = h(x);

        if(tabela[pos] == NULO){
            tabela[pos] = x;
            return true;
        }

        else{
            //teve colisão, chamar inserir da virtual
            t2.inserir(x);
            return true;
        }
    }
}

public class T2 {
    public int tam;

    private Lista l;
    private T3 t3;
    private Arvore a;

    public T2(int tamT3){
        this.t3 = new T3(tamT3);
        this.l = new Lista(); //cria o nó cabeça
        this.a = new Arvore();
    }

    public void inserir(int x){
        int pos = x % 3;

        if(pos == 0){
            t3.inserir(x);
        }

        else if(pos == 1){
            l.inserir(x);
        }

        else a.inserir(x);
    }
}