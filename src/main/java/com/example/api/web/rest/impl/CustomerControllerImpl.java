package com.example.api.web.rest.impl;

import java.net.URI;
import java.util.List;

import com.example.api.dto.CustomerDTO;
import com.example.api.web.rest.CustomerController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
public class CustomerControllerImpl implements CustomerController {

	private final CustomerService service;

	@Override
	public ResponseEntity<List<Customer>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@Override
	public ResponseEntity<Page<Customer>> findCustomers(Integer page, Integer size, String name, String email, String gender) {
		return ResponseEntity.ok().body(service.findByFilter(page, size, name, email, gender));
	}

	@Override
	public ResponseEntity<Customer> findById(Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@Override
	public ResponseEntity<Void> insert(CustomerDTO customer) {
		Customer obj = service.insert(customer);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@Override
	public ResponseEntity<Void> update(CustomerDTO customer, Long id) {
		service.update(customer, id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> delete(Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Page<Customer>> findByCidadeEstado(String uf, String cidade, Integer page, Integer size) {
		return ResponseEntity.ok().body(service.findByCidadeEstado(page, size, uf, cidade));
	}
}
