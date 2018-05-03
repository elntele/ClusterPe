package br.clustering;

import java.util.List;

import cbic15.Pattern;

public class ClusterCentroid {
	private List<Pattern>[] cluster;
	private Pattern[] centroids;
	private int interation;
	
	public ClusterCentroid() {
		super();
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
	public void setCentroids(Pattern[] centroids) {
		this.centroids = centroids;
	}

	public int getInteration() {
		return interation;
	}

	public void setInteration(int interation) {
		this.interation = interation;
	}
	

}
