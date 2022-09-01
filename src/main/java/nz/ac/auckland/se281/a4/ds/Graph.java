package nz.ac.auckland.se281.a4.ds;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.ArrayList;

import nz.ac.auckland.se281.a4.TwitterHandle;
//*******************************
//YOU SHOUD MODIFY THE SPECIFIED 
//METHODS  OF THIS CLASS
//HELPER METHODS CAN BE ADDED
//REQUIRED LIBRARIES ARE ALREADY 
//IMPORTED -- DON'T ADD MORE
//*******************************

public class Graph {

	/**
	 * Each node maps to a list of all the outgoing edges from that node
	 */
	protected Map<Node<String>, LinkedList<Edge<Node<String>>>> adjacencyMap;
	/**
	 * root of the graph, to know where to start the DFS or BFS
	 */
	protected Node<String> root;

	/**
	 * !!!!!! You cannot change this method !!!!!!!
	 */

	/**
	 * Creates a Graph
	 * 
	 * @param edges a list of edges to be added to the graph
	 */
	public Graph(List<String> edges) {
		adjacencyMap = new LinkedHashMap<>();
		int i = 0;
		if (edges.isEmpty()) {
			throw new IllegalArgumentException("edges are empty");
		}

		for (String edge : edges) {
			String[] split = edge.split(",");
			Node<String> source = new Node<String>(split[0]);
			Node<String> target = new Node<String>(split[1]);
			Edge<Node<String>> edgeObject = new Edge<Node<String>>(source, target);

			if (!adjacencyMap.containsKey(source)) {
				adjacencyMap.put(source, new LinkedList<Edge<Node<String>>>());
			}
			adjacencyMap.get(source).append(edgeObject);

			if (i == 0) {
				root = source;
			}
			i++;
		}
	}

	/**
	 * This method returns a boolean based on whether the input sets are reflexive.
	 * 
	 * TODO: Complete this method (Note a set is not passed in as a parameter but a
	 * list)
	 * 
	 * @param set      A list of TwitterHandles
	 * @param relation A relation between the TwitterHandles
	 * @return true if the set and relation are reflexive
	 */
	public boolean isReflexive(List<String> set, List<String> relation) {
		// empty set is reflexive
		if (relation == null) {
			return true;
		}
		int count = 0;
		// initalise counter and count if all the reflexive relationships equal to the
		// number in the set
		for (String element : set) {
			for (String relations : relation) {
				// for each realtion, split the nodes
				String[] r = relations.split(",");
				if (r[0].equals(r[1]) && r[0].equals(element)) {
					count++;
				}

			}
		}
		return count == set.size();
	}

	/**
	 * This method returns a boolean based on whether the input set is symmetric.
	 * 
	 * If the method returns false, then the following must be printed to the
	 * console: "For the graph to be symmetric tuple: " + requiredElement + " MUST
	 * be present"
	 * 
	 * TODO: Complete this method (Note a set is not passed in as a parameter but a
	 * list)
	 * 
	 * @param relation A relation between the TwitterHandles
	 * @return true if the relation is symmetric
	 */
	public boolean isSymmetric(List<String> relation) {
		// empty set is symmetric
		if (relation == null) {
			return true;
		}
		for (String relations : relation) {
			// for each realtion, split the nodes
			String[] r = relations.split(",");
			if (relation.contains(String.format("%s,%s", r[0], r[1]))) {
				// check if the relations contains symmetric relations
				if (!relation.contains(String.format("%s,%s", r[1], r[0]))) {
					System.out
							.println("For the graph to be symmetric tuple: " + r[1] + "," + r[0] + " MUST be present");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method returns a boolean based on whether the input set is transitive.
	 * 
	 * If the method returns false, then the following must be printed to the
	 * console: "For the graph to be transitive tuple: " + requiredElement + " MUST
	 * be present"
	 * 
	 * TODO: Complete this method (Note a set is not passed in as a parameter but a
	 * list)
	 * 
	 * @param relation A relation between the TwitterHandles
	 * @return true if the relation is transitive
	 */
	public boolean isTransitive(List<String> relation) {
		// empty set is not transitive
		if (relation == null) {
			return false;
		}
		for (String relations : relation) {
			// for each realtion, split the nodes
			String[] r = relations.split(",");
			if (!r[0].equals(r[1])) {
				// if not reflexive
				for (String relations1 : relation) {
					// for each realtion, split the nodes
					String[] s = relations1.split(",");
					if (r[1].equals(s[0])) {
						if (!relation.contains(String.format("%s,%s", r[0], s[1]))) {
							// if does not contain transitive relation
							System.out.println(
									"For the graph to be transitive tuple: " + r[0] + "," + s[1] + " MUST be present");
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * This method returns a boolean based on whether the input sets are
	 * anti-symmetric TODO: Complete this method (Note a set is not passed in as a
	 * parameter but a list)
	 * 
	 * @param set      A list of TwitterHandles
	 * @param relation A relation between the TwitterHandles
	 * @return true if the set and relation are anti-symmetric
	 */
	public boolean isEquivalence(List<String> set, List<String> relation) {
		// equivalent if reflexive, symmetric and transitive
		return (isReflexive(set, relation) && isSymmetric(relation) && isTransitive(relation));
	}

	/**
	 * This method returns a List of the equivalence class
	 * 
	 * If the method can not find the equivalence class, then The following must be
	 * printed to the console: "Can't compute equivalence class as this is not an
	 * equivalence relation"
	 * 
	 * TODO: Complete this method (Note a set is not passed in as a parameter but a
	 * list)
	 * 
	 * @param node     A "TwitterHandle" in the graph
	 * @param set      A list of TwitterHandles
	 * @param relation A relation between the TwitterHandles
	 * @return List that is the equivalence class
	 */
	public List<String> computeEquivalence(String node, List<String> set, List<String> relation) {
		// check if relation is equivalent
		if (!isEquivalence(set, relation) || !set.contains(node)) {
			System.out.println("Can't compute equivalence class as this is not an equivalence relation");
			return null;
		}
		List<String> eqRelations = new ArrayList<String>();
		for (String relations : relation) {
			String[] r = relations.split(",");
			// get equivalent relations based off the first node in relation
			if (r[1].equals(node)) {
				eqRelations.add(r[0]);
			}
		}
		return eqRelations;
	}

	/**
	 * This method returns a List nodes using the BFS (Breadth First Search)
	 * algorithm
	 * 
	 * @return List of nodes (as strings) using the BFS algorithm
	 */
	public List<Node<String>> breadthFirstSearch() {
		return breadthFirstSearch(root, false);
	}

	/**
	 * This method returns a List nodes using the BFS (Breadth First Search)
	 * algorithm
	 * 
	 * @param start A "TwitterHandle" in the graph
	 * @return List of nodes (as strings) using the BFS algorithm
	 */
	public List<Node<String>> breadthFirstSearch(Node<String> start, boolean rooted) {// name to breadthFirstSearch
		// initialise queue for nodes visited and need to visit
		ArrayList<Node<String>> toVisit = new ArrayList<Node<String>>();
		ArrayList<Node<String>> visited = new ArrayList<Node<String>>();
		// if rooted start with start, else start with first node in adjacencyMap
		toVisit.add(start);
		if (rooted) {
			while (!toVisit.isEmpty()) {
				Node<String> node = toVisit.remove(0);
				if (visited.contains(node)) {
					continue;
				}
				// add node to visited
				visited.add(node);
				// for the node in the graph visit is not yet visited
				for (Node<String> n : adjacencyMap.keySet()) {
					if (!visited.contains(n)) {
						toVisit.add(n);
					}
				}
			}
		} else {
			// To get all the nodes in the graph
			while (getAllNodes().size() != visited.size()) {
				// until all nodes are visited
				while (!toVisit.isEmpty()) {
					Node<String> node = toVisit.remove(0);
					if (visited.contains(node)) {
						continue;
					}
					// add node to visited
					visited.add(node);
					// for the node in the graph visit is not yet visited
					for (Node<String> n : adjacencyMap.keySet()) {
						if (!visited.contains(n)) {
							toVisit.add(n);
						}
					}
				}

				// if there is a portion of the graph that is not connected search through those
				// by adding to to visit
				for (Node<String> n : adjacencyMap.keySet()) {
					if (!visited.contains(n)) {
						toVisit.add(n);
						break;
					}
				}
			}
		}
		return visited;
	}

	/**
	 * This method returns a List nodes using the DFS (Depth First Search) algorithm
	 * 
	 * @return List of nodes (as strings) using the DFS algorithm
	 */
	public List<Node<String>> depthFirstSearch() {
		return depthFirstSearch(root, false);
	}

	/**
	 * This method returns a List nodes using the DFS (Depth First Search) algorithm
	 * 
	 * @param start A "TwitterHandle" in the graph
	 * @return List of nodes (as strings) using the DFS algorithm
	 */
	public List<Node<String>> depthFirstSearch(Node<String> start, boolean rooted) {
		// initialise nodes visited and stack of nodes to visit
		NodesStackAndQueue<Node<String>> toVisit = new NodesStackAndQueue<Node<String>>();
		ArrayList<Node<String>> visited = new ArrayList<Node<String>>();
		// if rooted start with start, else start with the first element in adjacencyMap
		toVisit.push(start);
		visited.add(start);
		if (rooted) {
			while (!toVisit.isEmpty()) {
				Node<String> node = toVisit.pop();
				// remove node from stack to visit and add to visited nodes
				if (!visited.contains(node)) {
					visited.add(node);
				}
				// for all the edges in the graph
				for (Edge<Node<String>> n : getAllEdges()) {
					// check for the children of n
					if (n.getSource().getValue().equals(node.getValue())) {
						// if there is a neighbour not yet visited add to to visit
						Node<String> next = n.getTarget();
						if (!visited.contains(next)) {
							toVisit.push(next);
						}
					}
				}
			}

		} else {
			// To get all the nodes in the graph
			while (getAllNodes().size() != visited.size()) {
				// until no more nodes left to visit
				while (!toVisit.isEmpty()) {
					Node<String> node = toVisit.pop();
					// remove node from stack to visit and add to visited nodes
					if (!visited.contains(node)) {
						visited.add(node);
					}
					// for all the edges in the graph
					for (Edge<Node<String>> n : getAllEdges()) {
						// check for the children of n
						if (n.getSource().getValue().equals(node.getValue())) {
							// if there is a neighbour not yet visited add to to visit
							Node<String> next = n.getTarget();
							if (!visited.contains(next)) {
								toVisit.push(next);
							}
						}
					}
				}
				// if there is a portion of the graph that is not connected search through those
				// by adding to to visit
				for (Node<String> n : adjacencyMap.keySet()) {
					if (!visited.contains(n)) {
						toVisit.push(n);
						break;
					}
				}
			}
		}
		return visited;
	}

	/**
	 * @return returns the set of all nodes in the graph
	 */
	public Set<Node<String>> getAllNodes() {

		Set<Node<String>> out = new HashSet<>();
		out.addAll(adjacencyMap.keySet());
		for (Node<String> n : adjacencyMap.keySet()) {
			LinkedList<Edge<Node<String>>> list = adjacencyMap.get(n);
			for (int i = 0; i < list.size(); i++) {
				out.add(list.get(i).getSource());
				out.add(list.get(i).getTarget());
			}
		}
		return out;
	}

	/**
	 * @return returns the set of all edges in the graph
	 */
	protected Set<Edge<Node<String>>> getAllEdges() {
		Set<Edge<Node<String>>> out = new HashSet<>();
		for (Node<String> n : adjacencyMap.keySet()) {
			LinkedList<Edge<Node<String>>> list = adjacencyMap.get(n);
			for (int i = 0; i < list.size(); i++) {
				out.add(list.get(i));
			}
		}
		return out;
	}

	/**
	 * @return returns the set of twitter handles in the graph
	 */
	public Set<TwitterHandle> getUsersFromNodes() {
		Set<TwitterHandle> users = new LinkedHashSet<TwitterHandle>();
		for (Node<String> n : getAllNodes()) {
			users.add(new TwitterHandle(n.getValue()));
		}
		return users;
	}

}
