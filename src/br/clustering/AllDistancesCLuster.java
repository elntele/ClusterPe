package br.clustering;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.ExcelGeneralNumberFormat;
import org.apache.poi.ss.usermodel.ExcelNumberFormat;

import br.cns.model.GmlData;
import br.cns.model.GmlNode;
import br.cns.persistence.SimonSolutionDao;
import cbic15.Pattern;

public class AllDistancesCLuster {
	private Pattern[] centroids;
	private GmlData gml;
	private List<Pattern>[] clustters;

	public AllDistancesCLuster(Pattern[] centroids, GmlData gml, List<Pattern>[] clus) {
		super();
		this.centroids = centroids;
		this.gml = gml;
		this.clustters = clus;
	}

	/**
	 * calcula distâncias dos nós dentro de cada cluster
	 * 
	 * @return o máximo valor, o mínimo e a méda de cada cluster em uma lista de
	 *         lista Obs: deve ser usado no final do loop principal quando o
	 *         Cluster já estiver todo montado
	 */

	public List<List<Double>> distanceIntraCluster() {
		List<List<Double>> distanceIntraCluster = new ArrayList();
		for (int i = 0; i < clustters.length; i++) {
			Double MaxDistance = Double.MIN_VALUE;
			Double MinDistance = Double.MAX_VALUE;
			double currentDistance = 0;
			double AverangeDistance = 0;
			int countDistance = 0;
			List<Double> distanceIntra = new ArrayList<>();
			List<Pattern> copy = new ArrayList();
			copy.addAll(clustters[i]);
			for (Pattern p : clustters[i]) {
				copy.remove(p);
				for (Pattern pa : copy) {
					currentDistance = gml.getDistances()[p.getId()][pa.getId()];
					if (currentDistance < MinDistance)
						MinDistance = currentDistance;
					if (currentDistance > MaxDistance)
						MaxDistance = currentDistance;
					AverangeDistance += currentDistance;
					countDistance += 1;
				}

			}

			if (countDistance != 0) {
				distanceIntra.add(MaxDistance);
				distanceIntra.add(MinDistance);
				distanceIntra.add(AverangeDistance / countDistance);
				distanceIntraCluster.add(distanceIntra);
			} else {
				distanceIntra.add(0.0);
				distanceIntra.add(0.0);
				distanceIntra.add(0.0);
				distanceIntraCluster.add(distanceIntra);
			}

		}

		return distanceIntraCluster;
	}

	/**
	 * clacula distancia entre os centroids
	 * 
	 * @return uma lista de lista com max, minima e media distancia de cada k
	 *         grupo de centroid... Deve ser usada por iteração uma vez por k.
	 *         Apesar de esta em uso e funcionando o método foi feito quando
	 *         pattern não tinha id, por isso ele pode ser melhorado depois que
	 *         pattern passou a ter id, um dia eu concerto...
	 */

	public List<Double> distanceInterCentroids() {
		GmlNode Arraynode[] = new GmlNode[centroids.length];
		for (int j = 0; j < centroids.length; j++) {
			for (GmlNode g : gml.getNodes()) {
				
				try {
					if (/*centroids[j].getName().equals(g.getLabel())*/ centroids[j].getId()==g.getId()) {
						Arraynode[j] = g;
					}
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
		}

		double CurrentDistance = 0;
		double averageDistance = 0;
		int distanceCount = 0;
		double minDistance = Double.MAX_VALUE;
		double maxDistance = Double.MIN_VALUE;
		List<Double> maxMinAverangeDisntance = new ArrayList<>();
		for (int j = 0; j < Arraynode.length; j++) {
			for (int g = j; g < Arraynode.length; g++) {
				if (j != g) {
					CurrentDistance = gml.getDistances()[Arraynode[j].getId()][Arraynode[g].getId()];
					if (CurrentDistance > maxDistance)
						maxDistance = CurrentDistance;
					if (CurrentDistance < minDistance)
						minDistance = CurrentDistance;
					averageDistance += CurrentDistance;
					distanceCount += 1;
					if (Arraynode[j].getId()==143 && Arraynode[g].getId()==170) {
						System.out.println(Arraynode[j].getLabel());
						System.out.println(Arraynode[g].getLabel());
					}
					
				}
			}
		}
		maxMinAverangeDisntance.add(maxDistance);
		maxMinAverangeDisntance.add(minDistance);
		maxMinAverangeDisntance.add(averageDistance / distanceCount);
		return maxMinAverangeDisntance;
	}
	
	public void praClodomir(){
		
	}
	

	public List<List<Double>>  distanceBetweenCentroidAndNodesInCluster() {

		List<List<Double>> maxMinAverangeDisntance = new ArrayList<>();
		for (int i = 0; i < centroids.length; i++) {
			double CurrentDistance = 0;
			double averageDistance = 0;
			int distanceCount = 0;
			double minDistance = Double.MAX_VALUE;
			double maxDistance = Double.MIN_VALUE;
			List<Double> distanceCentroidNode = new ArrayList<>();
			for (int w = 0; w < clustters.length; w++) {
				if (clustters[w].contains(centroids[i])) {
					for (Pattern p : clustters[i]) {
						if (centroids[i].getId() != p.getId()) {
							CurrentDistance = gml.getDistances()[centroids[i].getId()][p.getId()];
							if (CurrentDistance > maxDistance)
								maxDistance = CurrentDistance;
							if (CurrentDistance < minDistance)
								minDistance = CurrentDistance;
							averageDistance += CurrentDistance;
							distanceCount += 1;
						} else if((centroids[i].getId() == p.getId())&&(clustters[i].size()==1)){
							maxDistance=0;
							minDistance=0;
							distanceCount=1;
						}
					}
				}
			}
			distanceCentroidNode.add(maxDistance);
			distanceCentroidNode.add(minDistance);
			distanceCentroidNode.add(averageDistance / distanceCount);
			maxMinAverangeDisntance.add(distanceCentroidNode);
		}
		return maxMinAverangeDisntance;
	}

}
