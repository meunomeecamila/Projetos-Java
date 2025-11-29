//ESTRUTURA DOIDONA ==================
//Fases da estrutura
//*Fase 1 (T1): Hash normal com função de mapeamento 
//*Fase 2 (T2): Se não couber, faz % 3 que vai dar 0,1 ou 2. 
//   caso seja 0, vai para a T3: Hash normal com área de reserva em Árvore 1
//   caso seja 1, vai para uma lista flexível
//   caso seja 2, vai para Árvore 2