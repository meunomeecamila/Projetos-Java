public class Exemploprova5 {
    //!Questão 01
    /*Implemente o método void inserir() na estrutura de dados ilustradas abaixo cujo 
    primeiro nível contém uma tabela hash T1 de tamanho tam1. 
    Cada célula de T1 possui um número inteiro elemento e um ponteiro T2 para outra 
    tabela hash T2, de tamanho tam2.
    O método int hashT1(int i) retorna i % tam1.
    Cada T2 é uma hash com rehash sem tratar as colisões, e trata estas com listas 
    flexibilizadas.
    O método int hashT2(int i, int h) retorna (i % tam2).
    Quando a função rehash → h ≠ 0, o método não trata as colisões, e T2 de cada nó 
    trata essas colisões, e T2 rehash (t % tam2), (++z % tam2).
    O método int hashT3(int i) retorna i % 2.
    Quando este retorno é zero, inserimos o valor em uma lista listaT3.
    Quando não é zero, montamos uma árvore binária árvoreT3.

    Você deve usar as estruturas de Árvore Binária em forma de Lista, ou seja apenas 
    o Nó da Árvore e a Célula da Lista. A classe Dddiona tem o atributo CelulaT1 t[]. 
    A classe CelulaT1 tem os atributos elemento e HashT2 t2. 
    A classe HashT2 tem atributos int[] tab; Celula primeiroT3, ultimoT3; Nó raizT3. 
    As classes Celula e Nó são as vistas na sala de aula.*/

    public void inserir(int x){
        //primeiro, acessar a posição de T1
        int pos = hashT1(x);

        //dentro dessa posição, teremos uma hash t2
        HashT2 t2 = t1[pos].t2; //pegar o ponteiro para a t2 correspondente

        //agora, fazer a hash de t2
        int pos2 = t2.hashT2(x);

        if(t2.tab[pos2] == NULO){
            //se for nulo, inserir aqui
            t2.tab[pos2] = x;
            return;
        }

        //se não for nulo, fazer rehash
        else {
            int pos3 = t2.rehashT2(x);

            //se hash deu certo
            if(t2.tab[pos3] == NULO){
                t2.tab[pos3] = x;
                return;
            }

            //se não deu certo, fazer a virtual
            else {
                int pos4 = hashT3(x);
                if(pos4 == 0){
                    //inserção na lista no fim
                    //considerando a existência de um nó cabeça
                    t2.ultimot3.prox = new Celula(x);
                    t2.ultimot3 = t2.ultimot3.prox;

                }

                else if(pos4 == 1){
                    //inserção na árvore
                    t2.raizt3 = arvoreinserir(x, t2.raizt3);
                }

                else {
                    System.out.println("ERRO");
                    return;
                }
            }
        }
    }

    public No arvoreinserir(int x, No i){
        if(i == null){
            return new No(x);
        }
        else if(x < i.elemento) return arvoreinserir(x,i.esq);
        else if(x > i.elemento) return arvoreinserir(x, i.dir);

        return i;
    }

    //!Questão 2 é visual
    /*
    Seja a sequência de números: 4, 20, 8, 12, 2, 18, 14, 10, 6, 22 e 16, 
    ilustre o passo-a-passo do método de inserção na:
    a) Árvore AVL;
    b) Árvore 2-3-4 (use inserção com fragmentação na descida);
    c) Árvore Alvinegra.
    */

    //!Questão 3
    /*Implemente na classe Trie (vista na aula) o método Trie interseção(Trie a1, Trie a2) 
    que recebe duas árvores trie e retorna outra contendo todas as strings existentes 
    em a1 e a2.
    Considere que:
    a) a classe Trie tem implementado seus construtores e o método void inserir(String s);
    b) as strings de uma árvore não são prefixos exatos de outra árvore.

    Fique à vontade para implementar outros métodos na classe Trie.*/

    public Trie intersecao(Trie a1, Trie a2){
        Trie a3 = new Trie(); //cria nova árvore

        //para cada string de a1, apenas inserir se tiver em a2 também
        coletar(a1.raiz, a2, a3, "");
        return a3;
    }

    private void coletar(No i, Trie a2, Trie a3, String s){
        if(i.isFolha){
            String resp = s + i.elemento; //achamos a string
            //se ela estiver em a2, inserir em a3
            if(a2.pesquisar(resp) == true){
                a3.inserir(resp);
            }
        }

        else {
            for(int k = 0; k<i.prox.length; k++){
                if(i.prox[k] != null){
                    coletar(i.prox[k], a2, a3, s+i.elemento);
                }
            }
        }
    }

    //função que fica dentro do a2
    private boolean pesquisar(String s){
        return pesquisar(s, raiz, 0);
    }

    private boolean pesquisar(String s, No no, int cont){
        char c = s.charAt(cont);
        int tamm = s.length() -1;
        
        if(no.prox[c] == null) return false;

        else if(cont == tamm){
            return no.prox[c].isFolha;
        }

        else if(cont < tamm){
            return pesquisar(s, no.prox[c], cont+1);
        }
    }

    //!Questão 4
    /*
    O nó da árvore trie tem uma estrutura de dados para gerenciar seus filhos. 
    Por exemplo: lista flexível, árvore binária balanceada e tabela hash.
    Apresente e justifique qual complexidade de inserção se usando cada uma dessas três 
    estruturas (20% da questão).

    Em seguida, apresente o código da classe Nó considerando que a estrutura de dados 
    para gerenciar o acesso aos filhos é a lista flexível (20% da questão).

    Finalmente, implemente o método de inserir na árvore conforme visto na aula (25%).
    Para ajudar, abaixo temos o inserir visto na sala de aula:
    */


}
