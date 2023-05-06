package co.edu.umanizales.tads.model;


import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDE {
    private NodeDE head;
    private NodeDE tail;
    private int size;

    private List<Pet> pets = new ArrayList<>();


    public void addPet(Pet pet) throws ListDEException {
        if (head != null) {
            NodeDE temp = head;
            while (temp.getNextDE() != null) {
                if (temp.getData().getPetIdentification().equals(pet.getPetIdentification())) {
                    throw new ListDEException("Ya existe el perro");
                }
                temp = temp.getNextDE();
            }
            if (temp.getData().getPetIdentification().equals(pet.getPetIdentification())) {
                throw new ListDEException("Ya existe un perro");
            }
            /// Parado en el último
            NodeDE newNode = new NodeDE(pet);
            temp.setNextDE(newNode);
            newNode.setPrevious(temp);

        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    public List <Pet> print(){
        pets.clear();
        if (head != null){
            NodeDE temp = head;
            while (temp != null){
                pets.add(temp.getData());
                temp = temp.getNextDE();
            }
        }
        return pets;
    }


    public void addToStartPet(Pet pet) throws ListDEException {
        if (pet == null) {
            throw new ListDEException("El objeto pet no puede ser nulo");
        }

        NodeDE newNode = new NodeDE(pet);
        if (head != null) {
            head.setPrevious(newNode);
            newNode.setNextDE(head);
        }
        head = newNode;
        size++;
    }
    public void addByPosition(Pet pet, int position) throws ListDEException{
        if (position < 0 || position > size){
            throw new ListDEException("Posicion invalida:" + position);
        }
        NodeDE newNode = new NodeDE(pet);
        if (position == 0){
            newNode.setNextDE(head);
            if (head != null){
                head.setPrevious(newNode);
            }
            head = newNode;
        }else {
            NodeDE current = head;
            for (int i = 1; i < position -1; i++){
                current = current.getNextDE();
            }
            newNode.setNextDE(current.getNextDE());
            if (current.getNextDE() != null){
                current.getNextDE().setPrevious(newNode);
            }
            current.setNextDE(newNode);
            newNode.setPrevious(current);
        }
    }


    public void deleteById(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("El identificador del dueño no puede ser nulo");
        }

        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getPetIdentification().equals(id)) {
                NodeDE prev = temp.getPrevious();
                NodeDE next = temp.getNextDE();
                if (prev == null) {
                    head = next;
                } else {
                    prev.setNextDE(next);
                }
                if (next != null) {
                    next.setPrevious(prev);
                }
            }
            temp = temp.getNextDE();
        }
    }



// Aqui comienzan los 10 metodos, este es el 1.

    public void invertPets() throws ListDEException {
        if (this.head == null) {
            throw new ListDEException("No se puede invertir la lista porque esta vacia");
        }

        ListDE listcopy = new ListDE();
        NodeDE temp = this.head;
        while (temp != null) {
            listcopy.addToStartPet(temp.getData());
            temp = temp.getNextDE();
        }
        this.head = listcopy.getHead();
    }



// Este es el 2.

    public void orderPetsMasculineToStart() throws ListDEException {
        if (head == null) {
            throw new ListDEException(
                    "La lista está vacía y no se pueden agregar elementos");
        }
        ListDE listCopy = new ListDE();
        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getPetGender() == 'M') {
                listCopy.addToStartPet(temp.getData());
            } else {
                listCopy.addPet(temp.getData());
            }
            temp = temp.getNextDE();
        }
        head = listCopy.getHead();
    }

    // Este es el codigo 3.
    public void alternatePets() throws ListDEException {
        if (head == null) {
            throw new ListDEException("La lista está vacía y no se puede realizar el sorteo");
        }

        ListDE listM = new ListDE();
        ListDE listF = new ListDE();
        NodeDE temp = this.head;
        while (temp != null){
            if(temp.getData().getPetGender()=='M'){
                listM.addPet(temp.getData());
            }
            if(temp.getData().getPetGender()=='F'){
                listF.addPet(temp.getData());
            }
            temp = temp.getNextDE();
        }

        if (listM.getSize() == 0 || listF.getSize() == 0) {
            throw new ListDEException(
                    "No hay suficientes mascotas de ambos géneros para realizar el sorteo");
        }

        ListDE ListCP = new ListDE();
        NodeDE Nodem = listM.getHead();
        NodeDE Nodef = listF.getHead();
        while (Nodem != null || Nodef != null){
            if (Nodem != null){
                ListCP.addPet(Nodem.getData());
                Nodem = Nodem.getNextDE();
            }
            if (Nodef != null){
                ListCP.addPet(Nodef.getData());
                Nodef = Nodef.getNextDE();
            }
        }
        this.head = ListCP.getHead();
    }
// Este es el codigo 4.
public void deletePetByAge(Byte age) throws ListDEException {
    if (age == null) {
        throw new ListDEException("La edad de la mascota no puede ser nula");
    }
    NodeDE temp = head;
    while (temp != null) {
        if (temp.getData().getAgePet() == age) {
            NodeDE prev = temp.getPrevious();
            NodeDE next = temp.getNextDE();
            if (prev == null) {
                head = next;
            } else {
                prev.setNextDE(next);
            }
            if (next != null) {
                next.setPrevious(prev);
            }
        }
        temp = temp.getNextDE();
    }
}



    // Este es el codigo 5.
    public float averageAgePet() throws ListDEException {
        if (head != null) {
            NodeDE temp = head;
            int counter = 0;
            int ages = 0;
            while (temp.getNextDE() != null) {
                counter++;
                ages += temp.getData().getAgePet();
                temp = temp.getNextDE();
            }
            if (counter == 0) {
                throw new ListDEException("No se puede calcular el porcentaje en una lista vacia");
            }
            return (float) ages / counter;
        } else {
            throw new ListDEException("No se puede calcular el porcentaje en una lista vacia");
        }
    }

    // Este es el codigo 6.
    public int getCountPetsByLocationCode(String code) throws ListDEException {
        if(code == null || code.isEmpty()) {
            throw new ListDEException("El codigo de la ciudad no puede estar vacio");
        }
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNextDE();
            }
        }
        return count;
    }

    // Este es el codigo 7.

    public void winPetPosition(String id, int position) throws ListDEException {
        if (position < 0) {
            throw new ListDEException("La posición debe ser un número positivo");
        }
        if (head != null){
            NodeDE temp = head;
            int counter = 1;
            while (temp != null && !temp.getData().getPetIdentification().equals(id)){
                temp = temp.getNextDE();
                counter++;
            }
            if (temp != null){
                int newPosition = counter - position;
                if (newPosition < 0) {
                    throw new ListDEException(
                            "La posición especificada está fuera de los límites de la lista");
                }
                Pet listCopy = temp.getData();
                deleteById(temp.getData().getPetIdentification());
                if (newPosition > 0 ){
                    addByPosition(listCopy,newPosition);
                } else {
                    addToStartPet(listCopy);
                }
            }
        }
    }

    // Este es el codigo 8.

    public void losePetPosition(String id, int position) throws ListDEException{

        if (position < 0) {
            throw new ListDEException("La posicion debe ser positiva");
        }
        NodeDE temp = head;
        int count = 1;
        while (temp != null && !temp.getData().getPetIdentification().equals(id)) {
            temp = temp.getNextDE();
            count++;
        }

        int sum = position + count;
        Pet listCP = temp.getData();
        deleteById(temp.getData().getPetIdentification());
        addByPosition(listCP, sum);
    }

    // Este es el codigo 9.

    public int getPetRangeByAge(int Start, int finish) throws ListDEException {
        if (Start < 0 || finish < 0) {
            throw new ListDEException("El informe de rangos no puede ser negativo");
        }
        NodeDE temp = head;
        int counter = 0;
        while (temp != null){
            if (temp.getData().getAgePet() >= Start && temp.getData().getAgePet() <= finish){
                counter ++;
            }
            temp = temp.getNextDE();
        }
        return counter;
    }

    // Este es el codigo 10.

    public void sendPetToTheEndByLetter(char letter) throws ListDEException  {
        if (this.head != null) {
            ListDE listCopy = new ListDE();
            NodeDE temp = this.head;
            char firstChar = Character.toUpperCase(letter);

            while (temp != null) {
                char firstLetter = temp.getData().getNamePet().charAt(0);
                if (Character.toUpperCase(firstLetter) != firstChar) {
                    listCopy.addToStartPet(temp.getData());
                } else {
                    listCopy.addPet(temp.getData());
                }
                temp = temp.getNextDE();
            }
            this.head = listCopy.getHead();
        }else {
            throw new ListDEException("La lista no puede estar vacia");
        }
    }
    // Hasta aqui van los metodos
    public boolean checkPet(Pet pet) throws ListDEException {
        if (pet == null) {
            throw new ListDEException("La lista de los perros no puede estar vacia");
        }

        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {

                temp = temp.getNextDE();
            }
        }

        return true;
    }

    public int getCountPetsByDeptoCode(String code){
        if (code == null || code.length() != 5) {
            throw new IllegalArgumentException("El código de departamento debe tener 5 caracteres.");
        }
        int count = 0;
        if (this.head != null){
            NodeDE temp = this.head;
            while (temp != null){
                String locCode = temp.getData().getLocation().getCode();
                if (locCode != null && locCode.length() >= 5 && locCode.substring(0, 5).equals(code)){
                    count++;
                }
                temp = temp.getNextDE();
            }
        }
        return count;
    }
}
