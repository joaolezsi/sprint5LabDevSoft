### 3.3.2 Processo 2 – Gerenciamento de Recicláveis


![Processo 2 - Gerenciamento de Reciclaveis](images/Gerenciamento-de-reciclaveis.png "Modelo BPMN do Processo 2 (Cadastro de Recicláveis).")

## Descrição
O processo de **Gerenciamento de Recicláveis** envolve o fluxo completo de registro, atualização e exclusão de materiais recicláveis na plataforma. O ponto de coleta deve preencher um formulário de cadastro, informando o nome do material, o tipo de material e o preço por kg. Após a validação dos dados, o material é registrado no sistema e fica disponível para compra por empresas interessadas.

## Tipos de Dados Utilizados
Os seguintes tipos de dados serão aplicados nos campos do processo de cadastro:

- **Caixa de Texto**: Campo de múltiplas linhas de texto.
- **Número**: Campo para valores numéricos inteiros ou decimais.
- **Seleção**: Campo para selecionar uma opção predefinida, como o tipo de material reciclável.
- **Botão**: Elemento interativo usado para enviar formulários, confirmar decisões ou realizar uma ação dentro da interface, como "Confirmar Recebimento" ou "Cancelar".


## Cadastrar Material Reciclável

| Campo              | Tipo           | Restrições               | Valor default |
|--------------------|----------------|--------------------------|---------------|
| Nome do Material   | Caixa de Texto | Mínimo de 3 caracteres   | ---           |
| Descrição          | Caixa de Texto | Mínimo de 10 caracteres  | ---           |
| Preço por kg       | Número         | Valor maior que 0        | ---           |
| Tipo de Material   | Seleção        | Seleção obrigatória      | ---           |

## Comandos

| Comando    | Destino                         | Tipo   |
|------------|---------------------------------|--------|
| Cadastrar  | Salvar informações do material  | Botão  |
| Cancelar   | Cancelar operação e voltar      | Botão  |

## Atualizar Material Reciclável

Permite modificar as informações já cadastradas para um material específico. Ao selecionar "Editar", o usuário verá um formulário idêntico ao de cadastro, permitindo a atualização dos dados do material.

| Comando       | Destino                               | Tipo   |
|---------------|---------------------------------------|--------|
| Editar        | Carregar dados do material para edição | Botão  |
| Atualizar     | Salvar atualizações                   | Botão  |
| Cancelar      | Cancelar edição e manter dados atuais | Botão  |

## Deletar Material Reciclável

Permite remover um material do sistema.

| Comando      | Destino                               | Tipo   |
|--------------|---------------------------------------|--------|
| Deletar      | Remover material do cadastro          | Botão  |
| Excluir    | Confirmar exclusão do material        | Botão  |
| Cancelar     | Cancelar exclusão e manter o material | Botão  |



