/*
*****************
Project Requirements and Goals: 
Idea is to obtain the middle node of a given set of numbers inserted in ascending order
Inputted data is named p1data.txt and p1data2.txt
*****************
*/
import java.io.*;
import java.util.*;

class listNode{
    String data;
    listNode next;
    listNode(String dataConstructor){
        data = dataConstructor;
        next = null;
    }
}
class LList{
    listNode listHead;
    listNode middleNode;
    
    LList(){}
    
    void constructLL(listNode listHead, Scanner inFile, FileWriter debugFile) throws IOException{
        debugFile.write("In construct method \n");
        while(inFile.hasNext()){
            String data = inFile.next();
            listNode inputData = new listNode(data);
            listInsert(listHead, inputData, debugFile);
        }
    }
    void listInsert(listNode listHead, listNode inputData, FileWriter debugFile) throws IOException{
        debugFile.write("In listInsert method. \n");
        listNode Spot = findSpot(listHead, inputData);
        debugFile.write("Returning from findSpot, data is: " + Spot.data + "\n");
        
        inputData.next = Spot.next;
        Spot.next = inputData;
        
    }

    //Insert data into linked list in ascending order. 
    listNode findSpot(listNode listHead, listNode inputNode){
        listNode Spot = listHead;
        while(Spot.next != null && Spot.next.data.compareTo(inputNode.data) < 0){
            Spot = Spot.next;
        }
        return Spot;  
    }

    void printList(listNode listHead, FileWriter outFile) throws IOException{
        while(listHead.next != null){
            outFile.write("(" + listHead.data + ", " + listHead.next.data + ") -> ");
            listHead = listHead.next;

            if(listHead.next == null){
                outFile.write("(" + listHead.data + ", NULL " + ")\n");
            }

        }
    }

    listNode findMiddleNode(listNode listHead, FileWriter debugFile) throws IOException{
        debugFile.write("In findMiddleNode method. \n");
        listNode Walker = listHead.next;
        listNode Walker2 = listHead.next;
        
        while(Walker2.next != null && Walker2.next.next != null){
            Walker = Walker.next;
            Walker2 = Walker2.next.next;
        }

        debugFile.write("Walker1's data is: " + Walker.data);
        return Walker;
    }
}

public class EHou_Project1_Main{
    public static void main(String[] args) throws IOException{
    Scanner inFile = new Scanner(new FileReader(args[0]));
    FileWriter outFile = new FileWriter(args[1]);
    FileWriter debugFile = new FileWriter(args[2]);

    LList linkedList = new LList();
    listNode listHead = new listNode("dummy");
    linkedList.constructLL(listHead, inFile, debugFile);
    linkedList.printList(listHead, outFile);
    listNode middleNode = linkedList.findMiddleNode(listHead, debugFile);

    if(middleNode != null){
        outFile.write( "Middle node is: " + middleNode.data);
    }

    inFile.close();
    outFile.close();
    debugFile.close();
}
}
