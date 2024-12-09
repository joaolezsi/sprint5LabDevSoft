package com.example.demo.DTO;

import com.example.demo.Model.Negociacao;

public class NegociacaoResponseDTO {
    private int id;
    private double precoTotal;
    private int quantidade;
    private String status;
    private double valorPorKg;
    private int idEmpresaVendedora; // ID da empresa vendedora
    private int idEmpresaCompradora; // ID da empresa compradora
    private String nomeReciclavel;

        public NegociacaoResponseDTO(Negociacao negociacao) {
        this.id = negociacao.getId();
        this.precoTotal = negociacao.getPrecoTotal();
        this.quantidade = negociacao.getQuantidade();
        this.status = negociacao.getStatus().toString();
        this.valorPorKg = negociacao.getValorKg();
        this.idEmpresaVendedora = negociacao.getEmpresaVendedora().getId(); // ID da empresa vendedora
        this.idEmpresaCompradora = negociacao.getEmpresaCompradora().getId(); // ID da empresa compradora
        this.nomeReciclavel = negociacao.getReciclavel().getNome(); 
    }

        // Getters
        public int getId() {
            return id;
        }
    
        public double getPrecoTotal() {
            return precoTotal;
        }
    
        public int getQuantidade() {
            return quantidade;
        }
    
        public String getStatus() {
            return status;
        }
        
        public double getValorPorKg() {
            return valorPorKg;
        }

        public int getIdEmpresaVendedora() {
            return idEmpresaVendedora;
        }
    
        public int getIdEmpresaCompradora() {
            return idEmpresaCompradora;
        }

        public String getIdNomeReciclavel() {
            return nomeReciclavel;
        }
}
