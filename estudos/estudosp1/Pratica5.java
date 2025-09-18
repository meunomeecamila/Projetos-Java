import java.io.IOException;
import java.util.*;

public class Pratica5 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int h1 = sc.nextInt();
        int m1 = sc.nextInt();
        int h2 = sc.nextInt();
        int m2 = sc.nextInt();

        while(!(h1 == 0 && h2 == 0 && m1 == 0 && m2 == 0)){
            int minutos = 0; //quantidade de minutos q ela pode dormir

            if(h1 < h2){
                if(m1 <= m2) minutos = ((h2 - h1) * 60) + (m2 - m1);
                else minutos = ((h2 - h1) * 60) - (m1 - m2);
            }

            else if(h2 < h1){
                minutos = ((60-m1) + m2) + (h2*60) + ((23-h1)*60);
            }

            System.out.println(minutos);

            h1 = sc.nextInt();
            m1 = sc.nextInt();
            h2 = sc.nextInt();
            m2 = sc.nextInt();
        }
 
    }
}