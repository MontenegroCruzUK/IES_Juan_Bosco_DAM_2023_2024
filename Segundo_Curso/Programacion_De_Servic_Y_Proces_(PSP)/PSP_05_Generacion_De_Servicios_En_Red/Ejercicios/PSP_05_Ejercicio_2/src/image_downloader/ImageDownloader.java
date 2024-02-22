package image_downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Descargador de imágenes");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JTextField urlField = new JTextField(40);
      JButton downloadButton = new JButton("Descargar imágenes");
      downloadButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String webUrl = urlField.getText();
          String folderPath = "./imagenes_descargadas/";
          downloadImages(webUrl, folderPath);
          urlField.setText(""); // Limpia el campo de texto después de descargar las imágenes
        }
      });
      JPanel panel = new JPanel();
      panel.add(urlField);
      panel.add(downloadButton);
      frame.getContentPane().add(panel, BorderLayout.CENTER);
      frame.pack();
      frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
      frame.setVisible(true);
    });
  }

  public static void downloadImages(String webUrl, String folderPath) {
    try {
      Document doc = Jsoup.connect(webUrl).get();
      Elements imgElements = doc.select("img");
      File folder = new File(folderPath);
      if (!folder.exists()) {
        folder.mkdirs();
      }
      for (Element imgElement : imgElements) {
        String imgSrc = imgElement.absUrl("src");
        if (imgSrc.isEmpty()) {
          continue;
        }
        System.out.println("Descargando imagen: " + imgSrc);
        try {
          URL url = new URL(imgSrc);
          String fileName = url.getPath().substring(url.getPath().lastIndexOf('/') + 1);
          URLConnection connection = url.openConnection();
          try (InputStream is = connection.getInputStream(); OutputStream os = new FileOutputStream(folderPath + fileName)) {
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
              os.write(b, 0, length);
            }
          }
          System.out.println("Imagen descargada: " + fileName);
        } catch (IOException e) {
          System.out.println("Error al descargar la imagen: " + imgSrc);
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      System.out.println("Error al conectar a la página web: " + webUrl);
      e.printStackTrace();
    }
  }
}
