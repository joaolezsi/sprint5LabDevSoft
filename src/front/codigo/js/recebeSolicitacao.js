// Validação de acesso ao carregar a página
window.onload = function () {
    const tipoEmpresa = localStorage.getItem('tipoEmpresa');
    if (tipoEmpresa === 'COMPRADORA') {
        alert('Acesso negado');
        window.location.href = '../views/Login.html';
    }
};

// Declaração de variáveis globais
const empresasCompradora = [];
const pontosDeColeta = [];
const materiaisReciclaveis = [];
let dadosSolicitacao = [];
const userId = parseInt(localStorage.getItem('userId'), 10);
let solicitacaoId = [];

/**
 * Obtém os dados da API de forma otimizada.
 */
async function fetchDados() {
    try {
        // Busca dados principais
        const response = await fetch(`http://localhost:8080/api/solicitacao-pedido/empresa/${userId}`);
        const solicitacoes = await response.json();
        solicitacaoId = solicitacoes.map(solicitacao => solicitacao.idSolicitacao);
        console.log(solicitacaoId);
        // Realiza requisições paralelas para empresas, pontos de coleta e recicláveis
        const promessasEmpresas = solicitacoes.map(solicitacao =>
            fetch(`http://localhost:8080/empresaCompradora/${parseInt(solicitacao.empresaCompradora, 10)}`).then(res => res.json())
        );
        const promessasPontos = solicitacoes.map(solicitacao =>
            fetch(`http://localhost:8080/empresaVendedora/${parseInt(solicitacao.pontoDeColeta, 10)}`).then(res => res.json())
        );
        const promessasMateriais = solicitacoes.map(solicitacao =>
            fetch(`http://localhost:8080/reciclavel/listarMaterial/${parseInt(solicitacao.reciclavel, 10)}`).then(res => res.json())
        );

        // Aguarda todas as requisições paralelas
        const [empresas, pontos, materiais] = await Promise.all([
            Promise.all(promessasEmpresas),
            Promise.all(promessasPontos),
            Promise.all(promessasMateriais)
        ]);

        empresasCompradora.push(...empresas);
        pontosDeColeta.push(...pontos);
        materiaisReciclaveis.push(...materiais);
        dadosSolicitacao = solicitacoes;
    } catch (error) {
        console.error("Erro ao buscar dados:", error);
    }
}

/**
 * Cria elementos de proposta no DOM.
 * @param {Array} propostas - Lista de propostas formatadas.
 */
function renderizarPropostas(propostas) {
    const container = document.getElementById("listaHtml");
    if (!container) return;

    // Limpa conteúdo anterior
    container.innerHTML = '';

    // Adiciona cada proposta ao container
    propostas.forEach((proposta, index) => {
        container.innerHTML += `
            <div class="container1">
                <ul id="lista_proposta">
                    <li>Nome do Comprador: <strong>${proposta.nome}</strong></li>
                    <li>Tipo de Material: <strong>${proposta.material}</strong></li>
                    <li>Quantidade requerida: <strong>${proposta.quantidade}KG</strong></li>
                    <li>Observação: <strong>${proposta.observacao}</strong></li>
                </ul>
                <button class="editar" data-index="${index}" onclick="aceitar(${proposta.id})">Negociar <i class="fas fa-check"></i></button>
                <button class="recusar" data-index="${index}" onclick="cancelar(${proposta.id})">Recusar <i class="fas fa-trash-alt"></i></button>
            </div>
        `;
    });
}

/**
 * Prepara e exibe as propostas.
 */
async function exibirPropostas() {
    await fetchDados();

    // Formata os dados em propostas
    const propostas = dadosSolicitacao.map((dados, index) => ({
        id: solicitacaoId[index],
        nome: empresasCompradora[index]?.nome || 'Não informado',
        quantidade: dados.quantidade,
        observacao: dados.observacao,
        material: materiaisReciclaveis[index]?.tipo || 'Não informado'
    }));

    console.log(propostas);
    renderizarPropostas(propostas);
}

/**
 * Funções para ações de aceitar e cancelar (a implementar).
 */

async function aceitar(index) {
    const url = `http://localhost:8080/api/solicitacao-pedido/${index}/negociacao`;

    fetch(url, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
    })
    .then(response => {
        if (response.ok) {
            console.log(`Proposta ${index} aceita com sucesso.`);
            location.reload(); // Recarrega a página
        } else {
            console.error(`Falha ao aceitar a proposta ${index}:`, response.status);
        }
    })
    .catch(error => console.error('Erro:', error));
}

async function cancelar(index) {
    const url = `http://localhost:8080/api/solicitacao-pedido/${index}/negar`;

    fetch(url, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
    })
    .then(response => {
        if (response.ok) {
            console.log(`Proposta ${index} cancelada com sucesso.`);
            location.reload(); // Recarrega a página
        } else {
            console.error(`Falha ao cancelar a proposta ${index}:`, response.status);
        }
    })
    .catch(error => console.error('Erro:', error));
}

// Event Listener para ações
document.addEventListener("DOMContentLoaded", () => {
    exibirPropostas();
});
