// * Esse documento conterá as matérias para a prova 2 de AED. 
// ! Matérias:
// ? (Códigos de ordenação) 
/*
- Selection
- Bubble
- Insertion
- Heap
- Quick
- Merge 
- Shell
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