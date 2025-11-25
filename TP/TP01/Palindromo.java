import java.util.Scanner;

public class Palindromo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String linha = scanner.nextLine(); //string 

        //loop
        while (!linha.equals("FIM")) { //enquanto linha não for fim
            int palin=1;

            for(int i=0; i < (linha.length()/2); i++){
                //acessa caractere
                int lugar = linha.length() - i - 1;
                char a = linha.charAt(i); //percorrer o caractere do começo
                char b = linha.charAt(lugar); //percorrer o caractere do final

                if(a != b){
                    //se não forem iguais
                    palin = 0;
                    break;
                }
            } //fim do for

            if(palin == 1){
                System.out.println("SIM");
            }
            else {
                System.out.println("NAO");
            }

            //pegar a nova string
            linha = scanner.nextLine(); // ler a próxima linha
        }
    }
}