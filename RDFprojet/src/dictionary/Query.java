package dictionary;

import java.util.ArrayList;

public class Query {
	ArrayList <Condition> query;
	
	public Query(ArrayList<Condition> query) {
		super();
		this.query = query;
	}
	
	public Query() {
		super();
		this.query = new ArrayList<>();
	}
	
	public void addCondition(Condition c) {
		this.query.add(c);
	}

	@Override
	public String toString() {
		String toS = "#";
		for(Condition c : query) {
			toS += query.toString();
		}
		return toS;
	}

	public ArrayList<Condition> getQuery() {
		return query;
	}

	public void setQuery(ArrayList<Condition> query) {
		this.query = query;
	}
	
	
}
