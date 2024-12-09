const empresa = localStorage.getItem('userId');
const tipoEmpresa = localStorage.getItem('tipoEmpresa');

// Função para ocultar ou exibir os itens com base no tipo da empresa
async function verificarTipoDeRestricoes() {
    const exibirReciclaveis = document.getElementById("exibirReciclaveis");
    const cadastrarReciclaveis = document.getElementById("cadastrarReciclaveis");
    const receberProposta = document.getElementById("receberProposta");
    const receberSolicitacao = document.getElementById("receberSolicitacao");
    const enviarSolicitacao = document.getElementById("enviarProposta");

    try {
        if (tipoEmpresa === 'COMPRADORA') {
            exibirReciclaveis?.classList.remove("d-none");
            enviarSolicitacao?.classList.remove("d-none");
            cadastrarReciclaveis?.classList.add("d-none");
            receberProposta?.classList.add("d-none");
            receberSolicitacao?.classList.add("d-none");
        } else if (tipoEmpresa === 'VENDEDORA') {
            enviarSolicitacao?.classList.add("d-none");
            exibirReciclaveis?.classList.add("d-none");
            cadastrarReciclaveis?.classList.remove("d-none");
            receberProposta?.classList.remove("d-none");
            receberSolicitacao?.classList.remove("d-none");
        }
    } catch (error) {
        console.error("Erro ao manipular restrições:", error);
    }
}

// Função para verificar se o usuário está logado
async function verificarLogin() {
    // Retorna true se o tipo da empresa for válido
    return tipoEmpresa === "COMPRADORA" || tipoEmpresa === "VENDEDORA";
}

// Função para limpar registros no logout
async function removerRegistros() {
    alert("Saindo da conta");
    // Remove os itens específicos do Local Storage
    localStorage.removeItem("userId");
    localStorage.removeItem("tipoEmpresa");

    console.log("Registros removidos do Local Storage.");
    // Redireciona para a página de login
    window.location.href = "../views/Login.html";
}

// Lógica para alternar entre os botões e o dropdown
document.addEventListener("DOMContentLoaded", async () => {
    const authButtons = document.getElementById("auth-buttons");
    const userDropdown = document.getElementById("user-dropdown");

    if (await verificarLogin()) {
        authButtons.classList.add("d-none");
        userDropdown.classList.remove("d-none");
        await verificarTipoDeRestricoes();
    } else {
        authButtons.classList.remove("d-none");
        userDropdown.classList.add("d-none");
    }
});

document.getElementById('logout')?.addEventListener('click', removerRegistros);