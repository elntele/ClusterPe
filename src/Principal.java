import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import cbic15.Kmeans;
import cbic15.Pattern;

public class Principal {

	public static void main(String[] args) {
		String patch = "C:/Users/jorge/workspace/CluesterPe/src/MunicipiosDePernambucoTec.RedesFinalizado.gml";
		BigListCity bigListCity = new BigListCity(patch);
		List<City> listCity = bigListCity.getBigListCity();
		List<Pattern> listPatterns = new ArrayList<>();
		List<Integer> listIteration = new ArrayList<>();

		/**
		 * lista de cidades sendo converdida em lista de Pattern
		 */
		for (City c : listCity) {
			double[] variables = { c.getLatitude(), c.getLongitude() };
			Pattern pattern = new Pattern(c.getName(), variables, null);
			listPatterns.add(pattern);
		}

		List<List<Double>> listResult = new ArrayList();

		/**
		 * loop executa o kmeans com k de 4 a 20, ou seja com 4 a 20 clusters ha
		 * uma lista de centroids que esta sendo montada, impressa e
		 * reinicializada a cada k do loop até o ultimo k onde ela não é mais
		 * reinicializada. no final de cada iteração ha um impressão do Index
		 * Silhouette.
		 * 
		 */
		for (int i = 4; i <= 20; i++) {
			List<Pattern> listCentroids = new ArrayList<>();
			List<Double> result = new ArrayList();

			int w = 0;
			double average = 0;
			while (w < 30) {
				// array de listas do tipo pattern sendo preparado para ser
				// passado
				// (mais a frente) como parametro para o método
				// getSilhouetteIndex da classe Kmeans
				List<Pattern>[] clustters = new List[i];

				System.out.println("%%%%%%%%%%%%%%%%%%numero de clusters= " + i + " %%%%%%%%%%%%%%%%%%%");

				Kmeans kmeans = new Kmeans(i, listPatterns);
				clustters = kmeans.execute(200);

				// for (int u = 0; u < clustters.length; u++) {
				// System.out.println("######################cluster " + (u + 1)
				// + " #########################");
				// for (Pattern p : clustters[u]) {
				// System.out.println(p.getName());
				// }
				// }

				// for (int j = 0; j <
				// kmeans.getNearestPatternsFromCentroid().length; j++) {
				// System.out.println(
				// "centroid do cluster" + (j + 1) +
				// kmeans.getNearestPatternsFromCentroid()[j].getName());
				// }
				average += kmeans.getSilhouetteIndex(clustters);
				w += 1;
				listIteration.addAll(kmeans.getLisItaration());
			}

			result.add(average / w);
			result.add((double) i);
			listResult.add(result);
		}

		for (List L : listResult) {
			System.out.println(L.get(0));
			System.out.println(L.get(1));

		}
		
		
		listIteration.sort(null);
		System.out.println(listIteration);
		
		SpreadSheet dataSheet = new SpreadSheet(listResult);

	}
}
