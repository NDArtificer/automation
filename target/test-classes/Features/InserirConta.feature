# language: pt
Funcionalidade: Validar as telas da aplicação SrBarriga

  Contexto: 
    Dado que tenha acessado a aplicacao
    Quando informo o usuario "artificer@hotmail.com"
    E a senha "a"
    E seleciono entrar
    Entao visualizo a pagina inicial
    Quando seleciono Contas
    E seleciono adicionar

  Esquema do Cenario: Validar regras do cadastro de contas
    E informo a conta "<conta>"
    E seleciono salvar
    Entao recebo a mensagem "<mensagem>"

    @Aula-30
    Exemplos: 
      | conta          | mensagem                      |
      | Conta de Teste | Conta adicionada com sucesso! |

    @Aula-31
    Exemplos: 
      | conta | mensagem                |
      |       | Informe o nome da conta |

    @Aula-32
    Exemplos: 
      | conta            | mensagem                           |
      | Conta mesmo nome | Já existe uma conta com esse nome! |
