//package br.multiobjetivo;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Random;
//
//import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
//
////import sisaJmetalbeans.Aluno;
//import sisaJmetalbeans.Aluno;
//import sisaJmetalbeans.Disciplina;
//
//public class PreparacaoDoProblema {
//	private Aluno aluno;
////	private List <Float> varianciaDoPeriodo=new ArrayList<Float>();
//	private List<Disciplina> naoPagas=new  ArrayList <Disciplina>();
//	private float variaQtdDiscPorPeriodo;
////	DisciplinaDAO disciplinaDAO = new DisciplinaDAO();	
////	List<Disciplina> disciplinas=disciplinaDAO.getDisciplinas();
//	List<Disciplina> disciplinas;//depois tem que apagar e decomentar as 2 de cima
//	private int[] sugestaoMat;
//	private int AreaDePreferencia;
//	private int numberOfObjetives=5;
//	private int periodosRestantes;// quantos periodos antes de atingir o tempo maximo de curso
//	private int QtdDiscplinasParaConcluir;//qtd de disciplinas para cocluir o curso
//	private int qtdDiscForaDaMinhaArea;
//	private Float varianciaTotal;
//	private int tempoDeFormatura;// qtd de periodos, de acordo com a sugestão, para o aluno se formar, não é o tempo total antes da jubilação
//	private float verificaAcompanhada;
//	private float tempoExtraClasse;
//	
////	public void testaDiahora(){
////		for (Disciplina D:this.disciplinas){
////			
////			System.out.println( D.getNome()+"[segundas "+D.getDiaHora()[0]+"terças "+D.getDiaHora()[1]+"quartas"
////			+D.getDiaHora()[2]+"quintas "+D.getDiaHora()[3]+"sextas"+D.getDiaHora()[4]+"]");
////	
////			System.out.println("["+D.getDiaHora()[0]+" "+ ""+" "+D.getDiaHora()[1]+" "+ ""+D.getDiaHora()[2]+""
////				+ " "+ ""+D.getDiaHora()[3]+" "+ ""+D.getDiaHora()[4]+"]");
////		}
////	}
//	
//	/**
//	 * 
//	 * metodo auxiliar para o metodo tempo de 
//	 * fim de curso, retorna ano atual
//	 */
//	
//	public int calendario(){
//		Calendar cal = Calendar.getInstance();
//        int ano = cal.get(Calendar.YEAR) ;
//		return ano;
//	}
//		
//	/**
//	 *  seta a variavel periodosRestantes com o tempo que o aluno 
//	 *  ainda tem ate completar 16 periodos que é o maximo de perodos
//	 *  do curso.
//	 */	
//	public void tempoDeFimDecurso (){
//		int periodosGastos = ( (calendario()-this.aluno.getAnoIngresso()) *2);	
//		periodosGastos-=this.aluno.getQtdPeriTranc();
//		if (aluno.getSemestreIngresso()==2){
//			periodosGastos-=1;
//			}	
//		this.periodosRestantes=16-periodosGastos;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//
//////	/**
//////	 * simula a lista de displicinas que o aluno já pagou mas sera
//////	 * abandonado porque isso será recuperado do cadastro do aluno no banco
//////	 */
//////	//disciplinas pagas
////	public void montaListaDisciplinasPagas(){
////		int i=1;
////		List<Disciplina> transfer=new ArrayList();
////		for (Disciplina D:this.aluno.getDiscPagas() ){System.out.println("disciplina paga antes id: "+D.getId());}
////		for (Disciplina D:this.aluno.getDiscPagas() ){
////			 for (Disciplina Di:this.disciplinas ){
////				if (D.getId()==Di.getId())  transfer.add(Di);
////			 }
////		}
////		aluno.setDiscPagas(transfer);
////		
//////		for (Disciplina d:disciplinas){
//////
//////					if (
//////					d.getId()!=16&&d.getId()!=17
//////					&&d.getId()!=18&&d.getId()!=19&&d.getId()!=20
//////					&&d.getId()!=21&&d.getId()!=22&&d.getId()!=23
//////					&&d.getId()!=24&&d.getId()!=25)// aluno5	
//////
//////				
//////			{
//////				transfer.add(d);
//////				}
//////			i+=1;
//////			if (i==26) break;
//////			}
////		
//////		aluno.setDiscPagas(transfer);
////		for (Disciplina D:this.aluno.getDiscPagas() ){System.out.println("disciplina paga depois id: "+D.getId());}
////		}
//	
////	/**
////	 * metodo simula a lista de disciplinas acompanhadas e reprovadas
////	 *  para teste mas será abandonado porque isso será recuperado do 
////	 *  cadastro do aluno no banco
////	 */
////	
////
//	//disciplina acompanhada e reprovada
//	public void montaListaDisciplinasReprovadas(){
//		List<Disciplina> transfer=new ArrayList();
//		for (Disciplina d:disciplinas){
//
//			if (d.getId()==16&&d.getId()==17&&d.getId()==20&&d.getId()==21){//aluno5
//				transfer.add(d);
//				aluno.setDiscAcompanhada(transfer);
//				aluno.setDiscRepro(transfer);
//			}
//		}
//	}
//	
//	
//	
////	/**
////	 * simula a lista de displicinas que o aluno já pagou mas sera
////	 * abandonado porque isso será recuperado do cadastro do aluno no banco
////	 */
////	//disciplinas pagas
//	public void montaListaDisciplinasPagas(){
//		int i=1;
//		List<Disciplina> transfer=new ArrayList();
//		
//		for (Disciplina d:this.disciplinas){
//
//					if (d.getId()!=7
//					&&d.getId()!=10&&d.getId()!=13&&d.getId()!=14
//					&&d.getId()!=15&&d.getId()!=16&&d.getId()!=17
//					&&d.getId()!=18&&d.getId()!=19&&d.getId()!=20
//					&&d.getId()!=21&&d.getId()!=22&&d.getId()!=23
//					&&d.getId()!=24&&d.getId()!=25)// aluna alana	
//			{
//				transfer.add(d);
//				}
//			i+=1;
//			if (i==26) break;
//			}
//		
//		aluno.setDiscPagas(transfer);
//		for (Disciplina D:this.aluno.getDiscPagas() ){System.out.println("disciplina pagas por alanaid: "+D.getId());}
//		}
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	/**
//	 * seta a variavel QtdDiscplinasParaConcluir com o numero de disciplinas
//	 * que faltam para o aluno concluir o curso
//	 */
//	public void montaQtdDiscplinasParaConcluir(){
//		this.QtdDiscplinasParaConcluir=45-aluno.getDiscPagas().size();
//		
//	}
//	
//	/**
//	 * seta a lista nao pagas com a qtd de disciplina que 
//	 * o aluno ainda não pagou, ou seja, sao as disciplinas
//	 * do curso que ele ainda pode ou deve pagar 
//	 */
//	public void montaNaoPagas(){
//		List<Disciplina> faltaPagar=new ArrayList();
//		boolean presenca=false;
//		for( Disciplina D: disciplinas){ // percorre a lista de disciplinas geral
//			for (Disciplina Da:this.aluno.getDiscPagas()){ //percorre a lista de disciplinas que o aluno ja pagou
//				if (D.getId()==Da.getId()) {
//					presenca=true;
//					break;
//					} 
//			}
//			if (presenca==false) faltaPagar.add(D);
//			presenca=false;
//		}
//		this.naoPagas=faltaPagar;
//		
//	}
//	
//	/**
//	 * método monta uma sugestão para teste, mas será abandonado porque isso 
//	 * será recebido dos metodos de mescla de cromossomos do jmetal
//	 */
//	
//	public void montasugestao(){
//		List<Disciplina> copia=new ArrayList();
//		int temQuePagar=QtdDiscplinasParaConcluir;
//		copia.addAll(naoPagas);
//		int sugestao[]=new int [this.periodosRestantes*8];
//		Random gerador = new Random();		 
//        int numero; 
//        int lugarPraZero=sugestao.length-QtdDiscplinasParaConcluir;
//		for (int i=0;i<sugestao.length;i++){
//			numero=gerador.nextInt(4);
//			if (numero==0&&lugarPraZero!=0){	
//				sugestao[i]=0;
//				lugarPraZero-=1;
//				}else if (copia.size()!=0&&temQuePagar!=0){
//					numero=gerador.nextInt(copia.size());
//					sugestao[i]=copia.get(numero).getId();
//					copia.remove(numero);
//					temQuePagar-=1;
//					}else if (lugarPraZero!=0){
//							sugestao[i]=0;
//							lugarPraZero-=1;
//							}else{
//								continue;
//							}
//				}
//		this.sugestaoMat=sugestao;
//			}
//		
//	/**
//	 * 
//	 * @param id
//	 * @return
//	 * metodo auxiliar para o metodo varianciaDoPeriodo
//	 * retorna o grau de dificuldade de uma cadeira
//	 */
//	public float RetornaGrauDificuldadeDaDisc(int id){
//		Disciplina R=null;
//		for (Disciplina D:this.disciplinas){
//			if (D.getId()==id){
//				R=D;
//				break;
//			}
//		}
//		return R.getGrauDificuldade();
//	}
//	/**
//	 * metodo auxilar para qtdDeDisciplinasForaDeMinhaArea, 
//	 * ele retorna o tipo de area da disciplina:
//	 * 1 -Arq
//	 * 2-Ensiso
//	 * 3-Fc
//	 * @param id
//	 * @return
//	 */
//	public int RetornaArea(int id){
//		Disciplina R=null;
//		int retorno=0;
//		for (Disciplina D:this.naoPagas){
//			if (D.getId()==id){
//				R=D;
//				break;
//			}
//		}
//	if (R!=null){	
//		switch (R.getArea().getNome()){
//		 case "ARQ": retorno=1;
//		 			break;
//		 case "Ensiso":  retorno=2;
//			break;
//		 case "FC":  retorno=3;
//			break;
//		
//		 default: break;
//
//		 }
//	}
//
//		return  retorno;
//	}
//	
//	/**
//	 * calcula a variancia de nivel de dificuldade dos periodos
//	 * e seta a variavel varianciDoPeriodo 
//	 */
//	
//	
//	public void varianciaTotal(){
//		float varia=0;
//		float dificuldade=0;
//		float mediaDoPeriodo=0;
//		int divisorPorPeriodo=0;
//		int controladorDeModulo8=1;
//		for(int i=0;i<this.tempoDeFormatura*8;i++) {// percorre o array de disciplinas para calcular a variancia por periodo			
//			if (this.sugestaoMat[i]!=0){ 
//				divisorPorPeriodo+=1;
//				dificuldade+=RetornaGrauDificuldadeDaDisc(this.sugestaoMat[i]);
//				
//				}
//			
//			if (controladorDeModulo8%8==0){
//				if (divisorPorPeriodo!=0){					
//					mediaDoPeriodo=dificuldade/divisorPorPeriodo;
//					varia += (float) Math.pow( mediaDoPeriodo-2 ,2);
//					divisorPorPeriodo=0;
//					mediaDoPeriodo=0;
//					dificuldade=0;
//				}else{
//					// valor colocado para quando a sugestão vir com
//					// periodos só com 0 sem matricula, muito ruim...
////					mediaDoPeriodo=100;
////					varia += (float) Math.pow( mediaDoPeriodo-2 ,2);
//					divisorPorPeriodo=0;
//					mediaDoPeriodo=0;
//					dificuldade=0;
//				}
//				
//			}
//			controladorDeModulo8+=1;
//		}		
//		
//		
//		this.varianciaTotal=varia/this.tempoDeFormatura;
//	
//	}
//	/**
//	 * calcula a variancia de nivel de dificuldade total setando a variavel variancia total
//	 */
//
////	public void varianciaTotal(){
////		float varia=0;
////		for(int i = 0; i<varianciaDoPeriodo.size();i++){
////			varia += (float) Math.pow( this.varianciaDoPeriodo.get(i) - 2,2);			
////		}
////		if (varianciaDoPeriodo.size()!=0){
////		this.varianciaTotal=(varia/varianciaDoPeriodo.size());
////		}else{
////			varianciaTotal=(float) 0;
////		}
////	}
// /**
//  * verifica se a disciplina acompanhada esta no primeiro
//  * periodo da sugestão setando a variavel float verificaAcompanhada
//  * como os valores entre 0 e 1 (quanto menor melhor).
//  * OBS: ela só a ssume o valor 0 se o aluno só tiver uma disciplina acompanhada 
//  * e ela estiver no primeiro periodo e só assume o valor 1 em caso o contário...
//  * se o aluno tiver mais de uma disciplina acompanhada verificaAcompanhada 
//  * vai assumir valores entre 0.9 e 0 (intervalo aberto)
//  */
//	public void verificaAcompNoPrimeDasug(){
//		float retorno=0;
//		float qtdAcomp=this.aluno.getDiscAcompanhada().size();
//		int divisor=0;
//		if(qtdAcomp==1){
//			for (int i=0;i<8;i++){
//				for (Disciplina D:this.aluno.getDiscAcompanhada()){
//					if (this.sugestaoMat[i]==D.getId()){
//						retorno=0;
//					}else{
//						retorno=1;
//					}
//				}		
//			}			
//		}else{
//			for (int i=0;i<8;i++){
//				for (Disciplina D:this.aluno.getDiscAcompanhada()){
//					if (this.sugestaoMat[i]==D.getId()){
//						divisor+=1;						
//					}
//					if (divisor >0){ 
//						retorno= (float) (0.9/divisor);
//						}else{ 
//							retorno= 1;
//						}
//				}
//			}
//		}
//		this.verificaAcompanhada= retorno;		
//	}
//	/**
//	 * seta a variavel int tempoDeFormatura com a qtd de periodos que
//	 * o aluno vai se forma
//	 */
//	
//	public void contaTempoDeFormatura(){
//		int fimDeCurso=0;
//		int numeroDePeriodos=0;
//		for (int i=0;i<sugestaoMat.length;i++){ 
//			if (sugestaoMat[i]!=0){ // porque a partir de um momento a sugestão começa a ficar tudo zero
//				fimDeCurso=i;
//			}
//		} 
//		this.tempoDeFormatura=fimDeCurso/8;
//		if (fimDeCurso%8>0) this.tempoDeFormatura+=1;// se houver resto adicione um periodo a mais
//	}
//	/**
//	 * seta o inteiro qtdDiscForaDaMinhaArea com a qtd de disciplina fora da area
//	 * de preferência do aluno
//	 */
//	
//	public void qtdDeDisciplinasForaDeMinhaArea(){
//		int contador=0;
//		for (int i=0;i<this.tempoDeFormatura*8;i++ ){
//			if (sugestaoMat[i]!=0){
//				for(Disciplina D:disciplinas){
//					if (D.getId()==sugestaoMat[i]){
//						if (RetornaArea(sugestaoMat[i])!=this.AreaDePreferencia){
//							contador+=1;
//						}
//					}
//				}
//			}	
//		}
//		this.qtdDiscForaDaMinhaArea=contador;
//	}
//	
//	public void montaAredePreferencia(){
////		 switch (this.aluno.getAreaPref()){
////		 case "ARQ": this.AreaDePreferencia=1;
////		 			break;
////		 case "Ensiso": this.AreaDePreferencia=2;
////			break;
////		 case "FC": this.AreaDePreferencia=3;
////			break;
////		 default: break;
//		// gambiarra para anular a falha que aparentemente vem do frontEnd...
//		// As strings de area estão vindo minusculas como no switch case abaixo:
//		// apos sanar o problema delete o switch de baixo e descomente o de cima.
//		switch (this.aluno.getAreaPref()){
//		 case "arq": this.AreaDePreferencia=1;
//		 			break;
//		 case "ensiso": this.AreaDePreferencia=2;
//			break;
//		 case "fc": this.AreaDePreferencia=3;
//			break;
//		 default: break;
//
//		 }
//			
//		}
//	
//	/**
//	 * seta a lista variaQtdDiscPorPeriodo com a variancia
//	 * de quantidade de disciplina em cada periodo relativos
//	 * a media de 5 disciplinas por periodo
//	 * obs.: talvez seja necessario manipular este metodo para que
//	 * no primeiro periodo ou seja i<8 a variancia seja 3...
//	 * dica: Usar varia =(float) Math.pow(cont-3, 2)/8 para i<8
//	 */
//	public void varianciaDeQtdDeDiscPorPeriodo(){
//		int fim  = this.sugestaoMat.length/8 ;
//		int k=0;
//		DescriptiveStatistics cadeiras = new DescriptiveStatistics();
//		for (int i=0; i<fim; i++){ // cada i corresponde a 1 semestre
//			int cont = 0;
//			for (int j=0; j<8; j++){// periodo a periodo vai somando a qtd de disciplina de cada periodo
//				
//				if ((sugestaoMat[j+k])!=0){// k via de 8 em 8
//					cont = cont +1;
//					//System.out.println(sugestao[j+k]);
//					
//				}
//			}
//			k = k+8;
//			if (cont!=0){
////				System.out.println("cont="+cont);	
//			cadeiras.addValue(cont);
//			}
//		}
////		double variancia = cadeiras.getVariance();
//		double variancia =cadeiras.getPopulationVariance();
//		this.variaQtdDiscPorPeriodo= (float) variancia;
//		
////		int modulo8=1;
////		List <Integer> teste=new ArrayList<>();
////		ArrayList<List<Integer>> Listao=new ArrayList <List<Integer>>();
////		for (int i=0;i<this.sugestaoMat.length;i++){
////			teste.add(this.sugestaoMat[i]);
////			if (modulo8%8==0){
////				List <Integer> copia=new ArrayList<>();
////				copia.addAll(teste);
////				Listao.add(copia);
////				teste.removeAll(teste);
////			}
////			modulo8+=1;
////		}
////		for (List L:Listao){
////			System.out.println("periodo: "+L);
////		}
////		System.out.println("variancia: "+this.variaQtdDiscPorPeriodo);
////		
////		int cont=0;
////		int totalDeDisc=0;
////		float varia=0;
////		float media=0;
////		int modulo8=0;
////		for (int i=0; i<this.tempoDeFormatura*8;i++){
////			if (this.sugestaoMat[i]!=0){
////				totalDeDisc+=1;
////			}
////		}
////		
////		try {
////			media=totalDeDisc/tempoDeFormatura;
////			for (int i=0; i<this.tempoDeFormatura*8;i++){
////				if (this.sugestaoMat[i]!=0){
////					cont+=1;
////				}
////				if (modulo8%8==0 && modulo8!=0){
////						varia +=(float) Math.pow(cont-media, 2);
////						cont=0;
////				}
////				modulo8+=1;
////			}
////					} catch (ArithmeticException e) {
////				/**
////				 * tratamento de excessão para o caso de a sugestão
////				 * ser toda zerada e o por isso o tempoDeformatura =0 
////				 */
////						this.variaQtdDiscPorPeriodo=1000; // entrega um valor ruim a este parametro 
////		}
////		
////		this.variaQtdDiscPorPeriodo=varia/tempoDeFormatura;
//	}
//	
//	/**
//	 * Aluno estagia e mora longe 1.
//	 * Aluno estagia e mora perto 2.
//	 * Aluno não estagia e mora perto 3.
//	 * Classe mede o tempo de estudo extra classe e seta a variavel tempoExtraClasse
//	 * com 1,2,3 da seguinte forma:
//	 * intervalo de estudo semanal 			categoria			qtdMaxDeDisciplinas
//	 * 	 	 0-10								1				        3  
//	 * 		11 -15								2				 Entre 4 e 5			
//	 * 		 +15								3				 Entre 5 e 6
//	 * 		obs: o algoritmo so alocará mais que 6 disciplonas por periodo se
//	 * o aluno não tiver tempo de curso para pagar menos que isso.  	
//	 * 
//	 */
//	
//	public void tempoDeEstudoExtraClasse(){
//		float varia=0, varia1=0,varia2=0;
//		int cont=0;
//		int modulo8=0;
//		int tempo=this.aluno.getTempDispExtrCla();			
//				for (int i=0; i<this.tempoDeFormatura*8;i++){
//					if (sugestaoMat[i]!=0){
//						cont+=1;
//					}
//					if (modulo8%8==0 && modulo8!=0){
//							varia +=(float) Math.pow(cont-3, 2);
//							varia1+=(float) Math.pow(cont-4.5, 2);
//							varia2+=(float) Math.pow(cont-5.5, 2);
//							cont=0;
//					}
//					modulo8+=1;
//				}				
//				if (tempo<=10){
//					this.tempoExtraClasse=varia/this.tempoDeFormatura;
//				} else if(tempo>10 &&tempo<=15){
//					this.tempoExtraClasse=varia1/this.tempoDeFormatura;
//				} else if (tempo>15){
//					this.tempoExtraClasse=varia2/this.tempoDeFormatura;
//				}
//				
//			}
//			
//			
//		
//	
//	
//	
///**
// * getters and  any setters 	
// * @return
// */
//
//	
//	public Aluno getAluno() {
//		return aluno;
//	}
//
//	public int[] getSugestaoMat() {
//		return sugestaoMat;
//	}
//
//	public List<Disciplina> getDisciplinas() {
//		return disciplinas;
//	}
//
//	public int getNumberOfObjetives() {
//		return numberOfObjetives;
//	}
//
//	public int getPeriodosRestantes() {
//		return periodosRestantes;
//	}
//
//	public void setPeriodosRestantes(int periodosRestantes) {
//		this.periodosRestantes = periodosRestantes;
//	}
//
//	public int getQtdDiscplinasParaConcluir() {
//		return QtdDiscplinasParaConcluir;
//	}
//
//	public void setQtdDiscplinasParaConcluir(int qtdDiscplinasParaConcluir) {
//		QtdDiscplinasParaConcluir = qtdDiscplinasParaConcluir;
//	}
//
//	public List<Disciplina> getNaoPagas() {
//		return naoPagas;
//	}
//
//	public void setNaoPagas(List<Disciplina> naoPagas) {
//		this.naoPagas = naoPagas;
//	}
//	
//	public int getAreaDePreferencia() {
//		return AreaDePreferencia;
//	}
//	
//	public void setSugestaoMat(int[] sugestaoMat) {
//		this.sugestaoMat = sugestaoMat;
//	}
//		
//
//
//	public float getVariaQtdDiscPorPeriodo() {
//		return variaQtdDiscPorPeriodo;
//	}
//
//
//	public int getQtdDiscForaDaMinhaArea() {
//		return qtdDiscForaDaMinhaArea;
//	}
//
//
//	public Float getVarianciaTotal() {
//		return varianciaTotal;
//	}
//
//
//	public int getTempoDeFormatura() {
//		return tempoDeFormatura;
//	}
//
//
//	public float getVerificaAcompanhada() {
//		return verificaAcompanhada;
//	}
//	
//	
//
//
//	public float getTempoExtraClasse() {
//		return tempoExtraClasse;
//	}
//	
///**
// * contrutor para o jmetal	
// */
//
//	public PreparacaoDoProblema(Aluno aluno, List<Disciplina> listaGeral) {
//		super();
//		this.aluno = aluno;
////		montaListaDisciplinasPagas();
//		//montaListaDisciplinasReprovadas();
//		this.disciplinas = listaGeral;
//		montaNaoPagas();
//		montaAredePreferencia();
//		montaQtdDiscplinasParaConcluir();
//		tempoDeFimDecurso ();
//			
//	}
//
//	
//}