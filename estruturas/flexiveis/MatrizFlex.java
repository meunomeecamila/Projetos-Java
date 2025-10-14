// Obs: matriz implementada de forma dupla pra estudos, sem throw exception
// Métodos implementados até agora: mostrar

class Celula {
    public int elemento;
    public Celula ant, prox, sup, inf; // ponteiros para as 4 direções

    // Construtor padrão (sem elemento definido)
    public Celula() {
        this(0);
    }

    // Construtor com elemento
    public Celula(int elemento) {
        this.elemento = elemento;
        this.ant = this.prox = this.sup = this.inf = null;
    }
}

public class MatrizFlexDupla {
    private Celula inicio;  // primeira célula (canto superior esquerdo)
    private int linhas, colunas;

    // Construtor da matriz flexível dupla
    public MatrizFlexDupla(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        construirMatriz(); // chama o método que monta toda a estrutura
    }

    //método que o max pediu
        private void construirMatriz() {
        // Cria a primeira célula (canto superior esquerdo)
        inicio = new Celula();

        Celula linha = inicio;  // ponteiro que percorre as linhas
        Celula coluna;          // ponteiro que percorre as colunas
        Celula superior = null; // ponteiro para conectar linha de cima

        // Cria as demais linhas
        for (int i = 0; i < linhas; i++) {
            coluna = linha; // começa pelo início da linha atual

            // Cria as colunas da linha
            for (int j = 1; j < colunas; j++) {
                // Cria nova célula e liga à anterior
                coluna.prox = new Celula();
                coluna.prox.ant = coluna; // liga à esquerda
                coluna = coluna.prox;
            }

            // Se ainda houver próxima linha, cria a célula inicial da próxima linha
            if (i < linhas - 1) {
                linha.inf = new Celula(); // cria início da próxima linha
                linha.inf.sup = linha;    // liga acima
                superior = linha;         // guarda referência da linha de cima
                linha = linha.inf;        // desce para próxima linha

                // Liga as colunas verticalmente
                Celula deCima = superior.prox;
                Celula deBaixo = linha;

                while (deCima != null && deBaixo != null) {
                    // Cria célula abaixo da de cima
                    deBaixo.prox = new Celula();
                    deBaixo.prox.ant = deBaixo;
                    deBaixo.prox.sup = deCima;
                    deCima.inf = deBaixo.prox;

                    // anda para a direita
                    deCima = deCima.prox;
                    deBaixo = deBaixo.prox;
                }
            }
        }
    }
    
    //método de mostrar
    public void mostrar() {
        Celula linha = inicio;
        while (linha != null) {
            Celula coluna = linha;
            while (coluna != null) {
                System.out.print(coluna.elemento + " ");
                coluna = coluna.prox;
            }
            System.out.println();
            linha = linha.inf;
        }
    }
}


