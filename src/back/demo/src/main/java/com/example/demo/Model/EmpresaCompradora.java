package com.example.demo.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * Representa uma entidade de empresa compradora. Esta classe estende a classe
 * {@link Empresa} e inclui atributos adicionais específicos para uma empresa
 * compradora.
 * <p>
 * A empresa compradora é responsável por gerenciar uma lista de pedidos
 * associados.
 * </p>
 *
 * @see Empresa
 */
@Entity(name = "empresa_compradora")
public class EmpresaCompradora extends Empresa {

    /**
     * Lista de pedidos associados à empresa compradora.
     * <p>
     * O campo {@code pedidos} armazena todos os pedidos (instâncias de
     * {@link Pedido}) realizados ou relacionados a esta empresa compradora.
     * </p>
     */
    @OneToMany(mappedBy = "empresaCompradora")
    @JsonIgnore
    private List<SolicitacaoPedido> pedidos;

    /**
     * Constrói uma nova instância de EmpresaCompradora com os detalhes
     * especificados.
     *
     * @param nome o nome da empresa
     * @param cnpj o CNPJ (Cadastro Nacional da Pessoa Jurídica) da empresa
     * @param endereco o endereço da empresa
     * @param senha a senha para a conta da empresa
     * @param email o endereço de e-mail da empresa
     * @param tipoEmpresa o tipo da empresa, representado por uma instância de
     * {@link TipoEmpresa}
     */
    public EmpresaCompradora(String nome, String cnpj, Endereco endereco, String senha, String email, TipoEmpresa tipoEmpresa) {
        super(nome, cnpj, endereco, senha, email, tipoEmpresa);
    }

    /**
     * Construtor padrão para EmpresaCompradora.
     * <p>
     * Este construtor é utilizado quando não são fornecidos detalhes
     * específicos e uma entidade de empresa em branco precisa ser criada. Ele
     * inicializa o objeto sem valores predefinidos.
     * </p>
     */
    public EmpresaCompradora() {
    }

    public List<SolicitacaoPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<SolicitacaoPedido> pedidos) {
        this.pedidos = pedidos;
    }
}
