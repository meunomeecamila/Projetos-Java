/*
    Problema:
    Dado um conjunto de números de telefone, calcule o total de dígitos
    iguais nos prefixos entre cada par de números consecutivos
    após ordená-los lexicograficamente.

    Para cada telefone (a partir do segundo), compare com o telefone
    imediatamente anterior e conte quantos dígitos iniciais são iguais
    até encontrar uma diferença. Some todos esses valores e imprima o
    resultado final.

    A entrada contém vários casos de teste até EOF.
    Cada caso começa com um inteiro N, seguido por N números de telefone.
    A saída deve mostrar, para cada caso, a soma total dos prefixos comuns.

    Entrada:
    2
    12345
    12354
    3
    535456
    535488
    835456

    Saída:
    3
    4
*/


import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            String[] numeros = new String[n];

            for (int i = 0; i < n; i++) {
                numeros[i] = sc.next();
            }

            // ----------- ORDENAR (substitui bubble sort) ----------
            Arrays.sort(numeros);
            // ------------------------------------------------------

            int vezes = 0;

            int tam = numeros[0].length();

            // Comparar cada número com o anterior
            for (int i = 1; i < n; i++) {
                int vezestemp = 0;

                for (int j = 0; j < tam; j++) {
                    if (numeros[i - 1].charAt(j) != numeros[i].charAt(j)) {
                        break;
                    } else {
                        vezestemp++;
                    }
                }

                vezes += vezestemp;
            }

            System.out.println(vezes);
        }

        sc.close();
    }
}
