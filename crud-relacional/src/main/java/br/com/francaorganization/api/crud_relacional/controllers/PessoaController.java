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

	@GetMapping(value = "/{id}")
	public Pessoa findById(@PathVariable Short id) { //XXX Long
		return repository.findById(id).get();
	}
	
	@PostMapping
	public Pessoa insert(@RequestBody Pessoa pessoa) {
		return repository.save(pessoa);
	}

	@PutMapping(value = "/{id}")
	public Pessoa update(@PathVariable Short id, @RequestBody Pessoa pessoaAlterada) { //XXX Long
		Pessoa pessoaDoBanco = repository.findById(id).get();
		pessoaDoBanco.setNome(pessoaAlterada.getNome());
		pessoaDoBanco.setCpf(pessoaAlterada.getCpf());
		pessoaDoBanco.setDataNascimento(pessoaAlterada.getDataNascimento());
		pessoaDoBanco.setCargo(pessoaAlterada.getCargo());
		pessoaDoBanco.setEndereco(pessoaAlterada.getEndereco());
		return repository.save(pessoaDoBanco);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Short id) { //XXX Long
		repository.deleteById(id);
	}
}
