import java.util.Scanner;

public class Inverte {

    // função recursiva que inverte a string
    public static String inverter(String s) {
        //caso base,string vazia ou de um caractere só
        if (s.length() <= 1) {
            return s;
        }
        // pega o último caractere + chama recursivamente para o restante
        return s.charAt(s.length() - 1) + inverter(s.substring(0, s.length() - 1));
    }

    //funcao principal
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String linha = scanner.nextLine();

        //mesma coisa mas chama a funcao
        while (!(linha.length() == 3 && linha.charAt(0) == 'F' && linha.charAt(1) == 'I' && linha.charAt(2) == 'M')) {
            
            String invertida = inverter(linha);
            System.out.println(invertida);

            linha = scanner.nextLine();
        }
    }
}
