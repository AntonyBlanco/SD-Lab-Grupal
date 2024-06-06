package es.rosamarfil.soap; // Define el paquete al que pertenece la clase SOAPImpl.

import java.util.List; // Importa la interfaz List.
import javax.jws.WebService; // Importa la anotación WebService.
import es.rosamarfil.model.User; // Importa la clase User del paquete es.rosamarfil.model.

@WebService(endpointInterface = "es.rosamarfil.soap.SOAPI") // Especifica que esta clase implementa la interfaz SOAPI como un servicio web.
public class SOAPImpl implements SOAPI { // Define la clase SOAPImpl que implementa la interfaz SOAPI.
    
    @Override // Anotación que indica que este método sobrescribe el método de la interfaz.
    public List<User> getUsers() {
        return User.users; // Devuelve la lista estática de usuarios de la clase User.
    }

    @Override // Anotación que indica que este método sobrescribe el método de la interfaz.
    public void addUser(User user) {
        User.users.add(user); // Añade un usuario a la lista estática de usuarios de la clase User.
    }
}

