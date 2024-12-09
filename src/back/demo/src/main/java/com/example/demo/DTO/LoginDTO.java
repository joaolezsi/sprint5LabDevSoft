package com.example.demo.DTO;

import com.example.demo.Model.TipoEmpresa;

/**
 * Data Transfer Object (DTO) para autenticação de empresas.
 * <p>
 * A classe {@code LoginDTO} é utilizada para transferir dados de login entre o
 * cliente e o servidor. Ela contém informações como o email, a senha e o tipo
 * da empresa que está tentando se autenticar.
 * </p>
 *
 * @see TipoEmpresa
 */
public class LoginDTO {

    /**
     * Endereço de email da empresa que está tentando se autenticar.
     */
    public String email;

    /**
     * Senha da conta da empresa que está tentando se autenticar.
     */
    public String senha;

    /**
     * Tipo da empresa, que pode ser {@code COMPRADORA} ou {@code VENDEDORA}.
     * <p>
     * Este campo utiliza o enum {@link TipoEmpresa} para identificar o tipo de
     * empresa que está realizando o login.
     * </p>
     */
    public TipoEmpresa tipoEmpresa;
}
