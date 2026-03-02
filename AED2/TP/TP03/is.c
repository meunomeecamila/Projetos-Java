//criar 4 métodos iterativos
//método 1 -> se apenas tem vogais
//método 2 -> se apenas tem consoantes
//método 3 -> retorna true se for inteiro
//método 4 -> retorna true se for numero real

//obs: string vai passar por todos os métodos
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

// retorna 1 se s == "FIM" (case-insensitive), 0 caso contrário
int isFim(const char *s){
    return toupper((unsigned char)s[0])=='F' &&
           toupper((unsigned char)s[1])=='I' &&
           toupper((unsigned char)s[2])=='M' &&
           s[3]=='\0';
}

//Método 1 -> vogal
int recursivaV(char entrada[500],int v){
    if(entrada[v] == '\0') printf("SIM");
    else if(!(entrada[v]=='A'||entrada[v]=='a'||entrada[v]=='E'||entrada[v]=='e'||entrada[v]=='I'||entrada[v]=='i'||entrada[v]=='O'||entrada[v]=='o'||entrada[v]=='U'||entrada[v]=='u')){
            printf("NAO");
            return 0;
        }
    else return recursivaV(entrada,v+1);
}

//Método 2 -> consoante
int recursivaC(char entrada[500], int c){
    if(entrada[c] == '\0') printf("SIM");
    else if(!((entrada[c]>='A' && entrada[c]<='Z') || (entrada[c]>='a' && entrada[c]<='z'))){
        printf("NAO");
        return 0;
    }
    else if(entrada[c]=='A'||entrada[c]=='a'||entrada[c]=='E'||entrada[c]=='e'||entrada[c]=='I'||entrada[c]=='i'||entrada[c]=='O'||entrada[c]=='o'||entrada[c]=='U'||entrada[c]=='u'){
        printf("NAO");
        return 0;
    }
    else return recursivaC(entrada,c+1);
}

//Método 3 -> inteiro
int recursivaI(char entrada[500], int i){
    if(entrada[i] == '\0') printf("SIM");
    else if(!(entrada[i] >= '0' && entrada[i] <= '9')) printf("NAO");
    else return recursivaI(entrada, i+1);
}

//Método 4 -> real
int recursivaR(char entrada[500], int i, int separador){
    if(entrada[i] == '\0') printf("SIM");
    else if(entrada[i] == '.' || entrada[i] == ',') return recursivaR(entrada, i+1, separador+1);
    else if(separador > 1) printf("NAO");
    else if(!(entrada[i] >= '0' && entrada[i] <= '9')) printf("NAO");
    else return recursivaR(entrada,i+1, separador);
}

int main(){
    char entrada[500];

    while (fgets(entrada, sizeof(entrada), stdin)) {
        // remove \n ou \r\n do final
        size_t tam = strcspn(entrada, "\r\n");
        entrada[tam] = '\0';

        if (isFim(entrada)) break;
        if (tam == 0) continue; // evita linha extra

        //fazer tudo de forma recursiva
        int v = 0;
        recursivaV(entrada,v); //vogal
        printf(" ");

        int c = 0;
        recursivaC(entrada,c); //consoante
        printf(" ");

        int i = 0;
        recursivaI(entrada,i); //inteiro
        printf(" ");

        int r = 0;
        int separador = 0;
        recursivaR(entrada,r,separador); //real
        
        printf("\n");
    }
    return 0;
}


