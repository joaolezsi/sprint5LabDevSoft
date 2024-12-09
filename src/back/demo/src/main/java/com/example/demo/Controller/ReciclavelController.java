package com.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Service.EmpresaVendedoraService;
import com.example.demo.Service.ReciclavelService;
import com.example.demo.DTO.ReciclavelDTO;
import com.example.demo.DTO.ReciclavelResponseDTO;
import com.example.demo.Model.EmpresaVendedora;
import com.example.demo.Model.Reciclaveis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reciclavel")
@CrossOrigin(origins = "*")

public class ReciclavelController {

    @Autowired
    private ReciclavelService reciclavelService;

    @Autowired
    private EmpresaVendedoraService empresaVendedoraService; // Injeção correta do serviço

    @PersistenceUnit
    EntityManagerFactory factory; // Gerenciador de atividades do JPA

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarReciclavel(@RequestBody ReciclavelDTO reciclavelDTO) {
        EmpresaVendedora empresaVendedora = empresaVendedoraService.getEmpresaid(reciclavelDTO.getIdEmpresaVendedora());
        
        if (empresaVendedora == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa Vendedora com ID " + reciclavelDTO.getIdEmpresaVendedora() + " não encontrada.");
        }
        
        Reciclaveis reciclavel = new Reciclaveis(
                reciclavelDTO.getNome(),
                reciclavelDTO.getDescricao(),
                reciclavelDTO.getPrecoPorKg(),
                reciclavelDTO.getTipo(), 
                empresaVendedora
        );

        Reciclaveis createdReciclavel = reciclavelService.createReciclavel(reciclavel);
        empresaVendedoraService.adicionarReciclavel(reciclavelDTO.getIdEmpresaVendedora(), reciclavel);
        
        return new ResponseEntity<>(createdReciclavel, HttpStatus.CREATED);
    }    

    @GetMapping("/listar/{id}")
    public ResponseEntity<List<Reciclaveis>> listarReciclaveis(@PathVariable int id) {
        EmpresaVendedora empresaVendedora = empresaVendedoraService.getEmpresaid(id);
        List<Reciclaveis> reciclaveis = reciclavelService.listAll(empresaVendedora.getId());
        if (reciclaveis.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reciclaveis, HttpStatus.OK);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<Reciclaveis>> listarReciclaveis() {
        List<Reciclaveis> reciclaveis = reciclavelService.listAll();
        if (reciclaveis.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reciclaveis, HttpStatus.OK);
    }

    @GetMapping("/plastico")
    @Operation(summary = "Lista todos os recicláveis do tipo Plástico")
    public ResponseEntity<List<Reciclaveis>> listPlastico(Pageable pageable) {
        Page<Reciclaveis> reciclaveis = reciclavelService.listPlastico(pageable);
        if (reciclaveis.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reciclaveis.getContent(), HttpStatus.OK);
    }

    @GetMapping("/aluminio")
    @Operation(summary = "Lista todos os recicláveis do tipo Alumínio")
    public ResponseEntity<List<Reciclaveis>> listAluminio(Pageable pageable) {
        Page<Reciclaveis> reciclaveis = reciclavelService.listAluminio(pageable);
        if (reciclaveis.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reciclaveis.getContent(), HttpStatus.OK);
    }

    @GetMapping("/eletronico")
    @Operation(summary = "Lista todos os recicláveis do tipo Lixo Eletrônico")
    public ResponseEntity<List<Reciclaveis>> listEletronico(Pageable pageable) {
        Page<Reciclaveis> reciclaveis = reciclavelService.listEletronico(pageable);
        if (reciclaveis.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reciclaveis.getContent(), HttpStatus.OK);
    }



    @GetMapping("/listarMaterial/{id}")
    public ResponseEntity<ReciclavelResponseDTO> getById(@PathVariable int id) {
        Reciclaveis reciclavel = reciclavelService.GetReciclavelID(id);
        if (reciclavel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ReciclavelResponseDTO(reciclavel));
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualiza um reciclável", description = "Atualiza os detalhes de um reciclável com base no ID fornecido.")
    public ResponseEntity<Reciclaveis> updateReciclavel(@PathVariable("id") int id,
            @Valid @RequestBody ReciclavelDTO reciclavelDTO) {
        
        // Buscar a EmpresaVendedora com base no ID da empresa fornecido
        EmpresaVendedora empresaVendedora = empresaVendedoraService.getEmpresaid(reciclavelDTO.getIdEmpresaVendedora());
        if (empresaVendedora == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Retorna 404 se a empresa não for encontrada
        }
    
        // Criar o objeto Reciclaveis para atualização
        Reciclaveis reciclavel = new Reciclaveis(
                reciclavelDTO.getNome(),
                reciclavelDTO.getDescricao(),
                reciclavelDTO.getPrecoPorKg(),
                reciclavelDTO.getTipo(),
                empresaVendedora);
        reciclavel.setId(id);
    
        // Atualizar o Reciclaveis no serviço
        Optional<Reciclaveis> updatedReciclavel = reciclavelService.updateReciclavel(reciclavel);
    
        // Retornar a resposta apropriada
        return updatedReciclavel
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    

@DeleteMapping("/deletar/{id}/{empresaId}")
@Operation(summary = "Deletar um reciclável", description = "Deletar um reciclável com base no ID e ID da empresa vendedora fornecidos.")
public ResponseEntity<Void> deleteReciclavel(@PathVariable int id, @PathVariable int empresaId) {
    boolean deleted = reciclavelService.deleteReciclavel(id, empresaId);
    return new ResponseEntity<>(deleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
}


}