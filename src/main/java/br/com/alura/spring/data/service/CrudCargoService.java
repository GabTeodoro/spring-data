package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private final CargoRepository cargoRepository;
	private Boolean system = true;

	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {

		while (system) {
			System.out.println("Qual ação de cargo deseja executar:");
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

		System.out.println("Descrição do Cargo: ");
		String descricao = scanner.next();
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
		System.out.println("Cargo salvo!");
	}

	private void atualizar(Scanner scanner) {

		System.out.println("Qual ID deseja atualizar: ");
		int id = scanner.nextInt();
		System.out.println("Descrição do Cargo para atualizar: ");
		String descricao = scanner.next();

		Cargo cargo = new Cargo();
		cargo.setId(id);
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
		System.out.println("Atualizado! ");
	}

	private void visualizar() {

		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(c -> System.out.println(c));
	}

	private void deletar(Scanner scanner) {

		System.out.println("Qual ID deseja deletar: ");
		int id = scanner.nextInt();
		cargoRepository.deleteById(id);
		System.out.println("Cargo removido! ");
	}

}
