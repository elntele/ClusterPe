package br.clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import br.cns.model.GmlData;
import br.cns.model.GmlNode;
import br.cns.persistence.GmlDao;
import cbic15.Kmeans;
import cbic15.Pattern;

public class AlgClusterX {

	public static void main(String[] args) throws IOException {
		String patch = "C:/Users/jorge/workspace/ClusterPe/src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
		GmlData gml = new GmlDao().loadGmlData(patch); // novo
		List<Pattern> listPatterns = new ArrayList<>();
		List<GmlNode> listCity = gml.getNodes();// novo
		
		TableToList tableToList =new TableToList();
//		String patch2 ="C:/Users/jorge/Desktop/rural 2/2018.1/artigos/kmens e FCM/cities_clustering/algorithm_KMeans/centroids_k_4_exec_0.csv";
//		String patch3 ="C:/Users/jorge/Desktop/rural 2/2018.1/artigos/kmens e FCM/cities_clustering/algorithm_KMeans/clusters_k_4_exec_0.csv";
		
//		String patch1 ="C:/Users/jorge/workspace/ClusterPe/src/br/sourceClustered/centroids_k_2_exec_0.csv";
		//tableToList.retrievCentroid(patch2);
//		tableToList.retrievCluster(patch3);
		

//		/**
//		 * este parte será realocada mais a frente
//		 */
//		String line = null;
//	    BufferedReader stream = null;
//	    List<List<String>> csvData = new ArrayList<List<String>>();
//	    String patch1 ="C:/Users/jorge/workspace/ClusterPe/src/br/sourceClustered/centroids_k_2_exec_0.csv";
//	    try {
//	        stream = new BufferedReader(new FileReader(patch1)); // Podes usar BufferedReader ao invés do CSVReader 
//	        while ((line = stream.readLine()) != null) {
//	            String[] splitted = line.split(","); // Provavelmente teu separador é , ou ; depende do teu arquivo
//	            List<String> dataLine = new ArrayList<String>(splitted.length); // Note que assim ele irá pegar todas as colunas, independente de quantas
//	            for (String data : splitted)
//	                dataLine.add(data);
//	                csvData.add(dataLine);
//	        }
//	    } finally {
//	        if (stream != null)
//	            stream.close();
//	    }
//	    
//	    System.out.println("teste numero 1 "+csvData);
//	   //**************************************************************
	    
	    
	    
	    for (GmlNode c : listCity) {
			double[] variables = { c.getLatitude(), c.getLongitude() };
			Pattern pattern = new Pattern(c.getLabel(), variables, null);
			pattern.setId(c.getId());
			listPatterns.add(pattern);
		}
	    
//	    /**
//	     * fabricação de um cluster depois será removido
//	     * 
//	     */
//	    
//	    for (Pattern p: listPatterns){
//	    	System.out.println("teste2 :"+p.getName());
//	    }
//	    
//	    Pattern[] fakeCentroids=new Pattern[4];
//	    List<Pattern>[] fakeCluster = new List[4];
//	    for (int k=0;k<fakeCluster.length;k++){
//	    	fakeCluster[k] = new ArrayList<Pattern>(); 
//	    }
//	    Random gerator = new Random();
//	    for (Pattern p: listPatterns){
//	    	int i=gerator.nextInt(4);
//	    	fakeCluster[i].add(p);
//	    }
//	    fakeCentroids[0]=fakeCluster[0].get(0);
//	    fakeCentroids[1]=fakeCluster[1].get(0);
//	    fakeCentroids[2]=fakeCluster[2].get(0);
//	    fakeCentroids[3]=fakeCluster[3].get(0);
//	    //********************************************************************
	    
	    
	    
	    
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
		int kSizeMin = 4;
		int kSizeMax = 30;
		for (int i = kSizeMin; i <= kSizeMax; i++) {
			List<Pattern> listCentroids = new ArrayList<>();
			List<Double> resultSilhouetteAverage = new ArrayList();// lista pra pegar a média 
			List<Integer> listIteration = new ArrayList<>();
			int w = 0;
			double silhouetteAverage = 0;
			double DistanceAverange = 0;
			while (w < 30) {
				// array de listas do tipo pattern sendo preparado para ser
				// passado
				// (mais a frente) como parametro para o método
				// getSilhouetteIndex da classe Kmeans
		 		List<Pattern>[] clustters = new List[i];// descomentar depois
				
				/**
				 * neste parte tem que chamar a aplicação em python com i de 4 a 40 de acordo com o for externo
				 * ela tel que substituir as linas marcadas:
				 *  Kmeans kmeans = new Kmeans(i, listPatterns);
				 * 	clustters = kmeans.execute(200);
				 * 
				 */
				
				System.out.println("%%%%%%%%%%%%%%%%%%numero de clusters= " + i + " %%%%%%%%%%%%%%%%%%%");
				
				String patchCluster="C:/Users/jorge/Desktop/rural 2/2018.1/artigos/kmens e FCM/cities_clustering/algorithm_KMeans/clusters_k_"+i+"_exec_"+w+".csv";
				String patchCentroid="C:/Users/jorge/Desktop/rural 2/2018.1/artigos/kmens e FCM/cities_clustering/algorithm_KMeans/centroids_k_"+i+"_exec_"+w+".csv";;
				
				clustters = tableToList.retrievCluster(patchCluster).getCluster();
				
				
				Kmeans kmeans = new Kmeans(i, listPatterns);// subtituir este
				//clustters = kmeans.execute(200);// substituir este // descomentar depois
				// List<String> litleCluster = new ArrayList<>();
				if (w == 29) {
					// montando a lista com os nomes das cidades de cada cluster
					String stringCluster = "";
					for (int u = 0; u < clustters.length; u++) {// descomentar depois
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
					
					// comenta esse dois
					Pattern[] centroids =tableToList.retrievCentroid(patchCentroid).getCentroids();
					kmeans.setCentroids(centroids);
					/*Pattern[]*/ centroids = kmeans.getNearestPatternsFromCentroid();
					
					
					AllDistancesCLuster node = new AllDistancesCLuster(centroids, gml,clustters);

					maxMinAverangeDisntanceInterCentroids.add(node.distanceInterCentroids());
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

		SpreadSheet dataSheet = new SpreadSheet(globalResultSillhouetteAverange, listStringCluster, copyFinalclustters);
		dataSheet.createSpreedSheetMMAverangeDistanceBetweenCentroids(maxMinAverangeDisntanceInterCentroids, kSizeMin);
		dataSheet.createSpreedSheetMMAverangeDistanceBetweenCentroidAndNode(maxMindistanceBetweenCentroidsAndNodes);
		dataSheet.createSpreedSheetMMAverangeDistanceIntraCluster(maxMindistanceIntraCluster);
		dataSheet.createSpreedSheetItereation(mapIteration, kSizeMin, kSizeMax);
		dataSheet.createSpreadForMapGoogle();
	}
	    
	    
	    
	   
	}


