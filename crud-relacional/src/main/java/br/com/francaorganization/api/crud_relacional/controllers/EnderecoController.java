package br.com.francaorganization.api.crud_relacional.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.francaorganization.api.crud_relacional.entities.Endereco;
import br.com.francaorganization.api.crud_relacional.repositories.EnderecoRepository;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoRepository repository;
	
	@GetMapping
	public List<Endereco> findAll() {
		return repository.findAll();
	}

	@GetMapping(value = "/{id}")
	public Endereco findById(@PathVariable Short id) { //XXX Long
		return repository.findById(id).get();
	}
	
	@PostMapping
	public Endereco insert(@RequestBody Endereco endereco) {
		return repository.save(endereco);
	}

	@PutMapping(value = "/{id}")
	public Endereco update(@PathVariable Short id, @RequestBody Endereco enderecoAlterado) { //XXX Long
		Endereco enderecoDoBanco = repository.findById(id).get();
		enderecoDoBanco.setLogradouro(enderecoAlterado.getLogradouro());
		enderecoDoBanco.setNumero(enderecoAlterado.getNumero());
		enderecoDoBanco.setBairro(enderecoAlterado.getBairro());
		enderecoDoBanco.setCidade(enderecoAlterado.getCidade());
		enderecoDoBanco.setEstado(enderecoAlterado.getEstado());
		enderecoDoBanco.setCep(enderecoAlterado.getCep());
		return repository.save(enderecoDoBanco);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Short id) { //XXX Long
		repository.deleteById(id);
	}
}
