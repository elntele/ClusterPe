package br.multiobjetivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.comparator.DominanceComparator;

import br.bons.core.OpticalNetworkProblem;
//import br.clustering.LabeledIntegerSolution;
import br.cns.model.GmlData;
import br.cns.persistence.GmlDao;
import cbic15.Kmeans;
import cbic15.Pattern;

public class SearchForNetworkAndEvaluate extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private int lowerBound = 0;
	private int upperBound;
	private Kmeans kmeans;
	private GmlData gml;
	private Pattern[] lineColumn;
	private PatternToGml ptg;
	private GmlDao dao;
	private List<Pattern>[] clustters;
	private Pattern[] centroids;
	private OpticalNetworkProblem opticalNetwoark;
	private int contCreate = 0;
	private int contEvaluate = 0;
	IntegerSolution anterior;
	
	
	public void testCoparacao (IntegerSolution s1, IntegerSolution s2 ){
		DominanceComparator comparater =new DominanceComparator();
		int i=comparater.compare(s1, s2);
		if (i ==-1){
			System.out.println("s1 domina s2");
		}
		if (i==0){
			System.out.println("ambras são nao dominadas");
		}
		if (i==1){
			System.out.println("s2 domina s1");
		}
		
	}

	@Override
	public IntegerSolution createSolution() {

		 IntegerSolution retorno = new DefaultIntegerSolution(this);
		//IntegerSolution retorno = new LabeledIntegerSolution(this);
		Random gerador = new Random();
		List<Integer> pint = new ArrayList<>();

		// for (int i = 0; i < getNumberOfVariables(); i++) {
		// retorno.setVariableValue(i, 0);
		//// retorno.setVariableValue(i, gerador.nextInt(2));
		// }

		for (int i = 0; i < getNumberOfVariables(); i++) {
			pint.add(retorno.getVariableValue(i));
		}
		System.out.println("não vem zerada" + pint);

		if (this.contCreate == 458) {
			System.out.println("segunda vez");
			this.contCreate += 1;
		}
		System.out.println(this.contCreate);
		this.contCreate += 1;
		retorno.setLineColumn(lineColumn);
		//((LabeledIntegerSolution) retorno).setLineColumn(this.lineColumn);
		return retorno;
	}

	// @Override
	// public void evaluateConstraints(IntegerSolution Solution){
	// Solution.set
	// }

	@Override
	public void evaluate(IntegerSolution solution) {
		int load = 200;
		Integer[] vars = new Integer[solution.getNumberOfVariables()];
		List<Integer> pint = new ArrayList<>();
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
			// pint.add(solution.getVariableValue(i));
		}
		// System.out.println(pint);
		// System.out.println("teste"+((labeledIntegerSolution)
		// solution).getLineColumn()[2].getId());
		
		System.out.println("conte Evaluate: "+this.contEvaluate);
		this.contEvaluate+=1;
		//GmlData D = this.ptg.takeGmlData(((LabeledIntegerSolution) solution).getLineColumn(), vars);
		GmlData D = this.ptg.takeGmlData(solution.getLineColumn(), vars);
		OpticalNetworkProblem P = new OpticalNetworkProblem();
		P.reloadProblem(load, D);
		vars = P.getDefaultSolution();
		Double[] objectives = P.evaluate(vars);
		// System.out.println("Sumário da rede \"" + gml + "\" para a carga de "
		// + load + " erlangs:");
		System.out.println();
		System.out.printf("Probabilidade de bloqueio = %.6f\n", objectives[0]);
		System.out.printf("Custo de implantação = %.2f u.m.\n", objectives[1]);
		System.out.printf("Gasto energético = %.2f Watts\n", objectives[2]);
		System.out.printf("Conectividade algébrica = %.2f\n", 1 / (1 + objectives[3]));
		// setando os objetivos caulculados pelo fitnes para o Jmetal
		solution.setObjective(0, objectives[0]);
		solution.setObjective(1, objectives[1]);
		solution.setObjective(2, objectives[2]);
		solution.setObjective(3, 1 / (1 + objectives[3]));
	
		if(this.contEvaluate>3){
		
			testCoparacao(solution,this.anterior);
		}
		this.anterior=solution;
		

	}

	// @Override
	// public int getNumberOfVariables() {
	//
	// return this.kmeans.getCentroids().length
	// *((this.kmeans.getCentroids().length-1)/2);
	// }

	@Override
	public Integer getLowerBound(int index) {
		return this.lowerBound;
	}

	@Override
	public Integer getUpperBound(int index) {
		return this.upperBound;
	}

	/**
	 * método recebe uma lista de pattern e um pattern e retorna o pattern da
	 * lista mais próximo ao pattern recebido
	 * 
	 * @param node
	 * @param copyPatternList
	 * @return
	 */

	public Pattern takeNodeMinDistance(Pattern node, List<Pattern> copyPatternList) {
		double minDinstace = Double.MAX_VALUE;
		Pattern patternNode = null;
		for (Pattern P : copyPatternList) {
			if (P.getId() != node.getId()) {
				if (this.gml.getDistances()[node.getId()][P.getId()] < minDinstace) {
					minDinstace = this.gml.getDistances()[node.getId()][P.getId()];
					patternNode = P;
				}
			}
		}

		return patternNode;
	}

	/**
	 * metodo retorna os 3 patterns mais proximo a um pattern dentro do cluster
	 * dos parametros de recebimento, a lista de pattern em questão é o cluster
	 * e o patterns é o mebro do cluster aos qual se procura os 3 clusteres mais
	 * proximos
	 * 
	 * @param patternList
	 * @param pattern
	 * @return
	 */

	public List<Pattern> takeTreNodeMinDistance(List<Pattern> patternList, Pattern pattern) {
		List<Pattern> Litlepattern = new ArrayList<>();
		List<Pattern> copyPatternList = new ArrayList<>();
		copyPatternList.addAll(patternList);
		Litlepattern.add(takeNodeMinDistance(pattern, copyPatternList));
		copyPatternList.remove(Litlepattern.get(0));
		Litlepattern.add(takeNodeMinDistance(pattern, copyPatternList));
		copyPatternList.remove(Litlepattern.get(1));
		Litlepattern.add(takeNodeMinDistance(pattern, copyPatternList));
		return Litlepattern;
	}

	/**
	 * método pra modificar a matriz inteira mudando cada elemento dela por um
	 * dos 3 patterns mais proximos de forma aleatória
	 */
	public void createNewMatrix() {
		Pattern[] copyLineColumn = new Pattern[this.lineColumn.length];
		Random gerator = new Random();

		for (int i = 0; i < this.lineColumn.length; i++) {
			copyLineColumn[i] = takeTreNodeMinDistance(this.clustters[i], this.lineColumn[i]).get(gerator.nextInt(3));
		}
		this.lineColumn = copyLineColumn;

	}

	public void changeOneIndexOfMatrix(int position, Pattern pattern) {
		Random gerator = new Random();
		this.lineColumn[position] = takeTreNodeMinDistance(this.clustters[position], this.lineColumn[position])
				.get(gerator.nextInt(3));
	}

	public void testMudaElementoDaMatriz() {
		// teste muda elemento da matriz
		System.out.println("id de pattern de linha antes: " + this.lineColumn[2].getId());
		changeOneIndexOfMatrix(2, this.lineColumn[2]);
		System.out.println("id de pattern de linha depois: " + this.lineColumn[2].getId());
	}

	public void testMudaMatrixInterira() {
		// teste muda a matrix inteira
		for (int i = 0; i < this.lineColumn.length; i++) {
			System.out.println("linha: " + this.lineColumn[i].getId() + " coluna: " + this.lineColumn[i].getId());
		}

		createNewMatrix();

		System.out.println("#########################################");

		for (int i = 0; i < this.lineColumn.length; i++) {
			System.out.println("linha: " + this.lineColumn[i].getId() + " coluna: " + this.lineColumn[i].getId());
		}

	}

	public void testCardinalidadeCentroidCluster() {
		// teste se centroid n pretence ao cluster n
		// e centroid n+1 pertence ao cluster n+1
		int k = 0;
		for (int i = 0; i < this.centroids.length; i++) {
			System.out.println("tem centroide neste cluster: " + this.clustters[k].contains(this.centroids[i]));
			k += 1;
		}

	}

	public void SetNetWork() {
		int load = 80;
		Integer[] vars = new Integer[this.getNumberOfVariables()];
		for (int i = 0; i < vars.length; i++) {
			vars[i] = 1;
		}
		this.ptg.patternGmlData(this.lineColumn, vars);
		String path = "C:/Users/jorge/workspace/ClusterPe/src/Gmlevaluating.gml";
		this.opticalNetwoark = new OpticalNetworkProblem(load, path);
		int j = load;
	}

	public SearchForNetworkAndEvaluate(Kmeans kmeans, GmlData gml, List<Pattern>[] clustters) {
		super();
		this.setNumberOfObjectives(4);
		// tamanho do cromossomo
		this.setNumberOfVariables(kmeans.getCentroids().length * (kmeans.getCentroids().length - 1) / 2);
		this.upperBound = 1;// maior valor acomodado no cromossomo
		this.gml = gml;
		this.kmeans = kmeans;
		this.clustters = clustters;
		this.lineColumn = kmeans.getNearestPatternsFromCentroid();
		this.centroids = kmeans.getNearestPatternsFromCentroid();
		// testCardinalidadeCentroidCluster();
		// testMudaMatrixInterira();
		// testMudaElementoDaMatriz();
		this.ptg = new PatternToGml(gml);
		SetNetWork();
		//this.setNumberOfConstraints(1);
	}
}
