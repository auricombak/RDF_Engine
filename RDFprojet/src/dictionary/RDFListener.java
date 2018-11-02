package dictionary;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.RDFHandlerBase;

public class RDFListener extends RDFHandlerBase {

	private ArrayList<String > subjects = new ArrayList<>();
	private ArrayList<String > predicates = new ArrayList<>();
	private ArrayList<String > objects = new ArrayList<>();
	private Integer nbTriple = 0;
	
	@Override
	public void handleStatement(Statement st) {
		subjects.add(st.getSubject().toString());
		predicates.add(st.getPredicate().toString());
		objects.add(st.getObject().toString().replaceAll("\"", ""));						
		nbTriple++;
	}

	public ArrayList<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(ArrayList<String> subjects) {
		this.subjects = subjects;
	}

	public ArrayList<String> getPredicates() {
		return predicates;
	}

	public void setPredicates(ArrayList<String> predicates) {
		this.predicates = predicates;
	}


	public ArrayList<String> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<String> objects) {
		this.objects = objects;
	}

	public Integer getNbTriple() {
		return nbTriple;
	}

	public void setNbTriple(Integer nbTriple) {
		this.nbTriple = nbTriple;
	}
	


}