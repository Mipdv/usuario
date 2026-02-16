package com.javanauta.usuario.infrastructure.repository;

import com.henrique.aprendendospring.infrascruture.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndrecoRepository extends JpaRepository <Endereco, Long>{
}
