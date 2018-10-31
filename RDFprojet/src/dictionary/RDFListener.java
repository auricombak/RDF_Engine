package dictionary;

import java.util.ArrayList;

import org.openrdf.model.Statement;
import org.openrdf.rio.helpers.RDFHandlerBase;

public class RDFListener extends RDFHandlerBase {

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
	
	public void toString(int i) {
		for(int x=0; x<i; x++) {
			System.out.println("------");
			System.out.println("Predicate : ["+predicates.get(x).toString() + "]");
			System.out.println("Object : ["+objects.get(x).toString() + "]");
			System.out.println("Subject : ["+subjects.get(x).toString()+ "]");

		}
	}

}