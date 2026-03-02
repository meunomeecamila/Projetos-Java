
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;



public class Pessoa  {
    
    private int idPessoa;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private float renda;

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
    
    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(idPessoa);
        dos.writeUTF(nome);
        dos.write(cpf.getBytes());
        dos.writeInt((int)dataNascimento.toEpochDay());
        dos.writeFloat(renda);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
        idPessoa = dis.readInt();
        nome = dis.readUTF();
        byte[] aux = new byte[11];
        dis.read(aux);
        cpf = new String(aux);
        dataNascimento = LocalDate.ofEpochDay(dis.readInt());
        renda = dis.readFloat();
    }

}
