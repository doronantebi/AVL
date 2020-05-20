
/**
 * AVLTree
 * An implementation of a AVL Tree with distinct integer keys and info
 */

public class AVLTree {
	
	private IAVLNode root ; //the root of the AVLTree
	private IAVLNode minimum ; // the node with the minimal key 
	private IAVLNode maximum ; // the node with the maximal key
	
	public AVLTree() {
		this.root = buildVirtualNode(null); // virtual node as place-holder
	}
	
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
     */
	public boolean empty() {
		return (!this.root.isRealNode()) ; 
	}
	
	
	/**
	 * public IAVLNode searchNodes(IAVLNode node, int k)
	 *
	 * returns the Node with key k if it exists in the subtree of node
	 * otherwise, returns a virtual node 
	 * 
	 * Runs in O(log n).
	 * Recursive method.
	 * 
	 * @pre node != null
	 */
	private IAVLNode searchNode(IAVLNode node, int k) {
		if(node.isRealNode()== false) {         // stops the search if we could not find the key
			return node;                                             // returns the virtual node
		}
		if(node.getKey() == k) {           // if the key is equal to the key of the current node
			return node;                                                     // returns the node 
		}
		else if(node.getKey() > k) {    // if the key is larger than the key of the current node
			return searchNode(node.getLeft(), k);   // searching for the key k in the left child 
		}
		else {                         // if the key is smaller than the key of the current node
			return searchNode(node.getRight(), k);  // searching for the key k in the left child
		}
	  }
	
	
	/**
	 * public String search(int k)
	 *
	 * returns the info of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k){
		IAVLNode result = searchNode(this.root, k);  // searching for the node with key k
		if(result.isRealNode()) {                         // checks if the key was actually found 
			return result.getValue();                      // returns the value of the node if found
		} else {
			return null;
		}
	}

	/**
	 * public int getBF()
	 * 
	 * Calculates the node's Balance Factor
	 */
	private int getBF(IAVLNode node) {		
		if (!(node.isRealNode())) {
			return 0;  // balance factor of a virtual node is 0 
		                             
		} else {
			return node.getLeft().getHeight() - node.getRight().getHeight(); 
			    //returns the node's balance factor	
		}
	}
	
	
	
	/**
	 * public IAVLNode buildVirtualNode(IAVLNode parent)
	 * 
	 * builds a virtual node
	 */
	private IAVLNode buildVirtualNode(IAVLNode parent) {
		/* IAVLNode result = new AVLNode(-1);
		    result.setHeight(-1);
		    result.setParent(parent);
		    return result;*/
		return new AVLNode(parent);
	}
	

	/**
	 * public IAVLNode caculateSize(IAVLNode node)
	 * 
	 * Calculates the size of a node
	 */
	private int caculateSize(IAVLNode node) {
		if (node.isRealNode()) {
			int result = 1;
			if (node.getLeft().isRealNode()) {
				result += node.getLeft().getSubtreeSize();
			}
			if (node.getRight().isRealNode()) {
				result += node.getRight().getSubtreeSize();
			}
			return result;	
		} else {                  // if this is a virtual node the size is 0  
			return 0;
		}
	}
	

	/**
	 * public IAVLNode caculateHeight(IAVLNode node)
	 * 
	 * Calculates the height of a node
	 */
	private int caculateHeight(IAVLNode node) {
		if (node.isRealNode()) {
			int height1 = node.getLeft().getHeight();
			int height2 = node.getRight().getHeight();
			if (height1 > height2) {   //            return the maximum height + 1 
				return (height1 + 1);
			} else {
				return (height2 + 1); 
			}
		} 
		return -1;                 // if this is a virtual node the height is -1 
	}
	
	
	/**
	 * Calculates sum of real nodes in node's subtree, including self
	 */
	private int calculateSubtreeSum(IAVLNode node) {
		if (!node.isRealNode())
			return 0;
		return node.getKey() + node.getLeft().getSubtreeSum() + node.getRight().getSubtreeSum();
	}
	
	
	/**
	 * public void Rotate(IAVLNode node)
	 * 
	 * make the correct AVL rotation according to the Balance Factor
	 * returns the number of rotations 
	 * 
	 */
	private int rotate(IAVLNode node)
	{
		if (node.isRealNode()) {
			if(getBF(node) == 2) {
				if(getBF(node.getLeft()) >= 0) {
					rotateLL(node);
					return 1;
				}
				else  { // means that node.getLeft() balance Factor == -1
					rotateLR(node);
					return 2;
				}					
			} else {      // means that node balance factor == -2  
				if(getBF(node.getRight()) == 1) {
					rotateRL(node);
					return 2;
				} else { // means that node.getRight() balance Factor == -1 or balance factor == 0
					rotateRR(node);
					return 1;
				}
			}
		} else {
			return 0;
		}
	}

	
	
	/**
	 * public void RotateRight(IAVLNode node)
	 *
	 * executing right rotation
	 * the parent becomes the right child   
	 * 
	 * 
	 *                 P							P
	 *                 |							|
	 *                 A (+2 bf)    ===>			B
	 *               /    \    		===>	      /   \
	 *              /      \ 		===>		 /     \
	 *            B(+1 bf)  E       ===>		C		A
	 *           /  \  				===>			   / \
	 * 			C    D							      D   E
	 *
	 */
	private void rotateRight(IAVLNode node)
	{
	
		IAVLNode P = node.getParent();   // a pointer to the parent of the AVL BF Offender 
		IAVLNode A = node ;              // the current BF offender
		IAVLNode B = node.getLeft(); 
									  // the left child of the BF offender, which has another left child
		IAVLNode D = node.getLeft().getRight(); 
										  // the left child of the right child of the BF offender
										  // must become the left child of the BF offender after rotation
		if(A != root) {
			if(A == P.getLeft()) {  //connecting the BF offender's parent to the BF offender left child
				P.setLeft(B);
			}
			else {
				P.setRight(B);
			}
		}
		A.setLeft(D);   // D becomes A's left child
		D.setParent(A); // A becomes D's parent
		B.setRight(A); // B's right child becomes A
		A.setParent(B); // A's parent becomes B
		B.setParent(P); // B's parent becomes P
		A.setSubtreeSize(caculateSize(A)); // setting A's new size 
		B.setSubtreeSize(caculateSize(B)); //setting B's new size
		
		B.setHeight(caculateHeight(B)); // setting B's new height
		
		A.setHeight(caculateHeight(A)); // setting A's new height
		
		A.setSubtreeSum(calculateSubtreeSum(A));
		B.setSubtreeSum(calculateSubtreeSum(B));
		
		if (A == root) {
			this.root = B ;
		}
		
	}

	/**
	 * public void RotateLeft(IAVLNode node)
	 *
	 * executing left rotation:
	 * the parent becomes the left child
	 *
	 *
	 *                P						   P
	 *                |						   |
	 *                A(-2 bf)     ===>		   B
	 *               / \           ===>	      / \
	 *              /   \ 		   ===>		 /   \
	 *             E     B(-1 bf)  ===>		A	  C
	 *            	    / \        ===>    / \
	 * 				   D   C 		      E   D
	 *
	 *  D and E could be virtual nodes
	 *
	 */
	 
	private void rotateLeft(IAVLNode node)
	{
		IAVLNode P = node.getParent();   // a pointer to the parent of the AVL BF Offender 
		IAVLNode A = node ;              // the current BF offender
		IAVLNode B = node.getRight() ; 
									  // the right child of the BF offender, which has another right child
		IAVLNode D = node.getRight().getLeft(); 
										  // the right child of the left child of the BF offender
										  // must become the right child of the BF offender after rotation
		if(A != root) {
			if(A == P.getLeft()) {  //connecting the BF offender's parent to the BF offender right child
				P.setLeft(B);
			}
			else {
				P.setRight(B);
			}
		}
		A.setRight(D);   //D becomes A's right child
		D.setParent(A); //A becomes D's parent
		B.setLeft(A); // B's left child becomes A
		A.setParent(B); // A's parent becomes B
		B.setParent(P); // B's parent becomes P
		A.setSubtreeSize(caculateSize(A)); // setting A's new size 
		B.setSubtreeSize(caculateSize(B)); //setting B's new size
		B.setHeight(caculateHeight(B)); // setting B's new height
		A.setHeight(caculateHeight(A)); // setting A's new height
		
		A.setSubtreeSum(calculateSubtreeSum(A));
		B.setSubtreeSum(calculateSubtreeSum(B));
		
		if (A == root) {
			this.root = B ;
		}
		
	}
	
	
	/**
	 * public void RotateRR(IAVLNode node)
	 *
	 * executing RR rotation
	 * 
	 */
	private void rotateRR(IAVLNode node)
	{
		rotateLeft(node);
		
	}
	/**
	 * public void RotateLL(IAVLNode node)
	 *
	 * executing LL rotation
	 * 
	 */
	private void rotateLL(IAVLNode node)
	{
		rotateRight(node);
	}
	/**
	 * public void RotateRL(IAVLNode node)
	 *
	 * executing RL rotation
	 * 
	 */
	private void rotateRL(IAVLNode node)
	{
		rotateRight(node.getRight());
		rotateLeft(node);
	}
	/**
	 * public void RotateLR(IAVLNode node)
	 *
	 * executing LR rotation
	 * 
	 */
	private void rotateLR(IAVLNode node)
	{

		rotateLeft(node.getLeft());
		rotateRight(node);
	}
	
	
	
	/**
	 * public int insert(int k, String i)
	 *
	 * inserts an item with key k and info i to the AVL tree.
	 * the tree must remain valid (keep its invariants).
	 * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
	 * returns -1 if an item with key k already exists in the tree.
	 */
	public int insert(int k, String i) {
		int count = 0 ;   // counter for the amount of rotations
		IAVLNode newNode = new AVLNode(k, i); // create a new leaf node with the key and value 
		
		/*newNode.setLeft(buildVirtualNode(newNode)); // set left virtual child
		newNode.setRight(buildVirtualNode(newNode)); // set right virtual child
		newNode.setSubtreeSize(1); //set the size of the new node
		newNode.setHeight(0); //set the height of the new node*/
		
		if (this.empty()) { //if the tree is empty we need to create the root
			root = newNode; 
			newNode.setParent(null);
			minimum = newNode;
			maximum = newNode; 
			return count;
		}
		IAVLNode node = searchNode(root, k);  
		if (node.getKey() == k) { // if the node exist in the tree 
			return -1;
		} else {                  // node is virtual, and newNode should replace it 
			if(k < minimum.getKey()) { //if the new key is smaller than the minimun -
				minimum = newNode; // it will become the new minimum
			}
			else if(k > maximum.getKey()) { //if the new key is larger than the maximum - 
				maximum = newNode; // it will become the new maximum
			}
			newNode.setParent(node.getParent());
			if (k < node.getParent().getKey()) { // connect the new node to it's place in the AVLTree
				node.getParent().setLeft(newNode);
			}
			else {
				node.getParent().setRight(newNode);
			}
			
			count = updateBalanceAndFields(node);

		}
		
		return count;	
	}

	private int updateBalanceAndFields(IAVLNode node) {
		int countRotations = 0;
		while (node != null) { // going up the AVLTree until we find an unbalanced node
			if ((getBF(node) > 1) || (getBF(node) < -1)) {
				countRotations += rotate(node); // if there is an violation of balance, make a rotation 
			}

			node.setHeight(caculateHeight(node));
			node.setSubtreeSize(caculateSize(node));
			node.setSubtreeSum(calculateSubtreeSum(node));
			node = node.getParent();  // go up to it's parent
		}
		return countRotations;
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there;
	 * the tree must remain valid (keep its invariants).
	 * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
	 * returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k){
		IAVLNode node = searchNode(this.root, k);
		IAVLNode parent = (node != this.root) ? node.getParent() : buildVirtualNode(null); // if root - use temp parent
		IAVLNode physicalDelParent = parent; // for rotations
		int countRotations = 0;
		
		if (!node.isRealNode())
			return -1;
		
		if (node ==  this.minimum)
			this.minimum = successor(node);
		else if (node == this.maximum)
			this.maximum = predecessor(node);
		
		if (node.getLeft().isRealNode() && node.getRight().isRealNode()) { // has 2 children
			IAVLNode successor = successor(node);
			IAVLNode sucParent = successor.getParent();
			setToParentCorrectSide(sucParent, successor, successor.getRight()); // delete successor. it has no left.
			setToParentCorrectSide(parent, node, successor); // connect successor in node's place
			successor.setLeft(node.getLeft());    // connect node's children to successor
			successor.setRight(node.getRight());
			successor.getLeft().setParent(successor);
			successor.getRight().setParent(successor);
			if (sucParent == node) // the successor's parent was the deleted node (which is no longer part of the tree)
				physicalDelParent = successor;
			else
				physicalDelParent = sucParent;
		}
		else if (node.getLeft().isRealNode()) { // has only left child
			setToParentCorrectSide(parent, node, node.getLeft());
		}
		else { // node has only a right child or is a leaf (in which case its right child is a virtual node)
			setToParentCorrectSide(parent, node, node.getRight());
		}
		
		if (node == this.root) {
			this.root = parent.getRight(); // if node is root then we invoked parent.setRight
			this.root.setParent(null);
		}
		node.disconnect();
		countRotations = updateBalanceAndFields(physicalDelParent);
		return countRotations;
	}
	
	/*
	 * check on which side curChild is a child to parent, and replace it with newChild
	 * 
	 * @pre curChild.getParent() == parent || curChild == root 
	 */
	private void setToParentCorrectSide(IAVLNode parent, IAVLNode curChild, IAVLNode newChild) {
		if (curChild == parent.getLeft())
			parent.setLeft(newChild);
		else
			parent.setRight(newChild);
		newChild.setParent(parent);
	}

	/**
	 * public IAVLNode findSubtreeMin(IAVLNode node){
	 *
	 * finds the minimal node in a subtree
	 */
	private IAVLNode findSubtreeMin(IAVLNode node){
		while (node.isRealNode()) {
			node = node.getLeft();
		}
		return node.getParent();	
	}
	
	/**
	 * public IAVLNode findSubtreeMax(IAVLNode node){
	 *
	 * finds the maximal node in a subtree
	 */
	private IAVLNode findSubtreeMax(IAVLNode node){
		while (node.isRealNode()) {
			node = node.getRight();
		}
		return node.getParent();	
	}

	/**
	 * public IAVLNode successor(IAVLNode node)
	 *
	 * finds the successor 
	 * if this is the maximum - returns null
	 */
	private IAVLNode successor(IAVLNode node){
		if (node == maximum) { 
			return null; // if this is the maximum there is no successor
		}
		if (node.getRight().isRealNode()) { // if there is a right child- the successor is the
			return findSubtreeMin(node.getRight());                 // minimum of it's subtree 
		} else {
			IAVLNode parent = node.getParent();  // otherwise, we must go up the tree until we
			while ((parent != null) && (node == parent.getRight())) {    // find the first parent
				node = parent;                              // with a larger key than our node
				parent = parent.getParent() ;                      // it will be the successor
			}
			return parent;	
			}
		}

	/**
	 * public IAVLNode predecessor(IAVLNode node)
	 *
	 * finds the predecessor
	 * if this is the minimum - returns null  
	 */
	private IAVLNode predecessor(IAVLNode node){
		if (node == minimum) {
			return null; // if this is the minimum there is no predecessor
		}
		if (node.getLeft().isRealNode()) { // if there is a left child- the predecessor is the
			return findSubtreeMax(node.getLeft());                  // maximum of it's subtree 
		} else {
			IAVLNode parent = node.getParent();  // otherwise, we must go up the tree until we
			while ((parent != null) && (node == parent.getLeft())) {   // find the first parent 
				node = parent;                             // with a smaller key than our node
 				parent = parent.getParent() ;                    // it will be the predecessor 
			}
			return parent;	
		}
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty
	 */
	public String min(){
		if (empty()) {
			return null;
		}
		return minimum.getValue();
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty
	 */
	
	public String max(){
		if (empty()) {
			return null;
		}
		return maximum.getValue();
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	public int[] keysToArray() {
		IAVLNode[] nodes = nodesToArray();
		int[] arr = new int[size()]; // empty tree => root is a virtual node => its size is set to 0
		for (int i = 0; i < arr.length; i++)
			arr[i] = nodes[i].getKey();
        return arr;
	}

	/**
	 * public String[] infoToArray()
	 *
	 * Returns an array which contains all info in the tree,
	 * sorted by their respective keys,
	 * or an empty array if the tree is empty.
	 */
	public String[] infoToArray(){
		IAVLNode[] nodes = nodesToArray();
		String[] arr = new String[size()]; // empty tree => root is a virtual node => its size is set to 0
		for (int i = 0; i < arr.length; i++)
			arr[i] = nodes[i].getValue();
        return arr;
	}

	private IAVLNode[] nodesToArray() {
		IAVLNode[] arr = new IAVLNode[size()];
		recNodesToArray(this.root, arr, 0);
		return arr;
	}

	private int recNodesToArray(IAVLNode node, IAVLNode[] arr, int i) {
		if (!node.isRealNode())
			return i;
		i = recNodesToArray(node.getLeft(), arr, i);
		arr[i++] = node;
		i = recNodesToArray(node.getRight(), arr, i);
		return i;
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none
	 * postcondition: none
	 */
	public int size(){
		return root.getSubtreeSize(); // empty tree => root is a virtual node => its size is set to 0
	}
	
	public int sum() {
		return root.getSubtreeSum();
	}
   
	/**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    */
	public IAVLNode getRoot(){
		if (!empty())
			return this.root;
		return null;
	}
    /**
    * public string select(int i)
    *
    * Returns the value of the i'th smallest key (return null if tree is empty)
    * Example 1: select(1) returns the value of the node with minimal key 
	* Example 2: select(size()) returns the value of the node with maximal key 
	* Example 3: select(2) returns the value 2nd smallest minimal node, i.e the value of the node minimal node's successor 	
    *
	* precondition: size() >= i > 0
    * postcondition: none
    */    
	public String select(int i){
	   if (empty() || i > root.getSubtreeSize() || i < 1)
		   return null;
	   
	   IAVLNode node = this.minimum;
	   while (node.getSubtreeSize() < i)   // go up from minimum until subtree has at least i nodes
		   node = node.getParent();        // all these nodes are left children to their parents
	   
	   int p = node.getLeft().getSubtreeSize() + 1; // at this point: p <= i
	   while (p != i) {
		   if (p < i) {
			   node = node.getRight();
			   p += node.getLeft().getSubtreeSize() + 1;
		   }
		   else {    // p > i
			   p -= node.getLeft().getSubtreeSize() + 1; // we counted too many, so we'll remove the last addition
			   node = node.getLeft();
			   p += node.getLeft().getSubtreeSize() + 1;
		   }
	   }
	   
	   return node.getValue();
	}
	/**
    * public int less(int i)
    *
    * Returns the sum of all keys which are less or equal to i
    * i is not necessarily a key in the tree 	
    *
	* precondition: none
    * postcondition: none
    */   
	public int less(int i){
		if (empty() || i < minimum.getKey())
			return 0;
		if (i >= maximum.getKey())
			return root.getSubtreeSum();
		
		int sum = 0;
		IAVLNode node = this.root;
		boolean done = false;
		while (node.isRealNode() && !done) {
			if (i < node.getKey())
				node = node.getLeft();
			else {
				sum += node.getLeft().getSubtreeSum() + node.getKey();
				if (node.getKey() == i)
					done = true;
				else
					node = node.getRight();
			}
		}
		
		return sum;	
	}

	/**
	* public interface IAVLNode
	* ! Do not delete or modify this - otherwise all tests will fail !
	*/
	public interface IAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
		public void setSubtreeSize(int size); // sets the number of real nodes in this node's subtree
		public int getSubtreeSize(); // Returns the number of real nodes in this node's subtree (Should be implemented in O(1))
		public void setHeight(int height); // sets the height of the node
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
    	
    	// additional methods
		public void disconnect(); // reset node's parent (to null) and children (to virtual nodes)
		public void setSubtreeSum(int size); // sets the sum of real nodes' keys in this node's subtree
		public int getSubtreeSum(); // Returns the sum of real nodes' keys in this node's subtree (in O(1))
    	
	}

	/**
	 * public class AVLNode
	 *
	 * If you wish to implement classes other than AVLTree
	 * (for example AVLNode), do it in this file, not in 
	 * another file.
	 * This class can and must be modified.
	 * (It must implement IAVLNode)
	 */
	public class AVLNode implements IAVLNode{
		private int key; //holds the node's key 
		private String value; //holds the node's value [info]
		private IAVLNode left; //holds the node's left child
		private IAVLNode right; //holds the node's right child
		private IAVLNode parent; //holds the node's parent
	  	private int size ;   //holds the amount of nodes in this subtree(including itself)
	  						 //node.size = node.left.size + node.right.size + 1
	  	private int height; //holds the node's height
	  	private int sum; // holds sum of real nodes' keys in this node's subtree
	  	
	  	public AVLNode(IAVLNode parent) {
			this.key = -1;
			this.value = null;
			this.height = -1;
			this.parent = parent;
			this.size = 0;
			this.sum = 0;
			this.left = null;
			this.right = null;
			// constructor of a virtual node
		}
	  	
	  	public AVLNode(int i, String val) {
			this.key = i;
			this.value = val;
			this.height = 0;
			this.size = 1;
			this.left = new AVLNode(this); // virtual node
			this.right = new AVLNode(this); // virtual node
			this.sum = i;
			this.parent = null;
			
			// constructor of a node by a key and value
		}		
		
		public int getKey(){
			return this.key; // returns the node's key
		}
		
		public String getValue(){
			return this.value; // returns the node's value
		}
		
		public void setLeft(IAVLNode node){
			 this.left = node; // sets the node's left child
		}
		
		public IAVLNode getLeft(){
			return this.left; // returns the node's left child
		}
		
		public void setRight(IAVLNode node){
			this.right = node; // sets the node's right child
		}
		
		public IAVLNode getRight(){
			return this.right; // returns the node's right child
		}
		
		public void setParent(IAVLNode node){
			 this.parent = node; // sets the node's parent
		}
		
		public IAVLNode getParent(){
			return this.parent; // returns the node's parent
		}
		
		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode(){
			if (this.key != -1) { // real nodes keys are natural numbers 
				return true;
			}
			return false; 
		}
		
		public void setSubtreeSize(int size){
			this.size = size; // sets the node's size 
		}
		
		public int getSubtreeSize(){
			return this.size; // returns the node's size
		}
		
		public void setHeight(int height){
			this.height = height ; // sets the node's height
		}
		
		public int getHeight(){
			return this.height; // returns the node's height 
		}

		//additional methods
		
		@Override
		public void disconnect() {
			this.setParent(null);
			this.setLeft(new AVLNode(this));
			this.setRight(new AVLNode(this));
			this.setSubtreeSize(1);
			this.setHeight(0);
		}

		@Override
		public void setSubtreeSum(int sum) {
			this.sum = sum;
		}

		@Override
		public int getSubtreeSum() {
			return this.sum;
		}	
  }
}
  

