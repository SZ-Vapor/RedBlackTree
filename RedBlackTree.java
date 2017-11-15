
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Steven
 */
public class RedBlackTree {

    public static RedBlackNode root;
    private static final int BLACK = 0;
    private static final int RED = 1;

    public void insert(int idNum, String firstName, String lastName, int age) {
        root = insert(root, idNum, firstName, lastName, age);
        root.color = BLACK;

    }

    private RedBlackNode insert(RedBlackNode newNode, int idNum, String firstName, String lastName, int age) {
        if (newNode == null) {
            return new RedBlackNode(idNum, firstName, lastName, age, RED);
        }

        if (lastName.compareTo(newNode.lastName) < 0) {
            newNode.leftChild = insert(newNode.leftChild, idNum, firstName, lastName, age);
        } else if (lastName.compareTo(newNode.lastName) > 0) {
            newNode.rightChild = insert(newNode.rightChild, idNum, firstName, lastName, age);

        } else if (lastName.compareTo(newNode.lastName) == 0) {
            if (firstName.compareTo(newNode.firstName) <= 0) {
                newNode.leftChild = insert(newNode.leftChild, idNum, firstName, lastName, age);
            } else {
                newNode.rightChild = insert(newNode.rightChild, idNum, firstName, lastName, age);
            }

        }

        if ((newNode.rightChild != null && newNode.rightChild.color == RED) && !(newNode.leftChild != null && newNode.leftChild.color == RED)) {//ROTATE LEFT
            RedBlackNode temp = newNode.rightChild;
            newNode.rightChild = temp.leftChild;
            temp.leftChild = newNode;

            temp.color = newNode.color;//COLOR SWAP
            newNode.color = RED;
            newNode = temp;

            newNode.color = temp.color;
        }
        if ((newNode.leftChild != null && newNode.leftChild.color == RED) && (newNode.leftChild.leftChild != null && newNode.leftChild.leftChild.color == RED)) {//RIGHT ROTATE
            RedBlackNode temp = newNode.leftChild;
            newNode.leftChild = temp.rightChild;
            temp.rightChild = newNode;

            temp.color = newNode.color;//COLOR SWAP
            newNode.color = RED;
            newNode = temp;

            newNode.color = temp.color;
        }
        if ((newNode.leftChild != null && newNode.leftChild.color == RED) && (newNode.rightChild != null && newNode.rightChild.color == RED)) {

            newNode.color = RED;
            newNode.leftChild.color = BLACK;
            newNode.rightChild.color = BLACK;
        }
        return newNode;
    }

    private boolean search(String name) {
        return search(root, name) != null;
    }

    public String searchString(String name) {
        if (search(name)) {
            return name;
        }
        return "The requested name does not exist within the tree.";
    }
    //TESTING
    public String search2(String names){
        return search(root,names);
    }
    //
    
    private String search(RedBlackNode x, String name) {//CHANGE RETURN TO JUST LAST NAME IF NEEDED

        while (x != null) {
            String arr[] = name.split(" ");
            int cmpLast = arr[1].compareTo(x.lastName);
            int cmpFirst = arr[0].compareTo(x.firstName);
            if (cmpLast < 0) {

                x = x.leftChild;
            } else if (cmpLast > 0) {

                x = x.rightChild;
            } else if (cmpFirst < 0) {
                x = x.leftChild;
            } else if (cmpFirst > 0) {
                x = x.rightChild;
            } else {
                return "ID:"+x.id+" | NAME:"+x.firstName+" "+x.lastName+" | AGE:"+x.age;
            }
        }
        return "The requested name does not exist within the tree.";
    }

    private boolean searchandCompare(String name) {
        return searchandCompare(root, name) != null;
    }

    public String searchCompString(String name) {
        if (searchandCompare(name)) {
            return "\n" + name + " is in the tree.";
        }
        return "The requested name does not exist within the tree.";

    }
    //TESTING
    public String searchComp2(String names){
        return searchandCompare(root,names);
    }
    //
    private String searchandCompare(RedBlackNode x, String name) {
        System.out.print("Compared with: ");
        while (x != null) {
            
            System.out.print(x.firstName + " " + x.lastName + ", ");
            String arr[] = name.split(" ");
            int cmpLast = arr[1].compareTo(x.lastName);
            int cmpFirst = arr[0].compareTo(x.firstName);
            if (cmpLast < 0) {

                x = x.leftChild;
            } else if (cmpLast > 0) {

                x = x.rightChild;
            } else if (cmpFirst < 0) {
                x = x.leftChild;
            } else if (cmpFirst > 0) {
                x = x.rightChild;
            } else {
                return "\nID:"+x.id+" | NAME:"+x.firstName+" "+x.lastName+" | AGE:"+x.age;
            }
        }
        System.out.println();
        return "The requested name does not exist within the tree.";
    }

    public String printLastNamesOfLetter(String names) {
        if (names.length() > 1) {
            return "Invalid input. Please input a single letter to check last names that start with that letter.";
        } else if (printLastNamesOfLetter(root, names).equals("")) {
            return ("No last names begin with " + names + ".");
        } else {
            return ("Last names that start with " + names + ": " + printLastNamesOfLetter(root, names));
        }
    }

    private String printLastNamesOfLetter(RedBlackNode node, String names) {
        String nameString = "";
        int lowerBound = Integer.MIN_VALUE;
        int upperBound = Integer.MAX_VALUE;

        if (node == null) {
            return "";
        }
        if (lowerBound < (int) node.lastName.charAt(0)) {
            nameString += printLastNamesOfLetter(node.leftChild, names);
        }
        if (lowerBound <= (int) node.lastName.charAt(0) && upperBound >= (int) node.lastName.charAt(0)) {
            if (names.charAt(0) == node.lastName.charAt(0)) {
                nameString += node.firstName + " " + node.lastName + ", ";
            }
        }
        if (upperBound > (int) node.lastName.charAt(0)) {
            nameString += printLastNamesOfLetter(node.rightChild, names);
        }
        return nameString;
    }

}
