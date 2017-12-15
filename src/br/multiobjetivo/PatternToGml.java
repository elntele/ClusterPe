package br.multiobjetivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.solution.IntegerSolution;

import br.cns.model.GmlData;
import br.cns.model.GmlEdge;
import br.cns.model.GmlNode;
import br.cns.persistence.GmlDao;
import cbic15.Pattern;

public class PatternToGml {
	private GmlData gml;
	private Map mapNode;

	public PatternToGml(GmlData gml) {
		Map<Integer, GmlNode> map = new HashMap<Integer, GmlNode>();
		this.gml = gml;
		for (GmlNode G : gml.getNodes()) {
			map.put(G.getId(), G);
		}
		this.mapNode = map;
	}

	public List<GmlNode> patternGml(Pattern[] ArrayPatterns) {
		List<GmlNode> listNode = new ArrayList<>();
		for (int i = 0; i < ArrayPatterns.length; i++) {
			listNode.add((GmlNode) this.mapNode.get(ArrayPatterns[i].getId()));
		}
		return listNode;
	}

	public BooleanAndEdge makelink(Pattern[] arrayPatterns, Integer[] vars) {
		int VarIndex = 0;
		boolean have = false;
		BooleanAndEdge B = new BooleanAndEdge();
		List<GmlEdge> edges = new ArrayList<>();
		List<GmlEdge> falseEdges = new ArrayList<>();
		GmlEdge falseEdge = new GmlEdge();
		falseEdge.setSource((GmlNode) this.mapNode.get(arrayPatterns[1].getId()));
		falseEdge.setTarget((GmlNode) this.mapNode.get(arrayPatterns[2].getId()));
		falseEdges.add(falseEdge);
		for (int i = 0; i < arrayPatterns.length; i++) {
			for (int j = i; j < arrayPatterns.length; j++) {
				if (i != j) {
					if (vars[VarIndex] == 1) {
						GmlEdge edge = new GmlEdge();
						edge.setSource((GmlNode) this.mapNode.get(arrayPatterns[i].getId()));
						edge.setTarget((GmlNode) this.mapNode.get(arrayPatterns[j].getId()));
						edges.add(edge);
						have = true;
					}
					VarIndex += 1;

				}

			}
		}

		if (have) {
			B.setEdges(edges);
		} else {
			B.setEdges(falseEdges);
		}

		B.setHave(have);

		return B;
	}

	public GmlData takeGmlData(Pattern[] arrayPatterns, Integer[] vars) {
		GmlDao G = new GmlDao();
		GmlData gmlLocal = new GmlData();
		gmlLocal.setNodes(patternGml(arrayPatterns));
		BooleanAndEdge B = makelink(arrayPatterns, vars);
		// if (B.isHave()){
		gmlLocal.setEdges(B.getEdges());
		// }

		gmlLocal.createComplexNetwork();

		// try {
		gmlLocal = G.loadGmlDataFromContent(G.createFileContent(gmlLocal));
		// } catch (StringIndexOutOfBoundsException e) {
		// // TODO Auto-generated catch block
		// System.out.println("aqui");
		// e.printStackTrace();
		// }

		return gmlLocal;
	}

	public void patternGmlData(Pattern[] arrayPatterns, Integer[] vars) {
		List<GmlNode> listNode = new ArrayList<>();
		GmlDao G = new GmlDao();
		GmlData gmlLocal = new GmlData();
		GmlEdge edge = new GmlEdge();
		gmlLocal.setNodes(patternGml(arrayPatterns));
		BooleanAndEdge B = makelink(arrayPatterns, vars);
		gmlLocal.setEdges(B.getEdges());
		gmlLocal.createComplexNetwork();
		G.save(gmlLocal, "C:/Users/jorge/workspace/ClusterPe/src/GmlevaluatingMax.gml");

	}
	
	public void saveGmlFromSolution(String patch, IntegerSolution solution ) {
		Pattern[] arrayPatterns=solution.getLineColumn();
		Integer[] vars = new Integer[solution.getNumberOfVariables()];
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
		}
		Map<String, String> informations =new HashMap();
		informations.put("Country", "Brazil");
		informations.put("PB", Double.toString(solution.getObjective(0)));
		informations.put("Capex", Double.toString(solution.getObjective(1)));
		informations.put("Consumo em Watts", Double.toString(solution.getObjective(2)));
		informations.put("Conectividade Algébrica", Double.toString(solution.getObjective(3)));
		GmlDao G = new GmlDao();
		GmlData gmlLocal = new GmlData();
		gmlLocal.setNodes(patternGml(arrayPatterns));
		BooleanAndEdge B = makelink(arrayPatterns, vars);
		gmlLocal.setEdges(B.getEdges());
		gmlLocal.setInformations(informations);
		gmlLocal.createComplexNetwork();
		G.save(gmlLocal, patch);

	}

}
