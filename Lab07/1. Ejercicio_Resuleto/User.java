package es.rosamarfil.model; 

import java.io.Serializable; // Importa la interfaz Serializable para permitir la serialización.
import java.util.ArrayList; // Importa la clase ArrayList.
import java.util.Arrays; // Importa la clase Arrays.
import java.util.List; // Importa la interfaz List.
import javax.xml.bind.annotation.XmlAttribute; // Importa la anotación XmlAttribute
import javax.xml.bind.annotation.XmlValue; // Importa la anotación XmlValue

public class User implements Serializable { // Declara la clase User que implementa Serializable.
    private static final long serialVersionUID = 1L; // Identificador de versión para la serialización.

    // Lista estática de usuarios predefinidos.
    public static List<User> users = new ArrayList<>(Arrays.asList(
        new User("Rosa", "Marfil"),
        new User("Pepito", "Grillo"),
        new User("Manuela", "Río")
    ));

    public String name; // Campo público que almacena el nombre del usuario.
    public String username; // Campo público que almacena el nombre de usuario.

    // Constructor por defecto.
    public User() {
        super(); // Llama al constructor de la superclase (Object).
    }

    // Constructor parametrizado que inicializa name y username.
    public User(String name, String username) {
        super(); // Llama al constructor de la superclase (Object).
        this.name = name; // Inicializa el campo name.
        this.username = username; // Inicializa el campo username.
    }

    // Método setter para el campo name.
    public void setName(String name) {
        this.name = name; // Asigna el valor pasado al campo name.
    }

    // Método setter para el campo username.
    public void setUsername(String username) {
        this.username = username; // Asigna el valor pasado al campo username.
    }
}

