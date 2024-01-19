package com.example.api.repository;

import com.example.api.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface CustomerRepositoryCustom {

    Page<Customer> findByFilter(Pageable pageable, @Param("name") String name,
                                @Param("email") String email, @Param("gender") String gender);

    Page<Customer> findByCidadeEstado(Pageable pageable, @Param("uf")  String uf, @Param("cidade") String cidade);
}
