import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SpreadSheet {
	
	// Criando o arquivo e uma planilha chamada "Silhouette"
	
	private List <List <Double>> clusters = new ArrayList();
	private static final String fileName = "C:/Users/jorge/workspace/ClusterPe/src/tabela.xls";
		
	public SpreadSheet(List<List<Double>> clusters) {
		super();
		this.clusters = clusters;
		createSpreadSheet();
	}
	
	
	public void createSpreadSheet(){
		HSSFWorkbook workTable = new HSSFWorkbook();
		HSSFSheet sheet = workTable.createSheet("Silhouette");
		int rownum = 0;
		int count=0;
        for ( List L : this.clusters) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            if (count==0){
            	Cell numClus = row.createCell(cellnum++);
                numClus.setCellValue("Silhouette");
                Cell Silhouette = row.createCell(cellnum++);
                Silhouette.setCellValue("nº de clusters");
                Row row2 = sheet.createRow(rownum++);
                numClus = row2.createCell(cellnum-2);
                numClus.setCellValue((double)L.get(0));
                Silhouette = row2.createCell(cellnum-1);
                Silhouette.setCellValue((double)L.get(1));
                
            }else{
            Cell numClus = row.createCell(cellnum++);
            numClus.setCellValue((double)L.get(0));
            Cell Silhouette = row.createCell(cellnum++);
            Silhouette.setCellValue((double)L.get(1));
            }
            count++;
        }
         
        try {
            FileOutputStream out = 
                    new FileOutputStream(new File(SpreadSheet.fileName));
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
	
	
	
	

}
