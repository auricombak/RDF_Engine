package dictionary;

import java.util.ArrayList;
import java.util.HashMap;

public class Index {
	
	private HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> pos =  new HashMap<>();
	private HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> ops =  new HashMap<>();
	
	// Create Index ops and pos
	public Index(Dictionary dico, RDFListener list) {

				int i=0;
				while(i<list.nbTriple){
					
					int o = dico.getDico().get(list.objects.get(i));
					int p = dico.getDico().get(list.predicates.get(i));
					int s = dico.getDico().get(list.subjects.get(i));
					
					ops.putIfAbsent(o, new HashMap<Integer, ArrayList<Integer> >() ) ;
					HashMap<Integer, ArrayList<Integer>> hashOps = ops.get(o); 
					hashOps.putIfAbsent(p, new ArrayList<Integer>());
					hashOps.get(p).add(s);
					
					pos.putIfAbsent(p, new HashMap<Integer, ArrayList<Integer> >() ) ;
					HashMap<Integer, ArrayList<Integer>> hashPos = pos.get(p); 
					hashPos.putIfAbsent(o, new ArrayList<Integer>());
					hashPos.get(o).add(s);
					
					i++;
				}
				

	}

	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getPos() {
		return pos;
	}

	public void setPos(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> pos) {
		this.pos = pos;
	}

	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getOps() {
		return ops;
	}

	public void setOps(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> ops) {
		this.ops = ops;
	}
	
	
	
}
