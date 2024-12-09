package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reciclaveis")
public class Reciclaveis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "descricao", length = 100)
    private String descricao;
    @Column(name = "precoPorKg", length = 100)
    private double precoPorKg = 0.0;
    @Column(name = "tipo", length = 100)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "empresa_vendedora_id")
    @JsonBackReference
    private EmpresaVendedora empresaVendedora;

    public Reciclaveis() {

    }

    /**
     * Construtor da classe Reciclaveis
     * 
     * @param nome
     * @param descricao
     * @param precoPorKg
     * @param tipo
     */
    
     public Reciclaveis(String nome, String descricao, double precoPorKg, String tipo, EmpresaVendedora empresaVendedora) {
        this.nome = (nome != null && !nome.isEmpty()) ? nome : "Nome Padrão"; // Substitui por "Nome Padrão" se nome for nulo ou vazio
        this.descricao = descricao;
        this.precoPorKg = precoPorKg;
        this.tipo = tipo;
        this.empresaVendedora = empresaVendedora;
    }
    
    /**
     * Retorna o id do reciclavel
     * 
     * @return
     */
    public int getId() {
        return Id;
    }

    /**
     * Define o id do reciclavel
     * 
     * @param id
     */
    public void setId(int id) {
        this.Id = id;
    }

    /**
     * Retorna o nome do reciclavel
     * 
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do reciclavel
     * 
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descricao do reciclavel
     * 
     * @return
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descricao do reciclavel
     * 
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o preco por kg do reciclavel
     * 
     * @return
     */
    public double getPrecoPorKg() {
        return precoPorKg;
    }

    /**
     * Define o preco por kg do reciclavel
     * 
     * @param precoPorKg
     */
    public void setPrecoPorKg(double precoPorKg) {
        this.precoPorKg = precoPorKg;
    }

    /**
     * Retorna o tipo do reciclavel
     * 
     * @return
     */

    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do reciclavel
     * 
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna a empresa vendedora do reciclavel
     * 
     * @return
     */
    public EmpresaVendedora getEmpresaVendedora() {
        return empresaVendedora;
    }

    /**
     * Define a empresa vendedora do reciclavel
     * 
     * @param empresaVendedora
     */
    public void setEmpresaVendedora(EmpresaVendedora empresaVendedora) {
        this.empresaVendedora = empresaVendedora;
    }

    /**
     * Retorna uma representação em string do reciclavel
     * 
     * @return
     */
    @Override
    public String toString() {
        return "\"nome\": \"" + nome + "\","
                + "\"descricao\": \"" + descricao + "\","
                + "\"precoPorKg\": \"" + precoPorKg + "\","
                + "\"tipo\": \"" + tipo + "\"";
    }
}
