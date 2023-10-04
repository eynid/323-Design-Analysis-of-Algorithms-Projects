/*
****************************
Idea of this project is to understand Huffman's Encoding. 
Project uses p3data.txt and p3data2.txt
****************************
*/
import java.io.*;
import java.util.*;

class HNode{
    String code; //The binary code that will be accumulated for each character.
    String charStr; //The accumulated character string after combining the characters.
    
    int prob;
    int entropy;
    int numBit;
    
    HNode left;
    HNode right;
    HNode next;

    HNode(String charS, int probability, String binaryCode, int bits, HNode leftNode, HNode rightNode, HNode nextNode){ 
        charStr = charS;
        prob = probability;
        code = binaryCode;
        numBit = bits;
        entropy = bits * probability;
        
        left = leftNode;
        right = rightNode;
        next = nextNode;
    }

        //Project Requirements were to print out the variables on one line, however, there is a possibility that 
        //some nodes could be null, and they must be accounted for in the prints. 
    void printNode(HNode T, FileWriter outFile) throws IOException{ 
        if(T.next == null ){
            if(T.right == null && T.left == null){
                outFile.write("(" + T.charStr + " , " + T.prob + ", " + T.code + ", " 
                                + T.numBit + ", " + T.entropy + ", null , null, null) ->") ;
            }
            else{
                outFile.write("(" + T.charStr + ", " + T.prob + ", " + T.code + ", " 
                            + T.numBit + ", " + T.entropy + ", null, "
                            + T.left.charStr + ", " + T.right.charStr + ")->"); 
            }
        }
        else{
            if(T.right == null && T.left == null){
                outFile.write("(" + T.charStr + " , " + T.prob + ", " + T.code + ", " + T.numBit + 
                                ", " + T.entropy + ", " + T.next.charStr + ", null, null) ->") ;
            }
            else{
                outFile.write("(" + T.charStr + ", " + T.prob + ", " + T.code + ", " 
                            + T.numBit + ", " + T.entropy + ", " + T.next.charStr 
                            + ", " + T.left.charStr + ", " + T.right.charStr + ")->"); 
            }
        } 
}
}
class HuffmanTree{
    HNode listHead;
    HNode Root;
    
    int totalEntropy;
    //Counters to be able to neatly print 3 lines for each traversal. Required by project. 
    int preOrderCounter = 0;
    int inOrderCounter = 0;
    int postOrderCounter = 0;

    HuffmanTree(){
        listHead = new HNode("dummy", 0, "", 0,null, null, null);
    }
    
    //Again, we use spot to insert data into the linked list in ascending order. 
    HNode findSpot(HNode listHead, HNode newNode){
        HNode spot = listHead;
        while(spot.next != null && spot.next.prob < newNode.prob){ 
            spot = spot.next;
        }

        return spot;
    }

    void listInsert(HNode T, HNode listHead){
        HNode spot = findSpot(listHead, T);
        T.next = spot.next;
        spot.next = T;
    }

    void constructHuffmanLList(Scanner inFile, FileWriter debugFile, HNode listHead) throws IOException{
        while(inFile.hasNext()){
            String charString = inFile.next();
            int prob = inFile.nextInt();
            HNode newNode = new HNode(charString, prob, "", 0, null, null, null);
            listInsert(newNode, listHead);

        }
    }
    
    // Construct the tree by combining the strings and probabilitys of the inputted data.
    HNode constructHuffmanBinaryTree(HNode listHead, FileWriter debugFile) throws IOException{
        while(listHead.next.next != null){
            
         HNode newNode = new HNode("dummy", 0, "", 0,null, null, null);
         newNode.prob = listHead.next.prob + listHead.next.next.prob;
         newNode.charStr = listHead.next.charStr + listHead.next.next.charStr;
         newNode.left = listHead.next;
         newNode.right = listHead.next.next;
         newNode.next = null;
            
         listInsert(newNode, listHead);
         listHead.next = listHead.next.next.next;
         printList(listHead, debugFile);

        }
        
        return listHead.next;
    }

    void printEntropyTable(HNode T, String code, FileWriter outFile) throws IOException{
        if(isLeaf(T)){
            T.code = code;
            T.numBit = code.length();
            T.entropy = T.prob * T.numBit;
            totalEntropy += T.entropy;
            outFile.write("\n(" + T.charStr + ", " + T.prob + ", " + T.code + ", " + T.numBit + ", " + T.entropy + ")");
        
        }
         else{
            printEntropyTable(T.left, code + "0", outFile);
            printEntropyTable(T.right, code + "1", outFile);
            
         }
    }

    boolean isLeaf(HNode T){
        if(T.left == null && T.right == null){
            return true;
        }
        else{
            return false;
        }
    }
    
    void printList(HNode listHead, FileWriter outFile) throws IOException{
        int counter = 0;
        
        while(listHead.next!= null){
            if(counter % 3 == 0){
                outFile.write("\n");
            }
            
            listHead.printNode(listHead, outFile);
            counter++;
            listHead = listHead.next;
            
            if(listHead.next == null){
                listHead.printNode(listHead, outFile);
            }
        }
    }

    void printPreOrder(HNode T, FileWriter outFile) throws IOException{
        if(T == null){
            return;
        }
        else{
            preOrderCounter += 1;
            if(isLeaf(T)){
                T.printNode(T, outFile);
                
                if(preOrderCounter % 3 == 0){
                    outFile.write("\n");
                }
            }
            else{
                T.printNode(T, outFile);
                
                if(preOrderCounter % 3 == 0){
                    outFile.write("\n");
                }
                
                printPreOrder(T.left, outFile);
                printPreOrder(T.right, outFile);
            }
        }
    }
    
    void printPostOrder(HNode T, FileWriter outFile) throws IOException{
        if(T == null){
            return;
        }
        else{
            if(isLeaf(T)){
                postOrderCounter += 1;
                T.printNode(T, outFile);
                
                if(postOrderCounter % 3 == 0){
                    outFile.write("\n");
                }
            }
            else{
                printPostOrder(T.left, outFile);
                printPostOrder(T.right, outFile);
                postOrderCounter += 1;
                T.printNode(T, outFile);
                
                if(postOrderCounter % 3 == 0){
                    outFile.write("\n");
                }
                
            }
        }
    }
    
    void printInOrder(HNode T, FileWriter outFile) throws IOException{
        if(T == null){
            return;
        }
        else{
            if(isLeaf(T)){
                inOrderCounter += 1;
                T.printNode(T, outFile);
                
                if(inOrderCounter % 3 == 0){
                    outFile.write("\n");
                }
            }
            else{
                printInOrder(T.left, outFile);
                T.printNode(T, outFile);
                inOrderCounter += 1;
                
                if(inOrderCounter % 3 == 0){
                    outFile.write("\n");
                }
                printInOrder(T.right, outFile);
                
            }
        }
    }
    
}

public class HouE_Project3_Main {
    public static void main(String[] args) throws IOException{
        Scanner inFile = new Scanner(new FileReader(args[0]));
        FileWriter outFile = new FileWriter(args[1]);
        FileWriter debugFile = new FileWriter(args[2]);
        
        HuffmanTree treeCaller = new HuffmanTree();
        treeCaller.constructHuffmanLList(inFile, debugFile, treeCaller.listHead);
        outFile.write("Printing Huffman Linked List: \n ");
        treeCaller.printList(treeCaller.listHead, outFile);

        treeCaller.Root = treeCaller.constructHuffmanBinaryTree(treeCaller.listHead, debugFile);
        outFile.write("\n Printing Entropy Table: \n");
        treeCaller.printEntropyTable(treeCaller.Root, "", outFile);
        outFile.write("\n \n Huffman Coding Scheme's Entropy is: \n "  + (double)treeCaller.totalEntropy/100 + "\n");
        
        outFile.write("\n \n Printing Pre Order: \n");
        treeCaller.printPreOrder(treeCaller.Root, outFile);
        
        outFile.write("\n \n Printing In Order: \n");
        treeCaller.printInOrder(treeCaller.Root, outFile);
        
        outFile.write("\n \n Printing Post Order: \n");
        treeCaller.printPostOrder(treeCaller.Root, outFile);

        inFile.close();
        outFile.close();
        debugFile.close();
}
}

