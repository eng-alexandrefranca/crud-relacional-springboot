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

import br.com.francaorganization.api.crud_relacional.entities.Cargo;
import br.com.francaorganization.api.crud_relacional.repositories.CargoRepository;

@RestController
@RequestMapping(value = "/cargos")
public class CargoController {

	@Autowired
	private CargoRepository repository;
	
	@GetMapping
	public List<Cargo> findAll() {
		return repository.findAll();
	}

	@GetMapping(value = "/{id}")
	public Cargo findById(@PathVariable Byte id) { //XXX Long
		return repository.findById(id).get();
	}
	
	@PostMapping
	public Cargo insert(@RequestBody Cargo cargo) {
		return repository.save(cargo);
	}

	@PutMapping(value = "/{id}")
	public Cargo update(@PathVariable Byte id, @RequestBody Cargo cargoAlterado) { //XXX Long
		Cargo cargoDoBanco = repository.findById(id).get();
		cargoDoBanco.setCargo(cargoAlterado.getCargo());
		cargoDoBanco.setSalario(cargoAlterado.getSalario());
		return repository.save(cargoDoBanco);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Byte id) { //XXX Long
		repository.deleteById(id);
	}
}
