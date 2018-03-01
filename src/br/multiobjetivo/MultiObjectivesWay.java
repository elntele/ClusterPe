package br.multiobjetivo;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import br.cns.model.GmlData;
import cbic15.Kmeans;
import cbic15.Pattern;

public class MultiObjectivesWay {
	
	public MultiObjectivesWay(Kmeans kmeans, GmlData gml,List<Pattern>[] clustters) {
		Problem<IntegerSolution> problem; // do Jmetal
		Algorithm<List<IntegerSolution>> algorithm; // do Jmetal
		CrossoverOperator<IntegerSolution> crossover; // do Jmetal
		MutationOperator<IntegerSolution> mutation; // do Jmetal
		SelectionOperator<List<IntegerSolution>, IntegerSolution> selection; // do
		problem = new SearchForNetworkAndEvaluate(kmeans, gml,clustters);

		// ****************************
		double crossoverProbability = 1.0;
		double crossoverDistributionIndex = 20.0;
		crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex);
		double mutationProbability = 1.0 / problem.getNumberOfVariables();
		double mutationDistributionIndex = 20.0;
		mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex);
		selection = new BinaryTournamentSelection<IntegerSolution>();

		// algorithm = new NSGAIIBuilder<>(problem, crossover, mutation)
		// .setSelectionOperator(selection).setPopulationSize(1000).setMaxEvaluations(1000).build();
		// AlgorithmRunner algorithmRunner = new
		// AlgorithmRunner.Executor(algorithm).execute();

		algorithm = new NSGAIIIBuilder<>(problem,((SearchForNetworkAndEvaluate)problem).getGml(),clustters).setCrossoverOperator(crossover).setMutationOperator(mutation)
				.setSelectionOperator(selection).setPopulationSize(40).setMaxIterations(500).build();

		List<IntegerSolution> population;
		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
		population = algorithm.getResult();
		int w=1;
		
		PatternToGml ptgLocal=((SearchForNetworkAndEvaluate)problem).getPtg();
		for (IntegerSolution i: population){
			String s="C:/Users/jorge/Desktop/rural 2/2017.2/tcc/testes_evolucionarios/ResultadoGML/"+Integer.toString(w)+".gml";
			ptgLocal.saveGmlFromSolution(s, i);
			List <Integer> centros=new ArrayList<>(); 
			for (int j=0; j<i.getLineColumn().length;j++){
				centros.add(i.getLineColumn()[j].getId());
			}
			w+=1;
			System.out.println("centoides final : " +centros);
		}
		
		System.out.println("numero de avaliações de fitness"+((SearchForNetworkAndEvaluate)problem).getContEvaluate());
		System.out.println("base salva em formato GML");
		System.out.println("numro de soluções não dominadas encontrado pela busca local: "+((NSGAIII)algorithm).getLocalSeachFoundNoDominated() );
		
		long computingTime = algorithmRunner.getComputingTime();
		JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

		printFinalSolutionSet(population,kmeans);

	}

	/**
	 * Write the population into two files and prints some data on screen
	 * 
	 * @param population
	 */
	public static void printFinalSolutionSet(List<? extends Solution<?>> population, Kmeans kmeans) {

		new SolutionListOutput(population).setSeparator("\t")
				.setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
				.setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv")).print();
		
		List<Integer> centros = new ArrayList<>();
		for (int i = 0; i < kmeans.getNearestPatternsFromCentroid().length; i++) {
			centros.add(kmeans.getNearestPatternsFromCentroid()[i].getId());
		}
		System.out.println("centoides inicial: " + centros);
		JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
		JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
		JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
	}

	/**
	 * Print all the available quality indicators
	 * 
	 * @param population
	 * @param paretoFrontFile
	 * @throws FileNotFoundException
	 */

}