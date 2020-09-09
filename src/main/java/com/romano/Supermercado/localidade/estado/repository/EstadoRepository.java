package com.romano.Supermercado.localidade.estado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.localidade.estado.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
