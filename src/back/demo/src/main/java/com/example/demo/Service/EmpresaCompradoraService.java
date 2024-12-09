package com.example.demo.Service;

import com.example.demo.DTO.EmpresaDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.Model.EmpresaCompradora;
import com.example.demo.Model.Endereco;
import com.example.demo.Model.TipoEmpresa;
import com.example.demo.Repository.EmpresaCompradoraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class EmpresaCompradoraService {

    @Autowired
    private EmpresaCompradoraRepository empresaCompradoraRepository;

    @Autowired
    private EnderecoService enderecoService; // Supondo que exista um serviço para Endereco

    public ResponseEntity<Map<String, String>> login(LoginDTO loginDTO) {
        Optional<EmpresaCompradora> empresaOpt = empresaCompradoraRepository.findByEmail(loginDTO.email);

        if (empresaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Empresa compradora não encontrada"));
        }

        EmpresaCompradora empresa = empresaOpt.get();
        if (empresa.CompararSenha(loginDTO.senha)) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login realizado com sucesso",
                    "id", String.valueOf(empresa.getId()),
                    "tipoEmpresa", loginDTO.tipoEmpresa.toString()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Email ou senha inválidos"));
        }
    }

    public Map<String, String> addEmpresa(EmpresaDTO empresaDTO) {
        Endereco endereco = enderecoService.addEndereco(empresaDTO.endereco);
        EmpresaCompradora empresaCompradora = new EmpresaCompradora(empresaDTO.nome, empresaDTO.cnpj, endereco, empresaDTO.senha, empresaDTO.email, TipoEmpresa.COMPRADORA);
        empresaCompradoraRepository.save(empresaCompradora);

        return Map.of("message", "Empresa adicionada");
    }

    public ResponseEntity<String> getEmpresa(int id) {
        Optional<EmpresaCompradora> empresaOpt = empresaCompradoraRepository.findById(id);
        if (empresaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Empresa Compradora não encontrada\"}");
        }
        return ResponseEntity.ok(empresaOpt.get().toString());
    }

    public EmpresaCompradora getEmpresaID(int id) {
        Optional<EmpresaCompradora> empresaOpt = empresaCompradoraRepository.findById(id);
        return empresaOpt.orElse(null);
    }

    public ResponseEntity<String> updateEmpresa(EmpresaDTO empresaDTO, int id) {
        Optional<EmpresaCompradora> empresaOpt = empresaCompradoraRepository.findById(id);
        if (empresaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Empresa Compradora não encontrada\"}");
        }

        EmpresaCompradora empresa = empresaOpt.get();
        enderecoService.updateEndereco(empresaDTO.endereco, empresa.getEndereco().getId());
        empresa.editEmpresa(empresaDTO);
        empresaCompradoraRepository.save(empresa);

        return ResponseEntity.ok(empresa.toString());
    }

    public ResponseEntity<String> deleteEmpresa(int id) {
        if (!empresaCompradoraRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Empresa Compradora não encontrada\"}");
        }

        empresaCompradoraRepository.deleteById(id);
        return ResponseEntity.ok("{\"message\": \"Empresa removida com sucesso\"}");
    }
}
