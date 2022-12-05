package br.multiobjetivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindABetterValueToNormatizeFitness {
	
public static void main(String[] args) {// aleatorio elite 
		
		String path = "C:\\Users\\elnte\\OneDrive\\Área de Trabalho\\RedeParaCECin\\src\\resultados\\algorithm_KMeans\\com busca/execução "; 
		int begin=9120;
		int end=230280;
		int step=begin;

		List<List<String>> fourGreaterValue = new ArrayList();
		Double valueOne = Double.MIN_VALUE;
		Double valueTwo = Double.MIN_VALUE;
		Double valueThree = Double.MIN_VALUE;
		Double valueFour = Double.MIN_VALUE;
		System.out.println(valueOne);
		System.out.println(valueTwo);
		System.out.println(valueThree);
		System.out.println(valueFour);
		BufferedReader br;
		for (int i = 1; i <= 14; i++) {
			for (int j = begin; +j <= end; j += step) {
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
		String v1=valueOne.toString();
		v1=v1.replace(".", ",");
		String v2=valueTwo.toString();
		v2=v2.replace(".", ",");
		String v3=valueThree.toString();
		v3=v3.replace(".", ",");
		String v4=valueFour.toString();
		v4=v4.replace(".", ",");
		System.out.println(v1);
		System.out.println(v2);
		System.out.println(v3);
		System.out.println(v4);
	}

}
