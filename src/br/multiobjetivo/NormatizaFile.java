package br.multiobjetivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NormatizaFile {

	public static void main(String[] args) {
			StringBuffer sb = new StringBuffer();

			TakeTheHyperVolume takeHyper = new TakeTheHyperVolume();
			try {
				for (int i = 1; i <= 11; i++) {
					List<List<String>> groupStringList = new ArrayList<>();
					List<List<Double>> groupDorbleList = new ArrayList<>();

					String pathCbusca = "C:/Users/jorge/workspace/CluesterPe/src/cbusca/";
					String pathSbusca = "C:/Users/jorge/workspace/ClusterPe/src/sbusca/";
					BufferedReader br = new BufferedReader(new FileReader(pathSbusca + "FUN" + i + ".TSV"));
					while (br.ready()) {
						List<String> stringList = new ArrayList<>();
						String line = "";
						line += br.readLine();
						String ArrayString[] = line.split(" ");
						for (int k = 0; k < ArrayString.length; k++) {
							stringList.add(ArrayString[k]);
						}
						groupStringList.add(stringList);
					}
					for (List L : groupStringList) {
						List<Double> doubleList = new ArrayList<>();
						int w=0;
						for (Object S : L) {
							switch (w){
								case  0:
									doubleList.add(Double.parseDouble((String) S));
									w+=1;
									break;
								case 1:
									doubleList.add(Double.parseDouble((String) S)/29340);
									w+=1;
									break;
								case 2:
									doubleList.add(Double.parseDouble((String) S)/3795187.303);
									w+=1;
									break;
								case 3:
									doubleList.add(Double.parseDouble((String) S));
									w+=1;
									break;
								default: 
									break;
							}
						}
						groupDorbleList.add(doubleList);
					}
					takeHyper.makeSolution(groupDorbleList);
					
					br.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			takeHyper.hyperVolume();
			List<Double>listaHv=takeHyper.getHyperVolumePopulatios();
			for(Double d:listaHv){
				System.out.println("hipervolume "+d);
				
			}

		}

}
