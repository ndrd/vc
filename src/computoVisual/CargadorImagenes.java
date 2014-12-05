/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package computoVisual;



import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

import computoVisual.filtros.Imagenes;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

/**
 *
 * @author ndrd.thn
 */
public class CargadorImagenes extends Application {
   
 private Stage stagePrincipal;
 private AnchorPane panelPrincipal;
 
 public Stage getStage()
 {
     return this.stagePrincipal;
 }
 
 @Override
 public void start(Stage primaryStage) {
     this.stagePrincipal = primaryStage;
     try {
            FXMLLoader cargador =  new FXMLLoader(CargadorImagenes.class.getResource("vistas/VisorImagenes.fxml"));
            panelPrincipal = (AnchorPane) cargador.load();
            Scene scena = new Scene(this.panelPrincipal);
            primaryStage.setScene(scena);
            primaryStage.show();
          
        } catch (IOException ioe) {
        }
    
    }

   public static void main(String[] args) {

       final long start = System.nanoTime();
       final Date d = new Date();

       new Thread() {

           File colores = null;
           File[] paths;
           Imagenes sc = new Imagenes();
           BufferedImage magda_color = null;

           public void run() {
               try {
                   colores = new File("/home/socrates/micro_magda/l1");
                   paths = colores.listFiles();
                   System.err.print("\n----:" + paths.length);
                   magda_color = ImageIO.read(new File("/home/socrates/micro_magda/mega_magda_der.jpg"));
                   for (File path : paths) {
                       BufferedImage in = ImageIO.read(path);
                       if (in != null)
                           sc.agregarImagen(in);
                   }

                   sc.clasificarColores();

                   colores = new File("/home/socrates/micro_magda/fc1000x1000");
                   final File[] pathx = colores.listFiles();
                   final double t1 = pathx.length/3;
                   System.err.print("\n----:" + paths.length);
                   /*
                   new Thread() {
                       @Override
                       public void run() {
                           try {
                               for (int i = 0; i < (int ) t1; i++) {
                                   System.out.print(i + "/" + pathx.length);
                                   sc.magdifica_colores(ImageIO.read(pathx[i]), "/home/socrates/micro_magda/l2/t1" + (i) + ".png", 27, 65);

                               }
                           } catch(Exception e) {}
                       }
                   }.start();
                    *//*
                   new Thread() {
                       @Override
                       public void run() {
                           try {
                               for (int i = (int) t1; i <(int)(t1+2); i++) {
                                   System.out.print(" " + i + "/" + pathx.length);
                                   sc.magdifica_colores(ImageIO.read(pathx[67]), "/home/socrates/micro_magda/l2/t2" + (67) + ".png", 35, 70);

                               }
                           } catch(Exception e) {}
                       }
                   }.start();*/

                   new Thread() {
                       @Override
                       public void run() {
                           try {
                               for (int i = (int)(t1*2); i < pathx.length; i++) {
                                   System.out.print(i + "/" + pathx.length);
                                   sc.magdifica_colores(ImageIO.read(pathx[104]), "/home/socrates/micro_magda/l2/t3" + (i) + ".png", 36, 78);

                               }
                           } catch(Exception e) {}
                       }
                   }.start();


               } catch (Exception e) {
                   e.printStackTrace();
               }
               long end = System.nanoTime();
               System.out.println("\nDelta T " + ((end-start)/10000000) + " ns");
           }
       }.start();

       /*
       new Thread() {

           File grises = null;
           File[] paths;
           Imagenes ss = new Imagenes();
           BufferedImage magda_gris = null;

            public void run() {
                try{
                    // create new file
                    grises = new File("/home/socrates/micro_magda/gs");

                    magda_gris = ImageIO.read(new File("/home/socrates/micro_magda/mega_magda_izq.jpg"));


                    // returns pathnames for files and directory
                    paths = grises.listFiles();

                    // for each pathname in pathname array
                    for(File path:paths)
                    {
                        BufferedImage in = ImageIO.read(path);
                        if(in != null)
                            ss.agregarImagen(in);

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

                ss.clasficarGrises();
                ss.magdifica_grises(magda_gris,"/home/socrates/micro_magda/gs"+d.toString()+".png",50,65);

                long end = System.nanoTime();
                System.out.println("\n[Threat 1] Delta T " + ((end-start)/10000000) + " ns");

            }

       }.start();

*/



    }
    
}
