class No {
    public int elemento;
    public No esq, dir;
    private int nivel;

    public No(int elemento) {
        this(elemento, null, null);
    }

    public No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.nivel = 1;
    }

    public static int getNivel(No no) {
        return (no == null) ? 0 : no.nivel;
    }

    public void setNivel() {
        this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir));
    }
}

public class AVL {
    private No raiz;

    public AVL() {
        raiz = null;
    }

    public boolean pesquisar(int x) {
        return pesquisar(x, raiz);
    }

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
        return balancear(i);
    }

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
        return balancear(i);
    }

    private No maiorEsq(No i, No j) {
        if (j.dir == null) {
            i.elemento = j.elemento;
            j = j.esq;
        } else {
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    private No balancear(No no) throws Exception {
        if (no != null) {
            int fator = No.getNivel(no.dir) - No.getNivel(no.esq);

            if (Math.abs(fator) <= 1) {
                no.setNivel();
            } else if (fator == 2) {
                int fatorFilho = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);
                if (fatorFilho == -1) {
                    no.dir = rotacionarDir(no.dir);
                }
                no = rotacionarEsq(no);
            } else if (fator == -2) {
                int fatorFilho = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);
                if (fatorFilho == 1) {
                    no.esq = rotacionarEsq(no.esq);
                }
                no = rotacionarDir(no);
            } else {
                throw new Exception("Fator invalido!");
            }
        }
        return no;
    }

    private No rotacionarDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        no.setNivel();
        noEsq.setNivel();
        return noEsq;
    }

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
