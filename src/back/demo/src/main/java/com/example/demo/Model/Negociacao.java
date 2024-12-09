package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "negociacao")
public class Negociacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "empresa_vendedora_id")
    @JsonIgnore
    private EmpresaVendedora empresaVendedora;

    @ManyToOne
    @JoinColumn(name = "empresa_compradora_id")
    @JsonIgnore
    private EmpresaCompradora empresaCompradora;

    @ManyToOne
    @JoinColumn(name = "reciclavel_id")
    @JsonIgnore
    private Reciclaveis reciclavel;

    @OneToOne
    @JoinColumn(name = "solicitacao_pedido_id")
    @JsonIgnore
    private SolicitacaoPedido solicitacaoPedido;

    private int quantidade;
    private double precoTotal;
    private double valorKg;

    @Temporal(TemporalType.DATE)
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusPedido status;

    public Negociacao() {
    }

    public Negociacao(EmpresaVendedora empresaVendedora, EmpresaCompradora empresaCompradora, Reciclaveis reciclavel, int quantidade, double precoTotal, double valorKg, Date data, StatusPedido status, SolicitacaoPedido solicitacaoPedido) {
        this.empresaVendedora = empresaVendedora;
        this.empresaCompradora = empresaCompradora;
        this.reciclavel = reciclavel;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
        this.valorKg = valorKg;
        this.data = data;
        this.status = status;
        this.solicitacaoPedido = solicitacaoPedido;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmpresaVendedora getEmpresaVendedora() {
        return empresaVendedora;
    }

    public void setEmpresaVendedora(EmpresaVendedora empresaVendedora) {
        this.empresaVendedora = empresaVendedora;
    }

    public EmpresaCompradora getEmpresaCompradora() {
        return empresaCompradora;
    }

    public void setEmpresaCompradora(EmpresaCompradora empresaCompradora) {
        this.empresaCompradora = empresaCompradora;
    }

    public Reciclaveis getReciclavel() {
        return reciclavel;
    }

    public void setReciclavel(Reciclaveis reciclavel) {
        this.reciclavel = reciclavel;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public double getValorKg() {
        return valorKg;
    }

    public void setValorKg(double valorKg) {
        this.valorKg = valorKg;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public SolicitacaoPedido getSolicitacaoPedido() {
        return solicitacaoPedido;
    }

    public void setSolicitacaoPedido(SolicitacaoPedido solicitacaoPedido) {
        this.solicitacaoPedido = solicitacaoPedido;
    }
}
