// URL da API
const apiUrl = 'http://localhost:8080/indicadores/reducao-residuos';
const apiUrlporcentagem = 'http://localhost:8080/indicadores/contribuicaoBH';
const apiUrlTaxaEntrega = 'http://localhost:8080/indicadores/taxaDeEntrega';

// Função para buscar o indicador do backend
async function porcentagemBH() {
    try {
        const response = await fetch(apiUrlporcentagem);

        if (response.ok) {
            const data = await response.json();
            return parseFloat(data.porcentagem); // Retorna o valor como número
        }
    } catch (error) {
        console.error('Erro ao carregar o indicador:', error);
        return null; // Retorna null em caso de erro
    }
}

async function renderizarGrafico() {
    const porcentagem = await porcentagemBH();
    if (porcentagem === null) {
        console.error('Não foi possível carregar os dados para o gráfico.');
        return;
    }

    const ctx2 = document.getElementById('graficoPizza').getContext('2d');
    new Chart(ctx2, {
        type: 'pie',
        data: {
            labels: ['Materias negociados pela plataforma', 'Média de materias recicláveis em BH'],
            datasets: [{
                label: 'Distribuição',
                data: [porcentagem, 100 - porcentagem],
                backgroundColor: [
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 99, 132, 0.6)'
                ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 99, 132, 1)                                                                              '
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                tooltip: {
                    callbacks: {
                        label: function (tooltipItem) {
                            return `${tooltipItem.label}: ${tooltipItem.raw}%`;
                        }
                    }
                }
            }
        }
    });
}

// Função para buscar o indicador do backend
async function quantidadeTotal() {
    try {
        const response = await fetch(apiUrl);

        if (response.ok) {
            const data = await response.json();
            return parseFloat(data.quantidade); // Retorna o valor como número
        }
    } catch (error) {
        console.error('Erro ao carregar o indicador:', error);
        return null; // Retorna null em caso de erro
    }
}

// Inicializar o gráfico com gradiente
const ctx = document.getElementById('indicatorChart').getContext('2d');
const gradient = ctx.createLinearGradient(0, 0, 0, 400);
gradient.addColorStop(0, 'rgba(75, 192, 192, 0.6)');
gradient.addColorStop(1, 'rgba(75, 192, 192, 0)');

const indicatorChart = new Chart(ctx, {
    type: 'line', // Tipo do gráfico
    data: {
        labels: [], // Datas ou períodos
        datasets: [{
            label: 'Quantidade negociada na plataforma',
            data: [], // Quantidades do indicador
            borderColor: 'rgba(75, 192, 192, 1)', // Cor da linha
            backgroundColor: gradient, // Gradiente no preenchimento
            borderWidth: 3, // Espessura da linha
            tension: 0.3, // Suavização da linha
            fill: true, // Preencher abaixo da linha
            pointRadius: 5, // Tamanho dos pontos
            pointBackgroundColor: 'rgba(75, 192, 192, 1)', // Cor dos pontos
            pointBorderColor: '#fff', // Borda dos pontos
            pointHoverRadius: 7, // Tamanho do ponto no hover
            pointHoverBackgroundColor: '#fff', // Cor no hover
            pointHoverBorderColor: 'rgba(75, 192, 192, 1)' // Borda no hover
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                display: true,
                labels: {
                    color: '#333', // Cor da legenda
                    font: {
                        size: 14, // Tamanho da fonte
                        weight: 'bold' // Peso da fonte
                    }
                }
            },
            tooltip: {
                enabled: true,
                backgroundColor: 'rgba(0,0,0,0.7)',
                titleColor: '#fff',
                bodyColor: '#fff',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
            }
        },
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Evolução (MÊS)',
                    color: '#555',
                    font: {
                        size: 16,
                        weight: 'bold'
                    }
                },
                ticks: {
                    color: '#333',
                    font: {
                        size: 12
                    }
                },
                grid: {
                    color: 'rgba(200, 200, 200, 0.2)',
                    borderDash: [5, 5]
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Quantidade Total (kg)',
                    color: '#555',
                    font: {
                        size: 16,
                        weight: 'bold'
                    }
                },
                ticks: {
                    color: '#333',
                    font: {
                        size: 12
                    }
                },
                grid: {
                    color: 'rgba(200, 200, 200, 0.2)',
                    borderDash: [5, 5]
                }
            }
        }
    }
});

// Função para adicionar dados ao gráfico
function addData(label, value) {
    if (value !== null) { // Adiciona apenas se o valor não for nulo
        indicatorChart.data.labels.push(label); // Adiciona a data/período
        indicatorChart.data.datasets[0].data.push(value); // Adiciona o valor
        indicatorChart.update(); // Atualiza o gráfico
    }
}

// Função assíncrona para agendar a atualização
async function updateChart() {
    const now = new Date().toLocaleString(); // Data/hora da requisição
    const value = await quantidadeTotal(); // Busca o valor do backend
    addData(now, value); // Adiciona os dados ao gráfico
}



// Função para buscar o indicador do backend
async function taxaDeEntrega() {
    try {
        const response = await fetch(apiUrlTaxaEntrega);

        if (response.ok) {
            const data = await response.json();
            return parseFloat(data.porcentagem); // Retorna o valor como número
        }
    } catch (error) {
        console.error('Erro ao carregar o indicador:', error);
        return null; // Retorna null em caso de erro
    }
}

async function renderizarGrafico2() {
    const porcentagemTaxaEntrega = await taxaDeEntrega();
    if (porcentagemTaxaEntrega === null) {
        console.error('Não foi possível carregar os dados para o gráfico.');
        return;
    }

    const ctx3 = document.getElementById('graficoPizza2').getContext('2d');
    new Chart(ctx3, {
        type: 'pie',
        data: {
            labels: ['Materiais requisitados', 'Materiais entregues'],
            datasets: [{
                label: 'Distribuição',
                data: [100 - porcentagemTaxaEntrega, porcentagemTaxaEntrega],
                backgroundColor: [
                    'rgba(192, 75, 75, 0.6)',
                    'rgba(75, 192, 75, 0.6)'
                ],
                borderColor: [
                   'rgba(192, 75, 75, 1)',
                    'rgba(75, 192, 75, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                tooltip: {
                    callbacks: {
                        label: function (tooltipItem) {
                            return `${tooltipItem.label}: ${tooltipItem.raw}%`;
                        }
                    }
                }
            }
        }
    });
}

// Chama a função para renderizar o gráfico
renderizarGrafico();
renderizarGrafico2();
// Configurar o intervalo para executar a atualização diariamente
updateChart();
setInterval(updateChart, 43200000); // Atualizar a cada 12 horas 43200000
