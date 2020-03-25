package br.multiobjetivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindABetterValueToNormatize {

	public static void main(String[] args) {
		
		String path = "C:\\novos testes encontrar abordagem para media net\\variando canais\\PE\\sem busca 30 erlangs/execução "; 

		List<List<String>> fourGreaterValue = new ArrayList();
		double valueOne = Double.MIN_VALUE;
		double valueTwo = Double.MIN_VALUE;
		double valueThree = Double.MIN_VALUE;
		double valueFour = Double.MIN_VALUE;
		System.out.println(valueOne);
		System.out.println(valueTwo);
		System.out.println(valueThree);
		System.out.println(valueFour);
		BufferedReader br;
		for (int i = 1; i <= 11; i++) {
			for (int j = 20; +j <= 500; j += 20) {
				try {
					br = new BufferedReader(new FileReader(path + i + "/FUN" + j + ".tsv"));
					String linha;

					while ((linha = br.readLine()) != null) {
						String[] lista = linha.split(" ");
						if (Double.parseDouble(lista[0]) > valueOne) {
							valueOne = (Double.parseDouble(lista[0]));
						}
						if (Double.parseDouble(lista[1]) > valueTwo) {
							valueTwo = (Double.parseDouble(lista[1]));
						}
						if (Double.parseDouble(lista[2]) > valueThree) {
							valueThree = (Double.parseDouble(lista[2]));
						}
						if (Double.parseDouble(lista[3]) > valueFour) {
							valueFour = (Double.parseDouble(lista[3]));
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
		}
		System.out.println(valueOne);
		System.out.println(valueTwo);
		System.out.println(valueThree);
		System.out.println(valueFour);
	}
}
