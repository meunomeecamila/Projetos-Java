import java.io.*;
import java.util.*;

public class Main {
    static Game[] vetor;
    
    public static void main(String []args){
        //criar o vetor
        int n = 2000;
        vetor = new Game[n];

        //criar os objetos
        for(int i=0; i<n; i++){
            //criar um novo objeto
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
                
                lerlinha(linha,i); //fazer a funcao que guarda tudo nos atributos
            }
        }

        catch(IOException e){e.printStackTrace();}

        Scanner sc = new Scanner(System.in);
        int num; 

        while (sc.hasNextInt()) {
            num = sc.nextInt(); // pegar o id

            for (int i = 0; i < vetor.length; i++) {
                if (vetor[i] != null && vetor[i].getId() == num) {
                imprimirGame(vetor[i]);
                break;
                }
            }
        }

        sc.close();

    }

    public static void imprimirGame(Game g){
        System.out.print("=> " + g.getId() + " ## ");
        System.out.print(g.getName() + " ## ");
        System.out.print(g.getReleaseDate() + " ## ");
        System.out.print(g.getEstimatedOwners() + " ## ");
        System.out.print(g.getPrice() + " ## ");

        // arrays precisam ser formatados como [a, b, c]
        System.out.print(arrayToString(g.getSupportedLanguages()) + " ## ");
        System.out.print(g.getMetacriticScore() + " ## ");
        System.out.print(g.getUserScore() + " ## ");
        System.out.print(g.getAchievements() + " ## ");
        // aqui removemos aspas internas de publishers e developers na hora de imprimir
        System.out.print(arrayToStringSemAspas(g.getPublishers()) + " ## ");
        System.out.print(arrayToStringSemAspas(g.getDevelopers()) + " ## ");
        System.out.print(arrayToString(g.getCategories()) + " ## ");
        System.out.print(arrayToString(g.getGenres()) + " ## ");
        System.out.print(arrayToString(g.getTags()));
        System.out.println(" ##");
    }

    static String arrayToString(String[] arr) {
        if (arr == null || arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // remove aspas externas se existirem
    static String stripOuterQuotes(String s) {
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

    // igual ao arrayToString, mas limpando aspas internas de cada item antes de imprimir
    static String arrayToStringSemAspas(String[] arr) {
        if (arr == null || arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            String t = arr[i];
            if (t == null) t = "";
            
            // Remove aspas internas para Publishers e Developers
            t = t.replace("\"", "").replace("'", "").trim();
            
            sb.append(t);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void lerlinha(String linha, int i){
        int campo = 0;
        StringBuilder atual = new StringBuilder();  
        boolean aspas = false; // Indica se estamos DENTRO de um bloco delimitado por aspas CSV
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
            // Se chegou na virgula E não está entre aspas E não está dentro de colchetes, é final.
            if(c == ',' && aspas == false && colchetes == 0){
                salvarCampo(vetor[i], campo, atual.toString().trim());
                campo++;
                atual.setLength(0); // limpa o builder
                
                continue; // Pula a vírgula
            }
            
            // Se chegou até aqui, anexa o caractere. 
            // Os caracteres de controle (vírgula separadora, aspas de escape)
            // não são anexados se usados para separação.
            atual.append(c);
        }

        // Último campo
        salvarCampo(vetor[i], campo, atual.toString().trim());
    }

    //implementar a funcao de salvar o campo
    static void salvarCampo(Game g, int campo, String valor){

        //temos 14 elementos e portanto de 0 a 13 campos
        if(campo == 0){ //id
            try {
                if(valor.isEmpty()) g.setId(0);
                else g.setId(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setId(0); }
        }

        else if(campo == 1){ //nome
            // Remove aspas de escape do CSV, se houverem
            g.setName(stripOuterQuotes(valor));
        }

        else if (campo == 2) { // release Date
            String raw = stripOuterQuotes(valor).trim().replace("\"", ""); // remove aspas
            String dataFormatada = "";

            if (raw.matches("\\d{4}")) {
                // só ano (ex: 2018)
                dataFormatada = "01/01/" + raw;
            }
        else if (raw.matches("[A-Za-z]+,? \\d{4}")) {
            // mês (abreviado ou completo) + ano (ex: Aug 2018 ou August, 2018)
            String[] partes = raw.replace(",", "").split(" ");
            String mes = partes[0];
            String ano = partes[1];
            dataFormatada = "01/" + mesParaNumero(mes) + "/" + ano;
        }
        else if (raw.matches("[A-Za-z]+ \\d{1,2},? \\d{4}")) {
            // mês + dia + ano (ex: Oct 13, 2018 ou October 13 2018)
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
            if (valor.equalsIgnoreCase("Free to Play")) g.setPrice(0.0f);
            else if (!valor.isEmpty()) {
                try {
                    // Substitui vírgula por ponto para garantir o formato Float americano
                    g.setPrice(Float.parseFloat(valor.replace(',', '.'))); 
                } catch (NumberFormatException e) {
                    g.setPrice(0.0f); 
                }
            }
        }

        else if(campo == 5){ //supported languages
            g.setSupportedLanguages(removeColchete(stripOuterQuotes(valor)));
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
                    // Substitui vírgula por ponto para garantir o formato Float americano
                    g.setUserScore(Float.parseFloat(valor.replace(',', '.')));
                } catch (NumberFormatException e) {
                    g.setUserScore(-1.0f);
                }
            }
        }

        else if(campo == 8){ //achievement
            try {
                if (valor.isEmpty()) g.setAchievements(0);
                else g.setAchievements(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setAchievements(0); }
        }

        else if(campo == 9){ //publishers
            g.setPublishers(separaVirgula(stripOuterQuotes(valor))); // Aplicando stripOuterQuotes aqui também.
        }

        else if(campo == 10){ //developers
            g.setDevelopers(separaVirgula(stripOuterQuotes(valor))); // Aplicando stripOuterQuotes aqui também.
        }

        else if(campo == 11){ //categories
            g.setCategories(removeColchete(stripOuterQuotes(valor)));
        }

        else if(campo == 12){ //generos
            g.setGenres(removeColchete(stripOuterQuotes(valor)));
        }

        else if(campo == 13){ //tags
            g.setTags(removeColchete(stripOuterQuotes(valor)));
        }

    }

    //transformar o mes para numero
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


    //funcao simples de separar virgula
    static String[] separaVirgula(String s) {
        //inicializa a string, tratando null e removendo espaços em branco
        if (s == null) {
            s = "";
        } else {
            s = s.trim();
        }
        
        //verifica se a string está vazia após o tratamento
        if (s.isEmpty()) {
            return new String[0];
        }
        
        // Divide a string por vírgulas
        String[] arr = s.split(",");
        
        // Processa cada elemento do array
        for (int k=0; k < arr.length; k++) {
            String t = arr[k].trim(); // Remove espaços em branco do elemento
            
            // Verifica e remove aspas ou apóstrofos de delimitação
            boolean startsWithQuote = t.startsWith("'") &&  t.endsWith("'");
            boolean startsWithDoubleQuote = t.startsWith("\"") && t.endsWith("\"");
            
            if (startsWithQuote || startsWithDoubleQuote) {
                // Remove o primeiro e o último caractere (as aspas/apóstrofos)
                if (t.length() >= 2) {
                    t = t.substring(1, t.length() - 1);
                }
            }
            
            // Atribui o valor final (limpo) de volta ao array
            arr[k] = t.trim();
        }
        
          return arr;
    }

    //remove colchete
    static String[] removeColchete(String s) {
        //inicializa a string, tratando null e removendo espaços em branco
        if (s == null) {
            s = "";
        } else {
            s = s.trim();
        }
        
        //se a string não estiver vazia, verifica e remove os colchetes externos
        if (!s.isEmpty()) {
            if (s.startsWith("[") && s.endsWith("]")) {
                // Remove o primeiro e o último caractere (os colchetes)
                s = s.substring(1, s.length() - 1);
            }
        }
        
        //chama a função principal para dividir a lista pelos delimitadores de vírgula
        return separaVirgula(s);
    }
}

class Game {
    // atributos da classe (agora privados)
    private int id; // identificador único do jogo
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

    //criação do Construtor vazio
    public Game() {
        this.id = 0;
        this.name = "";
        this.releaseDate = "";
        this.estimatedOwners = 0;
        this.price = 0.0f;
        this.supportedLanguages = new String[0];
        this.metacriticScore = -1; //pedido no ex
        this.userScore = -1.0f; //pedido no ex
        this.achievements = 0;
        this.publishers = new String[0];
        this.developers = new String[0];
        this.categories = new String[0];
        this.genres = new String[0];
        this.tags = new String[0];
    }

    //criação dos métodos GET -> agora públicos
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

    //criação dos métodos SET
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