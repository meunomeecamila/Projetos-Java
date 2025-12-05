import java.util.*;

public class Exemploprova3 {

    public static class Crianca {
        String nome;
        int valor;
        int prev, next;
        boolean removido;

        public Crianca(String nome, int valor) {
            this.nome = nome;
            this.valor = valor;
            this.removido = false;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Crianca[] kids = new Crianca[n];

        for (int i = 0; i < n; i++) {
            String nome = sc.next();
            int val = sc.nextInt();
            kids[i] = new Crianca(nome, val);
        }

        // montar lista duplamente ligada circular
        for (int i = 0; i < n; i++) {
            kids[i].prev = (i - 1 + n) % n;
            kids[i].next = (i + 1) % n;
        }

        int vivos = n;
        int i = 0; // começa pela primeira criança

        while (vivos > 1) {
            int value = kids[i].valor;

            // ímpar → next ; par → prev  (EXATAMENTE igual ao C)
            if (value % 2 == 1) {
                for (int j = 0; j < value; j++) {
                    i = kids[i].next;
                }
            } else {
                for (int j = 0; j < value; j++) {
                    i = kids[i].prev;
                }
            }

            // eliminar kids[i]
            int prev = kids[i].prev;
            int next = kids[i].next;

            kids[prev].next = next;
            kids[next].prev = prev;

            vivos--;

            // mover para o próximo "valido" (igual ao C)
            i = next;
        }

        System.out.println("Vencedor(a): " + kids[i].nome);
    }
}
