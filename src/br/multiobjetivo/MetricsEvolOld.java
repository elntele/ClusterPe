package br.multiobjetivo;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.qualityindicator.impl.hypervolume.WFGHypervolume;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.Point;
import org.uma.jmetal.util.point.impl.ArrayPoint;
import org.uma.jmetal.util.point.util.PointSolution;

public class MetricsEvolOld {
	
	public static void main(String[] args) {
		Front frontRef = new ArrayFront(1, 4);
//		Point point = new ArrayPoint(4);
//		point.setDimensionValue(0, 0);
//		point.setDimensionValue(1, 0);
//		point.setDimensionValue(2, 0);
//		point.setDimensionValue(3, 0);
		
		WFGHypervolume hypervolume = new WFGHypervolume(frontRef);
		//PISAHypervolume hypervolume = new PISAHypervolume(frontRef);
		String pathCbusca = "C:/Users/jorge/workspace/ClusterPe/src/cbusca/";
		String pathSbusca = "C:/Users/jorge/workspace/ClusterPe/src/sbusca/";

		Front normalizedFront = null;
			for (int i = 1; i <= 11; i++) {
				String path = pathSbusca+ "FUN" + i + ".TSV";
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				List<PointSolution> normalizedPopulation =  FrontUtils.convertFrontToSolutionList(normalizedFront);
				System.out.printf("%.4f ", hypervolume.evaluate(normalizedPopulation));

				path = pathCbusca+ "FUN" + i + ".TSV";
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
				System.out.printf("%.4f\n", hypervolume.evaluate(normalizedPopulation));
			}
		

	}

	public static void mainHVEvol(String[] args) {
		Front frontRef = new ArrayFront(1, 4);
		Point point = new ArrayPoint(4);
		point.setDimensionValue(0, 0);
		point.setDimensionValue(1, 0);
		point.setDimensionValue(2, 0);
		point.setDimensionValue(3, 0);
		
		WFGHypervolume hypervolume = new WFGHypervolume(frontRef);


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
				hv += hypervolume.evaluate(normalizedPopulation);
				qtde++;
			}
			System.out.printf("%.4f\n", hv / qtde);
		}

	}

}
