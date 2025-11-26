/* PASSO A PASSO TP
01- Ler os ids até FIM e salvar os objetos em um uma árvore binária de nome
02- Ler os nomes pedidos e verificar se estão na árvore
03- Imprimir o nome, o caminho percorrido na árvore e SIM ou NAO
04- Gerar um arquivo log
*/
//obs: nao foi especificado o que colocar dentro do arquivo log, então coloquei a saída, matricula e tempo

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String []args){
        //inicia a contagem de tempo
        long startTime = System.currentTimeMillis();
        
        Game[] vetor = new Game[2000];
        
        for(int i=0; i<2000; i++){
            vetor[i] = new Game();  
        }

        try(BufferedReader br = new BufferedReader(new FileReader("/tmp/games.csv"))){
            br.readLine(); //ignorar o cabeçalho do csv

            //pegar as próximas linhas e guardar no vetor da classe game
            for(int i=0; i<2000; i++){
                String linha = br.readLine();
                if(linha == null) break;
                lerlinha(linha, i, vetor);
            }
        }
        catch(IOException e){
            System.err.println("erro");
            e.printStackTrace();
        }

        //obs: lembrando que pode ser uma árvore de qqr cor (bicolor)
        ArvoreAlvinegra arvore = new ArvoreAlvinegra();
        Scanner sc = new Scanner(System.in);
        
        while (sc.hasNextLine()) {
            String entrada = sc.nextLine().trim(); // Lê a linha inteira
            if(entrada.equals("FIM")) break;
            
            try {
                int id = Integer.parseInt(entrada);
                Game g = buscarPorId(id, vetor);
                if (g != null) {
                    arvore.inserir(g);  
                }
            } catch (NumberFormatException e) {
                //ignorar
            }
        }
        
        //agora, vamos começar a ler os nomes e printar os caminhos
        //printei tudo no arquivo log tbm. 
        try (PrintWriter logWriter = new PrintWriter("885761_arvoreAlvinegra.txt")) {
            while (sc.hasNextLine()) {
                String nomeBusca = sc.nextLine().trim();

                //obs: essa linha resolve o problema de imprimir e pesquisar por "FIM" na árvore
                if (nomeBusca.equals("FIM")) continue; 
                
                String resultado = arvore.pesquisarComCaminho(nomeBusca);
                
                //printar tanto no log quanto na saída
                System.out.println(resultado);
                logWriter.println(resultado);
            }

            //calcula o tempo de execução
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logWriter.println("Matrícula: 885761 ");
            logWriter.println("Tempo de execução: " + executionTime);
            
        } catch (IOException e) {
            System.err.println("erro ao escrever no log");
        }

        sc.close();
    }

    //procura e retorna o objeto game no vetor dado um id
    public static Game buscarPorId(int id, Game[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] != null && vetor[i].getId() == id) {
                return vetor[i].clone(); //usa o clone para não alterar o objeto original no vetor
            }
        }
        return null;
    }

    //implementa a funcao de salvar o campo no objeto game
    static void salvarCampo(Game g, int campo, String valor){
        //temos 14 elementos e portanto de 0 a 13 campos
        if(campo == 0){ //id
            try {
                if(valor.isEmpty()) g.setId(0);
                else g.setId(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setId(0); }
        }

        else if(campo == 1){ //nome
            g.setName(aspasExternas(valor));
        }

        else if (campo == 2) { //release date
            String raw = aspasExternas(valor).trim().replace("\"", "");
            String dataFormatada = "";

            if (raw.matches("\\d{4}")) {
                dataFormatada = "01/01/" + raw;
            }
            // Suporte para formatos de data americanos
            else if (raw.matches("[a-zA-Z]+,? \\d{4}")) { 
                String[] partes = raw.replace(",", "").split(" ");
                String mes = partes[0];
                String ano = partes[1];
                dataFormatada = "01/" + mesParaNumero(mes) + "/" + ano;
            }
            // Suporte para formatos de data americanos com dia
            else if (raw.matches("[a-zA-Z]+ \\d{1,2},? \\d{4}")) { 
                String[] partes = raw.replace(",", "").split(" ");
                String mes = partes[0];
                String dia = partes[1];
                String ano = partes[2];
                if (dia.length() == 1) dia = "0" + dia;
                dataFormatada = dia + "/" + mesParaNumero(mes) + "/" + ano;
            }
            else {
                dataFormatada = "01/01/0001";
            }

            g.setReleaseDate(dataFormatada);
        }

        else if(campo == 3){ //estimated owners
            String digitos = valor.replaceAll("\\D+", "");
            try {
                if (digitos.isEmpty()) g.setEstimatedOwners(0);
                else g.setEstimatedOwners(Integer.parseInt(digitos));
            } catch (NumberFormatException e) { g.setEstimatedOwners(0); }
        }

        else if(campo == 4){ //price
            if (valor.equalsIgnoreCase("free to play")) g.setPrice(0.0f);
            else if (!valor.isEmpty()) {
                try {
                    g.setPrice(Float.parseFloat(valor.replace(',', '.')));
                } catch (NumberFormatException e) {
                    g.setPrice(0.0f);
                }
            }
        }

        else if(campo == 5){ //supported languages
            g.setSupportedLanguages(removeColchete(aspasExternas(valor)));
        }

        else if(campo == 6){ //metacritic score
            try {
                if (valor.isEmpty()) g.setMetacriticScore(-1);
                else g.setMetacriticScore(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setMetacriticScore(-1); }
        }

        else if(campo == 7){ //user score
            if (valor.isEmpty() || valor.equalsIgnoreCase("tbd")) g.setUserScore(-1.0f);
            else {
                try {
                    g.setUserScore(Float.parseFloat(valor.replace(',', '.')));
                } catch (NumberFormatException e) {
                    g.setUserScore(-1.0f);
                }
            }
        }

        else if(campo == 8){ //achievement
            try {
                if (valor.isEmpty()) g.setAchievements(0);
                long achievementsLong = Long.parseLong(valor);
                // Trata overflow de int
                g.setAchievements(achievementsLong > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)achievementsLong); 
            } catch (NumberFormatException e) { g.setAchievements(0); }
        }

        else if(campo == 9){ //publishers
            g.setPublishers(separaVirgula(aspasExternas(valor)));  
        }

        else if(campo == 10){ //developers
            g.setDevelopers(separaVirgula(aspasExternas(valor)));  
        }

        else if(campo == 11){ //categories
            g.setCategories(removeColchete(aspasExternas(valor)));
        }

        else if(campo == 12){ //generos
            g.setGenres(removeColchete(aspasExternas(valor)));
        }

        else if(campo == 13){ //tags
            g.setTags(removeColchete(aspasExternas(valor)));
        }
    }

    //funcao para ler uma linha do csv e popular um objeto game
    public static void lerlinha(String linha, int i, Game[] vetor){
        int campo = 0;
        StringBuilder atual = new StringBuilder(); 
        boolean aspas = false;
        int colchetes = 0;

        for(int j=0; j<linha.length(); j++){
            char c = linha.charAt(j); 

            // Tratamento para aspas fora de colchetes (quebras de campo)
            if(c == '\"' && colchetes == 0){
                aspas = !aspas;
            }
            else if(c == '['){
                colchetes++;
            }
            else if(c == ']'){
                colchetes--;
            }

            // Apenas separa se for vírgula E não estiver entre aspas E não estiver em colchetes
            if(c == ',' && aspas == false && colchetes == 0){
                salvarCampo(vetor[i], campo, atual.toString().trim());
                campo++;
                atual.setLength(0);
                continue;
            }
            
            atual.append(c);
        }

        //último campo
        salvarCampo(vetor[i], campo, atual.toString().trim());
    }
    
    //remove aspas externas se existirem
    static String aspasExternas(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.length() >= 2) {
            char a = s.charAt(0), b = s.charAt(s.length()-1);
            if ((a == '"' && b == '"') || (a == '\'' && b == '\'')) {
                return s.substring(1, s.length()-1).trim();
            }
        }
        return s;
    }
    
    //transforma o mes para numero no formato "mm"
    static String mesParaNumero(String mes) {
        mes = mes.toLowerCase();
        if (mes.startsWith("jan")) return "01";
        if (mes.startsWith("feb")) return "02";
        if (mes.startsWith("mar")) return "03";
        if (mes.startsWith("apr")) return "04";
        if (mes.startsWith("may")) return "05";
        if (mes.startsWith("jun")) return "06";
        if (mes.startsWith("jul")) return "07";
        if (mes.startsWith("aug")) return "08";
        if (mes.startsWith("sep")) return "09";
        if (mes.startsWith("oct")) return "10";
        if (mes.startsWith("nov")) return "11";
        if (mes.startsWith("dec")) return "12";
        return "01";
    }

    //funcao de separar string por virgula e limpar aspas/espacos
    static String[] separaVirgula(String s) {
        if (s == null) s = "";
        else s = s.trim();
        
        if (s.isEmpty()) return new String[0];
        
        String[] arr = s.split(",");
        
        for (int k=0; k < arr.length; k++) {
            String t = arr[k].trim();
            
            boolean comecaAspas = t.startsWith("'") &&  t.endsWith("'");
            boolean comecaAspasDuplas = t.startsWith("\"") && t.endsWith("\"");
            
            if (comecaAspas || comecaAspasDuplas) {
                if (t.length() >= 2) {
                    t = t.substring(1, t.length() - 1);
                }
            }
            arr[k] = t.trim();
        }
          return arr;
    }

    //remove colchetes externos (usado para arrays) e depois separa por virgula
    static String[] removeColchete(String s) {
        if (s == null) s = "";
        else s = s.trim();
        
        if (!s.isEmpty()) {
            if (s.startsWith("[") && s.endsWith("]")) {
                s = s.substring(1, s.length() - 1);
            }
        }
        
        return separaVirgula(s);
    }
    
    // Implementação da Árvore bicolor 
    static class ArvoreAlvinegra {
        private Node raiz;
        //obs: chamei de alvinegra mas vamos usar true ou false

        //fiz uma classe interna pois tava dando erro
        private class Node {
            Game jogo;
            boolean cor; //bit de cor adicionado
            Node esq, dir;

            public Node(Game jogo) {
                this.jogo = jogo;
                this.cor = true; 
                this.esq = this.dir = null;
            }
        }

        public ArvoreAlvinegra() {
            this.raiz = null;
        }

        
        private boolean isColored(Node no) {
            if (no == null) return false;
            return no.cor == true;
        }
        
        // Rotação para a esquerda (corrige inclinação para a direita)
        private Node rotacionarEsquerda(Node no) {
            Node x = no.dir;
            no.dir = x.esq;
            x.esq = no;
            // Ajuste de cores
            x.cor = no.cor;
            no.cor = true; // no.cor = RED
            return x;
        }
        
        // Rotação para a direita (corrige dois coloridos consecutivos à esquerda)
        private Node rotacionarDireita(Node no) {
            Node x = no.esq;
            no.esq = x.dir;
            x.dir = no;
            // Ajuste de cores
            x.cor = no.cor;
            no.cor = true;
            return x;
        }
        
        // Inversão de cores
        private void flipCores(Node no) {
            // A cor do nó pai se inverte, e os filhos ficam pretos
            no.cor = true; 
            no.esq.cor = false; 
            no.dir.cor = false; 
        }

        public void inserir(Game novoJogo) {
            raiz = inserirRec(raiz, novoJogo);
            raiz.cor = false; // A raiz deve ser sempre preta (false)
        }

        private Node inserirRec(Node h, Game novoJogo) {
            if (h == null) {
                return new Node(novoJogo);
            }

            //comparação de strings para ordenação
            int comparacao = novoJogo.getName().compareTo(h.jogo.getName());

            if (comparacao < 0) {
                h.esq = inserirRec(h.esq, novoJogo);
            } else if (comparacao > 0) {
                h.dir = inserirRec(h.dir, novoJogo);
            } else {
                return h; // Jogo já existe
            }

            //======= ROTAÇÕES
            //rotação para a esquerda
            if (isColored(h.dir) && !isColored(h.esq)) {
                h = rotacionarEsquerda(h);
            }

            //rotação para a direita
            if (isColored(h.esq) && isColored(h.esq.esq)) {
                h = rotacionarDireita(h);
            }

               //inversão de cores
            if (isColored(h.esq) && isColored(h.dir)) {
                flipCores(h);
            }

            return h;
        }
        
        public String pesquisarComCaminho(String nomeJogo) {
            //usa uma lista para armazenar os ponteiros (raiz, esq, dir)
            List<String> ponteiros = new ArrayList<>();
            //inicia o caminhar
            boolean encontrado = pesquisarComCaminhoRec(raiz, nomeJogo, ponteiros, true);
            
            //converte a lista de ponteiros para string separada por espaço
            String caminhoString = String.join(" ", ponteiros);

            //resultado final com operador ternário
            String resultado = encontrado ? " SIM" : " NAO";
            return nomeJogo + ": =>" + caminhoString + resultado;
        }

        //caminhar (Busca recursiva)
        private boolean pesquisarComCaminhoRec(Node no, String nomeJogo, List<String> ponteiros, boolean isRoot) {
            if (no == null) {
                return false; //percorreu tudo e nao tinha
            }

            //obs: essa função garante que só chama a raiz uma vez, pra nao imprimir varias
            if (isRoot) {
                ponteiros.add("raiz"); //imprime raiz
            }
            
            int comparacao = nomeJogo.compareTo(no.jogo.getName());

            if (comparacao == 0) {
                return true; //jogo encontrado
            } else if (comparacao < 0) {
                ponteiros.add("esq");
                return pesquisarComCaminhoRec(no.esq, nomeJogo, ponteiros, false);
            } else {
                ponteiros.add("dir");
                return pesquisarComCaminhoRec(no.dir, nomeJogo, ponteiros, false);
            }
        }
    }
    
    // Classe Game
    static class Game implements Cloneable{
        private int id;
        private String name;
        private String releaseDate;
        private int estimatedOwners;
        private float price;
        private String[] supportedLanguages;
        private int metacriticScore;
        private float userScore;
        private int achievements;
        private String[] publishers;  
        private String[] developers;
        private String[] categories;
        private String[] genres;
        private String[] tags;

        public Game() {
            this.id = 0;
            this.name = "";
            this.releaseDate = "";
            this.estimatedOwners = 0;
            this.price = 0.0f;
            this.supportedLanguages = new String[0];
            this.metacriticScore = -1;
            this.userScore = -1.0f;
            this.achievements = 0;
            this.publishers = new String[0];
            this.developers = new String[0];
            this.categories = new String[0];
            this.genres = new String[0];
            this.tags = new String[0];
        }

        @Override
        public Game clone() {
            try {
                Game clone = (Game) super.clone();
                //copia profunda dos arrays
                clone.supportedLanguages = this.supportedLanguages.clone();
                clone.publishers = this.publishers.clone();
                clone.developers = this.developers.clone();
                clone.categories = this.categories.clone();
                clone.genres = this.genres.clone();
                clone.tags = this.tags.clone();
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        //metodos get
        public int getId() {return this.id;}
        public String getName() {return this.name;}
        public String getReleaseDate() {return this.releaseDate;}
        public int getEstimatedOwners() {return this.estimatedOwners;}
        public float getPrice() {return this.price;}
        public String[] getSupportedLanguages() {return this.supportedLanguages;}
        public int getMetacriticScore() {return this.metacriticScore;}
        public float getUserScore() {return this.userScore;}
        public int getAchievements() {return this.achievements;}
        public String[] getPublishers() {return this.publishers;}
        public String[] getDevelopers() {return this.developers;}
        public String[] getCategories() {return this.categories;}
        public String[] getGenres() {return this.genres;}
        public String[] getTags() {return this.tags;}

        //metodos set
        public void setId(int id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
        public void setEstimatedOwners(int estimatedOwners) { this.estimatedOwners = estimatedOwners; }
        public void setPrice(float price) { this.price = price; }
        public void setSupportedLanguages(String[] supportedLanguages) { this.supportedLanguages = supportedLanguages; }
        public void setMetacriticScore(int metacriticScore) { this.metacriticScore = metacriticScore; }
        public void setUserScore(float userScore) { this.userScore = userScore; }
        public void setAchievements(int achievements) { this.achievements = achievements; }
        public void setPublishers(String[] publishers) { this.publishers = publishers; }
        public void setDevelopers(String[] developers) { this.developers = developers; }
        public void setCategories(String[] categories) { this.categories = categories; }
        public void setGenres(String[] genres) { this.genres = genres; }
        public void setTags(String[] tags) { this.tags = tags; }
    }
}