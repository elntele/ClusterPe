package br.multiobjetivo;
import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetal.qualityindicator.impl.HypervolumeConc;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.Point;
import org.uma.jmetal.util.point.impl.ArrayPoint;
import org.uma.jmetal.util.point.util.PointSolution;

public class MetricsEvol {
	public static void main(String[] args) {
		Front frontRef = new ArrayFront(1, 4);
		Point point = new ArrayPoint(4);
		point.setDimensionValue(0, 0);
		point.setDimensionValue(1, 0);
		point.setDimensionValue(2, 0);
		point.setDimensionValue(3, 0);

		HypervolumeConc hypervolume = new HypervolumeConc(frontRef);

		Front normalizedFront = null;
		for (int i = 300; i <= 300; i += 20) {//aqui
//			double hvma = 0;// descomenta esse pra pegar a media jorge
//			double hvmb = 0;// descomenta esse pra pegar a media jorge
			for (int j = 1; j <= 11; j++) {
				double hvma = 0;// conmeta esse pra pegar a media jorge
				double hvmb = 0;// conmeta esse pra pegar a media jorge
				String path = "C:/Users/jorge/workspace/ClusterPe/src/sem busca/execu��o " + j + "/FUN" + i + ".tsv";
//				String path = "C:/UFRPE/gradua��o/tcc/jorge/sem busca/execu��o " + j + "/FUN" + i + ".tsv";
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
				hvma += hypervolume.evaluate(normalizedPopulation);
				

				path = "C:/Users/jorge/workspace/ClusterPe/src/com busca local/execu��o " + j + "/FUN" + i + ".tsv";
				// String path =
				// "C:/UFRPE/pesquisas/submiss�es/bracis16elliackin/docs/root_maopso/root_maopso/fronts_run_"
				// + j + "/IFUN." + i;
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1, normalizedFront.getPoint(s).getDimensionValue(1)/29340);
					normalizedFront.getPoint(s).setDimensionValue(2, normalizedFront.getPoint(s).getDimensionValue(2)/3795187.303);
					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3)/0.943406697);
				}
				normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);

				hvmb += hypervolume.evaluate(normalizedPopulation);
				//comenta esse pra pegar a media jorge
				System.out.print(/*i*/j + " ");// mude pra i jorge
				System.out.printf("%.4f ", hvma) ;
				System.out.printf("%.4f\n", hvmb);

				
			}
			//descomenta esse pra pegar a media jorge
//			System.out.print(i + " ");
//			System.out.printf("%.4f ", hvma/11) ;
//			System.out.printf("%.4f\n", hvmb/11);
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
				// "C:/UFRPE/pesquisas/submiss�es/bracis16elliackin/docs/root_maopso/root_maopso/fronts_run_"
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