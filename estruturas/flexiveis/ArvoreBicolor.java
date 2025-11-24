import java.io.*;
import java.util.*;

/**
 * Classe principal que implementa uma Árvore Rubro-Negra (Red-Black Tree),
 * adaptando as cores para Amarelo (Black) e Rosa (Red) conforme solicitado.
 * O código foi extensivamente comentado para fins de estudo, explicando
 * a lógica por trás das operações de balanceamento e inserção.
 */
public class ArvoreBicolor {

    // Constantes para as cores (Rosa = Vermelho, Amarelo = Preto)
    // Usamos booleanos para eficiência: true é a cor 'mais ativa' (Rosa/Red),
    // false é a cor 'de base' (Amarelo/Black).
    private static final boolean PINK = true;   // Rosa (Cor Vermelha na RB-Tree padrão)
    private static final boolean YELLOW = false; // Amarelo (Cor Preta na RB-Tree padrão)

    private Node raiz;

    /**
     * Classe interna para representar um Nó da Árvore Bicolor.
     * O campo 'cor' armazena se o link que chega a este nó é 'Rosa' (true) ou 'Amarelo' (false).
     */
    private class Node {
        int chave;
        boolean cor; // PINK (true) ou YELLOW (false)
        Node esq, dir;

        public Node(int chave) {
            this.chave = chave;
            this.cor = PINK; // Novos nós são sempre inseridos como Rosa (Red), violando a regra #5
            this.esq = this.dir = null;
        }

        // Método auxiliar para obter a cor de um nó (útil para nós nulos, que são Amarelos)
        public boolean getColor() {
            return this.cor;
        }
    }

    public ArvoreAmarelaRosa() {
        this.raiz = null;
    }

    /**
     * Verifica se o link (que aponta para o nó) é Rosa (Red).
     * Nós nulos (folhas externas) são sempre considerados Amarelos (Black).
     * @param no O nó a ser verificado.
     * @return true se o nó não for nulo e sua cor for PINK.
     */
    private boolean isPink(Node no) {
        if (no == null) return YELLOW; // Null links (folhas) são Amarelos/Pretos
        return no.cor == PINK;
    }

    /**
     * Aplica a Rotação à Esquerda.
     * Usada para corrigir um link Rosa inclinado à direita (propriedade violada #1 do 2-3-4 tree).
     *
     * |               |
     * h (Amarelo) -> x (Rosa)
     * \             / \
     * x (Rosa) -> h
     * / \         / \
     * TL  TR      TL  TR
     *
     * O nó 'h' (pai) se move para a esquerda e 'x' (filho direito Rosa) se torna o novo pai.
     * @param h O nó desbalanceado.
     * @return O novo nó pai (x).
     */
    private Node rotacionarEsquerda(Node h) {
        // 1. O filho direito (x) sobe.
        Node x = h.dir;
        
        // 2. A sub-árvore esquerda de x se torna a sub-árvore direita de h.
        h.dir = x.esq;
        
        // 3. h se torna o filho esquerdo de x.
        x.esq = h;
        
        // 4. Ajuste de cores (o link que desceu para h deve manter a cor original de h, e h fica Rosa).
        x.cor = h.cor;       // x herda a cor de h (se h era Amarelo, x também se torna Amarelo)
        h.cor = PINK;        // h se torna Rosa (Red)
        
        return x;
    }

    /**
     * Aplica a Rotação à Direita.
     * Usada para corrigir dois links Rosa consecutivos à esquerda (violação da regra #5: dois Reds juntos).
     *
     * |                   |
     * h (Amarelo) -> x (Rosa)
     * /                   / \
     * x (Rosa) ->         y (Rosa)
     * / \                 / \
     * y (Rosa)           TL  TR
     *
     * O nó 'x' (filho esquerdo Rosa) sobe, se tornando o novo pai, e 'h' desce.
     * @param h O nó desbalanceado.
     * @return O novo nó pai (x).
     */
    private Node rotacionarDireita(Node h) {
        // 1. O filho esquerdo (x) sobe.
        Node x = h.esq;
        
        // 2. A sub-árvore direita de x se torna a sub-árvore esquerda de h.
        h.esq = x.dir;
        
        // 3. h se torna o filho direito de x.
        x.dir = h;
        
        // 4. Ajuste de cores.
        x.cor = h.cor;       // x herda a cor de h
        h.cor = PINK;        // h se torna Rosa (Red)
        
        return x;
    }

    /**
     * Inverte as cores (Color Flip / Split 4-node).
     * Usada quando um nó e seus dois filhos são Rosa (Red), formando um nó 4-way na árvore 2-3-4.
     *
     * h (Amarelo)          h (Rosa)
     * / \        ==>       / \
     * L (Rosa) R (Rosa)    L (Amarelo) R (Amarelo)
     *
     * O pai 'h' se torna Rosa, e os filhos 'L' e 'R' se tornam Amarelos.
     * @param h O nó cujos filhos são Rosa.
     */
    private void flipCores(Node h) {
        // Assume-se que 'h' é Amarelo e seus filhos são Rosa (para funcionar na inserção)
        h.cor = PINK;         // Pai vira Rosa
        h.esq.cor = YELLOW;   // Filho esquerdo vira Amarelo
        h.dir.cor = YELLOW;   // Filho direito vira Amarelo
    }

    /**
     * Inicia a inserção na raiz e garante que a raiz seja Amarela no final.
     * @param chave A chave a ser inserida.
     */
    public void inserir(int chave) {
        raiz = inserirRec(raiz, chave);
        raiz.cor = YELLOW; // A raiz deve ser sempre Amarela (Propriedade #2)
    }

    /**
     * Função recursiva de inserção e balanceamento.
     * @param h O nó atual (raiz da sub-árvore).
     * @param chave A chave a ser inserida.
     * @return O nó atual após todas as rotações e flips de cor.
     */
    private Node inserirRec(Node h, int chave) {
        if (h == null) {
            return new Node(chave); // Cria um novo nó Rosa (Propriedade #3)
        }

        // 1. Descida (Inserção padrão da BST)
        if (chave < h.chave) {
            h.esq = inserirRec(h.esq, chave);
        } else if (chave > h.chave) {
            h.dir = inserirRec(h.dir, chave);
        } else {
            h.chave = chave; // Se chave igual, apenas atualiza (ou ignora, dependendo da necessidade)
            return h;
        }

        // 2. Correções de Balanceamento (Ascensão)

        // CASO 1: Rotação à Esquerda (Corrige link Rosa na direita: inclinação para a direita)
        // Se o link direito é Rosa E o link esquerdo é Amarelo.
        if (isPink(h.dir) && !isPink(h.esq)) {
            System.out.println("  -> Rotação Esquerda em Nó com chave: " + h.chave);
            h = rotacionarEsquerda(h);
        }

        // CASO 2: Rotação à Direita (Corrige dois links Rosa consecutivos à esquerda)
        // Se o link esquerdo é Rosa E o neto esquerdo também é Rosa.
        if (isPink(h.esq) && isPink(h.esq.esq)) {
            System.out.println("  -> Rotação Direita em Nó com chave: " + h.chave);
            h = rotacionarDireita(h);
        }

        // CASO 3: Inversão de Cores (Corrige 4-node - ambos os filhos são Rosa)
        // Se ambos os links filhos são Rosa.
        if (isPink(h.esq) && isPink(h.dir)) {
            System.out.println("  -> Flip de Cores em Nó com chave: " + h.chave);
            flipCores(h);
        }

        return h;
    }

    /**
     * Pesquisa uma chave na árvore e mostra o caminho percorrido.
     * @param chave A chave a ser buscada.
     * @return Uma string formatada com o resultado e o caminho.
     */
    public String pesquisarComCaminho(int chave) {
        StringBuilder caminho = new StringBuilder();
        Node atual = raiz;
        boolean encontrado = false;
        
        // Verifica se a raiz existe para começar a pesquisa
        if (atual != null) {
            caminho.append("raiz");
            
            while (atual != null) {
                if (chave == atual.chave) {
                    encontrado = true;
                    break;
                } else if (chave < atual.chave) {
                    caminho.append(" esq");
                    atual = atual.esq;
                } else {
                    caminho.append(" dir");
                    atual = atual.dir;
                }
            }
        } else {
            caminho.append("(Árvore vazia)");
        }

        // Monta o resultado final
        String resultado = encontrado ? " SIM" : " NAO";
        return "Chave " + chave + ": => " + caminho.toString() + resultado;
    }
    
    /**
     * Imprime a árvore usando a travessia In-Order para fins de verificação e estudo.
     * @param no Nó inicial da travessia.
     * @param nivel Nível atual do nó na árvore (para indentação).
     */
    private void imprimirInOrder(Node no, int nivel) {
        if (no != null) {
            imprimirInOrder(no.dir, nivel + 1);
            
            // Imprime o nó atual, indentado por nível
            for (int i = 0; i < nivel; i++) {
                System.out.print("   ");
            }
            
            String corStr = no.cor == PINK ? "ROSA" : "AMARELO";
            System.out.println(no.chave + " (" + corStr + ")");
            
            imprimirInOrder(no.esq, nivel + 1);
        }
    }

    /**
     * Método público para iniciar a impressão da árvore.
     */
    public void imprimir() {
        System.out.println("\n--- Estrutura da Árvore Amarela/Rosa (In-Order) ---");
        imprimirInOrder(raiz, 0);
        System.out.println("---------------------------------------------------\n");
    }

    /**
     * Método principal para demonstrar a funcionalidade da Árvore Bicolor.
     */
    public static void main(String[] args) {
        ArvoreAmarelaRosa arvore = new ArvoreAmarelaRosa();
        
        System.out.println("Iniciando inserção de chaves. O status da cor é: true=ROSA, false=AMARELO.");
        System.out.println("-------------------------------------------------");
        
        // Sequência de chaves para testar diferentes casos (rotações e flips)
        int[] chaves = {50, 20, 80, 10, 30, 70, 90, 5, 15, 25, 35, 75, 85, 95, 40};
        
        for (int chave : chaves) {
            System.out.println("Inserindo chave: " + chave);
            arvore.inserir(chave);
        }
        
        System.out.println("-------------------------------------------------");
        
        // Imprime a estrutura final da árvore (In-Order)
        arvore.imprimir();

        // Testando a pesquisa com caminho
        System.out.println("--- Teste de Pesquisa ---");
        
        // Chave existente
        System.out.println(arvore.pesquisarComCaminho(50)); // Raiz
        System.out.println(arvore.pesquisarComCaminho(15)); // Profundo na esquerda
        System.out.println(arvore.pesquisarComCaminho(85)); // Profundo na direita
        
        // Chave inexistente
        System.out.println(arvore.pesquisarComCaminho(99)); // Inexistente (dir-dir-dir-dir)
        System.out.println(arvore.pesquisarComCaminho(1));  // Inexistente (esq-esq-esq-esq)
    }
}
