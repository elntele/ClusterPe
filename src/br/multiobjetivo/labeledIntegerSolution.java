package br.multiobjetivo;

import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

import cbic15.Pattern;

public class labeledIntegerSolution extends DefaultIntegerSolution {
	public labeledIntegerSolution(IntegerProblem problem) {
		super(problem);
	}

	private Pattern[] lineColumn;
	
	
	public Pattern[] getLineColumn() {
		return lineColumn;
	}

	public void setLineColumn(Pattern[] lineColumn) {
		this.lineColumn = lineColumn;
	}
	
	

}
