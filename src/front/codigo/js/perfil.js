/**
 * Função principal chamada quando a página é carregada.
 * Verifica o tipo de empresa e carrega os dados correspondentes.
 */
function carregarDadosNoPlaceholder() {
    const id = localStorage.getItem("userId");
    const tipoEmpresa = localStorage.getItem("tipoEmpresa");

    if (!id) {
        window.location.href = "../views/Login.html";
        return;
    }

    const apiURL = definirApiUrl(tipoEmpresa, id);
    const tipoEmpresaHtml = definirTipoEmpresaHtml(tipoEmpresa);

    const tipoDeEmpresaElement = document.getElementById("TipoDeEmpresa");
    if (tipoDeEmpresaElement) {
        tipoDeEmpresaElement.innerHTML = tipoEmpresaHtml;
    }

    carregarDadosEmpresa(apiURL);
}

/**
 * Define a URL da API com base no tipo de empresa.
 */
function definirApiUrl(tipoEmpresa, id) {
    return tipoEmpresa === "VENDEDORA" 
        ? `http://localhost:8080/empresaVendedora/${id}`
        : `http://localhost:8080/empresaCompradora/${id}`;
}

/**
 * Define o rótulo a ser exibido para o tipo de empresa.
 */
function definirTipoEmpresaHtml(tipoEmpresa) {
    return tipoEmpresa === "VENDEDORA" 
        ? "Ponto de Coleta" 
        : "Empresa Parceira";
}

/**
 * Faz uma requisição à API para obter os dados da empresa e carregar os placeholders.
 */
function carregarDadosEmpresa(apiURL) {
    fetch(apiURL)
        .then(response => {
            if (!response.ok) throw new Error("Empresa não encontrada");
            return response.json();
        })
        .then(empresa => {
            atualizarPlaceholdersEmpresa(empresa);
            carregarEndereco(empresa.endereco.id);
        })
        .catch(error => console.error("Erro ao carregar dados da empresa:", error));
}

/**
 * Atualiza os campos do formulário com os dados da empresa.
 */
function atualizarPlaceholdersEmpresa(empresa) {
    const tipoDeEmpresaElement = document.getElementById("TipoDeEmpresa");
    if (tipoDeEmpresaElement) {
        tipoDeEmpresaElement.innerHTML = empresa.tipoEmpresa === "VENDEDORA" ? "Ponto de Coleta" : "Empresa Parceira";
    }

    const campos = [
        { id: "perfil_input_nome", value: empresa.nome },
        { id: "perfil_input_email", value: empresa.email },
        { id: "perfil_input_cnpj", value: empresa.cnpj },
        { id: "perfil_input_tipo", value: empresa.tipoEmpresa },
        { id: "editar_nome", value: empresa.nome },
        { id: "editar_email", value: empresa.email },
        { id: "editar_cnpj", value: empresa.cnpj },
    ];

    campos.forEach(campo => {
        const element = document.getElementById(campo.id);
        if (element) {
            element.value = campo.value || "";
        } else {
            console.error(`Elemento '${campo.id}' não encontrado no DOM.`);
        }
    });
}


/**
 * Faz uma requisição à API para obter os dados do endereço e carregar os placeholders.
 */
function carregarEndereco(idEndereco) {
    const apiUrlEndereco = `http://localhost:8080/endereco/${idEndereco}`;
    
    fetch(apiUrlEndereco)
        .then(response => {
            if (!response.ok) throw new Error("Endereço não encontrado");
            return response.json();
        })
        .then(endereco => atualizarPlaceholdersEndereco(endereco))
        .catch(error => console.error("Erro ao carregar dados do endereço:", error));
}

/**
 * Atualiza os campos do formulário com os dados do endereço.
 */
function atualizarPlaceholdersEndereco(endereco) {
    document.getElementById("perfil_input_cidade").value = endereco.cidade || "";
    document.getElementById("perfil_input_bairro").value = endereco.bairro || "";
    document.getElementById("perfil_input_numero").value = endereco.numero || "";
    document.getElementById("perfil_input_cep").value = endereco.cep || "";
    document.getElementById("perfil_input_estado").value = endereco.estado || "";
    document.getElementById("perfil_input_rua").value = endereco.rua || "";

    document.getElementById("editar_cidade").value = endereco.cidade || "";
    document.getElementById("editar_bairro").value = endereco.bairro || "";
    document.getElementById("editar_numero").value = endereco.numero || "";
    document.getElementById("editar_cep").value = endereco.cep || "";
    document.getElementById("editar_estado").value = endereco.estado || "";
    document.getElementById("editar_rua").value = endereco.rua || "";
}

/**
 * Envia os dados atualizados da empresa para a API correspondente.
 */
function editarPerfil() {
    const id = localStorage.getItem("userId");
    const tipoEmpresa = localStorage.getItem("tipoEmpresa");
    const apiURL = definirApiUrl(tipoEmpresa, id);

    const dadosAtualizados = {
        nome: document.getElementById("editar_nome").value,
        email: document.getElementById("editar_email").value,
        cnpj: document.getElementById("editar_cnpj").value,
        tipoEmpresa: tipoEmpresa,
        endereco: {
            cidade: document.getElementById("editar_cidade").value,
            bairro: document.getElementById("editar_bairro").value,
            numero: document.getElementById("editar_numero").value,
            cep: document.getElementById("editar_cep").value,
            estado: document.getElementById("editar_estado").value,
            rua: document.getElementById("editar_rua").value,
        },
        senha: document.getElementById("editar_confirmarSenha").value,
    };

    fetch(apiURL, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(dadosAtualizados),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao atualizar a empresa.");
            }
            alert("Empresa atualizada com sucesso!");
            window.location.reload();
        })
        .catch(error => console.error("Erro na atualização da empresa:", error));
}

/**
 * Envia uma requisição DELETE para a API para remover a empresa.
 */
function deletarEmpresa() {
    const id = localStorage.getItem("userId");
    const tipoEmpresa = localStorage.getItem("tipoEmpresa");
    const apiURL = definirApiUrl(tipoEmpresa, id);

    if (!id || !tipoEmpresa) {
        console.error("ID ou tipo de empresa não encontrados no localStorage.");
        return;
    }

    if (confirm("Tem certeza de que deseja deletar esta empresa?")) {
        fetch(apiURL, {
            method: "DELETE",
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro ao deletar a empresa.");
                }
                alert("Empresa deletada com sucesso!");
                redirecionarParaPaginaInicial();
            })
            .catch(error => console.error("Erro ao deletar a empresa:", error));
    }
}

/**
 * Redireciona o usuário para a página inicial após a exclusão da empresa.
 */
function redirecionarParaPaginaInicial() {
    window.location.href = "../views/Login.html";
}

/**
 * Inicializa os eventos e funções quando a página estiver carregada.
 */
window.onload = () => {
    carregarDadosNoPlaceholder();
    const botaoSalvar = document.getElementById("botao_salvar");
    if (botaoSalvar) {
        botaoSalvar.addEventListener("click", editarPerfil);
    }

    const botaoExcluir = document.getElementById("botao_excluir");
    if (botaoExcluir) {
        botaoExcluir.addEventListener("click", deletarEmpresa);
    }
};