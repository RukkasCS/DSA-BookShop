/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import java.sql.ResultSet;
import java.util.Stack;


/**
 *
 * @author malinda
 */
public class BinaryTree 
{
	//Variables
	Node root;

	
	public BinaryTree() 
	{
	// Default constructor
		try 
		(
				java.sql.Connection conn= DbUtil.getConnection(DbType.MySQL);
				java.sql.Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery("SELECT * FROM BOOK");		
		)
		{
			for (Node result :Node.display(rs)) 
			{
				this.addNode(result);
				//System.out.println(result.isbn+"\t"+result.title+"\t"+result.authorName+"\t"+result.authorSurname);
			}	
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
	public void addNode(Node node)
	{
		Node newNode=node;
		
		//If there is no root this becomes root
		
		if (root==null) 
		{
			root=newNode;
		}
		else
		{
			Node focusNode=root;
			
			Node parentNode;
			
			while (true) 
			{
				parentNode=focusNode;		
				if (newNode.title.compareTo(focusNode.title)<0) 
				{
					focusNode=focusNode.leftChild;
					if (focusNode==null) 
					{
						parentNode.leftChild=newNode;						
						return;
					}
				}
				else
				{
					focusNode=focusNode.rightChild;
					if (focusNode==null)
					{
						parentNode.rightChild=newNode;
						return;
					}
				}
				
			}
		}
	}

	public Node SearchNode(Node node)
	{
		Node focusNode=root;
		
		if (node.title==null) 
		{
			while(focusNode.isbn<node.isbn)
			{
				if (node.isbn<focusNode.isbn)
				{
					focusNode=focusNode.leftChild;
				}
				else
				{
					focusNode=focusNode.rightChild;
				}
				
					if (focusNode==null)
			{
				return null;
			}	
		}
			
				return focusNode;
		}
		else
		{
			while(focusNode.title.compareToIgnoreCase(node.title)!=0)
			{
				if (node.title.compareTo(focusNode.title)<0)
				{
					focusNode=focusNode.leftChild;
				}
				else
				{
					focusNode=focusNode.rightChild;
				}
				
					if (focusNode==null)
			{
				return null;
			}	
	 	   }
			
			return focusNode;
		}
		
		
		//while (focusNode.title.charAt(0)!=node.title.charAt(0))
		
		
	}
	
	//Search similar nodes	


	//Remove node
	
	public boolean remove(Node node)
	{

		if (node.title==null) 
		{
			// Start at the top of the tree

			Node focusNode = root;
			Node parent = root;

			// When searching for a Node this will
			// tell us whether to search to the
			// right or left

			boolean isItALeftChild = true;

			// While we haven't found the Node
			// keep looking

			while (focusNode.isbn<node.isbn)
			{

				parent = focusNode;

				// If we should search to the left

				if (node.isbn<focusNode.isbn)
				{

					isItALeftChild = true;

					// Shift the focus Node to the left child

					focusNode = focusNode.leftChild;

				} 
				else 
				{

					// Greater than focus node so go to the right

					isItALeftChild = false;

					// Shift the focus Node to the right child

					focusNode = focusNode.rightChild;

				}

				// The node wasn't found

				if (focusNode == null)
					return false;

			}

			// If Node doesn't have children delete it

			if (focusNode.leftChild == null && focusNode.rightChild == null) 
			{

				// If root delete it

				if (focusNode == root)
					root = null;

				// If it was marked as a left child
				// of the parent delete it in its parent

				else if (isItALeftChild)
					parent.leftChild = null;

				// Vice versa for the right child

				else
					parent.rightChild = null;

			}

			// If no right child

			else if (focusNode.rightChild == null) 
			{

				if (focusNode == root)
					root = focusNode.leftChild;

				// If focus Node was on the left of parent
				// move the focus Nodes left child up to the
				// parent node

				else if (isItALeftChild)
					parent.leftChild = focusNode.leftChild;

				// Vice versa for the right child

				else
					parent.rightChild = focusNode.leftChild;

			}

			// If no left child

			else if (focusNode.leftChild == null)
			{

				if (focusNode == root)
					root = focusNode.rightChild;

				// If focus Node was on the left of parent
				// move the focus Nodes right child up to the
				// parent node

				else if (isItALeftChild)
					parent.leftChild = focusNode.rightChild;

				// Vice versa for the left child

				else
					parent.rightChild = focusNode.rightChild;

			}

			// Two children so I need to find the deleted nodes
			// replacement

			else 
			{

				Node replacement = getReplacementNode(focusNode);

				// If the focusNode is root replace root
				// with the replacement

				if (focusNode == root)
					root = replacement;

				// If the deleted node was a left child
				// make the replacement the left child

				else if (isItALeftChild)
					parent.leftChild = replacement;

				// Vice versa if it was a right child

				else
					parent.rightChild = replacement;

				replacement.leftChild = focusNode.leftChild;

			}

			return true;
		}
		else
		{
		// Start at the top of the tree

		Node focusNode = root;
		Node parent = root;

		// When searching for a Node this will
		// tell us whether to search to the
		// right or left

		boolean isItALeftChild = true;

		// While we haven't found the Node
		// keep looking

		while (focusNode.title.compareTo(node.title)!=0)
		{

			parent = focusNode;

			// If we should search to the left

			if (node.title.compareTo(focusNode.title)<0)
			{

				isItALeftChild = true;

				// Shift the focus Node to the left child

				focusNode = focusNode.leftChild;

			} 
			else 
			{

				// Greater than focus node so go to the right

				isItALeftChild = false;

				// Shift the focus Node to the right child

				focusNode = focusNode.rightChild;

			}

			// The node wasn't found

			if (focusNode == null)
				return false;

		}

		// If Node doesn't have children delete it

		if (focusNode.leftChild == null && focusNode.rightChild == null) 
		{

			// If root delete it

			if (focusNode == root)
				root = null;

			// If it was marked as a left child
			// of the parent delete it in its parent

			else if (isItALeftChild)
				parent.leftChild = null;

			// Vice versa for the right child

			else
				parent.rightChild = null;

		}

		// If no right child

		else if (focusNode.rightChild == null) 
		{

			if (focusNode == root)
				root = focusNode.leftChild;

			// If focus Node was on the left of parent
			// move the focus Nodes left child up to the
			// parent node

			else if (isItALeftChild)
				parent.leftChild = focusNode.leftChild;

			// Vice versa for the right child

			else
				parent.rightChild = focusNode.leftChild;

		}

		// If no left child

		else if (focusNode.leftChild == null)
		{

			if (focusNode == root)
				root = focusNode.rightChild;

			// If focus Node was on the left of parent
			// move the focus Nodes right child up to the
			// parent node

			else if (isItALeftChild)
				parent.leftChild = focusNode.rightChild;

			// Vice versa for the left child

			else
				parent.rightChild = focusNode.rightChild;

		}

		// Two children so I need to find the deleted nodes
		// replacement

		else 
		{

			Node replacement = getReplacementNode(focusNode);

			// If the focusNode is root replace root
			// with the replacement

			if (focusNode == root)
				root = replacement;

			// If the deleted node was a left child
			// make the replacement the left child

			else if (isItALeftChild)
				parent.leftChild = replacement;

			// Vice versa if it was a right child

			else
				parent.rightChild = replacement;

			replacement.leftChild = focusNode.leftChild;

		}

		return true;
		}

	}

	public Node getReplacementNode(Node replacedNode) 
	{

		Node replacementParent = replacedNode;
		Node replacement = replacedNode;

		Node focusNode = replacedNode.rightChild;

		// While there are no more left children

		while (focusNode != null) 
		{

			replacementParent = replacement;

			replacement = focusNode;

			focusNode = focusNode.leftChild;

		}

		// If the replacement isn't the right child
		// move the replacement into the parents
		// leftChild slot and move the replaced nodes
		// right child into the replacements rightChild

		if (replacement != replacedNode.rightChild)
		{

			replacementParent.leftChild = replacement.rightChild;
			replacement.rightChild = replacedNode.rightChild;

		}

		return replacement;

	}
	
	public void displayTree()
	{
		Stack globalStack = new Stack<Node>();
		globalStack.push(root);	
		int emptyLeaf = 32;
		boolean isRowEmpty = false;
	//	System.out.println("****......................................................****");
		while(isRowEmpty==false)
	{

	Stack localStack = new Stack();
	isRowEmpty = true;
	for(int j=0; j<emptyLeaf; j++)
	System.out.print(' ');
	
	while(globalStack.isEmpty()==false)
	{
		Node temp = (Node) globalStack.pop();
		if(temp != null)
		{
			System.out.print(temp.title);
			localStack.push(temp.leftChild);
			localStack.push(temp.rightChild);
			if(temp.leftChild != null ||temp.rightChild != null)
		    isRowEmpty = false;
		}
		else
	{
		System.out.print("--");
		localStack.push(null);
		localStack.push(null);
	}
		
		for(int j=0; j<emptyLeaf*2-2; j++)
		System.out.print(' ');
	}
	System.out.println();
	
	emptyLeaf /= 2;
		while(localStack.isEmpty()==false)
			globalStack.push( localStack.pop() );
	}
		//System.out.println("****......................................................****");
	} 	
	 	
}
