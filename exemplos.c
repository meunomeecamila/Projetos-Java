// =========================================
// Arquivo: funcoes.c
// Trabalho - Comparação com Haskell
// =========================================

#include <stdio.h>
#include <string.h>

// -----------------------------------------
// 1. Dobro de um número
// -----------------------------------------
int dobro(int x) {
return x * 2;
}


// -----------------------------------------
// 2. Verificar se um número é par
// -----------------------------------------
int ehPar(int n) {
return n % 2 == 0;
}


// -----------------------------------------
// 3. Fatorial (recursão)
// -----------------------------------------
int fatorial(int n) {
if (n == 0) return 1;
return n * fatorial(n - 1);
}


// -----------------------------------------
// 4. Soma de elementos de um array
// -----------------------------------------
int somaLista(int arr[], int tamanho) {
int soma = 0;
for (int i = 0; i < tamanho; i++) {
soma += arr[i];
}
return soma;
}


// -----------------------------------------
// 5. Filtrar nomes (simulação)
// -----------------------------------------
void filtrarNomes(char nomes[][50], int tamanho) {
for (int i = 0; i < tamanho; i++) {
if (strlen(nomes[i]) > 5) {
printf("%s\n", nomes[i]);
}
}
}


// -----------------------------------------
// 6. QuickSort
// -----------------------------------------
void quicksort(int arr[], int inicio, int fim) {
int i = inicio, j = fim;
int pivo = arr[(inicio + fim) / 2];

while (i <= j) {
while (arr[i] < pivo) i++;
while (arr[j] > pivo) j--;

if (i <= j) {
int temp = arr[i];
arr[i] = arr[j];
arr[j] = temp;
i++;
j--;
}
}

if (inicio < j) quicksort(arr, inicio, j);
if (i < fim) quicksort(arr, i, fim);
}


// -----------------------------------------
// 7. Fibonacci (sem lazy evaluation)
// -----------------------------------------
int fib(int n) {
if (n <= 1) return n;
return fib(n - 1) + fib(n - 2);
}


// -----------------------------------------
// 8. Simulação de "SELECT"
// -----------------------------------------
typedef struct {
int id;
char nome[50];
} Usuario;

void buscarId(Usuario usuarios[], int tamanho, int idBusca) {
for (int i = 0; i < tamanho; i++) {
if (usuarios[i].id == idBusca) {
printf("%s\n", usuarios[i].nome);
}
}
}


// -----------------------------------------
// 9. Função de função (simulação)
// -----------------------------------------
int somarCinco(int x) {
return x + 5;
}

int dobro2(int x) {
return x * 2;
}

int dobroMaisCinco(int x) {
return dobro2(somarCinco(x));
}


// -----------------------------------------
// 10. Currificação (simulação)
// -----------------------------------------
int soma(int x, int y) {
return x + y;
}

// "Aplicação parcial" simulada
int somar10(int y) {
return soma(10, y);
}


// -----------------------------------------
// Função principal (teste)
// -----------------------------------------
int main() {

printf("Dobro: %d\n", dobro(5));
printf("Eh par: %d\n", ehPar(4));
printf("Fatorial: %d\n", fatorial(5));

int lista[] = {1, 2, 3, 4};
printf("Soma lista: %d\n", somaLista(lista, 4));

char nomes[][50] = {"Ana", "Camila", "Joao", "Fernanda"};
printf("Nomes com mais de 5 letras:\n");
filtrarNomes(nomes, 4);

int arr[] = {5, 3, 8, 1, 2};
quicksort(arr, 0, 4);
printf("QuickSort: ");
for (int i = 0; i < 5; i++) {
printf("%d ", arr[i]);
}
printf("\n");

printf("Fibonacci (10): %d\n", fib(10));

Usuario usuarios[] = {
{1, "Guilherme"},
{2, "Ane"},
{3, "Maria"},
{4, "Camila"}
};
printf("Buscar ID 2:\n");
buscarId(usuarios, 4, 2);

printf("Dobro + cinco: %d\n", dobroMaisCinco(3));
printf("Somar 10: %d\n", somar10(5));

return 0;
}