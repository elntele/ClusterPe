package br.clustering;

import java.util.ArrayList;
import java.util.List;

import br.cns.model.GmlData;
import cbic15.Pattern;

/**
 * classe para calcular o �ndice de Dunn que � menor dist�ncia entre dois centr�ides dividido
 * pelo maior di�metro dentre os clusteres, ou seja, o cluster de maior tamanho:
 * maior di�metro, intuitivamente � a maior distancia entre duas cidades
 * mas tem mais de um jeito, no artigo de alan foi a m�dia das dist�ncias
 * entre o centroid e todos os membros do cluster.  
 * Essa classe ira usar a classe AllDistanceCluster que j� tem muita coisa 
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
	 * Esse dunn � baseado na m�dia das dist�ncias entre todos os pontos 
	 * como tamanho do cluster e na dist�ncia entre os centr�ides como dist�ncia
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
	 * Esse dunn � baseado na maior das dist�ncias entre todos os pontos 
	 * como tamanho do cluster e na dist�ncia entre os centr�ides como dist�ncia
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
	 * Esse dunn � baseado na maior das dist�ncias entre o centroide e os pontos 
	 * como tamanho do cluster e na dist�ncia entre os centr�ides como dist�ncia
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
