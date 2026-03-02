#include <stdio.h>
#include <string.h>

//fazer a questão de forma recursiva
void recursiva(char str[100], int i, int fim){
    if(i >= fim){
        printf("SIM\n");
        return;
    }
    else if (str[i] != str[fim]) {
        printf("NAO\n");
        return;
    }
    else recursiva(str, i+1, fim-1);
}

int main(){
    //pegar a string
    char str[100];
    fgets(str,sizeof(str),stdin); //pegar a string, mesmo com espaço

    // Remove o '\n' do final da string, se existir
    str[strcspn(str, "\n")] = '\0';

    // Remove o '\r' da string, se existir
    str[strcspn(str, "\r")] = '\0';

    //chamar a função
    while(strcmp(str, "FIM") != 0){
        int fim = strlen(str)-1;
        int i=0;
        recursiva(str,i,fim);
        
        //pegar a nova string
        fgets(str,sizeof(str),stdin); //pegar a string, mesmo com espaço
        str[strcspn(str, "\n")] = '\0';
        str[strcspn(str, "\r")] = '\0';
    }
    

    return 0;
}