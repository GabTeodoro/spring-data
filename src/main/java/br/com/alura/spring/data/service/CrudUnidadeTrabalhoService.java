package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {

	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	private Boolean system = true;

	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {

		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public void inicial(Scanner scanner) {

		while (system) {
			System.out.println("Qual ação de Unidade de Trabalho deseja executar:");
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
				visualizar();
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

	private void salvar(Scanner scanner) {
		System.out.println("Nome da unidade de trabalho: ");
		String descricao = scanner.next();
		System.out.println("Endereço da unidade de trabalho: ");
		String endereco = scanner.next();

		UnidadeTrabalho trabalho = new UnidadeTrabalho();
		trabalho.setDescricao(descricao);
		trabalho.setEndereco(endereco);

		unidadeTrabalhoRepository.save(trabalho);
		System.out.println("Unidade de Trabalho salva!");

	}

	private void atualizar(Scanner scanner) {

		System.out.println("Qual ID da unidade de trabalho deseja atualizar: ");
		Integer id = scanner.nextInt();
		System.out.println("Nome da unidade de trabalho: ");
		String descricao = scanner.next();
		System.out.println("Endereço da unidade de trabalho: ");
		String endereco = scanner.next();

		UnidadeTrabalho trabalho = new UnidadeTrabalho();
		trabalho.setId(id);
		trabalho.setDescricao(descricao);
		trabalho.setEndereco(endereco);

		unidadeTrabalhoRepository.save(trabalho);
		System.out.println("Unidade de Trabalho atualizada!");

	}

	private void visualizar() {

		Iterable<UnidadeTrabalho> trabalhos = unidadeTrabalhoRepository.findAll();
		trabalhos.forEach(t -> System.out.println(t));

	}

	private void deletar(Scanner scanner) {
	

		System.out.println("Qual ID da unidade de trabalho deseja atualizar: ");
		Integer id = scanner.nextInt();
		
		unidadeTrabalhoRepository.deleteById(id);
		System.out.println("Unidade de Trabalho deletada!");

	}
}
