package es.rosamarfil.soap; // Define el paquete al que pertenece la interfaz SOAPI.

import java.util.List; // Importa la interfaz List.
import javax.jws.WebMethod; // Importa la anotación WebMethod para definir métodos de servicios web.
import javax.jws.WebService; // Importa la anotación WebService para definir servicios web.
import es.rosamarfil.model.User; // Importa la clase User del paquete es.rosamarfil.model.

@WebService // Marca esta interfaz como un servicio web.
public interface SOAPI {
    
    @WebMethod // Indica que este método es una operación del servicio web.
    public List<User> getUsers(); // Declaración del método para obtener una lista de usuarios.

    @WebMethod // Indica que este método es una operación del servicio web.
    public void addUser(User user); // Declaración del método para añadir un usuario.
}
