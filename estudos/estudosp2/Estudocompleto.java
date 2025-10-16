// * Esse documento conterá as matérias para a prova 2 de AED. 
// ! Matérias:
// ? (Códigos de ordenação) 
/*
- Selection - ok
- Bubble - ok
- Insertion - ok
- Heap
- Quick - ok
- Merge - ok
- Shell - ok
- Counting
*/

// ? (Estruturas de dados flexíveis)
/*
- Pilha flex
- Fila flex
- lista simples flex
- Lista dupla flex 
- Matriz flex 
*/

// ? (Extras)
/*
- Coleta de lixo 
- Buscas parciais
*/

//* Começo das matérias 
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




















//TODO - Funções auxiliares 
//TODO - SWAP
public static void swap(int i, int j, int[] array) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}