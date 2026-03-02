/*
    Problema: Fila do Recreio

    Enunciado:
    Durante o recreio, os alunos formam uma fila para comprar lanche. 
    O professor anotou a ordem em que eles chegaram e, em seguida, 
    pediu que a fila fosse reorganizada de forma que os alunos ficassem 
    em ordem decrescente de nota (ou pontuação).

    Sua tarefa é determinar quantos alunos permaneceram na mesma posição
    depois que a fila foi reorganizada.

    Entrada:
    - A primeira linha contém um inteiro N, representando o número de casos de teste.
    - Para cada caso de teste:
        - Uma linha contém um inteiro M, representando o número de alunos.
        - A linha seguinte contém M inteiros, representando as notas dos alunos,
          na ordem em que estavam originalmente na fila.

    Saída:
    - Para cada caso de teste, imprima o número de alunos que permaneceram 
      na mesma posição após a ordenação decrescente das notas.

    Exemplo de entrada:
    1
    5
    100 80 90 70 60

    Saída correspondente:
    3

    Explicação:
    A fila ordenada em ordem decrescente seria: [100, 90, 80, 70, 60]
    Os alunos que não mudaram de posição foram os das posições 0, 3 e 4.
*/

import java.util.*;

public class Fila {
    public static void main(String []args){
        Scanner sc = new Scanner(System.in);
        int n; //número de casos de teste
        n = sc.nextInt();

        int cont = 0;
        while(cont < n){
            int m; //número de alunos
            m = sc.nextInt();

            //criar dois vetores: um com a ordem original e outro que será ordenado
            int[] antes = new int[m];
            int[] notas = new int[m];

            for(int i=0; i<m; i++){
                int num = sc.nextInt();
                antes[i] = num;
                notas[i] = num;
            }

            //ordenar o vetor de notas em ordem decrescente (Insertion Sort)
            for(int i=1; i<m; i++){
                int tmp = notas[i];
                int j = i-1;

                while(j >= 0 && notas[j] < tmp){
                    notas[j+1] = notas[j];
                    j--;
                }

                notas[j+1] = tmp;
            }

            //comparar para ver quais alunos permaneceram na mesma posição
            int alunos = 0; //contador de alunos que não mudaram de lugar

            for(int i=0; i<m; i++){
                if(antes[i] == notas[i]) alunos++;
            }

            System.out.println(alunos);
            cont++;
        }

        sc.close();
    }
}
