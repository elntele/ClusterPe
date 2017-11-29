package br.multiobjetivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

import br.cns.model.GmlData;
import cbic15.Kmeans;
import cbic15.Pattern;

public class SearchForNetworkAndEvaluate extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private int lowerBound = 0;
	private int upperBound;
	private Kmeans kmeans;
	private GmlData gml;
	private Pattern[] line;
	private Pattern[] column;
	private List<Pattern>[] clustters;
	private Pattern[] centroids;

	@Override
	public IntegerSolution createSolution() {
		IntegerSolution retorno = new DefaultIntegerSolution(this);
		Random gerador = new Random();

		for (int i = 0; i < getNumberOfVariables(); i++) {
			retorno.setVariableValue(i, gerador.nextInt(2));
		}

//		for (int k = 0; k < getNumberOfVariables(); k++) {
//			System.out.println("valor sorteado" + retorno.getVariableValueString(k));
//		}

		return retorno;
	}

	@Override
	public void evaluate(IntegerSolution solution) {

		int[] vars = new int[solution.getNumberOfVariables()];
		List<Integer> impr = new ArrayList();
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
		}
		// setando a sugestão de matricula em problema preparado
		// solution.setObjective(0, problemaPreparado.getTempoDeFormatura());
		// solution.setObjective(1, problemaPreparado.getVarianciaTotal());
		// solution.setObjective(2,
		// problemaPreparado.getQtdDiscForaDaMinhaArea());
		// solution.setObjective(3,
		// problemaPreparado.getVariaQtdDiscPorPeriodo());
		// solution.setObjective(4, problemaPreparado.getTempoExtraClasse());

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
		Pattern[] copyLine = new Pattern[this.line.length];
		Pattern[] copyColumn = new Pattern[this.column.length];
		Random gerator = new Random();

		for (int i = 0; i < this.line.length; i++) {
			copyLine[i] = takeTreNodeMinDistance(this.clustters[i], this.line[i]).get(gerator.nextInt(3));
			copyColumn[i] = takeTreNodeMinDistance(this.clustters[i], this.line[i]).get(gerator.nextInt(3));
		}
		this.line = copyLine;
		this.column = copyColumn;

	}

	public void changeOneIndexOfLineMatrix(int position, Pattern pattern) {
		Random gerator = new Random();
		this.line[position] = takeTreNodeMinDistance(this.clustters[position], this.line[position])
				.get(gerator.nextInt(3));
	}

	public void changeOneIndexOfColumnMatrix(int position, Pattern pattern) {
		Random gerator = new Random();
		this.column[position] = takeTreNodeMinDistance(this.clustters[position], this.column[position])
				.get(gerator.nextInt(3));
	}

	public void teste(){
		// teste se centroid n pretence ao cluster n 
		//e centroid n+1 pertence ao cluster n+1
		int k=0;
		for (int i=0;i<this.centroids.length;i++){
			System.out.println("tem centroide neste cluster: "+	this.clustters[k].contains(this.centroids[i]));
			k+=1;
		}
		// teste muda elemento da linha
		System.out.println("id de pattern de linha antes: "+ this.line[2].getId());
		changeOneIndexOfLineMatrix(2,this.line[2]);
		System.out.println("id de pattern de linha depois: "+ this.line[2].getId());
		//teste muda elemento de coluna
		System.out.println("id de pattern de coluna antes: "+ this.column[2].getId());
		changeOneIndexOfColumnMatrix(2,this.column[2]);
		System.out.println("id de pattern de coluna depois: "+ this.column[2].getId());
		System.out.println();
		//teste muda a matrix inteira
		for (int i=0;i<this.line.length;i++){
			System.out.println("linha: "+this.line[i].getId()+" coluna: "+this.column[i].getId());
		}
		
		createNewMatrix();
		System.out.println("#########################################");
		for (int i=0;i<this.line.length;i++){
			System.out.println("linha: "+this.line[i].getId()+" coluna: "+this.column[i].getId());
		}
		
	}

	public SearchForNetworkAndEvaluate(Kmeans kmeans, GmlData gml, List<Pattern>[] clustters) {
		super();
		this.setNumberOfObjectives(3);
		this.setNumberOfVariables(kmeans.getCentroids().length * (kmeans.getCentroids().length - 1) / 2);// tamanho
																											// do
																											// cromossomo
		this.upperBound = 1;// maior valor acomodado no cromossomo
		this.gml = gml;
		this.kmeans = kmeans;
		this.clustters = clustters;
		this.line = kmeans.getNearestPatternsFromCentroid();
		this.column = kmeans.getNearestPatternsFromCentroid();
		this.centroids=kmeans.getNearestPatternsFromCentroid();
		//createSolution();
		teste();
		
		
	}
}
