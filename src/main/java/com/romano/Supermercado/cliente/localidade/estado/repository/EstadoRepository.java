package com.romano.Supermercado.cliente.localidade.estado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.cliente.localidade.estado.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
