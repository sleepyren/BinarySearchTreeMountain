//author: Renaldo Hyacinthe
//main method that reads, parses and starts my BSTMountain file
package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MountainHike
{
    public static void main(String[] args) {
        //initializing data that will be used
        Scanner line = null;
        BSTMountain bst = null;
        String elements[];
        RestStop stop;
        try
        {
            //preparing to read the file named by args[0]
            File file = new File(args[0]);
            line = new Scanner(file);
            bst = new BSTMountain();


            while (line.hasNext())
            {
                elements = line.nextLine().split(" ");
                stop = new RestStop(elements[0].charAt(0));
                bst.add(stop);
                fileToRestStopObject(stop,elements);
            }
        }
        catch (FileNotFoundException Exception)
        {
            System.err.println("File was not found!");
        }

        //after everything is added to the BST, the adventure finally starts
        //this will print any potential solutions
        bst.BSTjourney(bst.root,bst.hiker);

    }

    //this is my text parser
    //it takes an array of strings
    public static void fileToRestStopObject(RestStop stop, String[] split)
    {
        //if the array is greater than
        if ((split.length>2))
        {
            //if fallen is found then the method skips the next iteration because "tree"
            //will follow fallen
            int skipval=0;
            for (int i = 1; i < split.length; i++)
            {
                if (i == skipval)
                    continue;
                //switch cases that decide what to do with particular inputs
                //however if those inputs are supplies, and they come after obstacles, then they
                //will not be included because of the particular format a potential text file
                //must be (LABEL SUPPLIES OBSTACLES)
                switch (split[i]) {
                    case "food":
                        if (!stop.hasObstacles())
                        {
                            stop.addSupplies(split[i]);
                            break;
                        }
                    case "axe":
                        if (!stop.hasObstacles())
                        {
                            stop.addSupplies(split[i]);
                            break;
                        }
                    case "raft":
                        if (!stop.hasObstacles())
                        {
                            stop.addSupplies(split[i]);
                            break;
                        }
                    case "river":
                        stop.addObstacles(split[i]);
                        break;
                    case "fallen":
                        skipval = i + 1;
                        stop.addObstacles("fallen tree");
                        break;
                    default:
                }


            }
        }
    }
}
