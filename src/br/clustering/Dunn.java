package br.clustering;

import java.util.ArrayList;
import java.util.List;

import br.cns.model.GmlData;
import cbic15.Pattern;

/**
 * classe para calcular o índice de Dunn que é menor distância entre dois centróides dividido
 * pelo maior diâmetro dentre os clusteres, ou seja, o cluster de maior tamanho:
 * maior diâmetro, intuitivamente é a maior distancia entre duas cidades
 * mas tem mais de um jeito, no artigo de alan foi a média das distâncias
 * entre o centroid e todos os membros do cluster.  
 * Essa classe ira usar a classe AllDistanceCluster que já tem muita coisa 
 * implementada.
 * @author elnte
 *
 */

public class Dunn {
	private AllDistancesCLuster node;

	public Dunn(Pattern[] centroids, GmlData gml, List<Pattern>[] clus) {
		AllDistancesCLuster alldistance=new AllDistancesCLuster(centroids, gml, clus);
		this.node=alldistance;
	}
	
	/**
	 * Esse dunn é baseado na média das distâncias entre todos os pontos 
	 * como tamanho do cluster e na distância entre os centróides como distância
	 * entre cluster
	 * 
	 * @return
	 */
	
	public double dunnAverage() {
		List<List<Double>> clusterdistances = this.node.distanceIntraCluster();
		List<Double> centroidDistances =this.node.distanceInterCentroids();
		double minDistanceBethwenCentroids=Double.MAX_VALUE;
		double maxClusterSize=Double.MIN_VALUE;
		
		for (List l:clusterdistances) {
			if ( (double)l.get(2)>maxClusterSize) {
				maxClusterSize=(double) l.get(2);
				System.out.println();
			}
		}
		
		for (Double d:centroidDistances) {
			if ( d< minDistanceBethwenCentroids) {
				minDistanceBethwenCentroids=d;
			}
		}
		Double dunn=minDistanceBethwenCentroids/maxClusterSize;
		
		System.out.println(dunn);
		return dunn;
	}
	
	/**
	 * Esse dunn é baseado na maior das distâncias entre todos os pontos 
	 * como tamanho do cluster e na distância entre os centróides como distância
	 * entre cluster
	 * 
	 * @return
	 */
	
	public double dunnBiggerBetwennTwoPoints() {
		List<List<Double>> clusterdistances = this.node.distanceIntraCluster();
		List<Double> centroidDistances =this.node.distanceInterCentroids();
		double minDistanceBethwenCentroids=Double.MAX_VALUE;
		double maxClusterSize=Double.MIN_VALUE;
		
		for (List l:clusterdistances) {
			if ( (double)l.get(0)>maxClusterSize) {
				maxClusterSize=(double) l.get(1);
				System.out.println();
			}
		}
		
		for (Double d:centroidDistances) {
			if ( d< minDistanceBethwenCentroids) {
				minDistanceBethwenCentroids=d;
			}
		}
		Double dunn=minDistanceBethwenCentroids/maxClusterSize;
		
		System.out.println(dunn);
		return dunn;
	}
	
	/**
	 * Esse dunn é baseado na maior das distâncias entre o centroide e os pontos 
	 * como tamanho do cluster e na distância entre os centróides como distância
	 * entre cluster
	 * 
	 * @return
	 */
	
	public double dunnAverageBetwennCentroidAndPoints() {
		List<List<Double>> clusterdistances = this.node.distanceBetweenCentroidAndNodesInCluster();
		List<Double> centroidDistances = this.node.distanceInterCentroids();
		double minDistanceBethwenCentroids=Double.MAX_VALUE;
		double maxClusterSize=Double.MIN_VALUE;
		
		for (List l:clusterdistances) {
			if ( (double)l.get(0)>maxClusterSize) {
				maxClusterSize=(double) l.get(2);
				System.out.println();
			}
		}
		
		for (Double d:centroidDistances) {
			if ( d< minDistanceBethwenCentroids) {
				minDistanceBethwenCentroids=d;
			}
		}
		Double dunn=minDistanceBethwenCentroids/maxClusterSize;
		
		System.out.println(dunn);
		return dunn;
	}
	

}
