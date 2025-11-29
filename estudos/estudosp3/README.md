# 🌳 Árvore Alvinegra 
Árvore criada para resolver problemas da 234 e facilitar a sua compreensão.  
Os nós podem ter duas cores (true or false), que serão um booleano. O nosso professor, 
Max, utiliza preto e branco para facilitar a compreensão. Mas a árvore realmente
conhecida é a Rubronegra (vermelho e preto).   

> ⚠️ Observação importante  
> Na AN utilizada nesta disciplina, **preto = vermelho da Rubro-Negra**  
> e **branco = preto da Rubro-Negra**.  

## ✏️ Observações:
O bit de cor da alvinegra não interfere em nada, apenas serve para facilitar o balanceamento
Uma árvore alvinegra está desbalanceada quando temos dois elementos pretos seguidos  

- Elementos pretos: gêmeos na 234 (mesmo nó)
- Elementos brancos: não gêmeos

Dois pretos seguidos significa uma tendência na inserção e que o grupo de gêmeos estourou  
**obs:** A raiz sempre é branca  
Os gêmeos podem ser representados tanto pela coloração de suas arestas quanto pela de seus nós.  

![introducao](image.png)

## 🔧 Funções contidas no arquivo:
- Caminhar central
- Caminhar pré
- Caminhar pós
- Inserir (iterativo para os 3 primeiros elementos)
- Inserir (recursivo para os demais elementos)
- Pesquisar
- Funções de balanceamento

## 🛠️ Como executar ou testar o código?
1. Clone o repositório
2. Compile o arquivo com javac TestaAlvinegra.java
3. Execute o arquivo com java TestaAlvinegra
4. Utilize o menu na main para poder executar as funções

## ✏️ Resumo e explicações
A seguir, você encontrará o código da Alvinegra modularizado e otimizado, para facilitar 
a leitura e interpretação.  
O arquivo contém explicações em forma de texto e comentários, assim como imagens para 
visualizar os nós e estruturas em cada trecho.  

### 🚧 Dica extra
Recomenda-se baixar a extensão **Better Comments** caso for analisar o código.  
Ela colore os comentários, facilitando a compreensão

---

# 🧠 Nó da Alvinegra
O nó da AN funciona como os outros, porém tem um bit de cor adicionado.  
No seu construtor, o bit sempre começa com **true** (preto), considerando assim os novos
nós adicionados são gêmeos do pai

```java
//! Classe Nó Alvinegra
class NoAN {
    public int elemento;
    public NoAN esq, dir;
    public boolean cor; //true = preto, false = branco

    //construtor
    public NoAN(int elemento){
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
        this.cor = true; 
    }
}
```
---

# 🌳 Árvore Alvinegra
A classe da Árvore Alvinegra começa apenas com a sua raiz, que é nula inicialmente.

```java
//! Classe árvore alvinegra
class Alvinegra {
    public NoAN raiz;

    //construtor
    public Alvinegra(){
        this.raiz = null;
    }
}
```

A partir de agora, veremos as funções que podem estar contidas na sua classe  
## 🚶‍➡️ Funções de caminhar
Os "caminhar" são utilizados para percorrer a árvore, e permanecem os mesmos das outras
árvores estudadas. São eles: 

### Caminhar central
Lista todos os elementos em ordem crescente

```java
public void caminharcentral(NoAN i){
    if(i == null) return;
    caminharcentral(i.esq);
    System.out.println(i.elemento); //ou outra função
    caminharcentral(i.dir);
}
```

### Caminhar pré
Lista primeiro o nó e depois seus filhos

```java
public void caminharpre(NoAN i){
    if(i == null) return;
    System.out.println(i.elemento); //ou outra função
    caminharpre(i.esq);
    caminharpre(i.dir);
}
```

### Caminhar pós
Lista primeiro os filhos e por último o nó

```java
public void caminharpos(NoAN i){
    if(i == null) return;
    caminharpos(i.esq);
    caminharpos(i.dir);
    System.out.println(i.elemento); //ou outra função
}
```

---

## 🔍 Pesquisa
Retorna true se o elemento estiver na árvore e false se não estiver

```java
public boolean pesquisar(int x, NoAN i){
    if(i == null){
        //procurou na árvore toda e não achou
        return false;
    }

    else if(x < i.elemento) return pesquisar(x, i.esq);
    else if(x > i.elemento) return pesquisar(x, i.dir);

    else return true;
}
```

---

## ➕ Função de inserir iterativa
### Inserir os 3 primeiros elementos
Nessa função, implementamos os 3 primeiros nós de forma manual, porque a lógica recursiva do inserir só funciona quando você já tem pelo menos uma subárvore inteira para percorrer.  
Se não for um desses casos, chamamos o inserir recursivo normal. 

No *bloco abaixo*, tratamos dois casos. 
- Caso 1: 0 elementos -> apenas insere na raiz
- Caso 2: 1 elemento -> insere na direita ou na esquerda da raiz  
Esses dois primeiros casos acontecem normalmente

```java

public void inserir(int x){
        //zero elementos
        if(raiz == null){
            raiz = new NoAN(x);
        }

        //apenas um elemento
        else if(raiz.esq == null && raiz.dir == null){

            if(x < raiz.elemento){ //inserir na esquerda
                raiz.esq = new NoAN(x);
            }

            else if(x > raiz.elemento){ //inserir na direita
                raiz.dir = new NoAN(x);
            }

            else System.out.println("Item inválido!");
        } //continua

```

Para o próximo caso, temos 2 elementos na árvore e queremos inserir o terceiro.   
Assim, temos 6 possibilidade de locais de inserção  
Vamos tratar primeiro os casos de inserção onde já temos a raiz e um elemento à sua direita. Nela, o elemento pode
estar contido em uma das três posições a seguir.  

```java

//dois elementos sendo raiz e direita
        else if(raiz.esq == null){
            if(x < raiz.elemento){
                raiz.esq = new NoAN(x);
            }

            else if(x < raiz.dir.elemento){
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = x;
            }

            else {
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = raiz.dir.elemento; 
                raiz.dir.elemento = x;
            }

            raiz.dir.cor = false;
            raiz.esq.cor = false;
        }

```

Agora, caso já tenhamos uma raiz e um elemento à sua esquerda, temos outros três casos
de inserção, espelhados aos primeiros. 

```java

//dois elementos sendo raiz e esquerda
        else if(raiz.dir == null){
            if(x > raiz.elemento){
                raiz.dir = new NoAN(x);
            }

            else if(x > raiz.esq.elemento){
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = x;
            }

            else {
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = x;
            }

            raiz.dir.cor = false; //branco
            raiz.esq.cor = false; //branco
        }

```

Caso não tenha entrado em nenhuma das opções anteriores, significa que: 
- Temos mais que 0, 1 ou 2 elementos
- Podemos usar o inserir recursivo

```java

//se não entrou em nenhum dos anteriores, a árvore tem 3+ elementos
        else {
            System.out.println("Árvore com 3 ou mais elementos. Inserindo normalmente...");
            inserir(x, null, null, null, raiz);
        }

        raiz.cor = false;
    }

```

---

## ➕ Função de inserir recursiva
### Inserir os demais elementos
A função de inserir recursiva caminha com 4 ponteiros (bisavô, avô, pai e atual), 
para que seja possível identificar, no momento exato da descida, qualquer violação das regras da árvore e aplicar a rotação correta no nó certo sem precisar voltar a recursão.  

Nessa função, temos dois casos.  
Caso 1: i chegou em **null**

Como o i é **null**, fazemos a conferência da inserção no seu pai, para sabermos se a 
inserção acontecerá na esquerda ou na direita. 
**Obs:**    
1. Após a inserção, é importante conferir a cor do pai. Caso ela seja preta, 
chamamos a função de balancear, que veremos abaixo. 
2. Nesse caso, não precisamos conferir se o elemento é igual, pois isso já foi
confirmado no if tradicional. 

```java
//função recursiva de inserir elemento com 4 ponteiros
    private void inserir(int x, NoAN bisa, NoAN avo, NoAN pai, NoAN i){
        if(i == null){
            if(x < pai.elemento){
                pai.esq = new NoAN(x);
                i = pai.esq;
            }
            else{
                pai.dir = new NoAN(x);
                i = pai.dir;
            }

            if(pai.cor == true){
                balancear(bisa, avo, pai, i);
            }
        }

```

Caso 2: i ainda não é **null**

Nesse caso, estamos apenas percorrendo elementos da árvore. Ao percorrer, utilizamos
a **lógica de fragmentação na descida da 234**, e por isso, conferimos sempre se o nó
é do tipo 4.  
Se for, trocamos as cores e conferimos seu pai, como é mostrado abaixo: 

```java

        else {
            if(isNo4(i) == true){
                i.cor = true;
                i.esq.cor = false;
                i.dir.cor = false;

                if(pai != null && pai.cor == true){
                    balancear(bisa, avo, pai, i);
                }
            }

```

O código para conferir se é um nó do tipo 4 será mostrado no próximo capítulo. 


Após essa conferência, caminhamos tradicionalmente.

```java
        if(x < i.elemento) inserir(x, avo, pai, i, i.esq);
            else if(x > i.elemento) inserir(x, avo, pai, i, i.dir);
            else System.out.println("erro");
        }
    }

```

---

## ⚡ Função de conferir se um nó é do tipo 4
As árvores 234 e AN tem considerações diferentes para nós do tipo 4. Veja a seguir:  

**Para a 234**  
Um nó é considerado do tipo 4 quando ele já possui três elementos armazenados internamente (por exemplo: [a | b | c]) e, por isso, está “cheio”, exigindo divisão caso um novo elemento precise ser inserido.    
**Para a AN**  
Na AN, um nó é do tipo 4 se ele for branco e seus dois filhos forem pretos, indicando que 
são gêmeos do pai e logo, se formos adicionar mais um nó, não haveria espaço e exigiria
uma fragmentação. 

Nas árvores alvinegras, essa conferência é feita assim: 

```java

//função que verifica se um nó é do tipo 4
public boolean isNo4(NoAN i){
    if(i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true){
        return true;
    }
    else return false;
}

```

---

## ⚖️ Função de balancear

A função de balancear apenas acontecerá se a cor do pai for **true**.  
Assim, temos dois casos:    

Caso 1: Pai é maior que Avô    
Nesse caso, a árvore está manca para a **direita** e logo, a rotação mais importante
acontecerá na **esquerda**.    
Se o elemento atual for maior que o pai, a rotação é **Simples à esquerda** (ex 1).   
Se o elemento atual for menor que o pai, a rotação é **Dupla Dir-Esq** (ex 2).  

```java

//função de balancear
private void balancear(NoAN bisa, NoAN avo, NoAN pai, NoAN i) {
    if (pai.cor == true) {

        if (pai.elemento > avo.elemento) {

            //rotação simples à esquerda
            if (i.elemento > pai.elemento) {
                avo = rotacaoEsq(avo);
            }

            //rotação dupla dir-esq
            else {
                avo = rotacaoDirEsq(avo);
            }
        } //continua
```

Caso 2: Pai é menor que Avô    
Nesse caso, a árvore está manca para a **esquerda** e logo, a rotação mais importante
acontecerá na **direita**.    
Se o elemento atual for menor que o pai, a rotação é **Simples à direita** (ex 3).   
Se o elemento atual for maior que o pai, a rotação é **Dupla Esq-Dir** (ex 4).  

```java

else {

    //rotação simples à direita
    if (i.elemento < pai.elemento) {
        avo = rotacaoDir(avo);
    }

    //rotação dupla esq-dir
    else {
        avo = rotacaoEsqDir(avo);
    }
} //continua

```

Após os dois casos, conferimos as cores

```java

if (bisa == null) {
                raiz = avo;
            }
            else if (avo.elemento < bisa.elemento) {
                bisa.esq = avo;
            }
            else {
                bisa.dir = avo;
            }

            avo.cor = false;

            if (avo.esq != null) avo.esq.cor = true;
            if (avo.dir != null) avo.dir.cor = true;
        }
    }

```

---

## 🔄 Rotações

As quatro rotações servem para corrigir qualquer configuração estrutural inválida que surja durante a inserção — especialmente quando a árvore apresenta dois brancos consecutivos ou um alinhamento que viole as propriedades da AN — restaurando a forma canônica da árvore e garantindo balanceamento.  

A seguir, vemos um pouco sobre elas: 


### Rotação simples à esquerda  
Acontece quando a árvore está totalmente manca para a direita  

```java

//Rotação simples à esquerda 
NoAN rotacaoEsq(NoAN i){
    NoAN j = i.dir;
    NoAN k = j.esq;

    j.esq = i;
    i.dir = k;

    return j;
}

```

### Rotação dupla Dir-Esq  
Acontece quando a árvore está manca para a direita e depois para a esquerda

```java

//Rotação dupla Direita-Esquerda(>)
NoAN rotacaoDirEsq(NoAN i){
    i.dir = rotacaoDir(i.dir);
    return rotacaoEsq(i);
}

```

### Rotação simples à direita
Acontece quando a árvore está totalmente manca para a esquerda 

```java

//Rotação simples à direita
NoAN rotacaoDir(NoAN i){
    NoAN j = i.esq;
    NoAN k = j.dir;

    j.dir = i;
    i.esq = k;

    return j;
}

```

### Rotação dupla Esq-Dir  
Acontece quando a árvore está manca para a esquerda e depois para a direita

```java

//Rotação dupla Esquerda-Direita(<)
NoAN rotacaoEsqDir(NoAN i){
    i.esq = rotacaoEsq(i.esq);
    return rotacaoDir(i);
}

```

---

## 📦 Conclusão
A Árvore Alvinegra (AN) é uma árvore de busca balanceada que representa a lógica das árvores **2-3-4** usando apenas **cores nas arestas** (preto = normal, branco = promoção).  
Os **três primeiros nós** são tratados manualmente, pois ainda não existe estrutura suficiente para aplicar a inserção recursiva sem criar exceções.

A partir do quarto elemento, a inserção usa quatro ponteiros **(bisavô, avô, pai e atual)** para identificar violações durante a descida e corrigir no instante certo.  
Um nó é considerado **tipo 4** quando o **pai é branco e seus dois filhos são pretos**, correspondendo a um 4-nó que deve ser dividido por recoloração.

Se surgir um tipo 4 ou duas brancas consecutivas, a árvore é ajustada com uma das **quatro rotações** (simples ou duplas), restaurando a estrutura correta.  
Com essas regras, a AN mantém as operações sempre em **O(log n)** e evita desbalanceamentos.

















