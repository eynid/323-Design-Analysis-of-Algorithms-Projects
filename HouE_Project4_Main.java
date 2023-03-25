import java.io.*;
import java.util.*;


class treeNode{
    int key1;
    int key2;
    int rank;
    treeNode child1;
    treeNode child2;
    treeNode child3;
    treeNode parent;

    treeNode(int firstKey, int secondKey, int rRank, treeNode firstNode, treeNode secondNode, treeNode thirdNode, treeNode parentNode){
        key1 = firstKey;
        key2 = secondKey;
        rank = rRank;
        child1 = firstNode;
        child2 = secondNode;
        child3 = thirdNode;
        parent = parentNode;
    }
    void printNode(treeNode t, FileWriter outFile) throws IOException{
        if(t.parent != null){

            if(t.child1 == null){
                outFile.write("( " + t.key1 + ", " + t.key2 + ", " + rank + " null, null, null, " + t.parent.key1 + " )");
            }
            else if(t.child1 != null && t.child3 == null){
                outFile.write("(" + t.key1 + ", " + t.key2 + ", " + rank + ", " + t.child1.key1 + ", " + t.child2.key1 + ", null, " + t.parent.key1 + ")");
            }
            else{
                outFile.write("(" + t.key1 + ", " + t.key2 + ", " + rank + ", " + t.child1.key1 + ", " + t.child2.key1 + ", " + t.child3.key1 + ", " + t.parent.key1 + ")");
        }
        }
        else{
            if(t.child1 == null){
                outFile.write("( " + t.key1 + ", " + t.key2 + ", " + rank + " null, null, null, null) ");
            }
            else if(t.child1 != null && t.child3 == null){
                outFile.write("( " + t.key1 + ", " + t.key2 + ", " + rank + ", " + t.child1.key1 + ", " + t.child2.key1 + ", null , null) ");
            }
            else{
                outFile.write("( " + t.key1 + ", " + t.key2 + ", " + rank + ", " + t.child1.key1 + ", " + t.child2.key1 + ", " + t.child3.key1 + ", null) ");
            }
        }
    }
    void printTraversal(treeNode traversalNode, FileWriter outFile) throws IOException{
        outFile.write("(" +traversalNode.key1 + ", " + traversalNode.key2 + ", " + traversalNode.rank + ") \n");
    }
    
}
class Tree{
    treeNode Root;

    Tree(){
        Root = new treeNode(-1, -1, -1, null, null, null, null);
    }


    treeNode initialTree(Scanner inFile, FileWriter debugFile) throws IOException{

        debugFile.write("Entering initial tree \n");

        int data1 = 0; 
        
        int data2 = 0;

        data1 = inFile.nextInt();

        data2 = inFile.nextInt();

        System.out.println("Before Swap: " + data1 + " " + data2 + " ");

        if(data2 < data1){

            data1 = data1 ^ data2;
            
            data2 = data1 ^ data2;
            
            data1 = data1 ^ data2;
        }

        debugFile.write("After Swap: " + data1 + " " + data2 + " ");
        treeNode childNode1 = new treeNode(data1, -1, 1, null, null, null, Root);
        treeNode childNode2 = new treeNode(data2, -1, 1, null, null, null, Root);
        
        Root.child1 = childNode1;
        Root.child2 = childNode2;
        Root.key1 = data2;
        Root.printNode(Root, debugFile);

        debugFile.write("Exiting method.\n");
        return Root;
    }


    boolean isLeaf(treeNode T){

        if(T.child1 == null){
        
            return true;
        
        }
        
        else{
        
            return false;
        
        }
    }


    void build23Tree(Scanner infile, FileWriter debug) throws IOException{

        debug.write("entering build tree \n");
        
        int data = 0;
        
        treeNode spot = null;
        
        while(infile.hasNextInt()){
            
            if(spot == null){

                while(spot == null){
                
                data = infile.nextInt();
                spot = findSpot(data, Root, debug);

            }
                
            }
            else{

                data = infile.nextInt();
                spot = findSpot(data, Root,debug);

            }
            
            treeNode leafNode = new treeNode(data, -1, 5, null, null, null, null);
            
            leafNode.printNode(leafNode, debug);
            
            treeInsert(spot, leafNode, debug);
            
        }
        
        infile.close();

        debug.close();

    }


    treeNode findSpot(int data, treeNode Spot, FileWriter debug) throws IOException{
        System.out.println("find spot: " + Spot.key1 + " data: " + data +"\n");
        if(Spot.child1 == null){
        
            debug.write("At leaf node. Exiting. \n");
        
            return null;
        }

        if(data == Spot.key1 || data == Spot.key2){

            debug.write("Data is already in spot. exiting. \n");
            
            return null;
        }

        if(isLeaf(Spot.child1)){
        
            if(data == Spot.child1.key1 || data == Spot.child2.key2){
        
                debug.write("Data is already in leaf node. exiting. \n");
        
                return null;
            }

            else{
                        
                return Spot;
            }
        }
        else{

            if(data < Spot.key1){
            
                return findSpot(data, Spot.child1, debug);
            
            }
            
            else if(Spot.key2 == -1 || data > Spot.key1 && data < Spot.key2){
            
                return findSpot(data, Spot.child2, debug);
            
            }
            
            else if(Spot.key2 != -1 && data >= Spot.key2){
            
                return findSpot(data, Spot.child3, debug);
            
            }
            
            else{
            
                return null;
            
            }

        }

    }

    int countKids(treeNode testNode){

        if(testNode.child3 == null){
        
            return 2;
        
        }
        
        else{
        
            return 3;
        
        }

    }

    void treeInsert(treeNode Spot, treeNode newNode, FileWriter debug) throws IOException{

        
        if(Spot == null){
        
            debug.write(" \nTree insert, spot is null.\n ");
        
            return;
        
        }
        
        else{
        
            debug.write("\n in tree insert, printing spot and new node: \n");
        
            Spot.printNode(Spot, debug);
        
            newNode.printNode(newNode, debug);
        
        }
        
        if(countKids(Spot) == 2){
        
            treeTwoChildCase(Spot, newNode, debug);
            
        }
        
        else if(countKids(Spot) == 3){
        
            treeThreeChildCase(Spot, newNode, debug);

        }
        
    }

    void preOrder(treeNode T, FileWriter outFile) throws IOException{

        if(T == null){
        
            return;
        
        }
         
        else if(isLeaf(T)){
        
            T.printTraversal(T, outFile);
        
        }
        
        else if(T.child3 == null){


            preOrder(T.child1, outFile);
        
            preOrder(T.child2, outFile);     

        }

        else if(T.child3 != null){
            
            T.printTraversal(T, outFile);
            
            preOrder(T.child1, outFile);
            
            preOrder(T.child2, outFile); 
            
            preOrder(T.child3, outFile);
       
        }

    }

    void treeTwoChildCase(treeNode spot, treeNode newNode, FileWriter debug) throws IOException{

        System.out.println("\n entering two spot: \n");
        
        
        //Seeing where to put the third node. 
        
        if(newNode.key1 < spot.child2.key1){
        
            spot.child3 = spot.child2;
        
            spot.child2 = newNode;
        
        }
        
        else{
        
            spot.child3 = newNode;

        }
        //swap 

        if(spot.child2.key1 < spot.child1.key1){
        
            treeNode tempNode = spot.child1;
        
            spot.child1 = spot.child2;
        
            spot.child2 = tempNode; 
        
        }

        //update children the smallest data to largest: 1, 2, 3, respectively. set the childrens parents to spot.
        //problem is spot generally 

        spot.child1.parent = spot;

        spot.child1.rank = 1;
        
        spot.child2.parent = spot;
        
        spot.child2.rank = 2;
        
        spot.child3.parent = spot;
        
        spot.child3.rank = 3;

        updateKeys(spot, debug);
        
        if(spot.rank > 1){
        
            updateKeys(spot.parent, debug);
        
        }
        
        System.out.println(spot.key1 + " : " + spot.key2);
        
        debug.write("leaving spot 2 case method. printing spot: \n");
        spot.printNode(spot, debug);
    
    }


    int findMinLeaf(treeNode tnode){

        if(tnode == null){
        
            return -1;
        
        }
        
        if(tnode.child1 == null){
        
            return tnode.key1;
        
        }
        
        else{
        
            return findMinLeaf(tnode.child1);
        
        }
    }

    void treeThreeChildCase(treeNode spot, treeNode newnode, FileWriter debug) throws IOException{
        
        
        System.out.println("entering three spot case \n");
        
        treeNode sibling = new treeNode(-1, -1, 5, null, null, null, null);
        
        // if the value of newnode's key
        
        System.out.println("spot's child 3: " + spot.child3.key1 + "\n");
        
        System.out.println("newNode 3 case: " + newnode.key1);
        
        if(newnode.key1 > spot.child3.key1){//?

            sibling.child2 = newnode;

            sibling.child1 = spot.child3;
            
            spot.child3 = null;
        }
        
        else if(newnode.key1 < spot.child3.key1){

            sibling.child2 = spot.child3;

            spot.child3 = newnode;
        }

        if(spot.child3 != null){
        
            if(spot.child3.key1 > spot.child2.key1){
        
                sibling.child1 = spot.child3;
        
                spot.child3 = null;
        
            }
        
            else{
        
                sibling.child1 = spot.child2;
        
                spot.child2 = newnode;

            }
        }

        else if(spot.child2.key1 < spot.child1.key1){

            treeNode tempNode = spot.child1;

            spot.child1 = spot.child2;

            spot.child2 = tempNode; 

        }

        spot.child1.parent = spot;

        spot.child1.rank = 1;

        spot.child2.parent = spot;

        spot.child2.rank = 2;

        spot.child3 = null;

        //Set siblings parent nodes and ranks.

        sibling.child1.parent = sibling;

        sibling.child1.rank = 1;

        sibling.child2.parent = sibling;

        sibling.child2.rank = 2;

        sibling.child3 = null;


        updateKeys(spot, debug);

        updateKeys(sibling, debug);
        
        if(spot.rank == -1 && spot.parent == null){

            Root = makeNewRoot(spot, sibling, debug);
            
        }
        else{

            treeInsert(spot.parent, sibling, debug);

        }

        if(spot.rank > 1){

            updateKeys(spot.parent, debug);

        }


    }
    treeNode makeNewRoot(treeNode spot, treeNode sibling, FileWriter debug) throws IOException{
                
        treeNode newRoot = new treeNode(-1, -1, -1, null, null, null, null);
        
        newRoot.child1 = spot;

        newRoot.child2 = sibling;
        
        newRoot.child3 = null;
        
        newRoot.key1 = findMinLeaf(sibling);
        
        newRoot.key2 = -1;

        spot.parent = newRoot;

        spot.rank = 1;
        
        sibling.parent = newRoot;
        
        sibling.rank = 2;

        debug.write("new root created: ");
        
        newRoot.printNode(newRoot, debug);
        
        debug.write("\n");
        
        return newRoot; 
    }

    void updateKeys(treeNode tnode, FileWriter debug) throws IOException{
        
        debug.write("Entering update keys method. \n");
        
        if(tnode == null){
            return;
        }
        
        tnode.key1 = findMinLeaf(tnode.child2);
        
        tnode.key2 = findMinLeaf(tnode.child3);

        
        if(tnode.rank > 1){
            updateKeys(tnode.parent, debug);
        }
        tnode.printNode(tnode, debug);
        System.out.println("updated keys: " + tnode.key1 + " " + tnode.key2);
       
        debug.write("leaaving updatekeys: " + tnode.key1 + " " + tnode.key2);    
    }

}
public class HouE_Project4_Main{
    public static void main(String[] args) throws IOException{

        Scanner inFile = new Scanner(new FileReader(args[0]));
        
        FileWriter outFile = new FileWriter(args[1]);
        
        FileWriter debugFile = new FileWriter(args[2]);
        
        Tree TreeCaller = new Tree();
        
        TreeCaller.initialTree(inFile, debugFile);
        
        TreeCaller.build23Tree(inFile, debugFile);
        
        TreeCaller.preOrder(TreeCaller.Root, outFile);
        
        inFile.close();
        
        outFile.close();
        
        debugFile.close();
    }

}
