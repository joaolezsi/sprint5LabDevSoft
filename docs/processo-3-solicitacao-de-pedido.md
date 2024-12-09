### 3.3.3 Processo 3 – Solicitação de Pedido


![Processo 3 - Solicitação de Pediddo](images/SolicitaçãoDePedido.png "Modelo BPMN do Processo 3 (Solicitação de Pediddo).")

### Descrição Geral
O processo de Solicitação de Pedido descreve a sequência de ações entre uma empresa compradora e uma empresa fornecedora de materiais recicláveis. O fluxo começa na seleção do material disponível, segue pela montagem e envio do pedido, recebimento do pedido, até a decisão de atendimento e ajuste, caso necessário.

---

### **Tipos de Dados Utilizados**

Os seguintes tipos de dados serão aplicados nos campos do processo de **Solicitação de Pedido**:

- **Caixa de Texto**: Campo de uma única linha de texto, usado para entradas curtas como nomes, CNPJ e endereços.
- **Área de Texto**: Campo de várias linhas, permitindo descrições detalhadas, como observações ou informações adicionais.
- **Número**: Campo para valores numéricos inteiros ou decimais, limitados a entradas positivas ou específicas, como quantidade desejada ou preço por kg.
- **Seleção Única**: Campo com várias opções, das quais somente uma pode ser selecionada, utilizado para decisões binárias ou escolha de opções (pode ser um **radio button** ou **combobox**).
- **Data**: Campo para seleção de uma data válida, utilizado para prazos ou registros de datas, com restrições como datas futuras ou não passadas.
- **Link**: Campo para um hiperlink, usado para apontar para um documento, página da web ou formulário específico, permitindo ações como redirecionamentos.
- **Botão**: Elemento interativo usado para enviar formulários, confirmar decisões ou realizar uma ação dentro da interface, como "Confirmar Envio" ou "Cancelar".

---
### Detalhamento das Atividades

### **Selecionar Material**

| **Campo**              | **Tipo**         | **Restrições**               | **Valor Padrão** |
|------------------------|------------------|------------------------------|------------------|
| Nome do Material        | Caixa de Texto   | Mínimo de 3 caracteres        | ---              |
| Preço por kg            | Número           | Valor maior que 0             | ---              |
| Descrição               | Área de Texto    | Opcional                      | ---              |
| Quantidade Disponível   | Número           | Somente valores positivos     | ---              |

#### Comandos

| **Comandos**           | **Destino**                       | **Tipo**   |
|------------------------|-----------------------------------|------------|
| Solicitar Pedido     | Encaminha para a tela montar Pedido                    | botão      |


---

### **Montar Pedido e Enviar Pedido**

| **Campo**              | **Tipo**         | **Restrições**               | **Valor Padrão** |
|------------------------|------------------|------------------------------|------------------|
| Empresa Compradora      | Caixa de Texto   | Mínimo de 3 caracteres        | ---              |
| Quantidade Desejada     | Número           | Somente valores positivos     | ---              |
| CNPJ da Empresa Compradora | CNPJ          | Formato válido de CNPJ        | ---              |
| Observações             | Área de Texto    | Opcional                      | ---              |
---
| **Campo**              | **Tipo**         | **Restrições**               | **Valor Padrão** |
|------------------------|------------------|------------------------------|------------------|
| Material Selecionado    | Caixa de Texto   | Preenchido automaticamente    | ---              |
| Bairro                 | Caixa de Texto   | Mínimo de 3 caracteres        | ---              |
| Nome da Empresa Fornecedora | Caixa de Texto | Mínimo de 3 caracteres     | ---              |

#### Comandos

| **Comandos**           | **Destino**                       | **Tipo**   |
|------------------------|-----------------------------------|------------|
| Enviar Pedido      | Confirmar e Enviar Pedido         | botão      |
| Cancelar Pedido              | Cancelar e Retornar à tela de exibição      | botão      |

---

### **Receber Pedido**

| **Campo**              | **Tipo**         | **Restrições**               | **Valor Padrão**            |
|------------------------|------------------|------------------------------|-----------------------------|
| Nome do Comprador       | Caixa de Texto   | Preenchido automaticamente    | ---                         |
| Tipo de material     | Caixa de Texto           | Preenchido automaticamente     | ---                         |
| Quantidade Recebida     | Número           | Somente valores positivos     | ---                         |
| Observação              | Área de Texto    | Opcional                      | ---                         |


#### Comandos

| **Comandos**           | **Destino**                       | **Tipo**   |
|------------------------|-----------------------------------|------------|
| Negociar     | Confirma a Decisão do Pedido      | botão      |
| Recusar               | Cancelar Operação                | botão      |


