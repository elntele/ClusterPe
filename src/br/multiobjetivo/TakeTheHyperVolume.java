package br.multiobjetivo;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.WFGHypervolume;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

public class TakeTheHyperVolume extends AbstractIntegerProblem {

	private static final long serialVersionUID = 1L;
	private int lowerBound = 0;
	private int upperBound;
	private List<List<IntegerSolution>> populationGroup = new ArrayList<>();
	private List<Double> hyperVolumePopulatios = new ArrayList<>();


	public void makeSolution(List<List<Double>> groupDorbleList) {
		List<IntegerSolution> population = new ArrayList<>();
		for (List L : groupDorbleList) {
			IntegerSolution solution = new DefaultIntegerSolution(this);
			solution.setObjective(0,(Double)L.get(0));
			solution.setObjective(1,(Double)L.get(1));
			solution.setObjective(2,(Double)L.get(2));
			solution.setObjective(3,(Double)L.get(3));
			population.add(solution);
		}
		this.populationGroup.add(population);

	}
	
	public void  hyperVolume(){
		WFGHypervolume wfg= new WFGHypervolume();
		for (List L:this.populationGroup){
			this.hyperVolumePopulatios.add(wfg.evaluate(L));
		}
		
	}
	

	// @Override
	// public IntegerSolution createSolution() {
	// IntegerSolution retorno = new DefaultIntegerSolution(this);
	//// for (int i = 0; i < getNumberOfVariables(); i++) {
	//// retorno.setVariableValue(i, 0);
	//// }
	// return retorno;
	// }

	@Override
	public Integer getLowerBound(int index) {
		return this.lowerBound;
	}

	@Override
	public Integer getUpperBound(int index) {
		return this.upperBound;
	}

	@Override
	public void evaluate(IntegerSolution solution) {
		// TODO Auto-generated method stub

	}
	
	

	

	public List<Double> getHyperVolumePopulatios() {
		return hyperVolumePopulatios;
	}

	public TakeTheHyperVolume() {
		super();
		this.lowerBound = 0;
		this.upperBound = 1;
		this.setNumberOfObjectives(4);
		// tamanho do cromossomo
		this.setNumberOfVariables(15 * (15 - 1) / 2);
		this.upperBound = 1;// maior valor acomodado no cromossomo
	}
}
