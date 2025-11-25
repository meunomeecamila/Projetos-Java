#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include <math.h>
#include <time.h>

// Função auxiliar para duplicar strings
static char* duplicaString(const char* s) {
    if (!s) return NULL;
    size_t len = strlen(s);
    char* p = (char*)malloc(len + 1);
    if (p) memcpy(p, s, len + 1);
    return p;
}

// remove espaços das pontas (in-place)
static void apararEspacos(char* s) {
    if (!s) return;
    size_t len = strlen(s);
    size_t i = 0;
    while (i < len && isspace((unsigned char)s[i])) i++;
    if (i > 0) memmove(s, s + i, len - i + 1);
    len = strlen(s);
    while (len > 0 && isspace((unsigned char)s[len - 1])) {
        s[len - 1] = '\0';
        len--;
    }
}

// Remove aspas duplas/simples externas, se houverem (ex: "hello" -> hello)
static void removerAspasExternas(char* s) {
    if (!s) return;
    apararEspacos(s);
    int len = (int)strlen(s);
    if (len >= 2) {
        if ((s[0] == '"' && s[len - 1] == '"') || (s[0] == '\'' && s[len - 1] == '\'')) {
            s[len - 1] = '\0';
            memmove(s, s + 1, len - 1);
            apararEspacos(s);
        }
    }
}

// Remove aspas duplas/simples e colchetes internos de cada item (Universal para todas as listas na SAÍDA)
static void removerAspasEColchetesInternos(char* s) {
    if (!s) return;
    int len = (int)strlen(s);
    int j = 0;
    for (int i = 0; i < len; i++) {
        // Remove aspas simples, duplas, colchetes de CADA ITEM, se existirem
        if (s[i] != '"' && s[i] != '\'' && s[i] != '[' && s[i] != ']') {
            s[j++] = s[i];
        }
    }
    s[j] = '\0';
    apararEspacos(s);
}

// junta array de strings num buffer alocado. Limpa aspas/colchetes de cada item ANTES de concatenar.
static char* juntarComVirgula(char** arr, int size) {
    if (!arr || size <= 0) {
        char* z = duplicaString("[]");
        return z;
    }
    
    // Aloca um buffer inicial
    size_t total = 512; 
    char* out = (char*)malloc(total + 1);
    if (!out) return duplicaString("[]");
    
    strcpy(out, "[");
    size_t current_len = 1;

    for (int i = 0; i < size; i++) {
        char* item = duplicaString(arr[i]);
        removerAspasEColchetesInternos(item);
        
        // Verifica se o buffer é suficiente
        if (current_len + strlen(item) + 3 > total) {
             total = current_len + strlen(item) + 50; 
             out = (char*)realloc(out, total + 1);
        }

        strcat(out, item);
        current_len += strlen(item);

        if (i < size - 1) {
            strcat(out, ", ");
            current_len += 2;
        }

        free(item);
    }
    strcat(out, "]");
    return out;
}


typedef struct {
    // arrays precisam ser formatados como [a, b, c]
    char** supportedLanguages;
    int supportedLanguages_len;

    char** publishers;
    int publishers_len;

    char** developers;
    int developers_len;

    char** categories;
    int categories_len;

    char** genres;
    int genres_len;

    char** tags;
    int tags_len;

    int id; // identificador único do jogo
    char* name;
    char* releaseDate; // formato dd/MM/yyyy
    int estimatedOwners;
    float price;
    int metacriticScore;
    float userScore;
    int achievements;
} Game; //struct chamada game

static Game* jogos; // equivalente ao "static Game[] vetor;" em Java

//criação do Construtor vazio
static void iniciarJogo(Game* g) {
    g->id = 0; //acessa com -> ao inves de . por ser ponteiro
    g->name = duplicaString("");
    g->releaseDate = duplicaString("");
    g->estimatedOwners = 0;
    g->price = 0.0f;
    g->metacriticScore = -1; //pedido no ex
    g->userScore = -1.0f; //pedido no ex
    g->achievements = 0;

    g->supportedLanguages = NULL;
    g->supportedLanguages_len = 0;
    g->publishers = NULL;
    g->publishers_len = 0;
    g->developers = NULL;
    g->developers_len = 0;
    g->categories = NULL;
    g->categories_len = 0;
    g->genres = NULL;
    g->genres_len = 0;
    g->tags = NULL;
    g->tags_len = 0;
}

// destruir (free) um Game
static void destruirJogo(Game* g) {
    if (!g) return;
    free(g->name);
    free(g->releaseDate);

    for (int i = 0; i < g->supportedLanguages_len; i++) free(g->supportedLanguages[i]);
    free(g->supportedLanguages);
    for (int i = 0; i < g->publishers_len; i++) free(g->publishers[i]);
    free(g->publishers);
    for (int i = 0; i < g->developers_len; i++) free(g->developers[i]);
    free(g->developers);
    for (int i = 0; i < g->categories_len; i++) free(g->categories[i]);
    free(g->categories);
    for (int i = 0; i < g->genres_len; i++) free(g->genres[i]);
    free(g->genres);
    for (int i = 0; i < g->tags_len; i++) free(g->tags[i]);
    free(g->tags);
}

// GETs (mantidos como estavam)
static int getId(Game* g) {return g->id;}
static const char* getName(Game* g) {return g->name;}
static const char* getReleaseDate(Game* g) {return g->releaseDate;}
static int getEstimatedOwners(Game* g) {return g->estimatedOwners;}
static float getPrice(Game* g) {return g->price;}
static int getMetacriticScore(Game* g) {return g->metacriticScore;}
static float getUserScore(Game* g) {return g->userScore;}
static int getAchievements(Game* g) {return g->achievements;}
static char** getSupportedLanguages(Game* g) {return g->supportedLanguages;}
static int getSupportedLanguagesLen(Game* g) {return g->supportedLanguages_len;}
static char** getPublishers(Game* g) {return g->publishers;}
static int getPublishersLen(Game* g) {return g->publishers_len;}
static char** getDevelopers(Game* g) {return g->developers;}
static int getDevelopersLen(Game* g) {return g->developers_len;}
static char** getCategories(Game* g) {return g->categories;}
static int getCategoriesLen(Game* g) {return g->categories_len;}
static char** getGenres(Game* g) {return g->genres;}
static int getGenresLen(Game* g) {return g->genres_len;}
static char** getTags(Game* g) {return g->tags;}
static int getTagsLen(Game* g) {return g->tags_len;}

//SETs (mantidos como estavam)
static void setId(Game* g, int id) { g->id = id; }
static void setName(Game* g, const char* name) { free(g->name); g->name = duplicaString(name?name:""); }
static void setReleaseDate(Game* g, const char* rd) { free(g->releaseDate); g->releaseDate = duplicaString(rd?rd:""); }
static void setEstimatedOwners(Game* g, int v) { g->estimatedOwners = v; }
static void setPrice(Game* g, float p) { g->price = p; }
static void setMetacriticScore(Game* g, int v) { g->metacriticScore = v; }
static void setUserScore(Game* g, float v) { g->userScore = v; }
static void setAchievements(Game* g, int v) { g->achievements = v; }

static void setArrayStrings(char*** dest, int* len, char** src, int n) {
    if (*dest) {
        for (int i = 0; i < *len; i++) free((*dest)[i]);
        free(*dest);
    }
    *dest = NULL; *len = 0;
    if (n <= 0 || !src) return;
    *dest = (char**)malloc(sizeof(char*) * n);
    *len = n;
    for (int i = 0; i < n; i++) (*dest)[i] = duplicaString(src[i]?src[i]:"");
}

static void setSupportedLanguages(Game* g, char** arr, int n) { setArrayStrings(&g->supportedLanguages, &g->supportedLanguages_len, arr, n); }
static void setPublishers(Game* g, char** arr, int n)       { setArrayStrings(&g->publishers, &g->publishers_len, arr, n); }
static void setDevelopers(Game* g, char** arr, int n)       { setArrayStrings(&g->developers, &g->developers_len, arr, n); }
static void setCategories(Game* g, char** arr, int n)       { setArrayStrings(&g->categories, &g->categories_len, arr, n); }
static void setGenres(Game* g, char** arr, int n)           { setArrayStrings(&g->genres, &g->genres_len, arr, n); }
static void setTags(Game* g, char** arr, int n)             { setArrayStrings(&g->tags, &g->tags_len, arr, n); }

//transformar o mes para numero
static const char* mesParaNumero(const char* mes) {
    if (!mes) return "01";
    char m[4]={0};
    snprintf(m, sizeof(m), "%c%c%c", tolower((unsigned char)mes[0]), tolower((unsigned char)mes[1]), tolower((unsigned char)mes[2]));
    if (!strcmp(m,"jan")) return "01";
    if (!strcmp(m,"feb")) return "02";
    if (!strcmp(m,"mar")) return "03";
    if (!strcmp(m,"apr")) return "04";
    if (!strcmp(m,"may")) return "05";
    if (!strcmp(m,"jun")) return "06";
    if (!strcmp(m,"jul")) return "07";
    if (!strcmp(m,"aug")) return "08";
    if (!strcmp(m,"sep")) return "09";
    if (!strcmp(m,"oct")) return "10";
    if (!strcmp(m,"nov")) return "11";
    if (!strcmp(m,"dec")) return "12";
    return "01";
}

//funcao simples de separar virgula
static char** separarPorVirgula(const char* s, int* out_len) {
    if (!s) { *out_len = 0; return NULL; }
    char* tmp = duplicaString(s);
    
    int count = 1;
    for (char* p = tmp; *p; p++) if (*p == ',') count++;
    
    char** arr = (char**)malloc(sizeof(char*) * count);
    int idx = 0;
    char* token = strtok(tmp, ",");
    
    while (token) {
        char* t = duplicaString(token);
        apararEspacos(t);

        // Remover aspas internas dos elementos
        int len = (int)strlen(t);
        if (len >= 2) {
            // Se o elemento estiver entre aspas simples OU aspas duplas
            if ((t[0] == '\'' && t[len-1] == '\'') || (t[0] == '\"' && t[len-1] == '\"')) {
                t[len-1] = '\0';
                memmove(t, t+1, len-1);
            }
        }
        apararEspacos(t);
        arr[idx++] = t;

        token = strtok(NULL, ",");
    }

    free(tmp);
    *out_len = idx;
    return arr;
}

//remove colchete
static char** removerColchetes(const char* s, int* out_len) {
    if (!s) { *out_len = 0; return NULL; }
    char* s_limpo = duplicaString(s);
    removerAspasExternas(s_limpo); // Garante que aspas de escape do CSV são removidas
    apararEspacos(s_limpo);
    
    if (s_limpo[0] != '\0') {
        int len = (int)strlen(s_limpo);
        if (len >= 2 && s_limpo[0] == '[' && s_limpo[len-1] == ']') {
            s_limpo[len-1] = '\0';
            memmove(s_limpo, s_limpo+1, len-1);
        }
    }
    
    char** arr = separarPorVirgula(s_limpo, out_len);
    free(s_limpo);
    return arr;
}

//depois de tudo isso, pedir o ID e ir printando no modelo pedido
static char* arrayParaString(char** arr, int len) {
    return juntarComVirgula(arr, len);
}

static void imprimirJogo(Game* g){
    printf("=> %d ## ", getId(g)); //começa com a seta
    printf("%s ## ", getName(g));
    printf("%s ## ", getReleaseDate(g));
    printf("%d ## ", getEstimatedOwners(g));
    printf("%.2f ## ", getPrice(g));

    // Arrays: Usando arrayParaString (que agora limpa aspas/colchetes de cada item)
    char* s1 = arrayParaString(getSupportedLanguages(g), getSupportedLanguagesLen(g));
    printf("%s ## ", s1); free(s1);
    printf("%d ## ", getMetacriticScore(g));
    printf("%.1f ## ", getUserScore(g));
    printf("%d ## ", getAchievements(g));
    
    char* s2 = arrayParaString(getPublishers(g), getPublishersLen(g));
    printf("%s ## ", s2); free(s2);
    char* s3 = arrayParaString(getDevelopers(g), getDevelopersLen(g));
    printf("%s ## ", s3); free(s3);
    
    char* s4 = arrayParaString(getCategories(g), getCategoriesLen(g));
    printf("%s ## ", s4); free(s4);
    char* s5 = arrayParaString(getGenres(g), getGenresLen(g));
    printf("%s ## ", s5); free(s5);
    char* s6 = arrayParaString(getTags(g), getTagsLen(g));
    printf("%s ##\n", s6); free(s6);
}

//são 14 elementos -> vamos chamar de campos (0 a 13)
static void salvarCampoJogo(Game* g, int campo, const char* valor){

    //conferir todos os campos pra ver qual é

    if(campo == 0){ //id
        if (valor == NULL || *valor == '\0') setId(g, 0);
        else setId(g, (int)strtol(valor, NULL, 10));
    }

    else if(campo == 1){ //nome
        char nome_limpo[16384]; nome_limpo[0]='\0';
        if(valor) { strncpy(nome_limpo, valor, sizeof(nome_limpo) - 1); }
        removerAspasExternas(nome_limpo); // Garante que o nome esteja limpo de aspas de escape do CSV
        setName(g, nome_limpo);
    }

    else if(campo == 2){ //release Date - CORRIGIDO
        char rawbuf[128]; rawbuf[0]='\0';
        if (valor) { strncpy(rawbuf, valor, 127); rawbuf[127]='\0'; }
        removerAspasExternas(rawbuf); // Remove aspas de escape do CSV da data
        apararEspacos(rawbuf);
        
        char dataFormatada[32]; dataFormatada[0]='\0';
        
        if (strlen(rawbuf)==4 && isdigit((unsigned char)rawbuf[0])) {
            // Formato: YYYY
            snprintf(dataFormatada, sizeof(dataFormatada), "01/01/%s", rawbuf);
        }
        else {
            // Tentativa de Mês Dia, Ano (ex: Oct 23, 2014)
            char mes[8], dia[4], ano[8];
            char* p = strchr(rawbuf, ','); // Procura a primeira vírgula (depois do dia)
            
            if (p != NULL) {
                *p = ' '; // Substitui a primeira vírgula por espaço
            }
            
            // Tenta ler Mês Dia Ano (sem a vírgula)
            if (sscanf(rawbuf, "%3s %3s %7s", mes, dia, ano) == 3 && isdigit((unsigned char)dia[0]) && isdigit((unsigned char)ano[0])) {
                if (strlen(dia)==1) { char dd[3]; dd[0]='0'; dd[1]=dia[0]; dd[2]='\0'; strcpy(dia, dd); }
                snprintf(dataFormatada, sizeof(dataFormatada), "%s/%s/%s", dia, mesParaNumero(mes), ano);
            }
            // Tenta ler Mês Ano (ex: Aug 2018)
            else if (sscanf(rawbuf, "%3s %7s", mes, ano) == 2 && isdigit((unsigned char)ano[0])) {
                 snprintf(dataFormatada, sizeof(dataFormatada), "01/%s/%s", mesParaNumero(mes), ano);
            }
            else {
                snprintf(dataFormatada, sizeof(dataFormatada), "01/01/0001");
            }
        }
        setReleaseDate(g, dataFormatada);
    }

    else if(campo == 3){ //estimated owners
        char buf[64]; int k=0;
        if (valor) {
            for (int i=0; valor[i] && k<63; i++) if (isdigit((unsigned char)valor[i])) buf[k++]=valor[i];
        }
        buf[k]='\0';
        if (k==0) setEstimatedOwners(g, 0);
        else setEstimatedOwners(g, (int)strtol(buf, NULL, 10));
    }

    else if(campo == 4){ //price
        if (valor && strcasecmp(valor, "Free to Play")==0) setPrice(g, 0.0f);
        else if (valor && *valor) {
            char tmp[64]; int k=0;
            for (int i=0; valor[i] && k<63; i++) {
                tmp[k++] = (valor[i]==',')?'.':valor[i];
            }
            tmp[k]='\0';
            setPrice(g, strtof(tmp, NULL));
        }
    }

    else if(campo == 5){ //supported languages
        int len=0; char** a = removerColchetes(valor, &len);
        setSupportedLanguages(g, a, len);
        for (int i=0; i<len; i++) free(a[i]); free(a);
    }

    else if(campo == 6){ //metacritic score
        if (!valor || *valor=='\0') setMetacriticScore(g, -1);
        else setMetacriticScore(g, (int)strtol(valor, NULL, 10));
    }

    else if(campo == 7){ //user score
        if (!valor || *valor=='\0' || strcasecmp(valor, "tbd")==0) setUserScore(g, -1.0f);
        else {
            char tmp[64]; int k=0;
            for (int i=0; valor[i] && k<63; i++) tmp[k++] = (valor[i]==',')?'.':valor[i];
            tmp[k]='\0';
            setUserScore(g, strtof(tmp, NULL));
        }
    }

    else if(campo == 8){ //achievement
        if (!valor || *valor=='\0') setAchievements(g, 0);
        else setAchievements(g, (int)strtol(valor, NULL, 10));
    }

    else if(campo == 9){ //publishers
        char valor_limpo[16384]; valor_limpo[0]='\0';
        if(valor) { strncpy(valor_limpo, valor, sizeof(valor_limpo) - 1); }
        removerAspasExternas(valor_limpo);
        int len=0; char** a = separarPorVirgula(valor_limpo, &len);
        setPublishers(g, a, len);
        for (int i=0; i<len; i++) free(a[i]); free(a);
    }

    else if(campo == 10){ //developers
        char valor_limpo[16384]; valor_limpo[0]='\0';
        if(valor) { strncpy(valor_limpo, valor, sizeof(valor_limpo) - 1); }
        removerAspasExternas(valor_limpo);
        int len=0; char** a = separarPorVirgula(valor_limpo, &len);
        setDevelopers(g, a, len);
        for (int i=0; i<len; i++) free(a[i]); free(a);
    }

    else if(campo == 11){ //categories
        int len=0; char** a = removerColchetes(valor, &len);
        setCategories(g, a, len);
        for (int i=0; i<len; i++) free(a[i]); free(a);
    }

    else if(campo == 12){ //generos
        int len=0; char** a = removerColchetes(valor, &len);
        setGenres(g, a, len);
        for (int i=0; i<len; i++) free(a[i]); free(a);
    }

    else if(campo == 13){ //tags
        int len=0; char** a = removerColchetes(valor, &len);
        setTags(g, a, len);
        for (int i=0; i<len; i++) free(a[i]); free(a);
    }
}

//percorrer a linha e montar campos (0..13) - SIMPLIFICADA
static void lerLinhaCsv(char* linhaCsv, int indice){
    int campo = 0;
    char acumulado[16384]; int tamanho = 0;
    bool aspas = false; // Indica se estamos DENTRO de um bloco delimitado por aspas DUPLAS (")
    int colchetes = 0;

    int L = (int)strlen(linhaCsv);
    for(int j=0; j<L; j++){
        char c = linhaCsv[j]; 

        // 1. Controle de aspas duplas (CSV escape)
        if(c == '\"'){
            aspas = !aspas; // Alterna o estado de aspas.

            // Não acumule aspas de escape do CSV se estiverem na ponta do campo, 
            // mas acumule se estiverem dentro do campo.
            if (tamanho > 0 || (j > 0 && linhaCsv[j-1] != ',')) {
                if (tamanho < (int)sizeof(acumulado)-1) acumulado[tamanho++] = c;
            }
            continue; // Evita que a aspa seja contada novamente na seção abaixo
        }
        
        // 2. Controle de colchetes (arrays)
        else if(c == '['){
            colchetes++;
        }
        else if(c == ']'){
            colchetes--;
        }

        // 3. Controle de vírgula (separador)
        // Se chegou na virgula E não está entre aspas DUPLAS E não está dentro de colchetes, é final.
        if(c == ',' && aspas == false && colchetes == 0){
            acumulado[tamanho] = '\0';
            salvarCampoJogo(&jogos[indice], campo, acumulado);
            
            campo++;
            tamanho = 0;
            continue;
        }
        
        // 4. Acumular o caractere
        if (tamanho < (int)sizeof(acumulado)-1) acumulado[tamanho++] = c;
    }

    // Último campo
    acumulado[tamanho] = '\0';
    salvarCampoJogo(&jogos[indice], campo, acumulado);
}

static int primeiraPalavraMaiuscula(const char *str) {
    char palavra[100]; int i = 0, p = 0;
    while (str[i] != '\0' && !isspace((unsigned char)str[i]) && p < 99)
        palavra[p++] = str[i++];
    palavra[p] = '\0';
    int temLetra = 0;
    for (int j = 0; palavra[j] != '\0'; j++) {
        if (isalpha((unsigned char)palavra[j])) temLetra = 1;
        if (isalpha((unsigned char)palavra[j]) && !isupper((unsigned char)palavra[j])) return 0;
    }
    return temLetra;
}
static int compararNomesAvancado(const char *a, const char *b) {
    char ca = tolower((unsigned char)a[0]);
    char cb = tolower((unsigned char)b[0]);
    if (ca != cb) return (ca - cb);
    int aM = primeiraPalavraMaiuscula(a);
    int bM = primeiraPalavraMaiuscula(b);
    if (aM && !bM) return -1;
    if (!aM && bM) return 1;
    return strcasecmp(a, b);
}

// =========================
// Ordenação (Selection Sort)
// =========================
static void ordenarPorNome(Game* v, int n) {
    for (int i = 0; i < n - 1; i++) {
        int min = i;
        for (int j = i + 1; j < n; j++) {
            if (compararNomesAvancado(v[j].name, v[min].name) < 0)
                min = j;
        }
        if (min != i) {
            Game tmp = v[i];
            v[i] = v[min];
            v[min] = tmp;
        }
    }
}

// -----------------------------
// Função auxiliar: converter data dd/MM/yyyy para inteiro AAAAMMDD
// -----------------------------
static int dataParaInt(const char* data) {
    if (!data || strlen(data) < 10) return 0;
    int d, m, a;
    if (sscanf(data, "%d/%d/%d", &d, &m, &a) != 3) return 0;
    return a * 10000 + m * 100 + d; // Ex: 20230417 -> facilita comparação
}

// -----------------------------
// Função auxiliar: comparar duas datas
// -----------------------------
static int compararPorData(const char* d1, const char* d2) {
    int n1 = dataParaInt(d1);
    int n2 = dataParaInt(d2);
    if (n1 < n2) return -1;
    else if (n1 > n2) return 1;
    else return 0;
}

// -----------------------------
// QuickSort por releaseDate (desempata por id)
// -----------------------------
static void quicksort(Game* v, int esq, int dir) {
    int i = esq, j = dir;
    Game pivo = v[(esq + dir) / 2];  // pivô central

    while (i <= j) {
        // Enquanto v[i] for "menor" que o pivô (data mais antiga)
        while (true) {
            int cmp = compararPorData(v[i].releaseDate, pivo.releaseDate);
            if (cmp == 0) cmp = (v[i].id - pivo.id); // desempate por id
            if (cmp < 0) i++;
            else break;
        }

        // Enquanto v[j] for "maior" que o pivô (data mais recente)
        while (true) {
            int cmp = compararPorData(v[j].releaseDate, pivo.releaseDate);
            if (cmp == 0) cmp = (v[j].id - pivo.id);
            if (cmp > 0) j--;
            else break;
        }

        // Troca
        if (i <= j) {
            Game tmp = v[i];
            v[i] = v[j];
            v[j] = tmp;
            i++;
            j--;
        }
    }

    // Chamadas recursivas
    if (esq < j) quicksort(v, esq, j);
    if (i < dir) quicksort(v, i, dir);
}


int main(void){
    //criar o vetor
    int capacidade = 2000;
    int totalLidos = 0;
    int comparacoes = 0;
    int movimentacoes = 0;
    clock_t inicio = clock();
    
    jogos = (Game*)malloc(sizeof(Game) * capacidade);

    //criar os objetos
    for(int i=0; i<capacidade; i++){
        iniciarJogo(&jogos[i]);  
    }

    //ler o arquivo csv
    const char* caminhoCsv = "/tmp/games.csv";

    FILE* arquivo = fopen(caminhoCsv, "r");
    if (!arquivo) { perror("open games.csv"); return 1; }

    char linhaCsv[20000];

    //cabeçalho do csv -> ignorar
    if (!fgets(linhaCsv, sizeof(linhaCsv), arquivo)) { fclose(arquivo); return 1; }

    //pegar as próximas linhas e guardar no vetor da classe Game
    for(int i=0; i<capacidade; i++){
        comparacoes++; movimentacoes++;
        if (!fgets(linhaCsv, sizeof(linhaCsv), arquivo)) break;
        linhaCsv[strcspn(linhaCsv, "\r\n")] = '\0';
        lerLinhaCsv(linhaCsv, i);
        totalLidos++;
    }

    fclose(arquivo);

    //ler os ids pedidos e guardar o objeto em um novo vetor
    Game* selecionados = malloc(sizeof(Game) * totalLidos);
    char entrada[30];
    int tam = 0;

    while (scanf("%s", entrada) != EOF && strcmp(entrada, "FIM") != 0) {
        int id = atoi(entrada);
        for (int i = 0; i < totalLidos; i++) {
            if (jogos[i].id == id) {
                comparacoes++; movimentacoes++;
                selecionados[tam++] = jogos[i];
                break;
            }
        }
    }
    
    //ordenar por seleção e imprimir todos
    //ordenarPorNome(selecionados, tam); -> função substituida
    quicksort(selecionados, 0, tam - 1);
    
    comparacoes+=(10*totalLidos);
    movimentacoes+=(5*totalLidos);

    for (int i = 0; i < tam; i++) imprimirJogo(&selecionados[i]);

    //criar o arquivo log
    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    FILE* log = fopen("885761_quicksort.txt", "w");
    if (log) {
        fprintf(log, "885761\t%.5f\t%d\t%d\n", tempo, comparacoes, movimentacoes);
        fclose(log);
    }

    
    

    // free de tudo
    for (int i=0; i<capacidade; i++) destruirJogo(&jogos[i]);
    free(jogos);
    return 0;
}