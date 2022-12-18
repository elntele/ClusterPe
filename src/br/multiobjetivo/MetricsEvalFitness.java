package br.multiobjetivo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.uma.jmetal.qualityindicator.impl.HypervolumeConc;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.Point;
import org.uma.jmetal.util.point.impl.ArrayPoint;
import org.uma.jmetal.util.point.util.PointSolution;

import br.cns.util.ArrayUtils;

public class MetricsEvalFitness {
	
	public static void main(String[] args) {
		Front frontRef = new ArrayFront(1, 4);
		Point point = new ArrayPoint(4);
		point.setDimensionValue(0, 0);
		point.setDimensionValue(1, 0);
		point.setDimensionValue(2, 0);
		point.setDimensionValue(3, 0);

		HypervolumeConc hypervolume = new HypervolumeConc(frontRef);
		
		// os maiores de pe
//		double wA=18455.964069386184;
//		double wB=428587.36346861825;
		
		//os maiores de pe da dissertação e artigo de resvista em ingles				
		double wA=20922.650104366;
		double wB=612967.639391796;

		// os maiores da midia net para o mestrado/artigo de revista em inglês
		//OBS: mudar o caminho do diretório, se não não bate: 	
		//String  path = "D:\\resultados\\MediaNet\\Elite
//		double wA=19956.369280253864;
//		double wB=1593421.878767534;
	
//		// sem busca
//		double wsbA=17824.4218569369;
//		double wsbB=414062.258070812;
//		// busca em todos 
//		double wbtA=18400.5404621532;
//		double wbtB=433736.646000215;
//		// elite 
//		double weliA=19531.5005107225;
//		double weliB=451751.554806354;
//		// aleatório
//		double waleaA=19166.8158394274;
//		double waleaB=452270.159650401;

		List <Double> hvmaList= new ArrayList<>();
		List <Double> hvmbList= new ArrayList<>();
		List <Double> hvmcList= new ArrayList<>();
		Front normalizedFront = null;
		int begin=9120;
		int end=228000;
		int step=begin;
		int t=20;
		String it="it120";
		String it2="it20";
		String it3="it20";
		for (int i = begin; i <= end; i += step) {// aqui
			// cada um desses hvm representa o hypervolume de uma abordagem, no estudo de k
			// vizinho, hvma, hvmb e hvec eram respectivamente as médias de k2,k4 e k8
			// vizinhos
			
			double hvma = 0;// descomenta esse pra pegar a media jorge
			double hvmb = 0;// descomenta esse pra pegar a media jorge
			double hvmc = 0;// descomenta esse pra pegar a media jorge
			for (int j = 1; j <=30 ; j++) {

				//D:\resultados\elite\localSearchTestingAllAndDontStopUntilArriveInFInalevenFindAFirstDominator\nInd4\it20\neighbor3

				String  path = "D:\\resultados\\eleitos\\localSearchTestingAllAndDontStopUntilArriveInFInalevenFindAFirstDominator + sinc\\nInd8\\it20/execução "+ j + "/FUN" + i + ".tsv";
//				String  path = "D:\\resultados\\sem busca so com espalhamento/execução " 
//						+ j + "/FUN" + i + ".tsv";
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					if (i>=end) {
						path = "D:\\resultados\\elite\\localSearch\\nInd4"+it+"/neighbor3/execução "+ j + "/FUN" + i + ".tsv";
//						path = "D:\\resultados\\sem busca so com espalhamento/execução " 
//								+ j + "/FUN.tsv";
					}
					
				}
				
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				
				
				
				
				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1,
							normalizedFront.getPoint(s).getDimensionValue(1) / wA);//PE=29340   (midianet centroides originais =30984.82 veio do maxevaluating)
					normalizedFront.getPoint(s).setDimensionValue(2,
							normalizedFront.getPoint(s).getDimensionValue(2) / wB);//PE=3795187.303 (midianet centroides originais  =3623002.292673194 veio do find a better value)
					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3));
				}
				List<PointSolution> normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
				double resultA = hypervolume.evaluate(normalizedPopulation);
				hvma += resultA;
				hvmaList.add(resultA);
				

				
				  path =
				  "D:\\novos testes encontrar abordagem para media net\\variando canais\\PE\\busca em todos pos bug do jmetal/execução "
						  + j + "/FUN" + t/2 + ".tsv";
				 
				
				//path = "D:\\resultados\\MediaNet\\busca em todos/execução "+ j + "/FUN" + i + ".tsv";
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					if (i>=end) {
						path = "D:\\resultados\\elite\\localSearchTestingAllAndDontStopUntilArriveInFInalevenFindAFirstDominator\\nInd8\\"+it2+"/neighbor5/execução "+ j + "/FUN" + i + ".tsv";
					}
				}
				
				try {
					normalizedFront = new ArrayFront(path);
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}

				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
					normalizedFront.getPoint(s).setDimensionValue(1,
							normalizedFront.getPoint(s).getDimensionValue(1) / wA);//PE=29340 (midianet centroids kmeans=31727.51 veio do maxevaluating)
					normalizedFront.getPoint(s).setDimensionValue(2,
							normalizedFront.getPoint(s).getDimensionValue(2) /wB);//PE=3795187.303  (centroids kmeans=3785533.180398301 veio do find a better value)
					normalizedFront.getPoint(s).setDimensionValue(3,
							normalizedFront.getPoint(s).getDimensionValue(3) );
				}
				normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
				double resultB = hypervolume.evaluate(normalizedPopulation);
				hvmb += resultB;
				hvmbList.add(resultB);
//				
//				
//				path = "D:\\resultados\\aleatorio\\localSearchTestingAll\\nInd8\\it20\\neighbor3/execução "+ j + "/FUN" + i + ".tsv";
//				try {
//					normalizedFront = new ArrayFront(path);
//				} catch (FileNotFoundException e) {
//					if (i>=end) {
//						path = "D:\\resultados\\eleitos\\"
//								+ "localSearchTestingAllAndDontStopUntilArriveInFInalevenFindAFirstDominator + sinc\\nInd8\\"+it3+"/execução "+ j + "/FUN" + i + ".tsv";
//					}
//				}
//				
//				try {
//					normalizedFront = new ArrayFront(path);
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//
//				for (int s = 0; s < normalizedFront.getNumberOfPoints(); s++) {
//					normalizedFront.getPoint(s).setDimensionValue(0, normalizedFront.getPoint(s).getDimensionValue(0));
//					normalizedFront.getPoint(s).setDimensionValue(1,
//							normalizedFront.getPoint(s).getDimensionValue(1) / wA);//PE=29340 (midianet centroids kmeans=31727.51 veio do maxevaluating)
//					normalizedFront.getPoint(s).setDimensionValue(2,
//							normalizedFront.getPoint(s).getDimensionValue(2) / wB);//PE=3795187.303 (midianet centroids kmeans=3785533.180398301 veio do find a better value)
//					normalizedFront.getPoint(s).setDimensionValue(3, normalizedFront.getPoint(s).getDimensionValue(3));
//				}
//				normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
//
//				//hvmc += hypervolume.evaluate(normalizedPopulation);
//				hvmcList.add(0.0);
				
	

			}
			
			
			StandardDeviation sd= new StandardDeviation();
			 
			Double[] arrayHvmA = new Double[hvmaList.size()];
	        arrayHvmA = hvmaList.toArray(arrayHvmA);
	        double[] arrayHvmAPrimitive = Stream.of(arrayHvmA).mapToDouble(Double::doubleValue).toArray();
			Double standardDvA = sd.evaluate(arrayHvmAPrimitive);
			
			Double[] arrayHvmb = new Double[hvmaList.size()];
			arrayHvmb = hvmbList.toArray(arrayHvmb);
	        double[] arrayHvmBPrimitive = Stream.of(arrayHvmb).mapToDouble(Double::doubleValue).toArray();
			Double standardDvB = sd.evaluate(arrayHvmBPrimitive);

//			Double[] arrayHvmc = new Double[hvmaList.size()];
//			arrayHvmc = hvmcList.toArray(arrayHvmc);
//	        double[] arrayHvmCPrimitive = Stream.of(arrayHvmc).mapToDouble(Double::doubleValue).toArray();
//			Double standardDvC = sd.evaluate(arrayHvmCPrimitive);
			
			
		// tem que lembrar de comentar as onde hvmbList e hvmcList recebem 0: hvmbList.add(0.0) e hvmcList.add(0.0);
			System.out.print(i + " ");
			System.out.printf("%.4f ", hvma/30);
			System.out.printf("%.4f ", standardDvA);
			System.out.printf("%.4f ", hvmb /30);
			System.out.printf("%.4f\n", standardDvB);
//			System.out.printf("%.4f", hvmc /14);
//			System.out.printf("%.4f\n", standardDvC);
			
//			if (i==228000) { 
//				i=end-step;
//			}
			t+=20;
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
