import java.io.*;
import java.util.*;

public class Main {
    static Game[] vetor;
    static long comparacoes = 0;
    static long movimentacoes = 0;

    public static void main(String[] args) {
        int n = 2000;
        vetor = new Game[n];

        for (int i = 0; i < n; i++) {
            vetor[i] = new Game();
        }

        String caminhodocsv = "/tmp/games.csv"; // ajuste o caminho se necessário
        int totalLidos = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhodocsv))) {
            String header = br.readLine();
            for (int i = 0; i < n; i++) {
                String linha = br.readLine();
                if (linha == null) break;
                lerlinha(linha, i);
                totalLidos++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler IDs até FIM
        Scanner sc = new Scanner(System.in);
        ArrayList<Game> selecionados = new ArrayList<>();

        while (sc.hasNext()) {
            String entrada = sc.next();
            if (entrada.equals("FIM")) break;

            try {
                int id = Integer.parseInt(entrada);
                for (int i = 0; i < totalLidos; i++) {
                    if (vetor[i] != null && vetor[i].getId() == id) {
                        selecionados.add(vetor[i]);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // ignora entradas inválidas
            }
        }
        sc.close();

        Game[] arraySelecionados = selecionados.toArray(new Game[0]);

        // Ordenar com mergesort e medir tempo
        long inicio = System.nanoTime();
        mergesort(arraySelecionados, 0, arraySelecionados.length - 1);
        long fim = System.nanoTime();
        double tempoSegundos = (fim - inicio) / 1e9;

        // Imprimir registros ordenados
        /*for (Game g : arraySelecionados) {
            imprimirGame(g);
        }*/

        // Mostrar os 5 mais caros e 5 mais baratos usando imprimirGame
        System.out.println("| 5 preços mais caros |");
        for (int i = arraySelecionados.length - 1; i >= Math.max(0, arraySelecionados.length - 5); i--) {
            imprimirGame(arraySelecionados[i]);
        }

        System.out.println("\n| 5 preços mais baratos |");
        for (int i = 0; i < Math.min(5, arraySelecionados.length); i++) {
            imprimirGame(arraySelecionados[i]);
        }

        // Criar arquivo de log
        try (PrintWriter log = new PrintWriter("885761_mergesort.txt")) {
            log.printf("885761\t%d\t%d\t%.6f\n", comparacoes, movimentacoes, tempoSegundos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // MERGESORT
    // ==============================
    public static void mergesort(Game[] arr, int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(arr, esq, meio);
            mergesort(arr, meio + 1, dir);
            merge(arr, esq, meio, dir);
        }
    }

    private static void merge(Game[] arr, int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        Game[] L = new Game[n1];
        Game[] R = new Game[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[esq + i];
            movimentacoes++;
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[meio + 1 + j];
            movimentacoes++;
        }

        int i = 0, j = 0, k = esq;

        while (i < n1 && j < n2) {
            comparacoes++;
            if (compararPorPreco(L[i], R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            movimentacoes++;
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            movimentacoes++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            movimentacoes++;
        }
    }

    private static int compararPorPreco(Game a, Game b) {
        comparacoes++;
        if (Float.compare(a.getPrice(), b.getPrice()) != 0) {
            return Float.compare(a.getPrice(), b.getPrice());
        } else {
            return Integer.compare(a.getId(), b.getId());
        }
    }

    // ==============================
    // Funções utilitárias existentes
    // ==============================

    public static void imprimirGame(Game g){
        System.out.print("=> " + g.getId() + " ## ");
        System.out.print(g.getName() + " ## ");
        System.out.print(g.getReleaseDate() + " ## ");
        System.out.print(g.getEstimatedOwners() + " ## ");
        System.out.print(g.getPrice() + " ## ");
        System.out.print(arrayToString(g.getSupportedLanguages()) + " ## ");
        System.out.print(g.getMetacriticScore() + " ## ");
        System.out.print(g.getUserScore() + " ## ");
        System.out.print(g.getAchievements() + " ## ");
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

    static String arrayToStringSemAspas(String[] arr) {
        if (arr == null || arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            String t = arr[i];
            if (t == null) t = "";
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
        boolean aspas = false; 
        int colchetes = 0; 

        for(int j=0; j<linha.length(); j++){
            char c = linha.charAt(j); 
            if(c == '\"' && colchetes == 0){
                aspas = !aspas;
            }
            else if(c == '['){ colchetes++; }
            else if(c == ']'){ colchetes--; }

            if(c == ',' && aspas == false && colchetes == 0){
                salvarCampo(vetor[i], campo, atual.toString().trim());
                campo++;
                atual.setLength(0);
                continue;
            }
            atual.append(c);
        }
        salvarCampo(vetor[i], campo, atual.toString().trim());
    }

    static void salvarCampo(Game g, int campo, String valor){
        if(campo == 0){
            try { g.setId(valor.isEmpty() ? 0 : Integer.parseInt(valor)); }
            catch (NumberFormatException e) { g.setId(0); }
        }
        else if(campo == 1){ g.setName(stripOuterQuotes(valor)); }
        else if(campo == 2){
            String raw = stripOuterQuotes(valor).trim().replace("\"", "");
            String dataFormatada = "";
            if (raw.matches("\\d{4}")) dataFormatada = "01/01/" + raw;
            else if (raw.matches("[A-Za-z]+,? \\d{4}")) {
                String[] partes = raw.replace(",", "").split(" ");
                dataFormatada = "01/" + mesParaNumero(partes[0]) + "/" + partes[1];
            } else dataFormatada = "01/01/0001";
            g.setReleaseDate(dataFormatada);
        }
        else if(campo == 3){
            String digitos = valor.replaceAll("\\D+", "");
            try { g.setEstimatedOwners(digitos.isEmpty() ? 0 : Integer.parseInt(digitos)); }
            catch (NumberFormatException e) { g.setEstimatedOwners(0); }
        }
        else if(campo == 4){
            if (valor.equalsIgnoreCase("Free to Play")) g.setPrice(0.0f);
            else try { g.setPrice(Float.parseFloat(valor.replace(',', '.'))); }
            catch (NumberFormatException e) { g.setPrice(0.0f); }
        }
        else if(campo == 5){ g.setSupportedLanguages(removeColchete(stripOuterQuotes(valor))); }
        else if(campo == 6){ try { g.setMetacriticScore(valor.isEmpty()? -1 : Integer.parseInt(valor)); }
            catch (NumberFormatException e){ g.setMetacriticScore(-1); } }
        else if(campo == 7){ 
            if (valor.isEmpty() || valor.equalsIgnoreCase("tbd")) g.setUserScore(-1.0f);
            else try { g.setUserScore(Float.parseFloat(valor.replace(',', '.'))); }
            catch (NumberFormatException e){ g.setUserScore(-1.0f); }
        }
        else if(campo == 8){ try { g.setAchievements(valor.isEmpty()? 0 : Integer.parseInt(valor)); }
            catch (NumberFormatException e){ g.setAchievements(0); } }
        else if(campo == 9){ g.setPublishers(separaVirgula(stripOuterQuotes(valor))); }
        else if(campo == 10){ g.setDevelopers(separaVirgula(stripOuterQuotes(valor))); }
        else if(campo == 11){ g.setCategories(removeColchete(stripOuterQuotes(valor))); }
        else if(campo == 12){ g.setGenres(removeColchete(stripOuterQuotes(valor))); }
        else if(campo == 13){ g.setTags(removeColchete(stripOuterQuotes(valor))); }
    }

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

    static String[] separaVirgula(String s) {
        if (s == null) s = "";
        s = s.trim();
        if (s.isEmpty()) return new String[0];
        String[] arr = s.split(",");
        for (int k = 0; k < arr.length; k++) {
            String t = arr[k].trim();
            if ((t.startsWith("'") && t.endsWith("'")) || (t.startsWith("\"") && t.endsWith("\""))) {
                t = t.substring(1, t.length() - 1);
            }
            arr[k] = t.trim();
        }
        return arr;
    }

    static String[] removeColchete(String s) {
        if (s == null) s = "";
        s = s.trim();
        if (!s.isEmpty() && s.startsWith("[") && s.endsWith("]")) {
            s = s.substring(1, s.length() - 1);
        }
        return separaVirgula(s);
    }
}

class Game {
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

    public int getId() { return id; }
    public String getName() { return name; }
    public String getReleaseDate() { return releaseDate; }
    public int getEstimatedOwners() { return estimatedOwners; }
    public float getPrice() { return price; }
    public String[] getSupportedLanguages() { return supportedLanguages; }
    public int getMetacriticScore() { return metacriticScore; }
    public float getUserScore() { return userScore; }
    public int getAchievements() { return achievements; }
    public String[] getPublishers() { return publishers; }
    public String[] getDevelopers() { return developers; }
    public String[] getCategories() { return categories; }
    public String[] getGenres() { return genres; }
    public String[] getTags() { return tags; }

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
