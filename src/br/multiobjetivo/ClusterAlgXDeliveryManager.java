package br.multiobjetivo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.clustering.AllDistancesCLuster;
import br.clustering.ClusterCentroid;
import br.clustering.ClusterEmptData;
import br.clustering.MetricsIntraCluster;
import br.clustering.SpreadSheet;
import br.clustering.TableToList;
import br.cns.model.GmlData;
import br.cns.model.GmlNode;
import br.cns.persistence.GmlDao;
import cbic15.Kmeans;
import cbic15.Pattern;

public class ClusterAlgXDeliveryManager {

	public static void main(String[] args) throws IOException {
		String patch = "src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
	//	String patch = "C:/Users/jorge/workspace/ClusterPe/src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
		GmlData gml = new GmlDao().loadGmlData(patch); // novo
		List<Pattern> listPatterns = new ArrayList<>();
		List<GmlNode> listCity = gml.getNodes();// novo
		TableToList tableToList = new TableToList();

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
		 * loop executa o kmeans com k de 4 a 20, ou seja com 4 a 20 clusters ha
		 * uma lista de centroids que esta sendo montada, impressa e
		 * reinicializada a cada k do loop até o ultimo k onde ela não é mais
		 * reinicializada. no final de cada iteração ha um impressão do Index
		 * Silhouette.
		 * 
		 */
		int kSize = 15; // numero de cluster
		int execucao = 16;// uma das execuções de clodomir, vai de 1 a 30,
							// chutei a 10

		MetricsIntraCluster metrics = new MetricsIntraCluster();
		// array de listas do tipo pattern sendo preparado para ser
		// passado
		// (mais a frente) como parametro para o método
		// getSilhouetteIndex da classe Kmeans
		List<Pattern>[] clustters = new List[kSize];// descomentar depois

		/**
		 * neste parte tem que chamar as tabelas, resultados da a aplicação em
		 * python com kSize setado acima e na execução tabem setada acima
		 * lembando que este código é adaptado a qq execução, e qq alg. não
		 * precisa ser o kmeans, porém, o tratamento de cluster vazios, que
		 * apareceram com frequência no PSC, não foi considerando aqui, já que
		 * isso quebraria a busca local na etapa evolucionária, então esse
		 * código requer resultado, do alg. de cluster, com clusteres não vazios.
		 * sugestão para futura busca de cluster não vazio, varre as pastas de 
		 * tabelas até encontrar uma clusterização válida, se olhar na primeira 
		 * parte do projeto verá que já tem método que observa se existe cluster vario.
		 * se for usar o PSC, é bom impplementar esta varredura.
		 */
		
		String [] alg={"algorithm_PSC","algorithm_KMeans","algorithm_FCMeans"};
		String algselected =alg[2];
		System.out.println("algoritmo selecionado: " +algselected);
		
		String patchCluster = "src/"+algselected+"/clusters_k_" + kSize + "_exec_" + execucao + ".csv";
		String patchCentroid = "src/"+algselected+"/centroids_k_" + kSize + "_exec_" + execucao + ".csv";

		ClusterCentroid clusterCentroidCluster = new ClusterCentroid();
		clusterCentroidCluster = tableToList.retrievCluster(patchCluster);
		clustters = clusterCentroidCluster.getCluster();
		Kmeans kmeans = new Kmeans(kSize, listPatterns);
		Pattern[] centroids;
		metrics.setStringCluster(clustters);
		listStringCluster.add(metrics.getStringCluster());
		centroids = metrics.monteCentroids(patchCentroid, tableToList, kmeans, clustters);
		metrics.setCluster(clustters);
		centroids = metrics.getCentroids();
		kmeans.setCentroids(centroids);// este passo é novo, para os centroids
										// serem os que retornam das tabelas
		AllDistancesCLuster node = new AllDistancesCLuster(centroids, gml, clustters);
		maxMinAverangeDisntanceInterCentroids.add(node.distanceInterCentroids());
		for (int i=0;i<clustters.length;i++){
			System.out.println("tamanho dos clusters "+ clustters[i].size());
			
		}
		System.out.println();
		MultiObjectivesWay multi = new MultiObjectivesWay(kmeans, gml, clustters);
		// ate aqui

	}

}
