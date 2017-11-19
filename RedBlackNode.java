package edu.frostburg.COSC310.Hogenson;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Steven
 */
public class RedBlackNode {

    static final int BLACK = 1;
    static final int RED = 0;
    RedBlackNode leftChild;
    RedBlackNode rightChild;
    RedBlackNode parent;
    int id;
    String firstName;
    String lastName;
    int age;
    int color;

    public RedBlackNode(int id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.leftChild = null;
        this.rightChild = null;
        this.color = RED;
        

    }
}
