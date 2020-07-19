package br.multiobjetivo;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.HypervolumeConc;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.Point;
import org.uma.jmetal.util.point.impl.ArrayPoint;
import org.uma.jmetal.util.point.util.PointSolution;
/**
 * essa classe tira a media da interação x a ser configurada con parametro i
 * nas n execuções a ser configurada no parametro j fo for
 * é uma fotografia da média em um dado momento ou melhor dada iteração nas n execuções 
 * @author elnte
 *
 */

public class MetricsEvolFotografia {
	
	public static void main(String[] args) {
		Front frontRef = new ArrayFront(1, 4);
		Point point = new ArrayPoint(4);
		point.setDimensionValue(0, 0);
		point.setDimensionValue(1, 0);
		point.setDimensionValue(2, 0);
		point.setDimensionValue(3, 0);
		double wA=19518.068353117065;
		double wB=433736.6460002156;

		HypervolumeConc hypervolume = new HypervolumeConc(frontRef);

		Front normalizedFront = null;
		int i =280; // esse i é a itreração fotografada
			
			for (int j = 1; j <= 13; j++) { 
				double hvma = 0;// cometa esse pra pegar a media jorge
				double hvmb = 0;// cometa esse pra pegar a media jorge
				double hvmc = 0;
				
				String path = "C:\\novos testes encontrar abordagem para media net\\variando canais\\PE\\selecionados para o eniac/execução "
						+ j + "/FUN" + i + ".tsv";
				

				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1, normalizedFront.getPoint(s).getDimensionValue(1)/wA); 
					normalizedFront.getPoint(s).setDimensionValue(2, normalizedFront.getPoint(s).getDimensionValue(2)/wB);
					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3));
				}
				List<PointSolution> normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
				hvma += hypervolume.evaluate(normalizedPopulation);
				
				path = "C:\\novos testes encontrar abordagem para media net\\variando canais\\PE\\busca em todos 30 erlangs/execução "
						+ j + "/FUN" + i/2 + ".tsv";
				
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1, normalizedFront.getPoint(s).getDimensionValue(1)/wA);
					normalizedFront.getPoint(s).setDimensionValue(2, normalizedFront.getPoint(s).getDimensionValue(2)/wB);
					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3));
				}
				normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);

				hvmb += hypervolume.evaluate(normalizedPopulation);

				
				
				path = "C:\\novos testes encontrar abordagem para media net\\variando canais\\PE\\busca em todos 30 erlangs/execução "
						+ j + "/FUN" + i + ".tsv";
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1, normalizedFront.getPoint(s).getDimensionValue(1)/wA);
					normalizedFront.getPoint(s).setDimensionValue(2, normalizedFront.getPoint(s).getDimensionValue(2)/wB);
					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3));
				}
				normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);

				hvmc += hypervolume.evaluate(normalizedPopulation);
				//comenta esse pra pegar a media jorge
				System.out.print(j + " ");// mude pra i jorge
				System.out.printf("%.4f ", hvma) ;
				System.out.printf("%.4f ", hvmb);
				System.out.printf("%.4f\n", hvmc);
				
				
			}

	}

	public static void mainHVEvol(String[] args) {
		Front frontRef = new ArrayFront(1, 4);
		Point point = new ArrayPoint(4);
		point.setDimensionValue(0, 0);
		point.setDimensionValue(1, 0);
		point.setDimensionValue(2, 0);
		point.setDimensionValue(3, 0);

		HypervolumeConc hypervolume = new HypervolumeConc(frontRef);

		Front normalizedFront = null;
		for (int i = 10; i <= 5000; i += 50) {
			int qtde = 0;
			double hv = 0;
			;
			for (int j = 0; j <= 10; j++) {
				String path = "C:/workspace_research/bracis16/results/nsfnet/nsgaiii_std/" + j + "/FUN" + i + ".tsv";
				// String path =
				// "C:/UFRPE/pesquisas/submissões/bracis16elliackin/docs/root_maopso/root_maopso/fronts_run_"
				// + j + "/IFUN." + i;
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				List<PointSolution> normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
				hv += (double) hypervolume.evaluate(normalizedPopulation);
				qtde++;
			}
			System.out.printf("%.4f\n", hv / qtde);
		}

	}


}
