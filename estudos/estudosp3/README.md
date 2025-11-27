# Árvore Alvinegra 
Árvore criada para resolver problemas da 234 e facilitar a sua compreensão.  
Os nós podem ter duas cores (true or false), que serão um booleano. O nosso professor, 
Max, utiliza preto e branco para facilitar a compreensão. Mas a árvore realmente
conhecida é a Rubronegra (vermelho e preto).   

## Observações:
O bit de cor da alvinegra não interfere em nada, apenas serve para facilitar o balanceamento
Uma árvore alvinegra está desbalanceada quando temos dois elementos pretos seguidos  

- Elementos pretos: gêmeos na 234 (mesmo nó)
- Elementos brancos: não gêmeos

Dois pretos seguidos significa uma tendência na inserção e que o grupo de gêmeos estourou  
*obs:* A raiz sempre é branca  
Os gêmeos podem ser representados tanto pela coloração de suas arestas quanto pela de seus nós.  

## Funções contidas no arquivo:
- Caminhar central
- Caminhar pré
- Caminhar pós
- Inserir (iterativo para os 3 primeiros elementos)
- Inserir (recursivo para os demais elementos)
- Pesquisar
- Funções de balanceamento

## Como executar ou testar o código?
1. Clone o repositório
2. Compile o arquivo com javac TestaAlvinegra.java
3. Execute o arquivo com java TestaAlvinegra
4. Utilize o menu na main para poder executar as funções

## Resumo e explicações
A seguir, você encontrará o código da Alvinegra modularizado e otimizado, para facilitar 
a leitura e interpretação.  
O arquivo contém explicações em forma de texto e comentários, assim como imagens para 
visualizar os nós e estruturas em cada trecho.  

### Dica extra
Recomenda-se baixar a extensão Better Comments caso for analisar o código.

# Nó da Alvinegra
