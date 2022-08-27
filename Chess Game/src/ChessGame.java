import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class ChessGame {

    public static void main(String[] args) {
        //Creating a new file called record
        try{
            File myFile=new File("record.txt");
            if(myFile.createNewFile()){
                System.out.println("file created");
            }else{
                System.out.println("Already Exist");
            }
        }catch(IOException e){
            System.out.println("Error occurred");
        }
        //Preparing the FileWriter
        FileWriter editor;
        //Preparing our board which is a 8x8 string matrix
        //each troop is represented by the color of the troop followed by the type of troop
        //Ex black queen => b_q
        String[][] board=new String[8][8];
        setBoard(board);
        displayBoard(board);
        System.out.println();
        Scanner in=new Scanner(System.in);
        //Running an infinite while loop
        //Here I have not taken the normal naming convention used in chess games like a1,b2
        //Instead I have gone with the index numbers of the 2D array to represent the troops
        while(true){
            System.out.println("Please Select the troop --consider the 0 based index");
            //I have taken the input as a string
            String selected_index=in.next();
            if(selected_index.equals("exit")){
                try {
                    editor =new FileWriter("record.txt",true);
                    editor.write("Exited ");
                    editor.close();
                }catch(IOException e){
                    System.out.println("Error occurred in writing 1");
                }
                break;
            }

            if(selected_index.equals("print")){
                displayBoard(board);
                continue;
            }
            int i=(int)(selected_index.charAt(0))-'0';
            int j=(int)(selected_index.charAt(1))-'0';
            String selected_troop=board[i][j];
            try {
                editor =new FileWriter("record.txt");
                editor.write( "Selected "+selected_index+" -- "+selected_troop + "\n");
                editor.close();
            }catch(IOException e){
                System.out.println("Error occurred in writing 2");
            }
            //Called a function named printTroop to identify what troop we selected
            printTroop(selected_troop);
            System.out.println();
            //I have called a function named printPossiblePositions to return all the possible positions on selecting a troop
            ArrayList<ArrayList<Integer>> possible_positions =printPossiblePositions(i,j,selected_troop,board);
            System.out.println("The possible moves are ");
            for(int k=0;k<possible_positions.size();k++){
                System.out.print(possible_positions.get(k));
            }
            System.out.println();
            System.out.println("Please select a location to move your piece");
            String selected_index2=in.next();
            //here selected_index2 will have the new location of the piece
            if(selected_index2.equals("exit")){
                try {
                    editor =new FileWriter("record.txt");
                    editor.write("Exited ");
                    editor.close();
                }catch(IOException e){
                    System.out.println("Error occurred in writing 3");
                }
                break;
            }
            if(selected_index2.equals("print")){
                displayBoard(board);
                continue;
            }
            int newIndex1=(int)(selected_index2.charAt(0))-'0';
            int newIndex2=(int)(selected_index2.charAt(1))-'0';
            ArrayList<Integer> newPos=new ArrayList<>();
            newPos.add(newIndex1);
            newPos.add(newIndex2);
            //checking whether the entered position is a possible position or not
            //and if not we will ask the user to enter again
            if(isPosValid(newPos,possible_positions)){
                board[newIndex1][newIndex2]=board[i][j];
                board[i][j]=null;
                try {
                    editor =new FileWriter("record.txt");
                    editor.write( "Moved to "+selected_index2+ "\n");
                    editor.close();
                }catch(IOException e){
                    System.out.println("Error occurred in writing 2");
                }
            }else{
                System.out.println("You have entered an invalid position");
                System.out.println("Please try again");
                continue;
            }
            displayBoard(board);
        }
    }
    //This function is to print the board on the console
    public static void displayBoard(String[][] board){
        for(int i = 0; i < board.length; i++){
            System.out.println(Arrays.toString(board[i]));
        }
    }
    //This function will return the color of the troop given the index
    public static char color(int i,int j,String[][] board){
        String s1=board[i][j];
        return s1.charAt(0);
    }
    //This function is to arrange & rearrange the board
    public static void setBoard(String[][] board){
        board[0][0]="b_r";
        board[0][1]="b_h";
        board[0][2]="b_b";
        board[0][3]="b_q";
        board[0][4]="b_k";
        board[0][5]="b_b";
        board[0][6]="b_h";
        board[0][7]="b_r";
        board[1][0]="b_p";
        board[1][1]="b_p";
        board[1][2]="b_p";
        board[1][3]="b_p";
        board[1][4]="b_p";
        board[1][5]="b_p";
        board[1][6]="b_p";
        board[1][7]="b_p";
        board[7][0]="w_r";
        board[7][1]="w_h";
        board[7][2]="w_b";
        board[7][3]="w_q";
        board[7][4]="w_k";
        board[7][5]="w_b";
        board[7][6]="w_h";
        board[7][7]="w_r";
        board[6][0]="w_p";
        board[6][1]="w_p";
        board[6][2]="w_p";
        board[6][3]="w_p";
        board[6][4]="w_p";
        board[6][5]="w_p";
        board[6][6]="w_p";
        board[6][7]="w_p";
    }
    //This function will print the name of the selected troop
    public static void printTroop(String troop){
        int lastChar=troop.length()-1;
        if(troop.charAt(lastChar)=='p'){
            System.out.print("Pawn ");
        }else  if(troop.charAt(lastChar)=='r'){
            System.out.print("Rook ");
        }else  if(troop.charAt(lastChar)=='h'){
            System.out.print("Horse ");
        }else  if(troop.charAt(lastChar)=='b'){
            System.out.print("Bishop ");
        }else  if(troop.charAt(lastChar)=='q'){
            System.out.print("Queen ");
        }else  if(troop.charAt(lastChar)=='k'){
            System.out.print("King ");
        }
    }
    //This function will return all the possible moves of the selected coin in teh form of list of lists.
    public static ArrayList<ArrayList<Integer>> printPossiblePositions(int i,int j,String troop,String[][] board){
        int lastChar=troop.length()-1;
        if(troop.charAt(lastChar)=='p'){
            ArrayList<ArrayList<Integer>> ans=moves_of_pawn(i,j,board);
            return ans;
        }else  if(troop.charAt(lastChar)=='r'){
            ArrayList<ArrayList<Integer>> ans=moves_of_rook(i,j,board);
            return ans;
        }else  if(troop.charAt(lastChar)=='h'){
            ArrayList<ArrayList<Integer>> ans=moves_of_horse(i,j,board);
            return ans;
        }else  if(troop.charAt(lastChar)=='b'){
            ArrayList<ArrayList<Integer>> ans=moves_of_bishop(i,j,board);
            return ans;
        }else  if(troop.charAt(lastChar)=='q'){
            ArrayList<ArrayList<Integer>> ans=moves_of_queen(i,j,board);
            return ans;
        }else  if(troop.charAt(lastChar)=='k'){
            ArrayList<ArrayList<Integer>> ans=moves_of_king(i,j,board);
            return ans;
        }
        return new ArrayList<ArrayList<Integer>>();
    }
    //The following 6 functions are to find all possible moves of different troops like pawn,rook,horse,bishop,queen,king.
    //The obstructing coins are taken into consideration
    //if the obstructing coin is of same color we will not consider that position and positions beyond that position
    //But if the obstructing coin is of opposite color we will consider that position but will not consider positions beyond that position
    public static ArrayList<ArrayList<Integer>> moves_of_pawn(int i,int j,String[][] board){
        ArrayList<ArrayList<Integer>> moves=new ArrayList<>();
        if(color(i,j,board)=='w'){
            ArrayList<Integer> temp1=new ArrayList<>();
            if(i-2>=0 && i-2<=7 && j>=0 && j<=7 && i==6 && board[i-2][j]==null){
                temp1.add(i-2);
                temp1.add(j);
                moves.add(temp1);
            }
            if(i-1>=0 && i-1<=7 && j>=0 && j<=7 && board[i-1][j]==null){
                ArrayList<Integer> temp2=new ArrayList<>();
                temp2.add(i-1);
                temp2.add(j);
                moves.add(temp2);
            }
            String s1=null;
            if(i-1>=0 && i-1<=7 && j+1>=0 && j+1<=7 ){
                 s1=board[i-1][j+1];
            }
            if(i-1>=0 && i-1<=7 && j+1>=0 && j+1<=7 && board[i-1][j+1]!=null && s1.charAt(0)=='b'){
                ArrayList<Integer> temp3=new ArrayList<>();
                temp3.add(i-1);
                temp3.add(j+1);
                moves.add(temp3);
            }
            String s2=null;
            if(i-1>=0 && i-1<=7 && j-1>=0 && j-1<=7){
                s2=board[i-1][j-1];
            }
            if(i-1>=0 && i-1<=7 && j-1>=0 && j-1<=7 && board[i-1][j-1]!=null && s2.charAt(0)=='b'){
                ArrayList<Integer> temp4=new ArrayList<>();
                temp4.add(i-1);
                temp4.add(j-1);
                moves.add(temp4);
            }
            return moves;
        }else{
            ArrayList<Integer> temp1=new ArrayList<>();
            if(j>=0 && j<=7 && i==1 && board[i+2][j]==null){
                temp1.add(i+2);
                temp1.add(j);
                moves.add(temp1);
            }
            if(i+1>=0 && i+1<=7 && j>=0 && j<=7 && board[i+1][j]==null){
                ArrayList<Integer> temp2=new ArrayList<>();
                temp2.add(i+1);
                temp2.add(j);
                moves.add(temp2);
            }
            String s1=null;
            if(i+1>=0 && i+1<=7 && j+1>=0 && j+1<=7 ){
                s1=board[i+1][j+1];
            }
            if(i+1>=0 && i+1<=7 && j+1>=0 && j+1<=7 && board[i+1][j+1]!=null && s1.charAt(0)=='w'){
                ArrayList<Integer> temp3=new ArrayList<>();
                temp3.add(i+1);
                temp3.add(j+1);
                moves.add(temp3);
            }
            String s2=null;
            if(i+1>=0 && i+1<=7 && j-1>=0 && j-1<=7 ){
                s2=board[i+1][j-1];
            }
            if(i+1>=0 && i+1<=7 && j-1>=0 && j-1<=7 && board[i+1][j-1]!=null && s2.charAt(0)=='w'){
                ArrayList<Integer> temp4=new ArrayList<>();
                temp4.add(i+1);
                temp4.add(j-1);
                moves.add(temp4);
            }
            return moves;
        }
    }
    public static ArrayList<ArrayList<Integer>> moves_of_rook(int i,int j,String[][] board){
        ArrayList<ArrayList<Integer>> moves=new ArrayList<>();
        for (int k = i-1; k >= 0; k--) {
            ArrayList<Integer> temp1=new ArrayList<>();
            if(board[k][j]!=null && color(i,j,board)!=color(k,j,board)){
                temp1.add(k);
                temp1.add(j);
                moves.add(temp1);
                break;
            }
            else if(board[k][j]!=null && color(i,j,board)==color(k,j,board)){
                break;
            }
            temp1.add(k);
            temp1.add(j);
            moves.add(temp1);
        }
        for (int k = i+1; k < 8; k++) {
            ArrayList<Integer> temp1=new ArrayList<>();
            if(board[k][j]!=null && color(i,j,board)!=color(k,j,board)){
                temp1.add(k);
                temp1.add(j);
                moves.add(temp1);
                break;
            }
            else if(board[k][j]!=null && color(i,j,board)==color(k,j,board)){
                break;
            }
            temp1.add(k);
            temp1.add(j);
            moves.add(temp1);
        }
        for (int k = j-1; k >= 0; k--) {
            ArrayList<Integer> temp1=new ArrayList<>();
            if(board[i][k]!=null && color(i,j,board)!=color(i,k,board)){
                temp1.add(i);
                temp1.add(k);
                moves.add(temp1);
                break;
            }
            else if(board[i][k]!=null && color(i,j,board)==color(i,k,board)){
                break;
            }
            temp1.add(i);
            temp1.add(k);
            moves.add(temp1);
        }
        for (int k = j+1; k < 8; k++) {
            ArrayList<Integer> temp1=new ArrayList<>();
            if(board[i][k]!=null && color(i,j,board)!=color(i,k,board)){
                temp1.add(i);
                temp1.add(k);
                moves.add(temp1);
                break;
            }
            else if(board[i][k]!=null && color(i,j,board)==color(i,k,board)){
                break;
            }
            temp1.add(i);
            temp1.add(k);
            moves.add(temp1);
        }
        return moves;
    }
    public static ArrayList<ArrayList<Integer>> moves_of_horse(int i,int j,String[][] board){
        ArrayList<ArrayList<Integer>> moves=new ArrayList<>();
        if(i-2>=0 && i-2<=7 && j+1 >=0 && j+1<=7 ){
            if(board[i-2][j+1]==null || color(i,j,board)!=color(i-2,j+1,board) ){
                ArrayList<Integer> temp1 = new ArrayList<>();
                temp1.add(i-2);
                temp1.add(j+1);
                moves.add(temp1);
            }
        }
        if(i-2>=0 && i-2<=7 && j-1 >=0 && j-1<=7 ){
            if( board[i-2][j-1]==null || color(i,j,board)!=color(i-2,j-1,board) ){
                ArrayList<Integer> temp1 = new ArrayList<>();
                temp1.add(i-2);
                temp1.add(j-1);
                moves.add(temp1);
            }
        }
        if(i+2>=0 && i+2<=7 && j+1 >=0 && j+1<=7 ){
            if(board[i+2][j+1]==null || color(i,j,board)!=color(i+2,j+1,board) ){
                ArrayList<Integer> temp1 = new ArrayList<>();
                temp1.add(i+2);
                temp1.add(j+1);
                moves.add(temp1);
            }
        }
        if(i+2>=0 && i+2<=7 && j-1 >=0 && j-1<=7 ){
            if(board[i+2][j-1]==null || color(i,j,board)!=color(i+2,j-1,board) ){
                ArrayList<Integer> temp1 = new ArrayList<>();
                temp1.add(i+2);
                temp1.add(j-1);
                moves.add(temp1);
            }
        }
        if(i-1>=0 && i-1<=7 && j+2 >=0 && j+2<=7 ){
            if( board[i-1][j+2]==null || color(i,j,board)!=color(i-1,j+2,board) ){
                ArrayList<Integer> temp1 = new ArrayList<>();
                temp1.add(i-1);
                temp1.add(j+2);
                moves.add(temp1);
            }
        }
        if(i-1>=0 && i-1<=7 && j-2>=0 && j-2<=7 ){
            if(board[i-1][j-2]==null || color(i,j,board)!=color(i-1,j-2,board)){
                ArrayList<Integer> temp1 = new ArrayList<>();
                temp1.add(i-1);
                temp1.add(j-2);
                moves.add(temp1);
            }
        }
        if(i+1>=0 && i+1<=7 && j+2 >=0 && j+2<=7 ){
            if( board[i+1][j+2]==null || color(i,j,board)!=color(i+1,j+2,board) ){
                ArrayList<Integer> temp1 = new ArrayList<>();
                temp1.add(i+1);
                temp1.add(j+2);
                moves.add(temp1);
            }
        }
        if(i+1>=0 && i+1<=7 && j-2 >=0 && j-2<=7 ){
            if(color(i,j,board)!=color(i+1,j-2,board) || board[i+1][j-2]==null){
                ArrayList<Integer> temp1 = new ArrayList<>();
                temp1.add(i+1);
                temp1.add(j-2);
                moves.add(temp1);
            }
        }
        return moves;
    }
    public static ArrayList<ArrayList<Integer>> moves_of_bishop(int i,int j,String[][] board){
        ArrayList<ArrayList<Integer>> moves=new ArrayList<>();
        for(int row=i+1,col=j+1;row<8 && row>=0 && col>=0 && col<8;row++,col++){
            ArrayList<Integer> temp1=new ArrayList<>();
            if(board[row][col]!=null && color(i,j,board)!=color(row,col,board)){
                temp1.add(row);
                temp1.add(col);
                moves.add(temp1);
                break;
            }
            else if(board[row][col]!=null && color(i,j,board)==color(row,col,board)){
                break;
            }
            temp1.add(row);
            temp1.add(col);
            moves.add(temp1);
        }
       for (int row=i-1,col=j-1;row>=0 && row<8 && col<8 && col>=0;row--,col--){
           ArrayList<Integer> temp1=new ArrayList<>();
           if(board[row][col]!=null && color(i,j,board)!=color(row,col,board)){
               temp1.add(row);
               temp1.add(col);
               moves.add(temp1);
               break;
           }
           else if(board[row][col]!=null && color(i,j,board)==color(row,col,board)){
               break;
           }
           temp1.add(row);
           temp1.add(col);
           moves.add(temp1);
       }
       for(int row=i+1,col=j-1;row<8 && row>=0 && col<8 && col>=0;row++,col--){
           ArrayList<Integer> temp1=new ArrayList<>();
           if(board[row][col]!=null && color(i,j,board)!=color(row,col,board)){
               temp1.add(row);
               temp1.add(col);
               moves.add(temp1);
               break;
           }
           else if(board[row][col]!=null && color(i,j,board)==color(row,col,board)){
               break;
           }
           temp1.add(row);
           temp1.add(col);
           moves.add(temp1);
        }
        for(int row=i-1,col=j+1;row>=0 && row<8 && col>=0 && col<8;row--,col++){
            ArrayList<Integer> temp1=new ArrayList<>();
            if(board[row][col]!=null && color(i,j,board)!=color(row,col,board)){
                temp1.add(row);
                temp1.add(col);
                moves.add(temp1);
                break;
            }
            else if(board[row][col]!=null && color(i,j,board)==color(row,col,board)){
                break;
            }
            temp1.add(row);
            temp1.add(col);
            moves.add(temp1);
        }
        return moves;
    }
    public static ArrayList<ArrayList<Integer>> moves_of_queen(int i,int j,String[][] board){
        ArrayList<ArrayList<Integer>> list1=moves_of_rook(i,j,board);
        ArrayList<ArrayList<Integer>> list2=moves_of_bishop(i,j,board);
        list1.addAll(list2);
        return list1;
    }
    public static ArrayList<ArrayList<Integer>> moves_of_king(int i,int j,String[][] board){
        ArrayList<ArrayList<Integer>> moves=new ArrayList<>();
        if(i+1>=0 && i+1<8){
            if(board[i+1][j]==null || board[i+1][j]!=null && color(i,j,board)!=color(i+1,j,board)){
                ArrayList<Integer> temp1=new ArrayList<>();
                temp1.add(i+1);
                temp1.add(j);
                moves.add(temp1);
            }
        }
        if(i-1>=0 && i-1<8){
            if(board[i-1][j]==null || board[i-1][j]!=null && color(i,j,board)!=color(i-1,j,board)){
                ArrayList<Integer> temp1=new ArrayList<>();
                temp1.add(i-1);
                temp1.add(j);
                moves.add(temp1);
            }
        }
        if(j+1>=0 && j+1<8){
            if(board[i][j+1]==null || board[i][j+1]!=null && color(i,j,board)!=color(i,j+1,board)){
                ArrayList<Integer> temp1=new ArrayList<>();
                temp1.add(i);
                temp1.add(j+1);
                moves.add(temp1);
            }
        }
        if(j-1>=0 && j-1<8){
            if(board[i][j-1]==null || board[i][j-1]!=null && color(i,j,board)!=color(i,j-1,board)){
                ArrayList<Integer> temp1=new ArrayList<>();
                temp1.add(i);
                temp1.add(j-1);
                moves.add(temp1);
            }
        }
        if(i+1>=0 && i+1<8 && j+1>=0 && j+1<8){
            if(board[i+1][j+1]==null || board[i+1][j+1]!=null && color(i,j,board)!=color(i+1,j+1,board)){
                ArrayList<Integer> temp1=new ArrayList<>();
                temp1.add(i+1);
                temp1.add(j+1);
                moves.add(temp1);
            }
        }
        if(i-1>=0 && i-1<8 && j-1>=0 && j-1<8){
            if(board[i-1][j-1]==null || board[i-1][j-1]!=null && color(i,j,board)!=color(i-1,j-1,board)){
                ArrayList<Integer> temp1=new ArrayList<>();
                temp1.add(i-1);
                temp1.add(j-1);
                moves.add(temp1);
            }
        }
        if(i+1>=0 && i+1<8 && j-1>=0 && j-1<8){
            if(board[i+1][j-1]==null || board[i+1][j-1]!=null && color(i,j,board)!=color(i+1,j-1,board)){
                ArrayList<Integer> temp1=new ArrayList<>();
                temp1.add(i+1);
                temp1.add(j-1);
                moves.add(temp1);
            }
        }
        if(i-1>=0 && i-1<8 && j+1>=0 && j+1<8){
            if(board[i-1][j+1]==null || board[i-1][j+1]!=null && color(i,j,board)!=color(i-1,j+1,board)){
                ArrayList<Integer> temp1=new ArrayList<>();
                temp1.add(i-1);
                temp1.add(j+1);
                moves.add(temp1);
            }
        }
        return moves;
    }
    //Finally, this function is to check whether the entered position(to move) is actually a possible position
    public static boolean isPosValid(ArrayList<Integer> input,ArrayList<ArrayList<Integer>> solutions){
        return solutions.contains(input);
    }
}