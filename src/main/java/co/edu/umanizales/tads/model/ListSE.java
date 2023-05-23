package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListSE {

    private Node head;
    private int size;


    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el último

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */

    // Este es el codigo de añadir niños al final
    public void add(Kid kid) throws ListSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getIdentification().equals(kid.getIdentification())){
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if(temp.getData().getIdentification().equals(kid.getIdentification())){
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
    }
    public void deleteByidentification (String identification){
        Node currentNode = head;
        Node prevNode = null;

        while (currentNode != null && currentNode.getData().getIdentification() != identification) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        if(currentNode != null){
            if (prevNode == null){
                head = currentNode.getNext();
            }else {
                prevNode.setNext(currentNode.getNext());
            }
        }
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabez
     */
    public void addToStart(Kid kid){
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        }
        else {
            head = new Node(kid);
        }
        size++;
    }

    public void addKidsByPosition(Kid kid, int pos){
        Node newNode = new Node(kid);
        if (pos == 0){
            newNode.setNext(head);
            head = newNode;
        } else {
            Node current = head;
            for (int i = 1; i < pos - 1; i++){
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
    }

    // Desde aquí comienzan los 10 codigos, Este es el codigo 1.
    public void invert() throws ListSEException{
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
        else {
            throw new ListSEException("No hay niños para poder invertir la lista");
        }
    }
// Este es el codigo 2.
    public void orderBoysToStart() throws ListSEException {
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getGender()=='M')
                {
                    listCp.addToStart(temp.getData());
                }
                else{
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
        else {
            throw new ListSEException("No hay niños para completar esta operacion");
        }
    }

    // Este es el codigo 3.
    public void alternateKids() throws ListSEException {
        ListSE listCP = new ListSE();

        ListSE listBoys = new ListSE();
        ListSE listGirls = new ListSE();

        int countBoys = 0;
        int countGirls = 0;

        int totalKids;

        Node temp = head;

        if (this.head == null && this.head.getNext() == null) {
            throw new ListSEException("No existen niños o no hay suficientes para alternar");
        } else {
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listBoys.add(temp.getData());
                    countBoys++;
                } else {
                    if (temp.getData().getGender() == 'F') {
                        listGirls.add(temp.getData());
                        countGirls++;
                    }
                }
                temp = temp.getNext();
            }

            totalKids = countGirls + countBoys;
            Node boysNode = listBoys.getHead();
            Node girlsNode = listGirls.getHead();

            while (totalKids!=0){
                if (boysNode != null) {
                    listCP.add(boysNode.getData());
                    boysNode = boysNode.getNext();
                    countBoys--;
                }
                if (girlsNode != null) {
                    listCP.add(girlsNode.getData());
                    girlsNode = girlsNode.getNext();
                    countGirls--;
                }
                totalKids = countGirls + countBoys;
            }
            this.head = listCP.getHead();
        }
    }

    // Este es el codigo 4.
    public void deleteByAge (Byte age) throws ListSEException{
        Node temp = head;
        ListSE listcp = new ListSE();
        if (age <= 0){
            throw new ListSEException("La edad tiene que ser mayor que 0");
        }
        if (this.head == null){
            throw new ListSEException("No hay niños con los que se pueda realizar esta operacion");
        }
        while (temp != null){
            if (temp.getData().getAge() != age){
                listcp.addToStart(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = listcp.getHead();
    }
// Este es el codigo 5.
public float getAverageByAge()throws ListSEException{
    if(head != null){
        Node temp = head;
        int count = 0;
        int age = 0;
        while (temp.getNext() != null){
            count++;
            age = age + temp.getData().getAge();
            temp = temp.getNext();
        }
        return (float) age/count;
    }else{
        throw new ListSEException("No hay niños para poder hacer el promedio de edades");
    }
}
// Este es el codigo 6.
public int getCountKidsByLocationCode(String code) throws ListSEException{
    int count =0;
    if (this.head == null){
        throw new ListSEException("No hay niños para realizar esta operacion");
    }
    if( this.head!=null){
        Node temp = this.head;
        while(temp != null){
            if(temp.getData().getLocation().getCode().equals(code)){
                count++;
            }
            temp = temp.getNext();
        }
    }
    return count;
}

// Este es el codigo 7.
public void winPosition(String id, int position, ListSE listSE) throws ListSEException{
    if (head != null){
        Node temp = this.head;
        int counter = 0;

        while (temp != null && ! temp.getData().getIdentification().equals(id)){
            temp = temp.getNext();
            counter ++;
        }
        int newPosition = counter - position;
        Kid listCopy = temp.getData();
        listSE.deleteByidentification(temp.getData().getIdentification());
        listSE.addKidsByPosition(listCopy , newPosition);
    }
    else {
        throw new ListSEException("La lista esta vacia por lo tanto no se puede completar la accion");
    }
}
// Este es el codigo 8.
public void losePosition(String id, int position, ListSE listSE) throws ListSEException{
    if (head != null){
        Node temp = this.head;
        int counter = 1;

        while (temp != null && ! temp.getData().getIdentification().equals(id)){
            temp = temp.getNext();
            counter ++;
        }
        int newPosition = position+counter;
        Kid listCopy = temp.getData();
        listSE.deleteByidentification(temp.getData().getIdentification());
        listSE.addKidsByPosition(listCopy , newPosition);
    }
    else {
        throw new ListSEException("La lista esta vacia por lo tanto no se puede completar la accion");
    }
}

// Este es el 9.
public int getRangeByAge(int first, int last) throws ListSEException{
    Node temp = head;
    int count = 0;
    while (temp != null){
        if (temp.getData().getAge() >= first && temp.getData().getAge() <= last){
            count ++;
        }
        temp = temp.getNext();
    }
    return count;
}
// Este es el 10.
    public void sendKidToTheEndByLetter(char letter) throws ListSEException{
        ListSE listCopy = new ListSE();
        Node temp = this.head;

        while (temp != null) {
            if (temp.getData().getName().charAt(0) != Character.toUpperCase(letter)) {
                listCopy.add(temp.getData());
            }
            temp = temp.getNext();
        }

        temp = this.head;

        while (temp != null) {
            if (temp.getData().getName().charAt(0) == Character.toUpperCase(letter)) {
                listCopy.add(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = listCopy.getHead();
    }

// Hasta aqui son los 10 codigos de las listas simplemente enlazadas


public void addByPosition (Kid kid, int position) throws ListSEException{
        Node temp = head;
        Node newNode = new Node(kid);
        if (head != null){
            if (position > size){
                add(kid);
            } else if (position == 1){
                addToStart(kid);
            }else {
                for (int i = 0; i < position; i++){
                    temp = temp.getNext();
                }
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
            }
            size++;
        }
}


    public void changeExtremes() throws ListSEException{
        if(this.head !=null && this.head.getNext() !=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }
        else {
            throw new ListSEException("No hay niños con los que se pueda completar la operacion");
        }
    }


    public int getCountKidsByDeptoCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().substring(0,5).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }
    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report){
        if(head !=null){
            Node temp = this.head;
            while(temp!=null){
                if(temp.getData().getAge()>age){
                    report.updateQuantity(
                            temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }
}
