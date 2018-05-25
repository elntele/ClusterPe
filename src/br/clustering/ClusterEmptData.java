package br.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cbic15.Pattern;

public class ClusterEmptData {
	HashMap<Integer, List<Integer>> kAndexecution = new HashMap<Integer, List<Integer>>();

	public ClusterEmptData() {
	}

	public HashMap<Integer, List<Integer>> getkAndexecution() {
		return kAndexecution;
	}

//	public void setkAndexecution(List<Pattern>[] cluster, int execution) {
//		List<Integer> localList = new ArrayList<>();
//		localList.add(execution);
//		for (int i = 0; i < cluster.length; i++) {
//			if (cluster[i].size() == 0) {
//				localList.add(i);
//			}
//		}
//
//		this.kAndexecution.put(cluster.length, localList);
//	}
	
	/**
	 * k na primeira posição da lista e execução na segunda
	 * @param cluster
	 * @param execution
	 */
	public void setkAndexecution(List<Pattern>[] cluster, int execution) {
		List<Integer> localList = new ArrayList<>();
		localList.add(cluster.length);
		localList.add(execution);

		this.kAndexecution.put(cluster.length, localList);
	}


}
