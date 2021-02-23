package br.multiobjetivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.filechooser.FileSystemView;

import br.clustering.AllDistancesCLuster;
import br.clustering.ClusterCentroid;
import br.clustering.MetricsIntraCluster;
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
		boolean thisWasStartedBefore=false;
		Properties prop = new Properties();
		FileInputStream file=null;
		try {
			file = new FileInputStream("src/dados.properties");
		} catch (Exception e) {
			FileSystemView system = FileSystemView.getFileSystemView();
			file=new FileInputStream(system.getHomeDirectory().getPath()+"/redeAleatoria1/dados.properties");
		}
		
		prop.load(file);
		//carregando o gml da rede
		GmlData gml=null;
		String path = prop.getProperty("path");
		try {
			
			gml = new GmlDao().loadGmlData(path); // novo
			
		} catch (Exception e) {
			path = path.replace("src", "/redeAleatoria1/src/");
			FileSystemView system = FileSystemView.getFileSystemView();
			path=system.getHomeDirectory().getPath()+path;
			 gml = new GmlDao().loadGmlData(path);
		}
		
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
		 * reinicializada a cada k do loop até o ultimo k onde ela não é mais
		 * reinicializada. no final de cada iteração ha um impressão do Index
		 * Silhouette.
		 * 
		 */
//		int kSize = 15; // numero de cluster
//		int execucao = 9;// uma das execuções de clodomir, vai de 0 a 29,
//							// chutei a 10
		
		int kSize = Integer.parseInt(prop.getProperty("kSize")); // numero de cluster
		int execucao =Integer.parseInt(prop.getProperty("execX"));// uma das execuções de clodomir, vai de 0 a 29,
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
		 * código requer resultado, do alg. de cluster, com clusteres não
		 * vazios. sugestão para futura busca de cluster não vazio, varre as
		 * pastas de tabelas até encontrar uma clusterização válida, se olhar na
		 * primeira parte do projeto verá que já tem método que observa se
		 * existe cluster vario. se for usar o PSC, é bom impplementar esta
		 * varredura.
		 */
		String[] alg = { "algorithm_PSC", "algorithm_KMeans", "algorithm_FCMeans","algorithm_KMeans_medianet"};
		int algNumber = Integer.parseInt(prop.getProperty("alg"));
		String algselected = alg[algNumber];
		prop.setProperty("algName", algselected);
		int start=0;
		try {
			new File (prop.getProperty("local")+prop.getProperty("algName")+"/"+prop.getProperty("modo")).mkdirs();
		
		} catch (Exception e) {
			
			FileSystemView system = FileSystemView.getFileSystemView();
			String local=system.getHomeDirectory().getPath()+"\\redeAleatoria1\\resultados\\";
			prop.setProperty("local", local);
			new File (prop.getProperty("local")+prop.getProperty("algName")+"\\"+prop.getProperty("modo")).mkdirs();
		}
		
		
		if (prop.getProperty("startFromAstopedIteration").equals("y")) {
			 start=Integer.parseInt(prop.getProperty("executionStoped"));
			 if (thisWasStartedBefore) {
				 prop.setProperty("startFromAstopedIteration", "n");
			 }
			 thisWasStartedBefore=true;
					
		}else {
			 start=1;
		}
		
		

		for (int i = start; i <= Integer.parseInt(prop.getProperty("numeroDeExec")); i++) {
			
			System.out.println("algoritmo selecionado: " + algselected);
			prop.setProperty("alg", alg[algNumber]);

			String patchCluster = "src/" + algselected + "/clusters_k_" + kSize + "_exec_" + execucao + ".csv";
			String patchCentroid = "src/" + algselected + "/centroids_k_" + kSize + "_exec_" + execucao + ".csv";

			ClusterCentroid clusterCentroidCluster = new ClusterCentroid();
			
			try {
				clusterCentroidCluster = tableToList.retrievCluster(patchCluster);
			} catch (Exception e) {
				patchCluster=prop.getProperty("local")+"src/" + algselected + "/clusters_k_" + kSize + "_exec_" + execucao + ".csv";
				patchCentroid=prop.getProperty("local")+"src/" + algselected + "/centroids_k_" + kSize + "_exec_" + execucao + ".csv";
				patchCluster = patchCluster.replace("\\resultados", "");
				patchCentroid = patchCentroid.replace("\\resultados", "");
				clusterCentroidCluster = tableToList.retrievCluster(patchCluster);
			}		
			clustters = clusterCentroidCluster.getCluster();
			Kmeans kmeans = new Kmeans(kSize, listPatterns);
			Pattern[] centroids;
			metrics.setStringCluster(clustters);
			listStringCluster.add(metrics.getStringCluster());
			centroids = metrics.monteCentroids(patchCentroid, tableToList, kmeans, clustters);
			metrics.setCluster(clustters);
			centroids = metrics.getCentroids();
			kmeans.setCentroids(centroids);// este passo é novo, para os
											// centroids
											// serem os que retornam das tabelas
			AllDistancesCLuster node = new AllDistancesCLuster(centroids, gml, clustters);
			maxMinAverangeDisntanceInterCentroids.add(node.distanceInterCentroids());
			for (int l = 0; l < clustters.length; l++) {
				System.out.println("tamanho dos clusters " + clustters[l].size());

			}
			
			prop.setProperty("execucao", "execução "+i);
			new File (prop.getProperty("local")+prop.getProperty("algName")
			+"/"+prop.getProperty("modo")+"/"+prop.getProperty("execucao")).mkdir();

			MultiObjectivesWay multi = new MultiObjectivesWay(kmeans, gml, clustters, prop);
			// ate aqui

		}
	}

}
