import java.util.*;

public class Exemploprova4 {

    // ---------------- CELULA ----------------
    static class Celula {
        Carro dado;
        Celula prox;

        Celula(Carro c) {
            dado = c;
            prox = null;
        }
    }

    // ---------------- LISTA ENCADEADA ----------------
    static class Lista {
        Celula primeiro = null;
        Celula ultimo = null;

        void inserir(Carro c) {
            Celula nova = new Celula(c);
            if (primeiro == null) {
                primeiro = ultimo = nova;
            } else {
                ultimo.prox = nova;
                ultimo = nova;
            }
        }

        Carro pesquisar(String placa) {
            Celula j = primeiro;
            while (j != null) {
                if (j.dado.placa.equals(placa)) return j.dado;
                j = j.prox;
            }
            return null;
        }
    }

    // ---------------- CARRO ----------------
    static class Carro {
        String placa, modelo, tipo, chassi;

        Carro(String linha) {
            String[] p = linha.split(",");
            placa = p[0];
            modelo = p[1];
            tipo = p[2];
            chassi = p[3];
        }

        void imprimir() {
            System.out.println(
                placa + " " + modelo + " " + tipo + " " + chassi
            );
        }
    }

    // ---------------- TABELA HASH ----------------
    static class Hash {
        Carro[] tabela;
        Lista lista = new Lista();
        int tam;

        Hash(int tam) {
            this.tam = tam;
            tabela = new Carro[tam];
        }

        int h(String placa) {
            int soma = 0;
            for (int i = 0; i < placa.length(); i++) {
                soma += placa.charAt(i);
            }
            return soma % tam;
        }

        void inserir(Carro c) {
            int pos = h(c.placa);
            if (tabela[pos] == null)
                tabela[pos] = c;
            else
                lista.inserir(c);
        }

        Carro pesquisar(String placa) {
            int pos = h(placa);

            if (tabela[pos] != null && tabela[pos].placa.equals(placa))
                return tabela[pos];

            return lista.pesquisar(placa);
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        Hash h = new Hash(n);

        while (sc.hasNext()) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) break;
            Carro c = new Carro(linha);
            h.inserir(c);
        }

        while (sc.hasNext()) {
            String linha = sc.nextLine();
            if (linha.equals("FIM_CONSULTA")) break;

            Carro c = h.pesquisar(linha);
            if (c == null) System.out.println("VEICULO NAO CADASTRADO");
            else c.imprimir();
        }
    }
}
