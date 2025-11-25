import java.util.Scanner;

public class Algebra {
    //o scanner será utilizado nas outras funções, então passar do lado de fora
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args){
        int n; //numero de termos que serão 0 ou 1

        while ((n = scanner.nextInt()) > 0) { //sempre pegar o proximo da linha
            String expression =  readExpression(readValues(n)); //chama a função readExpression com readValues
            System.out.println(solveExpression(expression)); //chama a função solveExpression
        } //fim do while
    }// fim da main

    //Funções extras
    static String solveExpression(String expression) {
        //while for maior que 1 (tiver coisa), resolver
        while(expression.length()> 1) {
            
            // 1) Pegar o parêntese mais interno: o último '('
            /*obs: lastIndexOf pega a última vez que o item aparece , ou seja, 
            o item mais do meio*/
            int left = expression.lastIndexOf('(');
            
            // 2) Encontrar o ') correspondente a partir de left+1
            int right = left + 1;
            while (expression.charAt(right) != ')') right++; //enquanto nao for o fechamento, aumenta

            // 3) Parametrôs ficam entre os dois que acabamos de achar
            //separar em apenas uma string
            String params = expression.substring(left + 1, right);

            // 4) Descobrir o operador caminhando para a esquerda a partir de left-1
            int opEnd = left - 1;
            int opStart = opEnd;
            while(opStart  >= 0) {
                char c = expression.charAt(opStart);
                if(c == '('  || c == ',') { // operador começa após '(' ou ','
                    opStart++;
                    break;
                }
                opStart--; //vai diminuindo na expressão enquanto nao acha
            }
            if (opStart < 0) opStart = 0; // caso o operador esteja no inmício

            String operator = expression.substring(opStart, left);

            // 5) Avaliar a operação e substituir "operador(...)" por '1' ou '0'
            boolean res = operation(operator, params);
            String before = expression.substring(0, opStart);
            String after  = expression.substring(right + 1);
            expression = before + (res ? '1' : '0') + after;
        }
        return expression; // será "0" ou "1"
    }


    //podemos ter as opções de: NOT, OR, AND
    //fazer um switch case para conferir as 3 opções
    static boolean operation(String operator, String params) {
        boolean result = false;

        switch (operator) {
            case "not":
                //o not apenas inverte o que está
                //se for zero é 1, se for 1 é 0
                result = !(params.charAt(0) == '1');
                break;
            case "or":
                //o or percorre tudo e caso tenha algum 1, retorna verdadeiro
                // else retorna falso (apenas zeros)
                for(int i = 0; i < params.length(); i++) {
                    if(params.charAt(i) == '1') {
                        result = true;
                        break;
                    }
                }
                break;
            case "and":
                //o AND começa em verdadeiro
                result = true;
                //percorre tudo e caso tenha zero, retorna falso
                for (int i = 0; i < params.length(); i++) {
                    if(params.charAt(i) == '0') {
                        result = false;
                        break;
                    }
                }
                break;
        }
        return result; //retorna os resultados
    }

    // Função que lê valores
     static boolean[] readValues(int n){
        boolean[] values = new boolean[n];
        for (int i = 0; i < n; i++) { //percorre todo o n. n = quantidade de entradas
            //vai ler qualquer digito entre 0 e 9
            //se o caractere lido for 0 = false
            //se for 1 = true
            //cada posição do vetor guarda false ou true
            values[i] = scanner.next("[0-9]").charAt(0) != '0'; //guarda o valor num vetor
        }
         return values;
    }


    
    //Função que lê a expressão como um todo
    static String readExpression(boolean[] values) {
        String expression = scanner.nextLine(); //pegar o scanner
        StringBuilder sb = new StringBuilder(); //construir uma nova string
        for (int i = 0; i < expression.length(); i++) { //percorrer a expressão toda
            char ch = expression.charAt(i);//pehar o caractere
            if (ch != ' ') { //se não for espaço
                if (ch >= 'A' && ch <= 'Z') { //se for uma letra
                    sb.append(values[ch - 'A'] ? '1' : '0'); //transformar em 0 ou 1
                } else {
                    sb.append(ch); //só deixar normal
                }
            }
        }
        return sb.toString();
    }
}
