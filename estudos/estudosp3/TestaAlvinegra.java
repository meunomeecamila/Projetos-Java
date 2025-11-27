import java.util.Scanner;

//! Classe Nó Alvinegra
class NoAN {
    public int elemento;
    public NoAN esq, dir;
    public boolean cor; //true = preto, false = branco

    //construtor
    public NoAN(int elemento){
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
        this.cor = true; 
    }
}

//! Classe árvore alvinegra
class Alvinegra {
    public NoAN raiz;

    //construtor
    public Alvinegra(){
        this.raiz = null;
    }

    //! caminhar central 
    //lista todos os elementos em ordem crescente
    public void caminharcentral(NoAN i){
        if(i == null) return;
        caminharcentral(i.esq);
        System.out.println(i.elemento); //ou outra função
        caminharcentral(i.dir);
    }

    //! caminhar pré-ordem
    //lista primeiro o nó e depois seus filhos
    public void caminharpre(NoAN i){
        if(i == null) return;
        System.out.println(i.elemento); //ou outra função
        caminharpre(i.esq);
        caminharpre(i.dir);
    }

    //! caminhar pós-ordem
    //lista primeiro os filhos e por último o nó
    public void caminharpos(NoAN i){
        if(i == null) return;
        caminharpos(i.esq);
        caminharpos(i.dir);
        System.out.println(i.elemento); //ou outra função
    }

    //!função de pesquisar
    /*retorna true se o elemento estiver na árvore e false se não estiver */
    public boolean pesquisar(int x, NoAN i){
        if(i == null){
            //procurou na árvore toda e não achou
            return false;
        }

        else if(x < i.elemento) return pesquisar(x, i.esq);
        else if(x > i.elemento) return pesquisar(x, i.dir);

        else return true;
    }

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
            if(x < raiz.elemento){
                raiz.esq = new NoAN(x);
            }

            else if(x < raiz.dir.elemento){
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = x;
            }

            else {
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = raiz.dir.elemento; 
                raiz.dir.elemento = x;
            }

            raiz.dir.cor = false;
            raiz.esq.cor = false;
        }

        //dois elementos sendo raiz e esquerda
        else if(raiz.dir == null){
            if(x > raiz.elemento){
                raiz.dir = new NoAN(x);
            }

            else if(x > raiz.esq.elemento){
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = x;
            }

            else {
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = x;
            }

            raiz.dir.cor = false; //branco
            raiz.esq.cor = false; //branco
        }

        //se não entrou em nenhum dos anteriores, a árvore tem 3+ elementos
        else {
            System.out.println("Árvore com 3 ou mais elementos. Inserindo normalmente...");
            inserir(x, null, null, null, raiz);
        }

        raiz.cor = false;
    }

    //função recursiva de inserir elemento com 4 ponteiros
    private void inserir(int x, NoAN bisa, NoAN avo, NoAN pai, NoAN i){
        if(i == null){
            if(x < pai.elemento){
                pai.esq = new NoAN(x);
                i = pai.esq;
            }
            else{
                pai.dir = new NoAN(x);
                i = pai.dir;
            }

            if(pai.cor == true){
                balancear(bisa, avo, pai, i);
            }
        }

        else {
            if(isNo4(i) == true){
                i.cor = true;
                i.esq.cor = false;
                i.dir.cor = false;

                if(pai != null && pai.cor == true){
                    balancear(bisa, avo, pai, i);
                }
            }

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
        if (pai.cor == true) {

            if (pai.elemento > avo.elemento) {
                if (i.elemento > pai.elemento) {
                    avo = rotacaoEsq(avo);
                }
                else {
                    avo = rotacaoDirEsq(avo);
                }
            }

            else {
                if (i.elemento < pai.elemento) {
                    avo = rotacaoDir(avo);
                }
                else {
                    avo = rotacaoEsqDir(avo);
                }
            }

            if (bisa == null) {
                raiz = avo;
            }
            else if (avo.elemento < bisa.elemento) {
                bisa.esq = avo;
            }
            else {
                bisa.dir = avo;
            }

            avo.cor = false;

            if (avo.esq != null) avo.esq.cor = true;
            if (avo.dir != null) avo.dir.cor = true;
        }
    }

    //Rotação simples à esquerda 
    NoAN rotacaoEsq(NoAN i){
        NoAN j = i.dir;
        NoAN k = j.esq;

        j.esq = i;
        i.dir = k;

        return j;
    }

    //Rotação simples à direita
    NoAN rotacaoDir(NoAN i){
        NoAN j = i.esq;
        NoAN k = j.dir;

        j.dir = i;
        i.esq = k;

        return j;
    }

    //Rotação dupla Direita-Esquerda(>)
    NoAN rotacaoDirEsq(NoAN i){
        i.dir = rotacaoDir(i.dir);
        return rotacaoEsq(i);
    }

    //Rotação dupla Esquerda-Direita(<)
    NoAN rotacaoEsqDir(NoAN i){
        i.esq = rotacaoEsq(i.esq);
        return rotacaoDir(i);
    }

    //função de balancear para o caso comentado no inserir com 0,1 ou 2 elementos
    private void balancearRaiz() {
        if (raiz.esq == null && raiz.dir != null) {

            if (raiz.dir.dir != null) {
                raiz = rotacaoEsq(raiz);
            }

            else if (raiz.dir.esq != null) {
                raiz = rotacaoDirEsq(raiz);
            }

            raiz.cor = false;
            raiz.esq.cor = true;
            raiz.dir.cor = true;

            return;
        }

        if (raiz.dir == null && raiz.esq != null) {

            if (raiz.esq.esq != null) {
                raiz = rotacaoDir(raiz);
            }

            else if (raiz.esq.dir != null) {
                raiz = rotacaoEsqDir(raiz);
            }

            raiz.cor = false;
            raiz.esq.cor = true;
            raiz.dir.cor = true;

            return;
        }

        if (raiz.esq != null && raiz.dir != null) {
            raiz.cor = false;
            raiz.esq.cor = true;
            raiz.dir.cor = true;
        }
    }

}

//Main com menu interativo
public class TestaAlvinegra {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Alvinegra arvore = new Alvinegra();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\nÁRVORE ALVINEGRA");
            System.out.println("1 - Inserir elemento");
            System.out.println("2 - Pesquisar elemento");
            System.out.println("3 - Caminhar CENTRAL (em ordem)");
            System.out.println("4 - Caminhar PRE-ORDEM");
            System.out.println("5 - Caminhar POS-ORDEM");
            System.out.println("6 - Mostrar raiz e filhos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();

            switch(opcao){

                case 1:
                    System.out.print("\nDigite o elemento a inserir: ");
                    int x = sc.nextInt();
                    arvore.inserir(x);
                    System.out.println("Elemento " + x + " inserido!");
                    break;

                case 2:
                    System.out.print("\nDigite o elemento a pesquisar: ");
                    int p = sc.nextInt();
                    boolean achou = arvore.pesquisar(p, arvore.raiz);
                    if(achou) System.out.println("Elemento encontrado!");
                    else System.out.println("Elemento NÃO encontrado.");
                    break;

                case 3:
                    System.out.println("\nCaminhar CENTRAL:");
                    arvore.caminharcentral(arvore.raiz);
                    break;

                case 4:
                    System.out.println("\nCaminhar PRE-ORDEM:");
                    arvore.caminharpre(arvore.raiz);
                    break;

                case 5:
                    System.out.println("\nCaminhar POS-ORDEM:");
                    arvore.caminharpos(arvore.raiz);
                    break;

                case 6:
                    System.out.println("\n--- ESTADO DA RAIZ ---");
                    if(arvore.raiz == null){
                        System.out.println("Árvore vazia.");
                    }
                    else{
                        System.out.println("Raiz: " + arvore.raiz.elemento + " | cor = " + arvore.raiz.cor);

                        if(arvore.raiz.esq != null)
                            System.out.println(" ├─ Esquerda: " + arvore.raiz.esq.elemento + " | cor = " + arvore.raiz.esq.cor);
                        else
                            System.out.println(" ├─ Esquerda: null");

                        if(arvore.raiz.dir != null)
                            System.out.println(" └─ Direita: " + arvore.raiz.dir.elemento + " | cor = " + arvore.raiz.dir.cor);
                        else
                            System.out.println(" └─ Direita: null");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }
}
