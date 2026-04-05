import java.io.*;
import java.util.ArrayList;

class Pessoa {
    int id;
    String nome;

    public Pessoa() {}

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(id);
        dos.writeUTF(nome);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        id = dis.readInt();
        nome = dis.readUTF();
    }

    public String toString() {
        return id + " - " + nome;
    }
}

class ArquivoPessoa {
    RandomAccessFile arq;
    final int TAM_CABECALHO = 4;

    public ArquivoPessoa(String nome) throws Exception {
        arq = new RandomAccessFile(nome + ".db", "rw");

        if (arq.length() == 0) {
            arq.writeInt(0); // último ID
        }
    }

    // CREATE
    public int create(Pessoa p) throws Exception {
        arq.seek(0);
        int ultimoID = arq.readInt();
        int id = ultimoID + 1;

        arq.seek(0);
        arq.writeInt(id);

        p.id = id;

        arq.seek(arq.length());
        byte[] ba = p.toByteArray();

        arq.writeByte(' ');
        arq.writeShort(ba.length);
        arq.write(ba);

        return id;
    }

    // READ (1)
    public Pessoa read(int id) throws Exception {
        arq.seek(TAM_CABECALHO);

        while (arq.getFilePointer() < arq.length()) {
            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide != '*') {
                byte[] ba = new byte[tam];
                arq.readFully(ba);

                Pessoa p = new Pessoa();
                p.fromByteArray(ba);

                if (p.id == id)
                    return p;
            } else {
                arq.skipBytes(tam);
            }
        }
        return null;
    }

    // READ (todos)
    public ArrayList<Pessoa> readAll() throws Exception {
        ArrayList<Pessoa> lista = new ArrayList<>();

        arq.seek(TAM_CABECALHO);

        while (arq.getFilePointer() < arq.length()) {
            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide != '*') {
                byte[] ba = new byte[tam];
                arq.readFully(ba);

                Pessoa p = new Pessoa();
                p.fromByteArray(ba);

                lista.add(p);
            } else {
                arq.skipBytes(tam);
            }
        }
        return lista;
    }

    // UPDATE
    public boolean update(Pessoa nova) throws Exception {
        arq.seek(TAM_CABECALHO);

        while (arq.getFilePointer() < arq.length()) {
            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide != '*') {
                byte[] ba = new byte[tam];
                arq.readFully(ba);

                Pessoa p = new Pessoa();
                p.fromByteArray(ba);

                if (p.id == nova.id) {
                    byte[] novoBa = nova.toByteArray();

                    if (novoBa.length <= tam) {
                        arq.seek(pos + 3);
                        arq.write(novoBa);
                    } else {
                        arq.seek(pos);
                        arq.writeByte('*');

                        arq.seek(arq.length());
                        arq.writeByte(' ');
                        arq.writeShort(novoBa.length);
                        arq.write(novoBa);
                    }
                    return true;
                }
            } else {
                arq.skipBytes(tam);
            }
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) throws Exception {
        arq.seek(TAM_CABECALHO);

        while (arq.getFilePointer() < arq.length()) {
            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide != '*') {
                byte[] ba = new byte[tam];
                arq.readFully(ba);

                Pessoa p = new Pessoa();
                p.fromByteArray(ba);

                if (p.id == id) {
                    arq.seek(pos);
                    arq.writeByte('*');
                    return true;
                }
            } else {
                arq.skipBytes(tam);
            }
        }
        return false;
    }

    public void close() throws Exception {
        arq.close();
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        ArquivoPessoa arq = new ArquivoPessoa("pessoas");

        int id1 = arq.create(new Pessoa("Ana"));
        int id2 = arq.create(new Pessoa("Bruno"));

        System.out.println("Read ID 1: " + arq.read(id1));

        System.out.println("\nTodos:");
        for (Pessoa p : arq.readAll())
            System.out.println(p);

        Pessoa p = new Pessoa("Carlos");
        p.id = id2;
        arq.update(p);

        System.out.println("\nApós update:");
        System.out.println(arq.read(id2));

        arq.delete(id1);

        System.out.println("\nApós delete:");
        for (Pessoa px : arq.readAll())
            System.out.println(px);

        arq.close();
    }
}
