package com.example.demo.Model;

import com.example.demo.DTO.EmpresaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;

/**
 * Representa uma entidade abstrata de Empresa, que serve como classe base para
 * diferentes tipos de empresas. A classe contém informações básicas como nome,
 * CNPJ, endereço, senha, email e tipo da empresa.
 */
@MappedSuperclass
public class Empresa {

    /**
     * Identificador único da empresa, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Nome da empresa. Não pode ser nulo.
     */
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * CNPJ da empresa. Deve ser único e não nulo.
     */
    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    /**
     * Endereço da empresa, representado por uma associação One-to-One com a
     * entidade Endereco.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    /**
     * Senha de acesso da empresa. Não pode ser nula e deve ter no mínimo 8
     * caracteres.
     */
    @Column(name = "senha", nullable = false)
    private String senha;

    /**
     * Email da empresa. Deve ser único e não nulo.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Tipo da empresa, representado pela enumeração TipoEmpresa.
     */
    @Enumerated(EnumType.STRING) // Armazena como texto
    private TipoEmpresa tipoEmpresa;
    /**
     * Construtor padrão da classe Empresa.
     */
    public Empresa() {
    }

    /**
     * Construtor com parâmetros para inicializar uma instância de Empresa.
     *
     * @param nome Nome da empresa.
     * @param cnpj CNPJ da empresa (deve ter 14 dígitos).
     * @param endereco Endereço da empresa.
     * @param senha Senha de acesso (mínimo 8 caracteres).
     * @param email Email da empresa.
     * @param tipoEmpresa Tipo da empresa.
     * @throws IllegalArgumentException Se o CNPJ ou a senha forem inválidos.
     */
    public Empresa(String nome, String cnpj, Endereco endereco, String senha, String email, TipoEmpresa tipoEmpresa) {
        if (!validarCnpj(cnpj) || !validarSenha(senha)) {
            throw new IllegalArgumentException("Senha ou CNPJ inválidos");
        }
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.senha = senha;
        this.email = email;
        this.tipoEmpresa = tipoEmpresa;
    }

    /**
     * Compara a senha fornecida com a senha armazenada.
     *
     * @param senha A senha a ser comparada.
     * @return {@code true} se as senhas forem iguais; {@code false} caso
     * contrário.
     */
    public boolean CompararSenha(String senha) {
        return this.senha.equals(senha);
    }

    /**
     * Valida se o CNPJ é válido (não nulo e possui 14 dígitos).
     *
     * @param cnpj O CNPJ a ser validado.
     * @return {@code true} se o CNPJ for válido; {@code false} caso contrário.
     */
    private boolean validarCnpj(String cnpj) {
        return !(cnpj.length() != 14);
    }

    /**
     * Valida se a senha possui o comprimento mínimo necessário.
     *
     * @param senha A senha a ser validada.
     * @return {@code true} se a senha tiver 8 ou mais caracteres; {@code false}
     * caso contrário.
     */
    private boolean validarSenha(String senha) {
        return senha.length() >= 8;
    }

    /**
     * Retorna o identificador da empresa.
     *
     * @return O ID da empresa.
     */
    public int getId() {
        return id;
    }

    public void editEmpresa(EmpresaDTO empresaDTO) {
        if (!validarCnpj(cnpj) || !validarSenha(senha)) {
            throw new IllegalArgumentException("Senha ou CNPJ inválidos");
        }
        this.nome = empresaDTO.nome;
        this.cnpj = empresaDTO.cnpj;
        this.senha = empresaDTO.senha;
        this.email = empresaDTO.email;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String enderecoJson = "";
        try {
            enderecoJson = mapper.writeValueAsString(endereco);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "{"
                + "\"nome\": \"" + nome + "\","
                + "\"cnpj\": \"" + cnpj + "\","
                + "\"endereco\": " + enderecoJson + ","
                + "\"senha\": \"" + senha + "\","
                + "\"email\": \"" + email + "\","
                + "\"tipoEmpresa\": \"" + tipoEmpresa + "\""
                + "}";
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
