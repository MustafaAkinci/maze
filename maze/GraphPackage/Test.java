package src;

import java.io.File;
import java.util.Scanner;

import ADTPackage.LinkedQueue;
import ADTPackage.LinkedStack;
import ADTPackage.QueueInterface;
import ADTPackage.StackInterface;
import GraphPackage.*;

public class Test {
    public static void main(String[] args) {
        String[] mazeString = ReadMazeFile("Maze2.txt");
        if (mazeString == null)
            return;

        DirectedGraph<String> graph = new DirectedGraph<>();
        createVerticesAndEdges(mazeString, graph);
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Adjency Matrix (The Matrix of Big Mazes Does Not Fit The Console)");
            System.out.println("2. Breadth First Search");
            System.out.println("3. Depth First Search");
            System.out.println("4. Shortest Path");
            System.out.println("5. Cheapest Path");
            System.out.println("6. Exit");
            System.out.print("Enter Your Choice: ");
            int choice = input.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    graph.displayEdges();
                    graph.printAdjencyMatrix();
                    System.out.println("\nNumber Of edges: " + graph.getNumberOfEdges());
                    break;
                case 2:
                    // Breadth First Search
                    System.out.println("Breadth First Search");
                    QueueInterface<String> bfsqueue = graph.getBreadthFirstTraversal("0-1",
                            (mazeString.length - 2 + "-" + (mazeString[0].length() - 1)));
                    int bfsSize = printQueue(bfsqueue);
                    System.out.println("\nThe Number Of Visited Vertices For BFS: " + bfsSize);
                    System.out.println("\nGraphical Representation Of BFS");
                    printMazeQueue(bfsqueue, mazeString);
                    break;
                case 3:
                    // Depth First Search
                    System.out.println("Depth First Search");
                    QueueInterface<String> dfsQueue = graph.getDepthFirstTraversal("0-1",
                            (mazeString.length - 2 + "-" + (mazeString[0].length() - 1)));
                    int dfsSize = printQueue(dfsQueue);
                    System.out.println("\nThe Number Of Visited Vertices For BFS: " + dfsSize);
                    System.out.println("\nGraphical Representation Of DFS");
                    printMazeQueue(dfsQueue, mazeString);
                    break;
                case 4:
                    // Shortest Path
                    System.out.println("Shortest Path");
                    StackInterface<String> stack = new LinkedStack<>();

                    int shortestPathSize = graph.getShortestPath("0-1",
                            (mazeString.length - 2 + "-" + (mazeString[0].length() - 1)), stack);
                    printStack(stack);
                    System.out.println("\nThe Number Of Visited Vertices For Shortest Path: " + shortestPathSize);
                    System.out.println("\nGraphical Representation Of Shortest Path");
                    printMazeStack(stack, mazeString);
                    break;
                case 5:
                    // Cheapest Path
                    graph.addCost();
                    System.out.println("Cheapest Path");
                    stack = new LinkedStack<>();
                    double cheapestCost = graph.getCheapestPath("0-1",
                            (mazeString.length - 2 + "-" + (mazeString[0].length() - 1)),
                            stack);
                    int cheapestPathSize = printStack(stack);
                    System.out.println("\nThe Number Of Visited Vertices For Cheapest Path: " + cheapestCost);
                    System.out.println("The Cost Of Cheapest Path: " + cheapestPathSize);
                    System.out.println("\nGraphical Representation Of Cheapest Path");
                    printMazeStack(stack, mazeString);
                    break;
                case 6:
                    input.close();
                    return;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }

    private static void printMazeQueue(QueueInterface<String> queue, String[] mazeString) {
        if (queue.isEmpty())
            return;

        String[][] maze = new String[mazeString.length][mazeString[0].length()];
        for (int i = 0; i < mazeString.length; i++) {
            for (int j = 0; j < mazeString[i].length(); j++) {
                maze[i][j] = mazeString[i].charAt(j) + "";
            }
        }

        while (!queue.isEmpty()) {
            String[] str = queue.dequeue().split("-");
            maze[Integer.parseInt(str[0])][Integer.parseInt(str[1])] = ".";
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    private static void printMazeStack(StackInterface<String> stack, String[] mazeString) {
        if (stack.isEmpty())
            return;

        String[][] maze = new String[mazeString.length][mazeString[0].length()];
        for (int i = 0; i < mazeString.length; i++) {
            for (int j = 0; j < mazeString[i].length(); j++) {
                maze[i][j] = mazeString[i].charAt(j) + "";
            }
        }

        while (!stack.isEmpty()) {
            String[] str = stack.pop().split("-");
            maze[Integer.parseInt(str[0])][Integer.parseInt(str[1])] = ".";
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    private static int printStack(StackInterface<String> stack) {
        int size = 0;
        if (stack.isEmpty())
            return 0;

        StackInterface<String> temp = new LinkedStack<>();
        while (!stack.isEmpty()) {
            String str = stack.pop();
            temp.push(str);
            System.out.print("(" + str + ")");
            if (!stack.isEmpty())
                System.out.print("->");
            size++;
        }
        while (!temp.isEmpty())
            stack.push(temp.pop());
        return size;
    }

    private static int printQueue(QueueInterface<String> queue) {
        if (queue.isEmpty())
            return 0;

        int size = 1;
        System.out.print("(" + queue.dequeue() + ")");
        QueueInterface<String> temp = new LinkedQueue<>();
        while (!queue.isEmpty()) {
            String str = queue.dequeue();
            temp.enqueue(str);
            System.out.print("->(" + str + ")");
            size++;
        }
        while (!temp.isEmpty())
            queue.enqueue(temp.dequeue());
        return size;
    }

    private static void createVerticesAndEdges(String[] mazeString, DirectedGraph<String> graph) {
        for (int i = 0; i < mazeString.length - 1; i++) {
            for (int j = 0; j < mazeString[i].length() - 1; j++) {
                if (mazeString[i].charAt(j) != '#') {
                    if (!graph.contains(i + "-" + j))
                        graph.addVertex(i + "-" + j);
                } else
                    continue;

                if (mazeString[i + 1].charAt(j) != '#') {
                    if (!graph.contains((i + 1) + "-" + j))
                        graph.addVertex((i + 1) + "-" + j);
                    graph.addEdge(i + "-" + j, (i + 1) + "-" + j);
                }

                if (mazeString[i].charAt(j + 1) != '#') {
                    if (!graph.contains(i + "-" + (j + 1)))
                        graph.addVertex(i + "-" + (j + 1));
                    graph.addEdge(i + "-" + j, i + "-" + (j + 1));
                }

                if (i > 1 && mazeString[i - 1].charAt(j) != '#') {
                    if (!graph.contains((i - 1) + "-" + j))
                        graph.addVertex((i - 1) + "-" + j);
                    graph.addEdge(i + "-" + j, (i - 1) + "-" + j);
                }

                if (j > 1 && mazeString[i].charAt(j - 1) != '#') {
                    if (!graph.contains(i + "-" + (j - 1)))
                        graph.addVertex(i + "-" + (j - 1));
                    graph.addEdge(i + "-" + j, i + "-" + (j - 1));
                }
            }
        }
    }

    private static String[] ReadMazeFile(String fileName) {
        try {
            Scanner scn = new Scanner(new File(fileName));
            String mazeStr = "";
            while (scn.hasNextLine()) {
                mazeStr += scn.nextLine() + "-";
            }

            return mazeStr.split("-");
        } catch (Exception e) {
            System.out.println("File Not Found!");
            return null;
        }
    }
}
