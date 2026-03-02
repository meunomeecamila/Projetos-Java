import java.util.*;
public class Pratica7{
    public static void main(String []args){
        Scanner sc = new Scanner(System.in);
        int v = 0;

        while(sc.hasNextLine()){
            String c = sc.nextLine();
            if(c.charAt(0) == 'V') v++;
        }

        if(v == 5 || v == 6) System.out.println("1");
        else if(v == 3 || v == 4) System.out.println("2");
        else if(v == 1 || v == 2) System.out.println("3");
        else System.out.println("-1");
    }
}