package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class Node {

    private Kid data;
    private Node next;
    public  Kid kid;
    public String name;

    public Node(Kid data) {
        this.data = data;
    }
}
