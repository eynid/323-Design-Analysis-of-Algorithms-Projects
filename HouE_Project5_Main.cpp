#include <fstream>
#include <iostream>
#include <string>
using namespace std;

class Edge{
    public:
    int cost;

    Edge * next;
    
    int nU;
    
    int nW;

    Edge(int n1, int n2, int costData){
        nU = n1;
        nW = n2;
        cost = costData;
        next = nullptr;
    }

    void printEdge(Edge * Node, ofstream& outFile){
    
        outFile << "< " << Node->nU << " " << Node->nW << " " << Node->cost << " > ->" << endl;
    
    }

};
class KruskalMST{
    public:

    int numNodes;
    
    int * whichSet;
    
    int numSets;
    
    int totalMSTCost;
    
    Edge * edgeHead;
    
    Edge * mstHead;

    KruskalMST(int numberNodes){

        numNodes = numberNodes;
        
        numSets = numberNodes;
        
        totalMSTCost = 0;
        
        Edge * dummy = new Edge(0,0,0);
        
        Edge * dummy2 = new Edge(0,0,0);
        
        whichSet = new int[numNodes+1];
        
        for(int i = 0; i < numNodes+1; i++){
            whichSet[i] = i;
        }
        edgeHead = dummy;
        mstHead = dummy2;
    }

    void insertEdge(Edge * edgeHead, Edge * newEdge){
        Edge * spot = findSpot(edgeHead, newEdge);
        newEdge->next = spot->next;
        spot->next = newEdge;
    }

    Edge * removeEdge(){

        Edge * temp = edgeHead->next;

        edgeHead->next = temp->next;
        
        temp->next = nullptr;
        
        return temp;

    }
    Edge * findSpot(Edge * edgeHead, Edge * newEdge){

        Edge * spot = edgeHead;

        while(spot->next != nullptr && spot->next->cost < newEdge->cost){
            spot = spot->next;
        }
        
        return spot;

    }

    void merge2Sets(int firstNode, int secondNode){

        if(whichSet[firstNode] < whichSet[secondNode]){
        
            updateSet(whichSet[secondNode], whichSet[firstNode]);
        
        }
        
        else{
        
            updateSet(whichSet[firstNode], whichSet[secondNode]);
        
        }
    }

    void updateSet(int a, int b){
    
        for(int i = 0; i < numNodes +1; i++){
            if(whichSet[i] == a){
                whichSet[i] = b;
    
            }
    
        }
    
    }

    void printArray(int * which,ofstream& debug){
    
        for(int i = 0; i < numSets+1; i++){
            debug << which[i] << endl;
        }
    
    }
    
    void printList(Edge * listHead, ofstream& outFile){
        
        while(listHead->next != nullptr){
        
            listHead->printEdge(listHead, outFile);
            listHead = listHead->next;
        
            if(listHead->next == nullptr){
        
                listHead->printEdge(listHead, outFile);
        
            }
        }
    }


};
int main (int argc, char* argv[]){
    
    ifstream inFile;
    ofstream outFile;
    ofstream debugFile;
    inFile.open(argv[1]);
    outFile.open(argv[2]);
    debugFile.open(argv[3]);
    int numNodes = 0;
    inFile >> numNodes;
    KruskalMST * MSTCaller = new KruskalMST(numNodes);
    int u, w, cost = 0;

    while(!inFile.eof()){
        inFile >> u;
        inFile >> w;
        inFile >> cost;
        Edge * newEdge = new Edge(u, w, cost);
        MSTCaller->insertEdge(MSTCaller->edgeHead, newEdge);
    }

    MSTCaller->printList(MSTCaller->edgeHead, debugFile);

    Edge * nextEdge = MSTCaller->removeEdge();
    
    while(MSTCaller->numSets > 1){

        while(MSTCaller->whichSet[nextEdge->nU] == MSTCaller->whichSet[nextEdge->nW]){
            nextEdge = MSTCaller->removeEdge();
    
    }

    MSTCaller->insertEdge(MSTCaller->mstHead, nextEdge);
    
    MSTCaller->totalMSTCost += nextEdge->cost;
    
    MSTCaller->merge2Sets(nextEdge->nU, nextEdge->nW);
    
    //cout << "updated sets: " << MSTCaller->whichSet[nextEdge->nU] << " " << MSTCaller->whichSet[nextEdge->nW] << endl;
    
    MSTCaller->numSets--;
    
    debugFile << "Num Sets: " << MSTCaller->numSets << endl;

    
    debugFile << "Printing whichSet Array\n";
    
    MSTCaller->printArray(MSTCaller->whichSet,debugFile);
    
    debugFile << "Printing remaining edgeList\n";
    
    MSTCaller->printList(MSTCaller->edgeHead, debugFile);
    
    debugFile << "Printing growing MST list\n";
    
    MSTCaller->printList(MSTCaller->mstHead, debugFile);
    }
    
    MSTCaller->printList(MSTCaller->mstHead, outFile);
    
    outFile << " Total Cost :" << MSTCaller->totalMSTCost << endl;
    


    inFile.close();
    
    debugFile.close();
    
    outFile.close();

}