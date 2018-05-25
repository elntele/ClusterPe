package br.clustering;

import java.io.IOException;
import java.util.List;

import cbic15.Kmeans;
import cbic15.Pattern;

public class MetricsIntraCluster {

	private String stringCluster;
	private String pathCentroids;
	private List<Pattern>[] cluster;
	Pattern[] centroids;
	
	public MetricsIntraCluster() {
		
	}

	public String getStringCluster() {
		return stringCluster;
	}

	public void setStringCluster(List<Pattern>[] cluster) {
		String stringCluster = "";
		for (int u = 0; u < cluster.length; u++) {// descomentar depois
			stringCluster += "{";
			for (Pattern p : cluster[u]) {
				stringCluster += p.getName();
				stringCluster += ", ";
			}
			stringCluster += "} ";
		}

		this.stringCluster = stringCluster;
	}
	
	public Pattern[] monteCentroids(String patchCentroid,TableToList tableToList,Kmeans kmeans,
			List<Pattern>[] clustters) throws IOException{
		ClusterCentroid clusterCentroidCentroid=new ClusterCentroid();
		clusterCentroidCentroid=tableToList.retrievCentroid(patchCentroid);
		Pattern[] centroids =clusterCentroidCentroid.getCentroids();
		kmeans.setCentroids(centroids);
		centroids = kmeans.getNearestPatternsFromCentroid(clustters,centroids);
		centroids=tableToList.takeSelfObject(centroids, clustters);
		this.centroids=centroids;
		return centroids;
	}

	public List<Pattern>[] getCluster() {
		return cluster;
	}

	public void setCluster(List<Pattern>[] cluster) {
		this.cluster = cluster;
	}

	public Pattern[] getCentroids() {
		return centroids;
	}
	
	
	
	
	
	

}
