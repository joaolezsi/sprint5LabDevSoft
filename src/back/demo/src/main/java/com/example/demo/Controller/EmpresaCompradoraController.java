package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DAO.IUEmpresa;
import com.example.demo.DTO.EmpresaDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.Service.EmpresaCompradoraService;

@Controller
@RequestMapping("/empresaCompradora")
@CrossOrigin(origins = "*")
public class EmpresaCompradoraController implements IUEmpresa {

    @Autowired
    private EmpresaCompradoraService empresaCompradoraService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        return empresaCompradoraService.login(loginDTO);
    }

    @PostMapping
    @Override
    public @ResponseBody
    Map<String, String> addEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        return empresaCompradoraService.addEmpresa(empresaDTO);
    }

    @GetMapping("/{id}")
    @Override
    public @ResponseBody
    ResponseEntity<String> getEmpresa(@PathVariable int id) {
        return empresaCompradoraService.getEmpresa(id);
    }

    @PutMapping("/{id}")
    @Override
    public @ResponseBody
    ResponseEntity<String> updateEmpresa(@RequestBody EmpresaDTO empresaDTO, @PathVariable int id) {
        return empresaCompradoraService.updateEmpresa(empresaDTO, id);
    }

    @DeleteMapping("/{id}")
    @Override
    public @ResponseBody
    ResponseEntity<String> deleteEmpresa(@PathVariable int id) {
        return empresaCompradoraService.deleteEmpresa(id);
    }
}
