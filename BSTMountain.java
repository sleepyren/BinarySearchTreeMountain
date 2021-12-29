//author: Renaldo Hyacinthe with help from sources cited below
//This class creates an AVL tree based on characters it receives from
//the text file specified in main method's command line argument.
//the text file must follow the format: LABEL SUPPLIES OBSTACLES
//valid supplies are: food, raft or axe
//valid obstacles are fallen tree or river
//either supplies or obstacles can be an empty list
//It simulates a mountain, where a Hiker can traverse it, by
//passing through many RestStops
//do not forget to add command-line argument of text file

package project5;
public class BSTMountain {
    //Here the root is accessible because it is an important node that will be reused
    //the level integer is the maximum height of the tree fromm its root
    Node root;
    Hiker hiker = new Hiker();
    public BSTMountain()
    {}

    //wrapper method
    //also sets parent during every add procedure
    public void add(RestStop data)
    {

        root = add(data,root);
        parentSetter(null,root);
    }


    //This method takes a RestStop object and looks for a place to add it to the
    //tree
    //This function also rotates imbalanced nodes and uses recursion
    //This function was made with help from Dr. Joanna Klukowska's notes
    //on how to create add methods
    public Node add ( RestStop data, Node node)
    {
        //base case
        //when reached we return the new node
        if (node == null)
        {
            node = new Node(data);
            return node;
        }
        //if the new data is less than a node, then it proceeds
        //to that node's left sub-tree to be added
        else if (data.compareTo(node.data)<0)
        {
            node.left = add(data,node.left);
            node.height = height(node);
        }
        //if the new data is greater than a node, then it proceeds
        //to that node's right sub-tree to be added
        else if (data.compareTo(node.data)>0)
        {
            node.right = add(data,node.right);
            node.height = height(node);
        }
        //if the balance factor is > 1
        //or less than 1 the node must be balanced
        int balance = balanceFactor(node);
        if (balance > 1)
        {
            return rotate(node);
        }
        if (balance < -1)
            return rotate(node);

        //returns all the way to the root at the end
        return node;

    }
    //this method sets the parent field in a Node
    //essentially it identifies the Node's predecessor
    public void parentSetter(Node parent, Node child)
    {
        if (child == null)
        {
            return;
        }
        if (parent!=null)
        {
            child.parent = parent;
        }
        parent = child;
        parentSetter(parent,child.left);
        parentSetter(parent,child.right);



    }

    //finds the maximum height of the root node
    //height is calculated starting from one here
    public int depth(Node root)
    {
        if (root!=null)
        {
            int left = depth(root.left);
            int right = depth(root.right);
            if (left>right)
                return left + 1;
            else
                return right + 1;
        }
        else
            return 0;
    }
    //finds the balance factor of a node
    //right sub-tree height - left sub-tree height
    //remember height is calculated starting from one here
    public int balanceFactor(Node n)
    {
        int rightheight = 0;
        int leftheight = 0;
        if (n.right!=null)
            rightheight = n.right.height;
        if (n.left!=null)
            leftheight = n.left.height;
        return rightheight - leftheight;

    }


    //calculates the height of a particular node by recursive storing the taller
    //tree of the two sub trees
    public int height(Node n)
    {
        if ((n.left!=null) && (n.right!=null))
        {
            return Math.max(n.left.height, n.right.height) + 1;

        }
        else if ((n.left==null) && (n.right!=null))
            return n.right.height + 1;
        else if ((n.left!=null)&&(n.right==null))
            return n.left.height + 1;
        else
            return 1;
    }

    //this is called when a solution is reached in the journey function
    //it calls on the parent field of each node included within a particular
    //solution
    public void solutionPrinter(Node leaf)
    {
        String pathway="";
        while (leaf != null)
        {
            if (leaf.parent != null)
            pathway = " " + leaf.data.label + pathway;
            //when we reach the root, we do not want to add
            //any more spaces
            else
               pathway = leaf.data.label + pathway;
            leaf = leaf.parent;
        }
        System.out.println(pathway);
    }
    //wrapper method
    public String toStringTree( )
    {
        StringBuffer sb = new StringBuffer();
        toStringTree(sb, root, 0);
        return sb.toString();
    }

    //wrapper method that also sets parents
    public void BSTjourney(Node root, Hiker hiker)
    {
        parentSetter(null,root);
        BSTjourney(root,hiker,depth(root));
    }

    //this method starts the hiker at the root of the tree, then they traverse it

    public void BSTjourney(Node root, Hiker hiker, int level)
    {
        if (root==null)
            return;
        //when our lowest height is reached, then we will return a solution
        if (level==1) {
            solutionPrinter(root);
        }
        //here the hiker gains all items in the RestStop
        for (int i = 0; i < root.data.supplies.size(); i++)
        {
            hiker.addSupplies(root.data.supplies.get(i));
        }
        //if all of these conditions are filled, then the hiker explores the left subtree
        //and the right sub-tree
        if (hiker.hasFood() && hiker.hasDryLand(root.data) && hiker.hasClearPath(root.data))
        {

            BSTjourney(root.left, new Hiker(hiker), level-1);
            BSTjourney(root.right, new Hiker(hiker),level -1);
        }

    }
    //this method is quite literally copy and pasted from
    //Professor Klukowska's EdStem page
    //mostly for visual testing purposes
    private void toStringTree( StringBuffer sb, Node node, int level ) {
        if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append( "->\n");
            return;
        }
        else {
            sb.append( node.data.label + "\n");
        }

        toStringTree(sb, node.left, level+1);
        toStringTree(sb, node.right, level+1);
    }

    //ALL rotation algorithms are heavily based on
    //notes and information provided by Dr. Klukowska
    //in her course


    //Left sub-tree Left sub-tree imbalance
    //this method pulls the left sub-tree up, and pushes the root down
    //it returns the new root (left sub-tree)
    public Node LLRotation(Node node)
    {
        Node nodeleft = node.left;
        node.left = nodeleft.right;
        nodeleft.right = node;
        node.height=height(node);
        nodeleft.height = height(nodeleft);
        return nodeleft;

    }
    //Right sub-tree Right sub-tree imbalance
    //this method pulls the right sub-tree up, and pushes the root down
    //it returns the new root (right sub-tree)
    public Node RRRotation(Node node)
    {
        Node noderight = node.right;
        node.right = noderight.left;
        noderight.left = node;
        node.height=height(node);
        noderight.height = height(noderight);
        return noderight;
    }
    //Left sub-tree Right sub-tree imbalance
    //this method pulls the left sub-tree's right subtree up and pushes the root down
    //it returns the new root (left sub-tree's right sub-tree)
    public Node LRRotation(Node A)
    {
        Node B = A.left;
        Node C = B.right;
        A.left = C.right;
        B.right = C.left;
        C.left = B;
        C.right = A;
        A.height = height(A);
        B.height = height(B);
        C.height = height(C);
        return C;

    }
    //Right sub-tree Left sub-tree imbalance
    //this method pulls the right sub-tree's left subtree up and pushes the root down
    //it returns the new root (right sub-tree's left sub-tree)
    public Node RLRotation(Node A)
    {
        Node B = A.right;
        Node C = B.left;
        A.right = C.left;
        B.left = C.right;
        C.right = B;
        C.left = A;
        A.height = height(A);
        B.height = height(B);
        C.height = height(C);
        return C;

    }
    //wrapper method for all rotate methods
    public Node rotate(Node root)
    {
        //if this tree is right sub-tree heavy
        if (balanceFactor(root) > 1)
        {
            //if this node is right or left sub-tree heavy
            if (balanceFactor(root.right)>=0) {
                root = RRRotation(root);
            }
            else
                root = RLRotation(root);


        }
        //if this tree is right sub-tree heavy
        else if (balanceFactor(root)<-1)
        {
            //if this node is right or left sub-tree heavy
            if (balanceFactor(root.left)<=0)
                root = LLRotation(root);
            else
                root = LRRotation(root);

        }
        return root;
    }

     private class Node implements Comparable<Node> {

        //basic node implementation with left, right, intiail height, parent, and data elements
        RestStop data;
        Node left;
        Node right;
        int height = 1;
        Node parent;

        //Node constructor for easy data addition
        public Node(RestStop data) {
            this.data = data;
        }
        //compares the nodes by comparing their RestStop data defined in RestStop
        public int compareTo(Node other) {
            return this.data.compareTo(other.data);
        }

    }
}
