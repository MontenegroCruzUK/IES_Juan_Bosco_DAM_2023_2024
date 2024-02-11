package model;

import java.util.Scanner;

/**
 *
 * @author MONTENEGRO
 */
public class Main {

  static private Scanner teclado;

  public static void main(String[] args) {
    teclado = new Scanner(System.in);

    System.out.print("Indica el ID del cliente: ");
    int idCliente = teclado.nextInt();

    GeneradorDeInforme generador = new GeneradorDeInforme();
    generador.generarInforme(idCliente);
  }
}
