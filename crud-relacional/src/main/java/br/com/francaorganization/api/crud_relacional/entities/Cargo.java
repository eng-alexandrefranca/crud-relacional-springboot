package br.com.francaorganization.api.crud_relacional.entities;

//javax -> jakarta
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_cargos")
public class Cargo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cargo")
	private Byte id; //XXX Long
	
	@Column(name = "nome_cargo", nullable = false, length = 100)
	private String cargo; //XXX nome
	
	@Column(name = "salario", nullable = false, precision = 10, scale = 2)// DECIMAL(10,2), onde: 10 = quantidade total de dígitos (somando os algarismos antes e depois da vírgula)
	private BigDecimal salario; //XXX Double
	
// Método Construtor
	
	public Cargo() {
		//(String cargo)
		//this.cargo = cargo
	}	
	
// Getters e Setters
	
////////////////////////////////////- //XXX Long
	public Byte getId() {
		return id;
	}

	public void setId(Byte id) {
		this.id = id;
	}
////////////////////////////////////

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getSalario() { //XXX Double
		return salario;
	}

	public void setSalario(BigDecimal salario) { //XXX Double
		this.salario = salario;
	}
}
