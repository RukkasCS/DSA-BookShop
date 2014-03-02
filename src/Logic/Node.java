/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

/**
 *
 * @author malinda
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Node 
{
 	//Variables
	int isbn;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	String title;
	private String authorName;
	private String authorSurname;
	
	Node leftChild;
	Node rightChild;

	public Node() 
	{
		// Default constructor
	}
	
	//Parameterized constructor
	public Node(int ISBN,String TITLE,String AUTHORNAME,String AUTHORSURNAME)
	{
		this.isbn=ISBN;
		this.title=TITLE;
		this.authorName=AUTHORNAME;
		this.authorSurname=AUTHORSURNAME;
	}
	
	//Display Records
	public static ArrayList<Node> display(ResultSet rs) throws SQLException
	{
		ArrayList<Node> nlist=new ArrayList<>();
		while (rs.next()) 
		{
			nlist.add(new Node(rs.getInt("ISBN"),rs.getString("TITLE"),rs.getString("AUTHORNAME"),rs.getString("AUTHORSURNAME")));
		}
		
		return nlist;
		/*for (Node node : nlist) 
		{
			System.out.println(node.isbn+"\t"+ node.title+"\t"+ node.authorName +"\t"+ node.authorSurname);
		}*/
		
	}   

    /**
     * @return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName the authorName to set
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * @return the authorSurname
     */
    public String getAuthorSurname() {
        return authorSurname;
    }

    /**
     * @param authorSurname the authorSurname to set
     */
    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    /**
     * @return the leftChild
     */
    public Node getLeftChild() {
        return leftChild;
    }

    /**
     * @param leftChild the leftChild to set
     */
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * @return the rightChild
     */
    public Node getRightChild() {
        return rightChild;
    }

    /**
     * @param rightChild the rightChild to set
     */
    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * @return the isbn
     */
    public int getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}
