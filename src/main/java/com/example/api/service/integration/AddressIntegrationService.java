package com.example.api.service.integration;

import com.example.api.dto.AddressClientDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AddressIntegrationService {
    private static final String URI = "https://viacep.com.br/ws/";

    public AddressClientDTO findByCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(URI  + cep + "/json", AddressClientDTO.class);
    }

}
