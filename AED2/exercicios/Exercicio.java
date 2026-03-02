import java.util.*;

class No {
    int elemento;
    No esq, dir;

    No(int x) {
        elemento = x;
        esq = dir = null;
    }
}

class Celula {
    int elemento;
    Celula prox;

    Celula(int x) {
        elemento = x;
        prox = null;
    }
}

class CelulaDupla {
    int elemento;
    CelulaDupla prox, ant;

    CelulaDupla(int x) {
        elemento = x;
        prox = ant = null;
    }
}

public class ArvoreBinaria {

    No raiz; // nó principal da árvore

    public ArvoreBinaria() {
        raiz = null;
    }

    // ========================
    // INSERÇÃO NORMAL (Ex.02 e 07)
    // ========================
    public No insercao(No i, int x) {
        if (i == null) return new No(x);
        if (x < i.elemento) i.esq = insercao(i.esq, x);
        else if (x > i.elemento) i.dir = insercao(i.dir, x);
        return i;
    }

    // ========================
    // ALTURA (Ex.01)
    // ========================
    public int altura(No i) {
        if (i == null) return -1;
        int esq = altura(i.esq);
        int dir = altura(i.dir);
        return 1 + Math.max(esq, dir);
    }

    // ========================
    // FUNÇÃO PRINCIPAL DO Ex.02
    // ========================
    public void exercicio02() {
        Random gerador = new Random();
        gerador.setSeed(0);
        raiz = null;

        for (int i = 1; i <= 1000; i++) {
            int x = Math.abs(gerador.nextInt());
            raiz = insercao(raiz, x);

            System.out.println("Número de elementos: " + i);

            int h = altura(raiz);
            System.out.println("Altura: " + h);

            double log2 = (Math.log(i) / Math.log(2)) * 1.39;
            System.out.println("Log base 2 * 1.39: " + log2);
            System.out.println("---------------------------------");
        }
    }

    // ========================
    // SOMA DOS ELEMENTOS (Ex.03)
    // ========================
    public int somaElementos(No i) {
        if (i == null) return 0;
        return i.elemento + somaElementos(i.esq) + somaElementos(i.dir);
    }

    // ========================
    // CONTAR ELEMENTOS PARES (Ex.04)
    // ========================
    public int pares(No i) {
        if (i == null) return 0;
        int cont = (i.elemento % 2 == 0) ? 1 : 0;
        return cont + pares(i.esq) + pares(i.dir);
    }

    // ========================
    // VERIFICAR SE DUAS ÁRVORES SÃO IGUAIS (Ex.05)
    // ========================
    public boolean saoIguais(No a, No b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return (a.elemento == b.elemento) &&
               saoIguais(a.esq, b.esq) &&
               saoIguais(a.dir, b.dir);
    }

    // ========================
    // EXISTE ELEMENTO DIVISÍVEL POR 11? (Ex.06)
    // ========================
    public boolean divPorOnze(No i) {
        if (i == null) return false;
        if (i.elemento % 11 == 0) return true;
        return divPorOnze(i.esq) || divPorOnze(i.dir);
    }

    // ========================
    // TREE SORT (Ex.07)
    // ========================
    public void emOrdem(No i, List<Integer> lista) {
        if (i != null) {
            emOrdem(i.esq, lista);
            lista.add(i.elemento);
            emOrdem(i.dir, lista);
        }
    }

    public int[] treeSort(int[] vetor) {
        No raiz = null;
        for (int v : vetor) {
            raiz = insercao(raiz, v);
        }

        List<Integer> lista = new ArrayList<>();
        emOrdem(raiz, lista);

        int[] ordenado = new int[lista.size()];
        for (int i = 0; i < lista.size(); i++) ordenado[i] = lista.get(i);
        return ordenado;
    }

    // ========================
    // CONSTRUIR ÁRVORE A PARTIR DE LISTAS (Ex.08)
    // ========================
    public No toAB(Celula p1, CelulaDupla p2) {
        No arvore = null;

        if (p1 != null) p1 = p1.prox; // pular cabeça
        if (p2 != null) p2 = p2.prox;

        while (p1 != null && p2 != null) {
            arvore = insercao(arvore, p1.elemento);
            arvore = insercao(arvore, p2.elemento);
            p1 = p1.prox;
            p2 = p2.prox;
        }

        while (p1 != null) {
            arvore = insercao(arvore, p1.elemento);
            p1 = p1.prox;
        }

        while (p2 != null) {
            arvore = insercao(arvore, p2.elemento);
            p2 = p2.prox;
        }

        return arvore;
    }

    public static void main(String[] args) {
        ArvoreBinaria arv = new ArvoreBinaria();

        // Teste rápido de inserção e altura
        arv.raiz = arv.insercao(arv.raiz, 50);
        arv.raiz = arv.insercao(arv.raiz, 30);
        arv.raiz = arv.insercao(arv.raiz, 70);
        arv.raiz = arv.insercao(arv.raiz, 10);
        arv.raiz = arv.insercao(arv.raiz, 40);
        arv.raiz = arv.insercao(arv.raiz, 60);
        arv.raiz = arv.insercao(arv.raiz, 90);

        System.out.println("Altura: " + arv.altura(arv.raiz));
        System.out.println("Soma dos elementos: " + arv.somaElementos(arv.raiz));
        System.out.println("Quantidade de pares: " + arv.pares(arv.raiz));
        System.out.println("Existe múltiplo de 11? " + arv.divPorOnze(arv.raiz));

        // Teste do TreeSort
        int[] vetor = {5, 3, 8, 1, 2, 9};
        int[] ordenado = arv.treeSort(vetor);
        System.out.println("Vetor ordenado: " + Arrays.toString(ordenado));

        // Teste do Ex.02 (pode descomentar se quiser ver os 1000 elementos)
        // arv.exercicio02();
    }
}
