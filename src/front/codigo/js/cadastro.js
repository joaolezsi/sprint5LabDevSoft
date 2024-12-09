

document.getElementById("selecionar_pontoColeta").addEventListener("click", function(event) {
  event.preventDefault(); // Evita o comportamento padrão do link
  
  // Desmarcar o checkbox da empresa
  document.getElementById("checkbox_empresa").checked = false;

  // Marcar o checkbox do ponto de coleta
  document.getElementById("checkbox_pontoColeta").checked = true;

  // Mudar o estilo do <a> para verde mais escuro (background e border)
  this.style.backgroundColor = "#047a08";
  this.style.border = "1px solid #047a08";
  this.style.color = "white"; // Cor do texto

  // Mudar o estilo do <a> outra opção para padrão (background e border)
  outraOpcao = document.getElementById("selecionar_empresa");
  outraOpcao.style.backgroundColor = "#4caf4f";//fundo
  outraOpcao.style.border = "1px solid #4caf4f";
  outraOpcao.style.color = "white"; // Cor do texto
  const modalElement = document.getElementById("fullscreenModal");
  const modalInstance = bootstrap.Modal.getInstance(modalElement);
  
  if (modalInstance) 
    modalInstance.hide(); // Fecha o modal corretamente
});


document.getElementById("selecionar_empresa").addEventListener("click", function(event) {
  event.preventDefault(); // Evita o comportamento padrão do link
  
  // Desmarcar o checkbox do ponto de coleta
  document.getElementById("checkbox_pontoColeta").checked = false;

  // Marcar o checkbox da Empresa
  document.getElementById("checkbox_empresa").checked = true;

  // Mudar o estilo do <a> para verde mais escuro (background e border)
  this.style.backgroundColor = "#047a08";
  this.style.border = "1px solid #047a08";
  this.style.color = "white"; // Cor do texto


  // Mudar o estilo do <a> para padrão (background e border)
  outraOpcao = document.getElementById("selecionar_pontoColeta");
  outraOpcao.style.backgroundColor = "#4caf4f";//fundo
  outraOpcao.style.border = "1px solid #4caf4f";
  outraOpcao.style.color = "white"; // Cor do texto
  const modalElement = document.getElementById("fullscreenModal");
  const modalInstance = bootstrap.Modal.getInstance(modalElement);
  
  if (modalInstance) 
    modalInstance.hide(); // Fecha o modal corretamente
});

let iniciouVerificacao = false;

function validaSenha() {
const senha = document.getElementById('input_senha').value;
const confirmarSenha = document.getElementById('input_confirmarSenha').value;
const confirmarSenhaInput = document.getElementById('input_confirmarSenha');

if (!iniciouVerificacao) {
  iniciouVerificacao = true; // Começa a verificação após a primeira digitação
  return true;
}

if (confirmarSenha !== senha) {
  confirmarSenhaInput.setCustomValidity('As senhas não coincidem');
  confirmarSenhaInput.reportValidity(); // Exibe a mensagem de erro
  return false;
} else {
  confirmarSenhaInput.setCustomValidity(''); // Reseta a validade
  return true;
}
}

// Função para exibir alerta na tela por 5 segundos
function exibirAlerta(tipo, mensagem) {
  const alertContainer = document.querySelector('#alert-container');

  // Criar o elemento de alerta com base no tipo (danger ou success)
  const alertDiv = document.createElement('div');
  alertDiv.className = `alert alert-${tipo} d-flex align-items-center fade show`;
  alertDiv.role = 'alert';
  alertDiv.innerHTML = `
    <svg class="bi flex-shrink-0 me-2" role="img" aria-label="${tipo === 'danger' ? 'Danger' : 'Success'}:">
      <use xlink:href="#${tipo === 'danger' ? 'exclamation-triangle-fill' : 'check-circle-fill'}"/>
    </svg>
    <div>${mensagem}</div>
  `;

  // Inserir o alerta no contêiner
  alertContainer.appendChild(alertDiv);

  // Remover o alerta após 5 segundos
  setTimeout(() => {
    alertDiv.classList.remove('show');
    alertDiv.classList.add('hide');
    setTimeout(() => {
      alertDiv.remove();
    }, 500); // Tempo para a animação de ocultar
  }, 5000); // O alerta fica visível por 5 segundos
}

document.getElementById("botao_continuar").addEventListener("click", async function () {
  const nome = document.querySelector("#input_nome").value.trim();
  const email = document.querySelector("#input_email").value.trim();
  let cnpj = document.querySelector("#input_cnpj").value.trim();
  const cidade = document.querySelector("#input_cidade").value.trim();
  const bairro = document.querySelector("#input_bairro").value.trim();
  const numero = parseInt(document.querySelector("#input_numero").value.trim());
  const cep = document.querySelector("#input_cep").value.trim();
  const checkboxEmpresa = document.getElementById("checkbox_empresa").checked;
  const checkboxPontoColeta = document.getElementById("checkbox_pontoColeta").checked;
  const senha = document.getElementById("input_senha").value.trim();
  const rua = document.getElementById("input_rua").value.trim();
  const estado = document.getElementById("input_estado").value.trim();
  let alertaDiv = document.getElementById("alerta");

  // Remove caracteres especiais do CNPJ
  cnpj = removerCaracteresEspeciais(cnpj);

  // Valida o CNPJ
  if (!estado || !rua || !nome || !email || !cnpj || !cidade || !bairro || isNaN(numero) || !cep ||
      (!checkboxEmpresa && !checkboxPontoColeta) || !validaSenha()) {
      alertaDiv.innerHTML = `
          <div class="alert alert-danger d-flex align-items-center" role="alert">
              <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Danger:">
                  <use xlink:href="#exclamation-triangle-fill"/>
              </svg>
              <div class="alert-message">
                  Todos os campos devem ser preenchidos corretamente.
              </div>
          </div>`;
  }else {
      alertaDiv.innerHTML = `
          <div class="alert alert-success d-flex align-items-center" role="alert">
              <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Success:">
                  <use xlink:href="#check-circle-fill"/>
              </svg>
              <div>Cadastro válido! Prosseguindo...</div>
          </div>`;

      const tipoEmpresa = checkboxEmpresa ? "COMPRADORA" : "VENDEDORA";
      
      const enderecoDTO = {
          rua: rua,
          bairro: bairro,
          cidade: cidade,
          estado: estado,
          cep: cep,
          numero: numero
      };

      const empresaDTO = {
          nome: nome,
          cnpj: cnpj,
          endereco: enderecoDTO,
          senha: senha,
          email: email,
          tipoEmpresa: tipoEmpresa
      };

      criarEmpresa(empresaDTO, tipoEmpresa);
  }

  alertaDiv.style.display = 'block';
  setTimeout(() => {
      alertaDiv.style.display = 'none';
  }, 5000);
  window.location.href = "../views/Login.html";
});


// Função para fazer a requisição POST
async function criarEmpresa(empresaDTO, tipoEmpresa) {
  const apiUrl = definirApiUrl(tipoEmpresa);

  try {
      const response = await fetch(apiUrl, {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(empresaDTO)
      });

      if (response.ok) {
          const data = await response.json();
          console.log('Empresa adicionada:', data);
      } else {
          console.error('Erro ao adicionar empresa:', response.status, response.statusText);
      }
  } catch (error) {
      console.error('Erro na requisição:', error);
  }
}

function removerCaracteresEspeciais(texto) {
  return texto.replace(/[^\p{L}\p{N}\s]/gu, '');
}
function definirApiUrl(tipoEmpresa) {
  return tipoEmpresa === "VENDEDORA" 
      ? `http://localhost:8080/empresaVendedora`
      : `http://localhost:8080/empresaCompradora`;
}

