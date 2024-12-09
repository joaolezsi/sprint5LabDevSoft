// Função chamada ao clicar no botão "Solicitar pedido"
function solicitarPedido(materialId) {
    // Salva o ID do material no localStorage
    localStorage.setItem('materialId', materialId);
    
    // Redireciona para a página de solicitação de pedido
    window.location.href = '../views/solicitacaoPedido.html';
}

// Função para listar os recicláveis
async function listarReciclaveis() {
    const apiUrl = 'http://localhost:8080/reciclavel/listar';

    try {
        const response = await fetch(apiUrl);

        if (response.ok) {
            const data = await response.json();
            const container = document.getElementById('reciclaveis-container');

            // Verificação de existência do elemento
            if (!container) {
                console.error('Elemento reciclaveis-container não encontrado');
                return;
            }

            container.innerHTML = '';  // Limpa o container antes de adicionar os cartões

            const reciclaveis = Array.isArray(data) ? data : data.content || [];

            reciclaveis.forEach(material => {
                const { id, nome, descricao, precoPorKg, tipo } = material;

                const cardHTML = `
                <div class="card" data-card-id="${id}" style="text-align: center;">
                    <div style="display: flex; justify-content: center;">
                        <img src="https://via.placeholder.com/120" alt="Imagem do Produto" class="card-img-top" style="margin-bottom: 10px;">
                    </div>
                    <h2>${nome}</h2>
                    <p>Descrição: ${descricao}</p>
                    <p>Preço por Kg: R$${precoPorKg.toFixed(2)}</p>
                    <p>Tipo: ${tipo}</p>
                    <button class="select-btn" onclick="solicitarPedido(${id})">Solicitar pedido</button>
                </div>
            `;
                container.insertAdjacentHTML('beforeend', cardHTML);
            });
        } else {
            console.error('Erro ao listar os recicláveis:', response.statusText);
        }
    } catch (error) {
        console.error('Erro ao listar os recicláveis:', error);
    }
}

async function listarReciclaveisPorTipo(tipo) {
    const apiUrl = `http://localhost:8080/reciclavel/${tipo}`;
    const container = document.getElementById('reciclaveis-container');

    try {
        const response = await fetch(apiUrl);

        if (response.ok) {
            const data = await response.json();
            const reciclaveis = Array.isArray(data) ? data : data.content || [];

            // Limpa o container antes de adicionar os materiais
            container.innerHTML = '';

            if (reciclaveis.length === 0) {
                // Exibe a mensagem se a lista estiver vazia
                container.innerHTML = `
                    <div class="mensagem">
                        <p><i class="fa-solid fa-box-open" style="font-size: 50px; color: #ccc;"></i></p>
                        <p>Material indisponível.</p>
                    </div>
                `;
                container.querySelector('.mensagem').style.display = 'block';
                return;
            }
            
            // Adiciona os materiais ao container
            reciclaveis.forEach(material => {
                const { id, nome, descricao, precoPorKg, tipo } = material;

                const cardHTML = `
                <div class="card" data-card-id="${id}" style="text-align: center;">
                    <div style="display: flex; justify-content: center;">
                        <img src="https://via.placeholder.com/120" alt="Imagem do Produto" class="card-img-top" style="margin-bottom: 10px;">
                    </div>
                    <h2>${nome}</h2>
                    <p>Descrição: ${descricao}</p>
                    <p>Preço por Kg: R$${precoPorKg.toFixed(2)}</p>
                    <p>Tipo: ${tipo}</p>
                    <button class="select-btn" onclick="solicitarPedido(${id})">Solicitar pedido</button>
                </div>
                `;
                container.insertAdjacentHTML('beforeend', cardHTML);
            });
        } else {
            console.error(`Erro ao listar os recicláveis do tipo ${tipo}:`, response.statusText);
        }
    } catch (error) {
        console.error(`Erro ao listar os recicláveis do tipo ${tipo}:`, error);

        // Em caso de erro, exibe uma mensagem
        container.innerHTML = `
            <div class="mensagem">
                <p><i class="fa-solid fa-triangle-exclamation" style="font-size: 50px; color: #ccc;"></i></p>
                <p>Material não cadastrado.</p>
            </div>
        `;
        container.querySelector('.mensagem').style.display = 'block';
    }
}


// Funções específicas para listar tipos diferentes
function listarPlastico() {
    listarReciclaveisPorTipo('plastico');
}

function listarAluminio() {
    listarReciclaveisPorTipo('aluminio');
}

function listarEletronico() {
    listarReciclaveisPorTipo('eletronico');
}

async function listarPorPreco() {
    const apiUrl = 'http://localhost:8080/reciclavel/listar';

    try {
        const response = await fetch(apiUrl);

        if (response.ok) {
            const data = await response.json();
            const container = document.getElementById('reciclaveis-container');

            // Verificação de existência do elemento
            if (!container) {
                console.error('Elemento reciclaveis-container não encontrado');
                return;
            }

            container.innerHTML = ''; // Limpa o container antes de adicionar os cartões

            const reciclaveis = Array.isArray(data) ? data : data.content || [];

            // Ordena os materiais pelo preço (menor para maior)
            reciclaveis.sort((a, b) => a.precoPorKg - b.precoPorKg);

            // Renderiza os materiais ordenados
            reciclaveis.forEach(material => {
                const { id, nome, descricao, precoPorKg, tipo } = material;

                const cardHTML = `
                <div class="card" data-card-id="${id}" style="text-align: center;">
                    <div style="display: flex; justify-content: center;">
                        <img src="https://via.placeholder.com/120" alt="Imagem do Produto" class="card-img-top" style="margin-bottom: 10px;">
                    </div>
                    <h2>${nome}</h2>
                    <p>Descrição: ${descricao}</p>
                    <p>Preço por Kg: R$${precoPorKg.toFixed(2)}</p>
                    <p>Tipo: ${tipo}</p>
                    <button class="select-btn" onclick="solicitarPedido(${id})">Solicitar pedido</button>
                </div>
                `;
                container.insertAdjacentHTML('beforeend', cardHTML);
            });
        } else {
            console.error('Erro ao listar os recicláveis:', response.statusText);
        }
    } catch (error) {
        console.error('Erro ao listar os recicláveis:', error);
    }
}

document.addEventListener('DOMContentLoaded', listarReciclaveis);