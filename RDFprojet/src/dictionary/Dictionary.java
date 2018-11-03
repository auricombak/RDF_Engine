package dictionary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class Dictionary{
	
	//Dictionary with objects, predicates and subject sorted | TreeMap<Key,Value> -> <String,Integer>
	TreeMap < String, Integer > dico;
	
	//Base with objects, predicates and subject, for retrieve value in fast access | HashMap<Key,Value> -> <Integer,Value>
	HashMap<Integer, String> base;
	
	//Constructor
	public Dictionary(RDFListener list) {
		
		dico =  new TreeMap<>();
		base = new HashMap<>();
		
		TreeSet<String> listEachSPO = new TreeSet<>();
		
		
		// Populate TreeSet who sort and avoid doublons
		int i=0;
		while(i<list.getNbTriple()){
			
			listEachSPO.add(list.getSubjects().get(i));
			listEachSPO.add(list.getPredicates().get(i));
			listEachSPO.add(list.getObjects().get(i));			

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
	}

	//Getter Dico
	public TreeMap<String, Integer> getDico() {
		return dico;
	}

	//Setter Dico
	public void setDico(TreeMap<String, Integer> dico) {
		this.dico = dico;
	}

	//Getter base
	public HashMap<Integer, String> getBase() {
		return base;
	}

	//Setter base
	public void setBase(HashMap<Integer, String> base) {
		this.base = base;
	}

	//Write the content of the dico -> dico.txt
	public void writeDico() throws IOException {
		String aggFileName = String.valueOf("dico.txt");
		FileWriter fstream = new FileWriter(aggFileName);
		BufferedWriter out = new BufferedWriter(fstream);
		
		for (Map.Entry<String, Integer> entree : dico.entrySet()) {
		    out.write("Key-["+entree.getKey() + "]" + " | Value-[" + entree.getValue() + "]\n");
		    out.flush();

		}
	}
	
	//Write the content of the base -> base.txt
	public void writeBase() throws IOException {
		String aggFileName = String.valueOf("base.txt");
		FileWriter fstream = new FileWriter(aggFileName);
		BufferedWriter out = new BufferedWriter(fstream);
		
		for (Entry<Integer, String> entree : base.entrySet()) {
		    out.write("Key-["+entree.getKey() + "]" + " | Value-[" + entree.getValue() + "]\n");
		    out.flush();

		}
	}
	
}