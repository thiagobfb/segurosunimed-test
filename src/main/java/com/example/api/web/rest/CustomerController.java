package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.dto.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Tag( name = "Cadastro de Clientes" , description = "Endpoints responsaveis por cadastrar um cliente" )
@RequestMapping("/customers")
public interface CustomerController {

    @Operation( description = "Serviço que retorna todos os clientes" )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200" , description = "Retorna 200 quando a solicitação foi efetuada com sucesso." ),
            @ApiResponse( responseCode = "500" , description = "Retorna 500 quando ocorrer algum erro de negócio." ),
            @ApiResponse( responseCode = "400" , description = "Retorna 400 quando ocorrer algum erro de validação do domínio." ) } )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    ResponseEntity<List<Customer>> findAll();

    @Operation( description = "Serviço que retorna todos os clientes paginados por filtro" )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200" , description = "Retorna 200 quando a solicitação foi efetuada com sucesso." ),
            @ApiResponse( responseCode = "500" , description = "Retorna 500 quando ocorrer algum erro de negócio." ),
            @ApiResponse( responseCode = "400" , description = "Retorna 400 quando ocorrer algum erro de validação do domínio." ) } )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/page")
    ResponseEntity<Page<Customer>> findCustomers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "gender", required = false) String gender);

    @Operation( description = "Serviço que retorna dados de um cliente por ID" )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200" , description = "Retorna 200 quando a solicitação foi efetuada com sucesso." ),
            @ApiResponse( responseCode = "500" , description = "Retorna 500 quando ocorrer algum erro de negócio." ),
            @ApiResponse( responseCode = "400" , description = "Retorna 400 quando ocorrer algum erro de validação do domínio." ) } )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    ResponseEntity<Customer> findById(@PathVariable("id") Long id);

    @Operation( description = "Serviço que cadastra um cliente" )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "201" , description = "Retorna 201 quando a solicitação foi efetuada com sucesso." ),
            @ApiResponse( responseCode = "500" , description = "Retorna 500 quando ocorrer algum erro de negócio." ),
            @ApiResponse( responseCode = "400" , description = "Retorna 400 quando ocorrer algum erro de validação do domínio." ) } )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ResponseEntity<Void> insert(@RequestBody @Valid CustomerDTO customer);

    @Operation( description = "Serviço que atualiza dados de um cliente" )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "204" , description = "Retorna 204 quando a solicitação foi efetuada com sucesso." ),
            @ApiResponse( responseCode = "500" , description = "Retorna 500 quando ocorrer algum erro de negócio." ),
            @ApiResponse( responseCode = "400" , description = "Retorna 400 quando ocorrer algum erro de validação do domínio." ) } )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    ResponseEntity<Void> update(@RequestBody @Valid CustomerDTO customer, @PathVariable Long id);

    @Operation( description = "Serviço que exclui um cliente" )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "204" , description = "Retorna 204 quando a solicitação foi efetuada com sucesso." ),
            @ApiResponse( responseCode = "500" , description = "Retorna 500 quando ocorrer algum erro de negócio." ),
            @ApiResponse( responseCode = "400" , description = "Retorna 400 quando ocorrer algum erro de validação do domínio." ) } )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @Operation( description = "Serviço que retorna lista de cliente por Cidade/Estado" )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200" , description = "Retorna 200 quando a solicitação foi efetuada com sucesso." ),
            @ApiResponse( responseCode = "500" , description = "Retorna 500 quando ocorrer algum erro de negócio." ),
            @ApiResponse( responseCode = "400" , description = "Retorna 400 quando ocorrer algum erro de validação do domínio." ) } )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{uf}/{cidade}")
    ResponseEntity<Page<Customer>> findByCidadeEstado(@PathVariable("uf") String uf, @PathVariable("cidade") String cidade,
                                                      @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "size", defaultValue = "5") Integer size);
}
