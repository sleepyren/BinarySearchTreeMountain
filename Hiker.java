//author: Renaldo Hyacinthe
//This is a Hiker Object for to be used in my BSTMountain class
//The Hiker can hold supplies, check if he has dry land, check if he has food
//, and check if he has a clear path
package project5;

import java.util.ArrayList;

public class Hiker
{
    //This constructor essentially clones a Hiker object
    Hiker(){}
    Hiker(Hiker hiker)
    {
        for (int i = 0; i < hiker.supplies.size(); i++)
        {
            this.supplies.add(hiker.supplies.get(i));
        }
    }
    //This array list of supplies is added to by the method below
    ArrayList<String> supplies = new ArrayList<>();
    public void addSupplies(String s)
    {
        supplies.add(s);
    }
    //Here the hiker checks if he is holding any food
    //if he is the method returns true and he eats it
    //otherwise the method returns false
    public boolean hasFood()
    {
        if( supplies.contains("food"))
        {
            supplies.remove("food");
            return true;
        }
        else
            return false;


    }

    //Here the Hiker object checks with the RestStop object it is currently
    //standing on if it can proceed or if a river blocks its path
    public boolean hasDryLand(RestStop stop)
    {
        if (stop.hasRiver())
        {
            if (supplies.contains("raft"))
            {
                supplies.remove("raft");
                return true;
            }
            else
                return false;


        }
        else
        {
            return true;

        }
    }

    //Here the Hiker object checks with the RestStop object it is currently
    //standing on if it can proceed or if a fallen tree blocks its path
    public boolean hasClearPath(RestStop stop)
    {
        if (stop.hasFallenTree())
        {
            if (supplies.contains("axe"))
            {
                supplies.remove("axe");
                return true;
            }
            else
                return false;
        }
        else
            return true;
    }


}
