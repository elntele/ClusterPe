package br.multiobjetivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

import br.cns.model.GmlData;
import cbic15.Kmeans;

public class SearchForNetworkAndEvaluate extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private int lowerBound = 0;
	private int upperBound;	
	private Kmeans kmeans; 
	private GmlData gml;
	
	@Override
	public IntegerSolution createSolution() {
		IntegerSolution retorno = new DefaultIntegerSolution(this); 
		Random gerador = new Random();		 
        for (int i=0;i<getNumberOfVariables();i++){
        	  retorno.setVariableValue(i,gerador.nextInt(2));
        }
        
        for (int k=0;k<getNumberOfVariables();k++){
        	System.out.println("valor sorteado"+retorno.getVariableValueString(k));
        }
        
		return retorno;
  }
	
	@Override
	public void evaluate(IntegerSolution solution) {
		
		int [] vars = new int[solution.getNumberOfVariables()];
		List <Integer> impr= new ArrayList();
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
		}
		//setando a sugestão de matricula em problema preparado				
//		solution.setObjective(0, problemaPreparado.getTempoDeFormatura());
//		solution.setObjective(1, problemaPreparado.getVarianciaTotal());
//		solution.setObjective(2, problemaPreparado.getQtdDiscForaDaMinhaArea());
//		solution.setObjective(3, problemaPreparado.getVariaQtdDiscPorPeriodo());
//		solution.setObjective(4, problemaPreparado.getTempoExtraClasse());
		
	}

	
	@Override
	public int getNumberOfVariables() {
		
		return this.kmeans.getCentroids().length *((this.kmeans.getCentroids().length-1)/2);
	}
	
	@Override
	public Integer getLowerBound(int index) {
		return this.lowerBound;
	}

	@Override
	public Integer getUpperBound(int index) {
		return this.upperBound;
	}

	public SearchForNetworkAndEvaluate(Kmeans kmeans, GmlData gml) {
		super();
		this.setNumberOfObjectives(5);
		this.setNumberOfVariables(kmeans.getCentroids().length*(kmeans.getCentroids().length-1)/2);// tamanho do cromossomo
		this.upperBound=1;// maior valor acomodado no cromossomo
		this.gml=gml;
		this.kmeans=kmeans;
		createSolution();
	}
}
