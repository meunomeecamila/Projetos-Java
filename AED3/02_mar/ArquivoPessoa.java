import java.io.*;

public class ArquivoPessoa {

    String nomeEntidade;
    RandomAccessFile arquivo;
    public final int TAM_CABECALHO = 4;

    public ArquivoPessoa(String nomeEntidade) throws Exception {
        this.nomeEntidade = nomeEntidade;
        arquivo = new RandomAccessFile("./dados/"+nomeEntidade+".db", "rw");
        if(arquivo.length() < TAM_CABECALHO)
            arquivo.writeInt(0);
    }

    public int create(Pessoa p) throws Exception {
        arquivo.seek(0);
        int ultimoID = arquivo.readInt();
        int id = ultimoID + 1;
        arquivo.seek(0);
        arquivo.writeInt(id);

        p.setID(id);
        arquivo.seek(arquivo.length());
        byte[] aux = p.toByteArray();
        arquivo.writeByte(' ');  // lápide
        arquivo.writeShort(aux.length);
        arquivo.write(aux);
        return id;
    }

    public Pessoa read(int id) throws Exception {
        arquivo.seek(TAM_CABECALHO);
        while(arquivo.getFilePointer() < arquivo.length()) {
            byte lapide = arquivo.readByte();
            short tam = arquivo.readShort();
            if(lapide==' ') {
                byte[] aux = new byte[tam];
                arquivo.read(aux);
                Pessoa p = new Pessoa();
                p.fromByteArray(aux);
                if(p.getID() == id)
                    return p;
            } else {
                arquivo.skipBytes(tam);
            }
        }
        return null;
    }

    public boolean delete(int id) throws Exception {
        arquivo.seek(TAM_CABECALHO);
        while(arquivo.getFilePointer() < arquivo.length()) {
            long endereco = arquivo.getFilePointer();
            byte lapide = arquivo.readByte();
            short tam = arquivo.readShort();
            if(lapide==' ') {
                byte[] aux = new byte[tam];
                arquivo.read(aux);
                Pessoa p = new Pessoa();
                p.fromByteArray(aux);
                if(p.getID() == id) {
                    arquivo.seek(endereco);
                    arquivo.writeByte('*');
                    return true;
                }
            } else {
                arquivo.skipBytes(tam);
            }
        }
        return false;
    }

    public boolean update(Pessoa novaPessoa) throws Exception {
        arquivo.seek(TAM_CABECALHO);
        while(arquivo.getFilePointer() < arquivo.length()) {
            long endereco = arquivo.getFilePointer();
            byte lapide = arquivo.readByte();
            short tam = arquivo.readShort();
            if(lapide==' ') {
                byte[] aux = new byte[tam];
                arquivo.read(aux);
                Pessoa p = new Pessoa();
                p.fromByteArray(aux);
                if(p.getID() == novaPessoa.getID()) {

                    byte[] novoAux = novaPessoa.toByteArray();
                    short novoTamanho = (short)novoAux.length;

                    if(novoTamanho<=tam) { // registro manteve tamanho ou diminuiu
                        arquivo.seek(endereco+3);  // pula o lápide e o indicador de tamanho
                        arquivo.write(novoAux);
                    } else {   // registro aumentou de tamanho  
                        arquivo.seek(endereco);
                        arquivo.writeByte('*');
                        arquivo.seek(arquivo.length());
                        arquivo.writeByte(' ');
                        arquivo.writeShort(novoTamanho);
                        arquivo.write(novoAux);
                    }
                    return true;
                }
            } else {
                arquivo.skipBytes(tam);
            }
        }
        return false;
    }


    public void close() throws Exception {
        arquivo.close();
    }
    
}
