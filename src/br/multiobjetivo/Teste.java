package br.multiobjetivo;

import java.util.ArrayList;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List <Integer> l = new ArrayList<>();
		
		for (int i=1;i<=5;i++) {
			l.add(i);
		
		}
		
		System.out.println("lista iniciada "+l);
		
		l.set(3, 7);
		System.out.println("lista alterada com set "+l);
		l.set(3, 4);
		System.out.println("lista desalterada com set "+l);
		
		System.out.println("modo que esta sendo usado");
		
		l.remove(3);
		System.out.println("lista com elemento removido "+l);
		l.add(3,7);
		
		System.out.println("lista com elemento alterado inserido "+l);
		
		l.remove(3);
		System.out.println("lista com elemento removido "+l);
		l.add(3,4);
		System.out.println("lista restaurada "+l);

	}

}
