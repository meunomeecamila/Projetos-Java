public class No {
    int elemento;
    No esq, dir;
    int altura;

    public No(int elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
        this.altura = 1;
    }
}

public class ArvoreAVL {
    No raiz;

    // ---------- INSERÇÃO ----------
    public No inserir(No no, int x) {
        if (no == null) return new No(x);

        if (x < no.elemento)
            no.esq = inserir(no.esq, x);
        else if (x > no.elemento)
            no.dir = inserir(no.dir, x);
        else
            return no; // valores iguais não são inseridos

        // Atualiza altura
        no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));

        // Verifica fator de balanceamento
        int balance = getBalance(no);

        // 1️⃣ Totalmente pra esquerda (LL)
        if (balance > 1 && x < no.esq.elemento)
            return rotacaoDireita(no);

        // 2️⃣ Totalmente pra direita (RR)
        if (balance < -1 && x > no.dir.elemento)
            return rotacaoEsquerda(no);

        // 3️⃣ V pra esquerda (<) (LR)
        if (balance > 1 && x > no.esq.elemento) {
            no.esq = rotacaoEsquerda(no.esq);
            return rotacaoDireita(no);
        }

        // 4️⃣ V pra direita (>) (RL)
        if (balance < -1 && x < no.dir.elemento) {
            no.dir = rotacaoDireita(no.dir);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void inserir(int x) {
        raiz = inserir(raiz, x);
    }

    // ---------- FUNÇÕES DE APOIO ----------
    private int altura(No n) {
        return (n == null) ? 0 : n.altura;
    }

    private int getBalance(No n) {
        return (n == null) ? 0 : altura(n.esq) - altura(n.dir);
    }

    // ---------- 4 MÉTODOS DE BALANCEAMENTO ----------
    // 1️⃣ Rotação simples à direita (LL)
    private No rotacaoDireita(No y) {
        No x = y.esq;
        No T2 = x.dir;

        x.dir = y;
        y.esq = T2;

        y.altura = 1 + Math.max(altura(y.esq), altura(y.dir));
        x.altura = 1 + Math.max(altura(x.esq), altura(x.dir));

        return x;
    }

    // 2️⃣ Rotação simples à esquerda (RR)
    private No rotacaoEsquerda(No x) {
        No y = x.dir;
        No T2 = y.esq;

        y.esq = x;
        x.dir = T2;

        x.altura = 1 + Math.max(altura(x.esq), altura(x.dir));
        y.altura = 1 + Math.max(altura(y.esq), altura(y.dir));

        return y;
    }

    // 3️⃣ Rotação dupla à direita (LR)
    private No rotacaoDuplaDireita(No no) {
        no.esq = rotacaoEsquerda(no.esq);
        return rotacaoDireita(no);
    }

    // 4️⃣ Rotação dupla à esquerda (RL)
    private No rotacaoDuplaEsquerda(No no) {
        no.dir = rotacaoDireita(no.dir);
        return rotacaoEsquerda(no);
    }

    // ---------- EXIBIÇÃO (só pra testar) ----------
    public void preOrdem(No no) {
        if (no != null) {
            System.out.print(no.elemento + " ");
            preOrdem(no.esq);
            preOrdem(no.dir);
        }
    }
}
