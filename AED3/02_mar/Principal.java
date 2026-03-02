import java.io.File;
import java.time.LocalDate;

public class Principal {
    
    public static void main(String[] args) {

        File f = new File("./dados/pessoa.db");
        f.delete();
            

        //criação de novas pessoas para teste
        Pessoa p1 = new Pessoa("Conceição Maria", "12345678901", LocalDate.of(2000, 3, 20), 3500F);
        Pessoa p2 = new Pessoa( "João Carlos", "12312312312", LocalDate.of(2001, 11, 7), 2500F);
        Pessoa p3 = new Pessoa( "Juliana Alves", "00100200345", LocalDate.of(1999, 1, 3), 3875.50F);
        Pessoa p = new Pessoa();


        //operações que podemos fazer
        try {
            ArquivoPessoa arqPessoas = new ArquivoPessoa("pessoa");
            
            
            int id1 = arqPessoas.create(p1);
            int id2 = arqPessoas.create(p2);
            int id3 = arqPessoas.create(p3);

            p2.setNome("João");
            arqPessoas.update(p2);

            p = arqPessoas.read(2);
            if(p!=null)
                System.out.println(p);
            else
                System.out.println("Pessoa não encontrada!");

            arqPessoas.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
