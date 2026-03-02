//Questão 1 ====================
/*Considere uma estrutura de Lista de Pilhas. 
Crie o método CelulaLista MaiorPilha() na sua classe Lista, que retorna a célula da lista que 
aponta para a pilha com o maior número de elementos. Caso tenham pilhas do mesmo tamanho, 
retornar a primeira que aparece. */

CelulaLista MaiorPilha(){
    //considerar o primeiro como o maior
    CelulaLista maiorLista = inicio;
    int tamPilha = 0;
    
    //percorrer toda a lista
    for(CelulaLista j = inicio; j != null; j = j.prox){
        int tam = 0; //tamanho 
        //percorrer a pilha da lista
        for(CelulaPilha k = j.topo; k != null; k = k.prox) tam++;
        if(tam > tamPilha){
            //se for maior, substitui
            tamPilha = tam;
            maiorLista = j;
        }
    }

    return maiorLista;
}

//Questão 2 ====================
/*Na árvore de árvore ilustrada abaixo, implemente o método int contarPalavras(String padrao)
que recebe uma String e retorna o número de palavras que começam com a mesma letra e possuam
a mesma quantidade de caracteres. Ex: se a palavra passada fosse "PROVA", deveriam ser 
retornadas quantas palavras começam com P e possuam 5 caracteres. Obs: somente caracteres
maiúsculos utilizados. Faça a análise de complexidade do seu método. */

int contarPalavras(String padrao){
    //chegar até a sub árvore que começa com a inicial da string
    char c = padrao.charAt(0); //No caso de PROVA, seria P
    No j = raiz;

    //implementar de forma iterativa
    while(j != null) {
        if(c == j.elemento){
            //chegou na árvore correta, entrar na sub árvore
            No2 k = j.raiz; //aponta para a raiz do j

            //chamar a função para a sub árvore
            int palavras = contarPalavras(k, padrao);
            return palavras;
        }

        else if(c < j.elemento) j = j.esq;
        else if(c > j.elemento) j = j.dir;
    }

    //se chegou até aqui, não achou
    return -1;
}

//função de contar palavras pro Nó 2 (sub árvore)
int contarPalavras(No2 k, String padrao){
    //obs: p é a quantidade de palavras até agora
    //percorrer a string e aumentar o contador
    if(k == null) return 0;

    int cont = 0;
    int tam = padrao.length();

    if(k.elemento.length() == tam) cont++;
    cont += contarPalavras(k.esq, padrao);
    cont += contarPalavras(k.dir, padrao);
}

/*Como o método chama recursivamente ao segundo e percorre todos os elementos para
comparar o tamanho, sua complexidade é de O(n) no melhor caso, sendo n a quantidade de elementos
na subárvore desejada, considerando que por mais que a letra esteja na raiz, ainda será
necessário procurar por todos os elementos da subárvore. No pior caso, a complexidade é O(nxm), percorrendo
tanto toda a árvore do No normal quanto a subárvore. */

//Questão 3 ====================
/*Implemente o método public static int[] vetorOrdenado(int [] vetA, int[] vet B) que retorna um terceiro vetor 
ordenado, de tamanho n+m. O primeiro array possui elementos pares ordenados de forma decrescente, e o 
segundo array elementos ímpares, ordenados de forma decrescente. O array ordenado deverá
possuir os valores dos dois arrays ordenados de forma crescente. O método deve ter a complexidade
O(m+n). */

public static int[] vetorOrdenado(int[] vetA, int[] vetB){
    int m = vetA.length();
    int n = vetB.length();
    int tam = n+m; 

    int[] vetC = new int[tam]; //vetor que será ordenado

    int i = 0; //contador do vetor
    int a = m-1; //começa do final de A
    int b = n-1; //começa do final de B
    
    while(a>= 0 && b >= 0){
        if(vetA[a] <= vetB[b]){
            vetC[i] = vetA[a];
            a--; i++;
        }
        else if(vetA[a] > vetB[b]){
            vetC[i] = vetB[b]; 
            b--; i++;
        }
    }

    //colocou até um dos dois acabar
    //agora colocar o resto
    //se for o vetA
    while(a >= 0){
        vetC = vetA[a];
        a--; i++;
    }

    //se for vetB
    while(b >= 0){
        vetC = vetB[b];
        b--; i++;
    }

    return vetC;
}