import java.util.*;

public class Anagrama {
    public static void main (String []args){
        Scanner sc = new Scanner(System.in);

        //ler até o primeiro espaço
        String string1 = sc.next();

        while(string1.charAt(0) != 'F' && string1.charAt(1) != 'I' && string1.charAt(2) != 'M' && string.length() != 3){

        //pegar uma string com o hifen
        String hifen = sc.next();
        //ler a proxima
        String string2 = sc.next();

        //ver se são anagramas
        int tam1 = string1.length();
        int tam2 = string2.length();

        int a = 0;
        int certo = 0;

        if(tam1 == tam2){
            //só começa se os tamanhos forem iguais
            for(int i=0; i<tam1; i++){
                a = 0; // precisa zerar a cada nova letra de string1
                //para cada item da string 1, percorrer todos da string 2
                for(int j=0; j<tam2; j++){
                    if(string1.charAt(i) == string2.charAt(j)){ // corrigido
                        a++;
                    }
                }
                //se ao fim do for, a for igual a zero, quebra
                if(a == 0){
                    System.out.println("NÃO");
                    i = tam1;
                    break;
                }
                else certo++;
            }
        }

        if(certo == tam1){
            System.out.println("SIM");
        }

        string1 = sc.next(); // ler a próxima linha
        }

    }
}
