package fr.univ_lille.iut;

import javax.ws.rs.Path;
import javax.ws.rs.POST;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Map;
import java.util.HashMap;

/**
* Ressource User (accessible avec le chemin "/tasks")
*/
@Path("tasks")
public class TacheResource {
  // Pour l'instant, on se contentera d'une variable statique
  // pour conserver l'état
  private static Map<String, Tache> tasks = new HashMap<String, Tache>();

  // L'annotation @Context permet de récupérer des informations sur
  // le contexte d'exécution de la ressource.
  // Ici, on récupère les informations concernant l'URI de la requête HTTP,
  // ce qui nous permettra de manipuler les URI de manière générique.
  @Context
  public UriInfo uriInfo;

  /**
  * Une ressource doit avoir un contructeur (éventuellement sans arguments)
  */
  public TacheResource() {
  }

  /**
  * Méthode de création d'un utilisateur qui prend en charge les requêtes
  * HTTP POST
  * La méthode renvoie l'URI de la nouvelle instance en cas de succès
  *
  * @param  user Instance d'utilisateur à créer
  * @return Response le corps de la réponse est vide, le code de retour HTTP
  * est fixé à 201 si la création est faite.
  * L'en-tête contient un champs Location avec l'URI de la nouvelle ressource
  */
  @POST
  public Response createTask(Tache task) {
    // Si l'utilisateur existe déjà, renvoyer 409
    if ( tasks.containsKey(task.getCreator()) ) {
      return Response.status(Response.Status.CONFLICT).build();
    }
    else {
      tasks.put(task.getCreator(), task);

      // On renvoie 201 et l'instance de la ressource dans le Header
      // HTTP 'Location'
      URI instanceURI = uriInfo.getAbsolutePathBuilder().path(task.getCreator()).build();
      return Response.created(instanceURI).build();
    }
  }
}
