/*Passo a passo deste exercicio do TP
01- Ler do arquivo games.csv - ok
02- Guardar informações de cada jogo no vetor 
03- Ler os ids e guardar seus nomes em um vetor adicional até FIM
04- Ordenar esse vetor adicional por Name
05- Agora sim, ler o nome que o professor quer 
06- Conferir se tem no vetor adicional por busca binária
07- Imprimir SIM ou NAO pra cada 
08- Criar um log com as informações
obs: guardar o numero de comparações, e tempo de execução
*/

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
        String caminhodocsv = "games.csv";

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

        //guardar o tempo para o arquivo log
        long inicio = System.currentTimeMillis();

        //03 - ler os ids e guardar nomes
        String[] vetorNomes = lerIdsGuardarNomes(vetor, n);

        //04 - ordenar o vetor de nomes
        ordenarNomes(vetorNomes, vetorNomes.length);

        //05 - agora ler os nomes que o professor quer
        Scanner sc2 = new Scanner(System.in);
        String entrada2;
        int comparacoes = 0;

        while (sc2.hasNextLine()) {
            entrada2 = sc2.nextLine().trim();
            if (entrada2.equals("FIM")) break;

            //06 - busca binaria por nome
            boolean achou = buscaBinariaNome(vetorNomes, vetorNomes.length, entrada2);
            comparacoes++;

            if (achou) System.out.println("SIM");
            else System.out.println("NAO");
        }

        sc2.close();

        //07 - calcular tempo e criar o log
        long fim = System.currentTimeMillis();
        long tempoExecucao = fim - inicio;

        try {
            FileWriter fw = new FileWriter("885761_binaria.txt"); // coloque sua matrícula
            fw.write("885761" + "\t" + tempoExecucao + "\t" + comparacoes + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Ler IDs e guardar Nomes no vetor adicional
    //obs: talvez mudar o nome da função depois
    public static String[] lerIdsGuardarNomes(Game[] vetor, int n) {
    ArrayList<String> nomes = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    while (sc.hasNextLine()) {
        String entrada = sc.nextLine().trim();

        if (entrada.equals("FIM")) break; // o Verde envia exatamente "FIM"

        // converte o id e busca o nome correspondente
        int id = Integer.parseInt(entrada);
        String nomeEncontrado = buscarNomePorId(vetor, n, id);

        if (!nomeEncontrado.equals("")) {
            nomes.add(nomeEncontrado);
        }
    }

    sc.close();

    // converte ArrayList em vetor
    String[] vetorNomes = new String[nomes.size()];
    for (int i = 0; i < nomes.size(); i++) {
        vetorNomes[i] = nomes.get(i);
    }

    return vetorNomes;
}


    // FUNÇÃO 2 - Busca linear por ID no vetor de games
    public static String buscarNomePorId(Game[] vetor, int n, int id) {
        for (int i = 0; i < n; i++) {
            if (vetor[i] != null && vetor[i].getId() == id) {
                return vetor[i].getName();
            }
        }
        return ""; // se não encontrou, retorna vazio
    }

    // FUNÇÃO 3 - Ordenar vetor de nomes (Bubble Sort)
    public static void ordenarNomes(String[] vetor, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String nome1 = vetor[j];
                String nome2 = vetor[j + 1];

                if (vemDepois(nome1, nome2)) {
                    String tmp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = tmp;
                }
            }
        }
    }

    // FUNÇÃO 4 - Busca binária por nome no vetor de nomes
    public static boolean buscaBinariaNome(String[] vetor, int n, String nomeBusca) {
        int esq = 0;
        int dir = n - 1;

        while (esq <= dir) {
            int meio = (esq + dir) / 2;
            String nomeAtual = vetor[meio];

            int cmp = compararStrings(nomeBusca, nomeAtual);

            if (cmp == 0) return true; // achou
            else if (cmp > 0) esq = meio + 1; // nomeBusca > nomeAtual
            else dir = meio - 1; // nomeBusca < nomeAtual
        }
        return false;
    }

    // FUNÇÃO 5 - Comparar duas strings manualmente (sem compareTo)
    public static int compararStrings(String a, String b) {
        int len1 = a.length();
        int len2 = b.length();
        int minLen = (len1 < len2) ? len1 : len2;

        for (int i = 0; i < minLen; i++) {
            char c1 = Character.toLowerCase(a.charAt(i));
            char c2 = Character.toLowerCase(b.charAt(i));

            if (c1 != c2) {
                return c1 - c2;
            }
        }

        return len1 - len2;
    }

    //codifo de ordenar por nome como estava antes
    public static void ordenarPorName(Game[] vetor, int n){
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String nome1 = vetor[j].getName();
                String nome2 = vetor[j + 1].getName();

                // evita null (tratando como vazio)
                if (nome1 == null) nome1 = "";
                if (nome2 == null) nome2 = "";

                // se nome1 vem depois de nome2, troca
                if (vemDepois(nome1, nome2)) {
                    Game tmp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = tmp;
                }
            }
        }
    }

    public static boolean vemDepois(String nome1, String nome2) {
        int len1 = nome1.length();
        int len2 = nome2.length();
        int minLen = (len1 < len2) ? len1 : len2;

        // compara caractere por caractere
        for (int i = 0; i < minLen; i++) {
            char c1 = Character.toLowerCase(nome1.charAt(i));
            char c2 = Character.toLowerCase(nome2.charAt(i));

            if (c1 > c2) return true;   // nome1 vem depois (troca)
            if (c1 < c2) return false;  // nome1 vem antes (não troca)
        }

        // se todos os caracteres até o menor tamanho forem iguais,
        // o nome mais comprido vem depois (ex: "bat" < "batalha")
        return len1 > len2;
    }

    //função do último tp
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
            else if(c == '['){
                colchetes++;
            }
            else if(c == ']'){
                colchetes--;
            }

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
            try {
                if(valor.isEmpty()) g.setId(0);
                else g.setId(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setId(0); }
        }
        else if(campo == 1){ g.setName(stripOuterQuotes(valor)); }
        else if (campo == 2) {
            String raw = stripOuterQuotes(valor).trim().replace("\"", "");
            String dataFormatada = "";
            if (raw.matches("\\d{4}")) dataFormatada = "01/01/" + raw;
            else dataFormatada = "01/01/0001";
            g.setReleaseDate(dataFormatada);
        }
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
