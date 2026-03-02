public class No {
    int elemento;
    No esq, dir;

    public No(int elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }
}

public class ArvoreBinaria {
    No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    // ---------- INSERIR ----------
    public void inserir(int x) {
        raiz = inserir(raiz, x);
    }

    private No inserir(No no, int x) {
        if (no == null) {
            no = new No(x);
        } else if (x < no.elemento) {
            no.esq = inserir(no.esq, x);
        } else if (x > no.elemento) {
            no.dir = inserir(no.dir, x);
        } // se for igual, não insere (evita duplicados)

        return no;
    }

    // ---------- PESQUISAR ----------
    public boolean pesquisar(int x) {
        return pesquisar(raiz, x);
    }

    private boolean pesquisar(No no, int x) {
        if (no == null) return false;
        if (x == no.elemento) return true;
        if (x < no.elemento) return pesquisar(no.esq, x);
        else return pesquisar(no.dir, x);
    }

    // ---------- REMOVER ----------
    public void remover(int x) {
        raiz = remover(raiz, x);
    }

    private No remover(No no, int x) {
        if (no == null) return null;

        if (x < no.elemento) {
            no.esq = remover(no.esq, x);
        } else if (x > no.elemento) {
            no.dir = remover(no.dir, x);
        } else { // achou o nó a remover
            if (no.esq == null) return no.dir;
            if (no.dir == null) return no.esq;

            // substitui pelo sucessor
            No sucessor = menorNo(no.dir);
            no.elemento = sucessor.elemento;
            no.dir = remover(no.dir, sucessor.elemento);
        }

        return no;
    }

    private No menorNo(No no) {
        while (no.esq != null)
            no = no.esq;
        return no;
    }

    // ---------- PERCURSOS ----------
    public void preOrdem(No no) {
        if (no != null) {
            System.out.print(no.elemento + " ");
            preOrdem(no.esq);
            preOrdem(no.dir);
        }
    }

    public void inOrdem(No no) {
        if (no != null) {
            inOrdem(no.esq);
            System.out.print(no.elemento + " ");
            inOrdem(no.dir);
        }
    }

    public void posOrdem(No no) {
        if (no != null) {
            posOrdem(no.esq);
            posOrdem(no.dir);
            System.out.print(no.elemento + " ");
        }
    }
}
