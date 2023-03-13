package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ListSE {
    private Node head;

    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
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


    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
    }


    /*algoritmo para adicionar en posicion
    primero saber el tamaño de la lista con un metodo que recorra  la lista hasta que no haya un niño siguiente y en cada vuelta del bucle se va contando
    luego se crea un meotdo donde recive el niño nuevo y en donde se quiere ser agregado
    si la posicion que se quiere es coherente con el tamaño de la lista
      entonces
        se crea el niño
         si la posicion donde se quiere adicionar es 0  lo que sera la cabeza entonces
            entonces  se llamara el metodo que agrega el niño al inicio
         en caso de que sea otra posicion diferente a 0
            entonces se crea un mensajero para que vaya una posicion antes de la pedida
                cuando se llegue a a la posicion entonces
                    se crea un nuevo nodo
                    y el siguiente niño del nuevo nodo sera lo siguiente que teniea el menesajero
                    y lo siguiente del mensjaero sera el nuevo nodo

        en caso de que la posicion no sea posible se agrega al final con un metood agregar

    * */

    public int size() {
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.getNext();
        }
        return size;
    }


    public void addInPosition(int position, Kid kid) {

        if (size() >= position) {


            if (position == 0) {
                addToStart(kid);
            } else {
                Node temp = head;
                for (int i = 0; i < position - 1; i++) {
                    temp = temp.getNext();
                }
                Node newNode = new Node(kid);
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);

            }
        } else {
            add(kid);
        }
    }

    /*
     * algoritmo para eliminar por id
     * se crea una mensajero que sea igual a la cabeza
     * y otro nodo el cual sera para indentificar al anterior nodo
     * lugo se recorre la lista solo en caso de que el mensajer tenga algun valor y en caso de no se haya encontrado el niño con ese id
     * a dentro del bucle el segudno nodo sera igual al mensajero y el mensajero para a otra niño asi hasta que termine el ciclo
     * en caso de que el mensajero no haya quedado vacio
     * entonces
     *       si el nodo previous  es null es porque al que se quiere eliminar es a la cabeza  entonces nunca entraron al bulce
     *           entonces la cabeza sera el valor que le seguia al mensajero o sea el valor sigueite a la cabeza
     *
     *
     *    */

    public void deleteByIdentification(String identification) {
        Node temp = head;
        Node previousNode = null;


        while (temp != null && temp.getData().getIdentification() != (identification)) {
            previousNode = temp;
            temp = temp.getNext();

        }
        if (temp != null) {
            if (previousNode == null) {
                head = temp.getNext();
            } else {
                previousNode.setNext(temp.getNext());
            }
        }


    }
// Añadir niños al comienzo y niñas al final
    public void boysFirst(String gender, Kid kid) {
        Node temp = head;
        temp = temp.getNext();
        while(temp != null && temp.getData().getGender() != ("Masculino"))
            add(kid);
        if (gender == "Masculino") {
            addToStart(kid);
        }
    }
    public void rangeByAge(int age, Kid kid){
        Node temp = head;
        temp = temp.getNext();
        //Declaro variables para los rangos
        int range1to3 = 0;
        int range4to6 = 0;
        int range7to9 = 0;
        int range10to12 = 0;
        int range13to15 = 0;

        while(kid != null){
        kid.getAge();
        }
        if (age >= 1 && age<= 3){
            range1to3++;
        } else if (age >= 4 && age <= 6) {
            range4to6++;
        } else if (age >= 7 && age<= 9) {
            range7to9++;
        } else if (age >= 10 && age<= 12) {
            range10to12++;
        } else if (age >= 13 && age<= 15) {
            range13to15++;
        }

    }
}
