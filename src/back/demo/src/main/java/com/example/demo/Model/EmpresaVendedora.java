package com.example.demo.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;

@Entity(name = "empresa_vendedora")
public class EmpresaVendedora extends Empresa {

    @OneToMany(mappedBy = "empresaVendedora", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Reciclaveis> reciclaveis;

    public EmpresaVendedora() {
        
    }

    public EmpresaVendedora(String nome, String cnpj, Endereco endereco, String senha, String email, TipoEmpresa tipoEmpresa) {
        super(nome, cnpj, endereco, senha, email, tipoEmpresa);
    }

    public List<Reciclaveis> getReciclaveis() {
        return reciclaveis;
    }

    public void addReciclavel(Reciclaveis reciclavel) {
        reciclaveis.add(reciclavel);
    }
    
}
