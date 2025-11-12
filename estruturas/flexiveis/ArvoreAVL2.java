class No {
    public int elemento;
    public No esq, dir;
    private int nivel; //será usado para calcular o fator

    public No(int elemento) {
        this(elemento, null, null);
    }

    public No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.nivel = 1; //começa com 1 pois é o próprio nivel
        //obs: além disso, o pai precisaria de pelo menos 1 pra somar 1
    }

    public static int getNivel(No no) {
        if(no == null) return 0;
        else return no.nivel;
    }

    public void setNivel() {
        //o nivel será 1(do próprio nó) + o maior entre esq e dir
        this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir));
    }
}

public class AVL {
    /*A árvore AVL é uma árvore binária de pesquisa em que o fator de cada nó só
    pode ser igual a 0, 1 ou -1 */
    private No raiz;

    public AVL() {
        raiz = null;
    }

    public boolean pesquisar(int x) {
        return pesquisar(x, raiz);
    }

    // FUNÇÃO DE PESQUISAR TRADICIONAL ================

    private boolean pesquisar(int x, No i) {
        if (i == null) {
            return false;
        } else if (x == i.elemento) {
            return true;
        } else if (x < i.elemento) {
            return pesquisar(x, i.esq);
        } else {
            return pesquisar(x, i.dir);
        }
    }

    //FUNÇÕES DE CAMINHAR TRADICIONAIS =============

    public void caminharCentral() {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq);
            System.out.print(i.elemento + " ");
            caminharCentral(i.dir);
        }
    }

    public void caminharPre() {
        System.out.print("[ ");
        caminharPre(raiz);
        System.out.println("]");
    }

    private void caminharPre(No i) {
        if (i != null) {
            System.out.print(i.elemento + "(fb " + (No.getNivel(i.dir) - No.getNivel(i.esq)) + ") ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    public void caminharPos() {
        System.out.print("[ ");
        caminharPos(raiz);
        System.out.println("]");
    }

    private void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq);
            caminharPos(i.dir);
            System.out.print(i.elemento + " ");
        }
    }

    //FUNÇÃO DE INSERIR BALANCEADA ============

    public void inserir(int x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(int x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        } else {
            throw new Exception("Erro ao inserir!");
        }
        return balancear(i); //mudança
    }

    //FUNÇÃO DE REMOVER BALANCEADA ============

    public void remover(int x) throws Exception {
        raiz = remover(x, raiz);
    }

    private No remover(int x, No i) throws Exception {
        if (i == null) {
            throw new Exception("Erro ao remover!");
        } else if (x < i.elemento) {
            i.esq = remover(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = remover(x, i.dir);
        } else if (i.dir == null) {
            i = i.esq;
        } else if (i.esq == null) {
            i = i.dir;
        } else {
            i.esq = maiorEsq(i, i.esq);
        }
        return balancear(i); //mudança
    }

    //Função para achar o maior filho entre dois
    private No maiorEsq(No i, No j) {
        if (j.dir == null) {
            i.elemento = j.elemento;
            j = j.esq;
        } else {
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    //FUNÇÕES PARA BALANCEAR ===========
    private No balancear(No no) throws Exception {
        if (no != null) {
            //fator(i) = hdir(i) = hesq(i)
            int fator = No.getNivel(no.dir) - No.getNivel(no.esq);

            if (Math.abs(fator) <= 1) { //pegar o módulo
                no.setNivel();
            } 
            
            else if (fator == 2) { //desbalanceado pra direita
                int fatorFilho = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);
            
                if (fatorFilho == -1) { //rotação dupla
                    no.dir = rotacionarDir(no.dir);
                }
                no = rotacionarEsq(no); //rotação simples
            } 
            
            else if (fator == -2) { //desbalanceada para esquerda
                int fatorFilho = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);

                if (fatorFilho == 1) { //rotação dupla
                    no.esq = rotacionarEsq(no.esq);
                }
                no = rotacionarDir(no); //rotação simples
            } 
            
            /*OBS: Se o código estiver funcionando corretamente, ele não
            entrará nesse else NUNCA, pois o fator não passará de 2 ou de -2 */
            else {
                throw new Exception("Fator invalido!");
            }
        }
        return no;
    }

    //Rotacionar a direita dupla -> significa que é um V desbalanceado p esquerda
    private No rotacionarDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        no.setNivel();
        noEsq.setNivel();
        return noEsq;
    }

    //Rotacionar a esquerda dupla -> significa que é um V desbalanceado p direita
    private No rotacionarEsq(No no) {
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        no.setNivel();
        noDir.setNivel();
        return noDir;
    }
}
