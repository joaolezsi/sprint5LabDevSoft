package com.example.demo.Repository;

import com.example.demo.Model.EmpresaVendedora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpresaVendedoraRepository extends JpaRepository<EmpresaVendedora, Integer> {

    Optional<EmpresaVendedora> findByEmail(String email);

    Optional<EmpresaVendedora> findById(long id);  
}

