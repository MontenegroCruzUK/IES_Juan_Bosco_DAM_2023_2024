package server_http;

/**
 * La clase Mensajes contiene constantes para las líneas iniciales de las respuestas HTTP. Estas líneas iniciales son utilizadas
 * por el servidor para indicar el resultado de la solicitud del cliente.
 */
public class Mensajes {

  /**
   * Línea inicial para una respuesta HTTP exitosa. Esta línea se envía cuando el servidor ha procesado correctamente la solicitud
   * del cliente.
   */
  public static final String lineaInicial_OK = "HTTP/2.0 200 OK";

  /**
   * Línea inicial para una respuesta HTTP cuando el recurso solicitado no se encuentra. Esta línea se envía cuando el servidor no
   * puede encontrar el recurso solicitado por el cliente.
   */
  public static final String lineaInicial_NotFound = "HTTP/2.0 404 Not Found";

}
