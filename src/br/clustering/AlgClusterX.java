package br.clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.cns.experiments.centrality.CentralizationComparison;
import br.cns.model.GmlData;
import br.cns.model.GmlNode;
import br.cns.persistence.GmlDao;
import cbic15.Kmeans;
import cbic15.Pattern;

public class AlgClusterX {

	public static void main(String[] args) throws IOException {
		String patch = "src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
		GmlData gml = new GmlDao().loadGmlData(patch); // novo
		List<Pattern> listPatterns = new ArrayList<>();
		List<GmlNode> listCity = gml.getNodes();// novo
		TableToList tableToList = new TableToList();
		Pattern[] centroidsExternoParaUsarEmOutroIndices;
		int SeletorIndexQaulity = 1; // para selecionar a string em nameTable
		String[] nameTable = { "silhouette", "dunn" };

		for (GmlNode c : listCity) {
			double[] variables = { c.getLatitude(), c.getLongitude() };
			Pattern pattern = new Pattern(c.getLabel(), variables, null);
			pattern.setId(c.getId());
			listPatterns.add(pattern);
		}

		gml.createComplexNetwork();
		List<List<Double>> globalResultQualityIndexAverange = new ArrayList();
		List<String> listStringCluster = new ArrayList<>();
		HashMap<Integer, List<Integer>> mapIteration = new HashMap<Integer, List<Integer>>();
		List<List<Double>> maxMinAverangeDisntanceInterCentroids = new ArrayList<>();
		List<List<Double>> maxMindistanceIntraCluster = new ArrayList();
		List<List<Double>> maxMindistanceBetweenCentroidsAndNodes = new ArrayList();
		List<Pattern>[] copyFinalclustters = null;

		/**
		 * loop executa o kmeans com k de 4 a 20, ou seja com 4 a 20 clusters ha uma
		 * lista de centroids que esta sendo montada, impressa e reinicializada a cada k
		 * do loop até o ultimo k onde ela não é mais reinicializada. no final de cada
		 * iteração ha um impressão do Index Silhouette.
		 * 
		 */
		int kSizeMin = 4;
		int kSizeMax = 30;
		ClusterEmptData clusterEmptData = new ClusterEmptData();
		for (int i = kSizeMin; i <= kSizeMax; i++) {
			List<Pattern> listCentroids = new ArrayList<>();
			List<Double> resultQualityIndexAverage = new ArrayList();// lista pra pegar a média
			List<Integer> listIteration = new ArrayList<>();
			int w = 0;
			int numClusterSize0 = 0;
			double qualityIndexAverage = 0;
			double DistanceAverange = 0;
			MetricsIntraCluster metrics = new MetricsIntraCluster();
			while (w < 30) {
				boolean clusterEmptSignal = false;
				// array de listas do tipo pattern sendo preparado para ser
				// passado
				// (mais a frente) como parametro para o método
				// getSilhouetteIndex da classe Kmeans
				List<Pattern>[] clustters = new List[i];// descomentar depois

				/**
				 * neste parte tem que chamar a aplicação em python com i de 4 a 40 de acordo
				 * com o for externo ela tel que substituir as linas marcadas: Kmeans kmeans =
				 * new Kmeans(i, listPatterns); clustters = kmeans.execute(200);
				 * 
				 */

				System.out.println("%%%%%%%%%%%%%%%%%%numero de clusters= " + i + " %%%%%%%%%%%%%%%%%%%");
				String[] alg = { "algorithm_PSC", "algorithm_KMeans", "algorithm_FCMeans" };

				String patchCluster = "src/" + alg[2] + "/clusters_k_" + i + "_exec_" + w + ".csv";
				String patchCentroid = "src/" + alg[2] + "/centroids_k_" + i + "_exec_" + w + ".csv";
				;

				ClusterCentroid clusterCentroidCluster = new ClusterCentroid();
				clusterCentroidCluster = tableToList.retrievCluster(patchCluster);
				clustters = clusterCentroidCluster.getCluster();

				// guardando dados de quando houver cluster vazio
				Kmeans kmeans = new Kmeans(i, listPatterns);// subtituir este
				// MetricsIntraCluster metrics= new MetricsIntraCluster();
				if (tableToList.clusterEmpte(clustters) > 0) {
					numClusterSize0 -= 1;
					clusterEmptSignal = true;
					clusterEmptData.setkAndexecution(clustters, w);
					System.out.println("numero de vazios: " + clusterEmptData.getkAndexecution().size());
					System.out.println("numClusterSize0: " + numClusterSize0);
				} else {
					metrics.setStringCluster(clustters);
					listStringCluster.add(metrics.getStringCluster());
					Pattern[] centroids = metrics.monteCentroids(patchCentroid, tableToList, kmeans, clustters);
					metrics.setCluster(clustters);
				}

				int interation = clusterCentroidCluster.getInteration();
				// clustters = tableToList.retrievCluster(patchCluster).getCluster() ; // jorge

//				Kmeans kmeans = new Kmeans(i, listPatterns);// subtituir este
				// clustters = kmeans.execute(200);// substituir este jorge
				// List<String> litleCluster = new ArrayList<>();
//				(!clusterEmptSignal) 

				Pattern[] centroids;
				if (!clusterEmptSignal) {
					MetricsIntraCluster metrics1 = new MetricsIntraCluster();
					metrics1.setStringCluster(clustters);
					listStringCluster.add(metrics1.getStringCluster());
					centroids = metrics1.monteCentroids(patchCentroid, tableToList, kmeans, clustters);
				} else {
					clustters = metrics.getCluster();
					centroids = metrics.getCentroids();
				}
				if (w == 29) {

//					Pattern[] centroids =metrics.monteCentroids(patchCentroid, tableToList, kmeans, clustters);
					AllDistancesCLuster node = new AllDistancesCLuster(centroids, gml, clustters);
					maxMinAverangeDisntanceInterCentroids.add(node.distanceInterCentroids());

					// essa parte é só para imprimir nomes de cidades
					if (i == kSizeMax) {
						copyFinalclustters = clustters;
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

				if (!clusterEmptSignal) {
					switch (nameTable[SeletorIndexQaulity]) {
					case "silhouette":
						qualityIndexAverage += kmeans.getSilhouetteIndex(clustters);
						break;
					case "dunn":
						Dunn dunn = new Dunn(centroids, gml, clustters);
						qualityIndexAverage += dunn.dunnBiggerBetwennTwoPoints();

						break;

					default:
						break;
					}
					listIteration.add(interation);
				}
				// listIteration.add(kmeans.getCountItaration());
				w += 1;
			}
			listIteration.sort(null);
			mapIteration.put(i, listIteration);
			resultQualityIndexAverage.add(qualityIndexAverage / (w - numClusterSize0));
			resultQualityIndexAverage.add((double) i);
			globalResultQualityIndexAverange.add(resultQualityIndexAverage);
		}

		SpreadSheet dataSheet = new SpreadSheet(globalResultQualityIndexAverange, listStringCluster, copyFinalclustters,
				nameTable[SeletorIndexQaulity]);
		dataSheet.createSpreedSheetMMAverangeDistanceBetweenCentroids(maxMinAverangeDisntanceInterCentroids, kSizeMin);
		dataSheet.createSpreedSheetMMAverangeDistanceBetweenCentroidAndNode(maxMindistanceBetweenCentroidsAndNodes);
		dataSheet.createSpreedSheetMMAverangeDistanceIntraCluster(maxMindistanceIntraCluster);
		dataSheet.createSpreedSheetItereation(mapIteration, kSizeMin, kSizeMax);
		dataSheet.createSpreadForMapGoogle();
		dataSheet.createSpreedSheetCountEmptCluster(clusterEmptData);
	}

}
