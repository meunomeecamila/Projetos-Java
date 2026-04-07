-- =========================================
-- Arquivo: Funcoes.hs
-- Trabalho de Haskell - Exemplos práticos
-- =========================================

-- Dobro de um número
-- Recebe um número e retorna o dobro
dobro :: Int -> Int
dobro x = x * 2


-- Verificar se um número é par
-- Retorna True se for par, False caso contrário
ehPar :: Int -> Bool
ehPar n = mod n 2 == 0


-- Soma de elementos de uma lista
-- Caso base: lista vazia = 0
-- Caso recursivo: soma cabeça + resto
somaLista :: [Int] -> Int
somaLista [] = 0
somaLista (x:xs) = x + somaLista xs


-- Filtrar nomes com mais de 5 letras
-- Usa função de ordem superior (filter)
filtrarNomes :: [String] -> [String]
filtrarNomes nomes = filter (\n -> length n > 5) nomes

-- Fibonacci infinito (Lazy Evaluation)
-- Lista infinita calculada sob demanda
fibs :: [Int]
fibs = 0 : 1 : zipWith (+) fibs (drop 1 fibs)


-- Simulação de SELECT (banco de dados)
-- Lista de usuários (id, nome)
usuarios :: [(Int, String)]
usuarios = [(1, "Guilherme"), (2, "Ane"), (3, "Maria"), (4, "Camila")]

-- Busca usuários por id
buscarId :: Int -> [(Int, String)]
buscarId idLista = filter (\(i, nome) -> i == idLista) usuarios


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