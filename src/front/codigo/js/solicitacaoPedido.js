// Recupera o ID do material do localStorage
const materialId = localStorage.getItem('materialId');
const empresaCompradoraId = localStorage.getItem('userId');
if (!materialId) {
    console.error('ID do material não encontrado no localStorage');
}

let materialData; // Declarar materialData fora da função exibe para torná-lo acessível

async function exibe() {
    if (!materialId) {
        return;
    }

    try {
        // Requisição para obter detalhes do material
        const materialResponse = await fetch(`http://localhost:8080/reciclavel/listarMaterial/${materialId}`);
        if (!materialResponse.ok) 
            console.error(`Erro ao buscar os dados do material: ${materialResponse.status}`);

        materialData = await materialResponse.json();
        const empresa_fornecedora = materialData.idEmpresaVendedora;
        console.log(empresa_fornecedora);
        // Requisição para obter detalhes da empresa fornecedora
        const fornecedorResponse = await fetch(`http://localhost:8080/empresaVendedora/${empresa_fornecedora}`);
        if (!fornecedorResponse.ok) throw new Error("Erro ao buscar os dados da empresa fornecedora");

        const fornecedorData = await fornecedorResponse.json();
        const empresaEnderecoId = fornecedorData.endereco.id;
        const enderecoResponse = await fetch(`http://localhost:8080/endereco/${empresaEnderecoId}`);
        if (!enderecoResponse.ok) throw new Error("Erro ao buscar os dados do endereço da empresa fornecedora");
        const enderecoData = await enderecoResponse.json();

        // Requisição para obter detalhes da empresa compradora usando o ID da empresa compradora
        const compradoraResponse = await fetch(`http://localhost:8080/empresaCompradora/${empresaCompradoraId}`);
        if (!compradoraResponse.ok) throw new Error("Erro ao buscar os dados da empresa compradora");

        const compradoraData = await compradoraResponse.json();

        // Exibe os dados da empresa compradora
        let lista = document.getElementById("lista_proposta");
        if (lista) {
            lista.innerHTML += `
                <li>
                    Empresa compradora: <strong> ${compradoraData.nome} </strong>
                </li>
                <li>
                    CNPJ: <strong> ${compradoraData.cnpj} </strong>
                </li>
            `;
        }

        // Exibe os dados da empresa fornecedora e do material
        let container = document.getElementById("container_2");
        if (container) {
            container.innerHTML += `
                <div class="titulo">
                    <h4>EMPRESA FORNECEDORA: <strong> ${fornecedorData.nome} </strong> </h4>
                </div>
                <ul id="empresa_fornecedora">
                    <li> CNPJ da empresa: <strong> ${fornecedorData.cnpj}</strong></li>
                    <li> Material Escolhido: <strong> ${materialData.tipo}</strong></li>
                    <li> Bairro: <strong> ${enderecoData.bairro}</strong></li>
                </ul>
            `;
        }

    } catch (error) {
        console.error("Erro ao carregar os dados:", error);
    }
}

// Função para enviar pedido
async function enviarItens() {
    const quantidade = document.querySelector('.quantidade')?.value;
    const observacoes = document.querySelector('.observacoes')?.value;

    if (!quantidade || isNaN(quantidade) || quantidade <= 0) {
        alert('Por favor, insira uma quantidade válida.');
        return;
    }

    try {
        const pedido = {
            idReciclavel: materialId,
            quantidade: parseFloat(quantidade),
            observacao: observacoes || '',
            idEmpresaCompradora: empresaCompradoraId,
            precoPorKg: materialData?.precoPorKg,
        };

        const response = await fetch('http://localhost:8080/api/solicitacao-pedido', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(pedido),
        });

        if (!response.ok) throw new Error(`Erro ao enviar pedido: ${response.statusText}`);

        alert('Pedido enviado com sucesso!');
        document.querySelector('.quantidade').value = '';
        document.querySelector('.observacoes').value = '';
        window.location.href = "../views/exibirReciclaveis.html";
    } catch (error) {
        alert('Erro ao enviar o pedido.');
        console.error(error);
    }
}


function cancelar() {
    window.location.href = "../views/exibirReciclaveis.html";
}

// Configura os eventos
document.addEventListener("DOMContentLoaded", exibe);
document.querySelector('.enviaPedido')?.addEventListener('click', enviarItens);
document.querySelector('.cancela')?.addEventListener('click', cancelar);
