import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.cns.model.GmlData;
import br.cns.model.GmlNode;
import br.cns.persistence.GmlDao;
import cbic15.Kmeans;
import cbic15.Pattern;

public class Principal {

	public static void main(String[] args) {
		String patch = "C:/Users/jorge/workspace/ClusterPe/src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
		// BigListCity bigListCity = new BigListCity(patch);// antes
		// List<City> listCity = bigListCity.getBigListCity();//antes

		List<Pattern> listPatterns = new ArrayList<>();

		GmlData gml = new GmlDao().loadGmlData(patch); // novo

		List<GmlNode> listCity = gml.getNodes();// novo

		/**
		 * lista de cidades sendo converdida em lista de Pattern
		 */
		for (GmlNode c : listCity) {
			double[] variables = { c.getLatitude(), c.getLongitude() };
			Pattern pattern = new Pattern(c.getLabel(), variables, null);
			pattern.setId(c.getId());
			listPatterns.add(pattern);
		}

		// antes
		// for (City c : listCity) {
		// double[] variables = { c.getLatitude(), c.getLongitude() };
		// Pattern pattern = new Pattern(c.getName(), variables, null);
		// listPatterns.add(pattern);
		// }

		gml.createComplexNetwork();
		List<List<Double>> globalResultSillhouetteAverange = new ArrayList();
		List<String> listStringCluster = new ArrayList<>();
		HashMap<Integer, List<Integer>> mapIteration = new HashMap<Integer, List<Integer>>();
		List<List<Double>> maxMinAverangeDisntanceInterCentroids = new ArrayList<>();
		List<List<Double>> maxMindistanceIntraCluster = new ArrayList();
		List<List<Double>> maxMindistanceBetweenCentroidsAndNodes = new ArrayList();
		List<Pattern>[] copyFinalclustters=null;

		/**
		 * loop executa o kmeans com k de 4 a 20, ou seja com 4 a 20 clusters ha
		 * uma lista de centroids que esta sendo montada, impressa e
		 * reinicializada a cada k do loop até o ultimo k onde ela não é mais
		 * reinicializada. no final de cada iteração ha um impressão do Index
		 * Silhouette.
		 * 
		 */
		int kSizeMin = 4;
		int kSizeMax = 30;
		for (int i = kSizeMin; i <= kSizeMax; i++) {
			List<Pattern> listCentroids = new ArrayList<>();
			List<Double> resultSilhouetteAverage = new ArrayList();
			List<Integer> listIteration = new ArrayList<>();
			int w = 0;
			double silhouetteAverage = 0;
			double DistanceAverange = 0;
			while (w < 30) {
				// array de listas do tipo pattern sendo preparado para ser
				// passado
				// (mais a frente) como parametro para o método
				// getSilhouetteIndex da classe Kmeans
				List<Pattern>[] clustters = new List[i];

				System.out.println("%%%%%%%%%%%%%%%%%%numero de clusters= " + i + " %%%%%%%%%%%%%%%%%%%");

				Kmeans kmeans = new Kmeans(i, listPatterns);
				clustters = kmeans.execute(200);
				// List<String> litleCluster = new ArrayList<>();
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

					// calculando a distância mínima, máxima e média entre os
					// centrois
					Pattern[] centroids = kmeans.getNearestPatternsFromCentroid();
					AllDistancesCLuster node = new AllDistancesCLuster(centroids, gml, clustters);

					maxMinAverangeDisntanceInterCentroids.add(node.distanceInterCentroids());
					if (i == kSizeMax) {
						copyFinalclustters=clustters;
						maxMindistanceBetweenCentroidsAndNodes = node.distanceBetweenCentroidAndNodesInCluster();
						maxMindistanceIntraCluster = node.distanceIntraCluster();

						for (List L : maxMindistanceBetweenCentroidsAndNodes) {
							double max = (double) L.get(0);
							for (int h = 0; h < gml.getDistances().length; h++) {
								for (int j = 0; j < gml.getDistances().length; j++) {
									if (max == gml.getDistances()[h][j]) {
										for (GmlNode g : gml.getNodes()) {
											if (g.getId() == h)
												System.out.println("cidade a:" + g.getLabel());
											if (g.getId() == j)
												System.out.println("cidade b:" + g.getLabel());

										}

									}
								}

							}
						}

					}
				}

				// for (int j = 0; j <
				// kmeans.getNearestPatternsFromCentroid().length; j++) {
				// System.out.println(
				// "centroid do cluster: " + (j + 1) +
				// kmeans.getNearestPatternsFromCentroid()[j].getName());
				// }
				silhouetteAverage += kmeans.getSilhouetteIndex(clustters);
				listIteration.add(kmeans.getCountItaration());
				w += 1;
			}
			listIteration.sort(null);
			mapIteration.put(i, listIteration);
			resultSilhouetteAverage.add(silhouetteAverage / w);
			resultSilhouetteAverage.add((double) i);
			globalResultSillhouetteAverange.add(resultSilhouetteAverage);
		}

		SpreadSheet dataSheet = new SpreadSheet(globalResultSillhouetteAverange, listStringCluster,copyFinalclustters);
		dataSheet.createSpreedSheetMMAverangeDistanceBetweenCentroids(maxMinAverangeDisntanceInterCentroids, kSizeMin);
		dataSheet.createSpreedSheetMMAverangeDistanceBetweenCentroidAndNode(maxMindistanceBetweenCentroidsAndNodes);
		dataSheet.createSpreedSheetMMAverangeDistanceIntraCluster(maxMindistanceIntraCluster);
		dataSheet.createSpreedSheetItereation(mapIteration, kSizeMin, kSizeMax);
		dataSheet.createSpreadForMapGoogle();
	}
}
