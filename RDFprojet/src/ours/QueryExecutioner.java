package ours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
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
                    objects.get(Iobject);
                    if(objects.get(Iobject) != null) {
                        toMergeJoin.add(objects.get(Iobject));
                    }

                }else {
                    objects = mIndex.getOps().get(Iobject);
                    objects.get(Ipredicate);
                    if(objects.get(Ipredicate) != null) {
                        toMergeJoin.add(objects.get(Ipredicate));
                    }
                }
            }catch(NullPointerException e) {
                return results;
            }

        }

        //Sort the toMergeJoin
        if(toMergeJoin.size() >= 3 ) {
            toMergeJoin = sortStack(toMergeJoin);
        }


        //Pop tuple of lighter condition, get the intersection and add it to the stack while stack is not empty

        while (toMergeJoin.size() > 1) {

            TreeSet<Integer> light = toMergeJoin.pop();
            TreeSet<Integer> heavy = toMergeJoin.pop();

            TreeSet<Integer> tmp = intersection(light, heavy);
            toMergeJoin.push(tmp);

        }

        if(!toMergeJoin.isEmpty()) {
            for(Integer s: toMergeJoin.pop()) {
                results.add(mDico.getBase().get(s));
            }
        }

        return results;
    }

    //Return the intersection of two TreeSet
    public static TreeSet<Integer> intersection(TreeSet<Integer> a, TreeSet<Integer> b) {

        if(a.size()>b.size()) {
           intersection(b, a);
        }

        TreeSet<Integer> results = new TreeSet<>();

        for (Integer element : a) {
            if (b.contains(element)) {
                results.add(element);
            }
        }

        return results;
    }

    //OPTIMISATION
    //Get Stack in entry -> return Sorted Stack by TreeSet.size()
    public static Stack<TreeSet<Integer>> sortStack(Stack<TreeSet<Integer>> input)
    {
        Stack<TreeSet<Integer>> tmpStack = new Stack<>();
        while(!input.isEmpty())
        {
            int tmp = input.peek().size();

            TreeSet<Integer> tmpT = new TreeSet<>();
            tmpT = input.pop();

            while(!tmpStack.isEmpty() && tmpStack.peek().size() < tmp)
            {
                input.push(tmpStack.pop());
            }

            tmpStack.push(tmpT);
        }
        return tmpStack;
    }

    public ArrayList<String> getEvalOrdre(){
        ArrayList<String> res = new ArrayList<String>();
        for(Condition c: mQuery.getConditions()){
            res.add(c.toString());
        }
        return res;
    }
}