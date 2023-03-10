package br.projeto.tecnopolo_sistema_gerencial.utils;


import java.text.DecimalFormat;

import javax.swing.JOptionPane;

public class Utils {

	static String padrao1 = "R$ ###,##0.00";
	static DecimalFormat df = new DecimalFormat(padrao1);
	
	public static String doubleToString(double valor) {
		return df.format(valor);
	}
	
	public static void escrever(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public static String inserirString(String message) {
		return JOptionPane.showInputDialog(message);
	}
	
	public static int inserirInt(String message) {
		return Integer.parseInt(JOptionPane.showInputDialog(message));
	}
	
	public static Long inserirLong(String message) {
		return Long.parseLong(JOptionPane.showInputDialog(message));
	}
	
	public static Double inserirDouble(String message) {
		return Double.parseDouble(JOptionPane.showInputDialog(message));
	}
	
	public static void out(String message) {
		System.out.println("MESSAGE: " + message);
	}
	
	public static void outLog(String message) {
		System.out.println("LOG: " + message);
	}
		
	
}
