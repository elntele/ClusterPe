package br.multiobjetivo;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;


public class CountTimeExecution {

	
	public static void main(String[] args) throws IOException {

		CountTimeExecution.StudyTimeExecution nestedObject = new CountTimeExecution.StudyTimeExecution();

	}
	
	
	 public static class   StudyTimeExecution{
		 private String lineWithTimeConcat="";
		
		public StudyTimeExecution() throws IOException{
			scratchFile();
		}
		
		
		public void scratchFile() throws IOException {
			
			for (int j = 1; j <=30 ; j++) {
				//PE
				//String  path = "D:\\resultados\\eleitos\\localSearchTestingAllAndDontStopUntilArriveInFInalevenFindAFirstDominator + sinc\\nInd8\\it20/execução "+ j + "/print.txt";
				//String  path = "D:\\novos testes encontrar abordagem para media net\\variando canais\\PE\\busca em todos pos bug do jmetal/execução "+ j + "/print.txt";
				//mediaNet
				String  path = "D:\\resultados\\MediaNet\\busca em todos/execução "+ j + "/print.txt";
				//String  path = "D:\\resultados\\MediaNet\\Elite/execução "+ j + "/print.txt";
				try (Stream<String> stream = Files.lines(Paths.get(path), Charset.forName("windows-1252"))){
					
					stream.forEach(line-> separateLineWithInformationTime(line));
				}
			}
			
			splitString();
		}
		
		
		public void separateLineWithInformationTime(String line) {
			if (line.contains("Total execution time:")) {
				this.lineWithTimeConcat+=line;
			}
		}
		
		public void splitString() {
			String[] array = this.lineWithTimeConcat.split("ms");
			List <String> timesInMilisecondsList = new ArrayList<>();
			//List <String> l2 = new ArrayList<>();
			List <String> timesInHourMinuteSeconds = new ArrayList<>();
			for (int i =0; i<array.length;i++) {
				String[] arrayS=array[i].split(" ");
				String str= arrayS[3];
				timesInMilisecondsList.add(str);
			}
			Double soma= 0.0;
			List <Double> listaDouble = new ArrayList<>();
			for (String s: timesInMilisecondsList) {
				Duration durationOfEachTimeInHourMinuteSecond = Duration.ofMillis(Integer.parseInt(s));
				timesInHourMinuteSeconds.add(durationOfEachTimeInHourMinuteSecond.toString());
				soma+=Double.parseDouble(s);
				listaDouble.add(Double.parseDouble(s));
				
			}
			
			Double[] arrayForTimesInDoubleType = new Double[timesInMilisecondsList.size()];
			listaDouble.toArray(arrayForTimesInDoubleType);
			desvioPadrao(arrayForTimesInDoubleType);
			
			Double maxValue= Double.MIN_VALUE;
			Double minValue= Double.MAX_VALUE;
			
			for (String s: timesInMilisecondsList) {
				if (Double.parseDouble(s)>maxValue) maxValue=Double.parseDouble(s);
				if (Double.parseDouble(s)<minValue) minValue=Double.parseDouble(s);
			}

			Double media=soma/30;
			Duration durationAverageInHourMinuteSecond = Duration.ofMillis(media.intValue());
			
			Duration durationBiggerTimeExecution = Duration.ofMillis(maxValue.intValue());
			Duration durationMinTimeExecution = Duration.ofMillis(minValue.intValue());
			
			
			System.out.println("média da duração em HH MM SS: "+durationAverageInHourMinuteSecond);
			System.out.println("Lista de tempos em milissegundos"+timesInMilisecondsList);
			System.out.println("Lista de durações em HH MM SS: " +timesInHourMinuteSeconds);
			System.out.println("Maior tempo de execução: "+durationBiggerTimeExecution);
			System.out.println("Menor tempo de execução: "+durationMinTimeExecution);
		}
		
		
		public void desvioPadrao(Double[] array){
			double[] newArray= new double[array.length];
			for (int i=0;i<array.length;i++){
				newArray[i]=array[i];
			}
			
			StandardDeviation sd= new StandardDeviation();
			Double dv = sd.evaluate(newArray);
			// get the sum of array
		    double sum = 0.0;
		    for (double i : array) {
		        sum += i;
		    }

		    // get the mean of array
		    int length = array.length;
		    double mean = sum / length;

		    // calculate the standard deviation
		    Double standardDeviation = 0.0;
		    for (double num : array) {
		        standardDeviation += Math.pow(num - mean, 2);
		    }
		    
		    standardDeviation= Math.sqrt(standardDeviation / length);
		    
		    Duration standardDeviationInHourMinuteSecond = Duration.ofMillis(standardDeviation.intValue());
		    Duration standardDeviationInHourMinuteSecondApache = Duration.ofMillis(dv.intValue());
		    
		    
		    	System.out.println("desvio padrão em HH MM SS: "+ standardDeviationInHourMinuteSecond);	
		    	System.out.println("desvio padrão apache em HH MM SS: "+ standardDeviationInHourMinuteSecondApache);		
		}
		
	}
	
	
}
