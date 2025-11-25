import java.util.Scanner;

public class Cesar {
    public static void main(String Args[]) {
        Scanner scanner = new Scanner(System.In);
        String linha = scanner.nextLine(); //string 

        //loop
        while (!(linha.length() == 3 && linha.charAt(0) == 'F' && linha.charAt(1) == 'I' && linha.charAt(2) == 'M')) { // linha n é fim
            for(int i=0; i<linha.length(); i++){
                //percorre toda a string

                //acessa caractere
                char a = linha.charAt(i); //percorrer o caractere do começo
                
                //se for maiuscula
                if(a>= 'A' && a<= 'Z'){
                    a = (char) (a + 3);
                    //se passar de Z tem que voltar
                    /*if(a > 'Z'){
                        a = (char) (a - 26);
                    }*/
                }
                //se for minúscula
                else if(a>= 'a' && a<= 'z'){
                    a = (char) (a + 3);
                    //se passar de z tem que voltar
                    /*if(a > 'z'){
                        a = (char) (a - 26);
                    }*/
                }

                //só troca os que estiverem dentro da ASCII
                else if(a >= 0 && a<= 127){
                    a = (char) (a+3);
                }
                
                System.out.print(a);
            }
            System.out.println();
            //pegar a nova string
            linha = scanner.nextLine(); // ler a próxima linha
        }
    }
}
