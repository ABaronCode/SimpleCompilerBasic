import java.io.IOException;
import java.nio.file.*;

public class CodeHandler {
    private static String manipulateBasicStr;
    private static int index;

    //Constructor
    public CodeHandler(String fileName) throws IOException {
        manipulateBasicStr = new String(Files.readAllBytes(Path.of(fileName)));
    }

     static char Peek(int i) {
        //looks i ahead and returns that character, does not move index
        char whatC;
        //makes sure within bounds of data in string
        if ((i + index) < manipulateBasicStr.length()) {
            whatC = manipulateBasicStr.charAt(i + index);
            return whatC;
        } else {
            //empty char if not within bounds of the string of data
            return '\0';
        }

    }

     static String PeekString(int i) {
        //returns a string of i characters will not move index
        String finishedString;
        StringBuilder buildString = new StringBuilder();

        //We need to look ahead i characters and add them to nString
        //Start at index + desired length of String, if over max length return string to up to max length
        if ((index + i) < manipulateBasicStr.length()) {
            for (int x = 0; (index + x) < (index + i); x++) {
                buildString.append(manipulateBasicStr.charAt(index + x));
            }
        } else if ((index + i) >= manipulateBasicStr.length()) {
            for (int y = 0; (index + y) < manipulateBasicStr.length(); y++) {
                buildString.append(manipulateBasicStr.charAt(index + y));
            }
        }

        finishedString = buildString.toString();
        return finishedString;
    }

     static char GetChar() {
        //returns next character and moves index
        index++;
        return manipulateBasicStr.charAt(index);
    }

    static void Swallow(int i){
        //moves index i positions
        index = index + i;
        //if index is attempted to be swallowed past max length, will set equal to max length
        if(index > manipulateBasicStr.length()) {
            index = manipulateBasicStr.length();
        }
    }

     static boolean isDone(){
        //returns true if end of document
        //check length of document and if index is = to it, we have reached the end and if
        return (index >= manipulateBasicStr.length());
    }

     static String Remainder(){
        //returns rest of document as string;
        String remaining;
        StringBuilder sb = new StringBuilder();
        char c;
        int remainingIndex = index;

        //Starts at index and then creates a StringBuilder of the remaining characters until end of original string
        for(;remainingIndex < manipulateBasicStr.length(); remainingIndex++){
            c = manipulateBasicStr.charAt(remainingIndex);
            sb.append(c);
        }

        //transfer StringBuilder to string to return
        remaining = sb.toString();
        return remaining;
    }

}
