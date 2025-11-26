//* ESTUDO COMPLETO PROVA TEÓRICA 3
/*Esse arquivo abordará majoritariamente conteúdo de Árvores, tabelas hash
e matérias adjacentes de finalização da disciplina de AED2 */

//* Matérias p3
/* 
- Árvores binárias de busca - ok
- Balanceamento de árvores binárias (AVL) - ok
- Árvores 234 - ok
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

    //!função de remover
    //diferenças: precisa atualizar altura, calcular o fator e balancear se necessário
    public No remover(No i, int elemento){
        if(i == null){
            System.out.println("Erro! Não tem esse elemento");
        }
        else if(elemento < i.elemento){
            i.esq = remover(i.esq, elemento);
        }
        else if(elemento > i.elemento){
            i.dir = remover(i.dir, elemento);
        }
        else {
            //achou o elemento -> remover
            //caso 1 -> sem filhos
            if(i.esq == null && i.dir == null){
                return null;
            }

            //caso 2 -> apenas um filho
            //filho na direita
            if(i.esq == null){
                return i.dir;
            }

            //filho na esquerda
            if(i.dir == null){
                return i.esq;
            }

            //caso 3: dois filhos
            //substitui pelo maior da esquerda (ou pelo menor da direita)
            No maior = maiorNo(i.esq);
            i.elemento = maior.elemento;
            i.esq = remover(i.esq, maior.elemento);
        }
    }
}

//! Árvores 234
/* São estruturas com uma raiz e nós, onde cada nó tem 2, 3 ou 4 filhos
- Tipo 2 nó -> 1 item, 2 ponteiros
- Tipo 3 nó -> 2 itens, 3 ponteiros
- Tipo 4 nó -> 3 itens, 4 ponteiros 
Sua inserção, remoção e busca vai até no máximo lg n na base 2,3 ou 4
A inserção dos 234 sempre acontece nas folhas, até que elas se lotem.
Quando elas se lotam, os elementos são redivididos, com o do meio subindo,
o ds esquerda sendo menor e o da direita, maior.
*/

//Nas árvores 234, temos dois tipos de fragmentação.

//? Fragmentação na subida ou ascensão
/*Toda vez que ao inserir um elemento, ele for inserido em uma árvore tipo 4 nó, 
fragmentar o nó e conferir todos os de cima. */
//!Problema: cria uma cascata se tivermos nós do tipo 4 consecutivos

//? Fragmentação na descida
/*Quando caminhamos em uma inserção, quebramos o nó toda vez que encontrarmos
um do tipo 4.*/
//!Problema: há quebras proativas e talvez, desnecessárias
//*Solução: evita cascatas 

//TODO - observação: Nós não implementamos o código da AVL, pois usamos ela como
//TODO - uma introdução para a alvinegra. 

//As fragmentações e desenhos podem ser cobradas, então revisar isso!





//! Árvore bicolor - Alvinegra - Rubronegra
/*Árvore criada para resolver problemas da 234 e facilitar a sua compreensão. 
Os nós podem ter duas cores (true or false), que serão um booleano. O nosso professor, 
Max, utiliza preto e branco para facilitar a compreensão. Mas a árvore realmente
conhecida é a Rubronegra (vermelho e preto). */

//! O bit de cor da alvinegra não interfere em nada, apenas serve para facilitar o balanceamento
//? Uma árvore alvinegra está desbalanceada quando temos dois elementos pretos seguidos

// !Elementos pretos: gêmeos na 234 (mesmo nó)
// ?Elementos brancos: não gêmeos

//Dois pretos seguidos significa uma tendência na inserção e que o grupo de gêmeos estourou
//*obs: A raiz sempre é branca

//Os gêmeos podem ser representados tanto pela coloração de suas arestas quanto pela de seus nós.

public class NoAN {
    public NoAN esq;
    public NoAN dir;
    public int elemento;
    public boolean cor; //bit de cor adicionado

    //construtor
    public NoAN(int x){
        this.elemento = x;
        this.cor = true; //todas as cores começam com preto -> true
        this.esq = null;
        this.dir = null;
    }
}

public class Alvinegra(){
    public NoAN raiz;

    //construtor
    public Alvinegra(){
        this.raiz = null;
    }

    //!obs: os caminhar ficam iguais, então não implementei novamente

    //!função de inserir
    /*nessa função, os 3 primeiros nós são implementados manualmente. Se não for nenhum
    desses casos, chamamos o inserir recursivo normal */
    
    public void inserir(int x){
        //zero elementos
        if(raiz == null){
            raiz = new NoAN(x);
        }

        //apenas um elemento
        else if(raiz.esq == null && raiz.dir == null){

            if(x < raiz.elemento){ //inserir na esquerda
                raiz.esq = new NoAN(x);
            }

            else if(x > raiz.elemento){ //inserir na direita
                raiz.dir = new NoAN(x);
            }

            else System.out.println("Item inválido!");
        }

        //dois elementos sendo raiz e direita
        else if(raiz.esq == null){
            //aqui, temos 3 opções de onde o elemento pode estar
            if(x < raiz.elemento){
                //único caso em que não é preciso balancear
                raiz.esq = new NoAN(x);
            }

            else if(x < raiz.dir.elemento){
                //aqui, temos a opção de balancear ou de fazer manual
                //balanceando:
                //raiz.dir.esq = new NoAN(x);
                //balancear(raiz); //chama a função de balanceamento

                //fazendo manual:
                raiz.esq = new NoAN(raiz.elemento); //copia o elemento da raiz
                raiz.elemento = x; //troca a raiz por x
            }

            else {
                //aqui, temos a opção de balancear ou de fazer manual
                //balanceando:
                //raiz.dir.dir = new NoAN(x);
                //balancear(raiz);

                //fazendo manual
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = raiz.dir.elemento; 
                raiz.dir.elemento = x;
            }

            raiz.dir.cor = false;
            raiz.esq.cor = false;
        }

        //dois elementos sendo raiz e esquerda
        else if(raiz.dir == null){
            //aqui, temos 3 opções de onde o elemento pode estar
            if(x > raiz.elemento){
                //único caso em que não é preciso balancear
                raiz.dir = new NoAN(x);
            }

            else if(x > raiz.esq.elemento){
                //aqui, temos a opção de balancear ou de fazer manual
                //balanceando:
                //raiz.esq.dir = new NoAN(x);
                //balancear(raiz);

                //fazendo manual:
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = x;
            }

            else {
                //aqui, temos a opção de balancear ou de fazer manual
                //balanceando:
                //raiz.esq.esq = new NoAN(x);
                //balancear(raiz);

                //fazendo manual:
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = x;
            }

            //no final, temos que garantir a dinamica das cores
            raiz.dir.cor = false; //branco
            raiz.esq.cor = false; //branco
        }

        //se não entrou em nenhum dos anteriores, a árvore tem 3+ elementos
        //chamamos o inserir recursivo normal
        else {
            System.out.println("Árvore com 3 ou mais elementos. Inserindo normalmente...");
            //inserir(elemento, null, null, null, raiz);
        }

        //depois do término da inserção, garantir que a raiz esteja branca
        raiz.cor = false;
    }

    //função recursiva de inserir elemento com 4 ponteiros
    //entraremos nessa função no caso de a árvore ter 3+ elementos
    private void inserir(int x, NoAN bisa, NoAN avo, NoAN pai, NoAN i){
        if(i == null){ //momento de inserir
            //se não tivermos i, olhamos o pai
            if(x < pai.elemento){
                pai.esq = new NoAN(x);
            }

            else{
                pai.dir = new NoAN(x);
            }

            //após qualquer inserção, conferimos a cor do pai
            if(pai.cor == true){
                balancear(bisa, avo, pai, i);
            }
        }

        else {
            //se chegou até aqui, significa que i ainda não é null
            //então, vamos andar para a frente
            //para isso, precisamos sempre conferir se é um nó do tipo 4
            
            if(isNo4(i) == true){
                //trocar as cores
                i.cor = true;
                i.esq.cor = false;
                i.dir.cor = false;

                //conferir novamente a cor do pai
                if(pai.cor == true){
                    balancear(bisa, avo, pai, i);
                }
            }

            //agora que conferimos, continuar caminhando
            if(x < i.elemento) inserir(x, avo, pai, i, i.esq);
            else if(x > i.elemento) inserir(x, avo, pai, i, i.dir);
            else System.out.println("erro");


        }
    }

    //função que verifica se um nó é do tipo 4
    public boolean isNo4(NoAN i){
        if(i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true){
            return true;
        }
        else return false;
    }

    //função de balancear
    private void balancear(NoAN bisa, NoAN avo, NoAN pai, NoAN i) {
        //conferir a cor do pai
        if (pai.cor == true) {
            //aqui, temos que conferir qual dos casos de rotação será utilizado

            //manco à direita (pai está à direita do avô)
            if (pai.elemento > avo.elemento) {

                // Caso RR (i à direita do pai)
                if (i.elemento > pai.elemento) {
                    avo = rotacaoEsq(avo);
                }

                // Caso RL (i à esquerda do pai)
                else {
                    avo = rotacaoDirEsq(avo);
                }
            }

            // Manco à esquerda (pai está à esquerda do avô)
            else {

                // Caso LL (i à esquerda do pai)
                if (i.elemento < pai.elemento) {
                    avo = rotacaoDir(avo);
                }
                // Caso LR (i à direita do pai)
                else {
                    avo = rotacaoEsqDir(avo);
                }
            }

            //agora que chegou até aqui, fazer as conferências e ajustes
            //conferir o bisavô
            if (bisa == null) {
                raiz = avo;        // o avô é agora a nova raiz
            }
            else if (avo.elemento < bisa.elemento) {
                bisa.esq = avo;
            }
            else {
                bisa.dir = avo;
            }

            //ajuste de cores
            // avô vira branco (não-gêmeo)
            avo.cor = false;

            // seus filhos viram pretos (gêmeos)
            if (avo.esq != null) avo.esq.cor = true;
            if (avo.dir != null) avo.dir.cor = true;
        }
    }


    //quatro rotações possíveis
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

    //Rotação simples à esquerda 
    NoAN rotacaoEsq(No i){
        No j = i.dir;
        No k = j.esq;

        j.esq = i;
        i.dir = k;

        return j;
    }

    //Rotação simples à direitaa
    NoAN rotacaoDir(No i){
        No j = i.esq;
        No k = j.dir;

        j.dir = i;
        i.esq = k;

        return j;
    }

    //Rotação dupla Direita-Esquerda(>)
    NoAN rotacaoDirEsq(No i){
        i.dir = rotacaoDir(i.dir);
        return rotacaoEsq(i);
    }

    //Rotação dupla Esquerda-Direita(<)
    NoAN rotacaoEsqDir(No i){
        i.esq = rotacaoEsq(i.esq);
        return rotacaoDir(i);
    }

}




