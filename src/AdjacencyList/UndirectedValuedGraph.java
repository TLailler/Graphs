package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import GraphAlgorithms.GraphTools;

public class UndirectedValuedGraph extends UndirectedGraph<UndirectedNode> {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
    public UndirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.order = matrixVal.length;
    	for(int i=0; i<this.order;i++) {
    		this.nodes.add(this.makeNode(i));
    	}
    	for(int i=0; i<this.order;i++) {
    		List<UndirectedNode> adjs = new ArrayList<>();
    		for(int j=0;j<this.order;j++) {
    			if(matrixVal[i][j] > 0) {
    				adjs.add(this.nodes.get(j));
    				this.nodes.get(j).addCosts(matrixVal[i][j]);
    				if(i>=j) {
    					this.m++;
    				}
    			}
    		}
    		this.getNodes().get(i).setNeighbors(adjs);
    	}
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    
    /**
     * Removes the edge (from,to) and its cost if the edge is present in the graph
     */
    @Override
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
    	x=this.getNodes().get(x.getLabel());
    	y=this.getNodes().get(y.getLabel());
    	if(isEdge(x, y)) {
    		x.getNeighbors().remove(y);
    		y.getNeighbors().remove(x);
    		x.getCosts().remove(y);
    		y.getCosts().remove(x);
    	}
    	m--;
    }

    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
    	this.getNodes().get(x.getLabel()).getNeighbors().add(y);
    	this.getNodes().get(y.getLabel()).getNeighbors().add(x);
    	this.getNodes().get(x.getLabel()).getCosts().add(cost);
    	this.getNodes().get(y.getLabel()).getCosts().add(cost);
    	m++;
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 15, false, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.AfficherMatrix(matrix);
        GraphTools.AfficherMatrix(matrixValued);
        UndirectedValuedGraph al = new UndirectedValuedGraph(matrixValued);
        System.out.println(al);
        System.out.println(al.getNodes().get(2).getCosts());
        al.addEdge(new UndirectedNode(2), new UndirectedNode(5), 13);
        al.addEdge(new UndirectedNode(2), new UndirectedNode(7), 4);
        System.out.println(al);
        System.out.println(al.getNodes().get(2).getCosts());
        System.out.println(al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        al.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(al.getNodes().get(2).getCosts());
        System.out.println(al);
    }
	
}
