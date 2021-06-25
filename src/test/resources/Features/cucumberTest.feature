# language: pt

Funcionalidade: Aprender cucumber
	Como um aluno 
	eu quero apredender cucumber
	para que eu possa automatizar criterios de aceitacao


Cenario: Deve excutar especifica��o
	Dado que eu criei o arquivo corretamente
	Quando executa-lo
	Entao a especificacaco deve finalizar com sucesso
	
	
Cenario: Deve incrementar contaodor
	Dado que o valor do contador seja 15
	Quando eu incrementar o valor 3
	Entao o valor do contador sera 18
	
Cenario: Deve incrementar contaodor
	Dado que o valor do contador seja 135
	Quando eu incrementar o valor 25
	Entao o valor do contador sera 160
	
Cenario: calcula o atraso no prazo de entrega
	Dado que o prazo tem como data 05/04/2020
	Quando a entrega atrasar em 2 dias
	Entao a entrega sera efetuada em 07/04/2020
	
Cenario: calcula o atraso no prazo de entrega
	Dado que o prazo tem como data 05/04/2020
	Quando a entrega atrasar em 2 meses
	Entao a entrega sera efetuada em 05/06/2020
	
	
Cenario: Criar steps genericos para estes passos
	Dado que o ticket é AF345
	E que o valor da passagem é R$ 230,45
	E que o nome do passageiro é "Fulano da Silva"
	E que o telefone do passageiro é 9999-9999
	Quando criar estes steps
	Entao o teste ira funcionar

Cenario: Reaproveitar os steps "Dado" do cenario anterior
	Dado que o ticket é AB167
	E que o ticket especial é AB167
	E que o valor da passagem é R$ 1230,45
	E que o nome do passageiro é "Denilson Nascimento"
	E que o telefone do passageiro é 9898-0000
	
Cenario: Deve negar todos os steps "Dado" dos cenarios anteriores
	Dado que o ticket é CD123
	Dado que o ticket é AG1234
	Dado que o valor da passagem é R$ 1.1230,45
	Dado que o nome do passageiro é "Joãozinho Sousa Matos"
	Dado que o telefone do passageiro é 1234-0000
	Dado que o telefone do passageiro é  234-0000	
	