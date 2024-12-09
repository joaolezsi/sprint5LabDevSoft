package com.example.demo.DTO;

/**
 * Data Transfer Object (DTO) para a entidade {@link Endereco}.
 * <p>
 * A classe {@code EnderecoDTO} é usada para transferir dados de endereço entre
 * o cliente e o servidor. Ela contém informações sobre a rua, bairro, cidade,
 * estado, CEP e número do endereço.
 * </p>
 */
public class EnderecoDTO {

    /**
     * Nome da rua do endereço.
     */
    public String rua;

    /**
     * Nome do bairro do endereço.
     */
    public String bairro;

    /**
     * Nome da cidade do endereço.
     */
    public String cidade;

    /**
     * Nome do estado do endereço.
     */
    public String estado;

    /**
     * Código de Endereçamento Postal (CEP) do endereço.
     */
    public String cep;

    /**
     * Número do imóvel no endereço.
     */
    public int numero;
}
