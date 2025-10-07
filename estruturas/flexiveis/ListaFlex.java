// Obs: fila implementada de forma simples pra estudos, com throw exception
// Métodos implementados até agora: Inserir, remover e mostrar, pesquisar e tamanho

class Lista {
	private Celula primeiro;
	private Celula ultimo;
    private int tamanho = 0;

    //Construtor
	public Lista() {
		primeiro = new Celula();
		ultimo = primeiro;
	}

    //Inserir no inicio 
	public void inserirInicio(int x) {
		Celula tmp = new Celula(x);
      tmp.prox = primeiro.prox;
		primeiro.prox = tmp;
		if (primeiro == ultimo) {                 
			ultimo = tmp;
		}
      tmp = null;
      tamanho++;
	}


	//Inserir no fim
	public void inserirFim(int x) {
		ultimo.prox = new Celula(x);
		ultimo = ultimo.prox;
        tamanho++;
	}


	//Remover no inicio
	public int removerInicio() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		}

      Celula tmp = primeiro;
		primeiro = primeiro.prox;
		int resp = primeiro.elemento;
      tmp.prox = null;
      tmp = null;
      tamanho--;
      return resp;
	}

	//Remover no fim
	public int removerFim() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		} 

		// Caminhar ate a penultima celula:
      Celula i;
      for(i = primeiro; i.prox != ultimo; i = i.prox);

      int resp = ultimo.elemento; 
      ultimo = i; 
      i = ultimo.prox = null;
      tamanho--;
      return resp;
	}


	//Inserir em uma posição especifica
   public void inserir(int x, int pos) throws Exception {

      int tamanho = tamanho();

    //nao da para inserir em uma posição negativa ou maior que o tamanho da lista
      if(pos < 0 || pos > tamanho){
			throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
      } else if (pos == 0){
         inserirInicio(x); 
      } else if (pos == tamanho){
         inserirFim(x);
      } else {
		   // Caminhar ate a posicao anterior a insercao
         Celula i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         Celula tmp = new Celula(x);
         tmp.prox = i.prox;
         i.prox = tmp;
         tmp = i = null;
      }
      tamanho--;
   }


	//Remover em uma posição específica
	public int remover(int pos) throws Exception {
      int resp;
      int tamanho = tamanho();

		if (primeiro == ultimo){
			throw new Exception("Erro ao remover (vazia)!");

      } else if(pos < 0 || pos >= tamanho){
			throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
      } else if (pos == 0){
         resp = removerInicio();
      } else if (pos == tamanho - 1){
         resp = removerFim();
      } else {
		   // Caminhar ate a posicao anterior a insercao
         Celula i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         Celula tmp = i.prox;
         resp = tmp.elemento;
         i.prox = tmp.prox;
         tmp.prox = null;
         i = tmp = null;
      }

		tamanho--;
        return resp;
	}

	//Mostrar
	public void mostrar() {
		System.out.print("[ ");
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			System.out.print(i.elemento + " ");
		}
		System.out.println("] ");
	}

	//Pesquisar
	public boolean pesquisar(int x) {
		boolean resp = false;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
         if(i.elemento == x){
            resp = true;
            i = ultimo;
         }
		}
		return resp;
	}

	//Pegar o tamanho
   public int tamanho() {
    //tambem podemos simplesmente pegar a variavel feita
      int tamanho = 0; 
      for(Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
      return tamanho;
   }

   //Remover a segunda posição válida
   //obs: esse codigo considera a existencia de um nó cabeça
   //obs: feito com apenas uma célula. Pode ser feito com duas
   public int removerPos2Met() throws Exception {
    //verificação 
    if(primeiro.prox.prox == null){
        throw new Exception("Não há posição 2!");
    } 

    Celula tmp = primeiro.prox; //celula aponta pro 1
    int element = tmp.prox.elemento;
    tmp.prox = tmp.prox.prox; 

    return element;

   }

    //Remover a segunda posição válida
    //obs: esse codigo considera a existencia de um nó cabeça
    public int removerPos2() throws Exception{
        if (primeiro.prox == null || primeiro.prox.prox == null) {
            throw new Exception("Não há posição 2!");
        }

        Celula p = primeiro.prox; //Passo 2
        Celula q = p.prox; //Passo 3

        p.prox = q.prox; //Passo 4
        //Passo 5
        int element = q.elemento;
        q.prox = null;
        q = null;
        return elemento;
   }
}

//criar uma classe que garante sempre que os elementos fiquem ordenados
public class ListaSimplesEncadeadaOrdenada {

    private Celula primeiro;
    private Celula ultimo;

    public ListaSimplesEncadeadaOrdenada() {
        primeiro = new Celula(); // nó cabeça
        ultimo = primeiro;
    }

    public void inserir(int x) {
        Celula nova = new Celula(x);
        Celula anterior = primeiro;
        Celula atual = primeiro.prox;

        // procurar a posição onde o novo elemento deve entrar
        while (atual != null && atual.elemento < x) {
            anterior = atual;
            atual = atual.prox;
        }

        // inserir entre "anterior" e "atual"
        nova.prox = atual;
        anterior.prox = nova;

        // se inseriu no fim, atualizar o último
        if (nova.prox == null) {
            ultimo = nova;
        }
    }

    //Atividades pedidas -> Implementação dos métodos de ordenação
    public void shellsort() {
       int n = tamanho();
       int[] array = new int[n];

       //copiar lista para vetor
       int k = 0;
       for(Celula i = primeiro.prox; i != null; i = i.prox){
           array[k++] = i.elemento;
       }

       //algoritmo shellsort
       for (int gap = n/2; gap > 0; gap /= 2) {
           for (int i = gap; i < n; i++) {
               int temp = array[i];
               int j;
               for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                   array[j] = array[j - gap];
               }
               array[j] = temp;
           }
       }

       //regravar na lista
       Celula atual = primeiro.prox;
       for (int i = 0; i < n; i++, atual = atual.prox) {
           atual.elemento = array[i];
       }
   }

   //Quicksort simples
   public void quicksort() {
       int n = tamanho();
       int[] array = new int[n];
       int k = 0;
       for(Celula i = primeiro.prox; i != null; i = i.prox){
           array[k++] = i.elemento;
       }

       quicksortRec(array, 0, n - 1);

       Celula atual = primeiro.prox;
       for (int i = 0; i < n; i++, atual = atual.prox) {
           atual.elemento = array[i];
       }
   }

   private void quicksortRec(int[] array, int esq, int dir) {
       int i = esq, j = dir;
       int pivo = array[(esq + dir) / 2];
       while (i <= j) {
           while (array[i] < pivo) i++;
           while (array[j] > pivo) j--;
           if (i <= j) {
               int tmp = array[i];
               array[i] = array[j];
               array[j] = tmp;
               i++; j--;
           }
       }
       if (esq < j) quicksortRec(array, esq, j);
       if (i < dir) quicksortRec(array, i, dir);
   }

}