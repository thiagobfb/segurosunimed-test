package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.dto.AddressClientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag( name = "Endpoint de Endereços" , description = "Endpoints responsaveis por cadastrar um cliente" )
@RequestMapping("/addresses")
public interface AddressController {

    @Operation( description = "Serviço que consulta o endereço pelo CEP" )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200" , description = "Retorna 200 quando a solicitação foi efetuada com sucesso." ),
            @ApiResponse( responseCode = "500" , description = "Retorna 500 quando ocorrer algum erro de negócio." ),
            @ApiResponse( responseCode = "400" , description = "Retorna 400 quando ocorrer algum erro de validação do domínio." ) } )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{cep}")
    ResponseEntity<AddressClientDTO> getAddressByCep(@PathVariable String cep);
}
