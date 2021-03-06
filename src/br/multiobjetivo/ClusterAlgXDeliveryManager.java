package br.multiobjetivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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
		//**********planos futuros******************
//		criar o primeiro dados.properties dentro 
//		de qualquera area de trabalho independente do
//		cominho:
//		FileSystemView system = FileSystemView.getFileSystemView();
//		System.out.println(system.getHomeDirectory().getPath());
//		isso retona o caminha da area de trabalho
		//************************************
		Properties prop = new Properties();
		FileInputStream file = new FileInputStream("src/dados.properties");
		prop.load(file);
		//carregando o gml da rede
		String path = prop.getProperty("path");

		GmlData gml = new GmlDao().loadGmlData(path); // novo
		List<Pattern> listPatterns = new ArrayList<>();
		List<GmlNode> listCity = gml.getNodes();// novo
		String[] countryNameInGml = { "Brazil", "Germany" };
		int countrySelector=Integer.parseInt(prop.getProperty("country"));
		TableToList tableToList = new TableToList(countryNameInGml[countrySelector]);

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
		 * reinicializada a cada k do loop at� o ultimo k onde ela n�o � mais
		 * reinicializada. no final de cada itera��o ha um impress�o do Index
		 * Silhouette.
		 * 
		 */
//		int kSize = 15; // numero de cluster
//		int execucao = 9;// uma das execu��es de clodomir, vai de 0 a 29,
//							// chutei a 10
		
		int kSize = Integer.parseInt(prop.getProperty("kSize")); // numero de cluster
		int execucao =Integer.parseInt(prop.getProperty("execX"));// uma das execu��es de clodomir, vai de 0 a 29,
							// chutei a 10

		MetricsIntraCluster metrics = new MetricsIntraCluster();
		// array de listas do tipo pattern sendo preparado para ser
		// passado
		// (mais a frente) como parametro para o m�todo
		// getSilhouetteIndex da classe Kmeans
		List<Pattern>[] clustters = new List[kSize];// descomentar depois

		/**
		 * neste parte tem que chamar as tabelas, resultados da a aplica��o em
		 * python com kSize setado acima e na execu��o tabem setada acima
		 * lembando que este c�digo � adaptado a qq execu��o, e qq alg. n�o
		 * precisa ser o kmeans, por�m, o tratamento de cluster vazios, que
		 * apareceram com frequ�ncia no PSC, n�o foi considerando aqui, j� que
		 * isso quebraria a busca local na etapa evolucion�ria, ent�o esse
		 * c�digo requer resultado, do alg. de cluster, com clusteres n�o
		 * vazios. sugest�o para futura busca de cluster n�o vazio, varre as
		 * pastas de tabelas at� encontrar uma clusteriza��o v�lida, se olhar na
		 * primeira parte do projeto ver� que j� tem m�todo que observa se
		 * existe cluster vario. se for usar o PSC, � bom impplementar esta
		 * varredura.
		 */
		String[] alg = { "algorithm_PSC", "algorithm_KMeans", "algorithm_FCMeans","algorithm_KMeans_medianet"};
		int algNumber = Integer.parseInt(prop.getProperty("alg"));
		String algselected = alg[algNumber];
		prop.setProperty("algName", algselected);
		new File (prop.getProperty("local")+prop.getProperty("algName")+"/"+prop.getProperty("modo")).mkdirs();

		for (int i = 1; i <= Integer.parseInt(prop.getProperty("numeroDeExec")); i++) {
			
			System.out.println("algoritmo selecionado: " + algselected);
			prop.setProperty("alg", alg[algNumber]);

			String patchCluster = "src/" + algselected + "/clusters_k_" + kSize + "_exec_" + execucao + ".csv";
			String patchCentroid = "src/" + algselected + "/centroids_k_" + kSize + "_exec_" + execucao + ".csv";

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
			kmeans.setCentroids(centroids);// este passo � novo, para os
											// centroids
											// serem os que retornam das tabelas
			AllDistancesCLuster node = new AllDistancesCLuster(centroids, gml, clustters);
			maxMinAverangeDisntanceInterCentroids.add(node.distanceInterCentroids());
			for (int l = 0; l < clustters.length; l++) {
				System.out.println("tamanho dos clusters " + clustters[l].size());

			}
			
			prop.setProperty("execucao", "execu��o "+i);
			new File (prop.getProperty("local")+prop.getProperty("algName")
			+"/"+prop.getProperty("modo")+"/"+prop.getProperty("execucao")).mkdir();

			MultiObjectivesWay multi = new MultiObjectivesWay(kmeans, gml, clustters, prop);
			// ate aqui

		}
	}

}
