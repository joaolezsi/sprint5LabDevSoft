package com.example.demo.Controller;

import com.example.demo.DTO.EmpresaDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.Service.EmpresaVendedoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/empresaVendedora")
@CrossOrigin(origins = "*")
public class EmpresaVendedoraController {

    @Autowired
    private EmpresaVendedoraService empresaVendedoraService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        return empresaVendedoraService.login(loginDTO);
    }

    @PostMapping
    public @ResponseBody
    Map<String, String> addEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        return empresaVendedoraService.addEmpresa(empresaDTO);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<String> getEmpresa(@PathVariable int id) {
        return empresaVendedoraService.getEmpresa(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<String> updateEmpresa(@RequestBody EmpresaDTO empresaDTO, @PathVariable int id) {
        return empresaVendedoraService.updateEmpresa(empresaDTO, id);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<String> deleteEmpresa(@PathVariable int id) {
        return empresaVendedoraService.deleteEmpresa(id);
    }
}
