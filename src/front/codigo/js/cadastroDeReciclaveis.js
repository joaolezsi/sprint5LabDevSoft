async function listarReciclaveis() {
    const id = localStorage.getItem("userId");

    if (!id) {
        console.error('ID do usuário não encontrado no localStorage.');
        return;
    }

    const apiUrl = `http://localhost:8080/reciclavel/listar/${id}`;
    const container = document.getElementById('cards-container'); // Adicionando a referência ao container

    try {
        const response = await fetch(apiUrl);

        if (!response.ok) throw new Error(response.statusText);

        const data = await response.json();

        container.innerHTML = '';  // Limpa o container antes de adicionar os cards

        // Verifica se data é um array diretamente ou possui a propriedade 'content'
        const reciclaveis = Array.isArray(data) ? data : data.content || [];

        reciclaveis.forEach(reciclavel => {
            const { id, nome, descricao, precoPorKg, tipo, imagemUrl } = reciclavel;

            const precoFormatado = parseFloat(precoPorKg).toFixed(2);
            const imgSrc = imagemUrl || "https://via.placeholder.com/120";

            const cardHTML = `
                <div>
                    <div class="card" data-card-id="${id}" style="min-width: 100%;">
                        <div class="card-body d-flex align-items-center">
                            <img src="${imgSrc}" alt="Imagem do Produto" class="card-img-left me-3">
                            <div class="content" style="flex-grow: 1;">
                                <h5 class="card-title">${nome}</h5>
                                <p class="card-text">${descricao}</p>
                                <span class="type">Tipo: ${tipo}</span>
                            </div>
                            <div class="price text-end">R$ ${precoFormatado}</div>
                        </div>
                        <div class="action-buttons text-end">
                            <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editarCard(this)" data-card-id="${id}">
                                <i class="fas fa-edit"></i> 
                            </button>
                            <button id="delete-confirmation-button" type="button" class="btn btn-sm btn-danger ms-2" data-bs-toggle="modal" data-bs-target="#deleteModal" data-card-id="${id}">
                                <i class="fas fa-trash"></i> 
                            </button>
                        </div>
                    </div>
                </div>
            `;

            container.insertAdjacentHTML('beforeend', cardHTML);
        });
    } catch (error) {
        console.error('Erro ao listar os recicláveis:', error);
    }
}

// Função para enviar uma requisição DELETE para o backend
async function deletar(id) {
    const empresaVendedoraId = parseInt(localStorage.getItem("userId"));
    if (isNaN(empresaVendedoraId)) {
        console.error("ID da empresa vendedora inválido.");
        return;
    }
    const apiUrl = `http://localhost:8080/reciclavel/deletar/${id}/${empresaVendedoraId}`;

    try {
        const response = await fetch(apiUrl, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            console.log(`Produto com ID ${id} deletado com sucesso.`);
            return true;
        } else {
            console.error('Erro ao deletar o produto:', response.status, response.statusText);
            return false;
        }
    } catch (error) {
        console.error('Erro ao deletar o produto:', error);
        return false;
    }
}

// Atualiza a função confirmarDelecao para chamar deletar
async function confirmarDelecao() {
    const cardId = document.getElementById('delete-confirmation-button').getAttribute('data-card-id');

    if (!cardId) {
        console.error("ID do card não encontrado para exclusão.");
        return;
    }

    const deletado = await deletar(cardId);

    if (deletado) {
        const card = document.querySelector(`.card[data-card-id="${cardId}"]`);
        if (card) {
            card.remove();
        }
        bootstrap.Modal.getInstance(document.getElementById('deleteModal')).hide();
    } else {
        console.error("Erro ao tentar excluir o card.");
    }
}


function editarCard(button) {
    const cardId = button.getAttribute('data-card-id');
    const card = document.querySelector(`.card[data-card-id="${cardId}"]`);

    const nome = card.querySelector('.card-title').innerText;
    const descricao = card.querySelector('.card-text').innerText;
    const preco = card.querySelector('.price').innerText.replace('R$', '').trim();
    const tipo = card.querySelector('.type').innerText.replace('Tipo: ', '').trim();

    // Usar os campos do modal de edição, com IDs exclusivos
    document.getElementById('nome-produto-editar').value = nome;
    document.getElementById('descricao-produto-editar').value = descricao;
    document.getElementById('preco-produto-editar').value = preco;
    document.getElementById('categoria-produto-editar').value = tipo;

    // Atribuir o ID do card ao formulário de edição
    document.getElementById('product-form2').setAttribute('data-card-id', cardId);
}

async function atualizar(reciclavelDAO, id) {
    const apiUrl = `http://localhost:8080/reciclavel/atualizar/${id}`;

    try {
        const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(reciclavelDAO)
        });

        if (response.ok) {
            const data = await response.json();
            return data;  // Retorna o objeto atualizado
        } else {
            console.error('Erro na atualização:', response.statusText);
            return null;
        }
    } catch (error) {
        console.error('Erro na atualização:', error);
        return null;
    }
}

async function cadastrar(reciclavelDAO) {
    const apiUrl = `http://localhost:8080/reciclavel/cadastrar`;

    console.log("Dados a serem enviados:", reciclavelDAO);

    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(reciclavelDAO)
        });

        if (response.ok) {
            const data = await response.json();
            return data.id;
        } else {
            console.error('Erro na requisição:', response.status, response.statusText);
            return null;
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        return null;
    }
}

async function addCard(nome, descricao, precoPorKg, tipo) {
    const empresaVendedoraId = parseInt(localStorage.getItem("userId"));
    if (isNaN(empresaVendedoraId)) {
        console.error("ID da empresa vendedora inválido.");
        return;
    }

    if (!nome || !descricao || isNaN(precoPorKg) || !tipo) {
        console.error("Dados inválidos fornecidos para o produto.");
        return;
    }

    const reciclavelDAO = {
        nome: nome,
        descricao: descricao,
        precoPorKg: parseFloat(precoPorKg),  // parseFloat para garantir que seja número
        tipo: tipo,
        idEmpresaVendedora: empresaVendedoraId  // Alterado para idEmpresaVendedora
    };

    try {
        const cardID = await cadastrar(reciclavelDAO);

        if (cardID) {
            const cardHTML = `
                <div class="card" data-card-id="${cardID}" style="min-width: 100%;">
                    <div class="card-body d-flex align-items-center">
                        <img src="https://via.placeholder.com/120" alt="Imagem do Produto" class="card-img-left me-3">
                        <div class="content" style="flex-grow: 1;">
                            <h5 class="card-title">${nome}</h5>
                            <p class="card-text">${descricao}</p>
                            <span class="type">Tipo: ${tipo}</span>
                        </div>
                        <div class="price text-end">R$ ${parseFloat(precoPorKg).toFixed(2)}</div>
                    </div>

                    <div class="action-buttons text-end">
                        <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editarCard(this)" data-card-id="${cardID}" aria-label="Editar produto">
                            <i class="fas fa-edit"></i> 
                        </button>
                        <button type="button" class="btn btn-sm btn-danger ms-2" data-bs-toggle="modal" data-bs-target="#deleteModal" id="delete-confirmation-button" data-card-id="${cardID}" aria-label="Excluir produto">
                            <i class="fas fa-trash"></i> 
                        </button>
                    </div>
                </div>
            `;

            document.getElementById('cards-container').insertAdjacentHTML('beforeend', cardHTML);
        } else {
            console.error('Falha ao cadastrar o produto.');
        }
    } catch (error) {
        console.error('Erro ao cadastrar o produto:', error);
    }
}

document.getElementById('product-form').addEventListener('submit', function (event) {
    event.preventDefault();
    const nome = document.getElementById('nome-produto').value;
    const descricao = document.getElementById('descricao-produto').value;
    const preco = parseFloat(document.getElementById('preco-produto').value);  // Removendo o toFixed(2) aqui
    const tipo = document.getElementById('categoria-produto').value;

    addCard(nome, descricao, preco, tipo);

    document.getElementById('product-form').reset();
    document.getElementById('product-form').removeAttribute('data-card-id');
});


async function atualizarCard(cardId, nome, descricao, precoPorKg, tipo) {
    // Atualiza o conteúdo do card diretamente no DOM
    const card = document.querySelector(`.card[data-card-id="${cardId}"]`);
    if (card) {
        card.querySelector('.card-title').innerText = nome;
        card.querySelector('.card-text').innerText = descricao;
        card.querySelector('.price').innerText = `R$ ${precoPorKg}`;
        card.querySelector('.type').innerText = `Tipo: ${tipo}`;
    }

    const empresaVendedoraId = parseInt(localStorage.getItem("userId"));
    if (isNaN(empresaVendedoraId)) {
        console.error("ID da empresa vendedora inválido.");
        return;
    }

    // Monta o objeto para atualizar o servidor
    const reciclavelDAO = {
        nome: nome,
        descricao: descricao,
        precoPorKg: parseFloat(precoPorKg),  // parseFloat para garantir que seja número
        tipo: tipo,
        idEmpresaVendedora: empresaVendedoraId  // Alterado para idEmpresaVendedora
    };

    const resultado = await atualizar(reciclavelDAO, cardId);  // Usa apenas o valor do ID

    if (resultado) {
        console.log('Card atualizado com sucesso:', resultado);
    } else {
        console.error('Erro ao atualizar o card.');
    }
}

// Event listener para o formulário
document.getElementById('product-form2').addEventListener('submit', function (event) {
    event.preventDefault();

    // Pega os valores do modal de edição
    const nome = document.getElementById('nome-produto-editar').value;
    const descricao = document.getElementById('descricao-produto-editar').value;
    const preco = parseFloat(document.getElementById('preco-produto-editar').value).toFixed(2);
    const tipo = document.getElementById('categoria-produto-editar').value;

    // Pega o valor numérico do cardId
    const cardId = parseInt(document.getElementById('product-form2').getAttribute('data-card-id'));

    if (cardId) {
        atualizarCard(cardId, nome, descricao, preco, tipo);
    }

    // Fechar o modal e resetar o formulário
    bootstrap.Modal.getInstance(document.getElementById('editModal')).hide();
    document.getElementById('product-form2').reset();
    document.getElementById('product-form2').removeAttribute('data-card-id');
});

// Chama listarReciclaveis para carregar os cards ao abrir a página
document.addEventListener('DOMContentLoaded', listarReciclaveis);