package br.multiobjetivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

import org.uma.jmetal.gmlNetwaork.PatternToGml;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.comparator.DominanceComparator;

import com.fasterxml.jackson.annotation.JsonProperty;

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
//	private GmlDao dao= new GmlDao();
	private List<Pattern>[] clustters;
	private Pattern[] centroids;
	private OpticalNetworkProblem opticalNetwoark;
//	private int contCreate = 0;
	private int contEvaluate = 0;
	IntegerSolution anterior;
	private List<String> fixedNetworkConections;
	private boolean FixedInitiallinks;
	private Properties prop;
	private boolean lineColumnChangedBefore = false;
	int gmlNumberRede = 1;
	private List<Integer[]> idCities;
	private List<Integer[]> arrayVar;
	private Map<Integer, Pattern> ClusterMap;
	private int fitnessNumberFromPrintArchive;
	private boolean IdidLoadFromVarX=false;

	public void testCoparacao(IntegerSolution s1, IntegerSolution s2) {
		DominanceComparator comparater = new DominanceComparator();
		int i = comparater.compare(s1, s2);
		if (i == -1) {
			System.out.println("s1 domina s2");
		}
		if (i == 0) {
			System.out.println("ambras sï¿½o nao dominadas");
		}
		if (i == 1) {
			System.out.println("s2 domina s1");
		}

	}

	/**
	 * este mï¿½todo ï¿½ nativo da classe problem, ele ja recebe solution por
	 * solution que vem de algum lugar com a criaï¿½ï¿½o inicial de forma
	 * aletï¿½ria. Atï¿½ onde eu lembro ela nï¿½o vem zerada na linnha da
	 * invocaï¿½ï¿½o IntegerSolution retorno = new DefaultIntegerSolution(this); mas
	 * se precisar modificar essa solutio "retorno" de alguma forma excepcional, o
	 * lugar ï¿½ neste mï¿½todo. no nosso caso, ele usar o mï¿½todo
	 * alwaysTheSameSolution pra partir com os mesmos links sempre. obs: dependendo
	 * do alg de cluster do pre processamento as cidades podem mudar, mas a linkagem
	 * sempre virar de um arquivo var com o o nome de fixedSolution.tsv
	 */

	@Override
	public IntegerSolution createSolution() {
		IntegerSolution retorno = new DefaultIntegerSolution(this);
		if (this.FixedInitiallinks) {
			retorno = this.alwaysTheSameSolution(retorno);
		}
		if (this.prop.getProperty("selectThePredeterminedPops").equals("y") && !this.lineColumnChangedBefore) {
			selectThePredeterminedPops();
		}
		if (this.prop.getProperty("startFromAstopedIteration").equals("y")) {
			if (this.prop.getProperty("fromGml").equals("y")) {
				retorno = this.retrievNetworksFromGmls();
				return retorno;
			} else {
				retorno = takeSolutionFromStopedExcution();
				return retorno;
			}
		}
		retorno.setLineColumn(lineColumn.clone());
		return retorno;
	}

	/**
	 * metodo escrito pra corrigir a posição dos centroids que retornam ordenados do
	 * framwork do simtom
	 * 
	 * @return
	 */

	public Pattern[] locateTheCorrectPosition(Pattern[] linecollunm) {
		Pattern[] correctLineCollunm = new Pattern[this.clustters.length];
		for (int i = 0; i < linecollunm.length; i++) {
			for (int l = 0; l < this.clustters.length; l++) {
				for (Pattern p : this.clustters[l]) {
					if (p.getId() == linecollunm[i].getId()) {
						correctLineCollunm[l] = linecollunm[i];
						break;
					}
				}

			}
		}
		return correctLineCollunm;

	}

	/**
	 * esse metodo foi escrito para recuperar redes de uma execucao parada a partir
	 * de arquivos gml porem como a lista de cidades eh ordenada na hora de salvar o
	 * gml, ao ser recuperada ela perde a ordem original o que desemparelha ela como
	 * o arrey de lista clusteres. isso atrapalhou a logica da busca que consideram
	 * o emparelhamento como a segurança de que a cidade indice 1 do lineCollumn
	 * pertence cluster 1 no array de lista de cluster.
	 * 
	 * @return
	 */

	public IntegerSolution retrievNetworksFromGmls() {
		OpticalNetworkProblem p = new OpticalNetworkProblem();
		GmlDao gml = new GmlDao();
		String path = this.prop.getProperty("pathStartStopedExecution") + "/" + "execução "
				+ this.prop.getProperty("executionStoped") + "/" + "ResultadoGML"
				+ this.prop.getProperty("interationStopedInExecution") + "/" + Integer.toString(this.gmlNumberRede)
				+ ".gml";
		GmlData gmlData = gml.loadGmlData(path);
		// o gmlData original estava trocando os ids originais
		// estao esse foi criado so pra recuperar os ids corretos
		GmlData gmlDataOfCorrectIndex = gml.loadGmlDataWithTheSamId(path, true);
		p.reloadProblem(Integer.parseInt(this.prop.getProperty("erlangs")), gmlData);
		IntegerSolution solution = new DefaultIntegerSolution(this);
		Pattern[] lineCollunm = new Pattern[this.clustters.length];
		lineCollunm = this.ptg.takePatternData(gmlDataOfCorrectIndex);
		lineCollunm = locateTheCorrectPosition(lineCollunm.clone());
		solution.setLineColumn(lineCollunm);
		if (!this.IdidLoadFromVarX) {
			try {
				path= this.prop.getProperty("pathStartStopedExecution") + "/" + "execução "
						+ this.prop.getProperty("executionStoped") + "/" +"var"+ this.prop.getProperty("interationStopedInExecution") +".tsv";
				getTheChromosomeFronVarX(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Integer[] ArrayVar = this.arrayVar.get(0);
		this.arrayVar.remove(0);

		for (int i = 0; i < ArrayVar.length; i++) {
			solution.setVariableValue(i, ArrayVar[i]);
		}
		
//		Integer[] variables = p.getDefaultSolution();
//		for (int i = 0; i < this.getNumberOfVariables(); i++) {
//			solution.setVariableValue(i, variables[i]);
//
//		}

		this.gmlNumberRede += 1;
		return solution;
	}

	/**
	 * metodo lï¿½ as conexï¿½es da poluï¿½ï¿½o iniciala partir de um array list no
	 * atributos Observe que ele sï¿½ pode fazer isso uma vez jï¿½ que ele remove a
	 * cabeï¿½a da lista fixedNetworkConections, que guarda a lista de strings
	 * recuperada de um arquivo var.tsv pelo mï¿½todo
	 * retrieveTheFixedInitialNetworks. Uu seja, apos carregar a populaï¿½ï¿½o
	 * inicial a lista fixedNetworkConections serï¿½ esvaziada.
	 * 
	 * @param s
	 * @return
	 */

	public IntegerSolution alwaysTheSameSolution(IntegerSolution s) {

		String[] rede = new String[this.getNumberOfVariables()];
		rede = this.fixedNetworkConections.get(0).split(" ");

		for (int i = 0; i < rede.length; i++) {
			s.setVariableValue(i, Integer.parseInt(rede[i]));
		}
		this.fixedNetworkConections.remove(0);
		return s;
	}

	/**
	 * esse dois metodos a seguir sÃ£o o polimorfismo do metodo que coloca um
	 * conjunto de pops predeterminado pelo usuÃ¡rio, nÃ£o mais como uma decisÃ£o do
	 * alg.
	 * 
	 * @param s
	 * @return
	 */

	/*
	 * public IntegerSolution selectThePredeterminedPops(IntegerSolution s) {
	 * Pattern[] lineColumnLocal = new Pattern[this.kmeans.getK()]; String
	 * presetctedPop = this.prop.getProperty("hardPop"); String[] arrayIdPop =
	 * presetctedPop.split(","); for (int i = 0; i < this.clustters.length; i++) {
	 * 
	 * for (int w = 0; w < arrayIdPop.length; w++) {
	 * 
	 * for (Pattern p : this.clustters[i]) { if
	 * (p.getId()==Integer.parseInt(arrayIdPop[w])) { lineColumnLocal[w]=p; } } } }
	 * 
	 * s.setLineColumn(lineColumnLocal); return s; }
	 */
	public void selectThePredeterminedPops() {
		Pattern[] lineColumnLocal = new Pattern[this.kmeans.getK()];
		String presetctedPop = this.prop.getProperty("hardPop");
		String[] arrayIdPop = presetctedPop.split(";");
		for (int i = 0; i < this.clustters.length; i++) {

			for (int w = 0; w < arrayIdPop.length; w++) {

				for (Pattern p : this.clustters[i]) {
					if (p.getId() == Integer.parseInt(arrayIdPop[w])) {
						lineColumnLocal[w] = p;
					}
				}
			}
		}
		this.lineColumn = lineColumnLocal;
		this.lineColumnChangedBefore = true;
		SetNetWork();
	}

	public void retrieveTheFixedInitialNetworks() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(this.prop.getProperty("pathS.U")));// "src/fixedSolution.tsv"
		String linha;
		List<String> lista = new ArrayList();
		while ((linha = br.readLine()) != null) {
			lista.add(linha);

		}
		this.fixedNetworkConections = lista;
	}

	/**
	 * construcao de um hashmap dos cluster pra encontrar um pattern sem muito custo
	 * sera usado no metodo que levanta as solucoes a partir do print.txt e do
	 * var.tsv
	 */
	public void constructMapNodeClusters() {
		List<Pattern[]> listLineCollumn = new ArrayList<>();
		Map<Integer, Pattern> ClusterMap = new HashMap<Integer, Pattern>();

		for (int l = 0; l < this.clustters.length; l++) {
			for (Pattern p : this.clustters[l]) {
				ClusterMap.put(p.getId(), p);
			}

		}
		this.ClusterMap = ClusterMap;
	}

	/**
	 * constroi uma lista de array de ids de cidades a partir do arquivo print.txt
	 * para ser usado na recosntrucao das solutions a partir de uma execucao parada
	 * 
	 * @throws IOException
	 */

	public void constructArrayIdCities() throws IOException {
		if (!(this.prop.getProperty("fromGml").equals("y"))) {
			BufferedReader br = new BufferedReader(new FileReader(this.prop.getProperty("pathStartStopedExecution")
					+ "\\" + "execução " + this.prop.getProperty("executionStoped") + "/" + "print.txt"));
			String linha;
			List<String> idCities = new ArrayList();
			while ((linha = br.readLine()) != null) {
				String[] arrayLinha = linha.split(" ");
				if (arrayLinha[0].equals("centroides") && arrayLinha[1].equals("final")) {
					linha = linha.replace("centroides final : ", "");
					linha = linha.replace("[", "");
					linha = linha.replace("]", "");
					linha = linha.replace(",", "");
					idCities.add(linha);
				}
			}
			List<Integer[]> arrayId = new ArrayList<>();
			for (String s : idCities) {
				String[] l = s.split(" ");
				Integer[] intInCities = new Integer[l.length];
				for (int i = 0; i < l.length; i++) {
					intInCities[i] = Integer.parseInt(l[i]);
				}
				arrayId.add(intInCities);
			}
			this.idCities = arrayId;

		}

	}

	/**
	 * constroi uma lista de array de variable a partir do arquivo var.tsv para ser
	 * usado na recosntrucao das solutions a partir de uma execucao parada
	 * com uma  falha por excecao
	 * 
	 * @throws IOException
	 */
	public void constructArrayChromosome() throws IOException {

		if (!(this.prop.getProperty("fromGml").equals("y"))) {
			BufferedReader br = new BufferedReader(new FileReader(this.prop.getProperty("pathStartStopedExecution")
					+ "\\" + "execução " + this.prop.getProperty("executionStoped") + "/" + "var.tsv"));
			String linha;
			List<String> variableString = new ArrayList();
			while ((linha = br.readLine()) != null) {
				String[] arrayLinha = linha.split(" ");
				variableString.add(linha);
			}
			List<Integer[]> arrayvar = new ArrayList<>();
			for (String s : variableString) {
				String[] l = s.split(" ");
				Integer[] intVar = new Integer[l.length];
				for (int i = 0; i < l.length; i++) {
					intVar[i] = Integer.parseInt(l[i]);
				}
				arrayvar.add(intVar);
			}
			this.arrayVar = arrayvar;
		}

	}
	
	/**
	 *esse medodo e um auxiliar para a tecnica que recomeca a partir de um arquivo gml.
	 *Constroi uma lista de array de variable a partir do arquivo varX.tsv para ser
	 * usado na recosntrucao das solutions a partir de uma execucao parada porem 
	 * parada sem falha por excecao
	 * 
	 * @throws IOException
	 */
	public void getTheChromosomeFronVarX(String path) throws IOException {

			BufferedReader br = new BufferedReader(new FileReader(path));
			String linha;
			List<String> variableString = new ArrayList();
			while ((linha = br.readLine()) != null) {
				String[] arrayLinha = linha.split(" ");
				variableString.add(linha);
			}
			List<Integer[]> arrayvar = new ArrayList<>();
			for (String s : variableString) {
				String[] l = s.split(" ");
				Integer[] intVar = new Integer[l.length];
				for (int i = 0; i < l.length; i++) {
					intVar[i] = Integer.parseInt(l[i]);
				}
				arrayvar.add(intVar);
			}
			this.IdidLoadFromVarX=true;
			this.arrayVar = arrayvar;

	}

	/**
	 * recupera uma solution de uma execucao parada
	 */
	public IntegerSolution takeSolutionFromStopedExcution() {
		IntegerSolution s = new DefaultIntegerSolution(this);
		Integer[] ArrayIdCities = this.idCities.get(0);
		this.idCities.remove(0);
		Pattern[] p = new Pattern[this.lineColumn.length];

		for (int i = 0; i < ArrayIdCities.length; i++) {
			p[i] = this.ClusterMap.get(ArrayIdCities[i]);
		}

		s.setLineColumn(p);
		Integer[] ArrayVar = this.arrayVar.get(0);
		this.arrayVar.remove(0);

		for (int i = 0; i < ArrayVar.length; i++) {
			s.setVariableValue(i, ArrayVar[i]);
		}
		return s;

	}

	// public boolean isolated(GmlData data) {
	// System.out.println("pontos isolados ? "+ data.containsIsolatedNodes());
	// for (GmlNode G : data.getNodes()) {
	// boolean isolated = true;
	// for (GmlEdge E : data.getEdges()) {
	// if (E.getSource().getLabel().equals(G.getLabel()) ||
	// E.getTarget().getLabel().equals(G.getLabel())) {
	// isolated = false;
	// break;
	// }
	// }
	// if (isolated == false) {
	// isolated = true;
	// } else {
	// return true;
	// }
	//
	// }
	// return false;
	// }

	@Override
	public void evaluate(IntegerSolution solution) {
		int load = Integer.parseInt(this.prop.getProperty("erlangs"));
		Integer[] vars = new Integer[solution.getNumberOfVariables()];
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
		}

		System.out.println("conte Evaluate: " + this.contEvaluate);
		this.contEvaluate += 1;
		GmlData D = this.ptg.takeGmlData(solution.getLineColumn(), vars);
		if (D.containsIsolatedNodes()) {
			solution.setObjective(0, 1.000000);
			solution.setObjective(1, 100000.00);
			solution.setObjective(2, 100000000.00);
			solution.setObjective(3, 1);
		} else {
			OpticalNetworkProblem P = new OpticalNetworkProblem();
			P.reloadProblem(load, D);
//			vars = P.getDefaultSolution(); comentado por danilo no lance de variar os canais
			Double[] objectives = P.evaluate(vars);
			solution.setObjective(0, objectives[0]);
			solution.setObjective(1, objectives[1]);
			solution.setObjective(2, objectives[2]);
			solution.setObjective(3, 1 / (1 + objectives[3]));
		}
	}

	// @Override
	// public int getNumberOfVariables() {
	//
	// return this.kmeans.getCentroids().length
	// *((this.kmeans.getCentroids().length-1)/2);
	// }

	@Override
	public Integer getLowerBound(int index) {
		return this.lowerLimit.get(index);// modificado por danilo
	}

	@Override
	public Integer getUpperBound(int index) {
		return this.upperLimit.get(index);// modificado por danilo
	}

	/**
	 * mï¿½todo recebe uma lista de pattern e um pattern e retorna o pattern da
	 * lista mais prï¿½ximo ao pattern recebido
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
	 * metodo retorna os 3 patterns mais proximo a um pattern dentro do cluster dos
	 * parametros de recebimento, a lista de pattern em questï¿½o ï¿½ o cluster e o
	 * patterns ï¿½ o mebro do cluster aos qual se procura os 3 clusteres mais
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
	 * mï¿½todo pra modificar a matriz inteira mudando cada elemento dela por um dos
	 * 3 patterns mais proximos de forma aleatï¿½ria
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
		int load = Integer.parseInt(this.prop.getProperty("erlangs"));
		Integer[] vars = new Integer[this.getNumberOfVariables()];
		for (int i = 0; i < vars.length; i++) {
			vars[i] = 1;
		}
		this.ptg.patternGmlData(this.lineColumn, vars);
		String path = "src/Gmlevaluating.gml";
		this.opticalNetwoark = new OpticalNetworkProblem(load, path);
	}

	public GmlData getGml() {
		return gml;
	}

	public void printIncialCentroide() {
		List<Integer> centros = new ArrayList<>();
		for (int i = 0; i < this.centroids.length; i++) {
			centros.add(this.centroids[i].getId());
		}

		System.out.println("centoides inicial : " + centros);
	}

	public int getContEvaluate() {
		return contEvaluate;
	}

	public PatternToGml getPtg() {
		return ptg;
	}

	/**
	 * 
	 * nesta parte serï¿½o gerados um monte de gaet e set pra satisfazer o jackson
	 * 
	 * @param kmeans
	 * @param gml
	 * @param clustters
	 * @param fixedLinks
	 */
	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	public Kmeans getKmeans() {
		return kmeans;
	}

	public void setKmeans(Kmeans kmeans) {
		this.kmeans = kmeans;
	}

	public Pattern[] getLineColumn() {
		return lineColumn;
	}

	public void setLineColumn(Pattern[] lineColumn) {
		this.lineColumn = lineColumn;
	}

	public List<Pattern>[] getClustters() {
		return clustters;
	}

	public void setClustters(List<Pattern>[] clustters) {
		this.clustters = clustters;
	}

	public Pattern[] getCentroids() {
		return centroids;
	}

	public void setCentroids(Pattern[] centroids) {
		this.centroids = centroids;
	}

	public OpticalNetworkProblem getOpticalNetwoark() {
		return opticalNetwoark;
	}

	public void setOpticalNetwoark(OpticalNetworkProblem opticalNetwoark) {
		this.opticalNetwoark = opticalNetwoark;
	}

	public IntegerSolution getAnterior() {
		return anterior;
	}

	public void setAnterior(IntegerSolution anterior) {
		this.anterior = anterior;
	}

	public List<String> getFixedNetworkConections() {
		return fixedNetworkConections;
	}

	public void setFixedNetworkConections(List<String> fixedNetworkConections) {
		this.fixedNetworkConections = fixedNetworkConections;
	}

	public boolean isFixedInitiallinks() {
		return FixedInitiallinks;
	}

	public void setFixedInitiallinks(boolean fixedInitiallinks) {
		FixedInitiallinks = fixedInitiallinks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setGml(GmlData gml) {
		this.gml = gml;
	}

	public void setPtg(PatternToGml ptg) {
		this.ptg = ptg;
	}

	public void setContEvaluate(int contEvaluate) {
		this.contEvaluate = contEvaluate;
	}

	// ********************************************************************************************

//	@JsonCreator
	public SearchForNetworkAndEvaluate(@JsonProperty("kmeans") Kmeans kmeans, @JsonProperty("gml") GmlData gml,
			@JsonProperty("clustters") List<Pattern>[] clustters,
			/* @JsonProperty("fixedInitiallinks")String fixedLinks */@JsonProperty("prop") Properties prop) {
		super();
		this.setNumberOfObjectives(4);
		// tamanho do cromossomo
		this.setNumberOfVariables(kmeans.getCentroids().length * (kmeans.getCentroids().length - 1) / 2 + 2);
		// **************** adicionado por danilo daqui pra baixo******************
		List<Integer> ll = new Vector<>();
		List<Integer> ul = new Vector<>();
		for (int i = 0; i < this.getNumberOfVariables() - 2; i++) {
			ll.add(0);
			ul.add(1);
		}
		ll.add(0);
		ul.add(5);
		ll.add(4);
		ul.add(40);
		this.setLowerLimit(ll);
		this.setUpperLimit(ul);
		// ***********************adicionado por danilo daqui pra cima************
		this.upperBound = 1;// maior valor acomodado no cromossomo
		this.gml = gml;
		this.kmeans = kmeans;
		this.clustters = clustters;
		this.lineColumn = kmeans.getNearestPatternsFromCentroid();
		this.centroids = kmeans.getNearestPatternsFromCentroid();
		this.prop = prop;
		try {
			retrieveTheFixedInitialNetworks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (/* fixedLinks.equals("y") */prop.getProperty("solucaoInicialUnica").equals("y")) {
			this.FixedInitiallinks = true;
		} else {
			this.FixedInitiallinks = false;
		}

		this.ptg = new PatternToGml(gml);
		SetNetWork();
		printIncialCentroide();
		if (prop.getProperty("startFromAstopedIteration").equals("y")) {
			this.constructMapNodeClusters();
			try {
				this.constructArrayIdCities();
				this.constructArrayChromosome();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
