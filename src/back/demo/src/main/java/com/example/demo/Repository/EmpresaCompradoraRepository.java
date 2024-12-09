package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.EmpresaCompradora;

public interface EmpresaCompradoraRepository extends JpaRepository<EmpresaCompradora, Integer> {

    Optional<EmpresaCompradora> findByEmail(String email);
}
