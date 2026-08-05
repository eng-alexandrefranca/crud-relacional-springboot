package br.com.francaorganization.api.crud_relacional.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.francaorganization.api.crud_relacional.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Short> {

}
