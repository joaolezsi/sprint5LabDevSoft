package com.example.demo.Controller;

import com.example.demo.DTO.SolicitacaoPedidoDTO;
import com.example.demo.Model.EmpresaCompradora;
import com.example.demo.Model.Reciclaveis;
import com.example.demo.Model.SolicitacaoPedido;
import com.example.demo.Model.StatusPedido;
import com.example.demo.Service.EmpresaCompradoraService;
import com.example.demo.Service.ReciclavelService;
import com.example.demo.Service.SolicitacaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/solicitacao-pedido")
@CrossOrigin(origins = "*")
public class SolicitacaoPedidoController {

    @Autowired
    private SolicitacaoPedidoService service;

    @Autowired
    private ReciclavelService reciclavelService;

    @Autowired
    private EmpresaCompradoraService empresaCompradoraService;

    /**
     * Retorna todas as solicitações de pedido cadastradas.
     *
     * @return Lista de SolicitaçãoPedido.
     */
    @GetMapping
    public String getAll() {
        return service.listAll();
    }

    @GetMapping("/empresa/{id}")
    public String getByEmpresa(@PathVariable int id) {
        return service.findByEmpresa(id);
    }

    @GetMapping("/empresa/compradora/{id}")
    public ResponseEntity<List<SolicitacaoPedido>> getByEmpresaCompradora(@PathVariable int id) {
        List<SolicitacaoPedido> pedidos = service.findByEmpresaCompradoraAndStatus(id, StatusPedido.NEGOCIACAO);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Retorna uma solicitação de pedido com base no ID fornecido.
     *
     * @param id ID da solicitação de pedido.
     * @return A solicitação de pedido correspondente ou um erro 404 se não
     *         encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoPedido> getById(@PathVariable int id) {
        Optional<SolicitacaoPedido> solicitacaoPedido = service.findById(id);
        return solicitacaoPedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Cria uma nova solicitação de pedido.
     *
     * @param solicitacaoPedidoDTO Dados da solicitação de pedido fornecidos pelo
     *                             cliente.
     * @return A solicitação de pedido criada.
     */
    @PostMapping
    public ResponseEntity<SolicitacaoPedido> create(@RequestBody SolicitacaoPedidoDTO solicitacaoPedidoDTO) {
        EmpresaCompradora empresaCompradora = empresaCompradoraService
                .getEmpresaID(solicitacaoPedidoDTO.idEmpresaCompradora);
        Reciclaveis reciclavel = reciclavelService.GetReciclavelID(solicitacaoPedidoDTO.idReciclavel);
        SolicitacaoPedido newSolicitacaoPedido = new SolicitacaoPedido(solicitacaoPedidoDTO, empresaCompradora,
                reciclavel);
        SolicitacaoPedido savedPedido = service.save(newSolicitacaoPedido);

        // Retorna o objeto salvo junto com o código de status HTTP 201 Created
        return ResponseEntity.status(201).body(savedPedido);
    }

    /**
     * Atualiza uma solicitação de pedido com base no ID fornecido.
     *
     * @param id                   ID da solicitação de pedido a ser atualizada.
     * @param solicitacaoPedidoDTO Dados atualizados fornecidos pelo cliente.
     * @param status               Novo status do pedido.
     * @return A solicitação de pedido atualizada ou um erro 404 se não encontrado.
     */
    /*
     * 
     */
    @PutMapping("/{id}")
    public ResponseEntity<SolicitacaoPedido> update(@PathVariable int id,
            @RequestBody SolicitacaoPedidoDTO solicitacaoPedidoDTO) {
        if (service.findById(id).isPresent()) {
            EmpresaCompradora empresaCompradora = empresaCompradoraService
                    .getEmpresaID(solicitacaoPedidoDTO.idEmpresaCompradora);
            Reciclaveis reciclavel = reciclavelService.GetReciclavelID(solicitacaoPedidoDTO.idReciclavel);
            SolicitacaoPedido newSolicitacaoPedido = new SolicitacaoPedido(solicitacaoPedidoDTO, empresaCompradora,
                    reciclavel);
            return ResponseEntity.ok(service.updatePedido(id, newSolicitacaoPedido));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Exclui uma solicitação de pedido com base no ID fornecido.
     *
     * @param id ID da solicitação de pedido a ser excluída.
     * @return Resposta sem conteúdo ou erro 404 se não encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/negociacao")
    public ResponseEntity<SolicitacaoPedido> negociacao(@PathVariable int id) {
        if (service.findById(id).isPresent()) {
            return ResponseEntity.ok(service.updateStatus(id, 1));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/negar")
    public ResponseEntity<SolicitacaoPedido> negar(@PathVariable int id) {
        if (service.findById(id).isPresent()) {
            return ResponseEntity.ok(service.updateStatus(id, 2));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<SolicitacaoPedido> concluir(@PathVariable int id) {
        try {
            SolicitacaoPedido pedidoAtualizado = service.updateStatus(id, 4);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<SolicitacaoPedido> cancelar(@PathVariable int id) {
        if (service.findById(id).isPresent()) {
            return ResponseEntity.ok(service.updateStatus(id, 3));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}