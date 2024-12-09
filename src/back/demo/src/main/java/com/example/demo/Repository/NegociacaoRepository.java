package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.Negociacao;


public interface NegociacaoRepository extends JpaRepository<Negociacao, Integer> {

    List<Negociacao> findByEmpresaVendedoraId(int id);
    
    // Add this method to the repository interface
    List<Negociacao> findByEmpresaCompradoraId(int empresaCompradoraId);
}
