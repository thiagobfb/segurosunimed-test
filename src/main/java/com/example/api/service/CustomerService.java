package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.AddressClientDTO;
import com.example.api.dto.CustomerDTO;
import com.example.api.exceptions.DataIntegrityException;
import com.example.api.exceptions.ObjectNotFoundException;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {

	private final CustomerRepository repository;
	private final AddressRepository addressRepository;

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

		// Remove os endereços não presentes no DTO
		List<Address> addressesToRemove = new ArrayList<>();
		for (Address existingAddress : customer.getAddresses()) {
			boolean addressExistsInDTO = dto.getAddresses().stream()
					.anyMatch(addressDTO -> addressDTO.getId() != null && addressDTO.getId().equals(existingAddress.getId()));

			if (!addressExistsInDTO) {
				addressesToRemove.add(existingAddress);
			}
		}

		for (Address addressToRemove : addressesToRemove) {
			customer.getAddresses().remove(addressToRemove);
			// Remove a referência do cliente para evitar erro de 'a collection with cascade="all-delete-orphan" was no longer referenced'
			addressToRemove.setCustomer(null);
			addressRepository.delete(addressToRemove);
		}

		// Adiciona ou atualiza os endereços presentes no DTO
		for (AddressClientDTO addressDTO : dto.getAddresses()) {
			Address address;
			if (addressDTO.getId() != null) {
				// Atualiza um endereço existente
				address = addressRepository.findById(addressDTO.getId())
						.orElseThrow(() -> new ObjectNotFoundException("Endereço não encontrado: " + addressDTO.getId()));
				mapper.map(addressDTO, address);
			} else {
				// Cria um novo endereço
				address = mapper.map(addressDTO, Address.class);
				address.setCustomer(customer);
			}
			customer.getAddresses().add(address);
		}

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
