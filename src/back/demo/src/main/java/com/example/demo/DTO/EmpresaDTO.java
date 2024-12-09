package com.example.demo.DTO;

import com.example.demo.Model.TipoEmpresa;

/**
 * Data Transfer Object (DTO) para a entidade {@link Empresa}.
 * <p>
 * A classe {@code EmpresaDTO} é usada para transferir dados de empresas entre o
 * cliente e o servidor. Ela inclui informações básicas sobre a empresa, como
 * nome, CNPJ, endereço, senha, email, e o tipo de empresa.
 * </p>
 *
 * @see TipoEmpresa
 * @see EnderecoDTO
 */
public class EmpresaDTO {

    /**
     * Nome da empresa.
     */
    public String nome;

    /**
     * CNPJ da empresa.
     */
    public String cnpj;

    /**
     * Endereço da empresa, representado por um {@link EnderecoDTO}.
     */
    public EnderecoDTO endereco;

    /**
     * Senha da conta da empresa.
     */
    public String senha;

    /**
     * Endereço de email da empresa.
     */
    public String email;

    /**
     * Tipo da empresa, que pode ser {@code COMPRADORA} ou {@code VENDEDORA}.
     * <p>
     * Este campo utiliza o enum {@link TipoEmpresa} para identificar se a
     * empresa é compradora ou vendedora.
     * </p>
     */
    public TipoEmpresa tipoEmpresa;
}
