package com.example.demo.Repository;

import com.example.demo.Model.SolicitacaoPedido;
import com.example.demo.Model.StatusPedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoPedidoRepository extends JpaRepository<SolicitacaoPedido, Integer> {

    @Query("SELECT sp FROM solicitacao_pedido sp WHERE sp.empresaVendedora.id = :empresaVendedoraId")
    List<SolicitacaoPedido> findByEmpresaVendedoraId(int empresaVendedoraId);

    @Query("SELECT COUNT(sp) FROM solicitacao_pedido sp WHERE sp.empresaCompradora.id = :empresaCompradoraId AND sp.status = :status")
    long countByEmpresaCompradoraIdAndStatus(int empresaCompradoraId, StatusPedido status);

    long countByStatus(StatusPedido status);

    @Query("SELECT sp FROM solicitacao_pedido sp WHERE sp.empresaCompradora.id = :empresaCompradoraId AND sp.status = :status")
    List<SolicitacaoPedido> findByEmpresaCompradoraIdAndStatus(int empresaCompradoraId, StatusPedido status);
}
