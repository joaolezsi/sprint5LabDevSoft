package com.example.demo.DTO;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

public class NegociacaoDTO {

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser positiva.")
    private int quantidade;

    @NotNull(message = "O preço total é obrigatório.")
    @Positive(message = "O preço total deve ser positivo.")
    private double precoTotal;

    @NotNull(message = "O valor por Kg é obrigatório.")
    @Positive(message = "O valor por Kg deve ser positivo.")
    private double valorKg;

    // Getters e Setters
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
}