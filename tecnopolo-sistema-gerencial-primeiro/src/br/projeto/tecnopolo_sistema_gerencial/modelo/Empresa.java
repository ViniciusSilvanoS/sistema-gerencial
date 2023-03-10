package br.projeto.tecnopolo_sistema_gerencial.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Empresa {

	private Map<Long, Funcionario> funcionariosTotalEmpresa = new HashMap<>();
	private Map<Long, Funcionario> funcionariosSemDep = new HashMap<>();
	private Map<Long, Funcionario> funcionariosQueSairamDaEmpresa = new HashMap<>();
	
	private long countDepartamento = 1;
	private long countIdFuncionario = 1;
	
	private String nome;
	private final String CNPJ;
	private List<Departamento> departamentos;
	
	
	public Empresa(String nome, String cNPJ) {
		super();
		departamentos = new ArrayList<>();
		this.nome = nome;
		CNPJ = cNPJ;
	}
	
	//// ----------------------------------------------------
	
	public void adicionarFuncionarioSemDep(Funcionario funcionario) {
		
		funcionariosSemDep.put(countIdFuncionario, funcionario);
		totalFuncionarios(funcionario);
		
	}
	
	public void totalFuncionarios(Funcionario funcionario) {
		
		funcionariosTotalEmpresa.put(countIdFuncionario, funcionario);
		countIdFuncionario++;
		
	}
	
	public Funcionario procurarFuncionario(String cpf) {
		
		return funcionariosTotalEmpresa.values().parallelStream()
					.filter(e -> e.getCpf().equals(cpf))
					.findFirst()
					.orElse(null);
		
	}
	
	public Funcionario procurarFuncionario(Long id) {
		
		return funcionariosTotalEmpresa.entrySet()
					.parallelStream()
					.filter(e -> e.getKey().equals(id))
					.findFirst()
					.map(Map.Entry::getValue)
					.orElse(null);
		
	}
	
	public Funcionario procurarFuncionarioSemDep(Long id) {
		
		return funcionariosSemDep.entrySet()
				.parallelStream()
				.filter(e -> e.getKey().equals(id))
				.findFirst()
				.map(Map.Entry::getValue)
				.orElse(null);
		
	}
	
	public Long procurarIdFuncionario(Funcionario funcionario) {
		
		return funcionariosTotalEmpresa.entrySet()
				.parallelStream()
				.filter(e -> e.getValue() == funcionario)
				.findFirst()
				.map(Map.Entry::getKey)
				.orElse(null);
		
	}
	
	public Departamento procurarDepartamento(Long referencia) {
		
		return departamentos.parallelStream()
			.filter(e -> e.getReferencia() == referencia)
			.findFirst()
			.orElse(null);
			
	}
	
	public boolean isDepartamentoCadastrado (Departamento departamento) {
		
		return departamentos.parallelStream()
			.anyMatch(e -> e.getNome().trim().equalsIgnoreCase(departamento.getNome().trim()));
		
	}
	
	public void addDepartamento(Departamento departamento) {
		
		departamento.setReferencia(countDepartamento);
		
		departamentos.add(departamento);
		
		countDepartamento++;
		
	}
	
	
	public int quantidadeDeDep () {
		
		return departamentos.size();
		
	}
	
	
	public void tirarFuncionarioDosSemDep (Long id) {
				
		funcionariosSemDep.remove(id);
		
	}
	
	public void excluirFuncionario (Long id) {
		
		Funcionario funcionario = procurarFuncionario(id);
		funcionariosQueSairamDaEmpresa.put(id, funcionario);
		funcionariosTotalEmpresa.remove(id);
		
	}
	
	
	public void ajustarTodosSalarios() {
		
	}
	
	public String listarEmpresa() {
		return "Nome: " + nome + " | CNPJ: " + CNPJ + "\n";
	}
	
	//// ----------------------------------------------------
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	@Override
	public String toString() {
		return "Empresa [nome=" + nome + ", CNPJ=" + CNPJ + ", departamentos=" + departamentos + "]";
	}
	
	public StringBuilder listarFuncionariosCompletoTodos() {
		
		StringBuilder sb = new StringBuilder();
		
		for(Map.Entry<Long, Funcionario> f : funcionariosTotalEmpresa.entrySet()) {
			String departamento = f.getValue().getDepartamento() != null ? f.getValue().getDepartamento().getNome() : "Sem Dep.";
			sb.append("Id:" + f.getKey() + " | Nome: " + f.getValue().getNome() + " | Salario: " + f.getValue().getSalario() + 
					" | CPF: " + f.getValue().getCpf() + " | Data Admissão: "
					+ f.getValue().getDataAdmissao() + " | Departamento: " + departamento + "\n"); // 
		}
		
		return sb;

	}
	
	public StringBuilder listarFuncionariosSimplesTodos() {
		
		StringBuilder sb = new StringBuilder();
		
		for(Map.Entry<Long, Funcionario> f : funcionariosTotalEmpresa.entrySet()) {
			String departamento = f.getValue().getDepartamento() != null ? f.getValue().getDepartamento().getNome() : "Sem Dep.";
			sb.append("Id: " + f.getKey() + " | Nome: " + f.getValue().getNome() + " | Departamento: " + departamento + "\n");
		}
		
		return sb;
		
	}
	
	public StringBuilder listarFuncionariosCompletoSemDep() {
		
		StringBuilder sb = new StringBuilder();
		
		for(Map.Entry<Long, Funcionario> f : funcionariosSemDep.entrySet()) {
			String departamento = f.getValue().getDepartamento() != null ? f.getValue().getDepartamento().getNome() : "Sem Dep.";
			sb.append("Id:" + f.getKey() + " | Nome: " + f.getValue().getNome() + " | Salario: " + f.getValue().getSalario() + 
					" | CPF: " + f.getValue().getCpf() + " | Data Admissão: "
					+ f.getValue().getDataAdmissao() + " | Departamento: " + departamento + "\n"); // 
		}
		
		return sb;

	}
	
	public StringBuilder listarFuncionariosSimplesSemDep() {
		
		StringBuilder sb = new StringBuilder();
		
		for(Map.Entry<Long, Funcionario> f : funcionariosSemDep.entrySet()) {
			String departamento = f.getValue().getDepartamento() != null ? f.getValue().getDepartamento().getNome() : "Sem Dep.";
			sb.append("Id: " + f.getKey() + " | Nome: " + f.getValue().getNome() + " | Departamento: " + departamento + "\n");
		}
		
		return sb;
		
	}
	
	public StringBuilder listarFuncionariosSimplesComDep() {
		
		Map<Long, Funcionario> funcionariosComDep = new HashMap<>();
		
		funcionariosTotalEmpresa.entrySet()
			.stream()
			.filter(e -> !funcionariosSemDep.containsValue(e.getValue()))
			.forEach(e -> funcionariosComDep.put(e.getKey(), e.getValue()));
		
		StringBuilder sb = new StringBuilder();
		
		if(!funcionariosComDep.isEmpty()) {
			
			for(Map.Entry<Long, Funcionario> f : funcionariosComDep.entrySet()) {
				String departamento = f.getValue().getDepartamento() != null ? f.getValue().getDepartamento().getNome() : "Sem Dep.";
				sb.append("Id: " + f.getKey() + " | Nome: " + f.getValue().getNome() + " | Departamento: " + departamento + "\n");
			}
			
		}
		
		return sb;
		
	}
	
	public StringBuilder listarDepartamentos() {
		
		StringBuilder sb = new StringBuilder();
		
		if(departamentos != null) {
			if(departamentos.size() > 0) {
				for(Departamento d: departamentos) {
					sb.append(d.getReferencia() + " - " + d.getNome() + "\n");
				}
			}else {
				sb.append("Nenhum departamento cadastrado!");	
			}
		}else {
			sb.append("Nenhum departamento cadastrado!");	
		}

		return sb;
		
	}
	
}
