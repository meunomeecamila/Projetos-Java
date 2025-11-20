import java.util.Arrays;

/**
 * Implementação da Árvore 2-3-4 em Java.
 * Esta estrutura garante balanceamento perfeito, pois a inserção é sempre
 * precedida pela divisão (split) de qualquer nó 4 (cheio) encontrado durante a descida.
 */
public class Arvore234 {

    private No234 raiz;

    public Arvore234() {
        // A raiz começa como um nó vazio.
        this.raiz = new No234();
    }

    /**
     * Classe interna que representa um Nó da Árvore 2-3-4.
     * Um nó pode conter de 1 a 3 chaves e de 2 a 4 filhos.
     */
    private static class No234 {
        // Armazena as chaves do nó (máximo 3). Usamos um array para fácil acesso por índice.
        // O valor 0 indica uma chave vazia/não utilizada.
        private final int MAX_CHAVES = 3;
        private int[] chaves = new int[MAX_CHAVES];
        private int numChaves = 0; // Quantidade atual de chaves no nó (1, 2 ou 3)

        // Referências aos filhos (máximo 4).
        // Um nó com 'k' chaves tem 'k+1' filhos.
        private No234[] filhos = new No234[MAX_CHAVES + 1];
        private No234 pai;

        public No234() {
            // Inicialização padrão de um nó.
        }

        /**
         * Verifica se o nó atingiu sua capacidade máxima (Nó 4).
         * @return true se o nó tiver 3 chaves.
         */
        public boolean estaCheio() {
            return numChaves == MAX_CHAVES;
        }

        /**
         * Verifica se o nó não tem filhos (é uma folha).
         * @return true se todos os ponteiros de filhos forem nulos.
         */
        public boolean eFolha() {
            return filhos[0] == null;
        }

        /**
         * Imprime a estrutura do nó para fins de depuração/estudo.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < numChaves; i++) {
                sb.append(chaves[i]);
                if (i < numChaves - 1) {
                    sb.append(", ");
                }
            }
            sb.append("] (Filhos: ").append(numChaves + 1).append(")");
            return sb.toString();
        }
    }

    // ------------------------------------------------------------------
    // Métodos de Inserção e Balanceamento
    // ------------------------------------------------------------------

    /**
     * Insere uma nova chave na Árvore 2-3-4.
     * @param chave O valor inteiro a ser inserido.
     */
    public void inserir(int chave) {
        System.out.println("\n--- INSERINDO CHAVE: " + chave + " ---");
        No234 noAtual = raiz;

        // Loop principal: Desce pela árvore até encontrar uma folha ou um nó cheio.
        while (true) {
            
            // 1. VERIFICAÇÃO DE PRÉ-INSERÇÃO: Se o nó atual estiver cheio, divida-o.
            // Esta é a chave para o balanceamento da 2-3-4 Tree: a divisão é feita
            // na descida (top-down), garantindo que nunca inserimos em um nó 4.
            if (noAtual.estaCheio()) {
                System.out.println("  -> Nó Cheio encontrado: " + noAtual + ". Iniciando Divisão (Split).");
                noAtual = dividirNo(noAtual);
            }

            // 2. ENCONTRA O LOCAL DE INSERÇÃO
            if (noAtual.eFolha()) {
                // Chegamos a uma folha (e ela NÃO está cheia, pois a checagem acima garantiu isso).
                // Insere-se a chave no nó folha.
                System.out.println("  -> Inserindo " + chave + " na folha: " + noAtual);
                inserirNoNo(noAtual, chave, null);
                break; // Inserção concluída
            } else {
                // Não é folha, continua a busca pelo filho correto.
                int indiceFilho = encontrarFilhoParaChave(noAtual, chave);
                noAtual = noAtual.filhos[indiceFilho];
            }
        }
    }

    /**
     * Encontra o índice do filho que deve conter a chave ou onde a chave deve ser inserida.
     * @param no O nó atual.
     * @param chave A chave sendo buscada/inserida.
     * @return O índice do array de filhos (0 a 3).
     */
    private int encontrarFilhoParaChave(No234 no, int chave) {
        // Percorre as chaves do nó para decidir para qual filho descer.
        for (int i = 0; i < no.numChaves; i++) {
            if (chave < no.chaves[i]) {
                return i; // Desce para o filho à esquerda da chave no chaves[i]
            }
        }
        // Se a chave for maior que todas as chaves do nó, desce para o último filho.
        return no.numChaves;
    }

    /**
     * Divide um Nó 4 (cheio) em dois Nós 2, promovendo a chave do meio.
     * @param noCheio O nó a ser dividido (sempre com 3 chaves).
     * @return O nó pai atualizado ou o novo nó raiz.
     */
    private No234 dividirNo(No234 noCheio) {
        // 1. Extrai as chaves e filhos do nó cheio
        int chavePromovida = noCheio.chaves[1]; // A chave do meio (K2) sobe
        int chaveDireita = noCheio.chaves[2];  // A chave mais à direita (K3)

        No234 filho1 = noCheio.filhos[2]; // Filho 2 se torna o filho 0 do novo nó à direita
        No234 filho2 = noCheio.filhos[3]; // Filho 3 se torna o filho 1 do novo nó à direita

        // 2. Cria o novo nó à direita, contendo apenas K3
        No234 novoNoDireita = new No234();
        novoNoDireita.chaves[0] = chaveDireita;
        novoNoDireita.numChaves = 1;
        
        // 3. Atualiza o nó original, que se torna o novo nó à esquerda (contendo apenas K1)
        noCheio.chaves[1] = 0; noCheio.chaves[2] = 0; // Limpa K2 e K3
        noCheio.numChaves = 1; // Fica apenas com K1

        // 4. Se não for folha, reatribui os filhos
        if (!noCheio.eFolha()) {
            // Reatribui filhos ao novo nó à direita
            novoNoDireita.filhos[0] = filho1;
            novoNoDireita.filhos[1] = filho2;
            if (filho1 != null) filho1.pai = novoNoDireita;
            if (filho2 != null) filho2.pai = novoNoDireita;

            // Limpa as referências de filhos no nó original (K1)
            noCheio.filhos[2] = null;
            noCheio.filhos[3] = null;
        }

        // 5. Trata a promoção da chave (K2) para o pai
        No234 pai = noCheio.pai;

        if (pai == null) {
            // CASO: Divisão na RAIZ.
            System.out.println("  -> Divisão da Raiz. Aumentando a altura da árvore.");
            pai = new No234(); // Cria uma nova raiz
            this.raiz = pai;
            
            // A chave promovida (K2) vai para a nova raiz
            inserirNoNo(pai, chavePromovida, noCheio); // K2 vai para a nova raiz
            
            // K1 (noCheio) e K3 (novoNoDireita) se tornam filhos da nova raiz.
            pai.filhos[0] = noCheio;
            pai.filhos[1] = novoNoDireita;
            noCheio.pai = pai;
            novoNoDireita.pai = pai;
            
            return pai;
        } else {
            // CASO: Divisão em um nó não-raiz.
            // Insere a chave K2 promovida no nó pai.
            System.out.println("  -> Chave " + chavePromovida + " promovida para o Pai: " + pai);
            inserirNoNo(pai, chavePromovida, noCheio);
            
            // O novo nó à direita se torna o filho à direita de K2 no pai.
            // O nó original (K1) permanece como o filho à esquerda de K2.
            
            // O método inserirNoNo já cuida de mover o ponteiro do filho existente
            // para abrir espaço para o novoNoDireita.
            
            // Precisamos atualizar o pai do novo nó à direita
            novoNoDireita.pai = pai;
            
            return pai;
        }
    }

    /**
     * Insere uma chave num nó que NÃO está cheio, e reordena as chaves e filhos.
     * Esta função também cuida de ligar o novo filho (que veio do split).
     * @param no O nó a ser modificado.
     * @param novaChave A chave a ser adicionada.
     * @param novoFilhoDireita O novo nó (K3 do split) que deve se tornar filho de 'no'. Pode ser null.
     */
    private void inserirNoNo(No234 no, int novaChave, No234 novoFilhoDireita) {
        
        // 1. Encontra a posição correta para a nova chave
        int pos = no.numChaves;
        while (pos > 0 && no.chaves[pos - 1] > novaChave) {
            // 2. Move chaves e filhos existentes para a direita para abrir espaço
            no.chaves[pos] = no.chaves[pos - 1]; // Move a chave
            no.filhos[pos + 1] = no.filhos[pos]; // Move a referência do filho
            pos--;
        }

        // 3. Insere a nova chave
        no.chaves[pos] = novaChave;
        no.numChaves++;

        // 4. Liga o novo filho, se houver (o K3 do split)
        if (novoFilhoDireita != null) {
            // O novo filho é ligado na posição à direita da nova chave
            no.filhos[pos + 1] = novoFilhoDireita; 
            // O nó original (K1 do split) permanece em no.filhos[pos]
        }
    }
    
    // ------------------------------------------------------------------
    // Métodos de Visualização
    // ------------------------------------------------------------------

    private void imprimirRecursivo(No234 no, int nivel) {
        if (no == null) {
            return;
        }

        // Imprime o nó atual, indentado
        for (int i = 0; i < nivel; i++) {
            System.out.print("   ");
        }
        System.out.println(no);

        // Percorre todos os filhos
        for (int i = 0; i <= no.numChaves; i++) {
            imprimirRecursivo(no.filhos[i], nivel + 1);
        }
    }

    public void imprimir() {
        System.out.println("\n*** ESTRUTURA ATUAL DA ÁRVORE 2-3-4 ***");
        imprimirRecursivo(raiz, 0);
        System.out.println("*************************************\n");
    }

    // ------------------------------------------------------------------
    // Função Main para Teste
    // ------------------------------------------------------------------

    public static void main(String[] args) {
        Arvore234 arvore = new Arvore234();

        // Sequência de chaves que força a divisão e o aumento da altura
        int[] chavesParaInserir = {10, 20, 30, 40, 50, 60, 70, 80, 5, 25};

        for (int chave : chavesParaInserir) {
            arvore.inserir(chave);
            arvore.imprimir();
        }

        System.out.println("-------------------------------------------------");
        System.out.println("Árvore 2-3-4 finalizada. Observe que a altura é mínima.");
        System.out.println("-------------------------------------------------");
    }
}
