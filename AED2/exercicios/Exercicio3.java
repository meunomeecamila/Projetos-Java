//Montar as classes
public class Nopreto {
    char elemento;
    public Nopreto esq;
    public Nopreto dir;
    public Noverme raiz;

    //construtor completo
    public Nopreto(char e, Nopreto esq, Nopreto dir, Noverme raiz){
        this.elemento = e;
        this.esq = esq;
        this.dir = dir;
        this.raiz = raiz;
    }

    //construtor apenas com o char
    public Nopreto(char e){
        this(e,null,null,null);
    }
}

public class Noverme {
    public Noverme esq;
    public Noverme dir;
    String nome;

    //construtor completo
    public Noverme(Noverme esq, Noverme dir, String n){
        this.nome = n;
        this.esq = esq;
        this.dir = dir;
    }

    //construtor apenas com a string
    public Noverme(String n){
        this(null,null,n);
    }
}

public class Arvorepreta{
    Nopreto raiz;

    //construtor
    public Arvorepreta(Nopreto raiz){
        this.raiz = raiz;
    }

    public Arvorepreta(){
        this(null);
    }  

    Arvoreverme arvoreV = new Arvoreverme(); //árvore vermelha que vamos usar

    //método de pesquisar pela string nome
    public boolean PesquisarPreto(String nome, Nopreto i){
        char letra = nome.charAt(0); //pegar a inicial
        if(i == null) return false;
        else if(letra == i.elemento) return arvoreV.PesquisarVerme(nome, i.raiz);
        else if(letra < i.elemento) return PesquisarPreto(nome, i.esq);
        else return PesquisarPreto(nome, i.dir);
    }

    //método de inserir pela string nome
    public void inserirPreto(String nome, Nopreto i){
        char letra = nome.charAt(0); //inicial que queremos
        char ii = i.elemento; //onde estamos

        if(i == null) System.out.println("Erro!");
        else if(ii == letra){
            //achou a posição para inserir, chamar a função na outra árvore
            i.raiz = arvoreV.inserirVerme(nome, i.raiz);
        }
        else if(letra < ii) inserirPreto(nome, i.esq);
        else if(letra > ii) inserirPreto(nome, i.dir);

        //se não entrou em nenhum, não tem a letra
    }

    public void mostrarPreto(Nopreto i){
        if(i == null) return;
        
        //printar a letra, toda a sua árvore vermelha, e depois passar para as próximas
        System.out.println(i.elemento);
        arvoreV.mostrarVerme(i.raiz);

        mostrarPreto(i.esq);
        mostrarPreto(i.dir);
    }

    //função que retorna true se tivermos uma string de tamanho 10
    public boolean stringPreto(Nopreto i){
        //percorrer todas as letras
        if(i == null) return false;
        else if((arvoreV.stringVerme(i.raiz)) == true) return true;
        else return stringPreto(i.esq) || stringPreto(i.dir);
    }

    //função que retorna true se tivermos uma string de tamanho 10 que começa com o char c
    public boolean charPreto(Nopreto i, char c){
        if(i == null) return false;
        else if((arvoreV.charVerme(i.raiz, c)) == true) return true;
        else return charPreto(i.esq,c) || charPreto(i.dir,c);
    }
}

public class Arvoreverme {
    Noverme raiz;

    public Arvoreverme(Noverme raiz){
        this.raiz = raiz;
    }

    public Arvoreverme(){
        this(null);
    }

    public boolean PesquisarVerme(String nome, Noverme i){
        //fazer um contador pra contar os char 
        int j = 1; //ja checou a primeira letra
        return PesquisarVerme(nome, i, j);
    }

    public boolean PesquisarVerme(String nome, Noverme i, int j){
        if(i == null) return false; //nao achou

        //se ainda tiverem caracteres para checarmos se é igual a string
        if(nome.length() > j && i.nome.length() > j) {
            char letra = nome.charAt(j); //letra que queremos
            char atual = i.nome.charAt(j); //letra atual
            
            if(letra < atual) return PesquisarVerme(nome, i.esq, j); 
            else if(letra > atual) return PesquisarVerme(nome, i.dir, j);
                
            //se chegou ate aqui a letra é igual
            return PesquisarVerme(nome, i, j+1); //checa a proxima letra
        }

        //se chegou ate aqui, conferir
        if(j == i.nome.length() && j == nome.length()) return true;
        else return false;
    }

    public Noverme inserirVerme(String nome, Noverme i){
        int j = 1; //ja sabe que o primeiro char é igual
        return inserirVerme(nome, i, j);
    }

    public Noverme inserirVerme(String nome, Noverme i, int j){
        if(i == null) i = new Noverme(nome); //folha
        if(j == nome.length()) return i;

        else if(j < nome.length() && j < i.nome.length()){
            char letra = nome.charAt(j); //o que queremos
            char ii = i.nome.charAt(j); //onde estamos
        
            //agora sim, procuramos onde temos que inserir
            if(letra < ii) i.esq = inserirVerme(nome,i.esq,j);
            else if(letra > ii) i.dir = inserirVerme(nome,i.dir,j);

            //se chegou até aqui, a letra é igual. Conferir as próximas
            else i = inserirVerme(nome, i, j+1); 
        }

        return i;
    }

    public void mostrarVerme(Noverme i){
        if(i == null) return;
        System.out.println(i.nome);
        
        mostrarVerme(i.esq);
        mostrarVerme(i.dir);
    }

    //função que retorna true se tivermos uma string de tamanho 10
    public boolean stringVerme(Noverme i){
        if(i == null) return false;
        else if(i.nome.length() == 10) return true;
        else return stringVerme(i.esq) || stringVerme(i.dir);
    }

    //função que retorna true se tivermos uma string de tamanho 10 que começa com o char c
    public boolean charVerme(Noverme i, char c){
        if(i == null) return false;
        else if(i.nome.length() == 10 && i.nome.charAt(0) == c) return true;
        else return charVerme(i.esq,c) || charVerme(i.dir,c);
    }
    
}






    