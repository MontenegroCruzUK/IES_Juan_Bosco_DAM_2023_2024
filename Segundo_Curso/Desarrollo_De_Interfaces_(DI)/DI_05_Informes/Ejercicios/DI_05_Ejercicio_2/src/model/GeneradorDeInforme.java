package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.*;

/**
 *
 * @author MONTENEGRO
 */
public class GeneradorDeInforme {

  private Conexion conexion;
  private String sourceFileName = "src\\DI05_Tarea2_Informes\\facturasParametro.jasper";
  private Map<String, Object> params;
  private JasperPrint jasperPrint;

  public GeneradorDeInforme() {
    conexion = new Conexion();
    params = new HashMap<>();
  }

  public void generarInforme(int idCliente) {
    Connection connection = conexion.getConnection();

    if (connection != null) {
      params.put("ID_Cliente", idCliente);

      try {
        jasperPrint = JasperFillManager.fillReport(sourceFileName, params, connection);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src\\DI05_Tarea2_Informes\\Facturas" + Integer.toString(idCliente) + ".pdf");
      } catch (JRException ex) {
        ex.printStackTrace();
      }
    }
  }
}
