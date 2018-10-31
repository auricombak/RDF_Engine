package dictionary;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		RDFRawParser parcer = new RDFRawParser();
		parcer.parse("../RDFprojet/100K.rdfxml");
		
		Dictionary dico = new Dictionary(parcer.getList());
		Index index = new Index(dico, parcer.getList());
		
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
