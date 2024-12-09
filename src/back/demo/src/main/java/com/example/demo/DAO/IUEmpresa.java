package com.example.demo.DAO;

import java.util.Map; // Correct import

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.DTO.EmpresaDTO;
import com.example.demo.DTO.LoginDTO;

public interface IUEmpresa {

    ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO);

    Map<String, String> addEmpresa(@RequestBody EmpresaDTO empresaDTO);

    ResponseEntity<String> getEmpresa(@PathVariable int id);

    ResponseEntity<String> updateEmpresa(@RequestBody EmpresaDTO empresaDTO, @PathVariable int id);

    ResponseEntity<String> deleteEmpresa(@PathVariable int id);
}
