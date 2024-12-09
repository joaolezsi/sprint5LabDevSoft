package com.example.demo.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Model.EmpresaVendedora;
import com.example.demo.Model.Reciclaveis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ReciclavelRepository extends JpaRepository<Reciclaveis, Integer> {
    List<Reciclaveis> findByEmpresaVendedora(EmpresaVendedora empresaVendedora);
    Page<Reciclaveis> findByTipo(String tipo, Pageable pageable);
}


