package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListSE {

    private Node head;
    private int size;

    public int Size() {
        int size = 0;
        Node temp = head;
        while (temp != null){
            size ++;
            temp = temp.getNext();
        }
        return size;
    }

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
    public void alternateKids() throws ListSEException{
        Node boys = head;
        Node girls = head.getNext();
        Node girlHead = girls;
        if (head == null || head.getNext() == null){
            throw new ListSEException("No existen niños o solo hay un niño en la lista");
        }
        while (girls != null && boys != null){
            boys.setNext(girls.getNext());
            if (girls.getNext() != null) {
                girls.setNext(girls.getNext().getNext());
            }
            boys = boys.getNext();
            girls = girls.getNext();
        }
        if (girls == null) {
            boys.setNext(girlHead);
        }else {
            girls.setNext(girlHead);
        }
    }
    // Este es el codigo 4.
    public void removeKidByAge(byte age) throws ListSEException {
        if (age <= 0) {
            throw new ListSEException("La edad debe ser mayor que 0");
        }
        Node current = head;
        Node prev = null;
        while (current != null) {
            if (current.getData().getAge() == age) {
                if (prev == null) {
                    head = current.getNext();
                }else {
                    prev.setNext(current.getNext());
                }
            } else {
                prev = current;
            }
            current = current.getNext();
        }
    }

// Este es el codigo 5.

    public int getLength() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public double getAverageAge() throws ListSEException {
        double averageAge = 0;
        Node temp = this.head;
        if (this.head != null) {
            while (temp != null) {
                averageAge = averageAge + temp.getData().getAge();
                temp = temp.getNext();
            }
            averageAge = averageAge / getLength();
            return averageAge;

        }else {
            throw new ListSEException("La lista está vacía");
        }
    }

// Este es el codigo 6.
public int getCountKidsByLocationCode(String code) throws ListSEException{
    int count =0;
    if( this.head!=null){
        Node temp = this.head;
        while(temp != null){
            if(temp.getData().getLocation().getCode().equals(code)){
                count++;
            }
            temp = temp.getNext();
        }
    } else{
        throw new ListSEException("El codigo de la localizacion no puede ser nulo");
    }
    return count;
}

// Este es el codigo 7.
public void winPositionKid(String id, int win) throws ListSEException {
    Node temp = head;
    int sum;
    ListSE listSE = new ListSE();
    if (head != null) {
        while (temp != null && !temp.getData().getIdentification().equals(id)) {
            listSE.add(temp.getData());
            temp = temp.getNext();
        }
        if (temp == null) {
            throw new ListSEException("No se encontró un niño con el ID " + id);
        }
        sum = temp.getData().getPosition() + win;
        if (sum < 0) {
            throw new ListSEException("No se puede mover el niño más allá de la primera posición");
        } else if (sum > Size()) {
            throw new ListSEException("No se puede mover el niño más allá de la última posición");
        }
        listSE.add(new Kid(temp.getData().getIdentification(),
                temp.getData().getName(), sum));
        temp = temp.getNext();
        while (temp != null) {
            listSE.add(temp.getData());
            temp = temp.getNext();
        }
        head = listSE.getHead();
    } else {
        throw new ListSEException("La lista está vacía");
    }
}

// Este es el codigo 8.
public void losePositionKid(Kid kid, int pos2) throws ListSEException{
        Node temp = head;
        Node newNode = new Node(kid);
        int listLength = getLength();
        if (pos2 < 0 || pos2 >= listLength)
            add(kid);
        if (pos2 == 0) {
            newNode.setNext(head);
            head = newNode;
        }else {
            for (int i = 0; temp.getNext() != null && i < pos2 - 1; i++) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }
}

// Este es el 9.

    public void getRangeByAge(byte minAge, byte maxAge) throws ListSEException{
        Node current = head;
        boolean found = false;
        while (current != null){
            byte edad = current.getData().getAge();
            if (edad >= minAge && edad <= maxAge){
                String name = current.getData().getName();
                found = true;
            }
            current = current.getNext();
        }
        if (!found){
            throw new ListSEException("No se encontraron niños en el rango de edad pedido");
        }

    }

// Este es el 10.

    public void addToStartNameChar(char letter) throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista esta vacia");
        }
        Node prev = null;
        Node current = head;
        Node last = null;
        while (current != null) {
            if (current.name.startsWith(String.valueOf(letter))) {
                if (prev == null) {
                    head = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                if (last == null) {
                    last = current;
                } else {
                    last.setNext(current);
                    last = current;
                }
                current = current.getNext();
                last.setNext(null);
            } else {
                prev = current;
                current = current.getNext();
            }
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
