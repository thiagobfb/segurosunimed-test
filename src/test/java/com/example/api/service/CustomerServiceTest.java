package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.AddressClientDTO;
import com.example.api.dto.CustomerDTO;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    private List<Customer> customers;

    private CustomerDTO customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Customer c1 = Customer.builder().id(1L).name("Homem Aranha").email("aranha@vingadores.com").gender("M").build();
        Customer c2 = Customer.builder().id(2L).name("Thor").email("thor@vingadores.com").gender("M").build();
        Customer c3 = Customer.builder().id(3L).name("Viuva Negra").email("viuva@vingadores.com").gender("F").build();
        Customer c4 = Customer.builder().id(4L).name("Namor").email("namor@vingadores.com").gender("M").build();
        Customer c5 = Customer.builder().id(5L).name("Gamora").email("gamora@vingadores.com").gender("F").build();

        customers = Arrays.asList(c1, c2, c3, c4, c5);

            customer = CustomerDTO.builder().name("Thiago").email("thiago@vingadores.com").gender("M").addresses(new ArrayList<>()).build();
    }

    @Test
    public void testFindAll() {
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> result = customerService.findAll();
        assertNotNull(result);
    }

    @Test
    public void testFindById() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(Customer.builder().id(1L).build()));
        assertNotNull(customerService.findById(1L));

    }

    @Test
    public void testInsert() {
        Customer c1 = Customer.builder().name("Thiago").email("thiago@vingadores.com").gender("M").addresses(new ArrayList<>()).build();
        when(customerRepository.save(c1)).thenReturn(c1);

        Customer resultado = customerService.insert(customer);
        assertEquals(c1, resultado);
    }

    @Test
    public void testUpdate() {
        Customer c1 = Customer.builder().id(5L).name("Gamora").email("gamora@vingadores.com").gender("F").addresses(new ArrayList<>()).build();
        Customer c2 = Customer.builder().id(5L).name("Gamora").email("gamora2@vingadores.com").gender("F").addresses(new ArrayList<>()).build();
        Address a1 = Address.builder().id(1L).cep("25020010")
                .logradouro("Avenida Doutor Plínio Casado").bairro("Centro").localidade("Duque de Caxias").uf("RJ").build();
        Address a2 = Address.builder().id(2L).cep("22785600")
                .logradouro("Rua Manhuaçu, 55").complemento("Casa 45")
                .bairro("Vargem Grande").localidade("Rio de Janeiro").uf("RJ").build();
        c1.getAddresses().add(a1);
        c1.getAddresses().add(a2);
        c2.getAddresses().add(a1);
        CustomerDTO dto = CustomerDTO.builder().id(5L).name("Gamora").email("gamora2@vingadores.com").gender("F").addresses(new ArrayList<>()).build();
        AddressClientDTO addressClientDTO1 = AddressClientDTO.builder().id(1L).cep("25020010")
                .logradouro("Avenida Doutor Plínio Casado, 111").complemento("Apto. 601").bairro("Centro").localidade("Duque de Caxias").uf("RJ").build();
        dto.getAddresses().add(addressClientDTO1);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(c1));
        doNothing().when(addressRepository).delete(a2);
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(a1));
        when(customerRepository.save(c1)).thenReturn(c1);
        Customer resultado = customerService.update(dto, 5L);
        assertEquals(c2, resultado);
    }

    @Test
    public void testFindCustomers() {
        Page<Customer> page = new PageImpl<>(customers);
        when(customerRepository.findByFilter(PageRequest.of(0, 5),
                null, null, null)).thenReturn(page);
        Page<Customer> result = customerService.findByFilter(0, 5, null, null, null);
        assertIterableEquals(customers, result.getContent());
    }

    @Test
    public void testDelete() {
        Customer c1 = Customer.builder().id(5L).name("Gamora").email("gamora@vingadores.com").gender("F").build();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(c1));
        doNothing().when(customerRepository).delete(c1);
    }

    @Test
    public void testFindByCidadeEstado() {
        Customer c1 = Customer.builder().id(5L).name("Gamora").email("gamora@vingadores.com").gender("F").addresses(new ArrayList<>()).build();
        Customer c2 = Customer.builder().id(6L).name("Thiago").email("thiago@vingadores.com").gender("M").addresses(new ArrayList<>()).build();
        Address a1 = Address.builder().id(1L).cep("25020010")
                .logradouro("Avenida Doutor Plínio Casado").bairro("Centro").localidade("Duque de Caxias").uf("RJ").build();
        Address a2 = Address.builder().id(1L).cep("25075250")
                .logradouro("Rua Tauá, 49").bairro("Centro").localidade("Duque de Caxias").uf("RJ").build();
        c1.getAddresses().add(a1);
        c2.getAddresses().add(a2);
        List<Customer> customersList = Arrays.asList(c1, c2);
        Page<Customer> page = new PageImpl<>(customersList);
        when(customerRepository.findByCidadeEstado(any(), anyString(), anyString())).thenReturn(page);
        Page<Customer> result = customerService.findByCidadeEstado(0, 5, "RJ", "Duque de Caxias");
        assertIterableEquals(customersList, result.getContent());
    }
}
