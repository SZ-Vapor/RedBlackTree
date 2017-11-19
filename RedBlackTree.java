package edu.frostburg.COSC310.Hogenson;


import edu.frostburg.COSC310.Hogenson.RedBlackNode;


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

    public static RedBlackNode root = null;
    private static final int BLACK = 0;
    private static final int RED = 1;

    public void insert(int idNum, String firstName, String lastName, int age) {
        insert(new RedBlackNode(idNum,firstName,lastName,age));

    }

    private void insert(RedBlackNode newNode) {
        RedBlackNode temp = root;
        if (root == null) {
            root = newNode;
            newNode.color = BLACK;//root is always black
        } else {
            newNode.color = RED;//new nodes always get inserted as RED
            while (true) {
                if ((newNode.lastName).compareTo(temp.lastName) < 0) {//If last name of newly inserted node is "less than" the last name of the root node, compares to leftChild of root
                    if (temp.leftChild == null) {
                        temp.leftChild = newNode;
                        newNode.parent = temp;
                        break;
                    } else {
                        temp = temp.leftChild;
                    }
                } else if ((newNode.lastName).compareTo(temp.lastName) > 0) {//If last name of newly inserted node is "greater than" the last name of the root node, compares to rightChild of root
                    if (temp.rightChild == null) {
                        temp.rightChild = newNode;
                        newNode.parent = temp;
                        break;
                    } else {
                        temp = temp.rightChild;
                    }
                } else if ((newNode.firstName).compareTo(temp.firstName) <= 0) {//If last name of newly inserted node is "equal to" the last name of the root node, and if first name of newly inserted node is "less than or equal to" the first name of the root node, compares to leftChild of root compares to leftChild of root
                    if (temp.leftChild == null) {
                        temp.leftChild = newNode;
                        newNode.parent = temp;
                        break;
                    } else {
                        temp = temp.leftChild;
                    }
                } else//If last name of newly inserted node is "equal to" the last name of the root node, and if first name of newly inserted node is "greater than" the first name of the root node, compares to rightChild of root
                if (temp.rightChild == null) {
                    temp.rightChild = newNode;
                    newNode.parent = temp;
                    break;
                } else {
                    temp = temp.rightChild;
                }
            }
            balanceAndColor(newNode);//applies balancing and color rules after node is inserted
        }
    }

    //RELATIONSHIPS
    private RedBlackNode sibling(RedBlackNode x) {//return the "sibling" of a node (parent's other child)
        if (x.parent == null) {
            return null;
        }
        if (x == x.parent.rightChild && x.parent.leftChild != null) {
            return x.parent.leftChild;
        } else if (x == x.parent.leftChild && x.parent.rightChild != null) {
            return x.parent.rightChild;
        }
        return null;
    }

    private RedBlackNode grandparent(RedBlackNode x) {//returns the "grandparent" of a node (parent's parent)
        if (x.parent == null) {
            return null;
        }
        return x.parent.parent;
    }

    //ROTATIONS
    private void rotateLeft(RedBlackNode x) {
        RedBlackNode rC = x.rightChild;//used as constants for reference...
        RedBlackNode par = x.parent;//...
        RedBlackNode rClC = x.rightChild.leftChild;//...
        if (par != null) {
            if (x == par.leftChild) {
                par.leftChild = rC;
            } else {
                par.rightChild = rC;
            }
        } else {
            root = rC;//sets root node to rightChild of its own child
        }
        x.rightChild.parent = x.parent;//sets x's rightChild as parent of x, and daughter of x's parent

        if (rClC != null) {
            rClC.parent = x;//sets x as the new parent of x.rightChild.leftChild
        }
        x.rightChild = rClC;//sets rightchild to x.rightChild's leftChild node
        x.parent = rC;//sets x's rightChild as x's parent 
        rC.leftChild = x;//sets x.rightChild.leftChild to x

    }

    private void rotateRight(RedBlackNode x) {
        RedBlackNode lC = x.leftChild;//used as constants for reference..  
        RedBlackNode par = x.parent;//...
        RedBlackNode lCrC = x.leftChild.rightChild;//...
        if (par != null) {
            if (x == par.rightChild) {
                par.rightChild = lC;
            } else {
                par.leftChild = lC;
            }
        } else {
            root = lC;//sets root node to leftChild of its own child
        }
        x.leftChild.parent = x.parent;//sets x's leftChild as parent of x, and daughter of x's parent

        if (lCrC != null) {
            lCrC.parent = x;//sets x as the new parent of x.leftChild.rightChild
        }
        x.leftChild = lCrC;//sets leftChild to x.leftChild's rightChild node
        x.parent = lC;//sets x's leftChild as x's parent
        lC.rightChild = x;//sets x.leftChild.rightChild to x
    }

    //BLAANCE TREE
    private void balanceAndColor(RedBlackNode x) {
        if (x == root) {
            x.color = BLACK;//Root is always black
        } else if (x.color == RED && x.parent.color == RED) {//RED-RED relationship
            RedBlackNode uncle = sibling(x.parent);
            if (uncle == null || uncle.color == BLACK) {
                RedBlackNode p = x.parent;
                if (x.parent == grandparent(x).leftChild && x == x.parent.rightChild) {//If the parent of x is the leftChild of grandparent and x is the rightChild of parent (Zig-Zag)
                    rotateLeft(x.parent);
                    balanceAndColor(p);
                } else if (x.parent == grandparent(x).rightChild && x == x.parent.leftChild) {//If the parent of x is the rightChild of grandparent and x is the leftChild of parent (Zig-Zag)
                    rotateRight(x.parent);
                    balanceAndColor(p);
                } else if (x.parent == grandparent(x).leftChild && x == x.parent.leftChild) {//If the parent of x is the leftChild of grandparent and x is the leftChild of parent (Zig-Zig)
                    x.parent.color = BLACK;
                    grandparent(x).color = RED;
                    rotateRight(grandparent(x));
                } else if (x.parent == grandparent(x).rightChild && x == x.parent.rightChild) {//If the parent of x is the rightChild of grandparent and x is the rightChild of parent (Zig-Zig)
                    x.parent.color = BLACK;
                    grandparent(x).color = RED;
                    rotateLeft(grandparent(x));
                }
            } else { //Switches colors of parent and uncle if uncle is red
                x.parent.color = BLACK;
                uncle.color = BLACK;
                grandparent(x).color = RED;
                balanceAndColor(grandparent(x));
            }
        }
    }

    //SEARCH METHODS
    private boolean search(String name) {
        return search(root, name) != null;
    }

    public String searchString(String name) {
        if (search(name)) {
            return name;
        }
        return "The requested name does not exist within the tree.";
    }

    
    public String search2(String names) {
        return search(root, names);
    }
    

    private String search(RedBlackNode x, String name) {

        while (x != null) {
            String arr[] = name.split(" ");
            int cmpLast = arr[1].compareTo(x.lastName);
            int cmpFirst = arr[0].compareTo(x.firstName);
            if (cmpLast < 0) {//compare last names and go left if newNode's last name is less than root

                x = x.leftChild;
            } else if (cmpLast > 0) {//compare last names and go right if newNode's last name is greater than root

                x = x.rightChild;
            } else if (cmpFirst < 0) {//if last names are equal, compare first names and go left if newNode's first name is equal than root
                x = x.leftChild;
            } else if (cmpFirst > 0) {
                x = x.rightChild;
            } else {
                return "ID:" + x.id + " | NAME:" + x.firstName + " " + x.lastName + " | AGE:" + x.age;
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

    
    public String searchComp2(String names) {
        return searchandCompare(root, names);
    }

    
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
                return "\nID:" + x.id + " | NAME:" + x.firstName + " " + x.lastName + " | AGE:" + x.age;
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
        int lowerBound = (int) names.charAt(0) - 1;
        int upperBound = (int) names.charAt(0) + 1;

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

    public String printLastNamesOfName(String names) {

        if (printLastNamesOfName(root, names).equals("")) {
            return ("No people with the last name " + names + ".");
        } else {
            return ("People with the last name " + names + ": " + printLastNamesOfName(root, names));
        }
    }

    private String printLastNamesOfName(RedBlackNode node, String names) {
        String nameString = "";
        int lowerBound = (int) names.charAt(0) - 1;
        int upperBound = (int) names.charAt(0) + 1;

        if (node == null) {
            return "";
        }
        if (lowerBound < (int) node.lastName.charAt(0)) {//If node's last name is "greater than" requested letter, repeat the process again with leftChild
            nameString += printLastNamesOfName(node.leftChild, names);
        }
        if (lowerBound <= (int) node.lastName.charAt(0) && upperBound >= (int) node.lastName.charAt(0)) {//If node's last name's first letter is between the bounds
            if (names.equals(node.lastName)) {//If node's name equals requested name
                nameString += node.firstName + " " + node.lastName + ", ";
            }
        }
        if (upperBound > (int) node.lastName.charAt(0)) {//If node's last name is "less than" requested letter, repeat the process again with rightChild
            nameString += printLastNamesOfName(node.rightChild, names);
        }
        return nameString;
    }

}
