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
   //falta arrumar a esquerda e a direita
   public static void Quicksort() {
        int i = Celula i, j = tamanho();
        int pivo = PegarOPivot(); 

        while (true) {
            if (i > j) break;             

            while (true) {         
                if (!(encontrar[i] < pivo)) break;
                i++;
            }

            while (true) {
                if (!(encontrar[j] > pivo)) break;
                j--;
            }

            if (i <= j) {
               int array_i = encontrar(i);
               int array_j = encontrar(j);
               replace(array_i, array_j);
                i++;
                j--;
            }
        }

        if (esq < j) Quicksort(esq, j, array);  // Recursão na parte esquerda
        if (i < dir) Quicksort(i, dir, array);  // Recursão na parte direita
   }

   public int PegarOPivot(){
      int tam = tamanho();
      tam /= 2;
      // Caminhar ate a posicao anterior ao meio
      CelulaDupla i = primeiro.prox;
      for(int j = 0; j < tam; j++, i = i.prox);

      //devolver o meio como pivô
      return i.prox;
   }

   //SHELLSORT =================================
   //Fazer uma função auxiliar de encontrar e uma de substituir
   public int encontrar(int pos){
      int valorpos = 0; //valor da posição que quer encontrar
      if(pos >= 0 || pos < tamanho()){
         CelulaDupla i = primeiro.prox;
         for(int j = 0; j < pos; j++, i = i.prox);
         valor = i.elemento;
      }

      return valor;
   }

   public void replace(int pos, int rep){
      if(pos >= 0 || pos < tamanho()){
         int valor = 0;
         CelulaDupla i = primeiro.prox;
         for(int j = 0; j < pos; j++, i = i.prox);
         i.elemento = rep; //repoe o elemento
      }
   }

   //shellsort propriamente dito
   public void shellSort(){
      int tam = tamanho();
      
      //Começa com um gap e vai diminuindo
      for (int gap = tam / 2; gap > 0; gap /= 2) {

            // Faz a ordenação por inserção, mas com o intervalo "gap"
            for (int i = gap; i < n; i++) {
                int temp = encontrar(i); //encontrar o "array" na posição i
                int j = i;

                // Move os elementos que estão "gap" posições atrás
                while (j >= gap && encontrar(j - gap) > temp) {
                  replace(j,j-gap);
                  j -= gap;
                }

                replace(j,temp);
            }
      }

   }




}