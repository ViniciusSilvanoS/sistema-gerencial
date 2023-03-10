package br.projeto.tecnopolo_sistema_gerencial;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.projeto.tecnopolo_sistema_gerencial.modelo.Departamento;
import br.projeto.tecnopolo_sistema_gerencial.modelo.Empresa;
import br.projeto.tecnopolo_sistema_gerencial.modelo.Funcionario;
import br.projeto.tecnopolo_sistema_gerencial.utils.Utils;

public class Aplicacao {

	public static List<Empresa> empresas;
	
	public static void main(String[] args) {
		
		empresas = new ArrayList<>();
		
		Empresa e1 = new Empresa("Primeiro Teste", "111");
		Empresa e2 = new Empresa("Segundo Teste", "22222222222222");
		
		empresas.add(e1);
		empresas.add(e2);
		
		Funcionario f1 = new Funcionario("Vinicius", 6500.00, "987", e1, "01/01/2023");
		Funcionario f2 = new Funcionario("Mauricio", 3800.00, "654", e1, "10/09/2023");
		Funcionario f3 = new Funcionario("Eliana", 8500.00, "321", e2, "05/03/2020");
		
		operacoesEscolha();
	
		
	}
	
	public static void operacoesEscolha() {
		
		String escolha = Utils.inserirString("Digite a senha de acesso: \n1 - Empresas\n2 - Tecnopolo\n0 - sair");
		
		switch(escolha) {
			case "1":
				
				String cnpj = Utils.inserirString("CNPJ: ");
				Empresa empresa = procurarEmpresa(cnpj);
				
				if(empresa != null) {
					operacoesEmpresa(empresa);					
				}else {
					Utils.escrever("Empresa cnpj: " + cnpj + " não encontrado!");
					operacoesEscolha();
				}
				break;
				
			case "2":
				operacoesGeral();
				break;
			case "0":
				Utils.escrever("Saindo!!!");
				System.exit(0);
			default:
				Utils.escrever("Opção inválida!");
				operacoesEscolha();
		}
		
	}

	
	//// ----------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	public static void operacoesGeral() {
		
		String escolha = Utils.inserirString("1 - Listar empresas\n2 - Cadastrar empresa\n3 - Excluir empresa\n0 - Voltar ao menu principal");
		
		switch (escolha) {
		case "1": {
			listarEmpresas();
			break;
		}
		case "2": {
			cadastrarEmpresa();
			break;
		}
		case "3":
			excluirEmpresa();
			break;
		case "0":
			operacoesEscolha();
			break;
		default:
			Utils.escrever("Valor digitado inválido!");
			operacoesGeral();
		}
		
	}
	
	
	public static void listarEmpresas() {
		
		StringBuilder sb = new StringBuilder();
		
		empresas.stream()
			.forEach(e -> sb.append(e.listarEmpresa()));
			
		Utils.escrever(sb.toString());
		
		operacoesGeral();
		
	}
	
	
	public static void cadastrarEmpresa() {
		
		String nome = Utils.inserirString("Nome da empresa: ");
		String cnpj = Utils.inserirString("Qual o CNPJ: ");
		
		if(!isCnpjCadastrado(cnpj)) {
			Empresa empresa = new Empresa(nome, cnpj);
			empresas.add(empresa);
			
			Utils.escrever("Empresa cadastrada com sucesso!");
		}else {
			Utils.escrever("CNPJ já foi cadastrado!");
		}
		
		operacoesGeral();
		
	}
	
	
	public static boolean isCnpjCadastrado(String cnpj) {
		
		if(empresas.size() > 0) { 
			boolean resultado = empresas.stream()
						.anyMatch(e -> e.getCNPJ().equals(cnpj));
			return resultado;
		}else {
			return false;
		}
		
	}
	
	
	public static Empresa procurarEmpresa(String cnpj) {
		
		return empresas.parallelStream()
			.filter(e -> e.getCNPJ().equals(cnpj))
			.findFirst()
			.orElse(null);
		
	}
	
	
	public static int procurarIndexEmpresa(Empresa empresa) {
		
		int index = empresas.indexOf(empresa);
		return index;
		
	}
	
	
	public static void excluirEmpresa() {
		
		String cnpj = Utils.inserirString("Digite o CNPJ da empresa que deseja excluir: ");
		
		Empresa empresa;
		empresa = procurarEmpresa(cnpj);
		
		if(empresa != null) {
			
			int escolha = JOptionPane.showConfirmDialog(null, empresa.listarEmpresa() + 
					"\n\nTem certeza exluir a empresa e todas suas dependências?", 
					"Aviso!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
			if(escolha == 0) {
				
				int indexEmpresa = procurarIndexEmpresa(empresa);
				
				if(indexEmpresa >= 0) {
					empresas.remove(indexEmpresa);
					Utils.escrever("Empresa removida com sucesso!");
				}else {
					Utils.escrever("Ocorreu um erro ao remover a empresa!");
				}

			}else {
				Utils.escrever("Operação cancelada!");
			}
			
		}else {
			Utils.escrever("Empresa não encontrada!");
		}
		
		operacoesGeral();
		
	}
	
	
	//// ----------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	public static void operacoesEmpresa(Empresa empresa) {
				
		String escolha = Utils.inserirString("1 - Listar funcionários\n2 - Cadastrar funcionário\n3 - Inserir funcionário ao departamento"
				+ "\n4 - Excluir funcionário\n5 - Transferir funcionário de departamento"
				+ "\n6 - Listar departamentos\n7 - Cadastrar departamento"
				+ "\n0 - Voltar ao menu");
		
		switch (escolha) {
		case "1":
			listarFuncionarios(empresa);
			break;
		case "2":
			cadastrarFuncionario(empresa);
			break;
		case "3":
			inserirFuncionarioDep(empresa);
			break;
		case "4":
			excluirFuncionario(empresa);
			break;
		case "5":
			transferirFuncionarioDep(empresa);
			break;
		case "6":
			listarDepartamentos(empresa);
			break;
		case "7":
			cadastrarDepartamento(empresa);
			break;
		case "0":
			operacoesEscolha();
			break;
		default:
			Utils.escrever("Opção inválida!");
			operacoesEmpresa(empresa);
		}
		
	}
	
	// ----------------------------
	
	public static void cadastrarFuncionario(Empresa empresa) {
		
		String cpf = Utils.inserirString("CPF: ");
		
		Funcionario funcionarioEmpresa = empresa.procurarFuncionario(cpf);
		if(funcionarioEmpresa == null) {
			
			String nome = Utils.inserirString("Nome funcionário: ");
			
			Double salario = Utils.inserirDouble("Digite salário do funcionário: ");
			
			String dataAdmissao = Utils.inserirString("Digite a data de admissão: \nFormato: dia/mês/ano - 00/00/0000");
			
			Funcionario funcionario = new Funcionario(nome, salario, cpf, empresa, dataAdmissao); // CONFIRMAR SE É A MELHOR FORMA FAZER ASSIM
			Utils.escrever("Funcionário cadastrado com sucesso!");
			
			int escolhaDep = JOptionPane.showConfirmDialog(null, "Deseja atribuir departamento ao funcionário? ", 
					"Escolha.", JOptionPane.YES_NO_OPTION);
			
			if(escolhaDep == 0) {
				
				Long id = empresa.procurarIdFuncionario(funcionario);
				inserirFuncionarioDep(empresa, id);
				
			}
			
		}else {
			
			Utils.escrever(funcionarioEmpresa.toString() + "\nJá é um funcionário cadastrado!");
			
		}
		
		operacoesEmpresa(empresa);
		
	}
	
	
	public static void listarFuncionarios(Empresa empresa) {
		
		StringBuilder sb = new StringBuilder();
		
		sb = empresa.listarFuncionariosCompletoTodos();
		if(!sb.isEmpty()) {
			Utils.escrever(sb.toString());
		}else {
			Utils.escrever("Nenhum funcionário cadastrado!");
		}
		
		operacoesEmpresa(empresa);
		
	}
	
	// ----------------------------
	
	public static void cadastrarDepartamento(Empresa empresa) {
		
		String nomeDep = Utils.inserirString("Digite o nome do departamento");
		
		Departamento departamento = new Departamento(nomeDep, empresa);		
			
		if(!empresa.isDepartamentoCadastrado(departamento)) {
			
			empresa.addDepartamento(departamento);
			
			Utils.escrever("Departamento cadastrado com sucesso!");
			
		}else {
			Utils.escrever("Este departamento já foi cadastrado!");
		}
		
		operacoesEmpresa(empresa);
		
	}
	
	
	public static void listarDepartamentos(Empresa empresa) {
		
		StringBuilder sb = new StringBuilder();
		
		sb = empresa.listarDepartamentos();
		Utils.escrever(sb.toString());
		
		operacoesEmpresa(empresa);
		
	}
	
	
	public static void inserirFuncionarioDep(Empresa empresa){
		
		if(empresa.quantidadeDeDep() == 0) {
			Utils.escrever("Nenhum departamento cadastrado!");
			operacoesEmpresa(empresa);
		}
		
		Long idFuncionario = Utils.inserirLong("Digite o ID do funcionário: \n\n" + empresa.listarFuncionariosSimplesSemDep().toString());
		
		Funcionario funcionario = empresa.procurarFuncionarioSemDep(idFuncionario);
		
		if(funcionario != null) {
			
			if(funcionario.getDepartamento() == null) {

				Long idDep = Utils.inserirLong("Digite o ID do departamento: \n\n" + empresa.listarDepartamentos().toString());
				
				Departamento departamento;
				departamento = empresa.procurarDepartamento(idDep);
				
				if(departamento != null) {

					funcionario.setDepartamento(departamento);
					departamento.inserirFuncionarioDep(funcionario);
					empresa.tirarFuncionarioDosSemDep(idFuncionario); // Tirando funcionario do array de sem Departamento
					Utils.escrever("Funcionário inserido com sucesso!");
					
				}else {
					Utils.escrever("Departamento não existe!");			
				}
				
				
			}else { // Já tem departamento tem que transferir
				Utils.escrever("Funcionário já possui departamento: " + funcionario.getDepartamento().getNome() + ".");
			}
			
		}else { // funcionario nao existe tem que inserir
			
			Funcionario funcionarioEmpresa = empresa.procurarFuncionario(idFuncionario);
			
			if(funcionarioEmpresa != null) {
				Utils.escrever("Funcionário ( " + funcionarioEmpresa.getNome() + " ) está cadastrado no departamento: " + funcionarioEmpresa.getDepartamento().getNome());
			}else {
				Utils.escrever("Funcionário não está cadastrado!");

			}
			
		}
		
		
		operacoesEmpresa(empresa);
		
	}
	
	
	public static void inserirFuncionarioDep(Empresa empresa, Long idFuncionario){ // Inserir Dep durante cadastro de funcionário
		
		if(empresa.quantidadeDeDep() == 0) {
			Utils.escrever("Nenhum departamento cadastrado!");
			operacoesEmpresa(empresa);
		}
		
		Funcionario funcionario = empresa.procurarFuncionarioSemDep(idFuncionario);
		
		if(funcionario != null) {
			
			if(funcionario.getDepartamento() == null) {

				Long idDep = Utils.inserirLong("Digite o ID do departamento: \n\n" + empresa.listarDepartamentos().toString());
				
				Departamento departamento;
				departamento = empresa.procurarDepartamento(idDep);
				
				if(departamento != null) {

					funcionario.setDepartamento(departamento);
					departamento.inserirFuncionarioDep(funcionario);
					empresa.tirarFuncionarioDosSemDep(idFuncionario); // Tirando funcionario do array de sem Departamento
					Utils.escrever("Funcionário inserido com sucesso!");
					
				}else {
					Utils.escrever("Departamento não existe!");			
				}
				
				
			}else { // Já tem departamento tem que transferir
				Utils.escrever("Funcionário já possui departamento: " + funcionario.getDepartamento().getNome() + ".");
			}
			
		}else { // funcionario nao existe tem que inserir
			
			Funcionario funcionarioEmpresa = empresa.procurarFuncionario(idFuncionario);
			
			if(funcionarioEmpresa != null) {
				Utils.escrever("Funcionário ( " + funcionarioEmpresa.getNome() + " ) está cadastrado no departamento: " + funcionarioEmpresa.getDepartamento().getNome());
			}else {
				Utils.escrever("Funcionário não está cadastrado!");

			}
			
		}
		
		
		operacoesEmpresa(empresa);
		
	}
	
	
	public static void excluirFuncionario(Empresa empresa){
		
		// reconhcer o funcionario pelo id
		Long id = Utils.inserirLong("Digite o ID do funcionário: ");
		Funcionario funcionario = empresa.procurarFuncionario(id);
		Funcionario funcionarioSemDep = empresa.procurarFuncionarioSemDep(id);
		
		if(funcionario != null) {

			// excluir departamento do objeto Funcionário
			if(funcionario.getDepartamento() != null) {
				funcionario.setDepartamento(null);
			}
			
			// limpar do arrray de funcionariosTotal e colocar no array de ex funcionários
			if(funcionarioSemDep != null) {
				empresa.tirarFuncionarioDosSemDep(id);
			}
			
			String dataDesligamento = Utils.inserirString("Digite a data de admissão: \nFormato: dia/mês/ano - 00/00/0000");
			funcionario.setDataDesligamento(dataDesligamento);
			empresa.excluirFuncionario(id);
			
			Utils.escrever("Funcionário excluido com sucesso!");
			
		}else {
			Utils.escrever("Funcionário não encontrado!");
		}
		
		operacoesEmpresa(empresa);
		
	}
	
	
	public static void transferirFuncionarioDep(Empresa empresa){
		
		// funcionario desejado
		Long id = Utils.inserirLong("Digite o id do funcionário: \n" + empresa.listarFuncionariosSimplesComDep());
		Funcionario funcionario = empresa.procurarFuncionario(id);
		
		if(funcionario != null) {
			
			// informar id dep e listar dep's na hora para escolher id
			Long idDep = Utils.inserirLong("Digite o ID do departamento: \n\n" + empresa.listarDepartamentos().toString());
			Departamento departamento = empresa.procurarDepartamento(idDep);
			if(departamento != null) {
				
				if(funcionario.getDepartamento() != departamento) {
					
					// alterar dep do objeto funcionario
					funcionario.setDepartamento(departamento);
					
					Utils.escrever("Funcionário transferido com sucesso!");
					
				}else {
					Utils.escrever("Funcionário já faz parte deste departamento!");
				}
				
			}else {
				Utils.escrever("Departamento não encontrado!");
			}
			
		}else {
			Utils.escrever("Funcionario não existe!");
		}

		operacoesEmpresa(empresa);
		
	}
	
}
