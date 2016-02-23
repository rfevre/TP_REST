package fr.univ_lille.iut;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

public class TacheTest extends JerseyTest {

  /**
  * Il est obligatoire de redéfinir cette méthode qui permet de configurer le contexte de Jersey
  */
  @Override
  protected Application configure() {
    return new ResourceConfig(TacheRessource.class);
  }

  /**
  * Test de création d'un utilisateur (retour HTTP et envoi de l'URI de la nouvelle instance)
  */
  @Test
  public void testCreateTask() {
    Tache task = new Tache(1, "FEVRER", "ceci est une tache");
    // Conversion de l'instance de User au format JSON pour l'envoi
    Entity<Tache> taskEntity = Entity.entity(task, MediaType.APPLICATION_JSON);

    // Envoi de la requête HTTP POST pour la création de l'utilisateur
    Response response = target("/tasks").request().post(taskEntity);

    // Vérification du code de retour HTTP
    assertEquals(201, response.getStatus());

    // Vérification que la création renvoie bien l'URI de la nouvelle instance dans le header HTTP 'Location'
    // ici : http://localhost:8080/users/jsteed
    URI uriAttendue = target("/tasks").path(task.getCreator()).getUri();
    assertTrue(uriAttendue.equals(response.getLocation()));
  }
}
