import java.util.Scanner;

public class Inverte{
    public static void main(String[] args){
        //receber uma string e devolver invertida
        Scanner scanner = new Scanner(System.in);
        String linha = scanner.nextLine(); //string 

        while (!(linha.length() == 3 && linha.charAt(0) == 'F' && linha.charAt(1) == 'I' && linha.charAt(2) == 'M')) {
             String nova = ""; //vazia 

            int tam = linha.length();

            for(int i=0; i<linha.length(); i++){
                char b = linha.charAt(tam-1-i); //percorrer o caractere do começo
                System.out.print(b);
            }

            System.out.println();
            //pegar a nova string
            linha = scanner.nextLine(); // ler a próxima linha
        }
    }
}