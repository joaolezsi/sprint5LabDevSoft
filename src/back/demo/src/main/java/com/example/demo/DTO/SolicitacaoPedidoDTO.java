package com.example.demo.DTO;

public class SolicitacaoPedidoDTO {

    /**
     * Reciclável associado à solicitação de pedido.
     */
    public int idReciclavel;
    /**
     * Quantidade solicitada.
     */
    public double quantidade;

    public String observacao;
    /**
     * Empresa compradora associada à solicitação de pedido.
     */
    public int idEmpresaCompradora;
    /**
     * Preço por quilograma do material reciclável.
     */
    public double precoPorKg;
}