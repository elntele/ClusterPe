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
		for (int i = 20; i <= 500; i += 20) {// aqui
			// cada um desses hvm representa o hypervolume de uma abordagem, no estudo de k
			// vizinho, hvma, hvmb e hvec eram respectivamente as médias de k2,k4 e k8
			// vizinhos
			double hvma = 0;// descomenta esse pra pegar a media jorge
			double hvmb = 0;// descomenta esse pra pegar a media jorge
			double hvmc = 0;// descomenta esse pra pegar a media jorge
			for (int j = 1; j <= 11; j++) {
//				double hvma = 0;// cometa esse pra pegar a media jorge
//				double hvmb = 0;// cometa esse pra pegar a media jorge

				String path = "C:/Users/elnte/OneDrive/Área de Trabalho/rural 2/mestrado/2019.2/orientação/Eniac copiado de c.e/parcial midianet/com busca seletista elitista 2/algorithm_KMeans_medianet/com busca/execução "
						+ j + "/FUN" + i + ".tsv";

				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1,
							normalizedFront.getPoint(s).getDimensionValue(1) / 31727.51);//PE=29340   (midianet=31727.51 veio do maxevaluating)
					normalizedFront.getPoint(s).setDimensionValue(2,
							normalizedFront.getPoint(s).getDimensionValue(2) / 3785533.180398301);//PE=3795187.303 (midianet=3785533.180398301 veio do find a better value)
					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3));
				}
				List<PointSolution> normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
				hvma += hypervolume.evaluate(normalizedPopulation);

				path = "C:/Users/elnte/OneDrive/Área de Trabalho/rural 2/mestrado/2019.2/orientação/Eniac copiado de c.e/parcial midianet/com busca seletiva aleatória/execução "
						+ j + "/FUN" + i + ".tsv";
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1,
							normalizedFront.getPoint(s).getDimensionValue(1) / 31727.51);//PE=29340 (midianet=31727.51 veio do maxevaluating)
					normalizedFront.getPoint(s).setDimensionValue(2,
							normalizedFront.getPoint(s).getDimensionValue(2) /3785533.180398301);//PE=3795187.303  (midianet=3785533.180398301 veio do find a better value)
					normalizedFront.getPoint(s).setDimensionValue(3,
							normalizedFront.getPoint(s).getDimensionValue(3) );
				}
				normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);

				hvmb += hypervolume.evaluate(normalizedPopulation);
				// comenta esse pra pegar a media jorge
//				System.out.print(/*i*/j + " ");// mude pra i jorge
//				System.out.printf("%.4f ", hvma) ;
//				System.out.printf("%.4f\n", hvmb);
				path = "C:/Users/elnte/OneDrive/Área de Trabalho/rural 2/mestrado/2019.2/orientação/Eniac copiado de c.e/parcial midianet/busca em todos os individos/execução "
						+ j + "/FUN" + i + ".tsv";
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1,
							normalizedFront.getPoint(s).getDimensionValue(1) / 31727.51);//PE=29340 (midianet=31727.51 veio do maxevaluating)
					normalizedFront.getPoint(s).setDimensionValue(2,
							normalizedFront.getPoint(s).getDimensionValue(2) / 3785533.180398301);//PE=3795187.303 (midianet=3785533.180398301 veio do find a better value)
					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3));
				}
				normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);

				hvmc += hypervolume.evaluate(normalizedPopulation);
				// comenta esse pra pegar a media jorge
//				System.out.print(/*i*/j + " ");// mude pra i jorge
//				System.out.printf("%.4f ", hvma) ;
//				System.out.printf("%.4f\n", hvmb);

			}
			// descomenta esse pra pegar a media jorge
			System.out.print(i + " ");
			System.out.printf("%.4f ", hvma / 11);
			System.out.printf("%.4f ", hvmb / 11);
			System.out.printf("%.4f\n", hvmc / 11);
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
