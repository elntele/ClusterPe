package br.multiobjetivo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
	
	public MultiObjectivesWay(Kmeans kmeans, GmlData gml,List<Pattern>[] clustters,Properties prop) throws IOException {
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

		algorithm = new NSGAIIIBuilder<>(problem,((SearchForNetworkAndEvaluate)problem).getGml(),clustters, prop).setCrossoverOperator(crossover).setMutationOperator(mutation)
				.setSelectionOperator(selection).setPopulationSize(40).setMaxIterations(500).build();

		List<IntegerSolution> population;
		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
		population = algorithm.getResult();
		int w=1;
		
		 String path=prop.getProperty("local")+prop.getProperty("algName")
			+"/"+prop.getProperty("modo")+"/"+prop.getProperty("execucao")+"/"+"print.txt";

	    FileWriter arq = new FileWriter(path);
	    PrintWriter gravarArq = new PrintWriter(arq);
	    
	   
		
		PatternToGml ptgLocal=((SearchForNetworkAndEvaluate)problem).getPtg();
		new File (prop.getProperty("local")+prop.getProperty("algName")+"/"
		+prop.getProperty("modo")+"/"+prop.getProperty("execucao")+
		"/ResultadoGML").mkdir();
		String pathTogml=prop.getProperty("local")+prop.getProperty("algName")
		+"/"+prop.getProperty("modo")+"/"+prop.getProperty("execucao");
		for (IntegerSolution i: population){
//			String s="C:/Users/jorge/Desktop/rural 2/2017.2/tcc/testes_evolucionarios/ResultadoGML/"+Integer.toString(w)+".gml";
			String s=pathTogml+"/ResultadoGML/"+Integer.toString(w)+".gml";
			ptgLocal.saveGmlFromSolution(s, i);
			List <Integer> centros=new ArrayList<>(); 
			for (int j=0; j<i.getLineColumn().length;j++){
				centros.add(i.getLineColumn()[j].getId());
			}
			w+=1;
			System.out.println("centroides final : " +centros);
			 gravarArq.printf("centroides final : " +centros+'\n');
			 
		}
		
		System.out.println("numero de avaliações de fitness"+((SearchForNetworkAndEvaluate)problem).getContEvaluate());
		gravarArq.printf("numero de avaliações de fitness"+((SearchForNetworkAndEvaluate)problem).getContEvaluate()+'\n');
		System.out.println("base salva em formato GML");
		System.out.println("numero de soluções não dominadas encontrado pela busca local: "+((NSGAIII)algorithm).getLocalSeachFoundNoDominated() );
		gravarArq.printf("numero de soluções não dominadas encontrado pela busca local: "+((NSGAIII)algorithm).getLocalSeachFoundNoDominated()+'\n');
		long computingTime = algorithmRunner.getComputingTime();
		JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
		gravarArq.printf("Total execution time: " + computingTime + "ms"+'\n');
		printFinalSolutionSet(population,kmeans, arq, gravarArq, prop);

	}

	/**
	 * Write the population into two files and prints some data on screen
	 * 
	 * @param population
	 * @throws IOException 
	 */
	public static void printFinalSolutionSet(List<? extends Solution<?>> population, Kmeans kmeans,
			FileWriter arq,PrintWriter gravarArq,Properties prop) throws IOException {
		String path=prop.getProperty("local")+prop.getProperty("algName")
		+"/"+prop.getProperty("modo")+"/"+prop.getProperty("execucao"); 

		new SolutionListOutput(population).setSeparator("\t")
				.setVarFileOutputContext(new DefaultFileOutputContext(path+"/"+"VAR.tsv"))
				.setFunFileOutputContext(new DefaultFileOutputContext(path+"/"+"FUN.tsv")).print();
		
		List<Integer> centros = new ArrayList<>();
		for (int i = 0; i < kmeans.getNearestPatternsFromCentroid().length; i++) {
			centros.add(kmeans.getNearestPatternsFromCentroid()[i].getId());
		}
		System.out.println("centoides inicial: " + centros);
		gravarArq.printf("centoides inicial: " + centros+'\n');
		JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
		gravarArq.printf("Random seed: " + JMetalRandom.getInstance().getSeed()+'\n');
		arq.close();
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