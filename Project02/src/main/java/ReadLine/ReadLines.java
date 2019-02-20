package ReadLine;
/**
 * Author:Yi Jing
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadLines {
    private static Scanner scan;
    private static BufferedWriter writer;

    public static boolean isTag(String tag){
        String[] tags = {"INDI","NAME","SEX","BRIT","DEAT","FAMC","FAMS","FAM","MARR","HUSB","WIFE","CHIL","DIV","DATE","HEAD","TRLR","NOTE"};
        for(int i = 0; i < tags.length; i++){
            if(tags[i].equals(tag)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList parse(String directory) throws FileNotFoundException {
        ArrayList list = new ArrayList();
        scan = new Scanner(new FileReader(directory));
        String reader = scan.nextLine();
        int a = 0;
        while(scan.hasNextLine()){
            list.add("--> " + reader);
            String[] str = reader.split(" ");
            if(isTag(str[1])){
                reader = reader.replaceFirst(" ","|");
                reader = reader.replaceFirst(" ","|Y|");
                list.add("<-- " + reader);
            }
            else if(str.length > 2 && isTag(str[2])){
                reader = reader.replaceFirst(str[2],str[1]);
                reader = reader.replaceFirst(str[1],str[2]);
                reader = reader.replaceFirst(" ","|");
                reader = reader.replaceFirst(" ","|Y|");
                list.add("<-- " + reader);
            }
            else{
                reader = reader.replaceFirst(" ","|");
                reader = reader.replaceFirst(" ","|N|");
                list.add("<-- " + reader);
            }

            reader = scan.nextLine();
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            String directory = "GEDCOMsourcefile/shakespeare.ged";
            ArrayList list = new ArrayList();
            list = parse(directory);
            System.out.println(list.toString());
            FileWriter writer = new FileWriter("outputFile/output.txt");
            for(int i = 0; i < list.size(); i++) {
                writer.write(list.get(i).toString() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

