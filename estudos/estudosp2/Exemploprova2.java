/*
============================================================
Exercício: Sorveteiros na Praia
============================================================

Em uma praia de comprimento P metros, há S sorveteiros.
Cada sorveteiro ocupa um intervalo contínuo da praia,
definido por dois números inteiros:
 - o ponto de início
 - o ponto de fim

Alguns sorveteiros podem ter áreas de atuação que se
sobrepõem. O objetivo é agrupar os intervalos que se
interceptam e mostrar a extensão total de cada grupo.

O programa deve ler os intervalos de cada sorveteiro,
fundir aqueles que se sobrepõem e imprimir os intervalos
resultantes após a fusão.

------------------------------------------------------------
ENTRADA:
P               → comprimento total da praia
S               → quantidade de sorveteiros
<inicio1> <fim1>
<inicio2> <fim2>
...
<inicioS> <fimS>

------------------------------------------------------------
SAÍDA:
Para cada grupo de intervalos sobrepostos, imprimir
os valores de início e fim do grupo.

------------------------------------------------------------
EXEMPLO:
Entrada:
10
4
1 3
2 5
6 8
8 9

Saída:
1 5
6 9

------------------------------------------------------------
EXPLICAÇÃO:
- Os sorveteiros 1 e 2 se sobrepõem (1–3 e 2–5), formando o grupo 1–5.
- Os sorveteiros 3 e 4 também se sobrepõem (6–8 e 8–9), formando o grupo 6–9.
============================================================
*/


import java.util.*;

public class Sorveteiro {
    int inicio; 
    int fim;

    public static void main(String []args){
        Scanner sc = new Scanner(System.in);

        int p; //metros da praia
        p = sc.nextInt();
        int s; //quantidade de sorveteiros
        s = sc.nextInt();

        Sorveteiro []sorvets = new Sorveteiro[s];
            
        //ler s vezes o inicio e o fim
        //guardar no vetor
        for(int i=0; i<s; i++){
            int u; //inicio
            u = sc.nextInt();

            int v; //fim
            v = sc.nextInt();

            sorvets[i] = new Sorveteiro();
            sorvets[i].inicio = u;
            sorvets[i].fim = v;
        }

        int inicio = sorvets[0].inicio; //inicio do primeiro intervalo
        int fim = sorvets[0].fim; //fim do primeiro intervalo

        //percorrer e comparar cada sorveteiro
        for(int i=1; i<s; i++){

            //se o próximo sorveteiro começar antes do fim do grupo atual
            if(sorvets[i].inicio <= fim){
                //então há interseção e o grupo continua

                //se o fim do próximo for maior, estende o grupo
                if(sorvets[i].fim > fim){
                    fim = sorvets[i].fim;
                }
            }
            else {
                //caso contrário, não há mais interseção com o grupo atual
                //então imprime o grupo que terminou
                System.out.println(inicio + " " + fim);

                //reinicia um novo grupo com o próximo sorveteiro
                inicio = sorvets[i].inicio;
                fim = sorvets[i].fim;
            }
        }

        //após o loop, imprime o último grupo acumulado
        System.out.println(inicio + " " + fim);

        sc.close();
    }
}
