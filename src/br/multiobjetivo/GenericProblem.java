package br.multiobjetivo;

import java.io.Serializable;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.multiobjetivo.SearchForNetworkAndEvaluate;

public class GenericProblem implements Serializable {
	private SearchForNetworkAndEvaluate network;
	private UUID id;

	public GenericProblem(@JsonProperty("network")SearchForNetworkAndEvaluate network) {
		super();
		this.network = network;
		this.id = UUID.randomUUID();
	}


	public SearchForNetworkAndEvaluate getNetwork() {
		return network;
	}

	public void setNetwork(SearchForNetworkAndEvaluate network) {
		this.network = network;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

}
