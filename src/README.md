# Código do projeto


[Código do front-end](../src/front) -- repositório do código do front-end

[Código do back-end](../src/back)  -- repositório do código do back-end


# Especificações Cadastro

Para acessar os inputs e extrair os dados, siga este mapeamento:

Para facilitar a referência aos inputs, foram adicionados IDs a cada um deles:

Nome: id="input_nome"

E-mail: id="input_email"

CNPJ: id="input_cnpj"

Cidade: id="input_cidade"

Bairro: id="input_bairro"

Número: id="input_numero"

CEP: id="input_cep"

Senha: id="input_senha"

Confirmar senha: id="input_confirmarSenha"


Como dunciona a seleção de tipo:
A seleção de tipo é feita a partir de um chekbox com display none escondido na tela quando é selecionado 
a opção empresa o checkbox de id= "checkbox_empresa" e marcado, e quando é selecionada a opção Ponto de Coleta
o checkbox de id="checkbox_pontoColeta" é marcado.

Sugestão para o backend:

Faça uma função que veja qual oção esta marcada, e dependendo da que estiver, pegue o value(do html) e armazene
em uma string, apos isso faça uma função de comparação, e você podera adicionar no cadastro o tipo deles.

value do checkbox empresa: value="empresa"
value do checkbox ponto de coleta="ponto de coleta"

Quando implementar a função lembre de colocar ela para ser efetuada no botão de salvar do modal de seleção de tipo de cadastro,
alem de fechar o modal quando o botão for clickado.