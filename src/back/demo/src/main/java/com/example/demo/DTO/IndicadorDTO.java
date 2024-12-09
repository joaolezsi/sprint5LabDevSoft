package com.example.demo.DTO;

public class IndicadorDTO {

    private double porcentagem;
    private long totalRequisicoes;
    private long requisicoesFechadas;
    private double quantidadeTotal;


    public IndicadorDTO(double quantidade) {
        this.porcentagem = 0;
        this.totalRequisicoes = 0;
        this.requisicoesFechadas = 0;
        this.quantidadeTotal = quantidade;
    }

    public IndicadorDTO(double porcentagem, long totalRequisicoes, long requisicoesFechadas, double quantidade) {
        this.porcentagem = porcentagem;
        this.totalRequisicoes = totalRequisicoes;
        this.requisicoesFechadas = requisicoesFechadas;
        this.quantidadeTotal = quantidade;
    }

    public IndicadorDTO(double porcentagem, long totalRequisicoes, long requisicoesFechadas) {
        this.porcentagem = porcentagem;
        this.totalRequisicoes = totalRequisicoes;
        this.requisicoesFechadas = requisicoesFechadas;
    }
    
    // Getters and Setters
    public double getQuantidade() {
        return quantidadeTotal;
    }

    public void setQuantidade(double quantidade) {
        this.quantidadeTotal = quantidade;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public long getTotalRequisicoes() {
        return totalRequisicoes;
    }

    public void setTotalRequisicoes(long totalRequisicoes) {
        this.totalRequisicoes = totalRequisicoes;
    }

    public long getRequisicoesFechadas() {
        return requisicoesFechadas;
    }

    public void setRequisicoesFechadas(long requisicoesFechadas) {
        this.requisicoesFechadas = requisicoesFechadas;
    }
}
