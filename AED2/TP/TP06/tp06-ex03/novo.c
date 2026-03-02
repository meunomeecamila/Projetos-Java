#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

//definição da capacidade máxima para a string de linha (tamanho razoável)
#define MAX_LINHA 20000

//STRUCT GAME ====================
// Representa um único jogo com todos os seus atributos certinhos
typedef struct {
    char** idiomasSuportados; int lenIdiomas;
    char** publicadores; int lenPublicadores;
    char** desenvolvedores; int lenDesenvolvedores;
    char** categorias; int lenCategorias;
    char** generos; int lenGeneros;
    char** tags; int lenTags;
    int id;
    char* nome;
    char* dataLancamento;
    int donosEstimados;
    float preco;
    int notaMetacritic;
    float notaUsuario;
    int conquistas;
} Game;

// Vetor global para armazenar todos os jogos lidos do CSV
static Game* vetorJogosGlobais;

// --- IMPLEMENTAÇÃO MANUAL DA FUNÇÃO STRCAT ---
char* strcatManual(char* destino, const char* fonte) {
    if(destino == NULL) return destino;
    if(fonte == NULL) return destino;

    //percorre até encontrar null no destino
    char* p = destino;
    while (*p != '\0') {
        p++; // Avança o ponteiro 'p' um caractere por vez
    }
    
    //agora o ponteiro 'p' está apontando EXATAMENTE para o '\0' que precisa ser sobrescrito.

    //cpiar a 'fonte' pedida para onde 'p' aponta
    size_t i = 0;
    while (fonte[i] != '\0') {
        *p = fonte[i]; //copia o caractere da fonte para o destino
        p++;  //avança o ponteiro de destino
        i++;  //avança o índice da fonte
    }

    //grantir que a nova string concatenada termine com '\0'
    *p = '\0';

    return destino; // Retorna o ponteiro para o destino
}

//copiar uma string pra outra
static char* copiarString(const char* s) {
    if (!s) return NULL;
    size_t len = strlen(s);
    char* p = (char*)malloc(len + 1);
    
    if (p != NULL) strcpy(p, s);
    return p;
}

//funciona como um TRIM do java
static void removerEspacosLaterais(char* s) {
    if (!s) return;
    int len = (int)strlen(s);
    if (len == 0) return;

    //remove do início (anda o ponteiro)
    int inicio = 0;
    while (s[inicio] != '\0' && s[inicio] == ' ') {
        inicio++;
    }

    //se tem espaços no início, move o resto da string manualmente
    if (inicio > 0) {
        int i, j;
        for (i = inicio, j = 0; s[i] != '\0'; i++, j++) {
            s[j] = s[i];
        }
        s[j] = '\0'; // Termina a nova string
    }

    // Atualiza o comprimento
    len = (int)strlen(s);
    if (len == 0) return;
    
    // Remove do final
    while (len > 0 && isspace(s[len - 1])) {
        s[len - 1] = '\0';
        len--;
    }
}

//remove aspas ou apóstrofos que envolvem a string
static void limparAspas(char* s) {
    if (!s) return;
    removerEspacosLaterais(s);
    int len = (int)strlen(s);
    
    if (len >= 2) {
        //se o ultimo ou penultimo caractere for aspas
        if ((s[0] == '"' && s[len - 1] == '"') || (s[0] == '\'' && s[len - 1] == '\'')) {
            s[len - 1] = '\0'; // Remove o caractere final
            
            // Move a string um caractere para trás
            int i;
            for (i = 0; s[i + 1] != '\0'; i++) {
                s[i] = s[i + 1];
            }
            s[i] = '\0';

            removerEspacosLaterais(s); //se ainda sobrou
        }
    }
}

//remove caracteres de aspas, apóstrofos, colchetes 
static void limparCaracteresArray(char* s) {
    if (!s) return;
    int len = (int)strlen(s);
    
    int j = 0;
    for (int i = 0; i < len; i++) {
        if (s[i] != '"' && s[i] != '\'' && s[i] != '[' && s[i] != ']')
            s[j++] = s[i];
    }
    s[j] = '\0';
    removerEspacosLaterais(s);
}

//converte um array de strings em uma única string formatada
static char* arrayParaStringComVirgulas(char** arr, int size) {
    if (!arr) return copiarString("[]");
    if(size <= 0) return copiarString("[]");
    
    // Aloca um tamanho inicial grande
    size_t total = 512;
    char* out = (char*)malloc(total);
    if (!out) return copiarString("[]");
    
    strcpy(out, "[");
    
    for (int i = 0; i < size; i++) {
        //faz uma cópia temporária para limpar o elemento
        char* item = copiarString(arr[i]);
        limparCaracteresArray(item); // Limpa aspas e colchetes dentro do item

        // Verifica se ainda há espaço
        //é 3 porque é 1 pro nulo e 2 pra virgula e espaço
        if (strlen(out) + strlen(item) + 3 >total) { 
            // Realocação simples (ainda necessária, mas o uso é direto)
            total = strlen(out) + strlen(item) + 100; //chute
            out = (char*)realloc(out, total);
        }
        
        strcatManual(out, item);
        
        if (i < size - 1) {
            strcatManual(out, ", ");
        }
        free(item);
    }
    
    strcatManual(out, "]");
    return out;
}

//FUNÇÕES BÁSICAS ===============

static void inicializarJogo(Game* g) {
    g->id = 0;
    g->nome = copiarString("");
    g->dataLancamento = copiarString("");
    g->donosEstimados = 0;
    g->preco = 0.0f;
    g->notaMetacritic = -1;
    g->notaUsuario = -1.0f;
    g->conquistas = 0;
    g->idiomasSuportados = NULL; g->lenIdiomas = 0;
    g->publicadores = NULL; g->lenPublicadores = 0;
    g->desenvolvedores = NULL; g->lenDesenvolvedores = 0;
    g->categorias = NULL; g->lenCategorias = 0;
    g->generos = NULL; g->lenGeneros = 0;
    g->tags = NULL; g->lenTags = 0;
}

static void liberarMemoriaJogo(Game* g) {
    if (!g) return;
    free(g->nome);
    free(g->dataLancamento);
    
    // Libera os arrays de strings e as strings dentro
    char*** arrays[] = {
        &g->idiomasSuportados, &g->publicadores, &g->desenvolvedores,
        &g->categorias, &g->generos, &g->tags
    };
    int* lens[] = {
        &g->lenIdiomas, &g->lenPublicadores, &g->lenDesenvolvedores,
        &g->lenCategorias, &g->lenGeneros, &g->lenTags
    };
    
    for (int k = 0; k < 6; k++) {
        if (*arrays[k]) {
            for (int i = 0; i < *lens[k]; i++) {
                free((*arrays[k])[i]);
            }
            free(*arrays[k]);
        }
    }
}

//converte o mês abreviado em inglês para número (ex: Jan -> 01)
static const char* mesParaNumero(const char* mes) {
    if (!mes) return "01"; //se não for mes
    char m[4];
    //conversão manual para minúsculas para comparação
    m[0] = (char)tolower(mes[0]); m[1] = (char)tolower(mes[1]); m[2] = (char)tolower(mes[2]); m[3] = '\0';

    //Comparação simples de strings com strcmp
    if (strcmp(m,"jan")==0) return "01"; 
    if (strcmp(m,"feb")==0) return "02";
    if (strcmp(m,"mar")==0) return "03"; 
    if (strcmp(m,"apr")==0) return "04";
    if (strcmp(m,"may")==0) return "05"; 
    if (strcmp(m,"jun")==0) return "06";
    if (strcmp(m,"jul")==0) return "07"; 
    if (strcmp(m,"aug")==0) return "08";
    if (strcmp(m,"sep")==0) return "09"; 
    if (strcmp(m,"oct")==0) return "10";
    if (strcmp(m,"nov")==0) return "11"; 
    if (strcmp(m,"dec")==0) return "12";
    return "01";
}

//recebe uma string e a divide em um array de strings pelo separador (vírgula)
static char** dividirPorVirgula(const char* s, int* tam) {
    if (s == NULL) { 
        *tam = 0; 
        return NULL; //retorna NULL se a string de entrada for nula
    }
    
    //cria uma cópia da string original (tmp), pois a função strtok modifica a string de entrada,
    //substituindo os delimitadores (vírgulas) por terminadores nulos ('\0').
    char* tmp = copiarString(s); //função que eu fiz lá em cima
    if (tmp == NULL) {
        *tam = 0;
        return NULL;
    }

    //Contar os tokens ou elementos
    int count = 1; //começa com 1, pois o número de tokens é (vírgulas + 1)
    for (char* p = tmp; *p; p++) {
        if (*p == ',') {
            count++;
        }
    }

    char** arr = (char**)malloc(sizeof(char*) * count);
    if (arr == NULL) {
        free(tmp); // Falha na alocação, libera a cópia antes de sair
        *tam = 0;
        return NULL;
    }
    int idx = 0; // Índice de inserção no array de saída
    
    //Função string tokenizer
    //divide uma string em menores com base nos tokenizadores (nesse caso a virgula)
    char* token = strtok(tmp, ",");
    
    while (token) { //enquanto tiverem tokens válidos
        char* t = copiarString(token); 
        removerEspacosLaterais(t);
        limparAspas(t);
        
        arr[idx++] = t;
        
        //continuação do strtok, onde passamos NULL para indicar que ele deve continuar tokenizando a mesma string 'tmp', a partir do ponto onde parou.
        token = strtok(NULL, ",");
    }
    
    free(tmp);
    *tam = idx;
    return arr;
}

//remove os colchetes externos (ex: [a,b] -> a,b)
static char** limparColchetesEdividir(const char* s, int* tam) {
    if (s == NULL) { 
        *tam = 0; 
        return NULL; 
    }
    char* s_limpo = copiarString(s);
    limparAspas(s_limpo);
    removerEspacosLaterais(s_limpo);
    
    int L = (int)strlen(s_limpo);
    if (L >= 2 && s_limpo[0] == '[' && s_limpo[L-1] == ']') { //se tiver colchetes
        s_limpo[L-1] = '\0'; //remove colchete 2 (])
        
        //remove colchete 1 ([)
        int i;
        for (i = 0; s_limpo[i + 1] != '\0'; i++) {
            s_limpo[i] = s_limpo[i + 1];
        }
        s_limpo[i] = '\0';
    }
    
    char** arr = dividirPorVirgula(s_limpo, tam);
    free(s_limpo);
    return arr;
}

//atribui o valor do campo lido à struct Game
static void atribuirCampo(Game* g, int campo, const char* valor) {
    //função atoi -> converte ascii para inteiro
    if (campo == 0) { // id
        g->id = valor && *valor ? atoi(valor) : 0;
    }
    else if (campo == 1) { // nome
        char tmp[1024]; tmp[0] = '\0';
        if (valor) strncpy(tmp, valor, sizeof(tmp) - 1);
        limparAspas(tmp);
        free(g->nome);
        g->nome = copiarString(tmp);
    }
    else if (campo == 2) { // dataLancamento
        char mes[8], dia[8], ano[8];
        if (!valor || !*valor) {
            free(g->dataLancamento); 
            g->dataLancamento = copiarString("01/01/0001"); 
            return;
        }
        
        if (sscanf(valor, "%3s %3s %7s", mes, dia, ano) == 3) {
            char data[20];
            snprintf(data, sizeof(data), "%02d/%s/%s", atoi(dia), mesParaNumero(mes), ano);
            free(g->dataLancamento);
            g->dataLancamento = copiarString(data);
        } else if (sscanf(valor, "%3s %7s", mes, ano) == 2) {
            char data[20];
            snprintf(data, sizeof(data), "01/%s/%s", mesParaNumero(mes), ano);
            free(g->dataLancamento);
            g->dataLancamento = copiarString(data);
        } else {
            free(g->dataLancamento);
            g->dataLancamento = copiarString(valor);
        }
    }
    else if (campo == 3) { // donosEstimados
        // Remove as vírgulas manualmente
        char num[64]; int k = 0;
        if (valor) for (int i = 0; valor[i]; i++) if (isdigit(valor[i])) num[k++] = valor[i];
        num[k] = '\0';
        g->donosEstimados = k ? atoi(num) : 0;
    }
    else if (campo == 4) { // preco
        if (valor && strcmp(valor, "Free to Play") == 0) g->preco = 0.0f;
        else if (valor && *valor) {
            char tmp[64]; strcpy(tmp, valor);
            for (int i = 0; tmp[i]; i++) if (tmp[i] == ',') tmp[i] = '.';
            g->preco = (float)atof(tmp);
        } else g->preco = 0.0f;
    }
    else if (campo == 5) { // idiomasSuportados
        int len = 0; char** arr = limparColchetesEdividir(valor, &len);
        g->idiomasSuportados = arr; g->lenIdiomas = len;
    }
    else if (campo == 6) { // notaMetacritic
        g->notaMetacritic = (valor && *valor) ? atoi(valor) : -1;
    }
    else if (campo == 7) { // notaUsuario
        if (!valor || !*valor || strcmp(valor,"tbd")==0) g->notaUsuario = -1.0f;
        else g->notaUsuario = (float)atof(valor);
    }
    else if (campo == 8) { // conquistas
        g->conquistas = valor && *valor ? atoi(valor) : 0;
    }
    else if (campo == 9) { // publicadores
        int len = 0; char** arr = dividirPorVirgula(valor, &len);
        g->publicadores = arr; g->lenPublicadores = len;
    }
    else if (campo == 10) { // desenvolvedores
        int len = 0; char** arr = dividirPorVirgula(valor, &len);
        g->desenvolvedores = arr; g->lenDesenvolvedores = len;
    }
    else if (campo == 11) { // categorias
        int len = 0; char** arr = limparColchetesEdividir(valor, &len);
        g->categorias = arr; g->lenCategorias = len;
    }
    else if (campo == 12) { // generos
        int len = 0; char** arr = limparColchetesEdividir(valor, &len);
        g->generos = arr; g->lenGeneros = len;
    }
    else if (campo == 13) { // tags
        int len = 0; char** arr = limparColchetesEdividir(valor, &len);
        g->tags = arr; g->lenTags = len;
    }
}

static void processarLinha(char* linhaCsv, int indice) {
    int campo = 0; 
    char acumulado[MAX_LINHA]; // Buffer para armazenar o campo atual
    int tamanho = 0;
    
    //controle de estado para ignorar vírgulas dentro de aspas ou colchetes
    bool estaEmAspas = false; 
    int nivelColchetes = 0;
    
    int L = (int)strlen(linhaCsv);
    
    for (int j = 0; j < L; j++) {
        char c = linhaCsv[j];
        
        //alterna o estado de aspas
        if (c == '\"') { 
            estaEmAspas = !estaEmAspas; 
            continue; 
        }
        // Aumenta/diminui o nível de colchetes
        else if (c == '[') nivelColchetes++;
        else if (c == ']') nivelColchetes--;
        
        //verifica se é um separador de campo VÁLIDO (fora de aspas e colchetes)
        if (c == ',' && !estaEmAspas && nivelColchetes == 0) {
            acumulado[tamanho] = '\0';
            atribuirCampo(&vetorJogosGlobais[indice], campo, acumulado);
            campo++; 
            tamanho = 0; 
            continue;
        }
        
        // Adiciona o caractere ao buffer, se houver espaço
        if (tamanho < (int)sizeof(acumulado) - 1) {
             acumulado[tamanho++] = c;
        }
    }
    
    // Salva o último campo
    acumulado[tamanho] = '\0';
    atribuirCampo(&vetorJogosGlobais[indice], campo, acumulado);
}

//IMPRIMIR ===================
static void imprimirJogo(Game* g) {
    printf("=> %d ## ", g->id); 
    printf("%s ## ", g->nome);
    printf("%s ## ", g->dataLancamento);
    printf("%d ## ", g->donosEstimados);
    printf("%.2f ## ", g->preco);

    char* s1 = arrayParaStringComVirgulas(g->idiomasSuportados, g->lenIdiomas);
    printf("%s ## ", s1); free(s1);
    
    printf("%d ## ", g->notaMetacritic);
    printf("%.1f ## ", g->notaUsuario);
    printf("%d ## ", g->conquistas);
    
    char* s2 = arrayParaStringComVirgulas(g->publicadores, g->lenPublicadores);
    printf("%s ## ", s2); free(s2);
    char* s3 = arrayParaStringComVirgulas(g->desenvolvedores, g->lenDesenvolvedores);
    printf("%s ## ", s3); free(s3);
    
    char* s4 = arrayParaStringComVirgulas(g->categorias, g->lenCategorias);
    printf("%s ## ", s4); free(s4);
    char* s5 = arrayParaStringComVirgulas(g->generos, g->lenGeneros);
    printf("%s ## ", s5); free(s5);
    char* s6 = arrayParaStringComVirgulas(g->tags, g->lenTags);
    printf("%s ##\n", s6); free(s6);
}

//Célula da pilha
typedef struct Celula {
    Game* jogo;
    struct Celula* prox;
} Celula;

typedef struct {
    Celula* topo; 
    int tamanho;
} Pilha;

// Inicializa a Pilha
void iniciarPilha(Pilha* p) {
    // Aloca a cabeça)
    p->topo = (Celula*)malloc(sizeof(Celula));
    p->topo->prox = NULL;
    p->tamanho = 0;
}

// Operação PUSH: Inserir um elemento no topo
void empilhar(Pilha* p, Game* g) {
    Celula* tmp = (Celula*)malloc(sizeof(Celula));
    tmp->jogo = g;
    tmp->prox = p->topo->prox; 
    
    p->topo->prox = tmp;
    p->tamanho++;
}

// Operação POP: Remover um elemento do topo
Game* desempilhar(Pilha* p) {
    if (p->topo->prox == NULL) return NULL; 
    
    Celula* tmp = p->topo->prox; // tmp é o nó a ser removido (o topo)
    Game* g = tmp->jogo;        // Salva o jogo do topo para retorno
    
    p->topo->prox = tmp->prox; 
    
    free(tmp); 
    p->tamanho--;
    return g; 
}

//obs: mostrar a pilha invertida 
void mostrar(Pilha* p) {
    if (p->tamanho == 0) return;
    
    // 1. Aloca um vetor temporário para armazenar os ponteiros dos jogos
    Game** jogosTemporarios = (Game**)malloc(sizeof(Game*) * p->tamanho);
    if (jogosTemporarios == NULL) return; 

    Celula* i = p->topo->prox;
    int count = 0;
    
    // 2. Preenche o vetor, do Topo para a Base (índice 0 até tamanho-1)
    while (i != NULL) {
        if (count < p->tamanho) {
            jogosTemporarios[count++] = i->jogo;
        }
        i = i->prox;
    }
    
    // 3. Imprime o vetor de trás para frente (da Base para o Topo)
    // O elemento mais antigo (Base) está no final do vetor temporário (índice count - 1)
    // O índice [0] deve corresponder ao elemento mais antigo (Base)
    for (int idx = 0; idx < count; idx++) {
        // Exibe o elemento do final (Base) para o início (Topo) do vetor temporário
        // O elemento que será impresso é o que está no índice reverso: (count - 1 - idx)
        // Isso inverte a ordem de impressão.
        Game* g = jogosTemporarios[count - 1 - idx];
        printf("[%d] ", idx); 
        imprimirJogo(g);
    }

    // 4. Libera o vetor temporário
    free(jogosTemporarios);
}

//Funções de busca utilizadas no tp anterior
// Busca o jogo no vetor global pelo ID
Game* buscarJogoPorId(int id, int totalLidos) {
    for (int i = 0; i < totalLidos; i++) {
        if (vetorJogosGlobais[i].id == id) return &vetorJogosGlobais[i];
    }
    return NULL;
}

int main(void) {
    int capacidade = 2000;
    int totalLidos = 0;

    vetorJogosGlobais = (Game*)malloc(sizeof(Game) * capacidade);
    for (int i = 0; i < capacidade; i++) inicializarJogo(&vetorJogosGlobais[i]);

    FILE* arq = fopen("/tmp/games.csv", "r");
    if (!arq) {
        perror("Erro ao abrir games.csv");
        return 1;
    }

    char linha[MAX_LINHA];
    //ignora a linha do cabeçalho do csv
    if (!fgets(linha, sizeof(linha), arq)) { 
        fclose(arq); 
        return 1; 
    } 

    //lê todas as linhas do CSV e preenche o vetor
    while (fgets(linha, sizeof(linha), arq) && totalLidos < capacidade) {
        //remove quebra de linha do final
        //remove \r ou \n do final
        for (int i = strlen(linha) - 1; i >= 0 && (linha[i] == '\r' || linha[i] == '\n'); i--) {
            linha[i] = '\0';
        }
        processarLinha(linha, totalLidos++);
    }
    fclose(arq);

    // Pilha principal
    Pilha pilhaPrincipal; 
    iniciarPilha(&pilhaPrincipal);
    char entrada[100];

    // PASSO A PASSO PEDIDO NO TP ================

    // 1) Ler IDs iniciais até "FIM" e EMPILHAR (PUSH)
    while (scanf("%s", entrada) != EOF) {
        if (strcmp(entrada, "FIM") == 0) break;
        int id = atoi(entrada); 
        Game* g = buscarJogoPorId(id, totalLidos);
        // Empilha o jogo (Insere no Topo)
        if (g != NULL) empilhar(&pilhaPrincipal, g); 
    }

    // 2) Ler número de operações
    int numOperacoes;
    if (scanf("%d", &numOperacoes) != 1) numOperacoes = 0;

    // 3) Processar operações ('I' para Push, 'R' para Pop)
    for (int i = 0; i < numOperacoes; i++) {
        char comando[2]; // Suficiente para 'I' ou 'R'
        if (scanf("%1s", comando) != 1) break; // Lê um único caractere

        if (comando[0] == 'I') {
            // Empilhar (PUSH)
            int id; 
            if (scanf("%d", &id) == 1) { // Lê o ID em seguida
                Game* g = buscarJogoPorId(id, totalLidos);
                if (g) empilhar(&pilhaPrincipal, g);
            }

        } else if (comando[0] == 'R') {
            // Desempilhar (POP)
            Game* removido = desempilhar(&pilhaPrincipal);
            if (removido) printf("(R) %s\n", removido->nome);

        }
    }

    // 4) Mostrar itens restantes com índice (agora Base -> Topo)
    mostrar(&pilhaPrincipal);

    //liberação de memória
    // Nota: Os jogos no vetor global nunca são liberados aqui, pois são referências compartilhadas.
    for (int i = 0; i < totalLidos; i++) liberarMemoriaJogo(&vetorJogosGlobais[i]);
    free(vetorJogosGlobais);
    // Libera a sentinela da pilha
    free(pilhaPrincipal.topo); 
    
    return 0;
}