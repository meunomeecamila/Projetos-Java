/* PASSO A PASSO TP
01- Ler os ids até FIM e salvar os objetos em um vetor
02- Ler a quantidade de operações a serem feitas
03- Fazer um while que lê todas as operações e as faz 
04- Imprimir todos os removidos no estilo (R) <Name>
05- Imprimir todos os não removidos após as operações
*/

//obs: dessa vez, como fila

import java.io.*;
import java.util.*;

public class Main {
    static Game[] vetor;
    
    public static void main(String []args){
        int n = 2000;
        vetor = new Game[n];

        //criar os objetos Game
        for(int i=0; i<n; i++){
            //criar um novo objeto Game
            vetor[i] = new Game();  
        }

        //ler o arquivo csv
        String caminhodocsv = "/tmp/games.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(caminhodocsv))){
            String header = br.readLine(); //cabeçalho do csv -> ignorar

            //pegar as próximas linhas e guardar no vetor da classe Game
            for(int i=0; i<n; i++){
                String linha = br.readLine(); //linha atual do csv
                if(linha == null) break;
                lerlinha(linha,i); //funcao que guarda tudo nos atributos
            }
        }
        catch(IOException e){e.printStackTrace();}

        Scanner sc = new Scanner(System.in);
        Fila fila = new Fila(); 

        // 01- Ler os ids até FIM e salvar os objetos na fila
        while (sc.hasNext()) {
            //ler o id
            String entrada;
            entrada = sc.next();
            if(entrada.equals("FIM")) break;

            //transformar a entrada no id
            int id = 0;
            id = Integer.parseInt(entrada);
            
            Game g = buscarPorId(id, vetor);
            if (g != null) {
                //em uma Fila, a inserção é no FIM (enqueue)
                fila.inserirFim(g); 
            }
        }

        // 02- Ler a quantidade de operações a serem feitas
        int ops = 0;
        if (sc.hasNextInt()) {
            ops = sc.nextInt();
        } else {
            //se nao houver mais nada ou nao for um inteiro, para por aqui
            ops = 0; 
        }

        // 03- Fazer um for que lê todas as operações e as faz 
        for(int i=0; i<ops; i++){
            String comando; 
            if (!sc.hasNext()) break;
            comando = sc.next();

            if(comando.equals("I")){
                // Inserir no FIM da Fila
                if (sc.hasNext()) {
                    int id = 0;
                    try {
                        id = Integer.parseInt(sc.next());
                        Game g = buscarPorId(id, vetor);
                        if (g != null) fila.inserirFim(g);
                    } catch (NumberFormatException e) {
                        // Ignora se o ID for invalido
                    }
                }
            }

            else if(comando.equals("R")){
                // Remover no INICIO da Fila (dequeue)
                Game removido = fila.removerInicio();
                // 04- Imprimir todos os removidos no estilo (R) <Name>
                if(removido != null)
                    System.out.println("(R) " + removido.getName());
            }
        }

        // 05- Imprimir todos os não removidos após as operações
        fila.mostrar();
        sc.close();
    }

    //procura e retorna o objeto Game no vetor dado um ID
    public static Game buscarPorId(int id, Game[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] != null && vetor[i].getId() == id) {
                return vetor[i].clone(); 
            }
        }
        return null;
    }

    //funcao para imprimir um objeto Game no formato padrao
    public static void imprimirGame(int index, Game g){
        System.out.print("[" + index + "] => " + g.getId() + " ## ");
        System.out.print(g.getName() + " ## ");
        System.out.print(g.getReleaseDate() + " ## ");
        System.out.print(g.getEstimatedOwners() + " ## ");
        System.out.print(String.format("%.2f", g.getPrice()).replace(',', '.') + " ## ");

        // arrays precisam ser formatados como [a, b, c]
        System.out.print(arrayParaString(g.getSupportedLanguages()) + " ## ");
        System.out.print(g.getMetacriticScore() + " ## ");
        System.out.print(String.format("%.1f", g.getUserScore()).replace(',', '.') + " ## ");
        System.out.print(g.getAchievements() + " ## ");
        // aqui removemos aspas internas de publishers e developers na hora de imprimir
        System.out.print(arrayParaStringSemAspas(g.getPublishers()) + " ## ");
        System.out.print(arrayParaStringSemAspas(g.getDevelopers()) + " ## ");
        System.out.print(arrayParaString(g.getCategories()) + " ## ");
        System.out.print(arrayParaString(g.getGenres()) + " ## ");
        System.out.print(arrayParaString(g.getTags()));
        System.out.println(" ##");
    }

    //converte um array de Strings para o formato pedido
    //obs: lembrando que o trim corta espaços antes e depois
    static String arrayParaString(String[] arr) {
        if (arr == null)return "[]";
        if(arr.length == 0) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        
        for (int i = 0; i < arr.length; i++) {
            String item = arr[i] == null ? "" : arr[i].trim().replace("\"", "");
            sb.append(item);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    //emrove aspas externas se existirem
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

    //limpando aspas internas de cada item antes de imprimir
    static String arrayParaStringSemAspas(String[] arr) {
        if (arr == null)return "[]";
        if(arr.length == 0) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            String t = arr[i];
            if (t == null) t = "";
            // rmove aspas internas/externas de cada item
            t = t.replace("\"", "").replace("'", "").trim();
            sb.append(t);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    //funcao para ler uma linha do CSV e popular um objeto Game
    public static void lerlinha(String linha, int i){
        int campo = 0;
        StringBuilder atual = new StringBuilder();  
        boolean aspas = false; //Indica se estamos DENTRO de um bloco delimitado por aspas CSV
        int colchetes = 0; // Indica se estamos DENTRO de um array

        for(int j=0; j<linha.length(); j++){
            char c = linha.charAt(j); 

            // Controlar aspas:
            // Se encontrar uma aspa dupla e não estivermos em um array
            if(c == '\"' && colchetes == 0){
                aspas = !aspas; // Inverte o estado de aspas
            }

            // Controlar colchetes:
            else if(c == '['){
                colchetes++;
            }
            else if(c == ']'){
                colchetes--;
            }

            // Controlar vírgula:
            //se chegou na virgula E não está entre aspas E não está dentro de colchetes, é final.
            if(c == ',' && aspas == false && colchetes == 0){
                salvarCampo(vetor[i], campo, atual.toString().trim());
                campo++;
                atual.setLength(0); // limpa o builder
                
                continue; //pula a vírgula
            }
            
            // Se chegou até aqui, anexa o caractere. 
            atual.append(c);
        }

        // Último campo
        salvarCampo(vetor[i], campo, atual.toString().trim());
    }

    // implementa a funcao de salvar o campo no objeto Game
    static void salvarCampo(Game g, int campo, String valor){

        // temos 14 elementos e portanto de 0 a 13 campos
        if(campo == 0){ // id
            try {
                if(valor.isEmpty()) g.setId(0);
                else g.setId(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setId(0); }
        }

        else if(campo == 1){ // nome
            // Remove aspas de escape do CSV, se houverem
            g.setName(aspasExternas(valor));
        }

        else if (campo == 2) { // release Date
            String raw = aspasExternas(valor).trim().replace("\"", ""); // remove aspas
            String dataFormatada = "";

            // O codigo de parsing de data e mantido o mais proximo do original,
            // garantindo o formato dd/MM/yyyy.
            if (raw.matches("\\d{4}")) {
                // so ano (ex: 2018)
                dataFormatada = "01/01/" + raw;
            }
            else if (raw.matches("[A-Za-z]+,? \\d{4}")) {
                // mes (abreviado ou completo) + ano (ex: Aug 2018 ou August, 2018)
                String[] partes = raw.replace(",", "").split(" ");
                String mes = partes[0];
                String ano = partes[1];
                dataFormatada = "01/" + mesParaNumero(mes) + "/" + ano;
            }
            else if (raw.matches("[A-Za-z]+ \\d{1,2},? \\d{4}")) {
                // mes + dia + ano (ex: Oct 13, 2018 ou October 13 2018)
                String[] partes = raw.replace(",", "").split(" ");
                String mes = partes[0];
                String dia = partes[1];
                String ano = partes[2];
                // Formato dd
                if (dia.length() == 1) dia = "0" + dia;
                dataFormatada = dia + "/" + mesParaNumero(mes) + "/" + ano;
            }
            else {
                dataFormatada = "01/01/0001";
            }

            g.setReleaseDate(dataFormatada);
        }

        else if(campo == 3){ // estimated owners
            // Remove caracteres nao numericos para parsear
            String digitos = valor.replaceAll("\\D+", "");
            try {
                if (digitos.isEmpty()) g.setEstimatedOwners(0);
                else g.setEstimatedOwners(Integer.parseInt(digitos));
            } catch (NumberFormatException e) { g.setEstimatedOwners(0); }
        }

        else if(campo == 4){ // price
            if (valor.equalsIgnoreCase("Free to Play")) g.setPrice(0.0f);
            else if (!valor.isEmpty()) {
                try {
                    // Substitui virgula por ponto para garantir o formato Float americano
                    g.setPrice(Float.parseFloat(valor.replace(',', '.'))); 
                } catch (NumberFormatException e) {
                    g.setPrice(0.0f); 
                }
            }
        }

        else if(campo == 5){ // supported languages
            g.setSupportedLanguages(removeColchete(aspasExternas(valor)));
        }

        else if(campo == 6){ // metacritic score
            try {
                if (valor.isEmpty()) g.setMetacriticScore(-1);
                else g.setMetacriticScore(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setMetacriticScore(-1); }
        }

        else if(campo == 7){ // user score
            if (valor.isEmpty() || valor.equalsIgnoreCase("tbd")) g.setUserScore(-1.0f);
            else {
                try {
                    // Substitui virgula por ponto para garantir o formato Float americano
                    g.setUserScore(Float.parseFloat(valor.replace(',', '.')));
                } catch (NumberFormatException e) {
                    g.setUserScore(-1.0f);
                }
            }
        }

        else if(campo == 8){ // achievement
            try {
                if (valor.isEmpty()) g.setAchievements(0);
                // Usa Long.parseLong para garantir que numeros grandes sejam lidos, depois converte para int
                long achievementsLong = Long.parseLong(valor);
                g.setAchievements(achievementsLong > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)achievementsLong);
            } catch (NumberFormatException e) { g.setAchievements(0); }
        }

        else if(campo == 9){ // publishers
            g.setPublishers(separaVirgula(aspasExternas(valor))); 
        }

        else if(campo == 10){ // developers
            g.setDevelopers(separaVirgula(aspasExternas(valor))); 
        }

        else if(campo == 11){ // categories
            g.setCategories(removeColchete(aspasExternas(valor)));
        }

        else if(campo == 12){ // generos
            g.setGenres(removeColchete(aspasExternas(valor)));
        }

        else if(campo == 13){ // tags
            g.setTags(removeColchete(aspasExternas(valor)));
        }
    }

    // transforma o mes para numero no formato "MM"
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


    // funcao simples de separar string por virgula e limpar aspas/espacos
    static String[] separaVirgula(String s) {
        // inicializa a string, tratando null e removendo espacos em branco
        if (s == null) {
            s = "";
        } else {
            s = s.trim();
        }
        
        // verifica se a string esta vazia apos o tratamento
        if (s.isEmpty()) {
            return new String[0];
        }
        
        // Divide a string por virgulas
        String[] arr = s.split(",");
        
        // Processa cada elemento do array
        for (int k=0; k < arr.length; k++) {
            String t = arr[k].trim(); // Remove espacos em branco do elemento
            
            // Verifica e remove aspas ou apostrofos de delimitacao
            boolean comecaAspas = t.startsWith("'") &&  t.endsWith("'");
            boolean comecaAspasDuplas = t.startsWith("\"") && t.endsWith("\"");
            
            if (comecaAspas || comecaAspasDuplas) {
                // Remove o primeiro e o ultimo caractere (as aspas/apostrofos)
                if (t.length() >= 2) {
                    t = t.substring(1, t.length() - 1);
                }
            }
            
            // Atribui o valor final (limpo) de volta ao array
            arr[k] = t.trim();
        }
        
          return arr;
    }

    // remove colchetes externos (usado para arrays) e depois separa por virgula
    static String[] removeColchete(String s) {
        // inicializa a string, tratando null e removendo espacos em branco
        if (s == null) s = "";
        else s = s.trim();
        
        // se a string nao estiver vazia, verifica e remove os colchetes externos
        if (!s.isEmpty()) {
            if (s.startsWith("[") && s.endsWith("]")) {
                // Remove o primeiro e o ultimo caractere (os colchetes)
                s = s.substring(1, s.length() - 1);
            }
        }
        
        // chama a funcao principal para dividir a lista pelos delimitadores de virgula
        return separaVirgula(s);
    }

    // CLASSES PRINCIPAIS ========================

    // Implementacao da Fila (Queue) com array e remocao com deslocamento (mais simples)
    static class Fila {
        private Game[] array;
        // n representa o numero de elementos na fila e o indice da proxima insercao
        private int n; 

        public Fila() {
            // Inicializa a fila com um tamanho padrao (igual ao vetor de Games)
            array = new Game[2000];
            n = 0;
        }

        // Insere (enqueue) no fim da fila
        public void inserirFim(Game g) {
            if (n >= array.length) {
                // Erro: Fila cheia
                System.err.println("Erro: Fila cheia ao inserir!");
                return;
            }
            array[n] = g;
            n++;
        }

        // Remove (dequeue) do inicio da fila
        public Game removerInicio() {
            if (n == 0) {
                // Fila vazia
                return null;
            }
            // Guarda o elemento a ser removido (o primeiro)
            Game removido = array[0];
            
            // Desloca todos os elementos uma posicao para a esquerda
            // (Esta e a abordagem mais simples, mas menos eficiente)
            for (int i = 0; i < n - 1; i++) {
                array[i] = array[i + 1];
            }
            
            n--;
            // Limpa a ultima posicao do array apos o deslocamento
            array[n] = null; 
            
            return removido;
        }

        // Exibe todos os jogos que ficaram na fila
        public void mostrar() {
            for (int i = 0; i < n; i++) {
                // Imprime usando o indice da Fila
                Main.imprimirGame(i, array[i]);
            }
        }
    }
    
    // Classe Game (mantida do codigo original)
    static class Game implements Cloneable {
        // atributos da classe
        private int id; // identificador unico do jogo
        private String name;
        private String releaseDate; // formato dd/MM/yyyy
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
        private String[] tags; //palavras-chave

        // criacao do Construtor vazio
        public Game() {
            this.id = 0;
            this.name = "";
            this.releaseDate = "";
            this.estimatedOwners = 0;
            this.price = 0.0f;
            this.supportedLanguages = new String[0];
            this.metacriticScore = -1; // pedido no ex
            this.userScore = -1.0f; // pedido no ex
            this.achievements = 0;
            this.publishers = new String[0];
            this.developers = new String[0];
            this.categories = new String[0];
            this.genres = new String[0];
            this.tags = new String[0];
        }

        // Metodo para clonagem (copia profunda)
        @Override
        public Game clone() {
            try {
                Game clone = (Game) super.clone();
                // Copia profunda dos arrays
                clone.supportedLanguages = this.supportedLanguages.clone();
                clone.publishers = this.publishers.clone();
                clone.developers = this.developers.clone();
                clone.categories = this.categories.clone();
                clone.genres = this.genres.clone();
                clone.tags = this.tags.clone();
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Clone not supported", e);
            }
        }

        // criacao dos metodos GET
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

        // criacao dos metodos SET
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