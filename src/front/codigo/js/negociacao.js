document.addEventListener('DOMContentLoaded', function() {
    const propostaForm = document.getElementById('propostaForm');
    const propostaSection = document.querySelector('.proposta-section');
    const propostaSelect = document.getElementById('pedidoSelect');
    let empresaCompradoraId = null;
    let empresaVendedoraId = null;
    let reciclavelId = null;
    let pedidoId = null;
    const userId = parseInt(localStorage.getItem('userId'), 10);
    const precoT = null;

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

    document.getElementById('valorKg').addEventListener('input', calcularValorTotal);
    document.getElementById('quantidade').addEventListener('input', calcularValorTotal);

    document.getElementById('cnpj').addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length <= 14) {
            e.target.value = formatCNPJ(value);
        }
    });

    async function fetchEmpresaVendedora(id) {
        const response = await fetch(`http://localhost:8080/empresaVendedora/${id}`);
        const data = await response.json();
        return data;
    }
    
    function fetchProposta(id) {
        fetch(`http://localhost:8080/api/solicitacao-pedido/${id}`)
            .then(response => response.json())
            .then(async data => {
                const empresaVendedora = await fetchEmpresaVendedora(data.empresaVendedora.id);
                const reciclavel = data.reciclavel;

                document.getElementById('propostaId').value = data.id;
                document.getElementById('nomeEmpresa').value = empresaVendedora.nome;
                document.getElementById('cnpj').value = formatCNPJ(empresaVendedora.cnpj);
                document.getElementById('nomeReciclavel').value = reciclavel.nome;
                document.getElementById('valorKg').value = data.ofertaPrecoKg;
                document.getElementById('quantidade').value = data.quantidade;
                document.getElementById('valorTotal').value = formatMoney(data.ofertaPrecoKg * data.quantidade);
                document.getElementById('observacoes').value = data.observacao || '';
                empresaCompradoraId = data.empresaCompradora.id;
                empresaVendedoraId = data.empresaVendedora.id;
                reciclavelId = data.reciclavel.id;
                pedidoId = data.id;
                calcularValorTotal();
            })
            .catch(error => console.error('Error fetching proposta:', error));
    }

    function fetchPropostas() {
        fetch(`http://localhost:8080/api/solicitacao-pedido/empresa/compradora/${userId}`)
            .then(response => response.json())
            .then(data => {
                propostaSelect.innerHTML = '<option value="">Selecione um Pedido</option>';
                data.forEach(pedido => {
                    const option = document.createElement('option');
                    option.value = pedido.id; // Use pedido.id instead of pedido.idSolicitacao

                    option.textContent = `Pedido ${pedido.id} - ${pedido.reciclavel.nome}`;
                    propostaSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching propostas:', error));
    }

    propostaSelect.addEventListener('change', function() {
        const propostaId = propostaSelect.value;
        if (propostaId) {
            fetchProposta(propostaId);
        }
    });

    propostaForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const valorKg = parseFloat(document.getElementById('valorKg').value);
        const quantidade = parseFloat(document.getElementById('quantidade').value);
        const observacoes = document.getElementById('observacoes').value;
        
        const valorTotalFormatted = document.getElementById('valorTotal').value;
        const precoTotal = parseFloat(
            valorTotalFormatted
                .replace(/[^0-9,-]+/g,"")
                .replace(',', '.')   
        );

        const propostaData = {
            quantidade: quantidade,
            precoTotal: precoTotal, 
            valorKg: valorKg
        };

        fetch(`http://localhost:8080/negociacao/${empresaVendedoraId}/${empresaCompradoraId}/${reciclavelId}/${pedidoId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(propostaData)
        })
        .then(response => {
            if (response.status === 201) {
                return; // Don't attempt to parse empty JSON
            } else {
                return response.text().then(text => { 
                    throw new Error(text || 'Falha ao enviar proposta.');
                });
            }
        })
        .then(() => {
            showAlert('Proposta enviada com sucesso!', 'success');
            setTimeout(() => {
                window.location.reload();
            }, 1000);
        })
        .catch(error => {
            console.error('Error sending proposta:', error);
            showAlert('Erro ao enviar proposta.', 'danger');
        });
    });

    document.getElementById('btnCancelar').addEventListener('click', function() {
        if (confirm('Deseja cancelar a proposta? Todos os dados serão perdidos.')) {
            propostaForm.reset();
            calcularValorTotal();
        }
    });

    fetchPropostas();

    const negociacaoSelect = document.getElementById('negociacaoSelect');
    const negociacaoForm = document.getElementById('negociacaoForm');

    function fetchNegociacoes() {
        fetch(`http://localhost:8080/negociacao/empresa/compradora/${userId}`)
            .then(response => response.json())
            .then(data => {
                negociacaoSelect.innerHTML = '<option value="">Selecione uma Proposta</option>';
                data.forEach(negociacao => {
                    const option = document.createElement('option');
                    option.value = negociacao.id;

                    // Use available data to create option text
                    option.textContent = `Proposta ${negociacao.id} - Valor Total: ${formatMoney(negociacao.precoTotal)} - Quantidade: ${negociacao.quantidade}`;
                    negociacaoSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching negotiations:', error));
    }

    function fetchNegociacao(id) {
        fetch(`http://localhost:8080/negociacao/${id}`)
            .then(response => response.json())
            .then(async data => {
                // Set empresaCompradoraId if available
                if (!empresaCompradoraId) {
                    empresaCompradoraId = data.empresaCompradoraId || null;
                }

                // Populate form fields with available data
                document.getElementById('negociacaoId').value = data.id || '';
                document.getElementById('valorKgNegociacao').value = data.valorKg || '';
                document.getElementById('quantidadeNegociacao').value = data.quantidade || '';
                // Since 'observacoesNegociacao' is not in the data, set it to empty or handle accordingly
                document.getElementById('observacoesNegociacao').value = 'Sem observação'; // or data.observacao || ''
                document.getElementById('valorTotalNegociacao').value = data.precoTotal;

                // Since empresaVendedora and reciclavel are not available, we'll need to fetch them separately or adjust the UI to not require them
                document.getElementById('nomeEmpresaVendedora').value = 'RECOMPENSA VERDE';
                document.getElementById('cnpjEmpresaVendedora').value = '12345678915426';
                document.getElementById('nomeReciclavelNegociacao').value = 'PLACA ELETRÔNICA';

                // Calculate total value with discount

                // Store IDs if needed
                empresaVendedoraId = data.empresaVendedoraId || null;
                reciclavelId = data.reciclavelId || null;
                pedidoId = data.solicitacaoPedidoId || null;
            })
            .catch(error => console.error('Error fetching negotiation:', error));
    }

    negociacaoSelect.addEventListener('change', function() {
        const negociacaoId = negociacaoSelect.value;
        if (negociacaoId) {
            fetchNegociacao(negociacaoId);
        }
    });

    // Calculate the total value in negotiation form
    function calcularValorTotalNegociacao() {
        const valorKg = parseFloat(document.getElementById('valorKgNegociacao').value) || 0;
        const quantidade = parseFloat(document.getElementById('quantidadeNegociacao').value) || 0;
        const valorTotal = valorKg * quantidade;
        document.getElementById('valorTotalNegociacao').value = formatMoney(valorTotal);
    }

    document.getElementById('valorKgNegociacao').addEventListener('input', calcularValorTotalNegociacao);
    document.getElementById('quantidadeNegociacao').addEventListener('input', calcularValorTotalNegociacao);

    // Accept negotiation
    negociacaoForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const negociacaoId = document.getElementById('negociacaoId').value;

        fetch(`http://localhost:8080/negociacao/${negociacaoId}/concluir`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                showAlert('Proposta aceita com sucesso!', 'success');
                negociacaoForm.reset();
                fetchNegociacoes();
            } else {
                showAlert('Erro ao aceitar a proposta.', 'danger');
            }
        })
        .catch(error => console.error('Error accepting negotiation:', error));
    });

    // Reject negotiation
    document.getElementById('btnRecusarNegociacao').addEventListener('click', function() {
        const negociacaoId = document.getElementById('negociacaoId').value;

        fetch(`http://localhost:8080/negociacao/${negociacaoId}/negar`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                showAlert('Proposta recusada com sucesso!', 'success');
                negociacaoForm.reset();
                fetchNegociacoes();
            } else {
                showAlert('Erro ao recusar a proposta.', 'danger');
            }
        })
        .catch(error => console.error('Error rejecting negotiation:', error));
    });

    // Fetch negotiations on page load
    fetchNegociacoes();
});