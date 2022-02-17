package br.multiobjetivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DominateStatistics {

	public static void main(String[] args) {

		
		String it = "it20";
		int divisor = 0;
		Double soma = 0.0;
		Double max= Double.MIN_VALUE;
		Double min= Double.MAX_VALUE;
		int fim=10;
		for (int j = 1; j <= fim; j++) {
			String path = "D:\\resultados\\aleatorio\\localSearch\\nInd4\\"
					+ it + "\\neighbor3/execu��o " + j + "/print.txt";

			try {
				BufferedReader br = new BufferedReader(new FileReader(path));

				String search = "encontrado";
				String line;
				
				while ((line = br.readLine()) != null) {

					if (line.toLowerCase().indexOf(search.toLowerCase()) != -1) {
						divisor = divisor + 1;
						String line1 = line.trim();
						String[] arrayDeLine = line1.split(":");

						String numero = arrayDeLine[arrayDeLine.length - 1];
						numero = numero.replace(" ", "");
						soma = soma + Double.parseDouble(numero);
						if ( Double.parseDouble(numero)>max) {
							max = Double.parseDouble(numero);
						}
						
						if ( Double.parseDouble(numero)<min) {
							min = Double.parseDouble(numero);
						}
						
						System.out.println("N�mero de solu��es n�o dominadas encontrado na execu��o " + j+": " +numero);

					}
					
		
				}
			

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		}
		
		System.out.printf("M�dia de solu��es n�o dominadas = " + "%.2f\n", soma / divisor);
		System.out.println("Maior n�mero de solu��es n�o dominadas encontrado nas "+ fim+" execu��es " +max);
		System.out.println("Menor n�mero de solu��es n�o dominadas encontrado nas "+ fim+" execu��es " + min);

	}
}
