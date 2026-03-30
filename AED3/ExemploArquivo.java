import java.io.*;

public class ExemploArquivo {

    public static void main(String[] args) {

        String caminho = "exemplo.db";

        // ESCREVER NO ARQUIVO
        try {
            FileOutputStream fos = new FileOutputStream(caminho);
            DataOutputStream dos = new DataOutputStream(fos);

            // escrevendo dados
            dos.writeInt(1);
            dos.writeUTF("Calipe");
            dos.writeFloat(1500.50F);

            dos.close();

            System.out.println("Dados escritos com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // LER DO ARQUIVO
        try {
            FileInputStream fis = new FileInputStream(caminho);
            DataInputStream dis = new DataInputStream(fis);

            int id = dis.readInt();
            String nome = dis.readUTF();
            float saldo = dis.readFloat();

            dis.close();

            System.out.println("\nDados lidos:");
            System.out.println("ID: " + id);
            System.out.println("Nome: " + nome);
            System.out.println("Saldo: " + saldo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
