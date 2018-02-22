package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;

public class DirectedValuedGraph extends DirectedGraph<DirectedNode> {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public DirectedValuedGraph(int[][] matrixVal) {
		this.order = matrixVal.length;
        this.m = 0;
    	this.nodes = new ArrayList<>();
    	for(int i = 0 ; i < matrixVal.length ; i++) {
    		nodes.add(i,new DirectedNode(i));
    	}
    	
    	for(DirectedNode n : this.getNodes()) {
    		for(int j = 0 ; j < matrixVal[n.getLabel()].length ; j++) {
    			int cost = matrixVal[n.getLabel()][j];
    			DirectedNode nn = this.getNodes().get(j);
    			if (matrixVal[n.getLabel()][j] != 0) {
    				
    				n.getSuccs().add(nn);
    				n.addCosts(cost);
    				
    				nn.getPreds().add(n);
    				nn.addCosts(cost);
    				
    				this.m++;
    			}
    		}
    		
    	}         	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    
	/**
     * Removes the arc (from,to) with cost if it is present in the graph
     */
    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
    	if(isArc(from, to)) {
    		this.getNodes().get(from.getLabel()).getSuccs().remove(this.getNodes().get(to.getLabel()));
    		this.getNodes().get(from.getLabel()).getCosts().remove(this.getNodes().get(to.getLabel()).weight);
    		
    		this.getNodes().get(to.getLabel()).getPreds().remove(this.getNodes().get(from.getLabel()));
    		this.getNodes().get(to.getLabel()).getCostsInv().remove(this.getNodes().get(from.getLabel()).weight);
    		m--;
    	}
    }

    /**
     * Adds the arc (from,to) with cost  if it is not already present in the graph
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
    	if(!isArc(from, to)) {
    		this.getNodes().get(from.getLabel()).getSuccs().add(this.getNodes().get(to.getLabel()));
    		this.getNodes().get(from.getLabel()).getCosts().add(this.getNodes().get(to.getLabel()).weight);
    		
        	this.getNodes().get(to.getLabel()).getPreds().add(this.getNodes().get(from.getLabel()));
        	this.getNodes().get(to.getLabel()).getCostsInv().add(this.getNodes().get(from.getLabel()).weight);
        	m++;
    	}    
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.AfficherMatrix(matrix);
        GraphTools.AfficherMatrix(matrixValued);
        DirectedValuedGraph al = new DirectedValuedGraph(matrixValued);
        System.out.println(al);
        //DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        //System.out.println(alInv);
        System.out.println(al.getNodes().get(2).getCosts());
        al.addArc(new DirectedNode(2), new DirectedNode(5), 13);
        al.addArc(new DirectedNode(2), new DirectedNode(7), 4);
        System.out.println(al);
        System.out.println(al.getNodes().get(2).getCosts());
        System.out.println(al.isArc(new DirectedNode(2), new DirectedNode(5)));
        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(al.getNodes().get(2).getCosts());
    }
	
}
