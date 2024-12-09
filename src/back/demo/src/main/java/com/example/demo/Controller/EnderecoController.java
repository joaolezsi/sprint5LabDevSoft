package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.EnderecoDTO;
import com.example.demo.Model.Endereco;
import com.example.demo.Service.EnderecoService;

import java.util.Optional;

@Controller
@RequestMapping("/endereco")
@CrossOrigin(origins = "*")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<Endereco> addEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoService.addEndereco(enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @GetMapping("{id}")
    public @ResponseBody
    ResponseEntity<String> getEndereco(@PathVariable int id) {
        Optional<Endereco> endereco = enderecoService.getEndereco(id);
        if (endereco.isPresent()) {
            return ResponseEntity.ok(endereco.get().toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Endereço não encontrado\"}");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateEndereco(@RequestBody EnderecoDTO enderecoDTO, @PathVariable int id) {
        enderecoService.updateEndereco(enderecoDTO, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEndereco(@PathVariable int id) {
        enderecoService.deleteEndereco(id);
        return ResponseEntity.ok("{\"message\": \"Endereço removido com sucesso\"}");
    }
}
