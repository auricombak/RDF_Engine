package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class QueryExecutioner {
    Query mQuery;
    Model mModel;
    Dictionary mDico;
    Index mIndex;

    public QueryExecutioner(Query q, Model m) {
        this.mQuery = q;
        this.mModel = m;
        this.mDico = mModel.getmDictionary();
        this.mIndex = mModel.getmIndex();
    }

    public ArrayList<String> execute() {
        ArrayList<String> results = new ArrayList<>();
        Stack<TreeSet<Integer>> toMergeJoin = new Stack<>();
        ArrayList<Condition> mConditions = mQuery.getConditions();

        for (Condition c : mConditions) {
        	int Ipredicate;
        	int Iobject;
        	//If object or predicate not exist in Dictionary, the query return empty []
        	try {
        		Ipredicate = mDico.getDico().get(c.getP());
        		Iobject = mDico.getDico().get(c.getO());
        	}catch (NullPointerException e) {

        	    return results;

        	}

        	HashMap <Integer,TreeSet<Integer>> objects = new HashMap<>();
   	
        	//OPTIMISATION
        	//If argument predicate/object is unknown to Index P/Index O the query return []
        	try {
	        	if(mIndex.getIo().get(Iobject).get() > mIndex.getIp().get(Ipredicate).get()) {
	                objects = mIndex.getPos().get(Ipredicate);
	        	}else {
	        		objects = mIndex.getOps().get(Iobject);
	        	}
        	}catch(NullPointerException e) {
        		return results;
        	}

            if(objects.containsKey(Iobject)) {
                TreeSet<Integer> Isubject  = objects.get(Iobject);
                toMergeJoin.add(Isubject);
            }
        }
     
        toMergeJoin = sortStack(toMergeJoin);
     
        while (toMergeJoin.size() > 1) {

            TreeSet<Integer> light = toMergeJoin.pop();
            TreeSet<Integer> heavy = toMergeJoin.pop();

            TreeSet<Integer> tmp = intersection(light, heavy);
            toMergeJoin.push(tmp);
            
            toMergeJoin = sortStack(toMergeJoin);
            
        } 
        
        if(!toMergeJoin.isEmpty()) {
        	TreeSet<Integer> finalRes = toMergeJoin.pop();
            for(Integer s: finalRes) {
                results.add(mDico.getBase().get(s));
            }
        }
        
        return results;
    }

    //Return the intersection of two TreeSet
    public static TreeSet<Integer> intersection(TreeSet<Integer> a, TreeSet<Integer> b) {


        TreeSet<Integer> results = new TreeSet<>();

        for (Integer element : a) {
            if (b.contains(element)) {
                results.add(element);
            }
        }

        return results;
    }
    
    //Get Stack in entry -> return Sorted Stack by TreeSet.size()
    public static Stack<TreeSet<Integer>> sortStack(Stack<TreeSet<Integer>> input) 
    { 
    	Stack<TreeSet<Integer>> tmpStack = new Stack<>(); 
    	while(!input.isEmpty()) 
    	{ 
    		int tmp = input.peek().size(); 
    		TreeSet<Integer> tmpT = new TreeSet<>();
    		tmpT = input.pop();

    		while(!tmpStack.isEmpty() && tmpStack.peek().size() > tmp) 
    		{ 
    			input.push(tmpStack.pop()); 
    		} 

    		tmpStack.push(tmpT); 
    	} 
    	return tmpStack; 
	} 

}