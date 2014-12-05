package computoVisual.filtros;

/**
 * Dentro de esta clase se implementan los filtros solicitados dentro de la 
 * práctica 0 de Vision por Computadora.
 * Se crea la clase de manera estatica para que no sea necessario hacer una 
 * instanciacion dentro del controlador y usarla de manera
 */

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.LinkedList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swt.SWTFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 *
 * @author ndrd.thn
 */
public class Filtros 
{

    /**
     * Devuelve una imagen con los canales seleccionados, se toma Red, Green, Blue
     * como argumentos booleanos para indicar la presencia del color dentro
     * del canal, de ests manera no solo se obtiene el canal especifico, si no
     * que podemos obtener alguna de las diferentes combinaciones entre canales
     * @param original - la imagen que queremos modificar
     * @param canalRojo - si esta presente o no el canal dentro de la imagen
     * @param canalVerde - si esta presente o no el canal dentro de la imagen
     * @param canalAzul - si esta presente o no el canal dentro de la imagen
     * @return wImagen, una nueva imagen dentro de la cual ya se modifico los
     *                  valores dentro de cada uno de los pixeles de acuerdo
     *                  a los argumentos de los canales que se dessean mostrar.
     */
    public static WritableImage mostrarCanales(BufferedImage original, boolean canalRojo,
            boolean canalVerde, boolean canalAzul)
    {
      int ancho = original.getWidth();
      int alto = original.getHeight();
      WritableImage wImagen = new WritableImage(ancho, alto);
      PixelWriter wPixel =  wImagen.getPixelWriter();
      
      for(int y = 0; y < alto; y++)
      {
          for(int x= 0; x < ancho;x++)
          {
              // obtenemos la informacion de cada color recorriendo los bits
              // dentro de la imagen
             int pixel = original.getRGB(x, y);
             int alfa = (pixel >> 24) & 0x000000FF;
             int rojo = (pixel >> 16) & 0x000000FF;
             int verde = (pixel >> 8 ) & 0x000000FF;
             int azul = (pixel) & 0x000000FF;
                          
             pixel = (pixel & ~(0x000000FF << 16)) | (((canalRojo) ? rojo : 0)<< 16);
             pixel = (pixel & ~(0x000000FF << 8)) | (((canalVerde) ? verde : 0) << 8);
             pixel = (pixel & ~(0x000000FF )) | (((canalAzul) ? azul: 0));  
                 
             wPixel.setArgb(x, y, pixel);
          }
      }
      return wImagen;  
    }
    
    
    /**
     * Toma una imagen y calcula el promedio para cada pixel, despues iguala
     * el valor de cada una de las entradas de rgb para     
     * @param original
     * @return 
     */
    public static WritableImage escalaGrises(BufferedImage original)
    {
      int ancho = original.getWidth();
      int alto = original.getHeight();
      WritableImage wImagen = new WritableImage(ancho, alto);
      PixelWriter wPixel =  wImagen.getPixelWriter();
      for(int y = 0; y < alto; y++)
      {
          for(int x= 0; x < ancho;x++)
          {
             int pixel = original.getRGB(x, y);
             int alfa = (pixel >> 24) & 0x000000FF;
             int rojo = (pixel >> 16) & 0x000000FF;
             int verde = (pixel >> 8 ) & 0x000000FF;
             int azul = (pixel) & 0x000000FF;
             int prom = (rojo + verde + azul)/3;
                          
             pixel = (pixel & ~(0x000000FF << 16)) | (prom << 16);
             pixel = (pixel & ~(0x000000FF << 8)) | (prom << 8);
             pixel = (pixel & ~(0x000000FF )) | (prom);
             wPixel.setArgb(x, y, pixel);
          }
      }
      return wImagen;
    }
    
    
    /**
     * Para cada pixel de la imagen, obtenemos el valor de cada una de sus entradas
     * las promediamos y despues asignamos a cada canal el valor de la media
     * @param original
     * @return 
     */
    public static WritableImage inversaEscalaGrises(BufferedImage original)
    {
      int ancho = original.getWidth();
      int alto = original.getHeight();
      WritableImage wImagen = new WritableImage(ancho, alto);
      PixelWriter wPixel =  wImagen.getPixelWriter();
      for(int y = 0; y < alto; y++)
      {
          for(int x= 0; x < ancho;x++)
          {
             int pixel = original.getRGB(x, y);
             int alfa = (pixel >> 24) & 0x000000FF;
             int rojo = (pixel >> 16) & 0x000000FF;
             int verde = (pixel >> 8 ) & 0x000000FF;
             int azul = (pixel) & 0x000000FF;
             int prom = (rojo + verde + azul)/3;
                          
             pixel = (pixel & ~(0x000000FF << 16)) | ((255 - prom) << 16);
             pixel = (pixel & ~(0x000000FF << 8)) | ((255 - prom) << 8);
             pixel = (pixel & ~(0x000000FF )) | (255 - prom);
             wPixel.setArgb(x, y, pixel);
          }
      }
      return wImagen;
    }
    
    
    /**
     * Al igual que en la escala de grises obtenemos la media de cada pixel sumando
     * las entradas de cada canal, despues, si el valor es mayor a la media del canal
     * lo convierte a negro, en caso contrario se vuelve blanco.
     * @param original
     * @return 
     */
    public static WritableImage blancoNegro(BufferedImage original)
    {
      int ancho = original.getWidth();
      int alto = original.getHeight();
      WritableImage wImagen = new WritableImage(ancho, alto);
      PixelWriter wPixel =  wImagen.getPixelWriter();
      for(int y = 0; y < alto; y++)
      {
          for(int x= 0; x < ancho;x++)
          {
             int pixel = original.getRGB(x, y);
             int alfa = (pixel >> 24) & 0x000000FF;
             int rojo = (pixel >> 16) & 0x000000FF;
             int verde = (pixel >> 8 ) & 0x000000FF;
             int azul = (pixel) & 0x000000FF;
             int prom = (rojo + verde + azul)/3;
             prom = (prom > 127) ? 255 : 0;
                          
             pixel = (pixel & ~(0x000000FF << 16)) | (prom << 16);
             pixel = (pixel & ~(0x000000FF << 8)) | (prom << 8);
             pixel = (pixel & ~(0x000000FF )) | (prom);
             wPixel.setArgb(x, y, pixel);
          }
      }
      return wImagen;
    }
    
    /**
     * Calcula el complemento a 255 de cada canal para cada pixel dentro de la imagen
     * @param original
     * @return 
     */    
    public static WritableImage invertirColores(BufferedImage original)
    {
      int ancho = original.getWidth();
      int alto = original.getHeight();
      WritableImage wImagen = new WritableImage(ancho, alto);
      PixelWriter wPixel =  wImagen.getPixelWriter();
      for(int y = 0; y < alto; y++)
      {
          for(int x= 0; x < ancho;x++)
          {
             int pixel = original.getRGB(x, y);
             int alfa = (pixel >> 24) & 0x000000FF;
             int rojo = (pixel >> 16) & 0x000000FF;
             int verde = (pixel >> 8 ) & 0x000000FF;
             int azul = (pixel) & 0x000000FF;
                                     
             pixel = (pixel & ~(0x000000FF << 16)) | ((255 - rojo) << 16);
             pixel = (pixel & ~(0x000000FF << 8)) | ((255- verde) << 8);
             pixel = (pixel & ~(0x000000FF )) | (255 - azul);
             wPixel.setArgb(x, y, pixel);
          }
      }
      return wImagen;
    }
    
    
    /**
     * Sobrepone dos imagenes, calculando el promedio dentro de los pixeles de
     * cada una de ellas, existe un factor de proporcion, donde la imagen
     * original se sobrepone a la segunda, asi, el porcentaje que tenga de visibilidad
     * la imagen original, sera su presencia dentro del promedio (0-1) y la imagen
     * secundaria tendra el complemento de ese valor, de esta forma se puede 
     * ver más o menos una encima de la otra.
     * @param imagenUno
     * @param imagenDos
     * @param porcentaje
     * @return 
     */
    public static WritableImage blending(BufferedImage imagenUno, BufferedImage imagenDos, int porcentaje)
    {
        double proporcion = ((porcentaje < 0 || porcentaje > 100) ? 50 : (porcentaje)) / 100.0;
        System.out.print(proporcion);
        // obtenemos la interseccion de las imagenes, tomando el borde superior (0,0)
        int ancho = (imagenUno.getWidth() < imagenDos.getWidth()) ? imagenUno.getWidth() : imagenDos.getWidth();
        int alto = (imagenUno.getHeight() < imagenDos.getHeight()) ? imagenUno.getHeight() : imagenDos.getHeight();
        WritableImage wImagen = new WritableImage(ancho, alto);
        PixelWriter wPixel =  wImagen.getPixelWriter();
        for(int y = 0; y < alto; y++)
        {
            for(int x= 0, pixel; x < ancho;x++)
            {
               int pxUno = imagenUno.getRGB(x, y);
               int pxDos = imagenDos.getRGB(x, y);
               pixel = pxDos;
               
               int rojo = (int) (((pxUno >> 16) & 0x000000FF)*(proporcion) + ((pxDos >> 16) & 0x000000FF)*(1 - proporcion));
               int verde = (int) (((pxUno >> 8 ) & 0x000000FF)*(proporcion) + ((pxDos >> 8) & 0x000000FF)*(1 - proporcion));
               int azul = (int) ((pxUno & 0x000000FF)*proporcion + (pxDos & 0x000000FF)*(1 - proporcion));
               
               pixel = (pixel & ~(0x000000FF << 16)) | (rojo << 16);
               pixel = (pixel & ~(0x000000FF << 8)) | (verde << 8);
               pixel = (pixel & ~(0x000000FF )) | (azul);
               wPixel.setArgb(x, y, pixel);
            }
        }
        return wImagen;
          
    }
    
    /**
     * Aumenta el brillo de la imagen mediante cierto intervalo recibido el cual
     * aumenta el valor dentro de cada entrada de color para dar una sensacion 
     * de mayor luz  a la imagen
     * @param original
     * @param incremento
     * @return wImagen, una imagen para representar dentro de javafx con el efecto
     *                  deseado
     */
    public static WritableImage aumentarBrillo(BufferedImage original, int incremento)
    {
        //modulamos el incremento
        incremento = (incremento > 0 && incremento < 100) ? incremento : (incremento %100);
        int ancho = original.getWidth();
        int alto = original.getHeight();
        WritableImage wImagen = new WritableImage(ancho, alto);
        PixelWriter wPixel =  wImagen.getPixelWriter();
        for(int y = 0; y < alto;y++)
        {
            for(int x = 0; x < ancho; x++)
            {
             int pixel = original.getRGB(x, y);
             int alfa = (pixel >> 24) & 0x000000FF;
             int rojo = (((pixel >> 16) & 0x000000FF) + incremento) % 254;
             int verde = (((pixel >> 8 ) & 0x000000FF) + incremento) % 254;
             int azul = (((pixel) & 0x000000FF) + incremento) % 255;
                                     
             pixel = (pixel & ~(0x000000FF << 16)) | (rojo << 16);
             pixel = (pixel & ~(0x000000FF << 8)) | (verde << 8);
             pixel = (pixel & ~(0x000000FF )) | (azul);
             wPixel.setArgb(x, y, pixel);
            }
        }
        return wImagen;
    }
    
    
    /**
     * Recibe una matriz de convolucion de mxn elementos, despues recorre cada pixel
     * de la imagen busca los vecinos a la redonda donde el pixel actual esta "en
     * el centro" , despues multiplica todos los valores de cada pixel vecino
     * con su correspondiente entrada dentro de la matriz
     * @param original
     * @param matriz
     * @return 
     */
    public static WritableImage matrizConvolucion(BufferedImage original, int[][] matriz, int divisor, int bias)
    {
      divisor = (divisor == 0) ? 1: divisor;
      int ancho = original.getWidth();
      int alto = original.getHeight();
      WritableImage wImagen = new WritableImage(ancho, alto);
      PixelWriter wPixel =  wImagen.getPixelWriter();
      // tenemos una matriz cuadrada, entonces calculamos la distancia entre el
      // elemento central y sus vecinos de los bordes
      int mediaX = (int)((matriz.length - 1)/2);
      int mediaY = (int)((matriz[0].length - 1)/2);

      for(int y = 0; y < alto; y++)
      {
          for(int x= 0; x < ancho;x++)
          {
             int pixel = original.getRGB(x, y);
             int total = 0;
             double rojo = 0;
             double verde = 0;
             double azul = 0;
             
             for(int i = -mediaX, x1 = 0; i < (matriz.length - mediaX); i++, x1++)
             {
                 for(int j = -mediaY, y1 = 0; j < (matriz[0].length - mediaY); j++, y1++)
                 {
                     if(pixelValido(ancho, alto, x+i, y+j))
                         pixel = original.getRGB(x+i,y+j);
                     else if((x+i < 0 || y+j < 0 )&& ( x+i < ancho && y+j < alto))
                         pixel = original.getRGB(Math.abs(x+i),Math.abs(y+j));

                     rojo += ((pixel >> 16) & 0x000000FF) * matriz[x1][y1];
                     verde += ((pixel >> 8) & 0x000000FF) * matriz[x1][y1];
                     azul += ((pixel) & 0x000000FF) * matriz[x1][y1];

                 }
             }
             
             rojo /= divisor;
             verde /= divisor;
             azul /= divisor;

              pixel = (pixel & ~(0x000000FF << 16)) | ((int)(rojo) << 16);
              pixel = (pixel & ~(0x000000FF << 8)) | ((int) (verde) << 8);
              pixel = (pixel & ~(0x000000FF )) | ((int)(azul));

             wPixel.setArgb(x, y, pixel);
          }
      }
      return wImagen;  
    }
    
    
    /**
     * Toma una imagen, y el ancho y alto de una matriz (ventanita) que ira barriendo
     * a imagen, despues dentro de cada pixel, buscara a sus vecinos, calculara 
     * el promedio para cada una de las entradas del pixel y asignara esos valores
     * a cada pixel del vecindario.
     * @param original
     * @param mAncho
     * @param mAlto
     * @return 
     */
    public static WritableImage convolucionPromedio(BufferedImage original, int mAncho, int mAlto)
    {
        int alto = original.getHeight();
        int ancho = original.getWidth();
        WritableImage wImagen = new WritableImage(ancho,alto);
        PixelWriter pW  = wImagen.getPixelWriter();
        
        int mediaX = (int)((mAncho-1) / 2);
        int mediaY = (int)((mAlto-1) / 2);
                
        for(int y = 0; y < alto; y += mAlto)
        {
            for(int x = 0; x < ancho; x += mAncho)
            {
                int pixel = original.getRGB(x, y);
                
                int nPixelesValidos = 0;
                int mediaRojo = 0;
                int mediaVerde = 0;
                int mediaAzul = 0;
                int pxVecino = 0;
                
                /*
                dentro de estos ciclos, busca los vecinos, si son validos, entonces
                calculara la media de sus canales, en caso contrario, sigue con 
                los vecinos que si lo sean
                */
                for(int i = -mediaX; i < (mAncho - mediaX); i++)
                {
                    for(int j = -mediaY; j < (mAlto - mediaY); j++)
                    {
                        //debug(i + "," + j);
                        if(pixelValido(ancho, alto, x+i, y+j))
                        {
                            nPixelesValidos++;
                            pxVecino = original.getRGB(x+i, y+j);
                            mediaRojo += (pxVecino >> 16) & 0x000000FF;
                            mediaVerde += (pxVecino >> 8) & 0x000000FF;
                            mediaAzul += (pxVecino) & 0x000000FF;
                        }
                    }
                }
                mediaRojo = (int)(mediaRojo/nPixelesValidos);
                mediaVerde = (int)(mediaVerde/nPixelesValidos);
                mediaAzul = (int)(mediaAzul/nPixelesValidos);
                
               //debug("r: " + mediaRojo + " g: " + mediaVerde + " b:" + mediaAzul);
               pixel = (pixel & ~(0x000000FF << 16)) | (mediaRojo << 16);
               pixel = (pixel & ~(0x000000FF << 8)) | (mediaVerde << 8);
               pixel = (pixel & ~(0x000000FF )) | (mediaAzul);
                              
               /* Ya que tenemos el promedio por canal, enonces ponemos el valor 
               correspondiente a cada canal, en cada pixel*/
               for(int i = -mediaX; i < ancho-mediaX; i++)
                   for(int j = -mediaY; j < alto-mediaY;j++ )
                       if(pixelValido(ancho,alto,x+i,y+j))
                           pW.setArgb(x+i,y+j, pixel);
                       
                
            }
        }
        
        return wImagen;
    }

    public static WritableImage magda_gris(BufferedImage original, LinkedList<BufferedImage> [] escalas, int cuadro)
    {

        int alto = original.getHeight();
        int ancho = original.getWidth();
        WritableImage wImagen = new WritableImage(ancho,alto);
        PixelWriter pW  = wImagen.getPixelWriter();

        int mediaX = 0;//(int)((cuadro-1) / 2);
        int mediaY = 0;//(int)((cuadro-1) / 2);

        for(int y = 0; y < alto; y += cuadro)
        {
            for(int x = 0; x < ancho; x += cuadro)
            {
                int pixel = original.getRGB(x, y);

                int nPixelesValidos = 0;
                int mediaRojo = 0;
                int mediaVerde = 0;
                int mediaAzul = 0;
                int pxVecino = 0;

                /*
                dentro de estos ciclos, busca los vecinos, si son validos, entonces
                calculara la media de sus canales, en caso contrario, sigue con
                los vecinos que si lo sean
                */
                for(int i = -mediaX; i < (cuadro - mediaX); i++)
                {
                    for(int j = -mediaY; j < (cuadro - mediaY); j++)
                    {
                        //debug(i + "," + j);
                        if(pixelValido(ancho, alto, x+i, y+j))
                        {
                            nPixelesValidos++;
                            pxVecino = original.getRGB(x+i, y+j);
                            mediaRojo += (pxVecino >> 16) & 0x000000FF;

                        }
                    }
                }
                mediaRojo = (int)(mediaRojo/nPixelesValidos);
                mediaRojo /= 26;
                BufferedImage hoyo = escalas[mediaRojo].get((int)(Math.random() * escalas[mediaRojo].size()));

               /* Ya que tenemos el promedio por canal, enonces ponemos el valor
               correspondiente a cada canal, en cada pixel*/

                int deltaHx = hoyo.getWidth() / cuadro;
                int deltaHY = hoyo.getHeight() /cuadro;
                for(int i = -mediaX ,hx = 0; i < ancho-mediaX; i++ ,hx+=deltaHx)
                    for(int j = -mediaY,hy = 0; j < alto-mediaY;j++, hy+=deltaHY )
                        if(pixelValido(ancho,alto,x+i,y+j)) {
                            int nuevo = 0;
                            if(pixelValido(hoyo.getWidth(),hoyo.getHeight(), hx,hy))
                                nuevo = hoyo.getRGB(hx,hy);
                            pW.setArgb(x+i,y+j, nuevo);
                        }



            }
        }

        return wImagen;
    }

    public static WritableImage magda_color(BufferedImage original, LinkedList<BufferedImage> [] escalas, int cuadro)
    {

        int alto = original.getHeight();
        int ancho = original.getWidth();
        WritableImage wImagen = new WritableImage(ancho,alto);
        PixelWriter pW  = wImagen.getPixelWriter();

        int mediaX = 0;//(int)((cuadro-1) / 2);
        int mediaY = 0;//(int)((cuadro-1) / 2);

        for(int y = 0; y < alto; y += cuadro)
            for (int x = 0; x < ancho; x += cuadro) {
                int pixel = original.getRGB(x, y);

                int nPixelesValidos = 0;
                int mediaRojo = 0;
                int mediaVerde = 0;
                int mediaAzul = 0;
                int pxVecino = 0;

                /*
                dentro de estos ciclos, busca los vecinos, si son validos, entonces
                calculara la media de sus canales, en caso contrario, sigue con
                los vecinos que si lo sean
                */
                for (int i = -mediaX; i < (cuadro - mediaX); i++) {
                    for (int j = -mediaY; j < (cuadro - mediaY); j++) {
                        //debug(i + "," + j);
                        if (pixelValido(ancho, alto, x + i, y + j)) {
                            nPixelesValidos++;
                            pxVecino = original.getRGB(x + i, y + j);
                            mediaRojo += (pxVecino >> 16) & 0x000000FF;
                            mediaVerde += (pxVecino >> 8) & 0x000000FF;
                            mediaAzul += (pxVecino) & 0x000000FF;

                        }
                    }
                }
                mediaRojo = (int) (mediaRojo / nPixelesValidos);
                mediaAzul = (int) (mediaAzul / nPixelesValidos);
                mediaVerde = (int) (mediaVerde / nPixelesValidos);

                if(mediaRojo == mediaVerde && mediaRojo  == mediaAzul)
                    mediaRojo = (mediaRojo >= 0 && mediaRojo <= 85) ? 0 : (mediaAzul <= 160) ? 2 : 1;
                else
                    mediaRojo = ((mediaRojo > mediaAzul && mediaRojo > mediaVerde)? 0 : (mediaAzul < mediaVerde) ? 1 : 2);

                BufferedImage hoyo = escalas[mediaRojo].get((int) (Math.random() * escalas[mediaRojo].size()));

               /* Ya que tenemos el promedio por canal, enonces ponemos el valor
               correspondiente a cada canal, en cada pixel*/

                int deltaHx = hoyo.getWidth() / cuadro;
                int deltaHY = hoyo.getHeight() / cuadro;
                for (int i = -mediaX, hx = 0; i < ancho - mediaX; i++, hx += deltaHx)
                    for (int j = -mediaY, hy = 0; j < alto - mediaY; j++, hy += deltaHY)
                        if (pixelValido(ancho, alto, x + i, y + j)) {
                            int nuevo = 465123;
                            if (pixelValido(hoyo.getWidth(), hoyo.getHeight(), hx, hy)) {
                                nuevo = hoyo.getRGB(hx, hy);
                             }
                            pW.setArgb(x + i, y + j, nuevo);
                        }


            }

        return wImagen;
    }



    /**
     * Toma una imagen, y el ancho y alto de una matriz (ventanita) que ira barriendo
     * a imagen, despues dentro de cada pixel, buscara a sus vecinos, calculara 
     * el promedio para cada una de las entradas del pixel, despues vera cual es 
     * el color mas presente dentro del pixel y pondra el vecindario de ese
     * color.
     * @param original
     * @param mAncho
     * @param mAlto
     * @return 
     */
    public static WritableImage extraUno(BufferedImage original, int mAncho, int mAlto)
    {
        int alto = original.getHeight();
        int ancho = original.getWidth();
        WritableImage wImagen = new WritableImage(ancho,alto);
        PixelWriter pW  = wImagen.getPixelWriter();
        
        int mediaX = (int)((mAncho-1) / 2);
        int mediaY = (int)((mAlto-1) / 2);
                
        for(int y = 0; y < alto; y += mAlto)
        {
            for(int x = 0; x < ancho; x += mAncho)
            {
                int pixel = original.getRGB(x, y);
                
                int nPixelesValidos = 0;
                int mediaRojo = 0;
                int mediaVerde = 0;
                int mediaAzul = 0;
                int pxVecino = 0;
                int fin = 0;
                
                /*
                dentro de estos ciclos, busca los vecinos, si son validos, entonces
                calculara la media de sus canales, en caso contrario, sigue con 
                los vecinos que si lo sean
                */
                for(int i = -mediaX; i < (mAncho - mediaX); i++)
                {
                    for(int j = -mediaY; j < (mAlto - mediaY); j++)
                    {
                        //debug(i + "," + j);
                        if(pixelValido(ancho, alto, x+i, y+j))
                        {
                            nPixelesValidos++;
                            pxVecino = original.getRGB(x+i, y+j);
                            mediaRojo += (pxVecino >> 16) & 0x000000FF;
                            mediaVerde += (pxVecino >> 8) & 0x000000FF;
                            mediaAzul += (pxVecino) & 0x000000FF;
                        }
                    }
                }
                mediaRojo = (int)(mediaRojo/nPixelesValidos);
                mediaVerde = (int)(mediaVerde/nPixelesValidos);
                mediaAzul = (int)(mediaAzul/nPixelesValidos);
                
               if(mediaRojo > mediaVerde && mediaRojo > mediaAzul)
               {
                   mediaVerde = mediaAzul = 0;
               }
               else if(mediaVerde > mediaAzul)
               {
                   mediaRojo = mediaAzul = 0;
               }
               else
               {
                   mediaRojo = mediaVerde = 0;
               }
               
               
                
               //debug("r: " + mediaRojo + " g: " + mediaVerde + " b:" + mediaAzul);
               pixel = (pixel & ~(0x000000FF << 16)) | (mediaRojo << 16);
               pixel = (pixel & ~(0x000000FF << 8)) | (mediaVerde << 8);
               pixel = (pixel & ~(0x000000FF )) | (mediaAzul);
                              
               /* Ya que tenemos el promedio por canal, enonces ponemos el valor 
               correspondiente a cada canal, en cada pixel*/
               for(int i = -mediaX; i < ancho-mediaX; i++)
                   for(int j = -mediaY; j < alto-mediaY;j++ )
                       if(pixelValido(ancho,alto,x+i,y+j))
                           pW.setArgb(x+i,y+j, pixel);
                       
                
            }
        }
        
        return wImagen;
    }
    /**
     * Si el pixel tiene un valor positivo y se encuentra dentro del rango de la 
     * imagen, devulve true
     * @param ancho de la imagen, los limites del pixel
     * @param alto de la imagen o area
     * @param x
     * @param y
     * @return 
     */
     private static boolean pixelValido(int ancho, int alto, int x, int y)
     { 
         return (x < ancho && y < alto && x >= 0 && y >= 0);
     }

    private static void debug(Object o) {
        System.out.println(o);
    }


    
       /**
     * Toma una imagen, y el ancho y alto de una matriz (ventanita) que ira barriendo
     * a imagen, despues dentro de cada pixel, buscara a sus vecinos, calculara 
     * el promedio para cada una de las entradas del pixel y asignara esos valores
     * a cada pixel del vecindario.
     * @param original
     * @param mAncho
     * @param mAlto
     * @return 
     */
    public static WritableImage convolucionPromedio2(BufferedImage original, int mAncho, int mAlto)
    {
        int alto = original.getHeight();
        int ancho = original.getWidth();
        BufferedImage salida = new BufferedImage(ancho,alto,original.getType());
       
        WritableRaster raster = original.getRaster();
        WritableRaster out = salida.getRaster();
       
        
        int mediaX = (int)((mAncho-1) / 2);
        int mediaY = (int)((mAlto-1) / 2);
                
        double [] canales = null;
        for(int y = 0; y < alto; y += mAlto)
        {
            for(int x = 0; x < ancho; x += mAncho)
            {
                canales = raster.getPixel(x, y, canales);
                
                int nPixelesValidos = 0;
                double [] pxVecino = new double[3];
                
                /*
                dentro de estos ciclos, busca los vecinos, si son validos, entonces
                calculara la media de sus canales, en caso contrario, sigue con 
                los vecinos que si lo sean
                */
                for(int i = -mediaX; i < (mAncho - mediaX); i++)
                {
                    for(int j = -mediaY; j < (mAlto - mediaY); j++)
                    {
                        //debug(i + "," + j);
                        if(pixelValido(ancho, alto, x+i, y+j))
                        {
                            nPixelesValidos++;
                            raster.getPixel(x,y,pxVecino);
                            canales[0] += pxVecino[0];
                            canales[1] += pxVecino[1];
                            canales[2] += pxVecino[2];
                        }
                    }
                }
                               
               canales[0] = canales[0] / nPixelesValidos;
               canales[1] = canales[1] / nPixelesValidos;
               canales[2] = canales[2] / nPixelesValidos;
                              
               /* Ya que tenemos el promedio por canal, enonces ponemos el valor 
               correspondiente a cada canal, en cada pixel_*/
               for(int i = -mediaX; i < ancho-mediaX; i++)
                   for(int j = -mediaY; j < alto-mediaY;j++ )
                       if(pixelValido(ancho,alto,x+i,y+j))
                           out.setPixel(x+i,y+j, canales);
                       
                
            }
        }
         
        WritableImage wg = new WritableImage(ancho,alto);
        return SwingFXUtils.toFXImage(salida, wg);
    }

    public static WritableImage gaussianBlur(BufferedImage imagen, int sigma)
    {
        int alto = imagen.getHeight();
        int ancho = imagen.getWidth();

        WritableImage wImagen = new WritableImage(ancho,alto);
        PixelWriter pW  = wImagen.getPixelWriter();

        int dSigma = 2 * sigma;
        double [] kernel = kernel(sigma);

        for(int y = 0; y < alto; y++)
        {
            for(int x = 0; x < ancho; x++)
            {
                int pixel = imagen.getRGB(x,y);
                double rojos = 0, verdes = 0, azules = 0;

                for(int i = -sigma, n = 0; i < ((dSigma + 1) - sigma); i++, n++)
                {
                    for(int j = -sigma, m = 0; j < ((dSigma + 1) - sigma); j++, m++)
                    {
                        int px = 0;

                        if(pixelValido(ancho, alto, x+i, y+j))
                            px = imagen.getRGB(x+i,y+j);
                        else if((x+i < 0 || y+j < 0 )&& ( x+i < ancho && y+j < alto))
                            px = imagen.getRGB(Math.abs(x+i),Math.abs(y+j));

                        rojos += ((px >> 16) & 0x000000FF) * kernel[n] * kernel[m];
                        verdes += ((px >> 8) & 0x000000FF) * kernel[n] * kernel[m];
                        azules += ((px) & 0x000000FF) * kernel[n] * kernel[m];

                    }
                }

                pixel = (pixel & ~(0x000000FF << 16)) | ((int) (rojos) << 16);
                pixel = (pixel & ~(0x000000FF << 8)) | ((int) (verdes) << 8);
                pixel = (pixel & ~(0x000000FF )) | ((int)(azules));

                pW.setArgb(x,y,pixel);
            }
        }
        return wImagen;
    }

    public static double [] kernel(int sigma)
    {
        double [] kernel =  new double[2 * sigma + 1];
        double dSigma = 2 * sigma * sigma;
        double K = (1 / (dSigma * Math.PI));
        double e = Math.E;
        double sumaPesos = 0;

        for(int x = -sigma, i = 0; x < ((2 * sigma + 1) - sigma); x++, i++)
        {
            kernel[i] = K * Math.pow(e,-(x*x/dSigma));
            sumaPesos += kernel[i];
        }

        for(int i = 0; i < kernel.length; i++)
            kernel[i] = kernel[i] / sumaPesos;

        return kernel;
    }

    public static WritableImage highPassFilter(BufferedImage original, int sigma)
    {
        int alto = original.getHeight();
        int ancho = original.getWidth();

        WritableImage pasoBajo = Filtros.gaussianBlur(original,sigma);
        BufferedImage pasoBajoB = SwingFXUtils.fromFXImage(pasoBajo,null);
        WritableImage nueva = new WritableImage(ancho, alto);
        PixelWriter pW =  nueva.getPixelWriter();

        for(int y = 0; y < alto; y++)
        {
            for(int x = 0; x < ancho; x++)
            {
                int pixel = original.getRGB(x,y);
                int pxOriginal = original.getRGB(x,y);
                int pxBajo = pasoBajoB.getRGB(x,y);

                int rojos = (((pxOriginal >> 16) & 0x000000FF) - ((pxBajo >> 16) & 0x000000FF)) ;
                int verdes = (((pxOriginal >> 8) & 0x000000FF) - ((pxBajo >> 8) & 0x000000FF));
                int azules = (((pxOriginal) & 0x000000FF) - ((pxBajo) & 0x000000FF));

                rojos = (rojos + 255) / 2;
                verdes = (verdes + 255) / 2;
                azules = (azules + 255) / 2;

                pixel = (pixel & ~(0x000000FF << 16)) | ((int) (rojos) << 16);
                pixel = (pixel & ~(0x000000FF << 8)) | ((int) (verdes) << 8);
                pixel = (pixel & ~(0x000000FF )) | ((int)(azules));

                pW.setArgb(x,y,pixel);
             }
        }
        return nueva;
    }

    /**
     * Metodo que genera una imagen hibrida, recibe dos imagenes como parametro, aplicara el filtro bajo a la
     * primera, a la segunda el de paso alto, después las mezclara.
     * @param imagenUno la imagen a la que se le aplicara el filtro paso bajo
     * @param imagenDos la imagen a la que se le aplicara el filtro de paso alto
     * @return
     */
    public static  WritableImage imagenHibirda (BufferedImage imagenUno, BufferedImage imagenDos, int sigmaUno, int sigmaDos) {
        WritableImage pasoBajo = Filtros.gaussianBlur(imagenUno, sigmaUno);
        WritableImage pasoAlto = Filtros.highPassFilter(imagenDos, sigmaDos);
        PixelReader pR1 = pasoBajo.getPixelReader();
        PixelReader pR2 = pasoAlto.getPixelReader();

        int ancho = (imagenUno.getWidth() < imagenDos.getWidth()) ? imagenUno.getWidth() : imagenDos.getWidth();
        int alto = (imagenUno.getHeight() < imagenDos.getHeight()) ? imagenUno.getHeight() : imagenDos.getHeight();

        WritableImage nueva = new WritableImage(ancho, alto);
        PixelWriter pW = nueva.getPixelWriter();

        for (int y = 0; y < alto; y++)
        {
            for (int x = 0; x < ancho; x++)
            {
                int pxUno = pR1.getArgb(x,y);
                int pxDos = pR2.getArgb(x,y);

                int rojos = (((pxUno >> 16) & 0x000000FF) + ((pxDos >> 16) & 0x000000FF)) / 2 ;
                int verdes = (((pxUno >> 8) & 0x000000FF) + ((pxDos >> 8) & 0x000000FF)) / 2;
                int azules = (((pxUno) & 0x000000FF) + ((pxDos) & 0x000000FF)) / 2;

                int pixel = pxDos;
                pixel = (pixel & ~(0x000000FF << 16)) | ((int) (rojos) << 16);
                pixel = (pixel & ~(0x000000FF << 8)) | ((int) (verdes) << 8);
                pixel = (pixel & ~(0x000000FF )) | ((int)(azules));

                pW.setArgb(x,y,pixel);
            }
        }

        return nueva;
    }

    public  static WritableImage escalar(BufferedImage original, int r) {
        int ancho = original.getWidth();
        int alto = original.getHeight();
        return null;
    }

    /**
     * perdido en el morfing, el wrapping modifica el pixel de la imagen origen a su equivalente en la imagen
     * destino, pero en la nueva imagen, cual muestreo????
     * @param inicio
     * @param termino
     * @param grid1
     * @param grid2
     * @param dt
     * @return
     */
    public static WritableImage morphing(BufferedImage inicio, BufferedImage termino, double[][][][] grid1, double[][][][] grid2, int dt) {
        int ancho = (inicio.getWidth() < termino.getWidth()) ? inicio.getWidth() : termino.getWidth();
        int alto = (inicio.getHeight() < termino.getHeight()) ? inicio.getHeight() : termino.getHeight();
        WritableImage nueva = new WritableImage(ancho, alto);
        PixelWriter pw = nueva.getPixelWriter();

        for (int x = 0; x < ancho; x += grid1.length) {
            for(int y = 0; y < alto; y += grid1[0].length)
                for (int i = 0; i < grid1.length; i++) {
                    for (int j = 0; j < grid1[0].length;j++) {
                        int pixel =0;

                        int ax =(int) grid1[i][j][0][0];
                        int ay =(int) grid1[i][j][0][1];

                        int bx =(int) grid1[i+1][j][0][0];
                        int by =(int) grid1[i+1][j][0][1];

                        int cx =(int) grid1[i][j+1][0][0];
                        int cy =(int) grid1[i][j+1][0][1];

                        int dx =(int) grid1[i+1][j+1][0][0];
                        int dy =(int) grid1[i+1][j+1][0][1];


                    }
                }
        }


        return nueva;
    }

    private  static  double distancia(int x1,int y1,int x2,int y2) {
        return Math.sqrt((x2-x1)*(x2*1) + (y2-y1)*(y2-y1));
    }
}