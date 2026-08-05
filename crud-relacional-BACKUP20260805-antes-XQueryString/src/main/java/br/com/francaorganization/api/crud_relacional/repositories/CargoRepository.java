package br.com.francaorganization.api.crud_relacional.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.francaorganization.api.crud_relacional.entities.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Byte> {

}
