
public class BST{
    // the below variable should keep track of the number of nodes in the BST. Must increment/decrement num_nodes appropriately in insert/delete and ArrayToTree.

    private int num_nodes;
    private TreeNode root;
    private int[] traversal_array; 
    private int traversal_indx; 

//--------------------------------------------------Private tree node class -----------------------------------------
    private class TreeNode{
        //implement this class.
        private TreeNode left; 
        private TreeNode right; 
        private int data;
        
        
        public TreeNode(int data, TreeNode left, TreeNode right)
        {
            this.data = data; 
            this.left = left; 
            this.right = right; 
        }
        public TreeNode(TreeNode left, TreeNode right)
        {
            this.left = left; 
            this.right = right; 
        }
        public void setData(int data)
        {
            this.data = data; 
        }
        public int getData()
        {
            return this.data; 
        }
        public void setLeft(TreeNode left)
        {
            this.left = left; 
        }
        public TreeNode getLeft()
        {
            return this.left; 
        }public void setRight(TreeNode right)
        {
            this.right = right; 
        }
        public TreeNode getRight()
        {
            return this.right; 
        }
    }


// Start of Binary search tree implementation 
    public BST(){
        root = new TreeNode(null, null);
        num_nodes = 0; 
    }

    // returns the number of nodes in tree
    public int getNumNodes(){return num_nodes;}
    // inserts items into the tree 
    public void insert(int item){
        TreeNode temp = root; 
        // have to traverse the tree until we find the right spot to place the value
        // to do it recursively, it is useful to use a helper method. 
        insertHelp(temp, item);
        num_nodes++; 
    }
    private TreeNode insertHelp(TreeNode temp, int item)
    {
        // empty tree, so create a new node
        if(temp == null){return new TreeNode(item, null, null);}

        // want to place smaller values on the left and greater values on the right
        if(item < temp.getData())
        {
            temp.setLeft(insertHelp(temp.getLeft(), item));
        }
        else{
            temp.setRight(insertHelp(temp.getRight(), item));
        }
        return temp; 
    }
    // returns true if the item is in the tree
    public boolean find(int item){
        if(root == null){
            System.out.println("Empty Tree");
            return false;
        }

        TreeNode temp = root; 
        return item == findHelp(temp, item); 
    }
    // need help tot do it recursively. Search through the binary search tree for the desired value. If the value is found, it is returned. 
    private int findHelp(TreeNode node, int item)
    {
        // a value of -1 means not in the tree
        if(node == null){return -1;}
        if(item == node.getData())
        { //return item if found
            return item; 
        }// search left of tree if the value is less
        else if(item < node.getData())
        {
            return findHelp(node.getLeft(), item); 
        }// search to the right if the value is greater
        else{
            return findHelp(node.getRight(), item); 
        }
    }
// deletes a node from the tree
    public void delete(int item){

        if(!find(item)){return;} 
        TreeNode temp = root;  
        deleteHelp(temp, item);
        num_nodes--;
    }
    private TreeNode deleteHelp(TreeNode temp, int item)
    {
        if(temp == null){return null;}
        if(item < temp.getData())
        {// traverse the left side of tree and reassign pointers along the way
            temp.setLeft(deleteHelp(temp.getLeft(), item));
        }// traverse the right side if the value is greater than the root
        else if(item > temp.getData())
        {
            temp.setRight(deleteHelp(temp.getRight(), item));
        }// we found the node to delete
        else{
            // set the parent to the left child of the node we want to delete
            if(temp.getRight() == null){return temp.getLeft();}
            // set parent to the right child of the node we want to delete
            else if(temp.getLeft() == null){return temp.getRight();}
            else{
                // it has two children, so we reassaign pointer to the greater of the children
                TreeNode rt = root; 
                root = temp; 
                temp.setData(findMax());
                root = temp.getLeft(); 
                deleteMax();
                root = rt; 

            }
        }
        return temp; 
    }
// converts an array to a BST
// Part 2. Convert a sorted array to a binary search tree. 
// Takes a sorted array and converts it to a balanced binary tree. 
    public TreeNode ArrayToTree(int[] array, int low, int high){
        if(array.length == 0)
        {
            System.out.println("Empty array");
            return null;
        }
        
        // perform a binary search on the array and assign the values to nodes in a tree
        // assigns the root too. 
        
        root = Array_tree_help(array,  low, high);
        num_nodes = array.length; 

        return root;
        
    }


    private TreeNode Array_tree_help(int[] array, int low, int high){
        // essentially a recursive binary search that assigns values to the currect place in the tree
        
        if(low > high)
        {
            return null;
        }
        
        int mid = low + (high-low)/2; 
        
        TreeNode temp = new TreeNode(null, null); 
        temp.setData(array[mid]);

        temp.setLeft(Array_tree_help(array, low, mid-1));
        temp.setRight(Array_tree_help(array, mid+1, high));
        return temp; 

    }


   

    // Part 3. Traversals through the bianary search tree 
    //each traversal returns an array with the values in the order corresponding to their respectve binary tree. 

    // preorder traversal 
    public int[] preorder_traversal(){
        traversal_array= new int[num_nodes]; 
        traversal_indx = 0; 
        preorderHelp(root);
        return traversal_array; 

    }
    private void preorderHelp(TreeNode root)
    {
        if(root == null){return;}
        traversal_array[traversal_indx++] = root.getData(); 
        preorderHelp(root.getLeft()); 
        preorderHelp(root.getRight());  
        
    }

    // postorder traversal 
    public int[] postorder_traversal(){
        traversal_array = new int[num_nodes]; 
        traversal_indx = 0; 
        postorderHelp(root);
        return traversal_array;

    }
    private void postorderHelp(TreeNode root)
    {
        if(root == null){return;}
        postorderHelp(root.getLeft()); 
        postorderHelp(root.getRight()); 
        traversal_array[traversal_indx++] = root.getData(); 
        
    }

    //inorder traversal, prints values in sorted order. 
    public int[] inorder_traversal(){
        traversal_array = new int[num_nodes]; 
        traversal_indx = 0; 
        inorderHelp(root);
        return traversal_array;


    }
    private void inorderHelp(TreeNode root){
        if(root == null){return;}
        inorderHelp(root.getLeft()); 
        traversal_array[traversal_indx++] = root.getData(); 
        inorderHelp(root.getRight());  

    }

// Part 4. find the max value in the tree by traversing the right side
    public int findMax(){
        TreeNode temp = root; 
        return findMaxHelp(temp);

    }
    private int findMaxHelp(TreeNode curr_node){
        if(curr_node == null){return -1;}
        if(curr_node.getRight() != null){
            return findMaxHelp(curr_node.getRight());
        }
        return curr_node.getData();
    }
// find min value by traversing the left side
    public int findMin(){

        TreeNode temp = root; 
        return findMinHelp(temp);

    }
    private int findMinHelp(TreeNode curr_node){
        if(curr_node == null){return -1;}
        if(curr_node.getLeft() != null){
            return findMinHelp(curr_node.getLeft());
        }
        return curr_node.getData();
    }

    // want to delete the maximum value, so traverse the tree down the left branches. 
    // If cannot traverse to the left anymore, return the node on the right. 
    public void deleteMax(){
        root = deleteMaxHelp(root);
    }
    private TreeNode deleteMaxHelp(TreeNode curr_node){
        if(curr_node == null){
            return null;
        }
        if(curr_node.getLeft() == null){
            return curr_node.getRight();
        }
        curr_node.setLeft(deleteMaxHelp(curr_node.getLeft()));
        return curr_node;
    }

    // Main method for testing
    public static void main(String[] args)
    {
        BST trePR = new BST(); 
        BST trePT = new BST(); 
        BST treIN = new BST(); 
        
        
        int[] pr = {13,23,30,31,37,79,87,95,99}; 

        int[] pt = {11,28,30,41,43,50,61,67,68}; 

        int[] ino = {23,37,45,48,51,65,73,75,93};

        trePR.ArrayToTree(pr, 0, pr.length-1);
        trePT.ArrayToTree(pt, 0, pr.length-1);
        treIN.ArrayToTree(ino, 0, pr.length-1);
       

        
        int[] inord = treIN.inorder_traversal(); 
        int[] preod = trePR.preorder_traversal(); 
        int[] pos = trePT.postorder_traversal(); 

        for(int i = 0; i < pr.length; i ++){
            System.out.println(i + 1 + ": " + "Preorder " + preod[i]);
        }
        for(int i = 0; i < pr.length; i ++){
            System.out.println(i + 1+ ": " + "inorder " + inord[i]);
        }
        for(int i = 0; i < pr.length; i ++){
            System.out.println(i + 1+ ": " + "Post Order " + pos[i]);
        }

    }
}


