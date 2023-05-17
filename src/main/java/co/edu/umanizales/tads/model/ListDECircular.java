package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.Random;

@Data
public class ListDECircular {
    private NodeDE headCircular;
    private int size;
    private NodeDE tail;

    /*
    Algoritmo para añadir un perro
creamos un nuevo nodo que tenga un perro adentro
preguntamos si no hay datos
    si no hay datos, agregamos a la mascota y esa seria la cabeza donde
    le decimos a los brazos que tiene que agarren tambien la cabeza
preguntamos si hay solo un dato
    si hay solo un dato que seria la cabeza, le diriamos a la cabeza que agarre con el brazo previo y
    el brazo next a nuevo nodo y que nuevo nodo haga lo mismo
preguntamos si hay mas de un dato
    si hay mas de un dato le decimos a la cabeza que nome con su brazo previo a el nuevo nodo y que el
    nuevo nodo, tome con su brazo previo al anterior
     */

    public void addPet(Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        if (headCircular == null) {
            headCircular = newNode;
            newNode.setNextDE(headCircular);
            newNode.setPrevious(headCircular);
        }
        if (size == 1) {
            headCircular.setPrevious(newNode);
            headCircular.setNextDE(newNode);
            newNode.setNextDE(headCircular);
            newNode.setPrevious(headCircular);
        }
        NodeDE laterNode = headCircular.getPrevious();
        if (size > 1) {
            headCircular.setPrevious(newNode);
            newNode.setNextDE(headCircular);
            newNode.setPrevious(laterNode);
            laterNode.setNextDE(newNode);
        }
        size++;
    }

    public Pet[] printCircular() throws ListDEException {
        Pet[] petList = new Pet[size];
        if (size == 0) {
            return petList;
        }
        int num = 0;
        NodeDE temp = headCircular;

        if (temp == null) {
            throw new ListDEException("La lista está vacía");
        }

        do {
            petList[num] = temp.getData();
            temp = temp.getNextDE();
            num++;
        } while (temp != headCircular);

        return petList;
    }

    /*
    Algoritmo para añadir al comienzo
    creo un nuevo nodo en donde va a ir una mascota
    y creamos tambien un nodo que se llame anterior el cual va a ser el ultimo nodo
    preguntamos si hay datos
    si no hay datos
        la cabeza seria igual a nuevo nodo el cual sus brazos se agarren a si mismo
    si hay datos
        le digo a el nodo previo a la cabeza que agarre a nuevo nodo y al brazo previo de la cabeza
        que tambien lo agarre
        y el nuevo nodo agarra con el previo a el nodo anterior y con el siguiente a la cabeza

    al final decimos que cabeza es igual a nuevo nodo
    y que el tamaño de la lista se sume en 1

     */
    public void addToStart(Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        NodeDE later = headCircular.getPrevious();
        if (headCircular == null) {
            addPet(pet);
            headCircular = newNode;
        } else {
            later.setNextDE(newNode);
            headCircular.setPrevious(newNode);
            newNode.setPrevious(later);
            newNode.setNextDE(headCircular);
            headCircular = newNode;
        }
        size++;
    }

/*
Algoritmo para añadir en posicion
primero el metodo me tiene que recibir una posicion
si la posicion es menor que cero ó si es mayor que el tamaño de la lista
    Se crea una excepcion la cual me diga que no se puede agregar la mascota en esa posicion
despues pregunto que si hay datos
    si hay datos miro la posicion
    si la posicion que nos dan es 1
    creo una variable que se llame before el cual sea el nodo anterior de la cabeza
    adentro de esta condicion pregunto que si el tamaño de la lista es igual a 1
    si es uno
    el nuevo nodo agarra con su brazo next y previous a la cabeza y la cabeza agarraria con sus dos brazos
    a nuevo nodo
    la cabeza seria igual a nuevo nodo
    si el tamaño es mayor que 1
    creo una variable que sea el nodo anterior de la cabeza que se llame before
    entonces añado el nuevo nodo al comienzo
    le digo a la cabeza que agarre con su previo a nuevo nodo y al before que agarre con su next a nuevo nodo
    le digo a nuevo nodo que agarre con su siguiente a la cabeza y con su previo a el before
    la cabeza seria igual a nuevo nodo
    si la posicion no es 1
    creo un temporal que sea igual a la cabeza
    creo un contador inicializado en 1
    hago un ciclo que sea que mientras el contador sea menor que la posicion menos 1
    le digo al temporal que se pase al siguiente y que aumente el contador en 1
    aqui se cierra el ciclo
    cuando encuentre la posicion, voy a decirle al nuevo nodo que ponga en su siguiente al siguiente nodo
    que seria el temporal.getNext
    y que ponga en el nodo anterior al temporal
    le digo al temporal.getNext que agarre con su brazo anterior a nuevo nodo
    y le digo al temporal que con su brazo siguiente agarre a nuevo nodo
    y sumo uno al tamaño cada vez que se añade en posicion 
     */


    public void addByPosition(Pet pet, int position) throws ListDEException {
        if (position < 0 || position > size) {
            throw new ListDEException("No se puede añadir la mascota en la posicion solicitada ");
        }
        NodeDE newNode = new NodeDE(pet);

        if (size > 0) {
            if (position == 1) {
                NodeDE before = headCircular.getPrevious();
                if (size == 1) {
                    headCircular.setPrevious(newNode);
                    headCircular.setNextDE(newNode);
                    newNode.setNextDE(headCircular);
                    newNode.setPrevious(headCircular);
                    headCircular = newNode;
                } else {
                    headCircular.setPrevious(newNode);
                    before.setNextDE(newNode);
                    newNode.setNextDE(headCircular);
                    newNode.setPrevious(before);
                    headCircular = newNode;
                }
            } else {
                NodeDE temp = headCircular;
                int counter = 1;
                while (counter < position - 1) {
                    temp = temp.getNextDE();
                    counter++;
                }
                newNode.setNextDE(temp.getNextDE());
                newNode.setPrevious(temp);
                temp.getNextDE().setPrevious(newNode);
                temp.setNextDE(newNode);
            }
        } else {
            throw new ListDEException("No hay datos para añadir en posicion");
        }
        size++;
    }
    /*
    Algoritmo para bañar perros con un numero aleatorio y girando en izquierda o derecha
    primero para el metodo tenemos que recibir la direccion que va a ser un char
    el cual "d" va a ser derecha e "i" va a ser izquierda
    el metodo nos va a tener que retornar el perro al cual vamos a bañar
    creo un temporal el cual me vaya recorriendo la lista
    hago una variable la cual me genere un numero random mayor que el numero de la lista en ese momento
    entonces comenzamos con las condiciones
    si el sentido en el que quiero girar es "d"
        hago un ciclo for el cual me genere un numero inicializado en 1 y que cuando sea menor que el
        numero random se agregue 1
        le digo a temporal que se pase al siguiente
        aqui se acaba el ciclo
    si el sentido en el que quiero girar es "i"
        hago un ciclo for el cual me genere un numero inicializado en 1 y que cuando sea menor que el
        numero random se agregue 1
        le digo a temporal que se pase al anterior
        aqui se acaba el ciclo
creo una mascota que sea tipo Pet el cual sea el temporal que me obtenga los datos
y hago una condicion la cual diga que si obteniendo el estado del perro bañado es diferente a verdadero
que el temporal obtenga los datos y cambie el estado del perro bañado a verdadero
despues retorno el perro
si el perro ya estaba bañado
retorno nulo
     */

    public Pet getBathedPets(char direction) {
        NodeDE temp = headCircular;
        Random random = new Random();
        int numRandom = random.nextInt(size + 1);
            if (direction == 'd') {
                for (int i = 1; i < numRandom; i++){
                    temp = temp.getNextDE();
                }
            }
            if (direction == 'i') {
               for (int i = 1; i < numRandom; i++){
                    temp = temp.getPrevious();
                }
            }
        Pet pet = temp.getData();
        if (pet.getBathedPet() != true){
            temp.getData().setBathedPet(true);
            return pet;
        }
        return null;
    }
}

