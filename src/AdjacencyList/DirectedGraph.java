package AdjacencyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Abstraction.IDirectedGraph;
import AdjacencyMatrix.AdjacencyMatrixDirectedGraph;


public class DirectedGraph<A extends DirectedNode> extends AbstractListGraph<A> implements IDirectedGraph<A> {

	//private static int _DEBBUG =1;
	private static int _DEBBUG =0;
		
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public DirectedGraph(){
		super();
		this.nodes = new ArrayList<A>();
	}

	public DirectedGraph(int[][] matrix) {
    	this.order = matrix.length;
        this.m = 0;
    	this.nodes = new ArrayList<A>();
    	for(int i = 0 ; i < matrix.length ; i++) {
    		nodes.add(i, (A) new DirectedNode(i));
    	}
    	
    	for(A n : this.getNodes()) {
    		for(int j = 0 ; j < matrix[n.getLabel()].length ; j++) {
    			A nn = this.getNodes().get(j);
    			if (matrix[n.getLabel()][j] != 0) {
    				n.getSuccs().add(nn);
    				nn.getPreds().add(n);
    				this.m++;
    			}
    		}
    		
    	}
    }

    public DirectedGraph(DirectedGraph<A> g) {
    	// A completer

    }

    // ------------------------------------------
    // 		Accessors
    // ------------------------------------------
    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public List<A> getSuccessors(A x) {
        return this.getNodes().get(x.getLabel()).getSuccs().stream().map(n -> (A)n).collect(Collectors.toList());
    }

    @Override
    public List<A> getPredecessors(A x) {
        return this.getNodes().get(x.getLabel()).getPreds().stream().map(n -> (A)n).collect(Collectors.toList());
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    
    @Override
    public boolean isArc(A from, A to) {
    	return this.getSuccessors(this.getNodes().get(from.getLabel())).contains(this.getNodes().get(to.getLabel()));
    }

    @Override
    public void removeArc(A from, A to) {
    	if(isArc(from, to)) {
    		this.getNodes().get(from.getLabel()).getSuccs().remove(this.getNodes().get(to.getLabel()));
    		this.getNodes().get(to.getLabel()).getPreds().remove(this.getNodes().get(from.getLabel()));
    		m--;
    	}
    }

    @Override
    public void addArc(A from, A to) {
    	if(!isArc(from, to)) {
    		this.getNodes().get(from.getLabel()).getSuccs().add(this.getNodes().get(to.getLabel()));
        	this.getNodes().get(to.getLabel()).getPreds().add(this.getNodes().get(from.getLabel()));
        	m++;
    	}
    	
    }
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A)new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public A getNodeOfList(A src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
    	int[][] mat = new int[this.getNbNodes()][this.getNbNodes()];
    	for(A n : this.getNodes()) {
    		for(int i=0;i<this.getNbNodes();i++) {
    			mat[n.getLabel()][this.getNodes().get(i).getLabel()] = this.isArc(n, this.getNodes().get(i)) ? 1 : 0;
    		}
    	}
        return mat;
    }

    @Override
    public IDirectedGraph computeInverse() {
    	DirectedGraph<A> inv = new DirectedGraph<>(this);    	
		
		for(int i=0;i<inv.order;i++) {
			List<DirectedNode> suc = inv.nodes.get(i).getSuccs();
			List<DirectedNode> pre = inv.nodes.get(i).getPreds();
			inv.getNodes().get(i).setSuccs(pre);
			inv.getNodes().get(i).setPreds(suc);
		}
		return inv;
    }
    
 	
    @Override
    public String toString(){
        String s = "";
        for(DirectedNode n : nodes){
            s+="successors of "+n+" : ";
            for(DirectedNode sn : n.getSuccs()){
                s+=sn+" ";
            }
            s+="\n";
        }
        s+="\n";
        return s;
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
        GraphTools.AfficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);
        DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        System.out.println(alInv);
        al.addArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(al);
        System.out.println(al.isArc(new DirectedNode(2), new DirectedNode(5)));
        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(al);
        
       
    }
}
