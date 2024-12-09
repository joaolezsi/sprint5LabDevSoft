document.getElementById("botao_login").addEventListener("click", async function () {
    const email = document.querySelector("#input_email_login").value.trim();
    const senha = document.querySelector("#input_senha_login").value.trim();
    const empresaVendedora = document.getElementById("checkbox_empresa_vendedora").checked;
    const empresaCompradora = document.getElementById("checkbox_empresa_compradora").checked;
    const alertaDiv = document.getElementById("alerta");

    if (!email || (!empresaVendedora && !empresaCompradora) || senha.lenght > 8) {
        alertaDiv.innerHTML = `
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Danger:">
                    <use xlink:href="#exclamation-triangle-fill"/>
                </svg>
                <div class="alert-message">
                    Todos os campos devem ser preenchidos corretamente.
                </div>
            </div>`;
    } else if (empresaVendedora && empresaCompradora) {
        alertaDiv.innerHTML = `
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Danger:">
                    <use xlink:href="#exclamation-triangle-fill"/>
                </svg>
                <div class="alert-message">
                    Selecione apenas uma opção.
                </div>
            </div>`;
    } else {

        const tipoEmpresa = empresaVendedora ? "VENDEDORA" : "COMPRADORA";
        const isLoggedIn = await login(email, senha, tipoEmpresa); // Adicionando await
        if (isLoggedIn) {
            alertaDiv.innerHTML = `
            <div class="alert alert-success d-flex align-items-center" role="alert">
                <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Success:">
                    <use xlink:href="#check-circle-fill"/>
                </svg>
                <div>Cadastro válido! Prosseguindo...</div>
            </div>`;
            window.location.href = "perfil.html";
        }else if(!isLoggedIn){
            alertaDiv.innerHTML = `
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Success:">
                    <use xlink:href="#check-circle-fill"/>
                </svg>
                <div>Senha ou email invalidos</div>
            </div>`;
        }
    }
    alertaDiv.style.display = 'block';
    setTimeout(() => {
        alertaDiv.style.display = 'none';
    }, 5000);
});


async function login(email, senha, tipoEmpresa) {
    const loginDTO = {
        email: email,
        senha: senha,
        tipoEmpresa: tipoEmpresa
    };

    try {
        const response = await fetch(definirApiUrl(tipoEmpresa), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginDTO)
        });

        const result = await response.json();

        if (response.ok) {
            if (result.message === "Email ou senha inválidos") { // Comparação da mensagem
                return false;
            } else if (result.id) {
                console.log(result.id); // Log do id do usuário
                // Limpar todo o localStorage
                localStorage.clear();
                // Adicionar o id do usuário ao localStorage
                localStorage.setItem('userId', result.id);
                localStorage.setItem('tipoEmpresa', tipoEmpresa);
                return true; // Login bem-sucedido
            } else {
                return false; // Login falhou
            }
        } else {
            console.error(result.message); // Log da mensagem de erro do servidor
            return false; // Login falhou
        }
    } catch (error) {
        console.error("Erro: ", error); // Log do erro
        return false; // Retornar false em caso de erro
    }
}

function definirApiUrl(tipoEmpresa) {
    return tipoEmpresa === "VENDEDORA" 
        ? `http://localhost:8080/empresaVendedora/login`
        : `http://localhost:8080/empresaCompradora/login`;
}