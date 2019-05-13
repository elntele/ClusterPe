package br.multiobjetivo;

import java.util.ArrayList;
import java.util.List;

import br.clustering.AllDistancesCLuster;
import br.cns.model.GmlData;
import br.cns.model.GmlNode;
import br.cns.persistence.GmlDao;
import cbic15.Kmeans;
import cbic15.Pattern;

public class ClusterDeliveryManager {

	public static void main(String[] args) {
		String patch = "C:/Users/jorge/workspace/ClusterPe/src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
		List<Pattern> listPatterns = new ArrayList<>();
		GmlData gml = new GmlDao().loadGmlData(patch); // novo
		List<GmlNode> listCity = gml.getNodes();// novo

		/**
		 * lista de cidades sendo converdida em lista de Pattern
		 */
		for (GmlNode c : listCity) {
			double[] variables = { c.getLatitude(), c.getLongitude() };
			Pattern pattern = new Pattern(c.getLabel(), variables, null);
			pattern.setId(c.getId());
			listPatterns.add(pattern);
		}

		gml.createComplexNetwork();
		List<String> listStringCluster = new ArrayList<>();


		int Kcluster = 15;// número de clusters
		List<Pattern>[] clustters = new List[Kcluster];

		Kmeans kmeans = new Kmeans(Kcluster, listPatterns);
		clustters = kmeans.execute(200);
		
		
		// List<String> litleCluster = new ArrayList<>();

		// montando a lista com os nomes das cidades de cada cluster
		String stringCluster = "";
		for (int u = 0; u < clustters.length; u++) {
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
		Pattern[] centroids = kmeans.getNearestPatternsFromCentroid();
		AllDistancesCLuster node = new AllDistancesCLuster(centroids, gml, clustters);

//		MultiObjectivesWay multi =new MultiObjectivesWay(kmeans, gml,clustters);

	}
}
