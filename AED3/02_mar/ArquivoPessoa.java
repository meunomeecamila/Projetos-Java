import java.io.*;

public class ArquivoPessoa {

    String nomeEntidade;
    RandomAccessFile arquivo;
    public final int TAM_CABECALHO = 4;

    public ArquivoPessoa(String nomeEntidade) throws Exception {
        this.nomeEntidade = nomeEntidade;
        arquivo = new RandomAccessFile("./dados/"+nomeEntidade+".db", "rw");

        //criação de um cabeçalho que lê o tamanho de cada instância e guarda antes
        if(arquivo.length() < TAM_CABECALHO)
            arquivo.writeInt(0);
    }

    //o início do arquivo guarda o último id utilizado
    public int create(Pessoa p) throws Exception {
        arquivo.seek(0); //vai para o início
        int ultimoID = arquivo.readInt(); //lê o último id utilizado
        int id = ultimoID + 1; //acrescenta porque vamos criar o próximo id

        //volta para o início e escreve o novo id
        arquivo.seek(0);
        arquivo.writeInt(id);

        p.setID(id); //define o id do objeto
        arquivo.seek(arquivo.length()); //vai para o final do arquivo
        byte[] aux = p.toByteArray(); //cria o vetor auxiliar
        arquivo.writeByte(' ');  // lápide -> define se o registro ainda está útil ou foi removido
        //*se tivesse sido removido, não estaria em branco. Talvez um *

        arquivo.writeShort(aux.length); //escreve o tamanho que o registro ocupa
        arquivo.write(aux); //escreve os dados
        return id; //devolve o id para o sistema
    }

    //Método que faz busca sequencial no arquivo
    public Pessoa read(int id) throws Exception {
        arquivo.seek(TAM_CABECALHO); //pula o tamanho do cabeçalho
        while(arquivo.getFilePointer() < arquivo.length()) {
            //enquanto o arquivo não acabar
            byte lapide = arquivo.readByte(); //pega a lápide
            short tam = arquivo.readShort(); //lê o tamanho do arquivo
            if(lapide==' ') { //verifica se está válido
                //lê as informações
                byte[] aux = new byte[tam];
                arquivo.read(aux);
                Pessoa p = new Pessoa();
                p.fromByteArray(aux);

                //se for esse o que você procura, retorna
                if(p.getID() == id)
                    return p;
            } else {
                //se a lápide for *, não está disponível e então pula
                arquivo.skipBytes(tam);
            }
        }
        return null;
    }

    public boolean delete(int id) throws Exception {
        arquivo.seek(TAM_CABECALHO); //pula o cabeçalho
        //enquanto o arquivo não acabar, faz a mesma procura que no read
        while(arquivo.getFilePointer() < arquivo.length()) {
            long endereco = arquivo.getFilePointer();
            byte lapide = arquivo.readByte();
            short tam = arquivo.readShort();
            if(lapide==' ') {
                byte[] aux = new byte[tam];
                arquivo.read(aux);
                Pessoa p = new Pessoa();
                p.fromByteArray(aux);

                //!a única coisa que muda é aqui!!
                //se o id for o procurado, retorna verdadeiro pra deletar e troca a lápide
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
        arquivo.seek(TAM_CABECALHO); //pula o cabeçalho
        //funciona da mesma forma que read e delete
        while(arquivo.getFilePointer() < arquivo.length()) {
            long endereco = arquivo.getFilePointer();
            byte lapide = arquivo.readByte();
            short tam = arquivo.readShort();
            if(lapide==' ') {
                byte[] aux = new byte[tam];
                arquivo.read(aux);
                Pessoa p = new Pessoa();
                p.fromByteArray(aux);

                //! única coisa que muda é aqui!!
                //se for o id que você quer, fazer a atualização
                if(p.getID() == novaPessoa.getID()) {

                    byte[] novoAux = novaPessoa.toByteArray();
                    short novoTamanho = (short)novoAux.length;

                    if(novoTamanho<=tam) { // registro manteve tamanho ou diminuiu
                        arquivo.seek(endereco+3);  // pula o lápide e o indicador de tamanho que dão 3 bytes
                        //se o registro for menor, os bytes serão inutilizados, mas continuarão lá
                        //eles só serão pulados pois o indicador de tamanho não indicará eles
                        arquivo.write(novoAux);
                    } else {   // registro aumentou de tamanho  
                        //se aumentou de tamanho, não cabe nesse lugar
                        arquivo.seek(endereco); //volta no endereço
                        arquivo.writeByte('*'); //troca a lápide pra indisponivel

                        //vai para o final do arquivo e começa a escrever de lá
                        arquivo.seek(arquivo.length());
                        arquivo.writeByte(' '); //lápide nova disponivel
                        arquivo.writeShort(novoTamanho); //tamanho novo no final
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
