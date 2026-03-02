import java.util.*;

public class Pratica1 {
    
    public static boolean confere(){
        Scanner sc = new Scanner(System.in);
        //pegar string
        String linha = sc.nextLine();
        int i=0;
        int tam = linha.length();
        
        //contar as quantidades de cada um
        //obs: nao pode vir um fechando antes de um abrindo
        int p1 = 0; //(
        int co1 = 0; //[
        int ch1 = 0; //{

        //conferir caractere por caractere da string
        while(i<tam){
            char c = linha.charAt(i);
            //se nao for nada disso
            if(!(c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}')) return false;
                
            //adicionar os abertos
            else if(c == '(') p1++;
            else if(c == '[') co1++;
            else if(c == '{') ch1++;
                
            //ver os fechados
            else if(c == ')'){
                //tem que ter antes um aberto
                if(p1 > 0) p1--;
                else return false;
            }
            else if(c == ']'){
                //tem que ter antes um aberto
                if(co1 > 0) co1--;
                else return false;
            }
            else if(c == '}'){
                //tem que ter antes um aberto
                if(ch1 > 0) ch1--;
                else return false;
            }
            i++;
        }
        if(p1 != 0 || co1 != 0 || ch1 != 0) return false;
        else return true;

        
    }
    
    public static void main (String []args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); //numero de instancias
        sc.nextLine(); // consome o \n que sobrou

        int vezes = t;
        while(vezes > 0){
            //chamar a funcao que vai imprimir sim ou nao
            boolean b = confere();
            if(b == true) System.out.println("S");
            else if(b == false) System.out.println("N");
            vezes--;
        }
    }
}