package server_http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase Main implementa un servidor web simple que escucha y procesa solicitudes HTTP GET de los clientes.
 */
public class Main {

  // Puerto en el que el servidor estará escuchando
  private static final int SERVER_PORT = 8066;

  /**
   * Punto de entrada del programa.
   *
   * @param args Argumentos de la línea de comandos
   */
  public static void main(String[] args) {
    startServer();
  }

  /**
   * Método para iniciar el servidor. Crea un ServerSocket que escucha en el puerto definido por SERVER_PORT. Entra en un bucle
   * infinito donde acepta conexiones de clientes, procesa sus solicitudes y luego cierra la conexión.
   */
  private static void startServer() {
    try {
      // Inicializa el socket del servidor
      ServerSocket socServidor = new ServerSocket(SERVER_PORT);
      imprimeDisponible();

      // Bucle principal del servidor
      while (true) {
        // Procesa la petición del cliente
        try ( // Acepta una nueva conexión de cliente
                            Socket socCliente = socServidor.accept()) {
          // Procesa la petición del cliente
          System.out.println("Atendiendo al cliente ");
          procesaPeticion(socCliente);
          // Cierra la conexión con el cliente
        }
        System.out.println("Cliente atendido");
      }

    } catch (IOException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Método para procesar las peticiones de los clientes. Lee la petición del cliente, identifica el navegador del cliente a
   * partir de la cabecera User-Agent y luego procesa la petición. Si la petición es un método GET, llama al método
   * processGetRequest(peticion, printWriter) para procesarla.
   *
   * @param socketCliente El socket del cliente.
   */
  private static void procesaPeticion(Socket socketCliente) {
    try {
      // Inicializa los flujos de entrada y salida
      InputStreamReader inSR = new InputStreamReader(socketCliente.getInputStream()); // Lee los bytes de entrada del socket y los convierte en caracteres
      BufferedReader bufLeer = new BufferedReader(inSR); // Almacena los caracteres en un buffer para una lectura eficiente
      PrintWriter printWriter = new PrintWriter(socketCliente.getOutputStream(), true); // Escribe los caracteres en el socket para enviarlos al cliente

      // Lee la petición del cliente
      String peticion = bufLeer.readLine(); // Lee una línea de texto de la petición del cliente
      if (peticion == null) { // Si la petición es null, significa que el cliente se desconectó antes de enviar una petición
        System.out.println("Cliente desconectado antes de enviar petición");
        return;
      }

      // Identifica el navegador del cliente
      String userAgent = ""; // Inicializa la variable que almacenará la cabecera User-Agent
      String line; // Variable para almacenar cada línea de la petición
      while (!(line = bufLeer.readLine()).equals("")) { // Lee cada línea de la petición hasta que encuentra una línea vacía
        if (line.toLowerCase().startsWith("user-agent")) { // Si la línea comienza con "User-Agent", la guarda en la variable userAgent
          userAgent = line;
        }
      }
      String browser = identifyBrowser(userAgent); // Identifica el navegador a partir de la cabecera User-Agent
      System.out.println("Cliente conectado desde el navegador: " + browser); // Imprime el navegador del cliente

      // Procesa la petición
      peticion = peticion.replaceAll(" ", ""); // Elimina todos los espacios de la petición
      if (peticion.startsWith("GET")) { // Si la petición es un método GET, la procesa
        processGetRequest(peticion, printWriter);
      }

    } catch (IOException e) { // Captura cualquier excepción de entrada/salida que pueda ocurrir al leer la petición o escribir la respuesta
      System.out.println("Se produjo un error al procesar la petición del cliente: " + e.getMessage()); // Imprime el mensaje de la excepción
    }
  }

  /**
   * Método para identificar el navegador del cliente a partir de la cabecera User-Agent. Devuelve una cadena que representa el
   * nombre del navegador.
   *
   * @param userAgent La cabecera User-Agent de la petición HTTP.
   * @return El nombre del navegador.
   */
  private static String identifyBrowser(String userAgent) {
    // Inicializa la variable que almacenará el nombre del navegador
    String browser = "desconocido";

    // Comprueba si la cadena User-Agent contiene la palabra "Chrome"
    if (userAgent.contains("Chrome")) {
      // Si es así, el navegador es Chrome
      browser = "Chrome";
    } // Comprueba si la cadena User-Agent contiene la palabra "Firefox"
    else if (userAgent.contains("Firefox")) {
      // Si es así, el navegador es Firefox
      browser = "Firefox";
    } // Comprueba si la cadena User-Agent contiene la palabra "MSIE" o "Trident"
    else if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
      // Si es así, el navegador es Internet Explorer
      browser = "Internet Explorer";
    } // Comprueba si la cadena User-Agent contiene la palabra "Safari" y no contiene la palabra "Chrome"
    else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {
      // Si es así, el navegador es Safari
      browser = "Safari";
    }

    // Devuelve el nombre del navegador
    return browser;
  }

  /**
   * Método para procesar las solicitudes GET. Dependiendo de la ruta de la petición, elige el contenido HTML apropiado para la
   * respuesta y luego llama al método sendResponse(printWriter, status, html, date) para enviar la respuesta al cliente.
   *
   * @param peticion La petición del cliente.
   * @param printWriter Un objeto PrintWriter para enviar la respuesta.
   */
  private static void processGetRequest(String peticion, PrintWriter printWriter) {
    // Inicializa la variable que almacenará el contenido HTML de la respuesta
    String html;

    // Extrae la ruta de la petición, eliminando el método "GET" y la versión de HTTP
    peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

    // Obtiene la fecha y hora actuales en el formato especificado
    String date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z").format(new Date());

    // Comprueba si la petición es para la página de inicio
    if (peticion.length() == 0 || peticion.equals("/")) {
      // Si es así, establece el contenido HTML de la respuesta a la página de inicio
      html = Paginas.HTML_INDEX;

      // Envía la respuesta con el estado "200 OK", el contenido HTML de la página de inicio y la fecha actual
      sendResponse(printWriter, Mensajes.lineaInicial_OK, html, date);
    } // Comprueba si la petición es para la página "/quijote"
    else if (peticion.equals("/quijote")) {
      // Si es así, establece el contenido HTML de la respuesta a la página "/quijote"
      html = Paginas.HTML_QUIJOTE;

      // Envía la respuesta con el estado "200 OK", el contenido HTML de la página "/quijote" y la fecha actual
      sendResponse(printWriter, Mensajes.lineaInicial_OK, html, date);
    } // Si la petición no es para ninguna de las páginas anteriores
    else {
      // Establece el contenido HTML de la respuesta a la página de error "404 Not Found"
      html = Paginas.HTML_NO_ENCONTRADO;

      // Envía la respuesta con el estado "404 Not Found", el contenido HTML de la página de error y la fecha actual
      sendResponse(printWriter, Mensajes.lineaInicial_NotFound, html, date);
    }
  }

  /**
   * Método para enviar una respuesta HTTP al cliente. Toma como entrada un objeto PrintWriter para enviar la respuesta, una
   * cadena status para la línea de estado de la respuesta HTTP, una cadena html para el cuerpo de la respuesta HTTP, y una cadena
   * date para la cabecera Date de la respuesta HTTP.
   *
   * @param printWriter Un objeto PrintWriter para enviar la respuesta.
   * @param status La línea de estado de la respuesta HTTP.
   * @param html El cuerpo de la respuesta HTTP.
   * @param date La cabecera Date de la respuesta HTTP.
   */
  private static void sendResponse(PrintWriter printWriter, String status, String html, String date) {
    // Envía la línea de estado de la respuesta HTTP
    printWriter.println(status);

    // Envía la primera cabecera de la respuesta HTTP
    printWriter.println(Paginas.PRIMERA_CABECERA);

    // Envía la cabecera 'Date' de la respuesta HTTP, que contiene la fecha y hora actuales
    printWriter.println("Date: " + date);

    // Envía la cabecera 'Content-Length' de la respuesta HTTP, que contiene la longitud del contenido HTML
    printWriter.println("Content-Length: " + html.length());

    // Envía una línea vacía para separar las cabeceras del cuerpo de la respuesta HTTP
    printWriter.println("\n");

    // Envía el cuerpo de la respuesta HTTP, que es el contenido HTML
    printWriter.println(html);
  }

  /**
   * Método para imprimir un mensaje que indica que el servidor está en ejecución y proporciona algunas URLs que el usuario puede
   * visitar para interactuar con el servidor.
   */
  private static void imprimeDisponible() {
    // Imprime un mensaje multilínea
    System.out.println("""
                       El Servidor WEB se est\u00e1 ejecutando y permanece a la escucha por el puerto 8066.
                       Escribe en la barra de direcciones de tu explorador preferido:
                       
                       http://localhost:8066
                       para solicitar la p\u00e1gina de bienvenida
                       
                       http://localhost:8066/quijote
                        para solicitar una p\u00e1gina del Quijote,
                       
                       http://localhost:8066/q
                        para simular un error""");
  }

}
