package br.multiobjetivo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.util.point.impl.ArrayPoint;

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

	public List<GmlEdge> makelink(Pattern[] arrayPatterns, Integer[] vars) {
		int VarIndex = 0;
		List<GmlEdge> edges = new ArrayList<>();
		for (int i = 0; i < arrayPatterns.length; i++) {
			for (int j = i; j < arrayPatterns.length; j++) {
				if (i != j) {
					if (vars[VarIndex] == 1) {
						GmlEdge edge = new GmlEdge();
						edge.setSource((GmlNode) this.mapNode.get(arrayPatterns[i].getId()));
						edge.setTarget((GmlNode) this.mapNode.get(arrayPatterns[j].getId()));
						edges.add(edge);
					}
					VarIndex += 1;

				}

			}
		}

		return edges;
	}

	public void patternGmlData(Pattern[] arrayPatterns, Integer[] vars) {
		List<GmlNode> listNode = new ArrayList<>();
		GmlDao G = new GmlDao();
		GmlData gmlLocal = new GmlData();
		GmlEdge edge = new GmlEdge();
		gmlLocal.setNodes(patternGml(arrayPatterns));
		gmlLocal.setEdges(makelink(arrayPatterns, vars));
		gmlLocal.createComplexNetwork();
		G.save(gmlLocal, "C:/Users/jorge/workspace/ClusterPe/src/Gmlevaluating.gml");

	}

}
