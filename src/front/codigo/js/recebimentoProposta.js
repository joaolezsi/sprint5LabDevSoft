document.addEventListener('DOMContentLoaded', function () {
    const propostaForm = document.getElementById('propostaForm');
    const propostaSelect = document.getElementById('propostaSelect');
    let empresaCompradoraId = null;
    const userId = parseInt(localStorage.getItem('userId'), 10);

    function formatCNPJ(cnpj) {
        return cnpj.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, "$1.$2.$3/$4-$5");
    }

    function formatMoney(value) {
        return new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(value);
    }

    function showAlert(message, type = 'success') {
        const alertContainer = document.getElementById('alerta');
        alertContainer.innerHTML = `
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
        alertContainer.style.display = 'block';

        setTimeout(() => {
            alertContainer.style.display = 'none';
        }, 5000);
    }

    async function verificarDesconto(precoTotal, empresaCompradoraId) {
        const response = await fetch(`http://localhost:8080/negociacao/aplicarDesconto/${empresaCompradoraId}/${precoTotal}`);
        const precoComDesconto = await response.json();

        const descontoInfo = document.querySelector('.desconto-info');
        
        if (precoComDesconto < precoTotal) {
            descontoInfo.classList.remove('d-none');
        } else {
            descontoInfo.classList.add('d-none');
        }

        return precoComDesconto;
    }

    async function calcularValorTotal() {
        const valorKg = parseFloat(document.getElementById('valorKg').value) || 0;
        const quantidade = parseFloat(document.getElementById('quantidade').value) || 0;
        const precoTotal = valorKg * quantidade;
        const precoComDesconto = await verificarDesconto(precoTotal, empresaCompradoraId);
        
        document.getElementById('valorTotal').value = formatMoney(precoComDesconto);
    }

    async function fetchPropostas() {
        try {
            const response = await fetch(`http://localhost:8080/negociacao/empresa/${userId}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            propostaSelect.innerHTML = '<option value="">Selecione uma Proposta</option>';
            data.forEach(proposta => {
                const empresaId = proposta.idEmpresaCompradora;
                empresaCompradoraId = proposta.idEmpresaCompradora;
                const empresaData = fetchEmpresaCompradora(empresaId);
                const empresaNome = empresaData.nome;
                const option = document.createElement('option');
                option.value = proposta.id;
                option.textContent = `Proposta ${proposta.id} - ${proposta.idNomeReciclavel}`;
                propostaSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Error fetching propostas:', error);
        }
    }

    async function fetchEmpresaCompradora(id) {
        const response = await fetch(`http://localhost:8080/empresaCompradora/${id}`);
        const data = await response.json();
        return data;
    }

    async function fetchProposta(id) {
        fetch(`http://localhost:8080/negociacao/${id}`)
            .then(response => response.json())
            .then(async data => {
                const empresaCompradora = await fetchEmpresaCompradora(empresaCompradoraId);

                document.getElementById('nomeEmpresa').value = empresaCompradora.nome;
                document.getElementById('cnpj').value = formatCNPJ(empresaCompradora.cnpj);
                document.getElementById('propostaId').value = data.id;
                document.getElementById('valorKg').value = data.valorKg;
                document.getElementById('quantidade').value = data.quantidade;
                document.getElementById('valorTotal').value = formatMoney(data.precoTotal);
                document.getElementById('observacoes').value = data.observacoes;

                calcularValorTotal(); // Recalculate the total
            })
            .catch(error => console.error('Error fetching proposta:', error));
    }

    propostaSelect.addEventListener('change', function() {
        const propostaId = propostaSelect.value;
        if (propostaId) {
            fetchProposta(propostaId);
        }
    });

    propostaForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const propostaId = document.getElementById('propostaId').value;

        fetch(`http://localhost:8080/negociacao/${propostaId}/concluir`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                showAlert('Proposta aceita com sucesso!', 'success');
                propostaForm.reset();
                fetchPropostas();
            })
            .catch(error => console.error('Error accepting proposta:', error));
    });

    document.getElementById('btnRecusar').addEventListener('click', function () {
        const propostaId = document.getElementById('propostaId').value;
        fetch(`http://localhost:8080/negociacao/${propostaId}/negar`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                showAlert('Proposta recusada com sucesso!', 'success');
                propostaForm.reset();
                fetchPropostas();
            })
            .catch(error => console.error('Error recusando proposta:', error));
    });

    document.getElementById('btnContraproposta').addEventListener('click', function(e) {
        e.preventDefault();

        const propostaId = document.getElementById('propostaId').value;
        const valorKg = parseFloat(document.getElementById('valorKg').value);
        const quantidade = parseFloat(document.getElementById('quantidade').value);

        const negociacaoData = {
            valorKg: valorKg,
            quantidade: quantidade,
            valorTotal: valorKg * quantidade
        };

        fetch(`http://localhost:8080/negociacao/${propostaId}/negociacao`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(negociacaoData)
            })
            .then(response => response.json())
            .then(data => {
                showAlert('Proposta enviada com sucesso.');
                calcularValorTotal();
            })
            .catch(error => console.error('Error sending proposta:', error));
    });

    // Add event listeners for value changes
    document.getElementById('valorKg').addEventListener('input', calcularValorTotal);
    document.getElementById('quantidade').addEventListener('input', calcularValorTotal);

    document.getElementById('cnpj').addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length <= 14) {
            e.target.value = formatCNPJ(value);
        }
    });

    fetchPropostas();
});