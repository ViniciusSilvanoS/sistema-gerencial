package br.projeto.tecnopolo_sistema_gerencial.modelo;

import java.util.ArrayList;
import java.util.List;


public class Departamento {
	
	private String nome;
	private long referencia;
	private Empresa empresa;
	private List<Funcionario> funcionarios;
	
	
	public Departamento(String nome, Empresa empresa) {
		
		this.nome = nome;
		this.empresa = empresa;
		funcionarios = new ArrayList<>();
		
	}
	
	//// ----------------------------------------------------

	 public void inserirFuncionarioDep(Funcionario funcionario) {
		 
		 funcionarios.add(funcionario);
		 
	 }

	//// ----------------------------------------------------
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(Funcionario funcionarios) {
		this.funcionarios.add(funcionarios);
	}
	
	public long getReferencia() {
		return referencia;
	}

	public void setReferencia(long referencia) {
		this.referencia = referencia;
	}

	@Override
	public String toString() {
		return "Departamento [nome=" + nome + ", referencia=" + referencia + ", empresa=" + empresa + ", funcionarios="
				+ funcionarios + "]";
	}
	
	
	
}
