package br.com.francaorganization.api.crud_relacional.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.francaorganization.api.crud_relacional.entities.Pessoa;
import br.com.francaorganization.api.crud_relacional.repositories.PessoaRepository;


@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController {

	@Autowired
	private PessoaRepository repository;
	
	@GetMapping
	public List<Pessoa> findAll() {
		return repository.findAll();
	}
	
	
	/*
	 * pessoas/detalhes: Versão 01 - OK
	 * 
	// Busca usando o CPF enviado via Header "XQueryString"
	@GetMapping(value = "/detalhe")
	public Pessoa findByCpf(@RequestHeader("XQueryString") String cpf) {
		// Removido o ((Object) ...), deixando o Optional
		return repository.findByCpf(cpf)
				.orElseThrow(() -> new RuntimeException("Pessoa não encontrada para o CPF informado"));
	}	
	*/
	
	
	/*
	 * pessoas/detalhes: Versão 02 - OK
	 * 
	@GetMapping(value = "/detalhe")
	public Pessoa findByCpf(@RequestHeader(value = "XQueryString", required = false) String cpf) {
	    System.out.println("O CPF que chegou no Java foi: " + cpf);
	    
	    if (cpf == null) {
	        // Retorna status 400 XQueryString
	        throw new org.springframework.web.server.ResponseStatusException(
	                org.springframework.http.HttpStatus.BAD_REQUEST, "O CPF não foi enviado ou não foi reconhecido!");
	    }
	    
	    return repository.findByCpf(cpf)
	            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
	                    org.springframework.http.HttpStatus.NOT_FOUND, "Pessoa não encontrada para o CPF: " + cpf));
	}
	*/
	
	
	// pessoas/detalhes: Versão 03	
	// Adicionar o param: 'produces' para forçar o Spring a validar o formato de saída (406)
	@GetMapping(value = "/detalhe", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Pessoa findByCpf(@RequestHeader(value = "XQueryString", required = false) String cpf) {
	    System.out.println("O CPF que chegou no Java: " + cpf);
	    
	    // 400 (Bad Request): Cabeçalho ausente ou vazio
	    if (cpf == null || cpf.trim().isEmpty()) {
	        throw new org.springframework.web.server.ResponseStatusException(
	                org.springframework.http.HttpStatusCode.valueOf(400), 
	                "O cabeçalho 'XQueryString' é obrigatório."
	        );
	    }   
	    
	    // STATUS 404 (Not Found): Checagem da sintaxe ok antes do 404 disparar
	    if (cpf.matches("\\d{11}")) {
	        Pessoa pessoa = repository.findByCpf(cpf)
	                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
	                        org.springframework.http.HttpStatusCode.valueOf(404), 
	                        "Recurso não encontrado."
	                ));
	        
	        // STATUS 500 (Internal Server Error): Simulação de bug - CPF especial
	        if (cpf.equals("99999999999")) {
	            throw new RuntimeException("Erro interno simulado: Quebra inesperada no processamento.");
	        }
	        
	        // STATUS 503 (Service Unavailable) 
	        boolean bancoDeDadosForaDoAr = false; // true (Simulação de banco fora do ar)
	        if (bancoDeDadosForaDoAr) {
	            throw new org.springframework.web.server.ResponseStatusException(
	                    org.springframework.http.HttpStatusCode.valueOf(503), 
	                    "Serviço indisponível: O banco de dados não respondeu a tempo."
	            );
	        }
	        
	        return pessoa; // 200
	    }
	    
	    // 422 (Unprocessable Entity)
	    throw new org.springframework.web.server.ResponseStatusException(
	            org.springframework.http.HttpStatusCode.valueOf(422), 
	            "O CPF deve conter exatamente 11 dígitos numéricos."
	    );
	}

	@PostMapping
	public Pessoa insert(@RequestBody Pessoa pessoa) {
		return repository.save(pessoa);
	}

	// Atualização usando o CPF do Header "XQueryString"
	@PutMapping
	public Pessoa update(@RequestHeader("XQueryString") String cpf, @RequestBody Pessoa pessoaAlterada) {
		// Removido o ((Object) ...)
		Pessoa pessoaDoBanco = repository.findByCpf(cpf)
				.orElseThrow(() -> new RuntimeException("Pessoa não encontrada para o CPF informado"));
		
		pessoaDoBanco.setNome(pessoaAlterada.getNome());
		pessoaDoBanco.setDataNascimento(pessoaAlterada.getDataNascimento());
		pessoaDoBanco.setCargo(pessoaAlterada.getCargo());
		pessoaDoBanco.setEndereco(pessoaAlterada.getEndereco());
		
		return repository.save(pessoaDoBanco);
	}

	// Delete usando o CPF enviado via Header "XQueryString"
	@DeleteMapping
	public void delete(@RequestHeader("XQueryString") String cpf) {
		// Removido o ((Object) ...)
		Pessoa pessoaDoBanco = repository.findByCpf(cpf)
				.orElseThrow(() -> new RuntimeException("Pessoa não encontrada."));
		repository.delete(pessoaDoBanco);
	}
}
