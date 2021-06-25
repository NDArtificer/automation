#language: pt
Funcionalidade: Como um usuario Eu quero cadastrar alugueis de filmes Para contolar preços e datas de entregas

  #@Aula-20
  #Cenario: Alugar Filme com sucesso
  #Dado que um filme com 2 unidades
  #E que o preco seja R$3
  #Quando alugar o objeto
  #Entao preco cobrado sera R$3
  #E a data de entrega sera em 1 dia
  #E o estoque do filme sera 1 unidade
  #
  #@Aula-21
  #Cenario: Não alugar filme sem estoque
  #Dado que um filme com 0 unidades
  #Quando alugar o objeto
  #Entao nao sera possivel por falta de estoque
  #E o estoque do filme sera 0 unidade
  #
  #@Aula-22A
  #Cenario: Alugar com condicoes especiais para categoria extendida
  #Dado que um filme com 2 unidades
  #E que o preco seja R$4
  #E que o tipo de aluguel seja extendido
  #Quando alugar o objeto
  #Entao preco cobrado sera R$8
  #E a data de entrega sera em 3 dias
  #E a pontuacao recebida sera 2 pontos
  #
  #@Aula-22B
  #Cenario: Alugar com condicoes especiais para categoria comum
  #Dado que um filme com 2 unidades
  #E que o preco seja R$2
  #E que o tipo de aluguel seja comum
  #Quando alugar o objeto
  #Entao preco cobrado sera R$4
  #E a data de entrega sera em 1 dia
  #E a pontuacao recebida sera 1 pontos
  @ScemaScenarie
  Esquema do Cenario: deve dar condições para o tipo do aluguel
    Dado que um filme com 2 unidades
    E que o preco seja R$<preco>
    E que o tipo de aluguel seja <tipo>
    Quando alugar o objeto
    Entao preco cobrado sera R$<valor>
    E a data de entrega sera em <qtdDias> dias
    E a pontuacao recebida sera <pontuacao> pontos

    @Aula-23A
    Exemplos: 
      | preco | tipo      | valor | qtdDias | pontuacao |
      |     4 | extendido |     8 |       3 |         2 |

    @Aula-23B
    Exemplos: 
      | preco | tipo  | valor | qtdDias | pontuacao |
      |     4 | comum |     4 |       1 |         1 |

    @Aula-23C
    Exemplos: 
      | preco | tipo    | valor | qtdDias | pontuacao |
      |     5 | semanal |    15 |       7 |         3 |

  @Aula-24A
  Cenario: Alugar com condicoes especiais para categoria extendida
    Dado que um filme A
      | estoque | 2 |
      | preco   | 3 |
    Quando alugar o objeto
    Entao preco cobrado sera R$3
    E a data de entrega sera em 1 dias
    E o estoque do filme sera 1 unidade

  @Aula-24B
  Cenario: Alugar com condicoes especiais para categoria extendida
    Dado que um filme
      | estoque |     2 |
      | preco   |     3 |
      | tipo    | comum |
    Quando alugar o objeto
    Entao preco cobrado sera R$3
    E a data de entrega sera em 1 dias
    E a pontuacao recebida sera 1 pontos
    E o estoque do filme sera 1 unidade
