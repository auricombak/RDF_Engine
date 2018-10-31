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
	
	public static void main(String args[]) throws IOException {

		
		Reader reader = new FileReader(
				"../RDFprojet/100K.rdfxml");

		org.openrdf.rio.RDFParser rdfParser = Rio
				.createParser(RDFFormat.RDFXML);
		
		RDFListener list = new RDFListener();
		rdfParser.setRDFHandler(list);
		
		try {rdfParser.parse(reader, "");} catch (Exception e) {}
		
		try {reader.close();} catch (IOException e) {}
		
		Dictionary dico = new Dictionary(list);
		Index index = new Index(dico, list);
		
		// dico.writeDico();
		// dico.writeBase();
		
		System.out.println("Result : " + query(index, dico , "http://db.uwaterloo.ca/~galuc/wsdbm/userId", "9279708" ).toString());

	}
	


	public static ArrayList<String> query(Index index, Dictionary dico, String predicate, String object) {
		
		int Ipredicate = dico.getDico().get(predicate);
		int Iobject = dico.getDico().get(object);
		
		ArrayList<Integer> Isubject = index.getPos().get(Ipredicate).get(Iobject);
		ArrayList<String> subject = new ArrayList<>();
		
		
		for(Integer s: Isubject) {
			subject.add(dico.getBase().get(s));
		}
		
		
		return subject;
		
	}
}