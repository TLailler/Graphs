package AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Abstraction.IUndirectedGraph;


public class UndirectedGraph<A extends UndirectedNode> extends AbstractListGraph<A> implements IUndirectedGraph<A> {


    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public UndirectedGraph() {
		 this.nodes = new ArrayList<>();
	}
	
	public UndirectedGraph(List<A> nodes) {
        super(nodes);
        for (UndirectedNode i : nodes) {
            this.m += i.getNbNeigh();
        }
    }

    public UndirectedGraph(int[][] matrix) {
    	super();
    	this.order = matrix.length;
    	for(int i=0; i<this.order;i++) {
    		this.nodes.add(this.makeNode(i));
    	}
    	for(int i=0; i<this.order;i++) {
    		List<UndirectedNode> adjs = new ArrayList<>();
    		for(int j=0;j<this.order;j++) {
    			if(matrix[i][j] > 0) {
    				adjs.add(this.nodes.get(j));
    				if(i>=j) {
    					this.m++;
    				}
    			}
    		}
    		this.getNodes().get(i).setNeighbors(adjs);
    	}
    }

    public UndirectedGraph(UndirectedGraph<A> g) {
    	super(g.nodes);
    	for (UndirectedNode i : g.nodes) {
            this.m += i.getNbNeigh();
        }
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    @Override
    public int getNbEdges() {
        return this.m;
    }    

    @Override
    public List<A> getNeighbors(A x) {
        return this.getNodes().get(x.getLabel()).getNeighbors().stream().map(n -> (A)n).collect(Collectors.toList());
    }
    
    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    @Override
    public boolean isEdge(A x, A y) {
    	x=this.getNodes().get(x.getLabel());
    	y=this.getNodes().get(y.getLabel());
    	for(UndirectedNode n : this.getNeighbors(x)) {
    		if(n.getLabel() == y.getLabel()) {
    			return true;
    		}
    	}
    	return false;
    }

    @Override
    public void removeEdge(A x, A y) {
    	x=this.getNodes().get(x.getLabel());
    	y=this.getNodes().get(y.getLabel());
    	if(isEdge(x, y)) {
    		x.getNeighbors().remove(y);
    		y.getNeighbors().remove(x);
    	}
    	m--;
    }

    @Override
    public void addEdge(A x, A y) {
    	this.getNodes().get(x.getLabel()).getNeighbors().add(y);
    	this.getNodes().get(y.getLabel()).getNeighbors().add(x);
    	m++;
    }
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends UndirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A) new UndirectedNode(label);
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
    	int[][] mat = new int[this.getNbNodes()][this.getNbNodes()];
    	for(A n : this.getNodes()) {
    		for(int i=0;i<this.getNbNodes();i++) {
    			mat[n.getLabel()][this.getNodes().get(i).getLabel()] = this.isEdge(n, this.getNodes().get(i)) ? 1 : 0;
    		}
    	}
        return mat;
    }

    
    @Override
    public String toString() {
        String s = "";
        for (UndirectedNode n : nodes) {
            s += "neighbors of " + n + " : ";
            for (UndirectedNode sn : n.getNeighbors()) {
                s += sn.toString() + " ";
            }
            s += "\n";
        }
        s += "\n";
        return s;
    }


    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 123456);
        GraphTools.AfficherMatrix(mat);
        UndirectedGraph al = new UndirectedGraph(mat);
        System.out.println(al);
        al.addEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(al);
        System.out.println(al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        al.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(al);
    }

}
