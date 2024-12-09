package com.example.demo.Service;

import java.util.Optional;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Model.EmpresaVendedora;
import com.example.demo.Model.Reciclaveis;
import com.example.demo.Repository.EmpresaVendedoraRepository;
import com.example.demo.Repository.ReciclavelRepository;

import org.springframework.transaction.annotation.Transactional;


@Service
public class ReciclavelService {

    @Autowired
    private ReciclavelRepository reciclavelRepository;

    @Autowired
    private EmpresaVendedoraRepository empresaVendedoraRepository;  // Injeção do repositório

    @Transactional
    public Reciclaveis createReciclavel(Reciclaveis reciclavel) {
        if (reciclavel.getEmpresaVendedora() == null) {
            throw new IllegalArgumentException("Empresa Vendedora é obrigatória para criar um reciclável.");
        }
        return reciclavelRepository.save(reciclavel);
    }
    

    public List<Reciclaveis> listAll(Integer empresaVendedoraId) {
        // Encontrar a empresa vendedora pelo ID
        EmpresaVendedora empresaVendedora = empresaVendedoraRepository.findById(empresaVendedoraId).orElse(null);
        
        // Verificar se a empresa vendedora foi encontrada
        if (empresaVendedora != null) {
            // Buscar os recicláveis pela empresa vendedora
            return reciclavelRepository.findByEmpresaVendedora(empresaVendedora); 
        }
        return Collections.emptyList();
    }
    
    public List<Reciclaveis> listAll() {
        return reciclavelRepository.findAll();
    }

    public Page<Reciclaveis> listPlastico(Pageable pageable) {
        return reciclavelRepository.findByTipo("Plástico", pageable);
    }

    public Page<Reciclaveis> listAluminio(Pageable pageable) {
        return reciclavelRepository.findByTipo("Alumínio", pageable);
    }

    public Page<Reciclaveis> listEletronico(Pageable pageable) {
        return reciclavelRepository.findByTipo("Lixo Eletrônico", pageable);
    }

    public Optional<Reciclaveis> GetReciclavelId(int id) {
        return reciclavelRepository.findById(id);
    }

    /**
     * Retrieves a Reciclaveis object by its ID.
     *
     * @param id the ID of the Reciclaveis object to retrieve
     * @return the Reciclaveis object if found, otherwise null
     */
    public Reciclaveis GetReciclavelID(int id) {
        Optional<Reciclaveis> reciclaveOptional = reciclavelRepository.findById(id);
        return reciclaveOptional.orElse(null);
    }

    public Optional<Reciclaveis> updateReciclavel(Reciclaveis reciclavel) {
        if (!reciclavelRepository.existsById(reciclavel.getId())) {
            return Optional.empty();
        }
        try {
            return Optional.of(reciclavelRepository.save(reciclavel));
        } catch (Exception e) {
            System.err.println("Erro ao salvar o reciclável: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean deleteReciclavel(int reciclavelId, int empresaId) {
        // Busca o reciclável pelo ID
        Optional<Reciclaveis> reciclavelOpt = reciclavelRepository.findById(reciclavelId);
    
        if (reciclavelOpt.isPresent()) {
            Reciclaveis reciclavel = reciclavelOpt.get();
    
            // Verifica se o reciclável está associado à empresa vendedora correta
            if (reciclavel.getEmpresaVendedora().getId() == empresaId) {
                // Deleta o reciclável
                reciclavelRepository.delete(reciclavel);
                return true; // Deletado com sucesso
            }
        }
    
        // Caso não tenha encontrado ou a empresa não corresponda
        return false;
    }
    

}