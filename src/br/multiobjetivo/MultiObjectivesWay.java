package br.multiobjetivo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
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
import br.cns.model.GmlNode;
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

		algorithm = new NSGAIIIBuilder<>(problem).setCrossoverOperator(crossover).setMutationOperator(mutation)
				.setSelectionOperator(selection).setPopulationSize(500).setMaxIterations(30).build();

		List<IntegerSolution> population;
		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
		population = algorithm.getResult();
		long computingTime = algorithmRunner.getComputingTime();
		JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

		printFinalSolutionSet(population);

	}

	/**
	 * Write the population into two files and prints some data on screen
	 * 
	 * @param population
	 */
	public static void printFinalSolutionSet(List<? extends Solution<?>> population) {

		new SolutionListOutput(population).setSeparator("\t")
				.setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
				.setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv")).print();

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