//criar 4 métodos iterativos
//método 1 -> se apenas tem vogais
//método 2 -> se apenas tem consoantes
//método 3 -> retorna true se for inteiro
//método 4 -> retorna true se for numero real

//obs: string vai passar por todos os métodos
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// Método 1 -> se apenas tem vogais
void vogais(char string[100], int tam){
    int verdade = 1; // assume que é verdade
    for (int i=0; i<tam; i++){
        char c = string[i]; //facilitar e colocar na variavel
        //confere se não são vogais
        if(!(c=='A'||c=='a'||c=='E'||c=='e'||c=='I'||c=='i'||c=='O'||c=='o'||c=='U'||c=='u')){
            verdade = 0;
            break;
        }
    }
    if(verdade == 1) printf("SIM");
    else printf("NAO");
}

// Método 2 -> se apenas tem consoantes
void consoantes(char string[100], int tam){
    int verdade = 1;
    for(int i=0; i<tam; i++){
        char c = string[i];
        if(!((c>='A' && c<='Z') || (c>='a' && c<='z'))){ 
            verdade = 0; // não é letra
            break;
        }
        if(c=='A'||c=='a'||c=='E'||c=='e'||c=='I'||c=='i'||c=='O'||c=='o'||c=='U'||c=='u'){
            verdade = 0; // é vogal
            break;
        }
    }
    if(verdade) printf("SIM");
    else printf("NAO");
}

// Método 3 -> retorna true se for inteiro
void inteiro(char string[100], int tam){
    int verdade = 1;
    for(int i=0; i<tam; i++){
        if(!(string[i] >= '0' && string[i] <= '9')){
            verdade = 0;
            break;
        }
    }
    if(verdade) printf("SIM");
    else printf("NAO");
}

// Método 4 -> retorna true se for número real
void numeroreal(char string[100], int tam){
    int verdade = 1;
    int separador = 0; // conta quantos '.' ou ',' tem
    for(int i=0; i<tam; i++){
        if(string[i] == '.' || string[i] == ','){
            separador++;
            if(separador > 1){ // só pode ter um separador
                verdade = 0;
                break;
            }
        }
        else if(!(string[i] >= '0' && string[i] <= '9')){
            verdade = 0;
            break;
        }
    }
    if(verdade) printf("SIM");
    else printf("NAO");
}

#include <ctype.h>

// retorna 1 se s == "FIM" (case-insensitive), 0 caso contrário
int isFim(const char *s){
    return toupper((unsigned char)s[0])=='F' &&
           toupper((unsigned char)s[1])=='I' &&
           toupper((unsigned char)s[2])=='M' &&
           s[3]=='\0';
}

int main(){
    char entrada[500];

    while (fgets(entrada, sizeof(entrada), stdin)) {
        // remove \n ou \r\n do final
        size_t tam = strcspn(entrada, "\r\n");
        entrada[tam] = '\0';

        if (isFim(entrada)) break;
        if (tam == 0) continue; // evita linha extra

        // chama os métodos
        vogais(entrada, (int)tam);
        printf(" ");
        consoantes(entrada, (int)tam);
        printf(" ");
        inteiro(entrada, (int)tam);
        printf(" ");
        numeroreal(entrada, (int)tam);
        printf("\n");
    }
    return 0;
}


