package com.example.demo.DTO;

import com.example.demo.Model.Reciclaveis;

public class ReciclavelResponseDTO {
    private int id;
    private String nome;
    private String descricao;
    private double precoPorKg;
    private String tipo;
    private int idEmpresaVendedora;

    // Construtor para criar a partir da entidade Reciclaveis
    public ReciclavelResponseDTO(Reciclaveis reciclavel) {
        this.id = reciclavel.getId();
        this.nome = reciclavel.getNome();
        this.descricao = reciclavel.getDescricao();
        this.precoPorKg = reciclavel.getPrecoPorKg();
        this.tipo = reciclavel.getTipo();
        this.idEmpresaVendedora = reciclavel.getEmpresaVendedora().getId();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoPorKg() {
        return precoPorKg;
    }

    public void setPrecoPorKg(double precoPorKg) {
        this.precoPorKg = precoPorKg;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdEmpresaVendedora() {
        return idEmpresaVendedora;
    }

    public void setIdEmpresaVendedora(int idEmpresaVendedora) {
        this.idEmpresaVendedora = idEmpresaVendedora;
    }
}