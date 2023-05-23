package co.edu.umanizales.tads.model;


import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDE {
    private NodeDE headDE;
    private NodeDE tail;
    private int size;

    private List<Pet> pets = new ArrayList<>();


    public void addPet(Pet pet) throws ListDEException {
        if (headDE != null) {
            NodeDE temp = headDE;
            while (temp.getNextDE() != null) {
                if (temp.getData().getPetIdentification().equals(pet.getPetIdentification())) {
                    throw new ListDEException("Ya existe la mascota");
                }
                temp = temp.getNextDE();
            }
            /// Parado en el último
            NodeDE newNode = new NodeDE(pet);
            temp.setNextDE(newNode);
            newNode.setPrevious(temp);

        } else {
            headDE = new NodeDE(pet);
        }
        size++;
    }

    public List<Pet> print() {
        pets.clear();
        if (headDE != null) {
            NodeDE temp = headDE;
            while (temp != null) {
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

        NodeDE nodeCP = new NodeDE(pet);
        if (headDE != null) {
            headDE.setPrevious(nodeCP);
            nodeCP.setNextDE(headDE);
        }
        headDE = nodeCP;
        size++;
    }

    public void addByPosition(Pet pet, int position) throws ListDEException {
        if (position < 0 || position > size) {
            throw new ListDEException("Posicion invalida:" + position);
        }
        NodeDE newNode = new NodeDE(pet);
        if (position == 0) {
            newNode.setNextDE(headDE);
            if (headDE != null) {
                headDE.setPrevious(newNode);
            }
            headDE = newNode;
        } else {
            NodeDE temp = headDE;
            for (int i = 1; i < position - 1; i++) {
                temp = temp.getNextDE();
            }
            newNode.setNextDE(temp.getNextDE());
            if (temp.getNextDE() != null) {
                temp.getNextDE().setPrevious(newNode);
            }
            temp.setNextDE(newNode);
            newNode.setPrevious(temp);
        }
    }


    public void deleteById(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("El identificador del dueño no puede ser nulo");
        }

        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getPetIdentification().equals(id)) {
                NodeDE prev = temp.getPrevious();
                NodeDE next = temp.getNextDE();
                if (prev == null) {
                    headDE = next;
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


// Aquí comienzan los 10 metodos, este es el 1.

    public void invertPets() throws ListDEException {
        if (this.headDE == null) {
            throw new ListDEException("No se puede invertir la lista porque esta vacia");
        }

        ListDE listcopy = new ListDE();
        NodeDE temp = this.headDE;
        while (temp != null) {
            listcopy.addToStartPet(temp.getData());
            temp = temp.getNextDE();
        }
        this.headDE = listcopy.getHeadDE();
    }


// Este es el 2.

    public void orderPetsMasculineToStart() throws ListDEException {
        if (headDE == null) {
            throw new ListDEException(
                    "La lista está vacía y no se pueden agregar elementos");
        }
        ListDE listCopy = new ListDE();
        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getPetGender() == 'M') {
                listCopy.addToStartPet(temp.getData());
            } else {
                listCopy.addPet(temp.getData());
            }
            temp = temp.getNextDE();
        }
        headDE = listCopy.getHeadDE();
    }

    // Este es el codigo 3.
    public void alternatePets() throws ListDEException {
        if (headDE == null) {
            throw new ListDEException("La lista está vacía y no se puede realizar el sorteo");
        }

        ListDE listM = new ListDE();
        ListDE listF = new ListDE();
        NodeDE temp = this.headDE;
        while (temp != null) {
            if (temp.getData().getPetGender() == 'M') {
                listM.addPet(temp.getData());
            }
            if (temp.getData().getPetGender() == 'F') {
                listF.addPet(temp.getData());
            }
            temp = temp.getNextDE();
        }

        if (listM.getSize() == 0 || listF.getSize() == 0) {
            throw new ListDEException(
                    "No hay suficientes mascotas de ambos géneros para realizar el sorteo");
        }

        ListDE ListCP = new ListDE();
        NodeDE Nodem = listM.getHeadDE();
        NodeDE Nodef = listF.getHeadDE();
        while (Nodem != null || Nodef != null) {
            if (Nodem != null) {
                ListCP.addPet(Nodem.getData());
                Nodem = Nodem.getNextDE();
            }
            if (Nodef != null) {
                ListCP.addPet(Nodef.getData());
                Nodef = Nodef.getNextDE();
            }
        }
        this.headDE = ListCP.getHeadDE();
    }

    // Este es el codigo 4.
    public void deletePetByAge(Byte age) throws ListDEException {
        if (age == null) {
            throw new ListDEException("La edad de la mascota no puede ser nula");
        }
        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getAgePet() == age) {
                NodeDE prev = temp.getPrevious();
                NodeDE next = temp.getNextDE();
                if (prev == null) {
                    headDE = next;
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
        if (headDE != null) {
            NodeDE temp = headDE;
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
        if (code == null || code.isEmpty()) {
            throw new ListDEException("El codigo de la ciudad no puede estar vacio");
        }
        int count = 0;
        if (this.headDE != null) {
            NodeDE temp = this.headDE;
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
        if (headDE != null) {
            NodeDE temp = headDE;
            int counter = 1;
            while (temp != null && !temp.getData().getPetIdentification().equals(id)) {
                temp = temp.getNextDE();
                counter++;
            }
            if (temp != null) {
                int newPosition = counter - position;
                if (newPosition < 0) {
                    throw new ListDEException(
                            "La posición especificada está fuera de los límites de la lista");
                }
                Pet listCopy = temp.getData();
                deleteById(temp.getData().getPetIdentification());
                if (newPosition > 0) {
                    addByPosition(listCopy, newPosition);
                } else {
                    addToStartPet(listCopy);
                }
            }
        }
    }

    // Este es el codigo 8.

    public void losePetPosition(String id, int position) throws ListDEException {

        if (position < 0) {
            throw new ListDEException("La posicion debe ser positiva");
        }
        NodeDE temp = headDE;
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
        NodeDE temp = headDE;
        int counter = 0;
        while (temp != null) {
            if (temp.getData().getAgePet() >= Start && temp.getData().getAgePet() <= finish) {
                counter++;
            }
            temp = temp.getNextDE();
        }
        return counter;
    }

    // Este es el codigo 10.

    public void sendPetToTheEndByLetter(char letter) throws ListDEException {
        if (this.headDE != null) {
            ListDE listCopy = new ListDE();
            NodeDE temp = this.headDE;
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
            this.headDE = listCopy.getHeadDE();
        } else {
            throw new ListDEException("La lista no puede estar vacia");
        }
    }

    // Hasta aquí van los metodos
    public boolean checkPet(Pet pet) throws ListDEException {
        if (pet == null) {
            throw new ListDEException("La lista de los perros no puede estar vacia");
        }

        if (this.headDE != null) {
            NodeDE temp = this.headDE;
            while (temp != null) {

                temp = temp.getNextDE();
            }
        }

        return true;
    }

    public int getCountPetsByDeptoCode(String code) {
        if (code == null || code.length() != 5) {
            throw new IllegalArgumentException("El código de departamento debe tener 5 caracteres.");
        }
        int count = 0;
        if (this.headDE != null) {
            NodeDE temp = this.headDE;
            while (temp != null) {
                String locCode = temp.getData().getLocation().getCode();
                if (locCode != null && locCode.length() >= 5 && locCode.substring(0, 5).equals(code)) {
                    count++;
                }
                temp = temp.getNextDE();
            }
        }
        return count;
    }


    /*
    Eliminar mascotas soltandose
si la cabeza es nula hago que no retorne nada

si la identificacion es igual a la que nos mandan la cabeza seria igual al siguiente

si la cabeza es diferente de nula
    la cabeza sería igual a nulo

si no es igual a la identificacion el temporal se pasa al siguiente

me paro en el nodo de la mascota que quiero eliminar y pregunto

si es igual a nulo
    creo 2 variables que llamo anterior que sea igual al previo

    y el nodo temporal seria igual a el siguiente


si el siguiente es diferente de nulo creo 2 variables que se llamarian anterior y despues
    que sean iguales al previo y al siguiente

si se cumple la condicion lo que haria seria que el nodo siguiente que se llama despues lo pondria en el anterior
y el nodo anterior lo pondria en despues
     */

    public void eliminateByIdentification(String identification){
            if (headDE != null) {
                if (headDE.getData().getPetIdentification().equals(identification)){
                    headDE = headDE.getNextDE();
                    if (headDE != null) {
                        headDE.setPrevious(null);
                        size--;
                        return;
                    }
                }
                NodeDE temp = headDE;
                while (temp != null) {
                    if (temp.getData().getPetIdentification().equals(identification)) {

                        temp.getPrevious().setNextDE(temp.getNextDE());

                        if (temp.getNextDE() != null){
                            temp.getNextDE().setPrevious(temp.getPrevious());
                        }
                        size --;
                    }
                    temp = temp.getNextDE();
                }
            }
        }
}
