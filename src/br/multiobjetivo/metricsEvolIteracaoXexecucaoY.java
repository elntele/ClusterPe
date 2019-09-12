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
 * essa classe calucula o hiper volume alcançado  execução por execução (imprime uma a uma) no momento da interação x
 * configurada do paramentro inteiro i. 
 * @author elnte
 *
 */

public class metricsEvolIteracaoXexecucaoY {
	
	public static void main(String[] args) {
		Front frontRef = new ArrayFront(1, 4);
		Point point = new ArrayPoint(4);
		point.setDimensionValue(0, 0);
		point.setDimensionValue(1, 0);
		point.setDimensionValue(2, 0);
		point.setDimensionValue(3, 0);

		HypervolumeConc hypervolume = new HypervolumeConc(frontRef);

		Front normalizedFront = null;
		int i =200; // esse i é a itreração fotografada
			
			for (int j = 1; j <= 11; j++) { 
				
				String path = "C:/Users/elnte/OneDrive/Área de Trabalho/rural 2/mestrado/2019.1/computação evolutiva/teste de tempo busca seletiva/"
						+ "resultados busca seletiva/algorithm_KMeans/com busca/execução "+j 
				+ "/FUN" + i + ".tsv";

				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1, normalizedFront.getPoint(s).getDimensionValue(1)/29340);
					normalizedFront.getPoint(s).setDimensionValue(2, normalizedFront.getPoint(s).getDimensionValue(2)/3795187.303);
					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3));
				}
				List<PointSolution> normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
				double hvma = hypervolume.evaluate(normalizedPopulation);

					//comenta esse pra pegar a media jorge
				System.out.print(j + " ");// mude pra i jorge
				System.out.printf("%.4f\n", hvma) ;
				
				
			}

	}
	}
