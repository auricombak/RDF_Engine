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
	
	RDFListener list;

	public void parse(String path) throws FileNotFoundException {
		
		//Reader reader = new FileReader(path);
		Reader reader = new FileReader(
				"../RDFprojet/100K.rdfxml");

		org.openrdf.rio.RDFParser rdfParser = Rio
				.createParser(RDFFormat.RDFXML);
		
		list = new RDFListener();
		
		rdfParser.setRDFHandler(list);
		
		try {rdfParser.parse(reader, "");} catch (Exception e) {}
		
		try {reader.close();} catch (IOException e) {}
	}
	
	public void toString(int i) {
		for(int x=0; x<i; x++) {
			System.out.println("------");
			System.out.println("Predicate : ["+list.getPredicates().get(x).toString() + "]");
			System.out.println("Object : ["+list.getObjects().get(x).toString() + "]");
			System.out.println("Subject : ["+list.getSubjects().get(x).toString()+ "]");

		}
	}

	public RDFListener getList() {
		return list;
	}

	public void setList(RDFListener list) {
		this.list = list;
	}
	
	

}