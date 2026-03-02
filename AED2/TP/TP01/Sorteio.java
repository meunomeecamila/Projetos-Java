import java.util.Scanner;
import java.util.Random;

public class Sorteio {
    public static void main(String Args[]){
        Scanner scanner = new Scanner(System.in);
        String linha = scanner.nextLine(); //string 

        //por linha, sortear duas letras aleatórias
        Random rand = new Random();
        rand.setSeed(4); //para correção

        while (!(linha.length() == 3 && linha.charAt(0) == 'F' && linha.charAt(1) == 'I' && linha.charAt(2) == 'M')) { // linha n é fim
            
            char letra1 = (char) ('a' + (Math.abs(rand.nextInt()) % 26));
            char letra2 = (char) ('a' + (Math.abs(rand.nextInt()) % 26));
            
            //System.out.println(letra1);
            //System.out.println(letra2);
            
            for(int i=0; i<linha.length(); i++){
                //acessa caractere
                char a = linha.charAt(i); //percorrer o caractere do começo

                if(a == letra1){
                    a = letra2;
                }

                System.out.print(a);
            }

            System.out.println();
            //pegar a nova string
            linha = scanner.nextLine(); // ler a próxima linha
        }
    }
}