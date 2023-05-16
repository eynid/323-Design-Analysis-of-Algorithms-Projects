import java.io.*;
import java.util.*;


class DijsktraSSS{
    int numNodes;
    int sourceNode;
    int minNode; 
    int currentNode; 
    int costMatrix[][];
    int parentAry[];
    int toDoArray[];
    int bestArray[];
    
    DijsktraSSS(int numberofNodes){
        numNodes = numberofNodes;
        costMatrix = new int[numNodes+1][numNodes+1];
        bestArray = new int[numNodes+1];
        toDoArray = new int[numNodes+1];
        parentAry = new int[numNodes+1];
        for(int i = 0; i < numNodes+1; i++){
            for(int j = 0; j < numNodes+1; j++){
                if(i == j){
                    costMatrix[i][j] = 0;
                }
                else{
                    costMatrix[i][j] = 9999;
                }
            }
        }
    }

    void loadCostMatrix(Scanner inFile){
        while(inFile.hasNextInt()){
            int firstNode = inFile.nextInt();
            int secondNode = inFile.nextInt();
            int cost = inFile.nextInt();
            costMatrix[firstNode][secondNode] = cost;
        }

    }

    void setBestAry(int sourcePoint){
        bestArray = new int[numNodes+1];
        for(int i = 0; i < numNodes+1; i++){
            bestArray[i] = costMatrix[sourcePoint][i];
        }
    }

    void setParentAry(int sourcePoint){
        parentAry = new int[numNodes+1];
        for(int i = 0; i < numNodes+1; i++){
            parentAry[i] = sourcePoint;
        }

    }

    void setToDoAry(int sourcePoint){
        toDoArray = new int[numNodes+1];
        for(int i = 0; i < numNodes+1; i++){
            if(i == sourcePoint){
                toDoArray[i] = 0;
            }
            else{
                toDoArray[i] = 1;
            }
        }
        
        toDoArray[0] = 0;
    }

    int computeCost(int minNode, int presentNode){
        return bestArray[minNode] + costMatrix[minNode][presentNode];
    }
    
    boolean toDoEmptyCheck(){
        for(int i  = 0; i < numNodes +1; i++){
            if(toDoArray[i] == 1){
                return false;
            }
        }
        return true;
    }
    
    void debugPrint(FileWriter debugFile) throws IOException{

        debugFile.write("Source Node: " + sourceNode + "\n");
        
        for(int i = 0; i < numNodes + 1; i++){
            debugFile.write( "Counter:" + i  + " Best Value: " + bestArray[i] + " Parent Node :" + parentAry[i] 
            + " ToDoArray: " + toDoArray[i] + "\n");
        
        }
    }

    void printShortestPath(int currentNode, int sourceNode, FileWriter outfile) throws IOException{
        int traceTotal = bestArray[currentNode];
        outfile.write("Path from " + sourceNode + " to " + currentNode + " : " + currentNode);
        while(currentNode != sourceNode){
            outfile.write("-> " + parentAry[currentNode]);
            currentNode = parentAry[currentNode];
        }

        outfile.write(" Total cost: " + traceTotal + "\n");
    }

    int findMinNode(){

        int minCost = 99999;
        int minNode = 0;
        int index = 1;
        
        while(index <= numNodes){
          if(toDoArray[index] == 1 && bestArray[index] < minCost){
           minCost = bestArray[index];
           minNode = index;
            }
            index++;
        }

        return minNode;
    }


}   

public class HouE_Project6_Main {
    public static void main(String[] args) throws IOException{

        Scanner inFile = new Scanner(new FileReader(args[0]));
        FileWriter outFile = new FileWriter(args[1]);
        FileWriter debugFile = new FileWriter(args[2]);
        
        int numberofNodes = inFile.nextInt();

        DijsktraSSS pathCaller = new DijsktraSSS(numberofNodes);
        
        pathCaller.sourceNode = 1;
        
        pathCaller.loadCostMatrix(inFile);
        
        while(pathCaller.sourceNode <= pathCaller.numNodes){ 
            //Step 2 : Completed 
          pathCaller.setBestAry(pathCaller.sourceNode);
          pathCaller.setParentAry(pathCaller.sourceNode);
          pathCaller.setToDoAry(pathCaller.sourceNode);
        
          //Step 8: Completed.
          while(pathCaller.toDoEmptyCheck() != true){
            //Step 3: Completed.
           pathCaller.minNode = pathCaller.findMinNode();
           pathCaller.toDoArray[pathCaller.minNode] = 0;
           
           //Step 4: Completed.
           int childNode = 1;
          
           while(childNode <= numberofNodes){ // Step 7:
            if(pathCaller.toDoArray[childNode] == 1){
                //Step 5: Completed.
              int newCost = pathCaller.computeCost(pathCaller.minNode, childNode);
              if(newCost < pathCaller.bestArray[childNode]){
                  pathCaller.bestArray[childNode] = newCost;
                  pathCaller.parentAry[childNode] = pathCaller.minNode;
                  pathCaller.debugPrint(debugFile);
              }
         }
         //Step 6:
           childNode++;
    }
}
        pathCaller.currentNode = 1;
        outFile.write("Source Node = " + pathCaller.sourceNode + "\n");

        while(pathCaller.currentNode <= pathCaller.numNodes){
          pathCaller.printShortestPath(pathCaller.currentNode, pathCaller.sourceNode, outFile);
          pathCaller.currentNode++;
          
        }
        pathCaller.sourceNode++;
    }


        inFile.close();
        outFile.close();
        debugFile.close();
    }
}