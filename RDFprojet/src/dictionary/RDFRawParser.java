package dictionary;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.RDFHandlerBase;

public final class RDFRawParser {
	
	private static 	TreeMap <String, Integer > dico = new TreeMap<>();
	private static HashMap<Integer, String> base = new HashMap<>();
	
	//On recherchera toujours le sujet donc il est plus performant de le garder en fin pour une meilleure performance
	private static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> pos =  new HashMap<>();
	private static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> ops =  new HashMap<>();

		
	private static class RDFListener extends RDFHandlerBase {

		public ArrayList<String > subjects = new ArrayList<>();
		public ArrayList<String > predicates = new ArrayList<>();
		public ArrayList<String > objects = new ArrayList<>();
		static Integer nbTriple = 0;
		
		@Override
		public void handleStatement(Statement st) {

			
			subjects.add(st.getSubject().toString());
			predicates.add(st.getPredicate().toString());
			objects.add(st.getObject().toString().replaceAll("\"", ""));						
			nbTriple++;
		}

	};

	public static void main(String args[]) throws IOException {

		
		Reader reader = new FileReader(
				"../RDFprojet/100K.rdfxml");

		org.openrdf.rio.RDFParser rdfParser = Rio
				.createParser(RDFFormat.RDFXML);
		
		RDFListener list = new RDFListener();
		rdfParser.setRDFHandler(list);
		
		try {rdfParser.parse(reader, "");} catch (Exception e) {}
		
		try {reader.close();} catch (IOException e) {}
		
		init(list);

		
		/*
		for(int x=0; x<200; x++) {
			System.out.println("------");
			System.out.println("Predicate : ["+list.predicates.get(x).toString() + "]");
			System.out.println("Object : ["+list.objects.get(x).toString() + "]");
			System.out.println("Subject : ["+list.subjects.get(x).toString()+ "]");

		} */
		
		//displayDico();
		
		System.out.println("Result : " + query("http://db.uwaterloo.ca/~galuc/wsdbm/userId", "9279708" ).toString());

	}
	
	public static void init( RDFListener list){
		
		TreeSet<String> listEachSPO = new TreeSet<>();
		
		
		// Populate TreeSet who sort and avoid doublons
		int i=0;
		while(i<list.nbTriple){
			
			listEachSPO.add(list.subjects.get(i));
			listEachSPO.add(list.predicates.get(i));
			listEachSPO.add(list.objects.get(i));			
			
			i++;
		}
		
		// Populate dico & base
		int cmp=0;
		for(String value : listEachSPO){
			dico.put(value, cmp);
			base.put(cmp, value);
			cmp++;
			
			//System.out.println("<"+value+" , "+cmp+">");
		}
		
		
		// Create Index ops and pos
		int j=0;
		while(j<list.nbTriple){
			
			int o = dico.get(list.objects.get(j));
			int p = dico.get(list.predicates.get(j));
			int s = dico.get(list.subjects.get(j));
			
			ops.putIfAbsent(o, new HashMap<Integer, ArrayList<Integer> >() ) ;
			HashMap<Integer, ArrayList<Integer>> hashOps = ops.get(o); 
			hashOps.putIfAbsent(p, new ArrayList<Integer>());
			hashOps.get(p).add(s);
			
			pos.putIfAbsent(p, new HashMap<Integer, ArrayList<Integer> >() ) ;
			HashMap<Integer, ArrayList<Integer>> hashPos = pos.get(p); 
			hashPos.putIfAbsent(o, new ArrayList<Integer>());
			hashPos.get(o).add(s);
			
			j++;
		}
		
		//System.out.println(pos.size());
		//System.out.println(ops.size());
		//System.out.println(pos.size());
	
		//System.out.println(ops.toString());

	}

	public static ArrayList<String> query( String predicate, String object) {
		
		int Ipredicate = dico.get(predicate);
		int Iobject = dico.get(object);
		
		ArrayList<Integer> Isubject = pos.get(Ipredicate).get(Iobject);
		ArrayList<String> subject = new ArrayList<>();
		
		
		for(Integer s: Isubject) {
			subject.add(base.get(s));
		}
		
		
		return subject;
		
	}
	
	public static void displayDico() throws IOException {
		String aggFileName = "agg-"+String.valueOf("06.txt");
		FileWriter fstream = new FileWriter(aggFileName);
		BufferedWriter out = new BufferedWriter(fstream);
		
		for (Map.Entry<String, Integer> entree : dico.entrySet()) {
			System.out.println("Clé : "+entree.getKey()+" Valeur : "+entree.getValue());
		    out.write("Key "+entree.getKey() + "\t" + "Value" + entree.getValue() + "\n");
		    System.out.println("Done");
		    out.flush();

		}
	}
}