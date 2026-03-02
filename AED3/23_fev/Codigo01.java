/*Esse código é um exemplo de uso de arquivo binário chamado pessoas.db.
Ele cria o registro de uma pessoa e simula um banco de dados simples. */
//!obs: Foi usada a extensão HEX EDITOR para leitura em hexadecimal

import java.io.*;
public class Codigo01 {
    
    public static void main(String[] args) {
        
        //registro de pessoa
        int idPessoa = 1;
        String nome = "Conceição Maria";
        String CPF = "12345678901";
        boolean casado = false;
        float renda = 4321.85F;

        int idPessoa2;
        String nome2;
        String CPF2;
        boolean casado2;
        float renda2;

        try {
            //acessa o arquivo de pessoas ou cria se não existente
            //!obs: é necessário ter a pasta dados já criada
            RandomAccessFile arqPessoas = new RandomAccessFile("./dados/pessoas.db", "rw");
                        
            //?ESCRITA =========================================
            //utiliza as escritas de cada tipo
            arqPessoas.writeInt(idPessoa);
            arqPessoas.writeUTF(nome);
            arqPessoas.write(CPF.getBytes());
            arqPessoas.writeBoolean(casado);
            arqPessoas.writeFloat(renda);
            arqPessoas.writeInt(0);

            //traz o ponteiro que lê e escreve até o começo
            arqPessoas.seek(0);

            //?LEITURA =========================================
            //usa a leitura de cada tipo para alocar na pessoa 2
            idPessoa2 = arqPessoas.readInt();
            nome2 = arqPessoas.readUTF();
            byte[] aux = new byte[11];
            arqPessoas.read(aux);
            CPF2 = new String(aux);
            casado2 = arqPessoas.readBoolean();
            renda2 = arqPessoas.readFloat();

            System.out.println("ID: "+idPessoa2);
            System.out.println("Nome: "+nome2);
            System.out.println("CPF: "+CPF2);
            System.out.println("Casado: "+casado2);
            System.out.println("Renda: "+renda2);

            arqPessoas.close(); //*importante fehcar o arquivo no final */

        } catch(IOException e) {
            System.err.println("Deu erro!");
        }

    }

}
