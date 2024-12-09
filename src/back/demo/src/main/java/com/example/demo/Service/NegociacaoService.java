package com.example.demo.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.NegociacaoResponseDTO;
import com.example.demo.DTO.NegociacaoUpdateDTO;
import com.example.demo.Model.EmpresaCompradora;
import com.example.demo.Model.EmpresaVendedora;
import com.example.demo.Model.Reciclaveis;
import com.example.demo.Model.Negociacao;
import com.example.demo.Model.SolicitacaoPedido;
import com.example.demo.Repository.EmpresaCompradoraRepository;
import com.example.demo.Repository.EmpresaVendedoraRepository;
import com.example.demo.Repository.ReciclavelRepository;
import com.example.demo.Repository.SolicitacaoPedidoRepository;
import com.example.demo.Repository.NegociacaoRepository;
import com.example.demo.Model.StatusPedido;

@Service
public class NegociacaoService {

    @Autowired
    private EmpresaVendedoraRepository empresaVendedoraRepository;

    @Autowired
    private EmpresaCompradoraRepository empresaCompradoraRepository;

    @Autowired
    private ReciclavelRepository reciclavelRepository;

    @Autowired
    private NegociacaoRepository negociacaoRepository;

    @Autowired
    private SolicitacaoPedidoRepository solicitacaoPedidoRepository;

    public boolean negociar(int empresaVendedoraId, int empresaCompradoraId, int reciclavelId, int quantidade,
            double precoTotal, double valorKg, int pedidoId) {
        Optional<EmpresaVendedora> empresa = empresaVendedoraRepository.findById(empresaVendedoraId);
        Optional<EmpresaCompradora> empresaCompradora = empresaCompradoraRepository.findById(empresaCompradoraId);
        Optional<Reciclaveis> reciclavel = reciclavelRepository.findById(reciclavelId);
        Optional<SolicitacaoPedido> solicitacaoPedido = solicitacaoPedidoRepository.findById(pedidoId);

        if (empresa.isPresent() && empresaCompradora.isPresent() && reciclavel.isPresent()) {
            EmpresaVendedora empresaVendedora = empresa.get();
            EmpresaCompradora empresaCompradoraEntity = empresaCompradora.get();
            Reciclaveis itemReciclavel = reciclavel.get();
            SolicitacaoPedido pedido = solicitacaoPedido.get();

            Negociacao negociacao = new Negociacao(empresaVendedora, empresaCompradoraEntity, itemReciclavel,
                    quantidade, precoTotal, valorKg, new Date(), StatusPedido.NEGOCIACAO, pedido);
            negociacaoRepository.save(negociacao);

            empresaVendedoraRepository.save(empresaVendedora);
            empresaCompradoraRepository.save(empresaCompradoraEntity);

            return true;
        }
        return false;
    }

    public double aplicarDesconto(int empresaCompradoraId, double precoTotal) {
        long numeroCompras = solicitacaoPedidoRepository.countByEmpresaCompradoraIdAndStatus(empresaCompradoraId,
                StatusPedido.CONCLUIDO);
        if (numeroCompras >= 5) {
            return precoTotal * 0.95;
        }
        return precoTotal;
    }

    public Iterable<Negociacao> getAll() {
        return negociacaoRepository.findAll();
    }

    public Optional<Negociacao> getById(int id) {
        return negociacaoRepository.findById(id);
    }

    @Transactional
    public Negociacao updateStatus(int id, int op) {
        Negociacao negociacaoExistente = negociacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Negociação não encontrada com ID: " + id));

        switch (op) {
            case 2:
                negociacaoExistente.setStatus(StatusPedido.NEGADO);
                break;
            case 3:
                negociacaoExistente.setStatus(StatusPedido.CANCELADO);
                break;
            case 4:
                negociacaoExistente.setStatus(StatusPedido.CONCLUIDO);
                break;
            default:
                throw new IllegalArgumentException("Código de operação inválido: " + op);
        }

        SolicitacaoPedido solicitacaoPedido = negociacaoExistente.getSolicitacaoPedido();
        if (solicitacaoPedido != null) {
            solicitacaoPedido.setStatus(negociacaoExistente.getStatus());
            solicitacaoPedidoRepository.save(solicitacaoPedido);
        }

        return negociacaoRepository.save(negociacaoExistente);
    }

    @Transactional
    public Negociacao negociar(int id, NegociacaoUpdateDTO dto) {
        Negociacao negociacaoExistente = negociacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Negociação não encontrada com ID: " + id));

        negociacaoExistente.setStatus(StatusPedido.NEGOCIACAO);

        if (dto.getQuantidade() != null) {
            negociacaoExistente.setQuantidade(dto.getQuantidade());
        }
        if (dto.getValorKg() != null) { // Updated method
            negociacaoExistente.setValorKg(dto.getValorKg());
        }
        if (dto.getValorTotal() != null) {
            negociacaoExistente.setPrecoTotal(dto.getValorTotal());
        }

        SolicitacaoPedido solicitacaoPedido = negociacaoExistente.getSolicitacaoPedido();
        if (solicitacaoPedido != null) {
            solicitacaoPedido.setStatus(StatusPedido.NEGOCIACAO);
            solicitacaoPedidoRepository.save(solicitacaoPedido);
        }

        return negociacaoRepository.save(negociacaoExistente);
    }

    public List<NegociacaoResponseDTO> findByEmpresa(int id) {
        return negociacaoRepository.findByEmpresaVendedoraId(id).stream()
                .filter(s -> s.getStatus() == StatusPedido.NEGOCIACAO)
                .map(NegociacaoResponseDTO::new)
                .toList();
    }

    // Implement the service method
    public List<NegociacaoResponseDTO> findByEmpresaCompradora(int empresaCompradoraId) {
        return negociacaoRepository.findByEmpresaCompradoraId(empresaCompradoraId).stream()
                .filter(n -> n.getStatus() == StatusPedido.NEGOCIACAO)
                .map(NegociacaoResponseDTO::new)
                .collect(Collectors.toList());
    }
}