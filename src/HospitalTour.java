import java.util.*;
import java.io.*;

// write your matric number here: A0102800	
// write your name here: Suranjana Sengupta
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class HospitalTour {
	private int V; // number of vertices in the graph (number of rooms in the
					// hospital)
	private int[][] AdjMatrix; // the graph (the hospital)
	private int[] RatingScore; // the weight of each vertex (rating score of
								// each room)

	private List<List<Integer>> adjList;
	private List<Integer> importantRooms;
	private boolean isSource = false;

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class

	public HospitalTour() {
		adjList = new ArrayList<List<Integer>>();
		importantRooms = new ArrayList<Integer>();
	}

	int Query() {
		adjList.clear();
		importantRooms.clear();
		int ans = 0;

		if (V <= 2) {
			ans = -1;
		} else {
			findImportantRooms();

			if (importantRooms.isEmpty()) {
				ans = -1;
			} else {
				int min = 100000;
				for (int i = 0; i < importantRooms.size(); i++) {
					if (RatingScore[importantRooms.get(i)] < min) {
						min = RatingScore[importantRooms.get(i)];
					}
				}

				ans = min;
			}
		}

		return ans;
	}

	void createAdjList() {

		adjList.clear();
		for (int i = 0; i < V; i++) {
			List<Integer> tempList = new ArrayList<Integer>();
			for (int j = 0; j < V; j++) {
				if (AdjMatrix[i][j] == 1) {
					tempList.add(j);
				}
			}
			Collections.sort(tempList);
			adjList.add(tempList);
		}
	}

	void findImportantRooms() {

		createAdjList(); // O(V^2) Complexity

		for (int i = 0; i < V; i++) { // O(V) Complexity
			List<Integer> neighbours = new ArrayList<Integer>();

			if (i == 0) {
				isSource = true;
			} else {
				isSource = false;
			}

			neighbours.addAll(adjList.get(i));
			adjList.get(i).clear();

			for (int j = 0; j < neighbours.size(); j++) { // O(E) Complexity
				int m = neighbours.get(j);
				adjList.get(m).remove(adjList.get(m).indexOf(i));
				Collections.sort(adjList.get(neighbours.get(j)));
			}

			int count = BFS(adjList); // O(V+E) Complexity
			if (count != V - 1) {
				importantRooms.add(i);
			}

			for (int j = 0; j < neighbours.size(); j++) { // O(E) Complexity
				int m = neighbours.get(j);
				adjList.get(m).add(i);
				adjList.get(i).add(m);
				Collections.sort(adjList.get(j));
			}

			neighbours.clear();
		}
	}

	int BFS(List<List<Integer>> tempList) { // Breadth-first Search, Return:
											// count of vertices

		int visited[] = new int[V];
		int countV = 1;
		MyQueue queue = new MyQueue();

		for (int i = 0; i < V; i++) {
			visited[i] = 0;
		}

		if (isSource == true) {
			queue.enqueue(1);
			visited[1] = 1;
		} else {
			queue.enqueue(0);
			visited[0] = 1;
		}

		while (!queue.isEmpty()) {
			int u = queue.dequeue();
			for (int i = 0; i < tempList.get(u).size() && !tempList.get(u).isEmpty(); i++) {
				int temp = tempList.get(u).get(i);
				if (visited[temp] == 0) {
					visited[temp] = 1;
					countV++;
					queue.enqueue(temp);
				}

			}

		}

		return countV;

	}

	void run() throws Exception {
		// for this PS3, you can alter this method as you see fit

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int TC = Integer.parseInt(br.readLine()); // there will be several test
													// cases
		while (TC-- > 0) {
			br.readLine(); // ignore dummy blank line
			V = Integer.parseInt(br.readLine());

			StringTokenizer st = new StringTokenizer(br.readLine());
			// read rating scores, A (index 0), B (index 1), C (index 2), ...,
			// until the V-th index
			RatingScore = new int[V];
			for (int i = 0; i < V; i++)
				RatingScore[i] = Integer.parseInt(st.nextToken());

			// clear the graph and read in a new graph as Adjacency Matrix
			AdjMatrix = new int[V][V];
			for (int i = 0; i < V; i++) {
				st = new StringTokenizer(br.readLine());
				int k = Integer.parseInt(st.nextToken());
				while (k-- > 0) {
					int j = Integer.parseInt(st.nextToken());
					AdjMatrix[i][j] = 1; // edge weight is always 1 (the weight
											// is on vertices now)
				}
			}

			pr.println(Query());
		}
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		HospitalTour ps3 = new HospitalTour();
		ps3.run();
	}
}

class MyQueue {

	private LinkedList<Integer> queue;

	MyQueue() {
		queue = new LinkedList<Integer>();
	}

	int peek() {
		return queue.peek();
	}

	void enqueue(int e) {
		queue.addLast(e);
	}

	int dequeue() {
		if (!queue.isEmpty()) {
			return queue.removeFirst();
		} else {
			return -1;
		}
	}

	boolean isEmpty() {
		return queue.isEmpty();
	}
}
