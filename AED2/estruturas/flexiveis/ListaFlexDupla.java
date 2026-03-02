// Obs: fila implementada de forma simples pra estudos, com throw exception
// Métodos implementados até agora: inserir, remover, mostrar, tamanho, quicksort e shellsort

class ListaDupla {
	private CelulaDupla primeiro;
	private CelulaDupla ultimo;

	public ListaDupla() {
		primeiro = new CelulaDupla();
		ultimo = primeiro;
	}

    //Inserir no início
	public void inserirInicio(int x) {
		CelulaDupla tmp = new CelulaDupla(x);

      tmp.ant = primeiro;
      tmp.prox = primeiro.prox;
      primeiro.prox = tmp;
      if (primeiro == ultimo){
         ultimo = tmp;
      }
      else {
         tmp.prox.ant = tmp;
      }
      tmp = null;
	}


	//Inserir no fim
	public void inserirFim(int x) {
        ultimo.prox = new CelulaDupla(x);
        ultimo.prox.ant = ultimo;
		ultimo = ultimo.prox;
	}


	//Remover no inicio
	public int removerInicio() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		}

      CelulaDupla tmp = primeiro;
      primeiro = primeiro.prox;
      int resp = primeiro.elemento;
      tmp.prox = primeiro.ant = null;
      tmp = null;
      return resp;
	}


	//Remover no fim
	public int removerFim() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		} 
        int resp = ultimo.elemento;
        ultimo = ultimo.ant;
        ultimo.prox.ant = null;
        ultimo.prox = null;
		return resp;
	}


    //Inserir em pos especifica
   public void inserir(int x, int pos) throws Exception {
      int tamanho = tamanho();

      if(pos < 0 || pos > tamanho){
			throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
      } else if (pos == 0){
         inserirInicio(x);
      } else if (pos == tamanho){
         inserirFim(x);
      } else {
		   // Caminhar ate a posicao anterior a insercao
         CelulaDupla i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         CelulaDupla tmp = new CelulaDupla(x);
         tmp.ant = i;
         tmp.prox = i.prox;
         tmp.ant.prox = tmp.prox.ant = tmp;
         tmp = i = null;
      }
   }


	// Remover de posição especifica
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
         CelulaDupla i = primeiro.prox;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         i.ant.prox = i.prox;
         i.prox.ant = i.ant;
         resp = i.elemento;
         i.prox = i.ant = null;
         i = null;
      }

		return resp;
	}


	//Mostrar
	public void mostrar() {
		System.out.print("[ "); // Comeca a mostrar.
		for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
			System.out.print(i.elemento + " ");
		}
		System.out.println("] "); // Termina de mostrar.
	}


	//Mostrar invertido
	public void mostrarInverso() {
		System.out.print("[ ");
		for (CelulaDupla i = ultimo; i != primeiro; i = i.ant){
			System.out.print(i.elemento + " ");
      }
		System.out.println("] "); // Termina de mostrar.
	}


	//Pesquisar
	public boolean pesquisar(int x) {
		boolean resp = false;
		for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
         if(i.elemento == x){
            resp = true;
            i = ultimo;
         }
		}
		return resp;
	}

	//Retornar tamanho
   public int tamanho() {
      int tamanho = 0; 
      for(CelulaDupla i = primeiro; i != ultimo; i = i.prox, tamanho++);
      return tamanho;
   }

//QUICKSORT =================================

// Função auxiliar para pegar o pivô (valor do meio)
public int pegarOPivo() {
    int meio = tamanho() / 2;
    CelulaDupla i = primeiro.prox;
    for (int j = 0; j < meio; j++, i = i.prox);
    return i.elemento; // devolve o valor, não a célula
}

// Função principal do Quicksort (recursiva por índices)
public void quicksort(int esq, int dir) {
    int i = esq;
    int j = dir;
    int pivo = pegarOPivo();

    while (i <= j) {
        while (encontrar(i) < pivo) i++;
        while (encontrar(j) > pivo) j--;

        if (i <= j) {
            int temp = encontrar(i);
            replace(i, encontrar(j));
            replace(j, temp);
            i++;
            j--;
        }
    }

    // chamadas recursivas
    if (esq < j) quicksort(esq, j);
    if (i < dir) quicksort(i, dir);
}

// Função para iniciar a ordenação sem precisar passar índices
public void quicksort() {
    if (tamanho() > 1) {
        quicksort(0, tamanho() - 1);
    }
}

//SHELLSORT =================================
// Função auxiliar: encontrar valor em uma posição
public int encontrar(int pos) {
    if (pos < 0 || pos >= tamanho()) {
        System.out.println("Posição inválida!");
        return -1;
    }

    CelulaDupla i = primeiro.prox; // pula o nó cabeça
    for (int j = 0; j < pos; j++, i = i.prox);
    return i.elemento;
}

// Função auxiliar: substituir valor em uma posição
public void replace(int pos, int rep) {
    if (pos < 0 || pos >= tamanho()) {
        System.out.println("Posição inválida!");
        return;
    }

    CelulaDupla i = primeiro.prox;
    for (int j = 0; j < pos; j++, i = i.prox);
    i.elemento = rep;
}

// Shellsort propriamente dito
public void shellSort() {
    int tam = tamanho();

    // começa com gap e vai diminuindo pela metade
    for (int gap = tam / 2; gap > 0; gap /= 2) {

        // percorre os elementos a partir do gap
        for (int i = gap; i < tam; i++) {
            int temp = encontrar(i);
            int j = i;

            // move elementos gap posições atrás, se maiores que temp
            while (j >= gap && encontrar(j - gap) > temp) {
                replace(j, encontrar(j - gap));
                j -= gap;
            }

            // insere o elemento temporário na posição correta
            replace(j, temp);
        }
    }
}



}