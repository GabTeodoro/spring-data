package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private final FuncionarioRepository funcionarioRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	private final CargoRepository cargoRepository;

	private Boolean system = true;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");

	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository,
			UnidadeTrabalhoRepository unidadeTrabalhoRepository, CargoRepository cargoRepository) {

		this.funcionarioRepository = funcionarioRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {

		while (system) {
			System.out.println("Qual ação de funcionario deseja executar:");
			System.out.println("0 - Voltar");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar Todos");
			System.out.println("4 - Deletar");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar(scanner);
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	public void salvar(Scanner scanner) {
		
		System.out.println("Nome do funcionário: ");
		String nome = scanner.next();
		
		System.out.println("CPF do funcionário: ");
		String cpf = scanner.next();
		
		System.out.println("Salário do funcionário: ");
		BigDecimal salario = scanner.nextBigDecimal();
		
		System.out.println("Data de contratação do funcionário: ");
		String dataTemp = scanner.next();
		LocalDate data = LocalDate.parse(dataTemp, formatter);
		
		System.out.println("CargoId do funcionário: ");
		int cargoId = scanner.nextInt();

		List<UnidadeTrabalho> unidades = unidade(scanner);

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(data);
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidades);
		
		funcionarioRepository.save(funcionario);
		System.out.println("Funcionário Salvo!");

	}

	private List<UnidadeTrabalho> unidade(Scanner scanner) {
		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<>();

		while (isTrue) {
			System.out.println("Digite o unidadeId (Para sair digite 0)");
			int unidadeId = scanner.nextInt();

			if (unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				isTrue = false;
			}
		}

		return unidades;
	}

	public void atualizar(Scanner scanner) {
		
		System.out.println("ID do funcionário: ");
		int id = scanner.nextInt();		
		System.out.println("Nome do funcionário: ");
		String nome = scanner.next();
		System.out.println("CPF do funcionário: ");
		String cpf = scanner.next();
		System.out.println("Salário do funcionário: ");
		BigDecimal salario = scanner.nextBigDecimal();
		System.out.println("Data de contratação do funcionário: ");
		String dataTemp = scanner.next();
		LocalDate data = LocalDate.parse(dataTemp, formatter);
		System.out.println("CargoId do funcionário: ");
		int cargoId = scanner.nextInt();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(data);
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		
		funcionarioRepository.save(funcionario);
		System.out.println("Funcionário Alterado!");
	}

	public void visualizar(Scanner scanner) {
		
		System.out.println("Qual página deseja visualizar: ");
		int page = scanner.nextInt();
		
		Pageable pagealbe = PageRequest.of(page, 2, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pagealbe);
		
		System.out.println(funcionarios);
		System.out.println("Página Atual " + funcionarios.getNumber());
		System.out.println("Total de elementos " + funcionarios.getTotalElements());
		funcionarios.forEach(f -> System.out.println(f));
	}

	public void deletar(Scanner scanner) {

		System.out.println("Digite o ID para deletar: ");
		int id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Funcionario deletado!");
	}

}
