//Montar as classes
public class Nopreto {
    char elemento;
    public Nopreto esq;
    public Nopreto dir;
    public Noverme raiz;

    //construtor completo
    Nopreto(char e, Nopreto esq, Nopreto dir, Noverme raiz){
        this.elemento = e;
        this.esq = esq;
        this.dir = dir;
        this.raiz = raiz;
    }

    //construtor apenas com o char
    Nopreto(char e){
        this(e,null,null,null);
    }
}

public class Noverme {
    public Noverme esq;
    public Noverme dir;
    String nome;

    //construtor completo
    Noverme(Noverme esq, Noverme dir, String n){
        this.nome = n;
        this.esq = esq;
        this.dir = dir;
    }

    //construtor apenas com a string
    Noverme(String n){
        this(null,null,n);
    }
}

public class Arvorepreta{
    Nopreto raiz;

    //construtor
    Arvorepreta(Nopreto raiz){
        this.raiz = raiz;
    }

    Arvorepreta(){
        this(null);
    }   

    //método de pesquisar pela string nome
    boolean PesquisarPreto(String nome, Nopreto i){
        char letra = nome.charAt(0); //pegar a inicial
        if(i == null) return false;
        else if(letra == i.elemento) return PesquisarVerme(nome, i.raiz);
        else if(letra < i.elemento) return PesquisarPreto(nome, i.esq);
        else return PesquisarPreto(nome, i.dir);
    }
}

public class Arvoreverme {
    Noverme raiz;

    Arvoreverme(Noverme raiz){
        this.raiz = raiz;
    }

    Arvoreverme(){
        this(null);
    }

    boolean PesquisarVerme(String nome, Nopreto i){
        //fazer um contador pra contar os char 
        int j = 1; //ja checou a primeira letra
        return PesquisarVerme(nome, i, j);
    }

    boolean PesquisarVerme(String nome, Noverme i, int j){
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
}






    