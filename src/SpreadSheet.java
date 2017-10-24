import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.MinaMaxa;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SpreadSheet {
	
	// Criando o arquivo e uma planilha chamada "Silhouette"
	
	private List <List <Double>> clusters = new ArrayList();
	private static final String fileNameCsC = "C:/Users/jorge/workspace/ClusterPe/src/cluster_silhouette_cidade .xls";
	private static final String fileNameIte = "C:/Users/jorge/workspace/ClusterPe/src/Iteratio_MaxMim .xls";
	private static final String fileNameMiMaAveDis = "C:/Users/jorge/workspace/ClusterPe/src/distanciaDeCentroids .xls";
	private String clusterString[];
	public SpreadSheet(List<List<Double>> clusters, List <String> list) {
		super();
		this.clusters = clusters;
		String clusString[] =new String[list.size()];
		int i=0;
		for (String s:list){
			clusString[i]=s;
			i+=1;
			
		} 
		this.clusterString=clusString;
		createSpreadSheet();

	}
	
//	public SpreadSheet(HashMap <Integer, List<Integer>> mapIteration ) {
//		super();
//		createSpreedSheetItereation();
//	}


	
	public void createSpreedSheetMMAverangeDistance(List<List<Double>> mimMaxAverange ){
		HSSFWorkbook workTable = new HSSFWorkbook();
		HSSFSheet sheet = workTable.createSheet("minMaxAverangeDistance");
		int rownum = 0;
		int count=0;
		int j=4;
		for (List L:mimMaxAverange ){
        	Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            if (count==0){
            	Cell kNumber = row.createCell(cellnum++);
            	kNumber.setCellValue("K");
                Cell distanceMax = row.createCell(cellnum++);
                distanceMax.setCellValue("max Distance");
                Cell distanceMin =row.createCell(cellnum++);
                distanceMin.setCellValue("min Distance");
                Cell distanceAverange =row.createCell(cellnum++);
                distanceAverange.setCellValue("Averange distance");
                Row row2 = sheet.createRow(rownum++);
                kNumber = row2.createCell(cellnum-4);
                kNumber.setCellValue(j);
                distanceMax = row2.createCell(cellnum-3);
                distanceMax.setCellValue((double)L.get(0));
                distanceMin = row2.createCell(cellnum-2);
                distanceMin.setCellValue((double)L.get(1));
                distanceAverange = row2.createCell(cellnum-1);
                distanceAverange.setCellValue((double)L.get(2));
            }else{
               	Cell kNumber = row.createCell(cellnum++);
            	kNumber.setCellValue(j);
                Cell distanceMax = row.createCell(cellnum++);
                distanceMax.setCellValue((double)L.get(0));
                Cell distanceMin =row.createCell(cellnum++);
                distanceMin.setCellValue((double)L.get(1));
                Cell distanceAverange =row.createCell(cellnum++);
                distanceAverange.setCellValue((double)L.get(2));
            }
            count++;
            j+=1;
        }
         
        try {
            FileOutputStream out = 
                    new FileOutputStream(new File(SpreadSheet.fileNameMiMaAveDis));
            workTable.write(out);
            out.close();
            System.out.println("tabela de distancias criado com sucesso!");
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
               System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
               System.out.println("Erro na edição do arquivo!");
        }

		
	
	}	

	
	
	
	public void createSpreadSheet(){
		HSSFWorkbook workTable = new HSSFWorkbook();
		HSSFSheet sheet = workTable.createSheet("Silhouette");
		int rownum = 0;
		int count=0;
		int clusterCount=0;
        for ( List L : this.clusters) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            if (count==0){
            	Cell numClus = row.createCell(cellnum++);
                numClus.setCellValue("Silhouette");
                Cell silhouette = row.createCell(cellnum++);
                silhouette.setCellValue("nº de clusters");
                Cell clusterGroup =row.createCell(cellnum++);
                clusterGroup.setCellValue("Cluster");
                Row row2 = sheet.createRow(rownum++);
                numClus = row2.createCell(cellnum-3);
                numClus.setCellValue((double)L.get(0));
                silhouette = row2.createCell(cellnum-2);
                silhouette.setCellValue((double)L.get(1));
                clusterGroup= row2.createCell(cellnum-1);
                clusterGroup.setCellValue(this.clusterString[clusterCount]);
                clusterCount+=1;
            }else{
            Cell numClus = row.createCell(cellnum++);
            numClus.setCellValue((double)L.get(0));
            Cell silhouette = row.createCell(cellnum++);
            silhouette.setCellValue((double)L.get(1));
            Cell clusterGroup =row.createCell(cellnum++);
            clusterGroup.setCellValue(this.clusterString[clusterCount]);
            clusterCount+=1;	
            }
            count++;
        }
         
        try {
            FileOutputStream out = 
                    new FileOutputStream(new File(SpreadSheet.fileNameCsC));
            workTable.write(out);
            out.close();
            System.out.println("Arquivo Excel criado com sucesso!");
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
               System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
               System.out.println("Erro na edição do arquivo!");
        }
	}
	public void setClusters(List<List<Double>> clusters) {
		this.clusters = clusters;
	}
	
	public void createSpreedSheetItereation(HashMap <Integer, List<Integer>> mapIteration){
		HSSFWorkbook workTable = new HSSFWorkbook();
		HSSFSheet sheet = workTable.createSheet("iteration");
		int rownum = 0;
		int count=0;
		for (int j=4;j<=20;j++){
        	Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            if (count==0){
            	Cell kNumber = row.createCell(cellnum++);
            	kNumber.setCellValue("K");
                Cell iterationMax = row.createCell(cellnum++);
                iterationMax.setCellValue("min iteration");
                Cell iterationMin =row.createCell(cellnum++);
                iterationMin.setCellValue("max Interation");
                Row row2 = sheet.createRow(rownum++);
                kNumber = row2.createCell(cellnum-3);
                kNumber.setCellValue(j);
                iterationMax = row2.createCell(cellnum-2);
                iterationMax.setCellValue((double)mapIteration.get(j).get(0));
                iterationMin = row2.createCell(cellnum-1);
                iterationMin.setCellValue((double)mapIteration.get(j).get(mapIteration.get(j).size()-1));
            }else{
               	Cell kNumber = row.createCell(cellnum++);
            	kNumber.setCellValue(j);
                Cell iterationMax = row.createCell(cellnum++);
                iterationMax.setCellValue((double)mapIteration.get(j).get(0));
                Cell iterationMin =row.createCell(cellnum++);
                iterationMin.setCellValue((double)mapIteration.get(j).get(mapIteration.get(j).size()-1));
            }
            count++;
        }
         
        try {
            FileOutputStream out = 
                    new FileOutputStream(new File(SpreadSheet.fileNameIte));
            workTable.write(out);
            out.close();
            System.out.println("tabela de iterações criado com sucesso!");
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
               System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
               System.out.println("Erro na edição do arquivo!");
        }

		
	
	}	
	
	
	

}
