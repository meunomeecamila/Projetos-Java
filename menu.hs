-- =========================================
-- MENU INTERATIVO - TRABALHO HASKELL
-- =========================================

-- 1. Fatorial
fatorial :: Int -> Int
fatorial 0 = 1
fatorial n = n * fatorial (n - 1)

-- 2. QuickSort
quicksort :: [Int] -> [Int]
quicksort [] = []
quicksort (pivo:resto) =
    quicksort [x | x <- resto, x <= pivo] ++
    [pivo] ++
    quicksort [x | x <- resto, x > pivo]

-- 3. Função de função (composição)
dobro :: Int -> Int
dobro x = x * 2

somarCinco :: Int -> Int
somarCinco x = x + 5

dobroMaisCinco :: Int -> Int
dobroMaisCinco = dobro . somarCinco

-- 4. Currificada
soma :: Int -> Int -> Int
soma x y = x + y

somar10 :: Int -> Int
somar10 = soma 10


-- =========================================
-- MENUS
-- =========================================

main :: IO ()
main = do
    putStrLn "===== MENU HASKELL ====="
    putStrLn "1 - Fatorial"
    putStrLn "2 - QuickSort"
    putStrLn "3 - Funcao de funcao (dobro + 5)"
    putStrLn "4 - Funcao currificada (somar 10)"
    putStrLn "Escolha uma opcao:"
    
    opcao <- getLine

    case opcao of
        "1" -> menuFatorial
        "2" -> menuQuick
        "3" -> menuFuncao
        "4" -> menuCurrificada
        _   -> putStrLn "Opcao invalida"


-- -----------------------------------------
-- SUBMENU FATORIAL
-- -----------------------------------------
menuFatorial :: IO ()
menuFatorial = do
    putStrLn "Digite um numero:"
    entrada <- getLine
    let n = read entrada :: Int
    putStrLn ("Resultado: " ++ show (fatorial n))


-- -----------------------------------------
-- SUBMENU QUICKSORT
-- -----------------------------------------
menuQuick :: IO ()
menuQuick = do
    putStrLn "Digite uma lista de numeros (ex: [5,3,8,1]):"
    entrada <- getLine
    let lista = read entrada :: [Int]
    putStrLn ("Ordenado: " ++ show (quicksort lista))


-- -----------------------------------------
-- SUBMENU FUNCAO DE FUNCAO
-- -----------------------------------------
menuFuncao :: IO ()
menuFuncao = do
    putStrLn "Digite um numero:"
    entrada <- getLine
    let n = read entrada :: Int
    putStrLn ("Resultado (dobro + 5): " ++ show (dobroMaisCinco n))


-- -----------------------------------------
-- SUBMENU CURRIFICADA
-- -----------------------------------------
menuCurrificada :: IO ()
menuCurrificada = do
    putStrLn "Digite um numero:"
    entrada <- getLine
    let n = read entrada :: Int
    putStrLn ("Resultado (somar 10): " ++ show (somar10 n))