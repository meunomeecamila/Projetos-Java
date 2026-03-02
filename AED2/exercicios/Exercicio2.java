// Classe que representa um contato individual
public class Contato {
    String nome;
    String telefone;
    String email;
    int cpf;

    // Construtor para criar novos contatos
    public Contato(String nome, String telefone, String email, int cpf) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
    }

    // Mostrar informações do contato (para testes)
    public void mostrar() {
        System.out.println("Nome: " + nome);
        System.out.println("Telefone: " + telefone);
        System.out.println("E-mail: " + email);
        System.out.println("CPF: " + cpf);
        System.out.println("----------------------");
    }
}

// Classe que representa uma célula da lista de contatos
public class Celula {
    Contato contato; // dado armazenado
    Celula prox;     // ponteiro para o próximo da lista

    public Celula(Contato contato) {
        this.contato = contato;
        this.prox = null;
    }
}

// Cada nó da árvore representa uma letra do alfabeto
public class No {
    char letra;        // ex: 'A', 'B', 'C'
    No esq, dir;       // filhos esquerdo e direito
    Celula primeiro;   // início da lista de contatos dessa letra
    Celula ultimo;     // fim da lista de contatos dessa letra

    public No(char letra) {
        this.letra = letra;
        this.esq = null;
        this.dir = null;
        this.primeiro = null;
        this.ultimo = null;
    }
}

// Importar biblioteca padrão para testes
import java.util.*;

public class Agenda {
    No raiz;

    // ==========================================
    // Construtor: cria uma árvore balanceada com letras de A a Z
    // ==========================================
    public Agenda() {
        // cria vetor com todas as letras maiúsculas
        char[] letras = new char[26];
        for (int i = 0; i < 26; i++) {
            letras[i] = (char) ('A' + i);
        }

        // constrói árvore balanceada a partir do vetor
        raiz = construirBalanceado(letras, 0, 25);
    }

    // ==========================================
    // Constrói árvore balanceada (recursivamente)
    // ==========================================
    private No construirBalanceado(char[] letras, int inicio, int fim) {
        if (inicio > fim) return null;

        int meio = (inicio + fim) / 2;
        No novo = new No(letras[meio]);

        // construir recursivamente esquerda e direita
        novo.esq = construirBalanceado(letras, inicio, meio - 1);
        novo.dir = construirBalanceado(letras, meio + 1, fim);

        return novo;
    }

    // ==========================================
    // Inserir contato
    // ==========================================
    public void inserir(Contato contato) {
        char letra = Character.toUpperCase(contato.nome.charAt(0));
        No noLetra = pesquisarLetra(raiz, letra);

        if (noLetra != null) {
            Celula nova = new Celula(contato);

            // inserir no final da lista da letra
            if (noLetra.primeiro == null) {
                noLetra.primeiro = nova;
                noLetra.ultimo = nova;
            } else {
                noLetra.ultimo.prox = nova;
                noLetra.ultimo = nova;
            }
        } else {
            System.out.println("Letra " + letra + " não encontrada!");
        }
    }

    // ==========================================
    // Pesquisar uma letra na árvore
    // ==========================================
    private No pesquisarLetra(No i, char letra) {
        if (i == null) return null;
        else if (letra == i.letra) return i;
        else if (letra < i.letra) return pesquisarLetra(i.esq, letra);
        else return pesquisarLetra(i.dir, letra);
    }

    // ==========================================
    // Pesquisar contato pelo nome
    // ==========================================
    public Contato pesquisar(String nome) {
        char letra = Character.toUpperCase(nome.charAt(0));
        No noLetra = pesquisarLetra(raiz, letra);

        if (noLetra != null) {
            for (Celula i = noLetra.primeiro; i != null; i = i.prox) {
                if (i.contato.nome.equalsIgnoreCase(nome)) {
                    return i.contato;
                }
            }
        }
        return null;
    }

    // ==========================================
    // Pesquisar contato pelo CPF
    // ==========================================
    public Contato pesquisar(int cpf) {
        return pesquisarCPF(raiz, cpf);
    }

    private Contato pesquisarCPF(No i, int cpf) {
        if (i == null) return null;

        // verifica contatos da lista
        for (Celula c = i.primeiro; c != null; c = c.prox) {
            if (c.contato.cpf == cpf) return c.contato;
        }

        // pesquisa nas subárvores
        Contato achou = pesquisarCPF(i.esq, cpf);
        if (achou == null) achou = pesquisarCPF(i.dir, cpf);
        return achou;
    }

    // ==========================================
    // Remover contato pelo nome
    // ==========================================
    public void remover(String nome) {
        char letra = Character.toUpperCase(nome.charAt(0));
        No noLetra = pesquisarLetra(raiz, letra);

        if (noLetra == null || noLetra.primeiro == null) {
            System.out.println("Contato não encontrado.");
            return;
        }

        Celula anterior = null;
        Celula atual = noLetra.primeiro;

        while (atual != null && !atual.contato.nome.equalsIgnoreCase(nome)) {
            anterior = atual;
            atual = atual.prox;
        }

        if (atual == null) {
            System.out.println("Contato não encontrado.");
        } else {
            if (anterior == null) {
                noLetra.primeiro = atual.prox;
                if (noLetra.primeiro == null) noLetra.ultimo = null;
            } else {
                anterior.prox = atual.prox;
                if (atual == noLetra.ultimo) noLetra.ultimo = anterior;
            }
            System.out.println("Contato removido com sucesso!");
        }
    }

    // ==========================================
    // Mostrar todos os contatos
    // ==========================================
    public void mostrar() {
        mostrar(raiz);
    }

    private void mostrar(No i) {
        if (i != null) {
            mostrar(i.esq);
            if (i.primeiro != null) {
                System.out.println("Letra " + i.letra + ":");
                for (Celula c = i.primeiro; c != null; c = c.prox) {
                    c.contato.mostrar();
                }
            }
            mostrar(i.dir);
        }
    }

    // ==========================================
    // Mostrar estrutura da árvore (letras e profundidade)
    // ==========================================
    public void mostrarEstrutura() {
        mostrarEstrutura(raiz, 0);
    }

    private void mostrarEstrutura(No i, int nivel) {
        if (i != null) {
            mostrarEstrutura(i.dir, nivel + 1);
            for (int j = 0; j < nivel; j++) System.out.print("   ");
            System.out.println(i.letra);
            mostrarEstrutura(i.esq, nivel + 1);
        }
    }
}

public class Aplicacao {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();

        // Mostra a estrutura balanceada da árvore
        System.out.println("Árvore balanceada de letras:");
        agenda.mostrarEstrutura();
        System.out.println("-----------------------------");

        // Inserindo alguns contatos
        agenda.inserir(new Contato("Ana", "9999-1111", "ana@email.com", 123));
        agenda.inserir(new Contato("Bruno", "9999-2222", "bruno@email.com", 456));
        agenda.inserir(new Contato("Beatriz", "9999-3333", "bia@email.com", 789));
        agenda.inserir(new Contato("Carlos", "9999-4444", "carlos@email.com", 101));
        agenda.inserir(new Contato("Zuleica", "9999-5555", "zuzu@email.com", 202));

        // Mostrar todos os contatos
        System.out.println("\n--- Contatos na Agenda ---");
        agenda.mostrar();

        // Pesquisar por nome
        System.out.println("\n--- Pesquisando por nome: Bruno ---");
        Contato c = agenda.pesquisar("Bruno");
        if (c != null) c.mostrar();
        else System.out.println("Não encontrado.");

        // Remover um contato
        System.out.println("\n--- Removendo Bruno ---");
        agenda.remover("Bruno");
        agenda.mostrar();

        // Pesquisar por CPF
        System.out.println("\n--- Pesquisando por CPF: 789 ---");
        Contato x = agenda.pesquisar(789);
        if (x != null) x.mostrar();
        else System.out.println("CPF não encontrado.");
    }
}

/*
===========================================================
ANÁLISE DE COMPLEXIDADE - CLASSE AGENDA
===========================================================

1) Método inserir(Contato)
   - Melhor caso: O(log 26) + O(1) ≈ O(1)
       -> A letra é encontrada rapidamente (árvore balanceada)
          e a lista da letra está vazia.
   - Pior caso: O(n)
       -> A letra é encontrada, mas percorremos toda a lista 
          dessa letra até o final para inserir.

2) Método pesquisar(String nome)
   - Melhor caso: O(1)
       -> O contato é o primeiro da lista.
   - Pior caso: O(n)
       -> O contato está no final da lista ou não existe.

3) Método pesquisar(int cpf)
   - Melhor caso: O(1)
       -> Encontramos o CPF logo nos primeiros nós.
   - Pior caso: O(26 × n)
       -> Percorremos todos os 26 nós (letras)
          e todas as listas de contatos.

4) Método remover(String nome)
   - Melhor caso: O(1)
       -> O contato é o primeiro da lista.
   - Pior caso: O(n)
       -> O contato está no final da lista ou não existe.

-----------------------------------------------------------
Observações:
- A árvore de letras (A-Z) é balanceada, então a busca pela 
  letra é sempre O(log 26) ≈ constante.
- O custo principal está na lista encadeada de contatos de 
  cada letra, que pode crescer linearmente.
===========================================================
*/
