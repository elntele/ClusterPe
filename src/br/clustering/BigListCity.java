package br.clustering;
/**
 * caiu em desuso, apesar de que estava funcionando, o professor mandou
 * usar outra classe 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BigListCity {
	private List <City> bigListCity;
	
	public void arrayToListCity(List<String> copy){
		 List <City> listCity= new ArrayList<City>();
		 int counter=0;
	   	 for (String S:copy){
	   		 if (S.equals("node")){
	   			 City city = new City();
	   			 int litleCounter=counter+3;
	   			 city.setId(Integer.parseInt(copy.get(litleCounter)));
	   			 String name="";
	   			 litleCounter+=2;
	   			 while (!copy.get(litleCounter).equals("PE"+'"')){
	   				 name=name+copy.get(litleCounter)+ " ";
	   				 litleCounter+=1;
	   			  }
	   			  name+=copy.get(litleCounter);
	   			  city.setName(name);
	   			  litleCounter+=4;
	   			  city.setLongitude(Double.parseDouble(copy.get(litleCounter)));
	   			  litleCounter+=2;
	   			  city.setLatitude(Double.parseDouble(copy.get(litleCounter)));
	   			  litleCounter+=4;
	   			  city.setPopulation(Integer.parseInt(copy.get(litleCounter)));
	   			  litleCounter+=2;
	 			  city.setIDH(Double.parseDouble(copy.get(litleCounter)));
	 			  litleCounter+=2;
				  city.setGini(Double.parseDouble(copy.get(litleCounter)));
				  litleCounter+=2;
	 			  city.setPIB(Double.parseDouble(copy.get(litleCounter)));
	 			  listCity.add(city);
	   		  }
	   		  
	   		  
	   		counter+=1;
	   	 }
	   	  
//	   	 for (City c: listCity){
//	   		  System.out.println("id: "+ c.getId());
//	   		  System.out.println("nome: "+ c.getName());
//	   		  System.out.println("Latitude: "+ c.getLatitude());
//	   		  System.out.println("Logitude: "+ c.getLongitude());
//	   		  System.out.println("populacao: "+ c.getPopulation());
//	   		  System.out.println("idh: "+ c.getIDH());
//	   		  System.out.println("Gini: "+ c.getGini());
//	   		  System.out.println("PIB: "+ c.getPIB());
//	   	  }

		 
	this.bigListCity=listCity;	
	}
	
	public void stringToArrray(String text){
		String ArrayString []=text.split(" ");
	   	List <String> copy = new ArrayList<>();
	   	List <String> StringList = new ArrayList<>();
	    for (int i=0;i<ArrayString.length;i++){
	    	StringList.add(ArrayString[i]);
	   	  	}
	    
	    for (String S: StringList){
	    	if (!S.equals("")) copy.add(S);
	    	}
	    arrayToListCity(copy);
	}
	
	
	public void takeTextFromArchive(String patch){
		try{
//			BufferedReader br = new BufferedReader(new FileReader("C:/Users/jorge/workspace/CluesterPe/src/DadosProjetoTec.Redes_UFRPE_pe.gml"));
			BufferedReader br = new BufferedReader(new FileReader(patch));
		    String line="";
		    while(br.ready()){
		    	line +=  br.readLine();
		        }
		    br.close();
		    stringToArrray(line);
		}catch(IOException ioe){
	       ioe.printStackTrace();
	    }
	}

	public List<City> getBigListCity() {
		return bigListCity;
	}
	
	public BigListCity(String patch){
		takeTextFromArchive(patch);
	}
}
