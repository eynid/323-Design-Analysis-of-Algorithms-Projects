#include <fstream>
#include <iostream>
#include <string>
using namespace std;

class node{
    public:
    int id;
    node * next;
    node(int cID){
        id = cID;
        next = nullptr;
    }
};
class color{
    public: 

    int numNodes;
    int numUncolored;
    node ** hashTable;
    int * colorAry;

    color(int nums){

       numNodes = nums;
       numUncolored = nums;
       hashTable = new node*[numNodes+1];
       colorAry = new int[numNodes + 1];
       
       for(int i = 0; i < numNodes+1; i++){
            hashTable[i] = new node(-999);
            colorAry[i] = 0;
       }

    }

    void loadGraph(ifstream& infile){

        while(!infile.eof()){
        
            int edge1; 
            int edge2;
            infile >> edge1;
            infile >> edge2;
            hashInsert(edge1, edge2);
        
        }
    }

    void hashInsert(int id1, int id2){

        node * firstAdjacent = new node(id1);
        node * secondAdjacent = new node(id2);

        secondAdjacent->next = hashTable[id1]->next;
        hashTable[id1]->next = secondAdjacent;
        
        firstAdjacent->next = hashTable[id2]->next;
        hashTable[id2]->next = firstAdjacent;

    }

    void printHashTable(ofstream& outFile){
        for(int i = 1; i < numNodes +1; i++){
            node * temp = hashTable[i];

            outFile << "Printing : hash Table[" << i << "] ";
        
            while(temp != nullptr){
                outFile << " -> " << temp->id;
                temp = temp->next;
            }
        
            outFile << "\n";
        
        }
    }

    void method1(ofstream& debug, ofstream& outfile){
        int newColor = 64;

        while(numUncolored > 0){
          newColor++;
          int nodeID = 1; 

          while(nodeID <= numNodes){
          
          if(colorAry[nodeID] == 0){
            if(checkNeighbors(nodeID, newColor) == true){
                colorAry[nodeID] = newColor;
                numUncolored--;
            }
         }
           nodeID++;
        }
        }
        printAry(outfile);

    }

    void method2(ofstream& debug, ofstream& outfile){
        debug << "Entering method 2 " << endl;
        int lastUsedColor = 64;
        int nextNodeID = 0;
        
        //Step 7
        while(nextNodeID < numNodes){
         //Step 1:
         nextNodeID++;
         //Step 2:
         int nextUsedColor = 64+1;
         bool coloredFlag = false;
         while(coloredFlag == false && nextUsedColor <= lastUsedColor){
          //Step 3: 
          if(lastUsedColor > 64 && checkNeighbors(nextNodeID, nextUsedColor) == true){
             colorAry[nextNodeID] = nextUsedColor;
             coloredFlag = true;
            
            }
          else{
            
            nextUsedColor++;
           
           }
        }

         if(coloredFlag == false){
            lastUsedColor++;
            colorAry[nextNodeID] = lastUsedColor;
            debug << lastUsedColor << endl;
        
        }
        }
        printAry(outfile);

    }

    bool checkNeighbors(int nodeID, int color){

        node * nextNode = hashTable[nodeID];

        if(nextNode == nullptr){
            return true;
        }
        
        else{
        
        while(nextNode != nullptr){
        
            if(colorAry[nextNode->id] == color){
                return false;
            }
        
            else if(nextNode == nullptr){
                return true;
            }          
        
            nextNode = nextNode->next;  
        
        }

        }
        return true;
    
    }

    void printAry(ofstream& outfile){
        outfile << numNodes << endl;
        for(int i = 1; i < numNodes+1; i++){
            outfile << i << " " << (char)colorAry[i] << endl;
        }
    }

};
int main (int argc, char* argv[]){
    
    ifstream inFile;
    ofstream outFile;
    ofstream debugFile;
    inFile.open(argv[1]);
    outFile.open(argv[3]);
    debugFile.open(argv[4]);
    
    int nNodes = 0;
    inFile >> nNodes;
    color * colorCaller = new color(nNodes);

    colorCaller->loadGraph(inFile);
    colorCaller->printHashTable(debugFile);
    
    char * Method = argv[2];
    int whichMethod = atoi(Method);
    switch (whichMethod)
    {
    case 1:
        colorCaller->method1(debugFile, outFile);
        break;

    case 2:
        colorCaller->method2(debugFile, outFile);
        break;
    
    default:
        debugFile << "Argument must be 1 or 2. Exiting.\n";
        exit(0);
    }

    inFile.close();
    
    debugFile.close();
    
    outFile.close();

}