package GraphAlgorithms;
import java.util.*;

import Abstraction.Pair;
import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedNode;

import java.io.*;

public class GraphTools {

	//private static int DEBBUG =1;
	private static int _DEBBUG =0;

	public GraphTools() {
	
	}

	/**
	 * 
	 * @param n, the number of vertices
	 * @param multi, at true if we want a multi-graph
	 * @param s, at true if the graph is symmetric
	 * @param c, at true if the graph is connected
	 * @param seed, the unique seed giving a unique random graph
	 * @return the generated matrix 
	 */ 
	public static int[][] generateGraphData(int n, boolean multi, boolean s, boolean c, int seed){
		if(_DEBBUG>0){
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateGraphData en aléatoire complet>>");
		}

		Random rand = new Random(seed);
		int m = (rand.nextInt(n)+1)*(n-1)/2;
		if(_DEBBUG>0){System.out.println("m = "+m);}
		int [][] Matrix = new int[n][n] ;	
		if(c){
			List<Integer> vis = new ArrayList<Integer>();
			int from = rand.nextInt(n);
			vis.add(from);
			from = rand.nextInt(n);
			while(vis.size()<n ){
				if(!vis.contains(from)){
					int indDest = rand.nextInt(vis.size());
					int dest = vis.get(indDest);				
					if(s)
						Matrix[dest][from] = 1;
					Matrix[from][dest] = 1;
					vis.add(from);
				}
				from = rand.nextInt(n);				
			}
			m -= n-1;
		}

		while(m>0){
			int i = rand.nextInt(n);
			int j = rand.nextInt(n);
			//int i = (int) (Math.rand()*n);
			//int j = (int) (Math.rand()*n);
			if(_DEBBUG>0){
				System.out.println("i = "+i);
				System.out.println("j = "+j);
			}
			if(!multi){
				if(i!=j && Matrix[i][j]!=1 ){
					if(s)
						Matrix[j][i] = 1;
					Matrix[i][j] = 1;
					m--;
				}
			}
			else{
				if(Matrix[i][j]==0 ){
					int val = ( i!=j ? ( m<3 ? m: (int) (rand.nextInt(3))+1) : 1);
					//int val = ( i!=j ? ( m<3 ? m: (int) (Math.random()*3)+1) : 1);
					if(_DEBBUG>0){
						System.out.println("Pour multi, val = "+val);
					}
					if(s)
						Matrix[j][i] = val;
					Matrix[i][j] = val;
					m -= val;
				}
			}
		}
		return Matrix;
	}

	/**
	 * 
	 * @param n, the number of vertices
	 * @param m, the number of edges
	 * @param multi, at true if we want a multi-graph
	 * @param s, at true if the graph is symmetric
	 * @param c, at true if the graph is connexted
	 * @param seed, the unique seed giving a unique random graph
	 * @return the generated matrix
	 */ 
	public static int[][] generateGraphData(int n, int m, boolean multi, boolean s, boolean c, int seed){
		if(_DEBBUG>0){
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateGraphData >>");
		}
		int [][] Matrix = new int[n][n] ;
		Random rand = new Random(seed);
		if(c){
			List<Integer> vis = new ArrayList<Integer>();
			int from = rand.nextInt(n);
			vis.add(from);
			from = rand.nextInt(n);
			while(vis.size()<n ){
				if(!vis.contains(from)){
					int indDest = rand.nextInt(vis.size());
					int dest = vis.get(indDest);				
					if(s)
						Matrix[dest][from] = 1;
					Matrix[from][dest] = 1;
					vis.add(from);
				}
				from = rand.nextInt(n);				
			}
			m -= n-1;
		}

		while(m>0){
			int i = rand.nextInt(n);
			int j = rand.nextInt(n);
			//int i = (int) (Math.rand()*n);
			//int j = (int) (Math.rand()*n);
			if(_DEBBUG>0){
				System.out.println("i = "+i);
				System.out.println("j = "+j);
			}
			if(!multi){
				if(i!=j && Matrix[i][j]!=1 ){
					if(s)
						Matrix[j][i] = 1;
					Matrix[i][j] = 1;
					m--;
				}
			}
			else{
				if(Matrix[i][j]==0 ){
					int val = ( i!=j ? ( m<3 ? m: (int) (rand.nextInt(3))+1) : 1);
					//int val = ( i!=j ? ( m<3 ? m: (int) (Math.random()*3)+1) : 1);
					if(_DEBBUG>0){
						System.out.println("Pour multi, val = "+val);
					}
					if(s)
						Matrix[j][i] = val;
					Matrix[i][j] = val;
					m -= val;
				}
			}
		}
		return Matrix;
	}

	/**
	 * 
	 * @param n, the number of vertices
	 * @param multi, at true if we want a multi-graph
	 * @param s, at true if the graph is symmetric
	 * @param c, at true if the graph is connexted
	 * @param neg, at true if the graph has negative weights 
	 * @param seed, the unique seed giving a unique random graph
	 * @return
	 */
	public static int[][] generateValuedGraphData(int n, boolean multi, boolean s, boolean c, boolean neg, int seed){
		if(_DEBBUG>0){
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateValuedGraphData >>");
		}

		int[][] mat = generateGraphData(n, multi, s, c, seed);
		int [][] matValued = new int[mat.length][mat.length] ;
		Random rand = new Random(seed);
		int valNeg =0;
		if(neg)
			valNeg = -6;

		for(int i =0; i<mat.length; i++){
			for(int j =0; j<mat[0].length; j++){
				if(mat[i][j]>0){
					int val = ((int) (rand.nextInt(15)))+1+valNeg;
					//int val = ((int) (Math.random()*15))+1+valNeg;
					matValued[i][j] = val;
					if(s)
						matValued[j][i] = val;
				}
			}
		}

		return matValued;
	}

	/**
	 * @param M, a matrix
	 */
	public static void AfficherMatrix(int[][] M){
		for(int i =0;i<M.length;i++){
			for(int j =0;j<M[i].length;j++){
				System.out.print(M[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}


	/**
	 * @param mat, a matrix
	 * @return the symmetrical matrix 
	 */
	public static int[][] MatrixSym(int[][] mat){
		for(int i =0;i<mat.length;i++){
			for(int j =0;j<mat[i].length;j++){
				if(mat[i][j]==1)
					mat[j][i]=1;
			}
		}
		return mat;
	}

	/**
	 * @param graphe le graphe � parcourir
	 * @return le tableau des fins de parcours pour chaque sommet, ainsi que les composantes d�gag�es lors du parcours
	 */
	public static Pair<List<Set<DirectedNode>>, int[]> explorerGrapheProfondeur(DirectedGraph<DirectedNode> graphe) {
		
		Set<DirectedNode> sommetsVisites = new HashSet<>();
		int[] fin = new int[graphe.getNbNodes()];
		
		List<Set<DirectedNode>> composantes = new LinkedList<>();
		
		Compteur compteur = new Compteur();
		for (DirectedNode node : graphe.getNodes()) {
			if (!sommetsVisites.contains(node)) {
				compteur.incrementer();
				composantes.add(explorerSommetProfondeur(node, sommetsVisites, fin, compteur));
			}
		}
		
		return new Pair<>(composantes, fin);
	}
	
	/**
	 * @param sommet le sommet courant
	 * @param sommetsVisites l'ensemble des sommets d�j� visit�s
	 * @param fin le tableau des fins de parcours pour chaque sommet.
	 * @param compteur le temps courant
	 * 
	 * @return la composante connexe d�gag�e lors du parcours � partir du n�ud donn� en param�tre
	 */
	public static Set<DirectedNode> explorerSommetProfondeur(DirectedNode sommet, Set<DirectedNode> sommetsVisites, int[] fin, Compteur compteur) {
		sommetsVisites.add(sommet);
		
		Set<DirectedNode> composanteConnexe = new HashSet<>();
		composanteConnexe.add(sommet);
		
		for (DirectedNode node : sommet.getSuccs()) {
			if (!sommetsVisites.contains(node)) {
				compteur.incrementer();
				composanteConnexe.addAll(explorerSommetProfondeur(node, sommetsVisites, fin, compteur));
			}
		}
		compteur.incrementer(); // fin de l'exploration du n�ud
		fin[sommet.getLabel()] = compteur.getTemps();
		
		return composanteConnexe;
	}
	
	
	//
	// Parcours en largeur
	// Impl�mentations pour le cas orient� et le cas non orient�
	//
	public static void explorerGrapheLargeur(DirectedGraph<DirectedNode> graphe) {
		final int ordre = graphe.getNbNodes();
		boolean[] mark = new boolean[ordre]; // chaque case est � false par d�faut
		
		DirectedNode premierNoeud = graphe.getNodes().get(0);
		mark[premierNoeud.getLabel()] = true;
		
		// LinkedList peut s'utiliser comme une file
		LinkedList<DirectedNode> toVisit = new LinkedList<>();
		toVisit.push(premierNoeud);
		while (!toVisit.isEmpty()) {
			DirectedNode noeud = toVisit.pop();
			for (DirectedNode succ : noeud.getSuccs()) {
				int label = succ.getLabel();
				if (!mark[label]) {
					mark[label] = true;
					toVisit.push(succ);
				}
			}
		}
	}
	
	
	//
	// Calcul des composantes fortement connexes
	// Une nouvelle impl�mentation du parcours a �t� n�cessaire pour parcourir les n�uds dans l'ordre d�croissant des temps
	// 
	public static List<Set<DirectedNode>> composantesFortementConnexes(DirectedGraph<DirectedNode> graphe) {
		final int[] fin = explorerGrapheProfondeur(graphe).getRight();
		DirectedGraph<DirectedNode> grapheInverse = (DirectedGraph<DirectedNode>) graphe.computeInverse();
		return explorerGrapheProfondeur(grapheInverse, fin);
	}

	public static List<Set<DirectedNode>> explorerGrapheProfondeur(DirectedGraph<DirectedNode> graphe, int[] fin) {
		List<Set<DirectedNode>> composantes = new LinkedList<>();
		
		// Mise en place du parcours sur les n�uds par ordre d�croissant de leur temps de fin
		
		// cl� : valeur de fin pour le n�ud qui est en valeur.
		// c'est un peu moche je trouve mais j'ai pas pu penser � 
		// une meilleure mani�re de parcourir par ordre d�croissant
		// de temps sans perdre le num�ro de n�ud
		Map<Integer, DirectedNode> tempsFin = new HashMap<>();
		for (int i = 0; i < graphe.getNbNodes(); i++) {
			tempsFin.put(fin[i], graphe.getNodes().get(i));
		}
		
		List<Integer> temps = new LinkedList<>();
		tempsFin.keySet().stream().forEach(k -> temps.add((Integer) k) );
		Collections.sort(temps);
		Collections.reverse(temps); // tri� par orde d�croissant.

		// Parcours par ordre d�croissant des temps de fin
		Set<DirectedNode> sommetsVisites = new HashSet<>();
		for (Integer t : temps) {
			DirectedNode node = tempsFin.get(t);
			if (!sommetsVisites.contains(node)) {
				composantes.add(explorerSommetProfondeur(node, sommetsVisites, fin, new Compteur()));
			}
		}
		
		return composantes;
	}
	
	/**
	 * version de l'exploration du sommet en profondeur sans gestion du temps
	 * @param sommet
	 * @param sommetsVisites
	 * @return la composant d�gag�e du parcours
	 */
	public static Set<DirectedNode> explorerSommetProfondeur(DirectedNode sommet, Set<DirectedNode> sommetsVisites) {
		sommetsVisites.add(sommet);
		Set<DirectedNode> composanteConnexe = new HashSet<>();
		composanteConnexe.add(sommet);
		
		for (DirectedNode node : sommet.getSuccs()) {
			if (!sommetsVisites.contains(node)) {
				composanteConnexe.addAll(explorerSommetProfondeur(node, sommetsVisites));
			}
		}
		
		return composanteConnexe;
	}
	
	public static void Belmann(DirectedGraph G, int n) {
		int[] distK = new int[n], distK1 = new int [n];
		int[] pereK = new int[n], pereK1 = new int [n];
		for(int i = 0; i<n; i++) {
			distK[i] = Integer.MAX_VALUE;
			pereK[i] = -1;
		}
		distK[1] = 0;
		pereK[1] = 1;
		Boolean fini = false;
		int k = 0;
		while(!fini && k < n-1) {
			fini = true;
			for(int i = 1 ; i< n; i++) {
				distK1[i] = distK[i];
				DirectedNode ni = ((DirectedNode) G.getNodes().get(i));
				for(DirectedNode j : ni.getPreds() ) {
					//if(distK[j.getLabel()] + ni)
				}
			}
		}
		
	}
	
	

	public static void main(String[] args) {
		// Exemple envoy� par le prof par mail.
		int[][] grapheMat = generateGraphData(10, 20, false, false, true, 100001);
		
		DirectedGraph<DirectedNode> graphe = new DirectedGraph<>(grapheMat);
		List<Set<DirectedNode>> cfcs = composantesFortementConnexes(graphe);
		
		for (Set<DirectedNode> cfc : cfcs) {
			System.out.println(cfc);
		}
		
		System.out.println("\nCFC attendues :");
		System.out.println("- 7");
		System.out.println("- 2");
		System.out.println("- 0, 1, 8, 3, 5, 9, 6, 4");
	}
	
	/**
	 * Compteur pour remplir correctement le tableau fin.
	 * N�cessaire d'imbriquer l'entier dans une classe, si on passe l'entier directement 
	 * sa valeur n'est pas mise � jour correctement (cf passage par valeur).
	 */
	private static class Compteur {
		private int temps;
		public Compteur() { temps = 0; }
		public void incrementer() { temps += 1; } 
		public int getTemps() { return temps; }
	}


}
