package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.dto.CustomerDTO;
import com.example.api.exceptions.DataIntegrityException;
import com.example.api.exceptions.ObjectNotFoundException;
import com.example.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {

	private final CustomerRepository repository;

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	public Page<Customer> findByFilter(Integer page, Integer size, String name, String email, String gender) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return repository.findByFilter(pageRequest, name, email, gender);
	}

	public Customer findById(Long id) {
		Optional<Customer> opt = repository.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Customer.class.getName()));
	}

    public Customer insert(CustomerDTO dto) {
		ModelMapper mapper = new ModelMapper();
		Customer customer = mapper.map(dto, Customer.class);
		customer.getAddresses().forEach(address -> {
			address.setCep(address.getCep().replace("-", ""));
			address.setCustomer(customer);
		});

		return repository.save(customer);
    }

	public Customer update(CustomerDTO dto, Long customerId) {
		Customer customer = this.findById(customerId);
		ModelMapper mapper = new ModelMapper();
		mapper.map(dto, customer);
		return repository.save(customer);
	}

	public void delete(Long id) {
		this.findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o cliente");
		}
	}

	public Page<Customer> findByCidadeEstado(Integer page, Integer size, String uf, String cidade) {
		PageRequest pageRequest = PageRequest.of(page, size);

		return repository.findByCidadeEstado(pageRequest, uf, cidade);
	}
}
