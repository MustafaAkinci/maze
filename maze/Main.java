package mazework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ADTPackage.LinkedStack;
import ADTPackage.QueueInterface;
import ADTPackage.StackInterface;
import GraphPackage.DirectedGraph;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String maze ="maze1.txt";
		File filee= new File(maze);
		Scanner sc=new Scanner(filee);
		int a=0;
		int mazerow=0;
		int mazecolumn=0;
		String[] mazerowcount;
		String[] mazecolumncount;
		while(sc.hasNextLine()) {
			mazerowcount=sc.nextLine().split("");
			mazecolumn=mazerowcount.length;
			mazerow=mazerow+1;
		}
		//System.out.println(mazecolumn+" "+mazerow);
		String[][] mazeArea=new String[mazerow][mazecolumn];
		sc=new Scanner(filee);
		while(sc.hasNextLine()) {
			mazecolumncount=sc.nextLine().split("");
			for(int i=0;i<mazecolumn;i++) {
				mazeArea[a][i]=mazecolumncount[i];
			}
			a=a+1;
		}
		sc.close();
		for(int i=0;i<mazerow;i++) {
			for(int j=0;j<mazecolumn;j++) {
				System.out.print(mazeArea[i][j]);
			}
			System.out.println();
		}
		DirectedGraph<String> graph=new DirectedGraph<String>();
		//graph.addVertex(i+"-"+j);
		//sc=new Scanner(filee);
		for(int i=0;i<mazerow;i++) {
			for(int j=0;j<mazecolumn;j++) {
				if(mazeArea[i][j].equals(" ") ){
					graph.addVertex(i+"-"+j);
				}
				
			}
		}
		//graph.addedge
		String[][] location1=new String[1][1];//start point
		for(int i=0;i<mazecolumn;i++) {
			if(mazeArea[0][i]==" ") {
				location1[0][0]=mazeArea[0][i];
				break;
			}
		}
		//int firstrow=0;
		//int firstcolumn=1;
		/*String[][] location2=new String[1][1];//finish point
		for(int i=0;i<mazecolumn;i++) {
			if(mazeArea[mazerow][i]==" ") {
				location2[0][0]=mazeArea[mazerow][i];
				break;
			}
		}*/
		for(int i=0;i<mazerow;i++) {
			for(int j=0;j<mazecolumn;j++) {
				if(i<mazerow-1&&mazeArea[i][j].equals(" ")&&mazeArea[i+1][j].equals(" ")) {
					graph.addEdge(i+"-"+j,((i+1)+"-"+(j)));
				}
				if(j<mazecolumn-1&&mazeArea[i][j].equals(" ")&&mazeArea[i][j+1].equals(" ")) {
					graph.addEdge(i+"-"+j,((i)+"-"+(j+1)));
				}
				if(i > 2 && mazeArea[i][j].equals(" ")&&mazeArea[i-1][j].equals(" ")) {
					graph.addEdge(i+"-"+j,((i-1)+"-"+(j)));
				}
				if(j > 2 && mazeArea[i][j].equals(" ")&&mazeArea[i][j-1].equals(" ")) {
					graph.addEdge(i+"-"+j,((i)+"-"+(j-1)));
				}
			}
		}
		String endvertex=(mazerow-2) + "-"+ (mazecolumn-1);
		
		System.out.println("1-Adjency Matrix" );
		graph.displayEdges();

		System.out.println("2-Breadth First Search" );
		QueueInterface<String> breadthFirstSearch = graph.getBreadthFirstTraversal("0-1",endvertex);
		int bfsSize = printMazeFromQueue(breadthFirstSearch, mazeArea);
		System.out.println("Breadth First Search Number of Visited Vertices: " + bfsSize);
		System.out.println();
		
		System.out.println("3-Depth First Search" );
		QueueInterface<String> depthFirstSearch = graph.getDepthFirstTraversal("0-1",endvertex);
		int dfsSize = printMazeFromQueue(depthFirstSearch, mazeArea);
		System.out.println("Depth First Search Number of Visited Vertices: " + dfsSize);
		System.out.println();
		
		System.out.println("4-Shortest Path" );
		StackInterface<String> stack = new LinkedStack<>();
		int shortPathSize = graph.getShortestPath("0-1",endvertex, stack);
		int spSize = printMazeFromStack(stack, mazeArea);
		System.out.println("Shortest Path Search Number of Visited Vertices: " + spSize);
		System.out.println();
		
		System.out.println("5-Cheapest Path" );
		stack = new LinkedStack<>();
		double cheapestCost=graph.getCheapestPath("0-1",endvertex, stack);
		int cpSize = printMazeFromStack(stack, mazeArea);
		System.out.println("Cheapest Path Search Number of Visited Vertices: " + cpSize);
		System.out.println("Cost: " + cheapestCost);

        
	}

	static int printMazeFromStack(StackInterface<String> stack, String[][] maze){
		int size = 0;
		String elem = "";

		for (int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++){
				if(!stack.isEmpty() && elem.equals(""))
				elem = stack.pop();

				if(elem.equals(i+"-"+j)){
					System.out.print(".");
					elem = "";
					size++;
				}
				else 
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}
		return size;

	}

	static int printMazeFromQueue(QueueInterface<String> queue, String[][] maze){
		String elem= "";
		int size = 0;
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if(!queue.isEmpty()&& elem.equals(""))
					elem = queue.dequeue();
				if(elem.equals(i+"-"+j)){
					System.out.print(".");
					elem = "";
					size++;
				}
				else
					System.out.print(maze[i][j]);
			}
			System.out.println();
		}
		return size;
	}

}

