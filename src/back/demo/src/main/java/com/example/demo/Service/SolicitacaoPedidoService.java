package com.example.demo.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.IndicadorDTO;
import com.example.demo.Model.SolicitacaoPedido;
import com.example.demo.Model.StatusPedido;
import com.example.demo.Repository.SolicitacaoPedidoRepository;

@Service
public class SolicitacaoPedidoService {

    private double quantidadeTotalBH = 360000;

    @Autowired
    private SolicitacaoPedidoRepository repository;

    /**
     * Retorna todas as solicitações de pedido.
     *
     * @return Iterable com todas as Solicitações de Pedido.
     */
    public String listAll() {
        return repository.findAll().toString();
    }

    public String findByEmpresa(int id) {
        List<SolicitacaoPedido> solicitacoes = repository.findByEmpresaVendedoraId(id);
        solicitacoes = solicitacoes.stream().filter(s -> s.getStatus() == StatusPedido.ABERTO).toList();
        return solicitacoes.toString();
    }

    public String findByEmpresaEmNegociacao(int id) {
        List<SolicitacaoPedido> solicitacoes = repository.findByEmpresaVendedoraId(id);
        solicitacoes = solicitacoes.stream().filter(s -> s.getStatus() == StatusPedido.NEGOCIACAO).toList();
        return solicitacoes.toString();
    }

    /**
     * Busca uma solicitação de pedido pelo ID.
     *
     * @param id ID da solicitação de pedido.
     * @return Optional com a solicitação encontrada ou vazio se não encontrado.
     */
    public Optional<SolicitacaoPedido> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Salva uma nova solicitação de pedido no banco de dados.
     *
     * @param solicitacaoPedido Objeto Solicitação de Pedido a ser salvo.
     * @return A solicitação de pedido salva.
     */
    public SolicitacaoPedido save(SolicitacaoPedido solicitacaoPedido) {
        return repository.save(solicitacaoPedido);
    }

    /**
     * Exclui uma solicitação de pedido pelo ID.
     *
     * @param id ID da solicitação de pedido a ser excluída.
     */
    public void deleteById(int id) {
        repository.deleteById(id);
    }  

    /**
     * Atualiza uma solicitação de pedido existente.
     *
     * @param id                    ID da solicitação de pedido a ser atualizada.
     * @param solicitacaoAtualizada Dados atualizados da solicitação de pedido.
     * @param status                Novo status do pedido.
     * @return A solicitação de pedido atualizada.
     * @throws IllegalArgumentException se a solicitação de pedido não for
     *                                  encontrada.
     */

    @Transactional
    public SolicitacaoPedido updatePedido(int id, SolicitacaoPedido solicitacaoAtualizada) {
        // Busca a solicitação existente no banco de dados
        SolicitacaoPedido solicitacaoExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada com ID: " + id));

        // Atualiza os campos necessários
        solicitacaoExistente.setReciclavel(solicitacaoAtualizada.getReciclavel());
        solicitacaoExistente.RefazerSolicitacao(
                solicitacaoAtualizada.getQuantidade(),
                solicitacaoAtualizada.getOfertaPrecoKg());
        solicitacaoExistente.Negociacao(1);

        // Salva a solicitação atualizada no banco de dados
        return repository.save(solicitacaoExistente);
    }

    @Transactional
    public SolicitacaoPedido updateStatus(int id, int op) {
        SolicitacaoPedido solicitacaoExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada com ID: " + id));
        solicitacaoExistente.Negociacao(op);
        return repository.save(solicitacaoExistente);
    }


    public IndicadorDTO TaxaDeEntrega(){
        List<SolicitacaoPedido> solicitacoes = repository.findAll();
        List<SolicitacaoPedido> solicitacoesConcluidas = solicitacoes.stream()
            .filter(s -> s.getStatus() == StatusPedido.CONCLUIDO)
            .collect(Collectors.toList());
        long totalRequisicoes = solicitacoes.size();
        long requisicoesConcluidasCount = solicitacoesConcluidas.size();
        double porcentagem = (totalRequisicoes == 0) ? 0.0 : ((double) requisicoesConcluidasCount / totalRequisicoes) * 100;
        return new IndicadorDTO(porcentagem, totalRequisicoes, requisicoesConcluidasCount);
    }

    public IndicadorDTO VolumeReducaoResiduos() {
        // Puxa todas as solicitações de pedidos
        List<SolicitacaoPedido> solicitacoes = repository.findAll();
        
        //Total em kg de reciclaveis das requisições CONCLUIDAS
        double quantidadeTotal = solicitacoes.stream()
            .filter(s -> s.getStatus() == StatusPedido.CONCLUIDO) // Filtra solicitações concluídas
            .mapToDouble(SolicitacaoPedido::getQuantidade) // Mapeia a quantidade
            .sum(); // Soma todas as quantidades
    
        // Cria e retorna o DTO com as informações
        return new IndicadorDTO(quantidadeTotal);
    }  

    public IndicadorDTO calcularReducaoResiduos() {
        // Puxa todas as solicitações de pedidos
        List<SolicitacaoPedido> solicitacoes = repository.findAll();
    
        //Total em kg de reciclaveis das requisições CONCLUIDAS
        double quantidadeTotal = solicitacoes.stream()
            .filter(s -> s.getStatus() == StatusPedido.CONCLUIDO) // Filtra solicitações concluídas
            .mapToDouble(SolicitacaoPedido::getQuantidade) // Mapeia a quantidade
            .sum(); // Soma todas as quantidades
    
        // Calcula a porcentagem de redução de resíduos
        double porcentagem = (quantidadeTotal == 0) ? 0.0 : ((double) quantidadeTotal / quantidadeTotalBH) * 100;
    
        // Cria e retorna o DTO com as informações
        return new IndicadorDTO(porcentagem, 0,0, quantidadeTotal);
    }

    public List<SolicitacaoPedido> findByEmpresaCompradoraAndStatus(int empresaCompradoraId, StatusPedido status) {
        return repository.findByEmpresaCompradoraIdAndStatus(empresaCompradoraId, status);
    }

}