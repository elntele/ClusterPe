package br.multiobjetivo;

import java.util.ArrayList;
import java.util.List;

import br.cns.model.GmlEdge;

public class BooleanAndEdge {
	private List<GmlEdge> edges = new ArrayList<>();
	private boolean have;
	public List<GmlEdge> getEdges() {
		return edges;
	}
	public void setEdges(List<GmlEdge> edges) {
		this.edges = edges;
	}
	public boolean isHave() {
		return have;
	}
	public void setHave(boolean have) {
		this.have = have;
	}
	public BooleanAndEdge() {
		super();
	}
	

}
