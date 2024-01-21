package com.example.api.web.rest;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.AddressClientDTO;
import com.example.api.dto.CustomerDTO;
import com.example.api.service.integration.AddressIntegrationService;
import com.example.api.web.rest.impl.AddressControllerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressControllerImplTest {

    @InjectMocks
    private AddressControllerImpl addressController;

    @Mock
    private AddressIntegrationService addressIntegrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAddressByCep() {
        AddressClientDTO mockAddressDTO = new AddressClientDTO();
        when(addressIntegrationService.findByCep("12345678")).thenReturn(mockAddressDTO);
        ResponseEntity<AddressClientDTO> responseEntity = addressController.getAddressByCep("12345678");
        assertEquals(mockAddressDTO, responseEntity.getBody());
        verify(addressIntegrationService, times(1)).findByCep("12345678");
    }
}
