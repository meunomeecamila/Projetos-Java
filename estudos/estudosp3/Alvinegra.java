public class Alvinegra {
    public NoAN raiz;

    //construtor
    public Alvinegra(){
        this.raiz = null;
    }

    //caminhar central 
    //lista todos os elementos em ordem crescente
    public void caminharcentral(NoAN i){
        if(i == null) return;
        caminharcentral(i.esq);
        System.out.println(i.elemento); //ou outra função
        caminharcentral(i.dir);
    }

    //caminhar pré-ordem
    //lista primeiro o nó e depois seus filhos
    public void caminharpre(NoAN i){
        if(i == null) return;
        System.out.println(i.elemento); //ou outra função
        caminharpre(i.esq);
        caminharpre(i.dir);
    }

    //caminhar pós-ordem
    //lista primeiro os filhos e por último o nó
    public void caminharpos(NoAN i){
        if(i == null) return;
        caminharpos(i.esq);
        caminharpos(i.dir);
        System.out.println(i.elemento); //ou outra função
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
            //aqui, temos 3 opções de onde o elemento pode estar
            if(x < raiz.elemento){
                //único caso em que não é preciso balancear
                raiz.esq = new NoAN(x);
            }

            else if(x < raiz.dir.elemento){
                //aqui, temos a opção de balancear ou de fazer manual
                //balanceando:
                //raiz.dir.esq = new NoAN(x);
                //balancearRaiz(); //chama a função de balanceamento

                //fazendo manual:
                raiz.esq = new NoAN(raiz.elemento); //copia o elemento da raiz
                raiz.elemento = x; //troca a raiz por x
            }

            else {
                //aqui, temos a opção de balancear ou de fazer manual
                //balanceando:
                //raiz.dir.dir = new NoAN(x);
                //balancearRaiz();

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
            // CHAMADA CORRETA:
            inserir(x, null, null, null, raiz);
        }

        //depois do término da inserção, garantir que a raiz esteja branca
        raiz.cor = false;
    }

    //função recursiva de inserir elemento com 4 ponteiros
    //entraremos nessa função no caso de a árvore ter 3+ elementos
    private void inserir(int x, NoAN bisa, NoAN avo, NoAN pai, NoAN i){
        if(i == null){ //momento de inserir

            if(x < pai.elemento){
                pai.esq = new NoAN(x);
                i = pai.esq;
            }
            else{
                pai.dir = new NoAN(x);
                i = pai.dir;
            }

            //após qualquer inserção, conferimos a cor do pai
            if(pai.cor == true){
                balancear(bisa, avo, pai, i);
            }
        }

        else {
            //se chegou até aqui, significa que i ainda não é null

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
