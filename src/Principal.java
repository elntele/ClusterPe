import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import cbic15.Kmeans;
import cbic15.Pattern;

public class Principal {

	public static void main(String[] args) {
		String patch = "C:/Users/jorge/workspace/ClusterPe/src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
		BigListCity bigListCity = new BigListCity(patch);
		List<City> listCity = bigListCity.getBigListCity();
		List<Pattern> listPatterns = new ArrayList<>();
		

		/**
		 * lista de cidades sendo converdida em lista de Pattern
		 */
		for (City c : listCity) {
			double[] variables = { c.getLatitude(), c.getLongitude() };
			Pattern pattern = new Pattern(c.getName(), variables, null);
			listPatterns.add(pattern);
		}

		List<List<Double>> globalResultSillhouetteAverange = new ArrayList();
		List<String> listStringCluster = new ArrayList<>();
		HashMap <Integer, List<Integer>> mapIteration = new HashMap <Integer, List<Integer>>();
		List<List<Double>> maxMinAverangeDisntance = new ArrayList<>();

		/**
		 * loop executa o kmeans com k de 4 a 20, ou seja com 4 a 20 clusters ha
		 * uma lista de centroids que esta sendo montada, impressa e
		 * reinicializada a cada k do loop até o ultimo k onde ela não é mais
		 * reinicializada. no final de cada iteração ha um impressão do Index
		 * Silhouette.
		 * 
		 */
		for (int i = 4; i <= 20; i++) {
			List<Pattern> listCentroids = new ArrayList<>();
			List<Double> resultSilhouetteAverage = new ArrayList();
			List<Integer> listIteration = new ArrayList<>();
			int w = 0;
			double silhouetteAverage = 0;
			double DistanceAverange=0;
			while (w < 30) {
				// array de listas do tipo pattern sendo preparado para ser
				// passado
				// (mais a frente) como parametro para o método
				// getSilhouetteIndex da classe Kmeans
				List<Pattern>[] clustters = new List[i];

				System.out.println("%%%%%%%%%%%%%%%%%%numero de clusters= " + i + " %%%%%%%%%%%%%%%%%%%");

				Kmeans kmeans = new Kmeans(i, listPatterns);
				clustters = kmeans.execute(200);
				//List<String> litleCluster = new ArrayList<>();
				if (w == 29) {
					// montando a lista com os nomes das cidades de cada cluster
					String stringCluster = "";
					for (int u = 0; u < clustters.length; u++) {
						stringCluster += "{";
						for (Pattern p : clustters[u]) {
							stringCluster += p.getName();
							stringCluster += ", ";
						}
						stringCluster += "} ";
					}
					listStringCluster.add(stringCluster);
					// calculando a distância mínima, máxima e média entre os centrois
					Pattern[] centroids = kmeans.getNearestPatternsFromCentroid();
					double minDistance = Double.MAX_VALUE;
					double maxDistance = Double.MIN_VALUE;
					double CurrentDistance = 0;
					double averageDistance = 0;
					for (int j = 0; j < centroids.length; j++) {
						for (int g = j; g < centroids.length; g++) {
							if (j != g) {
								CurrentDistance = centroids[j].euclidianDistance(centroids[g]);
								if (CurrentDistance > maxDistance)
									maxDistance = CurrentDistance;
								if (CurrentDistance < minDistance)
									minDistance = CurrentDistance;
								DistanceAverange += CurrentDistance;
							}
						}

					}
					List<Double> localMaxMinAverangeDistance = new ArrayList<>();
					localMaxMinAverangeDistance.add(maxDistance);
					localMaxMinAverangeDistance.add(minDistance);
					localMaxMinAverangeDistance.add(DistanceAverange / centroids.length);
					maxMinAverangeDisntance.add(localMaxMinAverangeDistance);
				}

				for (int j = 0; j < kmeans.getNearestPatternsFromCentroid().length; j++) {
					System.out.println(
							"centroid do cluster" + (j + 1) + kmeans.getNearestPatternsFromCentroid()[j].getName());
				}
				silhouetteAverage += kmeans.getSilhouetteIndex(clustters);
				w += 1;
				listIteration.add(kmeans.getCountItaration());
			}
			listIteration.sort(null);
			mapIteration.put(i, listIteration);
			resultSilhouetteAverage.add(silhouetteAverage / w);
			resultSilhouetteAverage.add((double) i);
			globalResultSillhouetteAverange.add(resultSilhouetteAverage);
		}

		
//		System.out.println(listIteration);
		
		
		SpreadSheet dataSheet = new SpreadSheet(globalResultSillhouetteAverange, listStringCluster);
		dataSheet.createSpreedSheetMMAverangeDistance(maxMinAverangeDisntance);
		dataSheet.createSpreedSheetItereation(mapIteration);
	}
}
