-- =========================================
-- Arquivo: Funcoes.hs
-- Trabalho de Haskell - Exemplos práticos
-- =========================================

-- 1. Dobro de um número
-- Recebe um número e retorna o dobro
dobro :: Int -> Int
dobro x = x * 2


-- 2. Verificar se um número é par
-- Retorna True se for par, False caso contrário
ehPar :: Int -> Bool
ehPar n = mod n 2 == 0


-- 3. Fatorial com recursão
-- Caso base: 0! = 1
-- Caso recursivo: n! = n * (n-1)!
fatorial :: Int -> Int
fatorial 0 = 1
fatorial n = n * fatorial (n - 1)


-- 4. Soma de elementos de uma lista
-- Caso base: lista vazia = 0
-- Caso recursivo: soma cabeça + resto
somaLista :: [Int] -> Int
somaLista [] = 0
somaLista (x:xs) = x + somaLista xs


-- 5. Filtrar nomes com mais de 5 letras
-- Usa função de ordem superior (filter)
filtrarNomes :: [String] -> [String]
filtrarNomes nomes = filter (\n -> length n > 5) nomes


-- 6. QuickSort (ordenar lista)
-- Divide a lista em menores e maiores que o pivô
quicksort :: [Int] -> [Int]
quicksort [] = []
quicksort (pivo:resto) =
quicksort [x | x <- resto, x <= pivo] ++
[pivo] ++
quicksort [x | x <- resto, x > pivo]


-- 7. Fibonacci infinito (Lazy Evaluation)
-- Lista infinita calculada sob demanda
fibs :: [Int]
fibs = 0 : 1 : zipWith (+) fibs (drop 1 fibs)


-- 8. Simulação de SELECT (banco de dados)
-- Lista de usuários (id, nome)
usuarios :: [(Int, String)]
usuarios = [(1, "Guilherme"), (2, "Ane"), (3, "Maria"), (4, "Camila")]

-- Busca usuários por id
buscarId :: Int -> [(Int, String)]
buscarId idLista = filter (\(i, nome) -> i == idLista) usuarios


-- 9. Função de função (composição)
-- Combina duas funções usando (.)
somarCinco :: Int -> Int
somarCinco x = x + 5

dobroMaisCinco :: Int -> Int
dobroMaisCinco = dobro . somarCinco
-- Exemplo: dobroMaisCinco 3 = (3 + 5) * 2 = 16


-- 10. Função currificada
-- Recebe um argumento por vez
soma :: Int -> Int -> Int
soma x y = x + y

-- Aplicação parcial (fixa um argumento)
somar10 :: Int -> Int
somar10 = soma 10
-- Exemplo: somar10 5 = 15


-- =========================================
-- Função principal (para testar tudo)
-- =========================================

main :: IO ()
main = do
print (dobro 5)
print (ehPar 4)
print (fatorial 5)
print (somaLista [1,2,3,4])
print (filtrarNomes ["Ana", "Camila", "Joao", "Fernanda"])
print (quicksort [5,3,8,1,2])
print (take 10 fibs)
print (buscarId 2)
print (dobroMaisCinco 3)
print (somar10 5)