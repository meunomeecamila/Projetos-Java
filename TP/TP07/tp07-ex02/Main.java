/* PASSO A PASSO DO TP
01- Criar a Árvore 1 inserindo na ordem pedida
02- Inserir os nomes na Árvore 2
03- Buscar nos nós da primeira e da segunda árvore
04- Printar o nome, caminho e se foi encontrado
obs: gerei um log com os resultados
*/

import java.io.*;
import java.util.*;

public class Main {
    // Matrícula fornecida para o log
    //private static final String MATRICULA = "885761_arvoreDeArvores";
    private static int comparacoes = 0;

    public static void main(String[] args) {
        //contagem de tempo pro log
        long startTime = System.currentTimeMillis();

        Game[] vetor = new Game[2000];
        for (int i = 0; i < 2000; i++) {
            vetor[i] = new Game();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("/tmp/games.csv"))) {
            br.readLine(); // Ignorar o cabeçalho do csv

            //preenche o vetor com os objetos Game
            for (int i = 0; i < 2000; i++) {
                String linha = br.readLine();
                if (linha == null) break;
                lerlinha(linha, i, vetor);
            }
        } catch (IOException e) {
            System.err.println("erro");
            e.printStackTrace();
            return; //encerra se não conseguir ler o arquivo
        }

        //arvore de arvores
        ArvoreDeArvores arvoreDeArvores = new ArvoreDeArvores();
        Scanner sc = new Scanner(System.in);

        //insere os jogos na estrutura
        while (sc.hasNextLine()) {
            String entrada = sc.nextLine().trim();
            if (entrada.equals("FIM")) break;

            int id = Integer.parseInt(entrada);
            Game g = buscarPorId(id, vetor);
            if (g != null) {
                arvoreDeArvores.inserir(g); // Insere na estrutura Árvore de Árvores
            }
        }

        //agora, vamos começar a ler os nomes e printar os caminhos
        //printei tudo no arquivo log tbm
        //try (PrintWriter logWriter = new PrintWriter(MATRICULA + ".txt")) {
            while (sc.hasNextLine()) {
                String nomeBusca = sc.nextLine().trim();

                if (nomeBusca.equals("FIM")) continue;

                String resultado = arvoreDeArvores.pesquisarComCaminho(nomeBusca);

                //printar tanto no log quanto na saída
                System.out.println(resultado);
                //logWriter.println(resultado);
            }

            //calcula o tempo de execução
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            //logWriter.println("Matrícula: " + MATRICULA.split("_")[0]);
            //logWriter.println("Número de comparações: " + comparacoes);
            //logWriter.println("Tempo de execução (ms): " + executionTime);

        /*} catch (IOException e) {
            System.err.println("erro");
        }*/

        sc.close();
    }

    //procura e retorna o objeto game no vetor dado um id
    public static Game buscarPorId(int id, Game[] vetor) {
        for (Game g : vetor) {
            if (g != null && g.getId() == id) {
                return g.clone(); //usa o clone para não alterar o objeto original no vetor
            }
        }
        return null;
    }

    //implementa a funcao de salvar o campo no objeto game
    static void salvarCampo(Game g, int campo, String valor) {
        if (campo == 0) { // id
            try {
                if (valor.isEmpty()) g.setId(0);
                else g.setId(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setId(0); }
        } else if (campo == 1) { // nome
            g.setName(aspasExternas(valor));
        } else if (campo == 2) { // release date
            String raw = aspasExternas(valor).trim().replace("\"", "");
            String dataFormatada = "";

            if (raw.matches("\\d{4}")) {
                dataFormatada = "01/01/" + raw;
            } else if (raw.matches("[a-zA-Z]+,? \\d{4}")) {
                String[] partes = raw.replace(",", "").split(" ");
                String mes = partes[0];
                String ano = partes[1];
                dataFormatada = "01/" + mesParaNumero(mes) + "/" + ano;
            } else if (raw.matches("[a-zA-Z]+ \\d{1,2},? \\d{4}")) {
                String[] partes = raw.replace(",", "").split(" ");
                String mes = partes[0];
                String dia = partes[1];
                String ano = partes[2];
                if (dia.length() == 1) dia = "0" + dia;
                dataFormatada = dia + "/" + mesParaNumero(mes) + "/" + ano;
            } else {
                dataFormatada = "01/01/0001";
            }
            g.setReleaseDate(dataFormatada);
        } else if (campo == 3) { // estimated owners
            String digitos = valor.replaceAll("\\D+", "");
            try {
                if (digitos.isEmpty()) g.setEstimatedOwners(0);
                else g.setEstimatedOwners(Integer.parseInt(digitos));
            } catch (NumberFormatException e) { g.setEstimatedOwners(0); }
        } else if (campo == 4) { // price
            if (valor.equalsIgnoreCase("free to play")) g.setPrice(0.0f);
            else if (!valor.isEmpty()) {
                try {
                    g.setPrice(Float.parseFloat(valor.replace(',', '.')));
                } catch (NumberFormatException e) {
                    g.setPrice(0.0f);
                }
            }
        } else if (campo == 5) { // supported languages
            g.setSupportedLanguages(removeColchete(aspasExternas(valor)));
        } else if (campo == 6) { // metacritic score
            try {
                if (valor.isEmpty()) g.setMetacriticScore(-1);
                else g.setMetacriticScore(Integer.parseInt(valor));
            } catch (NumberFormatException e) { g.setMetacriticScore(-1); }
        } else if (campo == 7) { // user score
            if (valor.isEmpty() || valor.equalsIgnoreCase("tbd")) g.setUserScore(-1.0f);
            else {
                try {
                    g.setUserScore(Float.parseFloat(valor.replace(',', '.')));
                } catch (NumberFormatException e) {
                    g.setUserScore(-1.0f);
                }
            }
        } else if (campo == 8) { // achievement
            try {
                if (valor.isEmpty()) g.setAchievements(0);
                long achievementsLong = Long.parseLong(valor);
                g.setAchievements(achievementsLong > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) achievementsLong);
            } catch (NumberFormatException e) { g.setAchievements(0); }
        } else if (campo == 9) { // publishers
            g.setPublishers(separaVirgula(aspasExternas(valor)));
        } else if (campo == 10) { // developers
            g.setDevelopers(separaVirgula(aspasExternas(valor)));
        } else if (campo == 11) { // categories
            g.setCategories(removeColchete(aspasExternas(valor)));
        } else if (campo == 12) { // generos
            g.setGenres(removeColchete(aspasExternas(valor)));
        } else if (campo == 13) { // tags
            g.setTags(removeColchete(aspasExternas(valor)));
        }
    }

    //funcao para ler uma linha do csv e popular um objeto game
    public static void lerlinha(String linha, int i, Game[] vetor) {
        int campo = 0;
        StringBuilder atual = new StringBuilder();
        boolean aspas = false;
        int colchetes = 0;

        for (int j = 0; j < linha.length(); j++) {
            char c = linha.charAt(j);

            if (c == '\"' && colchetes == 0) {
                aspas = !aspas;
            } else if (c == '[' && !aspas) { //só conta colchete se não estiver dentro de aspas
                colchetes++;
            } else if (c == ']' && !aspas) { //só conta colchete se não estiver dentro de aspas
                colchetes--;
            }

            //separador é a vírgula, fora de aspas e fora de colchetes.
            if (c == ',' && aspas == false && colchetes == 0) {
                salvarCampo(vetor[i], campo, atual.toString().trim());
                campo++;
                atual.setLength(0);
                continue;
            }

            atual.append(c);
        }

        //ultimo campo
        salvarCampo(vetor[i], campo, atual.toString().trim());
    }

    //remove aspas externas se existirem
    static String aspasExternas(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.length() >= 2) {
            char a = s.charAt(0), b = s.charAt(s.length() - 1);
            if ((a == '"' && b == '"') || (a == '\'' && b == '\'')) {
                return s.substring(1, s.length() - 1).trim();
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

        for (int k = 0; k < arr.length; k++) {
            String t = arr[k].trim();

            boolean comecaAspas = t.startsWith("'") && t.endsWith("'");
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

    //definição da Classe Game
    static class Game implements Cloneable {
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
                // Copia profunda dos arrays
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

        // Metodos get
        public int getId() { return this.id; }
        public String getName() { return this.name; }
        public String getReleaseDate() { return this.releaseDate; }
        public int getEstimatedOwners() { return this.estimatedOwners; }
        public float getPrice() { return this.price; }
        public String[] getSupportedLanguages() { return this.supportedLanguages; }
        public int getMetacriticScore() { return this.metacriticScore; }
        public float getUserScore() { return this.userScore; }
        public int getAchievements() { return this.achievements; }
        public String[] getPublishers() { return this.publishers; }
        public String[] getDevelopers() { return this.developers; }
        public String[] getCategories() { return this.categories; }
        public String[] getGenres() { return this.genres; }
        public String[] getTags() { return this.tags; }

        // Metodos set
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


    //estrutura da Segunda Árvore (nós tipo 2 - chave: String Name)
    static class ArvoreSecundaria {
        private NoSecundario raiz;

        private class NoSecundario {
            Game jogo;
            NoSecundario esq, dir;

            public NoSecundario(Game jogo) {
                this.jogo = jogo;
                this.esq = this.dir = null;
            }
        }

        public ArvoreSecundaria() {
            this.raiz = null;
        }

        public void inserir(Game novoJogo) {
            raiz = inserirRec(raiz, novoJogo);
        }

        private NoSecundario inserirRec(NoSecundario no, Game novoJogo) {
            if (no == null) {
                return new NoSecundario(novoJogo);
            }

            //comparação de strings para ordenação (chave: Name)
            int comparacao = novoJogo.getName().compareTo(no.jogo.getName());

            if (comparacao < 0) {
                no.esq = inserirRec(no.esq, novoJogo);
            } else if (comparacao > 0) {
                no.dir = inserirRec(no.dir, novoJogo);
            }
            return no;
        }

        public boolean pesquisarECaminho(String nomeJogo, List<String> caminhoLista) {
            return pesquisarECaminhoRec(raiz, nomeJogo, caminhoLista);
        }

        private boolean pesquisarECaminhoRec(NoSecundario no, String nomeJogo, List<String> caminhoLista) {
            if (no == null) {
                return false; //não encontrou
            }
            
            comparacoes++; //conta a comparação de nome
            int comparacao = nomeJogo.compareTo(no.jogo.getName());

            //basicamente um caminhar
            if (comparacao == 0) {
                return true; // Jogo encontrado
            } else if (comparacao < 0) {
                caminhoLista.add("esq");
                return pesquisarECaminhoRec(no.esq, nomeJogo, caminhoLista);
            } else {
                caminhoLista.add("dir");
                return pesquisarECaminhoRec(no.dir, nomeJogo, caminhoLista);
            }
        }
    }


    //estrutura da Primeira Árvore (nós tipo 1 - chave: int mod 15) e Estratégias
    static class ArvoreDeArvores {
        private NoPrincipal raiz;

        //nó da Primeira Árvore (Chave: int, Ponteiro para a Sub-Árvore)
        private class NoPrincipal {
            int chave; // estimatedOwners mod 15
            NoPrincipal esq, dir;
            ArvoreSecundaria subArvore;

            public NoPrincipal(int chave) {
                this.chave = chave;
                this.esq = this.dir = null;
                this.subArvore = new ArvoreSecundaria();
            }
        }

        public ArvoreDeArvores() {
            //ordem fixa q pediram
            int[] chaves = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
            for (int chave : chaves) {
                this.raiz = inserirPrincipalRec(this.raiz, chave);
            }
        }

        //insere as chaves da primeira árvore (apenas a estrutura)
        private NoPrincipal inserirPrincipalRec(NoPrincipal no, int chave) {
            if (no == null) {
                return new NoPrincipal(chave);
            }
            if (chave < no.chave) {
                no.esq = inserirPrincipalRec(no.esq, chave);
            } else if (chave > no.chave) {
                no.dir = inserirPrincipalRec(no.dir, chave);
            }
            return no;
        }

        //encontra o nó principal com a chave (estimatedOwners mod 15)
        private NoPrincipal getNoPrincipal(int chave) {
            NoPrincipal atual = this.raiz;
            while (atual != null) {
                if (chave == atual.chave) {
                    return atual;
                } else if (chave < atual.chave) {
                    atual = atual.esq;
                } else {
                    atual = atual.dir;
                }
            }
            return null;
        }

        public void inserir(Game g) {
            int chave = g.getEstimatedOwners() % 15;
            NoPrincipal noPrincipal = getNoPrincipal(chave);

            if (noPrincipal != null) {
                noPrincipal.subArvore.inserir(g);
            } else {
                System.err.println("Chave " + chave + " não encontrada (erro de inicialização).");
            }
        }

        public String pesquisarComCaminho(String nomeJogo) {
            //stringBuilder para acumular o caminho de travessia da árvore principal
            StringBuilder caminhoPrincipalAcumulado = new StringBuilder();
            //lista para guardar o caminho da última sub-árvore visitada
            List<String> caminhoUltimaSecundaria = new ArrayList<>();

            //o método de travessia retorna TRUE se encontrado
            boolean encontrado = mostrarPreOrdemEConsultar(
                this.raiz, 
                nomeJogo, 
                caminhoPrincipalAcumulado, 
                caminhoUltimaSecundaria,
                true //começa na raiz principal
            );

            //monta o resultado
            String prefixo = "=> " + nomeJogo + " => ";
            
            if (encontrado) {
                //se encontrado, o caminho Principal Acumulado JÁ contém o caminho da sub-árvore
                return prefixo + caminhoPrincipalAcumulado.toString().trim() + " SIM";
            } else {
                //se NÃO encontrado, anexa o caminho da última sub-árvore visitada
                String caminhoCompleto = caminhoPrincipalAcumulado.toString().trim();
                
                //junta o caminho da última sub-árvore (em minúsculas)
                if (!caminhoUltimaSecundaria.isEmpty()) {
                    caminhoCompleto += " " + String.join(" ", caminhoUltimaSecundaria);
                }

                return prefixo + caminhoCompleto + " NAO";
            }
        }

        private boolean mostrarPreOrdemEConsultar(
            NoPrincipal no, 
            String nomeJogo, 
            StringBuilder caminhoPrincipalAcumulado,
            List<String> caminhoUltimaSecundaria,
            boolean isRoot) 
        {
            if (no == null) {
                return false;
            }
            
            //adiciona o indicador de visita ao nó (RAIZ, ou os ponteiros subsequentes)
            if (isRoot) {
                caminhoPrincipalAcumulado.append(" raiz");
            }

            //limpa o caminho da secundária para a pesquisa atual
            List<String> caminhoSecundariaAtual = new ArrayList<>();
            
            //pesquisa na sub-árvore
            boolean encontrado = no.subArvore.pesquisarECaminho(nomeJogo, caminhoSecundariaAtual);
            
            //se encontrado, anexa o caminho da sub-árvore e retorna SIM
            if (encontrado) {
                caminhoPrincipalAcumulado.append(" ").append(String.join(" ", caminhoSecundariaAtual));
                return true; 
            }
            
            //se NÃO encontrado, este é o caminho de falha (esq/dir) da última sub-árvore visitada
            caminhoUltimaSecundaria.clear();
            caminhoUltimaSecundaria.addAll(caminhoSecundariaAtual);


            //busca recursivamente na sub-árvore esquerda
            caminhoPrincipalAcumulado.append("  ESQ");
            if (mostrarPreOrdemEConsultar(no.esq, nomeJogo, caminhoPrincipalAcumulado, caminhoUltimaSecundaria, false)) {
                return true;
            }
            
            //busca recursivamente na sub-árvore direita
            caminhoPrincipalAcumulado.append("  DIR");
            if (mostrarPreOrdemEConsultar(no.dir, nomeJogo, caminhoPrincipalAcumulado, caminhoUltimaSecundaria, false)) {
                return true;
            }

            return false;
        }
    }
}