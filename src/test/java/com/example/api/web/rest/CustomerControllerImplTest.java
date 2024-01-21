package com.example.api.web.rest;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.AddressClientDTO;
import com.example.api.dto.CustomerDTO;
import com.example.api.service.CustomerService;
import com.example.api.web.rest.impl.CustomerControllerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerControllerImplTest {


    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerControllerImpl customerController;

    private Customer customer;
    private CustomerDTO dto;

    HttpServletResponse httpServletResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customer = Customer.builder().id(6L).name("Gamora").email("gamora@vingadores.com").gender("F").addresses(new ArrayList<>()).build();
        Address a1 = Address.builder().id(4L).cep("25020010")
                .logradouro("Avenida Doutor Plínio Casado").bairro("Centro").localidade("Duque de Caxias").uf("RJ").build();
        Address a2 = Address.builder().id(5L).cep("22785600")
                .logradouro("Rua Manhuaçu, 55").complemento("Casa 45")
                .bairro("Vargem Grande").localidade("Rio de Janeiro").uf("RJ").build();
        customer.getAddresses().add(a1);
        customer.getAddresses().add(a2);
        dto = CustomerDTO.builder().name("Thiago").email("thiago@vingadores.com").gender("M").addresses(new ArrayList<>()).build();
        AddressClientDTO addressClientDTO1 = AddressClientDTO.builder().id(1L).cep("25020010")
                .logradouro("Avenida Doutor Plínio Casado, 111").complemento("Apto. 601").bairro("Centro").localidade("Duque de Caxias").uf("RJ").build();
        dto.getAddresses().add(addressClientDTO1);
        httpServletResponse = new MockHttpServletResponse();
    }

    @Test
    public void testInsertComDadosValidos() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(customerService.insert(any(CustomerDTO.class))).thenReturn(customer);

        ResponseEntity<Void> responseEntity = customerController.insert(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    void testInsertComFalhaNaInsercao() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("Thiago").email("thiago@vingadores.com").gender("").addresses(new ArrayList<>()).build();
        when(customerService.insert(any(CustomerDTO.class))).thenThrow(new RuntimeException("Erro na inserção"));
        assertThrows(RuntimeException.class, () -> {
            customerController.insert(customerDTO);
        });
    }

    @Test
    void testFindAll() {
        List<Customer> customerList = new ArrayList<>();
        when(customerService.findAll()).thenReturn(customerList);
        ResponseEntity<List<Customer>> responseEntity = customerController.findAll();
        assertEquals(customerList, responseEntity.getBody());
        verify(customerService, times(1)).findAll();
    }

    @Test
    void testFindCustomers() {
        Page<Customer> customerPage = mock(Page.class);
        when(customerService.findByFilter(anyInt(), anyInt(), anyString(), anyString(), anyString())).thenReturn(customerPage);
        ResponseEntity<Page<Customer>> responseEntity = customerController.findCustomers(0, 5, "John", "john@example.com", "M");
        assertEquals(customerPage, responseEntity.getBody());
        verify(customerService, times(1)).findByFilter(0, 5, "John", "john@example.com", "M");
    }

    @Test
    void testFindById() {
        Customer mockCustomer = new Customer();
        when(customerService.findById(1L)).thenReturn(mockCustomer);
        ResponseEntity<Customer> responseEntity = customerController.findById(1L);
        assertEquals(mockCustomer, responseEntity.getBody());
        verify(customerService, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        CustomerDTO customerDTO = new CustomerDTO();
        ResponseEntity<Void> responseEntity = customerController.update(customerDTO, 1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(customerService, times(1)).update(eq(customerDTO), eq(1L));
    }

    @Test
    void testDelete() {
        ResponseEntity<Void> responseEntity = customerController.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(customerService, times(1)).delete(1L);
    }

    @Test
    void testFindByCidadeEstado() {
        Page<Customer> customerPage = mock(Page.class);
        when(customerService.findByCidadeEstado(anyInt(), anyInt(), anyString(), anyString())).thenReturn(customerPage);
        ResponseEntity<Page<Customer>> responseEntity = customerController.findByCidadeEstado("SP", "SaoPaulo", 0, 5);
        assertEquals(customerPage, responseEntity.getBody());
        verify(customerService, times(1)).findByCidadeEstado(0, 5, "SP", "SaoPaulo");
    }
}