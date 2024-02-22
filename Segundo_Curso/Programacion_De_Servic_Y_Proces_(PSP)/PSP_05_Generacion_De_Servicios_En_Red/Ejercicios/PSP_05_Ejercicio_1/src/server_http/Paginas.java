package server_http;

/**
 * La clase Paginas contiene las constantes y métodos para generar las páginas HTML que el servidor HTTP devuelve como respuesta a
 * las solicitudes de los clientes.
 */
public class Paginas {

  /**
   * Cabecera de contenido para las respuestas HTTP, indicando que el contenido es HTML y usa la codificación de caracteres UTF-8.
   */
  public static final String PRIMERA_CABECERA = "Content-Type:text/html;charset=UTF-8";
  /**
   * Estilos CSS para las páginas HTML.
   */
  private static final String CSS_ESTILOS = "<style>"
                      + "body {"
                      + "font-family: Arial, sans-serif; "
                      + "margin:0; padding:0; "
                      + "background-color: #f0f0f0; "
                      + "display: flex; justify-content: center; "
                      + "align-items: center; height: 100vh;"
                      + "}"
                      + "div {"
                      + "text-align: center; "
                      + "border: 1px solid #ddd; "
                      + "border-radius: 5px; "
                      + "padding: 20px; "
                      + "width: 300px; "
                      + "box-shadow: 0 2px 5px rgba(0,0,0,0.3);"
                      + "}"
                      + "h1 {"
                      + "color: #333;"
                      + "}"
                      + "p {"
                      + "font-size: 1em; "
                      + "color: #666;"
                      + "}"
                      + "</style>";
  /**
   * HTML para la página de inicio.
   */
  public static final String HTML_INDEX = generarHTML("index", "¡Enhorabuena!", "Tu servidor HTTP mínimo funciona correctamente");
  /**
   * HTML para la página del Quijote.
   */
  public static final String HTML_QUIJOTE = generarHTML("quijote", "Así comienza el Quijote",
                      "En un lugar de la Mancha, de cuyo nombre no quiero "
                      + "acordarme, no ha mucho tiempo que vivía un hidalgo de los "
                      + "de lanza en astillero, adarga antigua, rocín flaco y galgo "
                      + "corredor. Una olla de algo más vaca que carnero, salpicón "
                      + "las más noches, duelos y quebrantos (huevos con tocino) los "
                      + "sábados, lentejas los viernes, algún palomino de añadidura "
                      + "los domingos, consumían las tres partes de su hacienda. El "
                      + "resto della concluían sayo de velarte (traje de paño fino), "
                      + "calzas de velludo (terciopelo) para las fiestas con sus "
                      + "pantuflos de lo mismo, y los días de entresemana se honraba "
                      + "con su vellorí (pardo de paño) de lo más fino. Tenía en su "
                      + "casa una ama que pasaba de los cuarenta, y una sobrina que "
                      + "no llegaba a los veinte, y un mozo de campo y plaza, que "
                      + "así ensillaba el rocín como tomaba la podadera...");
  /**
   * HTML para la página de error "404 Not Found".
   */
  public static final String HTML_NO_ENCONTRADO = generarHTML("noEncontrado", "¡ERROR! Página no encontrada", "La página que solicitaste no existe en nuestro servidor");

  /**
   * Método para generar una cadena de HTML completa dado un título, un encabezado y algún contenido.
   *
   * @param titulo El título de la página.
   * @param encabezado El encabezado de la página.
   * @param contenido El contenido de la página.
   * @return Una cadena que representa una página HTML completa.
   */
  private static String generarHTML(String titulo, String encabezado, String contenido) {
    return "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>" + titulo + "</title>"
                        + CSS_ESTILOS
                        + "</head>"
                        + "<body>"
                        + "<div>"
                        + "<h1>" + encabezado + "</h1>"
                        + "<p>" + contenido + "</p>"
                        + "</div>"
                        + "</body>"
                        + "</html>";
  }
}
