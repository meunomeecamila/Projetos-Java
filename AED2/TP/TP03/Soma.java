import java.util.Scanner;

public class Soma {
    public static int SomaDig(int n){
        if(n<10) return n;
    	else return n%10 + SomaDig(n/10);
    }

    public static void main(String []args){
        Scanner sc = new Scanner(System.in);
        
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int soma = SomaDig(n);
            System.out.println(soma);
        }
    }
}