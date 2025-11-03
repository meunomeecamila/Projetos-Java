/*
============================================================
Exercício: Feira de Frutas
============================================================

Em uma feira, há N barracas vendendo frutas.
Cada barraca tem:
 - o nome de uma fruta (String)
 - a quantidade disponível (int)

Durante o dia, M clientes fazem compras.
Cada cliente informa:
 - o nome da fruta desejada
 - a quantidade que deseja comprar

Se a barraca tiver quantidade suficiente, a compra é feita
e o estoque é reduzido. Caso contrário, a compra NÃO é atendida.
Se a fruta pedida não existir, também conta como falha.

O programa deve informar quantas compras não puderam
ser atendidas (por falta de estoque ou fruta inexistente).

------------------------------------------------------------
ENTRADA:
N
<fruta1> <quantidade1>
<fruta2> <quantidade2>
...
M
<fruta_pedida1> <quantidade_pedida1>
<fruta_pedida2> <quantidade_pedida2>
...

------------------------------------------------------------
SAÍDA:
Um número inteiro representando o total de falhas.

------------------------------------------------------------
EXEMPLO:
Entrada:
4
banana 10
maca 5
uva 7
laranja 3
4
banana 8
uva 10
pera 3
maca 2

Saída:
2
============================================================
*/


import java.util.*;
public class Frutas {

    static class Barraca {
        String fruta; 
        int quantidade;
    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n; //barracas
        n = sc.nextInt();

        //criar um vetor de barracas
        Barraca []tent = new Barraca[n];

        //guardar as barracas e quantidades
        for(int i=0; i<n; i++){
            tent[i] = new Barraca();

            String frut = sc.next();
            int quant = sc.nextInt();

            tent[i].fruta = frut;
            tent[i].quantidade = quant;
        }

        int m; //clientes
        m = sc.nextInt();

        int falhas = 0; //compras que não foram atendidas

        for(int i=0; i<m; i++){
            String pedido = sc.next();
            int qtd = sc.nextInt();

            int achou = 0;
            
            //pesquisar nas barracas
            for(int j=0; j<n; j++){
                if(tent[j].fruta.equals(pedido)){
                    achou = 1;

                    if(tent[j].quantidade >= qtd){
                        tent[j].quantidade -= qtd; //cliente comprou
                    }

                    else achou = 0;
                    break;
                }
            }

            //se no final não tiver achado, falhas ++
            if(achou == 0) falhas++;
        }

        System.out.println(falhas);
        sc.close();
    }
}
