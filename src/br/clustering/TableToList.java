package br.clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cbic15.Pattern;

public class TableToList {

	public TableToList() {

	}
	
	public Pattern[] takeSelfObject(Pattern[] centroids,List<Pattern>[] cluster){
		Pattern[] copyCentroids=new Pattern[centroids.length];
		
		for (int i=0;i<centroids.length;i++){
			for (Pattern p:cluster[i]){
				if (p.getId()==centroids[i].getId()){
					copyCentroids[i]=p;
				}
				
			}
		}
//		for (int i=0;i<centroids.length;i++){
//			System.out.println("centroid"+copyCentroids[i].getName());
//		} 
		
		return copyCentroids;
	}
	
	
	/**
	 * este é o metodo que preenche o cluster
	 * @param csvData
	 * @return
	 */

	public ClusterCentroid listClusterToPatternList(List<List<String>> csvData) {
		List<Pattern> listPatterns = new ArrayList<>();
		int size = 0;
		int i=0;
//		System.out.println("array"+csvData); //é este print que tem que decomentar
		
		for (List s : csvData) {
			if (!s.get(0).equals("Brazil")) {
				size += 1;
			}
		}

		List<Pattern>[] cluster = new List[size];
		for (int k = 0; k < cluster.length; k++) {
			cluster[k] = new ArrayList<Pattern>();
		}
		int k = 0;
		Boolean begin = true;
		for (List s : csvData) {
			if (begin) {
				begin = false;
			} else {
				// se for colocar mais informações no pattern tem que ser aqui
				// descomente o printi acima pra se basear nas possições da lista
				if (s.get(0).equals("Brazil")) {
					double[] variables = { Double.parseDouble((String) s.get(3)),
							Double.parseDouble((String) s.get(4)) };
					Pattern pattern = new Pattern(s.get(2).toString().trim()+","+" "+"PE", variables, null);
					double X=Double.parseDouble((String) s.get(9));
					pattern.setId((int)X);
					listPatterns.add(pattern);
				}else{
					double z=Double.parseDouble((String) s.get(0));
					i=(int)z;
					cluster[k].addAll(listPatterns);
					listPatterns.removeAll(listPatterns);
					k+=1;
				}
			}
		}
		cluster[k].addAll(listPatterns);
		
//		for (int i=0;i<cluster.length;i++) {
//			for (Pattern p:cluster[i]){
//			System.out.println(p.getName());
//			System.out.println(p.getVariables()[0]);
//			System.out.println(p.getVariables()[1]);
//			}
//		}
		ClusterCentroid clusterCentroid=new ClusterCentroid();
		clusterCentroid.setCluster(cluster);
		clusterCentroid.setInteration(i);
		return clusterCentroid;
	}

	/**
	 * método que preenche o centroid
	 * 
	 * @param csvData
	 * @return
	 */
	public ClusterCentroid listCentroidToPatternList(List<List<String>> csvData) {
		List<Pattern> listPatterns = new ArrayList<>();
		int t = 0;
		for (List s : csvData) {
			if (t != 0) {
				double[] variables = { Double.parseDouble((String) s.get(1)), Double.parseDouble((String) s.get(2)) };
				Pattern pattern = new Pattern(null, variables, null);
				pattern.setId(0);
				listPatterns.add(pattern);
			}
			t += 1;
		}
//		for (Pattern p : listPatterns) {
//			System.out.println(p.getName());
//			System.out.println(p.getVariables()[0]);
//			System.out.println(p.getVariables()[1]);
//		}
		 Pattern[] centroids=new Pattern[listPatterns.size()];
		 int i=0;
		 for (Pattern p: listPatterns){
			 centroids[i]=p;
			 i+=1;
		 }
		 ClusterCentroid clusterCentroid=new ClusterCentroid();
		 clusterCentroid.setCentroids(centroids);

		return clusterCentroid;
	}

	public ClusterCentroid takepatternList(String patch, int id) throws IOException {

		String line = null;
		BufferedReader stream = null;
		List<List<String>> csvData = new ArrayList<List<String>>();
		try {
			// Podes usar BufferedReader ao invés do CSVReader
			stream = new BufferedReader(new FileReader(patch));
			while ((line = stream.readLine()) != null) {
				// Provavelmente teu separador é , ou ; depende do teu arquivo
				String[] splitted = line.split(",");
				// Note que assim ele irá pegar todas as colunas, independente
				// de quantas
				List<String> dataLine = new ArrayList<String>(splitted.length);
				for (String data : splitted)
					dataLine.add(data);
				csvData.add(dataLine);
			}
		} finally {
			if (stream != null)
				stream.close();
		}
//		System.out.println("aqui"+csvData);

		if (id == 1) {

			return listCentroidToPatternList(csvData);
		} else {
			return listClusterToPatternList(csvData);
		}
	}

	public ClusterCentroid retrievCentroid(String patch) throws IOException {
		return takepatternList(patch, 1);
	}

	public ClusterCentroid retrievCluster(String patch) throws IOException {

		return takepatternList(patch, 2);
	}
}
