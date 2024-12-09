package com.example.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.IndicadorDTO;
import com.example.demo.Service.SolicitacaoPedidoService;

@RestController
@RequestMapping("/indicadores")
@CrossOrigin(origins = "*")
public class IndicadorController {

    private final SolicitacaoPedidoService solicitacaoPedidoService;

    public IndicadorController(SolicitacaoPedidoService solicitacaoPedidoService) {
        this.solicitacaoPedidoService = solicitacaoPedidoService;
    }

    @GetMapping("/reducao-residuos")
    public ResponseEntity<IndicadorDTO> calcularVolumeoResiduos() {
        IndicadorDTO indicador = solicitacaoPedidoService.VolumeReducaoResiduos();
        return ResponseEntity.ok(indicador);
    }

    @GetMapping("/contribuicaoBH")
    public ResponseEntity<IndicadorDTO> calcularContrinuicao() {
        IndicadorDTO indicador = solicitacaoPedidoService.calcularReducaoResiduos();
        return ResponseEntity.ok(indicador);
    }
    
    @GetMapping("/taxaDeEntrega")
    public ResponseEntity<IndicadorDTO> calcularTaxaDeEntrega() {
        IndicadorDTO indicador = solicitacaoPedidoService.TaxaDeEntrega();
        return ResponseEntity.ok(indicador);
    }

}
