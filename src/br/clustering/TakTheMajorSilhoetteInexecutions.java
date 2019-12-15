package br.clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.cns.model.GmlData;
import br.cns.model.GmlNode;
import br.cns.persistence.GmlDao;
import cbic15.Kmeans;
import cbic15.Pattern;

public class TakTheMajorSilhoetteInexecutions {

	public static void main(String[] args) throws IOException {
//		String patch = "src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
		String patch = "src/meniaNetGermanyAmpliada.gml";
		GmlData gml = new GmlDao().loadGmlData(patch); // novo
		List<Pattern> listPatterns = new ArrayList<>();
		String[] countryNameInGml = { "Brazil", "Germany" };
		int countrySelector=1;
		TableToList tableToList = new TableToList(countryNameInGml[countrySelector]);
		List<GmlNode> listCity = gml.getNodes();// novo
		Pattern[] centroidsCopia = null;

		for (GmlNode c : listCity) {
			double[] variables = { c.getLatitude(), c.getLongitude() };
			Pattern pattern = new Pattern(c.getLabel(), variables, null);
			pattern.setId(c.getId());
			listPatterns.add(pattern);
		}

		gml.createComplexNetwork();
		List<List<Double>> globalResultSillhouetteAverange = new ArrayList();
		List<String> listStringCluster = new ArrayList<>();
		HashMap<Integer, List<Integer>> mapIteration = new HashMap<Integer, List<Integer>>();
		List<List<Double>> maxMinAverangeDisntanceInterCentroids = new ArrayList<>();
		List<List<Double>> maxMindistanceIntraCluster = new ArrayList();
		List<List<Double>> maxMindistanceBetweenCentroidsAndNodes = new ArrayList();
		List<Pattern>[] copyFinalclustters = null;

		/**
		 * loop seleciona a execução desejada do alg desejado setado nos parametros
		 * ClusterSelected e algselected. Então recupera as metricas inclusive o
		 * silhette, tudo é salvo nas tabelas dentro do projeto
		 */
		int NumberClusterSelected = 18;
		String[] alg = { "algorithm_PSC", "algorithm_KMeans", "algorithm_FCMeans","algorithm_KMeans_medianet"};
		String algselected = alg[3];
		System.out.println("algoritmo selecionado: " + algselected);
		Double maiorSilhouette = Double.MIN_VALUE;
		int NumeroDAExecucao = 0;
		for (int i = 0; i <= 29; i++) {
			ClusterEmptData clusterEmptData = new ClusterEmptData();
			List<Pattern> listCentroids = new ArrayList<>();
			List<Double> resultSilhouetteAverage = new ArrayList();// lista pra
																	// pegar a média
			List<Integer> listIteration = new ArrayList<>();
			int numClusterSize0 = 0;
			double silhouetteAverage = 0;
			double DistanceAverange = 0;
			MetricsIntraCluster metrics = new MetricsIntraCluster();
			boolean clusterEmptSignal = false;
			// array de listas do tipo pattern sendo preparado para ser
			// passado
			// (mais a frente) como parametro para o método
			// getSilhouetteIndex da classe Kmeans
			List<Pattern>[] clustters = new List[NumberClusterSelected];// descomentar
																		// depois

			/**
			 * neste parte tem que chamar a aplicação em python com i de 4 a 40 de acordo
			 * com o for externo ela tel que substituir as linas marcadas: Kmeans kmeans =
			 * new Kmeans(i, listPatterns); clustters = kmeans.execute(200);
			 * 
			 */

			System.out
					.println("%%%%%%%%%%%%%%%%%%numero de clusters= " + NumberClusterSelected + " %%%%%%%%%%%%%%%%%%%");

			String patchCluster = "src/" + algselected + "/clusters_k_" + NumberClusterSelected + "_exec_" + i + ".csv";
			String patchCentroid = "src/" + algselected + "/centroids_k_" + NumberClusterSelected + "_exec_" + i
					+ ".csv";
			;

			ClusterCentroid clusterCentroidCluster = new ClusterCentroid();
			clusterCentroidCluster = tableToList.retrievCluster(patchCluster);
			clustters = clusterCentroidCluster.getCluster();

			// guardando dados de quando houver cluster vazio
			Kmeans kmeans = new Kmeans(NumberClusterSelected, listPatterns);// subtituir
																			// este
			if (tableToList.clusterEmpte(clustters) > 0) {
				numClusterSize0 -= 1;
				clusterEmptSignal = true;
				clusterEmptData.setkAndexecution(clustters, i);
				System.out.println("numero de vazios: " + clusterEmptData.getkAndexecution().size());
				System.out.println("numClusterSize0: " + numClusterSize0);
			} else {
				metrics.setStringCluster(clustters);
				listStringCluster.add(metrics.getStringCluster());
				Pattern[] centroids = metrics.monteCentroids(patchCentroid, tableToList, kmeans, clustters);
				metrics.setCluster(clustters);
			}

			int interation = clusterCentroidCluster.getInteration();

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

//			AllDistancesCLuster node = new AllDistancesCLuster(centroids, gml, clustters);
//			maxMinAverangeDisntanceInterCentroids.add(node.distanceInterCentroids());

//			if (NumberClusterSelected == NumberClusterSelected) {
//				copyFinalclustters = clustters;
//				maxMindistanceBetweenCentroidsAndNodes = node.distanceBetweenCentroidAndNodesInCluster();
//				maxMindistanceIntraCluster = node.distanceIntraCluster();
//
//				for (List L : maxMindistanceBetweenCentroidsAndNodes) {
//					double max = (double) L.get(0);
//					for (int h = 0; h < gml.getDistances().length; h++) {
//						for (int j = 0; j < gml.getDistances().length; j++) {
//							if (max == gml.getDistances()[h][j]) {
//								for (GmlNode g : gml.getNodes()) {
//									if (g.getId() == h)
//										System.out.println("cidade a:" + g.getLabel());
//									if (g.getId() == j)
//										System.out.println("cidade b:" + g.getLabel());
//
//								}
//
//							}
//						}
//
//					}
//				}
//
//			}

			if (kmeans.getSilhouetteIndex(clustters) > maiorSilhouette && !clusterEmptSignal) {
				maiorSilhouette = kmeans.getSilhouetteIndex(clustters);
				NumeroDAExecucao = i;
				centroidsCopia = centroids;
			}

			

		}
System.out.println("########################################################################################");
		for (Pattern p : centroidsCopia) {
			System.out.println("centroid " + p.getName());

		}

		System.out.println("maior silhoette= " + maiorSilhouette);
		System.out.println("alcançado na execução= " + NumeroDAExecucao);
	}

}
