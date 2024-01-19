package com.example.api.web.rest.impl;

import com.example.api.dto.AddressClientDTO;
import com.example.api.service.integration.AddressIntegrationService;
import com.example.api.web.rest.AddressController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AddressControllerImpl implements AddressController {

    private final AddressIntegrationService addressIntegrationService;

    @Override
    public ResponseEntity<AddressClientDTO> getAddressByCep(String cep) {
        return ResponseEntity.ok().body(addressIntegrationService.findByCep(cep));
    }
}
