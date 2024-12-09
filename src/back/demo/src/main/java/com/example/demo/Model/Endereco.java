package com.example.demo.Model;

import com.example.demo.DTO.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * Representa um endereço que contém informações detalhadas como rua, bairro,
 * cidade, estado, CEP e número.
 * <p>
 * A entidade {@code Endereco} é usada para armazenar e representar dados
 * relacionados à localização física de uma empresa ou pessoa.
 * </p>
 */
@Entity
public class Endereco {

    /**
     * Identificador único do endereço.
     * <p>
     * O campo {@code id} é gerado automaticamente pela estratégia de geração
     * {@link GenerationType#IDENTITY}.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nome da rua do endereço.
     */
    @Column(name = "rua")
    private String rua;

    /**
     * Nome do bairro do endereço.
     */
    @Column(name = "bairro")
    private String bairro;

    /**
     * Nome da cidade do endereço.
     */
    @Column(name = "cidade")
    private String cidade;

    /**
     * Nome do estado do endereço.
     */
    @Column(name = "estado")
    private String estado;

    /**
     * Código postal (CEP) do endereço.
     */
    @Column(name = "cep")
    private String cep;

    /**
     * Número do endereço.
     */
    @Column(name = "numero")
    private int numero;

    @JsonIgnore
    @OneToOne(mappedBy = "endereco") // Se precisar de bidirecionalidade
    private EmpresaVendedora empresaVendedora;

    @JsonIgnore
    @OneToOne(mappedBy = "endereco")
    private EmpresaCompradora empresaCompradora;

    /**
     * Construtor padrão para a classe {@code Endereco}.
     * <p>
     * Este construtor é usado para criar um novo endereço sem valores
     * predefinidos.
     * </p>
     */
    public Endereco() {
    }

    /**
     * Constrói uma nova instância de {@code Endereco} com os detalhes
     * especificados.
     *
     * @param rua o nome da rua
     * @param numero o número do endereço
     * @param bairro o nome do bairro
     * @param cidade o nome da cidade
     * @param estado o nome do estado
     * @param cep o código postal (CEP)
     */
    public Endereco(String rua, int numero, String bairro, String cidade, String estado, String cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.numero = numero;
    }

    /**
     * Retorna uma representação em forma de texto do endereço.
     * <p>
     * O método {@code toString} formata o endereço em uma string legível,
     * contendo rua, bairro, cidade, estado e CEP.
     * </p>
     *
     * @return uma string representando o endereço.
     */
    @Override
    public String toString() {
        return "{"
                + "\"rua\": \"" + rua + "\","
                + "\"bairro\": \"" + bairro + "\","
                + "\"cidade\": \"" + cidade + "\","
                + "\"estado\": \"" + estado + "\","
                + "\"cep\": \"" + cep + "\","
                + "\"numero\": " + numero
                + "}";
    }

    public void editEndereco(EnderecoDTO enderecoDTO) {
        this.rua = enderecoDTO.rua;
        this.numero = enderecoDTO.numero;
        this.bairro = enderecoDTO.bairro;
        this.cidade = enderecoDTO.cidade;
        this.estado = enderecoDTO.estado;
        this.cep = enderecoDTO.cep;
    }

    public int getId() {
        return id;
    }
}
