package com.example.demo.Service;

import com.example.demo.DTO.EmpresaDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.Model.EmpresaVendedora;
import com.example.demo.Model.Endereco;
import com.example.demo.Model.Reciclaveis;
import com.example.demo.Repository.EmpresaVendedoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class EmpresaVendedoraService {

    @Autowired
    private EmpresaVendedoraRepository empresaVendedoraRepository;

    @Autowired
    private EnderecoService enderecoService;

    public ResponseEntity<Map<String, String>> login(LoginDTO loginDTO) {
        Optional<EmpresaVendedora> empresaOpt = empresaVendedoraRepository.findByEmail(loginDTO.email);

        if (empresaOpt.isPresent()) {
            EmpresaVendedora empresa = empresaOpt.get();
            if (empresa.CompararSenha(loginDTO.senha)) {
                return ResponseEntity.ok(Map.of(
                        "message", "Login realizado com sucesso",
                        "id", String.valueOf(empresa.getId()),
                        "tipoEmpresa", loginDTO.tipoEmpresa.toString()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Email ou senha inválidos"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Empresa vendedora não encontrada"));
        }
    }

    public Map<String, String> addEmpresa(EmpresaDTO empresaDTO) {
        Endereco endereco = enderecoService.addEndereco(empresaDTO.endereco);
        EmpresaVendedora empresaVendedora = new EmpresaVendedora(empresaDTO.nome, empresaDTO.cnpj, endereco, empresaDTO.senha, empresaDTO.email, empresaDTO.tipoEmpresa);
        empresaVendedoraRepository.save(empresaVendedora);
        return Map.of("message", "Empresa adicionada");
    }

    public ResponseEntity<String> getEmpresa(int id) {
        Optional<EmpresaVendedora> empresaOpt = empresaVendedoraRepository.findById(id);
        return empresaOpt.map(empresa -> ResponseEntity.ok(empresa.toString()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"message\": \"Empresa Vendedora não encontrada\"}"));
    }

    public EmpresaVendedora getEmpresaid(int id) {
    Optional<EmpresaVendedora> empresaOpt = empresaVendedoraRepository.findById(id);
    return empresaOpt.orElse(null);
    }


    public ResponseEntity<String> updateEmpresa(EmpresaDTO empresaDTO, int id) {
        Optional<EmpresaVendedora> empresaOpt = empresaVendedoraRepository.findById(id);
        if (empresaOpt.isPresent()) {
            EmpresaVendedora empresa = empresaOpt.get();
            enderecoService.updateEndereco(empresaDTO.endereco, empresa.getEndereco().getId());
            empresa.editEmpresa(empresaDTO);
            empresaVendedoraRepository.save(empresa);
            return ResponseEntity.ok(empresa.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Empresa Vendedora não encontrada\"}");
        }
    }

    public ResponseEntity<String> adicionarReciclavel(int empresaId, Reciclaveis reciclavel) {
        Optional<EmpresaVendedora> empresaOpt = empresaVendedoraRepository.findById(empresaId);
        if (empresaOpt.isPresent()) {
            EmpresaVendedora empresa = empresaOpt.get();
            empresa.addReciclavel(reciclavel);
            empresaVendedoraRepository.save(empresa);
            return ResponseEntity.ok("{\"message\": \"Reciclável adicionado com sucesso\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Empresa Vendedora não encontrada\"}");
        }
    }

    public ResponseEntity<String> deleteEmpresa(int id) {
        Optional<EmpresaVendedora> empresaOpt = empresaVendedoraRepository.findById(id);
        if (empresaOpt.isPresent()) {
            empresaVendedoraRepository.delete(empresaOpt.get());
            return ResponseEntity.ok("{\"message\": \"Empresa removida com sucesso\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Empresa Vendedora não encontrada\"}");
        }
    }
}
