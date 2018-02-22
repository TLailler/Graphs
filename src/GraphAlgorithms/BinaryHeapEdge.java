package GraphAlgorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import Abstraction.AbstractNode;
import Abstraction.Pair;
import Abstraction.Triple;
import AdjacencyList.DirectedNode;


public class BinaryHeapEdge<A extends Object> {

	private  List<Triple<A,A,Integer>> nodes;

    public BinaryHeapEdge() {
        this.nodes = new ArrayList<Triple<A,A,Integer>>();
    }

    public boolean isEmpty() {
       return this.nodes.size() > 0;
    }
    
    public void swap(Triple<A,A,Integer> x, Triple<A,A,Integer> y) {
    	Collections.swap(nodes, nodes.indexOf(x), nodes.indexOf(y));	
    }

    public Triple<A,A,Integer> getParent(Triple<A,A,Integer> t){
    	if(nodes.indexOf(t) != 0) {
    		return nodes.get((nodes.indexOf(t) % 2 == 0 ? ((nodes.indexOf(t)-2)/2) : ((nodes.indexOf(t)-1)/2) ));
    	}else {
    		return t;
    	}    	
    }
    
    public void insert(A from, A to, int val) {
    	Triple<A, A, Integer> t = new Triple<A, A, Integer>(from, to, val);
    	nodes.add(t);
    	while(t.getThird() > getParent(t).getThird()) {
    		swap(t, getParent(t));
    	}
    }

    public Triple<A,A,Integer> remove() {
    	Triple<A,A,Integer> n = nodes.get(0);
    	swap(nodes.get(0), nodes.get(nodes.size()-1));
    	nodes.remove(n);
    	
    	Triple<A,A,Integer> m = nodes.get(0);
    	
//    	while(m.getThird() < ){
//    		
//    	}
    	
    	return n;
    }


    private boolean isLeaf(int src) {
    	return nodes.size() < ((src*2)+1);
    }

   

    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	int compt = 0;
    	int nbMaxNodeAvantNouveauNiveau = 1;
    	int niveauCourant = 0;
    	int length = this.nodes.size();
    	
    	while (compt < length) {
    		sb.append(nodes.get(compt));
    		compt++;

    		if (compt >= nbMaxNodeAvantNouveauNiveau) {
    			sb.append("\n");
    			niveauCourant++;
    			nbMaxNodeAvantNouveauNiveau += Math.pow(2, niveauCourant);
    		}
    	}
    	return sb.toString();
    }

    // test recursif pour verifier que fils gauche et fils droit sont bien >= a la racine.
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
    	int lastIndex = nodes.size()-1; 
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= lastIndex) {
                return nodes.get(left).getThird() >= nodes.get(root).getThird() && testRec(left);
            } else {
                return nodes.get(left).getThird() >= nodes.get(root).getThird() && testRec(left) && 
                		nodes.get(right).getThird() >= nodes.get(root).getThird() && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeapEdge<DirectedNode> jarjarBin = new BinaryHeapEdge<DirectedNode>();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 10;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));            
            System.out.print("insert triple des noeuds "+k+" et "+(k+30)+" de poids " + rand);
            jarjarBin.insert(new DirectedNode(k), new DirectedNode(k+30), rand);
            System.out.println(" ok");
            k--;
        }
        System.out.println("\n" + jarjarBin);
//        System.out.println(jarjarBin.isEmpty());
//        System.out.println(jarjarBin.test() + "\n");
//        while (m > 0) {
//            System.out.print("remove " + jarjarBin.remove());
//            System.out.println(" ok");
//            m-=2;
//        }
//        System.out.println();
//        System.out.println("\n" + jarjarBin);
//        System.out.println(jarjarBin.isEmpty());
//        System.out.println(jarjarBin.test());
    }


}

