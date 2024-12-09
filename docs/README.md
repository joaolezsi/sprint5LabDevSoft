# RECOMPENSA VERDE


**Jonathan Sena da Silva, jonathansilvagw@gmail.com**

**Víctor Gabriel Cruz Pereira, victorgabrielcruzpereira21@gmail.com**

**Wesley Filemon Rocha Rodrigues, wesleyfilemon@gmail.com**

**Vitor Hugo Dutra Marinho, vitorhdmarinho@gmail.com**

**Daniel Vieira Veríssimo de Carvalho Ferreira, danielvercarvalho@gmail.com**

**João Pedro Lezsi Fernandes, joaolezsi@gmail.com**

**Pedro Faria Pires, p10290703@gmail.com**

---

Professores:

**Prof. Aline Norberta de Brito**

**Prof. Cleia Marcia Gomes Amaral**

**Prof. Cristiano de Macêdo Neto**

---

_Curso de Engenharia de Software_

_Instituto de Informática e Ciências Exatas – Pontifícia Universidade Católica de Minas Gerais (PUC MINAS), Belo Horizonte – MG – Brasil_

---

Este trabalho apresenta o desenvolvimento de uma plataforma online destinada a facilitar o comércio de materiais recicláveis, com o objetivo de promover a sustentabilidade e incentivar a economia circular em Belo Horizonte. A plataforma conecta parceiros interessados na compra de recicláveis com pontos de coleta que dispõem desses materiais, permitindo a negociação de preços e o gerenciamento de espaços de coleta. Ao mitigar os efeitos negativos do descarte inadequado de resíduos, especialmente plásticos e lixo eletrônico, o projeto busca reduzir a poluição ambiental e criar novas oportunidades econômicas na cadeia de reciclagem.

---

## 1. Introdução

Este trabalho apresenta o desenvolvimento de uma plataforma online destinada a facilitar o comércio de materiais recicláveis, com o foco exclusivo em empresas parceiras da cidade de Belo Horizonte. O objetivo é promover a sustentabilidade e incentivar a economia circular na região, conectando empresas que geram resíduos recicláveis com aquelas que os utilizam em seus processos produtivos. A plataforma permite a negociação de preços e o gerenciamento de espaços de coleta, visando uma solução local e prática para a gestão de resíduos, principalmente plásticos e lixo eletrônico.

### 1.1 Contextualização

Belo Horizonte, como muitas outras grandes cidades, enfrenta desafios crescentes no manejo de resíduos sólidos devido ao aumento da urbanização e do consumo. O descarte inadequado de materiais, em especial plásticos e lixo eletrônico, tem sérios impactos ambientais, como a poluição do solo, da água e a contribuição para as mudanças climáticas. Localmente, é notável a necessidade de soluções que integrem empresas que geram grandes volumes de resíduos a outras que possam reaproveitá-los de maneira eficiente. Este projeto, portanto, visa suprir essa lacuna, facilitando o comércio e a reciclagem de materiais dentro de um contexto urbano específico.

### 1.2 Problema

O principal problema abordado é o descarte inadequado de resíduos sólidos em Belo Horizonte, especialmente plásticos e lixo eletrônico. Esse cenário tem causado problemas ambientais graves na cidade, como a contaminação de rios, entupimento de sistemas de drenagem e a geração de resíduos em aterros sanitários, que agrava a crise de gerenciamento de resíduos urbanos.

### 1.3 Objetivo geral

O objetivo deste trabalho é desenvolver uma plataforma online que facilite o comércio de materiais recicláveis em Belo Horizonte, conectando empresas que geram esses resíduos a empresas compradoras. O projeto visa promover uma economia circular local e reduzir o impacto ambiental dos resíduos, ao mesmo tempo que gera valor econômico para as empresas envolvidas.

#### 1.3.1 Objetivos específicos
- Estabelecer uma conexão eficiente entre empresas que geram resíduos recicláveis e empresas interessadas em comprá-los.
- Desenvolver funcionalidades para permitir o registro de requisições e ofertas de materiais recicláveis, bem como a gestão de espaços de coleta em Belo Horizonte.
- Monitorar e analisar dados referentes à geração e comercialização de resíduos recicláveis dentro do município, visando otimizar a reciclagem.
- Criar um sistema de ranking para destacar empresas conforme sua atuação sustentável e eficiência no uso da plataforma.
- Expor ofertas de compra e venda de materiais recicláveis com base na demanda das empresas locais.

### 1.4 Justificativas

A crescente produção de resíduos sólidos, especialmente plásticos e lixo eletrônico, representa um desafio ambiental significativo. A aplicação web proposta visa promover a reciclagem, incentivar a economia circular e reduzir a quantidade de resíduos descartados de forma inadequada, contribuindo para mitigar os efeitos negativos do lixo no meio ambiente e criar novas oportunidades econômicas. Além disso, a plataforma oferecerá uma solução prática e eficiente para o gerenciamento de resíduos recicláveis, o que justifica sua relevância e necessidade. A decisão de focar exclusivamente em Belo Horizonte e trabalhar apenas com empresas foi baseada no feedback dos professores, que recomendaram uma abordagem mais restrita e prática. A cidade enfrenta sérios desafios no manejo de resíduos, especialmente no que diz respeito ao lixo eletrônico e plástico. Ao implementar uma solução local, espera-se promover práticas mais eficazes de gestão de resíduos, impulsionando a reciclagem e beneficiando tanto o meio ambiente quanto a economia local.

---

## 2. Participantes do processo

Com o escopo restrito a Belo Horizonte e focado exclusivamente em empresas como parceiros, os participantes do processo são redefinidos conforme descrito abaixo:

### Empresas Vendedoras (Pequenas e Médias Empresas)

- **Descrição**: Empresas sediadas em Belo Horizonte que geram resíduos recicláveis, como plásticos e lixo eletrônico. Exemplos incluem supermercados, lojas de eletrônicos, e distribuidoras locais.
  
- **Função**: Listar e vender seus materiais recicláveis na plataforma, gerando receita adicional e contribuindo para a redução do impacto ambiental local.

### Empresas Compradoras (Grandes Empresas e Indústrias)

- **Descrição**: Empresas localizadas em Belo Horizonte e região metropolitana que utilizam materiais recicláveis em seus processos produtivos, como indústrias de reciclagem e manufatura.
  
- **Função**: Comprar materiais recicláveis a preços competitivos, contribuindo para práticas sustentáveis e redução de custos com matéria-prima.

---

## 3. Modelagem do processo de negócio

### 3.1. Análise da situação atual

Atualmente, o mercado de reciclagem em Belo Horizonte apresenta oportunidades e muitos desafios. Por mais que a cidade conte com programas de coleta seletiva e parcerias com cooperativas, o setor de comércio de materiais recicláveis ainda carece de uma maior integração entre empresas geradoras de resíduos e potenciais compradores.

Em Belo Horizonte, as iniciativas de reciclagem estão fragmentadas. As empresas que geram grandes volumes de resíduos recicláveis não possuem um canal eficiente e estruturado para comercializar esses materiais diretamente com indústrias que podem reutilizá-los. Isso gera uma desconexão no ciclo de reciclagem, impactando tanto o reaproveitamento dos resíduos quanto o desenvolvimento sustentável local.

Além disso, o setor ainda é marcado por uma forte informalidade. Muitos dos processos de coleta e comercialização de materiais recicláveis acontecem de forma descentralizada, o que dificulta a otimização da cadeia de suprimentos e a criação de um mercado robusto. Embora existam iniciativas pontuais para melhorar essa dinâmica, como programas de coleta seletiva e apoio a cooperativas, a cidade ainda enfrenta dificuldades para consolidar uma rede de reciclagem eficiente que beneficie tanto as empresas quanto a sociedade.

Esse cenário aponta a necessidade de soluções que conectem de forma mais efetiva as empresas geradoras de resíduos recicláveis com compradores locais, promovendo uma economia circular mais eficiente.

### 3.2. Descrição geral da proposta de solução

A plataforma "Recompensa Verde" oferece uma solução prática e inovadora para conectar empresas de Belo Horizonte que geram resíduos recicláveis com aquelas que têm interesse em adquiri-los. A proposta visa facilitar o comércio de resíduos, promover a economia circular na cidade e reduzir os problemas ambientais causados pelo descarte inadequado.

#### Limites da Proposta

- **Dependência Logística:** A eficiência do processo dependerá da capacidade das empresas locais de coletar e entregar os materiais recicláveis de forma eficiente.
- **Qualidade dos Materiais:** Garantir a qualidade dos materiais recicláveis será essencial para satisfazer as empresas compradoras.
- **Escalabilidade Local:** A solução está limitada a Belo Horizonte, o que facilita a implementação, mas limita a escala inicial de atuação.
- **Adoção pelas Empresas:** Convencer empresas locais a adotarem a plataforma pode ser um desafio, especialmente em setores que ainda não possuem práticas sustentáveis bem estabelecidas.

#### Ligações com Estratégias e Objetivos do Negócio

- **Sustentabilidade Local:** A plataforma é alinhada com as metas de sustentabilidade de Belo Horizonte, promovendo o uso de materiais recicláveis e contribuindo para a redução de resíduos.
- **Redução de Custos:** As empresas compradoras podem reduzir custos com matéria-prima ao utilizar materiais recicláveis adquiridos na plataforma.
- **Responsabilidade Social:** A participação na plataforma pode ajudar as empresas a demonstrar compromisso com a sustentabilidade e a melhorar sua reputação no mercado local.
- **Inovação:** A plataforma incentiva as empresas locais a adotarem práticas inovadoras de gestão de resíduos, promovendo competitividade e eficiência.

#### Oportunidades de Melhoria

- **Expansão de Parcerias Locais:** Ampliar a rede de empresas compradoras e vendedoras para aumentar a eficiência da plataforma em Belo Horizonte.
- **Certificação de Qualidade:** Implementar um sistema de certificação para garantir a qualidade dos materiais comercializados na plataforma, aumentando a confiança entre compradores e vendedores.
- **Educação e Sensibilização:** Promover campanhas de conscientização sobre a importância da reciclagem e o uso da plataforma entre as empresas locais.
- **Tecnologia e Inovação:** Investir em tecnologias para otimizar as operações logísticas e melhorar a rastreabilidade dos materiais recicláveis.
- **Melhoria Contínua:** Coletar feedback das empresas para ajustar a plataforma às necessidades do mercado local e promover melhorias contínuas.


### 3.3. Modelagem dos processos

[PROCESSO 1 - Gestão de empresas](processo-1-gestao-de-empresas.md "Detalhamento do processo de registro de usuários")

[PROCESSO 2 - Gerenciamento de recicláveis](processo-2-Gerenciamento-de-reciclaveis.md "Detalhamento do processo de cadastro de recicláveis")

[PROCESSO 3 - Solicitação de pedido](processo-3-solicitacao-de-pedido.md "Detalhamento do processo de solicitação de pedido")

[PROCESSO 4 - Negociação do pedido](processo-4-negociacao-do-pedido.md "Detalhamento do processo de negociação do pedido")

## 4. Projeto da solução

_O documento a seguir apresenta o detalhamento do projeto da solução. São apresentadas duas seções que descrevem, respectivamente: modelo relacional e tecnologias._

[Projeto da solução](solution-design.md "Detalhamento do projeto da solução: modelo relacional e tecnologias.")


## 5. Indicadores de desempenho

_O documento a seguir apresenta os indicadores de desempenho dos processos._

[Indicadores de desempenho dos processos](performance-indicators.md)


## 6. Interface do sistema

_A sessão a seguir apresenta a descrição do produto de software desenvolvido._ 

[Documentação da interface do sistema](interface.md)

## 7. Conclusão

**Conclusão**

Neste trabalho, abordamos a temática da reciclagem em Belo Horizonte, explorando seus desafios, práticas atuais e possíveis melhorias. A experiência proporcionou uma visão mais ampla sobre a importância da sustentabilidade e do papel ativo que a sociedade pode desempenhar na preservação do meio ambiente.

Além disso, o trabalho destacou a relevância do trabalho em equipe e da colaboração. A troca de ideias, a divisão de tarefas e o suporte mútuo foram fundamentais para o desenvolvimento das etapas do projeto. A convivência em grupo nos ensinou a importância de respeitar diferentes pontos de vista e aproveitar a diversidade de habilidades para alcançar objetivos comuns.

Ao longo do processo, aprendemos também sobre a necessidade de paciência e resiliência. Enfrentamos desafios e imprevistos, mas esses momentos nos ensinaram a lidar com problemas de forma construtiva e a buscar soluções criativas. Apesar das dificuldades, conseguimos desenvolver um trabalho satisfatório, que reflete nosso esforço e dedicação.

Por fim, como sugestão para futuras linhas de estudo, seria interessante aprofundar a análise sobre a eficácia das políticas públicas voltadas para a reciclagem e explorar modelos de sucesso aplicados em outras cidades, adaptando-os para a realidade local. Esses estudos poderiam contribuir para o aprimoramento das práticas e o aumento do impacto positivo na sociedade.

# REFERÊNCIAS

_Como um projeto de software não requer revisão bibliográfica, a inclusão das referências não é obrigatória. No entanto, caso você deseje incluir referências relacionadas às tecnologias, padrões, ou metodologias que serão usadas no seu trabalho, relacione-as de acordo com a ABNT._

_Verifique no link abaixo como devem ser as referências no padrão ABNT:_

http://portal.pucminas.br/imagedb/documento/DOC_DSC_NOME_ARQUI20160217102425.pdf

**[1.1]** - _ELLEN MACARTHUR FOUNDATION. The New Plastics Economy: Rethinking the Future of Plastics & Catalysing Action. 2017. Disponível em: https://ellenmacarthurfoundation.org/the-new-plastics-economy-rethinking-the-future-of-plastics._

**[1.2]** - _KAZA, S.; YAO, L.; BHADA-TATA, P.; VAN WOERDEN, F. What a Waste 2.0: A Global Snapshot of Solid Waste Management to 2050. Washington, DC: World Bank, 2018._

**[1.3]** - _FORTI, V.; BALDÉ, C. P.; KUEHR, R.; BEL, G. The Global E-waste Monitor 2020: Quantities, flows, and the circular economy potential. Bonn: United Nations University (UNU); Geneva: International Telecommunication Union (ITU); Rotterdam: International Solid Waste Association (ISWA), 2020_

**[1.4]** - _G1. Brasil é o 5º país que mais produz resíduos eletrônicos, mas descarte correto ainda é pequeno. Jornal Nacional, 27 abr. 2024. Disponível em: https://g1.globo.com/jornal-nacional/noticia/2024/04/27/brasil-e-o-5o-pais-que-mais-produz-residuos-eletronicos-mas-descarte-correto-ainda-e-pequeno.ghtml._

**[1.5]** - _ELMASRI, Ramez; NAVATHE, Sham. Sistemas de banco de dados. 7. ed. São Paulo: Pearson, c2019. E-book. ISBN 9788543025001._

**[1.6]** - _RACHEL RAMIREZ, Lixo eletrônico chegou a nível recorde. CNN Brasil, 2024. Disponivel em:
https://www.cnnbrasil.com.br/tecnologia/lixo-eletronico-chegou-a-nivel-recorde-entenda-o-problema/. 
Acesso em: 25 ago. 2024._

**[1.7]** - _LUCIANO FILHO, Descarte irregular de resíduos é origem para diversos problemas. JONAL DA USP, 2019.
Disponivel em: https://jornal.usp.br/atualidades/descarte-irregular-de-residuos-e-origem-para-diversos-problemas/. Acesso em: 25 ago. 2024._

**[1.8]** - _ALINE OLIVEIRA, Como descartar o lixo eletrônico?. CNN Brasil, 2024. Disponivel em: https://www.cnnbrasil.com.br/tecnologia/como-descartar-o-lixo-eletronico/. Acesso em: 26 ago. 2024._

**[1.9]** - _GUILHERME GAMA, Pela primeira vez, pesquisadores da USP encontram lixo no oceano profundo. CNN Brasil, 2024, Disponivel em: https://www.cnnbrasil.com.br/nacional/pela-primeira-vez-pesquisadores-da-usp-encontram-lixo-no-oceano-profundo/. Acesso em: 26 ago. 2024._
  
**[2.0]** - _TV GAZETA, G1. Descarte errado de lixo põe em risco catadores e garis. G1, 2024. Disponivel em: https://g1.globo.com/al/alagoas/noticia/2024/04/02/descarte-irregular-lixo-risco-saude-catadores-e-garis. Acesso em: 26 ago. 2024._



# APÊNDICES


_Atualizar os links e adicionar novos links para que a estrutura do código esteja corretamente documentada._


## Apêndice A - Código fonte

[Código do front-end](../src/front/) -- repositório do código do front-end

[Código do back-end](../src/back/)  -- repositório do código do back-end


## Apêndice B - Apresentação final


[Slides da apresentação final](presentations/)


[Vídeo da apresentação final](video/)






