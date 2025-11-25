//* ESTUDO COMPLETO PROVA TEÓRICA 3
/*Esse arquivo abordará majoritariamente conteúdo de Árvores, tabelas hash
e matérias adjacentes de finalização da disciplina de AED2 */

//* Matérias p3
/* 
- Árvores binárias de busca - ok
- Balanceamento de árvores binárias (AVL)
- Árvores 234
- Árvores bicolor (alvinegras)
- Tabela hash
 */

//! Árvores binárias de busca
/* São estruturas com uma raiz e nós, onde cada nó tem 0,1 ou 2 filhos (binárias). 
Os filhos da sua esquerda são sempre menores e os da sua direita, maiores que o nó. 
É considerada balanceada quando a diferença de altura entre a direita e a esquerda
em cada nó é no máximo 1 ou -1. 
Sua inserção, remoção e busca vai até no máximo lg n
*/

public class No {
    public No esq; 
    public No dir;
    public int elemento;

    //construtor
    public No(){
        this(0, null, null);
    }

    public No(int x){
        this.elemento = x;
        this.esq = null;
        this.dir = null;
    }
}

public class Arvore {
    public No raiz; 

    //construtor
    public Arvore(){
        this.raiz = null;
    }

    //caminhar central 
    //lista todos os elementos em ordem crescente
    public void caminharcentral(No i){
        if(i == null) return;
        caminharcentral(i.esq);
        System.out.println(i.elemento); //ou outra função
        caminharcentral(i.dir);
    }

    //caminhar pré-ordem
    //lista primeiro o nó e depois seus filhos
    public void caminharpre(No i){
        if(i == null) return;
        System.out.println(i.elemento); //ou outra função
        caminharpre(i.esq);
        caminharpre(i.dir);
    }

    //caminhar pós-ordem
    //lista primeiro os filhos e por último o nó
    public void caminharpos(No i){
        if(i == null) return;
        caminharpos(i.esq);
        caminharpos(i.dir);
        System.out.println(i.elemento); //ou outra função
    }

    //inserir um elemento
    public No inserir(No i, int elemento){
        if(i == null){
            //lugar de inserção
            i = new No(elemento);
        }

        else if(elemento < i.elemento){
            i.esq = inserir(i.esq, elemento);
        }

        else if(elemento > i.elemento){
            i.dir = inserir(i.dir, elemento);
        }

        else System.out.println("Inválido!");

        return i;
    }

    //pesquisar um elemento
    public boolean pesquisar(No i, int elemento){
        if(i==null){
            //chegou no fim e nao achou
            return false;
        }

        else if(elemento < i.elemento){
            return pesquisar(i.esq, elemento);
        }

        else if(elemento > i.elemento){
            return pesquisar(i.dir,elemento);
        }

        else return true;
    }

    //remover um elemento
    public No remover(No i, int x) {
        //procurou em toda a árvore e não encontrou
        if (i == null) {
            System.out.println("Não tem");
            return null;
        }

        //caminhar
        if (x < i.elemento) {
            i.esq = remover(i.esq, x);
        }

        else if (x > i.elemento) {
            i.dir = remover(i.dir, x);
        }

        else {
            // achou o elemento

            // caso 1: sem filhos
            if (i.esq == null && i.dir == null) {
                return null; //retorna null pra quem chamou, desconectando
            }

            // caso 2: um filho (direita)
            if (i.esq == null) {
                return i.dir; //retorna dir pra quem chamou, desconectando
            }

            // caso 2: um filho (esquerda)
            if (i.dir == null) {
                return i.esq; //retorna esq pra quem chamou, desconectando
            }

            // caso 3: dois filhos
            // substitui pelo maior da esquerda (ou pelo menor da direita)
            No maior = maiorNo(i.esq);
            i.elemento = maior.elemento;
            i.esq = remover(i.esq, maior.elemento);
        }

        return i;
    }

    //função auxiliar pra encontrar o maior nó
    public No maiorNo(No i){
        if(i.dir == null) return i;
        else return maiorNo(i.dir); //sempre anda pra direita pra encontrar o maior
    }

}

//! Balanceamento de árvores binárias (AVL)
/* São estruturas com uma raiz e nós, onde cada nó tem 0,1 ou 2 filhos (binárias). 
Os filhos da sua esquerda são sempre menores e os da sua direita, maiores que o nó. 
Nessa árvore, rotacionamos em 4 direções para balancear caso necessário, gerando
eficiência em termos de pesquisa, remoção e inserção
Sua inserção, remoção e busca vai até no máximo lg n
*/

//* Principais mudanças: 
/*
- Altura do nó (cada nó armazena sua própria altura ou fator)
- Cálculo do FB (fator de balanceamento) ao inserir
- Função de balancear
- 4 rotações (SD, SE, DDE, DED)
*/

public class NoAVL {
    public NoAVL esq;
    public NoAVL dir;
    public int elemento;
    public int altura;

    public NoAVL(int x){
        this.elemento = x;
        this.altura = 0;
        this.esq = null;
        this.dir = null;
    }
}

public class AVL {
    public NoAVL raiz;

    public AVL(){
        this.raiz = null;
    }

    //!obs: os caminhar ficam iguais, então não implementei novamente

    //!função de inserir
    //diferenças: precisa atualizar altura, calcular o fator e balancear se necessário
    public NoAVL inserir(NoAVL i, int elemento){

        //etapa 1 -> normal, igual BST
        if(i == null) return new NoAVL(elemento);
        else if(elemento < i.elemento) i.esq = inserir(i.esq, elemento);
        else if(elemento > i.elemento) i.dir = inserir(i.dir, elemento);
        else {
            System.out.println("erro! Elemento repetido");
            return i;
        }

        //etapa 2 -> atualizar altura
        i.altura = 1 + Math.max(altura(i.esq), altura(i.dir)); 
        //obs: sua altura será vc (1) + a altura dos seus filhos

        //etapa 3 -> balancear caso necessário
        return balancear(i);
    }

    //função pra pegar a altura
    public int altura(NoAVL i){
        if(i == null) return -1;
        else return i.altura;
    }

    //! função de calcular o FB(fator de balanceamento)
    //a árvore precisará ser balanceada quando FB for 2 (dir) ou -2 (esq)
    public int fator(NoAVL i){
        return altura(i.esq) - altura(i.dir);
    }

    //! função de balancear - importantíssima pra AVL
    /*calcula o FB (fator de balanceamento) pra detectar se está 
    desbalanceada. Escolhe a rotação e retorna o nó rotacionado */

    //* Opções de rotação
    //TODO - Rotação simples à direita
    /* Acontece quando: árvore está toda manca pra esquerda
    Rotação é no: avô */

    //TODO - Rotação simples à esquerda
    /* Acontece quando: árvore está toda manca pra direita
    Rotação é no: avô*/

    //TODO - Rotação dupla esq-dir
    /* Acontece quando: árvore está manca pra esquerda e depois direita (<)
    Rotação é no: primeira no pai, segunda no avô*/

    //TODO - Rotação dupla dir-esq
    /* Acontece quando: árvore está manca pra direita e depois esquerda (>)
    Rotação é no: primeira no pai, segunda no avô*/

    public NoAVL balancear(NoAVL i){
        int fb = fator(i); //fator de balanceamento

        //ver qual rotação será feita
        if(fb == 2){ //rotação para a direita (simples ou dupla)
            if(fator(i.esq) < 0){ //rotação dupla
                i = rotacaoEsqDir(i);
            }

            else { //rotação simples
                i = rotacaoDir(i);
            }
        }

        else if(fb == -2){ //rotação para a esquerda (simples ou dupla)
            if(fator(i.dir) > 0){
                i = rotacaoDirEsq(i);
            }

            else { //rotação simples
                i = rotacaoEsq(i);
            }
        }

        return i; 
    }
}

//Rotação simples à esquerda 
No rotacaoEsq(No i){
    No j = i.dir;
    No k = j.esq;

    j.esq = i;
    i.dir = k;

     // Atualizar alturas
    i.altura = 1 + Math.max(altura(i.esq), altura(i.dir));
    j.altura = 1 + Math.max(altura(j.esq), altura(j.dir));

    return j;
}

//Rotação simples à direitaa
No rotacaoDir(No i){
    No j = i.esq;
    No k = j.dir;

    j.dir = i;
    i.esq = k;

     // Atualizar alturas
    i.altura = 1 + Math.max(altura(i.esq), altura(i.dir));
    j.altura = 1 + Math.max(altura(j.esq), altura(j.dir));

    return j;
}

//Rotação dupla Direita-Esquerda(>)
No rotacaoDirEsq(No i){
    i.dir = rotacaoDir(i.dir);
    return rotacaoEsq(i);
}

//Rotação dupla Esquerda-Direita(<)
No rotacaoEsqDir(No i){
    i.esq = rotacaoEsq(i.esq);
    return rotacaoDir(i);
}