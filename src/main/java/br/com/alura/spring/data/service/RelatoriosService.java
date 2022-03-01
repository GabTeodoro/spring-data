package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
	private FuncionarioRepository funcionarioRepository;

	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {

		while (system) {
			System.out.println("Qual ação de cargo deseja executar:");
			System.out.println("0 - Voltar");
			System.out.println("1 - Busca funcionário por nome");
			System.out.println("2 - Busca funcionário por nome/salário/data de contratação");
			System.out.println("3 - Busca ID, Nome e Salário do funcionário");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				buscaFuncionarioNome(scanner);
				break;
			case 2:
				buscaNomeSalarioMaiorDataContratacao(scanner);
				break;
			case 3:
				buscaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
		}

	}

	private void buscaFuncionarioNome(Scanner scanner) {
		System.out.println("Qual o nome pesquisar: ");
		String nome = scanner.next();
		List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);
		funcionarios.forEach(System.out::println);
	}

	private void buscaNomeSalarioMaiorDataContratacao(Scanner scanner) {
		System.out.println("Qual o nome do funcionário: ");
		String nome = scanner.next();

		System.out.println("Qual o salario do funcionário: ");
		BigDecimal salario = scanner.nextBigDecimal();

		System.out.println("Qual a data de contratação: ");
		String dataTemp = scanner.next();
		LocalDate data = LocalDate.parse(dataTemp, formatter);

		List<Funcionario> lista = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, data);
		lista.forEach(System.out::println);
	}

	private void buscaFuncionarioSalario() {

		List<FuncionarioProjecao> lista = funcionarioRepository.findFuncionarioSalario();
		lista.forEach(f -> System.out
				.println("Funcionário ID: " + f.getId() + " | Nome: " + f.getNome() + " | Salário: " + f.getSalario()));
	}
}
