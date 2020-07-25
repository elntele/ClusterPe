package br.multiobjetivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.DrbgParameters.NextBytes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MediaOfOneObjectiveInAllPopulation {

	public static void main(String[] args) {
		
//		String path = "C:\\novos testes encontrar abordagem para media net\\variando canais\\PE\\selecionados para o eniac/execução ";
		String path = "C:\\novos testes encontrar abordagem para media net\\variando canais\\PE\\busca em todos 30 erlangs/execução "; 
		BufferedReader br;
		int objective=3;
		Double[] mediaObjetivo= new Double[25];
		double wA=19518.068353117065;
		double wB=433736.6460002156;
		for (int i=0;i<mediaObjetivo.length;i++) {
			mediaObjetivo[i]=0.0;
		}
//		Random r = new Random();
//		for (int z=0;z<3;z++) {
//			System.out.println(r.nextInt(31));//		}
//		
		for (int i = 1; i <= 13; i++) {
			int index=0;
			for (int j = 20; +j <= 500; j += 20) {
				
				try {
					br = new BufferedReader(new FileReader(path + i + "/FUN" + j/2 + ".tsv"));
					String linha;
					int divisor=0;
					Double sum=0.0;
					while ((linha = br.readLine()) != null) {
						String[] lista = linha.split(" ");
						sum+=(Double.parseDouble(lista[objective]));
						divisor+=1;
					}
					sum=sum/divisor;
					mediaObjetivo[index]+=sum;
					index+=1;

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		int w=20;
		List <Double>l = new ArrayList<>();
		
		for (Double d: mediaObjetivo) {
//			l.add(d/13);
;			System.out.print(w + " ");
			System.out.printf("%.4f\n", d /13);
			w+=20;
		}
//		Collections.sort(l);
//		
//		double media =l.get(0);
//		media+=l.get(l.size()-1);
//		media=media/2;
//		System.out.println("media maior e menor " + media);
//	
	}

}
