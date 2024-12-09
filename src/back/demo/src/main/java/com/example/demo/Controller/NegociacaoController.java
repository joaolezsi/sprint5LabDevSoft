package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.DTO.NegociacaoDTO;
import com.example.demo.DTO.NegociacaoResponseDTO;
import com.example.demo.DTO.NegociacaoUpdateDTO;
import com.example.demo.Model.Negociacao;
import com.example.demo.Service.NegociacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/negociacao")
@CrossOrigin(origins = "*")
public class NegociacaoController {

    @Autowired
    private NegociacaoService negociacaoService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(negociacaoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Negociacao> getById(@PathVariable int id) {
        Optional<Negociacao> negociacao = negociacaoService.getById(id);
        if (negociacao.isPresent()) {
            return ResponseEntity.ok(negociacao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/empresa/{id}")
    public List<NegociacaoResponseDTO> getByEmpresa(@PathVariable int id) {
        return negociacaoService.findByEmpresa(id);
    }    

    @PostMapping("/{empresaVendedoraId}/{empresaCompradoraId}/{reciclavelId}/{pedidoId}")
    public ResponseEntity<Void> negociar(
            @PathVariable int empresaVendedoraId,
            @PathVariable int empresaCompradoraId,
            @PathVariable int reciclavelId,
            @PathVariable int pedidoId,
            @Valid @RequestBody NegociacaoDTO negociacaoDTO) {

        boolean sucesso = negociacaoService.negociar(
                empresaVendedoraId,
                empresaCompradoraId,
                reciclavelId,
                negociacaoDTO.getQuantidade(),
                negociacaoDTO.getPrecoTotal(),
                negociacaoDTO.getValorKg(),
                pedidoId
        );

        return sucesso ? ResponseEntity.status(HttpStatus.CREATED).build()
                       : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/aplicarDesconto/{empresaCompradoraId}/{precoTotal}")
    public ResponseEntity<Double> aplicarDesconto(@PathVariable int empresaCompradoraId, @PathVariable double precoTotal) {
        double precoComDesconto = negociacaoService.aplicarDesconto(empresaCompradoraId, precoTotal);
        return ResponseEntity.ok(precoComDesconto);
    }

    @PutMapping("/{id}/negociacao")
    public ResponseEntity<Negociacao> negociar(
            @PathVariable int id,
            @Valid @RequestBody NegociacaoUpdateDTO dto) {
        try {
            Negociacao negociacaoAtualizada = negociacaoService.negociar(id, dto);
            return ResponseEntity.ok(negociacaoAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/negar")
    public ResponseEntity<Negociacao> negar(@PathVariable int id) {
        Optional<Negociacao> negociacaoOpt = negociacaoService.getById(id);
        if (negociacaoOpt.isPresent()) {
            Negociacao negociacaoAtualizada = negociacaoService.updateStatus(id, 2); // 2 = Negada
            return ResponseEntity.ok(negociacaoAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<Negociacao> concluir(@PathVariable int id) {
        try {
            Negociacao negociacaoAtualizada = negociacaoService.updateStatus(id, 4); // 4 = Concluída
            return ResponseEntity.ok(negociacaoAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/empresa/compradora/{empresaCompradoraId}")
    public List<NegociacaoResponseDTO> getByEmpresaCompradora(@PathVariable int empresaCompradoraId) {
        return negociacaoService.findByEmpresaCompradora(empresaCompradoraId);
    }
}