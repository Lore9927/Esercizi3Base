package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import entities.Articolo;

public class Main 
{

	public static void main(String[] args) 
	{
		Map<String,Articolo> articoli = new LinkedHashMap<>();
		String codice;
		String descrizione;
		int quantita;
		String risposta = "";
		Scanner tastiera = new Scanner(System.in);
		String[] rigaFile;
		Scanner file = null;
		boolean trovato = false;
		try 
		{
			file = new Scanner(new File("src/res/Articoli.csv"));	
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		while(file.hasNextLine())
		{
			rigaFile = file.nextLine().split(";");
			Articolo a = new Articolo();
			a.setId(Integer.parseInt(rigaFile[0]));
			a.setCodice(rigaFile[1]);
			a.setDescrizione(rigaFile[2]);
			a.setQuantita(Integer.parseInt(rigaFile[3]));
			
			articoli.put(a.getCodice(), a);
		}
		file.close();
		
		for(Articolo a : articoli.values())
			System.out.println("Id:" 			+ a.getId()				+ "\n" + 
					   		   "Codice: " 		+ a.getCodice()			+ "\n" +
					   		   "Descrizione: " 	+ a.getDescrizione()	+ "\n" +
					   		   "Quantità: " 	+ a.getQuantita() 		+ "\n");
		
		System.out.println("Vuoi aggiungere un nuovo articolo?\ns/n");
		risposta = tastiera.nextLine();
		
		if(risposta.equalsIgnoreCase("s"))
		{
			System.out.println("Inserisci il codice del nuovo articolo ");
			codice = tastiera.nextLine();

			System.out.println("Inserisci la descrizione del nuovo articolo ");
			descrizione = tastiera.nextLine();

			System.out.println("Inserisci la quantità del nuovo articolo " );
			quantita = Integer.parseInt(tastiera.nextLine());

			Articolo a = new Articolo();
			a.setId(articoli.size()+1);
			a.setCodice(codice);
			a.setDescrizione(descrizione);
			a.setQuantita(quantita);
			articoli.put(a.getCodice(),a);
			
			try (PrintWriter writer = new PrintWriter(new File("src/res/nuovi-articoli.csv"))) 
			{
				StringBuilder sb = new StringBuilder();
				for(Articolo art : articoli.values())
				{
					sb.append(art.getId());
					sb.append(';');
					sb.append(art.getCodice());
					sb.append(';');
					sb.append(art.getDescrizione());
					sb.append(';');
					sb.append(art.getQuantita());
					sb.append('\n');
				}
				writer.write(sb.toString());
				System.out.println("Scrittura file completata");

			} 
			catch (FileNotFoundException e) 
			{
				System.out.println(e.getMessage());
			}
		}
			System.out.println("Vuoi stampare un articolo in particolare?\ns/n");
			risposta = tastiera.nextLine();
			System.out.println("Inserisci il codice dell'articolo");
			risposta = tastiera.nextLine();
			
			for(Articolo a : articoli.values())
			{
				if(a.getCodice().equalsIgnoreCase(risposta))
				{
					System.out.println("Id:" 			+ a.getId()				+ "\n" + 
					   		   		   "Codice: " 		+ a.getCodice()			+ "\n" +
					   		   		   "Descrizione: " 	+ a.getDescrizione()	+ "\n" +
					   		   		   "Quantità: " 	+ a.getQuantita() 		+ "\n");
					trovato = true;
				}
			}
			if(!trovato)
				System.out.println("Articolo non presente");
			tastiera.close();
		
	}

}
