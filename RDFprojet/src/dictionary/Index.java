package dictionary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

public class Index {
	
    private HashMap<Integer, HashMap<Integer, TreeSet<Integer>>> pos =  new HashMap<>();
    private HashMap<Integer, HashMap<Integer, TreeSet<Integer>>> ops = new HashMap<>();
	private HashMap<Integer, HashMap<Integer, MutableDouble>> op =  new HashMap<>();
	private HashMap<Integer, HashMap<Integer, MutableDouble>> po =  new HashMap<>();
	private HashMap<Integer, MutableDouble> io =  new HashMap<>();
	private HashMap<Integer, MutableDouble> ip =  new HashMap<>();
	private int nbTriple;
	
	class MutableDouble {
		  int sizeComp;
		  Double value = 0.0; // note that we start at 1 since we're counting
		  public void increment () { value++ ;}
		  public Double  get () { return value;}
	}
	
	// Create Index ops and pos
	public Index(Dictionary dico, RDFListener list) {

				int i=0;

				while(i<list.getNbTriple()){
					
					int o = dico.getDico().get(list.getObjects().get(i));
					int p = dico.getDico().get(list.getPredicates().get(i));
					int s = dico.getDico().get(list.getSubjects().get(i));
					
					ops.putIfAbsent(o, new HashMap<Integer, TreeSet<Integer> >() ) ;
					HashMap<Integer, TreeSet<Integer>> hashPs = ops.get(o); 
					hashPs.putIfAbsent(p, new TreeSet<Integer>());
					hashPs.get(p).add(s);
					
					pos.putIfAbsent(p, new HashMap<Integer, TreeSet<Integer> >() ) ;
					HashMap<Integer, TreeSet<Integer>> hashOs = pos.get(p); 
					hashOs.putIfAbsent(o, new TreeSet<Integer>());
					hashOs.get(o).add(s);
					
					op.putIfAbsent(o, new HashMap<Integer, MutableDouble >() ) ;
					HashMap<Integer, MutableDouble> hashPf = op.get(o);
					hashPf.putIfAbsent(p, new MutableDouble());
					hashPf.get(p).increment();

					po.putIfAbsent(p, new HashMap<Integer, MutableDouble >() ) ;
					HashMap<Integer, MutableDouble> hashOf = po.get(p);
					hashOf.putIfAbsent(o, new MutableDouble());
					hashOf.get(o).increment();
					
					io.put(o, new MutableDouble());
					io.get(o).increment();
					
					ip.putIfAbsent(p, new MutableDouble());
					ip.get(p).increment();
					
					i++;
				}

				
				

	}

	public int getNbTriple() {
		return nbTriple;
	}
	
	public HashMap<Integer, HashMap<Integer, TreeSet<Integer>>> getPos() {
		return pos;
	}

	public void setPos(HashMap<Integer, HashMap<Integer, TreeSet<Integer>>> pos) {
		this.pos = pos;
	}

	public HashMap<Integer, HashMap<Integer, TreeSet<Integer>>> getOps() {
		return ops;
	}

	public void setOps(HashMap<Integer, HashMap<Integer, TreeSet<Integer>>> ops) {
		this.ops = ops;
	}

	public HashMap<Integer, HashMap<Integer, MutableDouble>> getOp() {
		return op;
	}

	public void setOp(HashMap<Integer, HashMap<Integer, MutableDouble>> op) {
		this.op = op;
	}

	public HashMap<Integer, HashMap<Integer, MutableDouble>> getPo() {
		return po;
	}

	public void setPo(HashMap<Integer, HashMap<Integer, MutableDouble>> po) {
		this.po = po;
	}

	public HashMap<Integer, MutableDouble> getIo() {
		return io;
	}

	public void setIo(HashMap<Integer, MutableDouble> io) {
		this.io = io;
	}

	public HashMap<Integer, MutableDouble> getIp() {
		return ip;
	}

	public void setIp(HashMap<Integer, MutableDouble> ip) {
		this.ip = ip;
	}

	public void setNbTriple(int nbTriple) {
		this.nbTriple = nbTriple;
	}
	
	//Write the content of the dico -> dico.txt
	public void writeIo() throws IOException {
		String aggFileName = String.valueOf("io.txt");
		FileWriter fstream = new FileWriter(aggFileName);
		BufferedWriter out = new BufferedWriter(fstream);
		
		for (Entry<Integer, MutableDouble> entree : io.entrySet()) {
		    out.write("Key-["+entree.getKey() + "]" + " | Value-[" + entree.getValue() + "]\n");
		    out.flush();

		}
	}
	
	
}
