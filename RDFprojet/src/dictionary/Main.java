package dictionary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RDFRawParser parcer = new RDFRawParser();
		parcer.parse("../RDFprojet/100K.rdfxml");
		
		Dictionary dico = new Dictionary(parcer.getList());
		Index index = new Index(dico, parcer.getList());
		QueryFactory factory = new QueryFactory();
		
		//Return ArrayList containing ArrayList<Query> for each file
		factory.loadFromFolder("../RDFprojet/queries");
		
		
		
		
		// Query q = query.create("ca");
		// System.out.println(q.toString());

		/*
		ArrayList<Query> aq = factory.createMultipleQuery("../RDFprojet/queries/Q_4_location_nationality_gender_type.queryset");
		int i=0;
		for(Query q : aq) {
			i++;
			System.out.println("Query "+i + " : ");
			System.out.println(q.toString());
		} */
		
		/*
		int x = 0;
		for(ArrayList<Query> aq : factory.createMultipleFileQuery("../RDFprojet/queries")) {
			x++;
			System.out.println();
			System.out.println("File "+x + " : ");
			int i=0;
			for(Query q : aq) {
				i++;
				System.out.println("Query "+i + " : ");
				System.out.println(q.toString());
			}
		}
		
		*/
		
		// dico.writeDico();
		// dico.writeBase();
		// query.executeListQuery("../RDFprojet/")
		// System.out.println("Result : " + query(index, dico , "http://db.uwaterloo.ca/~galuc/wsdbm/userId", "9279708" ).toString());
		
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
