package com.example.demo.DTO;
public class NegociacaoUpdateDTO {

    private Integer quantidade;
    private Double valorKg;
    private Double valorTotal;

    // Corrected Getter and Setter for valorKg
    public Double getValorKg() {
        return valorKg;
    }

    public void setValorKg(Double valorKg) {
        this.valorKg = valorKg;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}