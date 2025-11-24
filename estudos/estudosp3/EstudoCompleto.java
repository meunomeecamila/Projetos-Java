//* ESTUDO COMPLETO PROVA TEÓRICA 3
/*Esse arquivo abordará majoritariamente conteúdo de Árvores, tabelas hash
e matérias adjacentes de finalização da disciplina de AED2 */

//* Matérias p3
/* 
- Árvores binárias de busca (AVL)
- Balanceamento de árvores binárias
- Árvores 234
- Árvores bicolor (alvinegras)
- Tabela hash
 */

//! Árvores binárias de busca (AVL)
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

public class AVL {
    public No raiz; 

    //construtor
    public AVL(){
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
}