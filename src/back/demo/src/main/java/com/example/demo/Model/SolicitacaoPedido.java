package com.example.demo.Model;

import com.example.demo.DTO.SolicitacaoPedidoDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Representa uma solicitação pedido de compra de um material realizado por uma
 * empresa compradora, onde o preco de compra é negociavel.
 * <p>
 * A entidade {@code Pedido} contém informações como o material solicitado, a
 * quantidade, e o valor total do pedido.
 * </p>
 */
@Entity(name = "solicitacao_pedido")
public class SolicitacaoPedido {

    /**
     * Identificador único do pedido.
     * <p>
     * O campo {@code id} é gerado automaticamente pela estratégia de geração
     * {@link GenerationType#IDENTITY}.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "observacao")
    private String observacao;
    /**
     * Material associado ao pedido.
     * <p>
     * O campo {@code material} referencia o material solicitado no pedido. Ele é
     * mapeado como uma chave estrangeira que referencia a entidade
     * {@link Material}.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "material_id") // Chave estrangeira para a tabela Material
    @JsonManagedReference
    private Reciclaveis reciclavel;

    /**
     * Preço do material por kg.
     */
    @Column(name = "oferta_preco_kg")
    private double ofertaPrecoKg;
    /**
     * Quantidade do material solicitada no pedido.
     */
    @Column(name = "quantidade")
    private double quantidade;

    /**
     * Empresa compradora associada ao pedido.
     */
    @ManyToOne
    @JoinColumn(name = "empresa_compradora_id")
    @JsonManagedReference
    private EmpresaCompradora empresaCompradora;

    /**
     * Empresa vendedora associada ao pedido.
     */
    @ManyToOne
    @JoinColumn(name = "empresa_vendedora_id")
    @JsonManagedReference
    private EmpresaVendedora empresaVendedora;

    /**
     * Status atual do pedido.
     */

    @Enumerated(EnumType.STRING) // Salva os valores como Strings no banco
    @Column(nullable = false)
    private StatusPedido status;

    /**
     * Construtor padrão da classe Pedido.
     */
    public SolicitacaoPedido() {

    }

    public SolicitacaoPedido(SolicitacaoPedidoDTO solicitacaoPedidoDTO, EmpresaCompradora empresaCompradora,
            Reciclaveis reciclavel) {
        this.reciclavel = reciclavel;
        this.quantidade = solicitacaoPedidoDTO.quantidade;
        this.empresaCompradora = empresaCompradora;
        this.empresaVendedora = reciclavel.getEmpresaVendedora();
        this.status = StatusPedido.ABERTO;
        this.ofertaPrecoKg = solicitacaoPedidoDTO.precoPorKg;
        this.observacao = "Sem observações";
        if (solicitacaoPedidoDTO.observacao != null && !solicitacaoPedidoDTO.observacao.isBlank())
            this.observacao = solicitacaoPedidoDTO.observacao;
    }

    public void RefazerSolicitacao(double quantidade, double precoPorKg) {
        this.quantidade = quantidade;
        this.ofertaPrecoKg = precoPorKg;
    }

    /**
     * Atualiza o status do pedido.
     * 
     * @param status Novo status do pedido.
     */
    public void Negociacao(int op) {
        switch (op) {
            case 1:
                this.status = StatusPedido.NEGOCIACAO;
                break;
            case 2:
                this.status = StatusPedido.NEGADO;
                break;
            case 3:
                this.status = StatusPedido.CANCELADO;
                break;
            case 4:
                this.status = StatusPedido.CONCLUIDO;
                break;
            default:
                throw new IllegalArgumentException("Operação inválida para atualização do status.");
        }
    }

    /**
     * Retorna a empresa compradora associada ao pedido.
     * 
     * @return Empresa compradora.
     */
    public EmpresaCompradora getEmpresaCompradora() {
        return empresaCompradora;
    }

    /**
     * Retorna a empresa vendedora associada ao pedido.
     * 
     * @return Empresa vendedora.
     */
    public EmpresaVendedora getEmpresaVendedora() {
        return empresaVendedora;
    }

    public int getId() {
        return id;
    }

    public Reciclaveis getReciclavel() {
        return reciclavel;
    }

    public void setReciclavel(Reciclaveis reciclavel) {
        this.reciclavel = reciclavel;
    }

    /**
     * Retorna uma representação em string do pedido.
     * 
     * @return String representando o pedido.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{")
                .append("\"idSolicitacao\": \"").append(id).append("\",")
                .append("\"empresaCompradora\": \"").append(empresaCompradora.getId()).append("\",")
                .append("\"pontoDeColeta\": \"").append(empresaVendedora.getId()).append("\",")
                .append("\"quantidade\": ").append(quantidade).append(",") // Remove aspas extras aqui
                .append("\"reciclavel\": \"").append(reciclavel.getId()).append("\",")
                .append("\"observacao\": \"").append(observacao).append("\",")
                .append("\"status\": \"").append(status).append("\"")
                .append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SolicitacaoPedido) {
            SolicitacaoPedido pedido = (SolicitacaoPedido) obj;
            return pedido.id == this.id;
        }
        return false;
    }

    public double getOfertaPrecoKg() {
        return ofertaPrecoKg;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoTotal(Double valorTotal) {
        this.ofertaPrecoKg = valorTotal;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}