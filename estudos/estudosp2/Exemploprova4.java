/*
QUESTÃO 02 – Van Escolar

Uma escola contrata uma van para levar os alunos até suas casas.
Cada aluno deve informar:

- Seu nome (sem espaços);
- A região onde mora, representada por uma única letra maiúscula (N, S, L, O);
- E a distância, em quilômetros, da escola até sua casa (um número inteiro).

A empresa responsável pela van deseja organizar a ordem das entregas dos alunos
seguindo os seguintes critérios de prioridade:

1. Menor distância primeiro;
2. Em caso de empate na distância, ordenar por região em ordem alfabética crescente;
3. Se ainda houver empate, ordenar por nome em ordem alfabética crescente.

O programa deve:
1. Ler um número inteiro q (quantidade de alunos);
2. Ler, para cada aluno, seu nome, região e distância;
3. Armazenar os dados em um vetor de objetos;
4. Ordenar os alunos conforme as regras acima;
5. Imprimir apenas os nomes dos alunos na nova ordem, um por linha.

Exemplo de entrada:
5
Maria N 10
Joao L 5
Pedro N 5
Ana L 10
Lucas O 5

Saída esperada:
Joao
Lucas
Pedro
Ana
Maria
*/



import java.util.*;

public class Van {
    static class Aluno {
        String nome; //nome do aluno
        char r; //regiao que o aluno mora (L,N,O,S)
        int distancia; //distancia 
    }

    //função que compara as situações
    //devolve 1 se tem que trocar
    //devolve 0 se não tem
    static int compara(Aluno a, Aluno b){
        //ordenar por distancia
        if(a.distancia > b.distancia) return 1;
        else if(a.distancia < b.distancia) return 0;

        else { //ordenar por regiao
            if(a.r > b.r) return 1;
            else if(a.r < b.r) return 0;

            else {
                return comparaNome(a.nome,b.nome, 0);
            }
            
        }
    }

    //função que compara nome especificamente
    static int comparaNome(String a, String b, int j){
        if(j == a.length() || j == b.length()){
            //se chegou no final das palavras e elas são iguais, retorna a menor
            if(a.length() < b.length()) return 0;
            else return 1;
        }
            
        char aa = a.charAt(j);
        char bb = b.charAt(j);

        if(aa > bb) return 1;
        else if(aa < bb) return 0;
        else return comparaNome(a,b,j+1);
    }
    
    public static void main(String []args){
        Scanner sc = new Scanner(System.in);
        int q; //quantidade de alunos
        q = sc.nextInt();

        //vetor de alunos
        Aluno []vetor = new Aluno[q];

        for(int i=0; i<q; i++){
            //ler os objetos
            vetor[i] = new Aluno(); //criar o objeto

            String n = sc.next();
            String stringre = sc.next();
            int dis = sc.nextInt();

            //como nao ha metodo de ler char, obter o char
            char re = stringre.charAt(0);

            vetor[i].nome = n;
            vetor[i].r = re;
            vetor[i].distancia = dis;
        }

        //agora que temos os dados, ordenar
        //ordenar com bubble

        for(int i=0; i<q-1; i++){
            for(int j=0; j<q-1-i; j++){
                int result = compara(vetor[j], vetor[j+1]);
                if(result == 1){
                    //swap
                    Aluno tmp = vetor[j];
                    vetor[j] = vetor[j+1];
                    vetor[j+1] = tmp;
                }
            }
        }

        //agora, printar as respostas
        for(int i=0; i<q; i++){
            System.out.println(vetor[i].nome);
        }
        
    }
}