// * Esse documento conterá as matérias para a prova 2 de AED. 

// TODO - Caso deseje utilizar algum dos códigos, troque o nome do arquivo Estudocompleto.java para o desejado
// TODO - A segunda opção é copiar e colar para outro arquivo

// ! Matérias:
// ? (Códigos de ordenação) 
/*
- Selection - ok
- Bubble - ok
- Insertion - ok
- Heap - ok
- Quick - ok
- Merge - ok
- Shell - ok
- Counting - ok
*/

// ? (Estruturas de dados flexíveis)
/*
- Pilha flex - ok
- Fila flex - ok
- lista simples flex - ok
- Lista dupla flex - ok
- Matriz flex - ok
*/

// ? (Extras)
/*
- Coleta de lixo - ok
- Buscas parciais
*/

//* Algoritmos de ordenação ========================
// ! Selection sort 
/* Complexidade no melhor caso: n²
   Complexidade no pior caso: n²
   Utilização: Bom para achar o menor item ou para ordenação parcial. 
*/

public void Selection(){
    int[] vetor = {3, 5, 1, 0, 9, 8, 10};
    int n = vetor.length;
    for(int i=0; i<n; i++){
        int menor = i;
        for(int j=i+1; j<n;j++){
            if(vetor[j] < vetor[menor]) menor = j;
        }
        swap(vetor,menor,i); //funcao que normalmente pode ter
    }
}

// ! Insertion sort 
/* Complexidade no melhor caso: n
   Complexidade no pior caso: n²
   Utilização: Bom para inserir elementos em um conjunto já ou parcialmente ordenado
*/

public void Insertion(){
    int[] vetor = {3, 5, 1, 0, 9, 8, 10};
    int n = vetor.length;
    for(int i=1;i<n;i++){
        int tmp = vetor[i];
        int j = i-1;

        while(j>=0 && vetor[j] > tmp){
            vetor[j+1] = vetor[j]; //desloca
            j--;
        }

        vetor[j+1] = tmp;
    }
}

// ! Bubble sort 
/* Complexidade no melhor caso: n
   Complexidade no pior caso: n²
   Utilização: Fácil de implementar e eficiente em casos pequenos
*/
public void Bubble() {
    int[] vetor = {3, 5, 1, 0, 9, 8, 10};
    int n = vetor.length;
    for(int i=0; i<n-1; i++){
        for(int j=0; j<n-1-i; j++){
            if(vetor[j] > vetor[j+1]) swap(vetor, j, j+1);
        }
    }
}

// ! Quicksort
/* Complexidade no melhor caso: n x lg n
   Complexidade no pior caso: n²
   Utilização: Muito rápido e usa pouca memória
*/

public void AuxiliarQuick(){
    int[] vetor = {3, 5, 1, 0, 9, 8, 10};
    int n = vetor.length;
    Quicksort(0,n-1,vetor);
}

public void Quicksort(int esq, int dir, int[] vetor){
    int i = esq, j = dir;
    int pivo = vetor[(esq+dir)/2]; //meio
    
    while(i<=j){
        while(vetor[i]<pivo)i++;
        while(vetor[j]>pivo)j--;
        if(i<=j){
            swap(vetor,i,j);
            i++; j--;
        }
    }

    if (esq < j) Quicksort(esq, j, vetor);  // Recursão na parte esquerda
    if (i < dir) Quicksort(i, dir, vetor);  // Recursão na parte direita
}

// ! Shellsort 
/* Complexidade no melhor caso: n x log² n
   Complexidade no pior caso: n²
   Utilização: Melhoria do insertion, desloca menos quando está longe
*/

public void Shellsort(){
    int[] vetor = {3, 5, 1, 0, 9, 8, 10};
    int n = vetor.length;

    //gap
    for(int gap = n/2; gap > 0; gap/=2){
        for(int i=gap; i<n; i++){
            int tmp = vetor[i];
            int j = i;

            while(j>=gap && vetor[j-gap] > tmp){
                vetor[j] = vetor[j-gap];
                j-=gap; 
            }

            vetor[j] = tmp;

        }
    }
}

// ! Mergesort
/* Complexidade no melhor caso: n x log n
   Complexidade no pior caso: n x log n
   Utilização: Sempre a mesma complexidade, ordenações externas
*/

public void MergeSort(int[]vetor, int esq, int dir){
    if(esq < dir){
        int meio = (esq+dir)/2;
        //ordenar as duas metades
        mergeSort(vetor,esq,meio);
        mergeSort(vetor,meio+1,dir);
        //intercalar
        merge(vetor,esq,meio,dir);
    }
}

//função de intercalar
public void Merge(int []vetor, int esq, int meio, int dir){
    int n1 = meio - esq + 1;
    int n2 = dir - meio;

    int[] esquerda = new int[n1];
    int[] direita = new int[n2];

    for(int i=0; i<n1; i++) esquerda[i] = vetor[esq+i];
    for(int i=0; i<n2; i++) direita[i] = vetor[meio + 1 + i];

    int i = 0, j = 0, k = esq;

    while(i<n1 && j<n2){
        if(esquerda[i] <= direita[j]) vetor[k++] = esquerda[i++];
        else vetor[k++] = direita[j++];
    }

    //se sobrou algo, copia o restante
    while(i<n1) vetor[k++] = esquerda[i++];
    while(j<n2) vetor[k++] = direita[j++];

}

// ! Heap
/* Complexidade no melhor caso: n x log n
   Complexidade no pior caso: n x log n
   Utilização: Sempre a mesma complexidade, precisa de pouca memória 
*/

public void Heap(){
    int[] vetor = {3, 5, 1, 0, 9, 8, 10};
    int n = vetor.length;

    //transformar o vetor em um heap 
    for(int i = (n/2 - 1); i>=0; i--) heapify(vetor,n,i);

    //extrair item por item do heap
    for(int i=n-1; i>0; i--){

        int tmp = vetor[0];
        vetor[0] = vetor[i];
        vetor[i] = tmp;

        heapify(vetor,i,0);
    }
}

public void heapify(int[] vetor, int tam, int i){
    int maior = i;
    int esquerda = 2*i + 1; //filho 1
    int direita = 2*i + 2; //filho 2

    if(esquerda < tam && vetor[esquerda] > vetor[maior]){
        maior = esquerda;
    }

    if(direita > tam && vetor[direita] < vetor[maior]){
        maior = direita;
    }

    if(maior != i){
        int tmp = vetor[i];
        vetor[i] = vetor[maior];
        vetor[maior] = tmp;

        heapify(vetor,tam,maior);
    }
}

// ! Counting
/* Complexidade no melhor caso: (n+k) sendo k o maior num do vetor
   Complexidade no pior caso: (n+k) sendo k o maior num do vetor
   Utilização: Rápido para inteiros pequenos
*/

public void Counting(int[] vetor, int n){
    int max = vetor[0];
    for(int i=1; i<n; i++){
        if(vetor[i] > max ) max = vetor[i];
    }

    int[] contagem = new int[max+1];

    //contar quantas vezes cada um aparece
    for(int i=0; i<n; i++) contagem[vetor[i]]++;

    //reconstrução
    int index = 0;
    for(int i=0; i<contagem.length; i++){
        while(contagem[i]>0){
            vetor[index++] = i;
            contagem[i]--;
        }
    }
}

//* Estruturas de dados flexíveis ========================
//? Obs: Sem contar com a matriz, as células das estruturas flexíveis seguem o
//? mesmo modelo, que é: 

//? Podemos considerar com ou sem o nó cabeça, isso deve ser explicito na pergunta 
//? ou até no comentário do código

class Celula {
    int elemento; //número dentro da caixinha
    Celula prox; //ponteiro da caixinha 

    Celula(int x) {
        this.elemento = x;
        this.prox = null;
    }
}

// ! Pilha flexível (sem nó cabeça)
// O primeiro a entrar é o último a sair, são empilhados (LIFO)
public class Pilhaflex{ //mudar o nome do arquivo se for executar 
    public Celula topo;

    public Pilhaflex(){
        topo = null;
    }

    //inserir -> desconsiderando o nó cabeça
    public void inserir(int x){
        Celula tmp = new Celula(x);
        tmp.prox = topo;
        topo = tmp;
        tmp = null; 
    }

    //remover -> desconsiderando o nó cabeça
    public int remover(){
        //remover o topo
        int elemento = topo.elemento;
        Celula tmp = topo;
        topo = topo.prox;
        tmp.prox = null;
        tmp = null;
        return elemento;
    }

    public void mostrar(){
        for(Celula i = topo; i != null; i=i.prox){
            System.out.println(i.elemento);
        }
    }
}

// ! Fila flexível (com nó cabeça)
//O primeiro a entrar é o primeiro a sair (FIFO)

public class FilaFlex{ //mudar o nome do arquivo se for executar 
    public Celula primeiro;
    public Celula ultimo; 

    public FilaFlex(){
        primeiro = new Celula(0);
        ultimo = primeiro;
    }

    //inserir -> considerando nó cabeça
    public void inserir(int x){
        //inserir no fim
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    //remover -> considerando nó cabeça
    public int remover(){
        //remover no inicio
        Celula tmp = primeiro.prox;
        int elemento = tmp.elemento;
        primeiro = primeiro.prox;
        tmp.prox = null;
        tmp = null;
        return elemento;
    }

    public void mostrar(){
        for(Celula i = primeiro; i!=null; i=i.prox){
            System.out.println(i.elemento);
        }
    }
}

// ! Lista flexivel simples (com nó cabeça)
//Pode-se inserir e remover de qualquer lugar

public class ListaFlex{ //mudar o nome do arquivo se for usar
    public Celula primeiro;
    public Celula ultimo;

    public ListaFlex(){
        primeiro = new Celula(0);
        ultimo = primeiro;
    }

    //inserir no início
    public void inserir_inicio(int x){
        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        tmp = null;
    }

    //inserir no fim
    public void inserir_fim(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    //inserir no meio
    public void inserir_meio(int x, int pos){
        int tam = getTam();
        if(pos > tam) return;
        else if(pos < 0) return; 
        else if(pos == 0) inserir_inicio(x);
        else if(pos == tam) inserir_fim(x);
        else {
            //andar com o ponteiro até antes de onde insere
            Celula i = primeiro;
            for(int j=0; j<pos; i=i.prox,j++); 
            Celula tmp = new Celula(x);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
    }

    //remover no inicio
    public int remover_inicio(){
        //considerando a existencia de um nó cabeça
        Celula tmp = primeiro.prox;
        int elemento = tmp.elemento;
        primeiro.prox = tmp.prox;
        tmp.prox = null;
        tmp = null;
        return elemento;
    }

    //remover no fim
    public int remover_fim(){
        //considerando a existencia de um nó cabeça
        int elemento = ultimo.elemento; 
        Celula i;
        //andar com o ponteiro
        for(i = primeiro; i.prox != ultimo; i=i.prox);

        //se há apenas um elemento, sobrará apenas o nó cabeça
        if(primeiro.prox == ultimo){
            ultimo = primeiro;
            primeiro.prox = null;
        }
        else {
            ultimo = i;
            ultimo.prox = null;
        }
        return elemento;
    }

    //remover no meio
    public int remover_meio(int pos){
        //aqui podemos fazer verificações
        int tam = getTam();
        if(pos > tam) return 0;
        else if(pos < 0) return 0; 
        else if(pos == 0) remover_inicio();
        else if(pos == tam-1) remover_fim();

        else {
            int j; Celula i;
            //anda com o ponteiro
            for(j=0, i = primeiro; j < pos; j++, i=i.prox);
            int elemento = i.prox.elemento;
            Celula k = i.prox;
            i.prox = k.prox;
            k.prox = null;
            i = k = null;
            return elemento;
        }
    }

    //função de mostrar
    public void mostrar(){
        for(Celula i = primeiro; i!=null; i=i.prox){
            System.out.println(i.elemento);
        }
    }

    public int getTam(){
        int tam = 0;
        for(Celula i=primeiro; i!=null; i=i.prox) tam++;
        return tam;
    }
}

// ! Lista flexivel dupla (com nó cabeça)
//Pode-se inserir e remover de qualquer lugar
//obs: a parte boa da lista dupla é que podemos andar pros dois lados

//para isso, teríamos que usar a celula dupla
class CelulaDupla {
    int elemento; //número dentro da caixinha
    CelulaDupla prox; //ponteiro da caixinha 
    CelulaDupla ant;

    CelulaDupla(int x) {
        this.elemento = x;
        this.prox = null;
        this.ant = null;
    }
}

public class ListaFlexDupla { //trocar o nome do arquivo se for usar
    public CelulaDupla primeiro;
    public CelulaDupla ultimo;

    public ListaFlexDupla(){
        primeiro = new CelulaDupla(0);
        ultimo = primeiro;
    }

    public void inserir_inicio(int x){
        //considerando a existencia de um no cabeca
        CelulaDupla tmp = new CelulaDupla(x);
        tmp.prox = primeiro.prox;
        tmp.ant = primeiro;

        //agora que conectamos na lista, temos que colocar o resto
        primeiro.prox.ant = tmp;
        primeiro.prox = tmp;

        tmp = null;
    }

    public void inserir_fim(int x){
        CelulaDupla tmp = new CelulaDupla(x);
        ultimo.prox = tmp;
        tmp.ant = ultimo;
        tmp = null;
        ultimo = ultimo.prox; //atualiza o ponteiro
    }

    public void inserir_meio(int pos, int x){
        int tam = getTam();
        if(pos > tam || pos < 0) return;
        else if(pos == 0) inserir_inicio(x);
        else if(pos == tam) inserir_fim(x);
        else {
            CelulaDupla i = primeiro;
            for(int j=0; j<pos; j++,i=i.prox);
            //para antes da posição que tem que inserir 
            CelulaDupla tmp = new CelulaDupla(x);
            tmp.prox = i.prox;
            tmp.ant = i;
            i.prox = tmp;
            tmp.prox.ant = tmp;
        }
    }

    //remover no inicio
    public int remover_inicio(){
        //considerando nó cabeça
        int elemento = primeiro.prox.elemento;
        CelulaDupla i = primeiro.prox;
        primeiro.prox = i.prox;
        // se ainda houver alguém depois, ajusta o ant
        if (i.prox != null) {
            i.prox.ant = primeiro;
        }
        i.ant = i.prox = null;
        i = null;
        return elemento;
    }

    public int remover_fim(){
        //já que em java temos coleta de lixo, podemos fazer assim
        //obs: esse código considera a existência de um nó cabeça e de pelo menos 1 elemento
        int elemento = ultimo.elemento;
        ultimo = ultimo.ant;
        ultimo.prox = null;
        return elemento;
    }

    public int remover_meio(int pos){
        int tam = getTam();
        if(pos < 0 || pos > tam) return -1;
        else if(pos == 0) remover_inicio();
        else if(pos == tam-1) remover_fim();
        else {
            CelulaDupla i = primeiro;
            for(int j=0; j<pos; j++, i=i.prox); //só vai andar
            //chegou em uma posicao antes da que queremos remover
            int elemento = i.prox.elemento;
            CelulaDupla k = i.prox; //caixa a ser removida
            i.prox = k.prox;
            k.prox.ant = i;
            k.ant = null;
            k.prox = null;
            k = null;
            return elemento; 
        }
    }
}

// ! Matriz flexível
//Conta com um ponteiro de início que aponta para a célula (0,0)
//tem ant, prox, sub e inf

//Classe e construtores
class CelulaMatriz { 
    int elemento;
    CelulaMatriz ant, prox; //ponteiros laterais ou horizontais
    CelulaMatriz sup, inf; //ponteiros verticais

    CelulaMatriz(){
        this(0);
    }

    CelulaMatriz(int x){
        this.elemento = x;
        this.sup = this.ant = this.prox = this.inf = null;
    }
}

class Matriz { //trocar o nome do arquivo caso for usar
    CelulaMatriz inicio; 
    int linha; int coluna; 

    //Construtor
    Matriz(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
        construirMatriz();
    }

    private void construirMatriz(){
        CelulaMatriz i,j,k,t; 
        inicio = new CelulaMatriz(); //ponteiro que aponta pra primeira celula

        //Criação da primeira linha
        i = inicio;
        for(int var=1; var < coluna; var++){
            i.prox = new CelulaMatriz();
            i.prox.ant = i;
            i = i.prox;
        }

        //Cria as demais linhas
        t = inicio;
        for(int var=1; var < linha; var++){
            i = new CelulaMatriz();
            t.inf = i;
            t.sup = t;

            //liga horizontalmente
            j = i;
            k = t.prox;

            for(int var2=1; var2<coluna; var2++){
                j.prox = new CelulaMatriz();
                j.prox.ant = j;
                j = j.prox;

                j.sup = k;
                k.inf = j;
                k = k.prox;
            }

            t = t.inf;
        }
    }

    public void inserir(int linha, int coluna, int valor){
        CelulaMatriz tmp = inicio;
        int i,j;
        //andar com o i e o j
        for(i=0; i<linha; i++) tmp = tmp.inf;
        for(j=0; j<linha; j++) tmp = tmp.prox;
        tmp.elemento = valor;
    }

    public int getElemento(int linha, int coluna){
        //retornar o elemento em alguma posição dada linha e coluna
        int l,c;
        CelulaMatriz i = inicio;
        for(l=0;l<linha;l++) i=i.inf; //anda com as linhas pra baixo
        for(c=0;c<coluna;c++) i=i.prox; //anda com as colunas pro lado
        int ele = i.elemento;
        return ele; 
    }

    public void printMainDiag(int linha, int coluna){
        if(linha != coluna) return; //tem que ser quadrada
        CelulaMatriz i = inicio;
        for(int j=0; j<linha; j++){
            System.out.println(i.elemento);
            if(i.prox == null || i.prox.inf == null) break;
            i = i.prox.inf;
        }
    }

    public void printSecondDiag(int linha, int coluna){
        if(linha != coluna) return; //tem que ser quadrada

        //andar até o final das colunas (topo direito)
        CelulaMatriz i = inicio;
        while(i.prox != null) i=i.prox;

        //agora descer e ir a esquerda
        for(int j=0; j<linha; j++){
            System.out.println(i.elemento);
            if(i.ant == null || i.ant.inf == null) break;
            i=i.ant.inf;
        }
    }

    public void mostrar(){
        CelulaMatriz linhaA = inicio; //linha Atual

        while(linhaA != null){
            CelulaMatriz colunaA = linhaA; //coluna Atual
            
            while(colunaA != null){
                System.out.print(colunaA.elemento + " ");
                colunaA = colunaA.prox;
            }

            System.out.println(); //\n
            linhaA = linhaA.inf;
        }
    }

    /*Exercício extra - Lista em Matriz
    Cada nó da matriz tem um ponteiro de primeiro e é o nó cabeça de uma lista. 
    Escreva uma função que passa como parametro a linha e a coluna para acharmos 
    o nó cabeça na matriz e a posicao que queremos imprimir da lista, 
    considerando o nó cabeça como a posição zero.
    */

    public void listaEmMatriz(int linha, int coluna, int pos){
        CelulaMatriz i = inicio;
        int j,k;
        
        //percorrer as linhas
        for(j=0; j<linha; j++) i=i.inf;
        //percorrer as colunas
        for(k=0; k<coluna; k++) i=i.prox;

        //Agora que chegamos na posição do nó cabeça, procurar o elemento
        int c;
        Celula prim = i.primeiro; //ponteiro primeiro do nó cabeça
        //obs: deve ser um ponteiro celula e não Célula Matriz pois percorre a lista!
        for(c=0; c<pos; c++) prim = prim.prox;

        //quando acabar, imprimir o elemento
        System.out.println(prim.elemento);
    }
}

// ! Observação importante
/* É possível que os professores misturem duas ou mais matérias e peçam para aplicar
os algoritmos de ordenação em estruturas de dados flexíveis. Para isso, seria necessário
mudar muitas partes do código, e logo seria trabalhoso e diferente pra cada um. 
Uma opção que vou adotar nestas próximas linhas é usar de duas funções auxiliares que
simulem um vetor. Assim, essas funções deixarão os códigos mais modularizados e impedirão
mudanças bruscas em relação aos originais
*/

//Função auxiliar 01: busca 
//recebe uma posição e retorna o valor daquela posição
//vou usar como exemplo a lista
public int busca(int pos){
    if(pos < 0 /*|| pos > tam*/) return -1;

    //se chegou até aqui, posição válida
    Celula i = primeiro; int j;
    for(j=0; j<pos; j++) i = i.prox;

    //depois do for, o i está a uma casa antes 
    return i.prox.elemento;
}

//Função auxiliar 02: replace
//recebe uma posição e o elemento que queremos colocar
//troca o elemento da posição pelo pedido
//é um tipo de swap
public void replace(int pos, int element){
    if(pos < 0 /*|| pos > tam*/) return;

    //se chegou até aqui, posição válida
    Celula i = primeiro; int j;
    for(j=0; j<pos; j++) i = i.prox;

    i.elemento = element;
}

//* Coleta de lixo ========================
/* A coleta de lixo é um método que facilita a vida de quem mexe com Java. 
Itens que perdem a referência, ou seja, não tem nenhum ponteiro apontado para eles, 
são coletados pelo Garbage Collector quando é necessária a utilização de espaço. 
*/

//* Prós: não é necessário dar free, mais tranquilo para o dev
//! Contras: não se tem controle de quando será removido pela máquina virtual Java

/*Obs: é importante lembrar que linguagens como C e C++ não possuem a coleta de 
lixo automática, e por isso é necessário dar o free antes de atribuir a nulo
*/



















//TODO - Funções auxiliares 
//TODO - SWAP
public static void swap(int i, int j, int[] array) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}

//TODO - PEGAR O TAMANHO
public static int getTam(Celula primeiro, Celula prox){ //lista
    int tam = 0;
    for(Celula i = primeiro; i != null; i = i.prox) tam++;
    return tam;
}