/* PASSO A PASSO TP
01- Ler os ids até FIM e salvar os objetos em um uma árvore binária de nome
02- Ler os nomes pedidos e verificar se estão na árvore
03- Imprimir o nome, o caminho percorrido na árvore e SIM ou NAO
04- Gerar um arquivo log 
*/
//obs: nao foi especificado o que colocar dentro do arquivo log, então coloquei a saída, matricula e tempo

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include <time.h> // adicionei time.h para calcular e registrar o tempo

//definição da capacidade máxima para a string de linha (tamanho razoável)
#define MAX_LINHA 20000
//capacidade máxima para string de caminho de pesquisa
#define MAX_CAMINHO 4096 

//STRUCT GAME ====================
//representa um único jogo com todos os seus atributos certinhos
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

//STRUCT NÓ AVL ====================
// Nó da Árvore AVL, chave é o nome (char*)
typedef struct NoAVL {
    Game* jogo;           // Ponteiro para o dado (struct Game)
    int altura;           // Altura do nó (necessária para balanceamento)
    struct NoAVL* esq;    // Ponteiro para o filho esquerdo
    struct NoAVL* dir;    // Ponteiro para o filho direito
} NoAVL;

// Vetor global para armazenar todos os jogos lidos do CSV
static Game* vetorJogosGlobais;
// Variável global para armazenar o número total de comparações
static int numComparacoes = 0;

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

// Funções de busca utilizadas no tp anterior
// Busca o jogo no vetor global pelo ID
Game* buscarJogoPorId(int id, int totalLidos) {
    for (int i = 0; i < totalLidos; i++) {
        if (vetorJogosGlobais[i].id == id) return &vetorJogosGlobais[i];
    }
    return NULL;
}

// FUNÇÕES AVL ==========================================================================
// implementadas no estilo de estudante :)

// função auxiliar que retorna o maior entre dois inteiros
static int max(int a, int b) {
    return (a > b) ? a : b;
}

// retorna a altura do nó (considera NULL como -1)
static int get_altura(NoAVL* no) {
    if (no == NULL) return -1;
    return no->altura;
}

// retorna o fator de balanceamento do nó (esq - dir)
static int calcula_fb(NoAVL* no) {
    if (no == NULL) return 0;
    return get_altura(no->esq) - get_altura(no->dir);
}

// rotação simples à direita (caso EE)
static NoAVL* rotacao_simples_dir(NoAVL* p) {
    NoAVL* q = p->esq;
    
    p->esq = q->dir;
    q->dir = p;
    
    // atualiza alturas (do nó mais baixo para o mais alto)
    p->altura = max(get_altura(p->esq), get_altura(p->dir)) + 1;
    q->altura = max(get_altura(q->esq), get_altura(q->dir)) + 1;
    
    return q; // nova raiz da subárvore
}

// rotação simples à esquerda (caso DD)
static NoAVL* rotacao_simples_esq(NoAVL* p) {
    NoAVL* q = p->dir;
    
    p->dir = q->esq;
    q->esq = p;
    
    // atualiza alturas (do nó mais baixo para o mais alto)
    p->altura = max(get_altura(p->esq), get_altura(p->dir)) + 1;
    q->altura = max(get_altura(q->esq), get_altura(q->dir)) + 1;
    
    return q; // nova raiz da subárvore
}

// rotação dupla à direita (caso ED)
static NoAVL* rotacao_dupla_dir(NoAVL* p) {
    // 1. rotacao simples à esquerda no filho esquerdo
    p->esq = rotacao_simples_esq(p->esq);
    
    // 2. rotacao simples à direita no nó desbalanceado
    return rotacao_simples_dir(p);
}

// rotação dupla à esquerda (caso DE)
static NoAVL* rotacao_dupla_esq(NoAVL* p) {
    // 1. rotacao simples à direita no filho direito
    p->dir = rotacao_simples_dir(p->dir);
    
    // 2. rotacao simples à esquerda no nó desbalanceado
    return rotacao_simples_esq(p);
}

// função recursiva de insercao avl
static NoAVL* inserir_avl(NoAVL* no, Game* g) {
    if (no == NULL) {
        // 1. caso base: cria e retorna o novo nó
        NoAVL* novo = (NoAVL*)malloc(sizeof(NoAVL));
        novo->jogo = g;
        novo->altura = 0; // nó folha tem altura 0
        novo->esq = novo->dir = NULL;
        return novo;
    }

    // 2. procura o local de insercao (baseado no 'nome')
    int comparacao = strcmp(g->nome, no->jogo->nome);

    if (comparacao < 0) {
        // vai para a esquerda
        no->esq = inserir_avl(no->esq, g);
    } else if (comparacao > 0) {
        // vai para a direita
        no->dir = inserir_avl(no->dir, g);
    } else {
        // chave duplicada: NAO insere
        return no;
    }

    // 3. atualiza a altura do nó atual (na volta da recursão)
    no->altura = max(get_altura(no->esq), get_altura(no->dir)) + 1;

    // 4. verifica o fator de balanceamento e rotaciona se precisar
    int fb = calcula_fb(no);

    // desbalanceado para a esquerda (fb > 1)
    if (fb > 1) {
        // caso ee: rotação simples à direita
        // se fb(esq) >= 0 (inserido na subárvore esquerda do filho esquerdo)
        if (calcula_fb(no->esq) >= 0) { 
            return rotacao_simples_dir(no);
        }
        // caso ed: rotação dupla à direita
        // se fb(esq) < 0 (inserido na subárvore direita do filho esquerdo)
        else { 
            return rotacao_dupla_dir(no);
        }
    }

    // desbalanceado para a direita (fb < -1)
    if (fb < -1) {
        // caso dd: rotação simples à esquerda
        // se fb(dir) <= 0 (inserido na subárvore direita do filho direito)
        if (calcula_fb(no->dir) <= 0) { 
            return rotacao_simples_esq(no);
        }
        // caso de: rotação dupla à esquerda
        // se fb(dir) > 0 (inserido na subárvore esquerda do filho direito)
        else { 
            return rotacao_dupla_esq(no);
        }
    }

    // se balanceado, retorna o nó sem alterações
    return no;
}

// função de pesquisa avl que imprime o caminho
void pesquisar_avl(NoAVL* raiz, const char* nomeBusca, FILE* logFile) {
    NoAVL* atual = raiz;
    char caminho[MAX_CAMINHO] = "raiz"; // armazena o caminho percorrido, começando com "raiz"
    bool encontrado = false;
    
    // percorre a árvore, montando o caminho das direções
    while (atual != NULL) {
        
        numComparacoes++; // conta a comparação de strcmp

        int comparacao = strcmp(nomeBusca, atual->jogo->nome);

        if (comparacao == 0) {
            encontrado = true;
            break;
        } else if (comparacao < 0) {
            // vai para a esquerda
            strcatManual(caminho, " esq"); // Adiciona a direção ESQ
            atual = atual->esq;
        } else {
            // vai para a direita
            strcatManual(caminho, " dir"); // Adiciona a direção DIR
            atual = atual->dir;
        }
    }

    // Monta o resultado final SIM/NAO
    const char* resultado = encontrado ? "SIM" : "NAO";

    // imprime o resultado na saída padrão e no arquivo de log no formato:
    // Nome do Jogo: raiz <direções> SIM/NAO
    printf("%s: %s %s\n", nomeBusca, caminho, resultado);
    fprintf(logFile, "%s: %s %s\n", nomeBusca, caminho, resultado);
}

// liberação recursiva da memória dos nós da árvore avl
static void liberar_memoria_avl(NoAVL* no) {
    if (no != NULL) {
        liberar_memoria_avl(no->esq);
        liberar_memoria_avl(no->dir);
        // O Game* não é liberado aqui, pois ele é um ponteiro para o vetorJogosGlobais.
        free(no);
    }
}

int main(void) {
    // marca o tempo de início para o log
    clock_t inicio = clock();

    int capacidade = 2000;
    int totalLidos = 0;

    vetorJogosGlobais = (Game*)malloc(sizeof(Game) * capacidade);
    // adicionei a verificação de alocação de memória (boa prática)
    if (!vetorJogosGlobais) {
        perror("erro ao alocar vetorJogosGlobais");
        return 1;
    }
    for (int i = 0; i < capacidade; i++) inicializarJogo(&vetorJogosGlobais[i]);

    FILE* arq = fopen("/tmp/games.csv", "r");
    if (!arq) {
        perror("erro ao abrir games.csv");
        return 1;
    }

    char linha[MAX_LINHA];
    //ignora a linha do cabeçalho do csv
    if (!fgets(linha, sizeof(linha), arq)) { 
        // caso o arquivo esteja vazio (linha 1: cabeçalho)
        if (totalLidos == 0) {
            fclose(arq); 
            free(vetorJogosGlobais);
            return 1; 
        }
    } 

    //lê todas as linhas do CSV e preenche o vetor
    while (fgets(linha, sizeof(linha), arq) && totalLidos < capacidade) {
        //remove quebra de linha do final
        //remove \r ou \n do final
        for (int i = strlen(linha) - 1; i >= 0 && (linha[i] == '\r' || linha[i] == '\n'); i--) {
            linha[i] = '\0';
        }
        processarLinha(linha, totalLidos++);
        // se precisar de mais capacidade, realoca o vetor
        if (totalLidos == capacidade) {
             capacidade *= 2;
             Game* novoVetor = (Game*)realloc(vetorJogosGlobais, sizeof(Game) * capacidade);
             if (novoVetor == NULL) {
                perror("erro ao realocar vetorJogosGlobais");
                // se a realocação falhar, continua com a capacidade atual, mas não lê mais
                break; 
             }
             vetorJogosGlobais = novoVetor;
             // inicializa os novos slots
             for (int i = totalLidos; i < capacidade; i++) inicializarJogo(&vetorJogosGlobais[i]);
        }
    }
    fclose(arq);

    // Estrutura AVL principal
    NoAVL* raizAVL = NULL;
    char entrada[100];

    // PASSO A PASSO PEDIDO NO TP ================

    // 1) Ler IDs iniciais até "FIM" e INSERIR na AVL (Chave: Nome)
    while (scanf("%s", entrada) != EOF) {
        if (strcmp(entrada, "FIM") == 0) break;
        int id = atoi(entrada); 
        Game* g = buscarJogoPorId(id, totalLidos);
        // Insere o jogo na AVL. A função cuida do balanceamento e de duplicidade.
        if (g != NULL) {
            raizAVL = inserir_avl(raizAVL, g);
        }
    }

    // 2) Abrir arquivo de log
    FILE* logFile = fopen("885761_arvoreAVL.txt", "w");
    if (!logFile) {
        perror("erro ao criar arquivo de log.");
    }
    
    fprintf(logFile, "matrícula: 885761\n");
    fprintf(logFile, "caminho de pesquisa e resultado (SIM/NAO)\n");
    // calcula o tempo de construção da árvore (aproximado)
    double tempoConstrucao = (double)(clock() - inicio) / CLOCKS_PER_SEC;

    // 3) Processar nomes para pesquisa na AVL e mostrar o caminho
    char nomeBusca[100];
    // usa um espaço antes do %s para consumir qualquer newline/whitespace pendente
    while (scanf(" %[^\n]", nomeBusca) != EOF) { 
        if (strcmp(nomeBusca, "FIM") == 0) {
            break; 
        }
        
        limparAspas(nomeBusca);
        pesquisar_avl(raizAVL, nomeBusca, logFile);
    }
    
    // marca o tempo de fim e calcula o tempo total
    clock_t fim = clock();
    double tempoTotal = (double)(fim - inicio) / CLOCKS_PER_SEC;

    // 4) Adicionar informações de tempo e comparações no log (passo 4)
    fprintf(logFile, "\ntempo de construção da árvore (s): %.6f\n", tempoConstrucao);
    fprintf(logFile, "tempo total do programa (s): %.6f\n", tempoTotal);
    fprintf(logFile, "número total de comparações: %d\n", numComparacoes);

    // 5) Liberação de memória
    if (logFile) fclose(logFile); // Fecha o arquivo de log

    liberar_memoria_avl(raizAVL); // Libera os nós da AVL
    // Libera a memória alocada para as strings dentro de cada struct Game
    for (int i = 0; i < totalLidos; i++) liberarMemoriaJogo(&vetorJogosGlobais[i]);
    free(vetorJogosGlobais);
    
    return 0;
}