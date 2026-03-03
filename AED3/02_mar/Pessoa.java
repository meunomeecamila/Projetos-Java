
/*O código a seguir possui os métodos comuns de pessoa, com seus contrutores, gets
e sets. Além disso, ele tem duas funções que manipualam objetos e vetores de bytes,
ao invés de gravar direto no arquivo. Isso é melhor porque separa responsabilidades
com POO e cada classe tem uma função delimitada. */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;



public class Pessoa  {
    
    //informações da pessoa
    private int idPessoa;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private float renda;

    //CONSTRUTORES 

    public Pessoa() {
        this(-1, "", "", LocalDate.now(), 0F);
    }

    public Pessoa(String n, String c, LocalDate d, float r) {
        this(-1, n, c, d, r);
    }

    public Pessoa(int i, String n, String c, LocalDate d, float r) {
        idPessoa = i;
        nome = n;
        cpf = c;
        dataNascimento = d;
        renda = r;
    }

    public int getID() {
        return idPessoa;
    }

    public void setID(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public float getRenda() {
        return renda;
    }

    public void setRenda(float renda) {
        this.renda = renda;
    }

    @Override
    public String toString() {
        return "ID......: " + idPessoa + 
               "\nNome....: " + nome +
               "\nCPF.....: " + cpf +
               "\nDt.Nasc.: " + dataNascimento +
               "\nRenda...: R$ " + renda + "\n";
    }
    
    //transforma o objeto atual em um vetor de bytes
    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //apenas cria o arquivo
        //o array output stream permite apenas alguns tipos em byte
        //então, transferimos para data output stream pois assim temos todos os tipos
        DataOutputStream dos = new DataOutputStream(baos);

        //escrever informações -> importante ler na mesma ordem
        dos.writeInt(idPessoa);
        dos.writeUTF(nome);
        dos.write(cpf.getBytes());
        dos.writeInt((int)dataNascimento.toEpochDay());
        dos.writeFloat(renda);
        return baos.toByteArray();
    }

    //reconstrói o objeto a partir de um vetor de bytes
    //Tem que estar exatamente na mesma ordem da escrita
    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
        idPessoa = dis.readInt();
        nome = dis.readUTF();
        byte[] aux = new byte[11];
        dis.read(aux);
        cpf = new String(aux);

        //reconstrução da data de nascimento a partir do dia
        dataNascimento = LocalDate.ofEpochDay(dis.readInt());
        renda = dis.readFloat();
    }

}
