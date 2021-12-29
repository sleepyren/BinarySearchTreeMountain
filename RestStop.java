//author: Renaldo Hyacinthe
//This is a RestStop Object that represents a the data of a node in my BSTMountain class
//it stores the name, supplies, and obstacles a stop has
package project5;

import java.util.ArrayList;

public class RestStop implements Comparable<RestStop>
{

    //this is the name of the stop on the tree
    char label;
    //these arraylists are added to when a supply or obstacle is added to the stop
    ArrayList<String> supplies = new ArrayList<>();
    ArrayList<String> obstacles= new ArrayList<>();
    RestStop(char label)
    {
        this.label = label;
    }

    //checks if this particular RestStop Object contains obstacles
    public boolean hasObstacles()
    {
        if (obstacles.size()>0)
            return true;
        else
            return false;

    }
    //adds a String to the supply list
    public void addSupplies(String s)
    {
            supplies.add(s);

    }

    //adds a String to the obstacle list
    public void addObstacles(String s)
    {
            obstacles.add(s);

    }

    //checks if this contains a river
    public boolean hasRiver()
    {
        if (this.obstacles.contains("river"))
        {
            return true;
        }
        else
            return false;
    }
    //checks if this contains a fallen tree
    public boolean hasFallenTree()
    {
        if (this.obstacles.contains("fallen tree"))
        {
            return true;
        }
        else
            return false;
    }

    //compares the two RestStop objects by their label char
    public int compareTo(RestStop stop)
    {
        if (this.label==stop.label)
            return 0;
        if (this.label>stop.label)
            return 1;
        else
        {
            return -1;
        }
    }
}
