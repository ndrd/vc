package computoVisual.filtros;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.LinkedList;


public class Imagenes extends Thread {
    private LinkedList<BufferedImage> imagenes;
    private LinkedList<BufferedImage> [] escalas_grises;

    public Imagenes() {
        imagenes = new LinkedList<BufferedImage>();
        escalas_grises = new LinkedList[10];
    }

    public void agregarImagen(BufferedImage img) {
        imagenes.add(img);
    }

    public void clasficarGrises() {
        for (BufferedImage img: imagenes) {
            int rango = clasificaImagenGris(img);
            if (escalas_grises[rango] == null)
                escalas_grises[rango] = new LinkedList<BufferedImage>();
            escalas_grises[rango].add(img);
        }
    /*
           int i = 1;
        for (LinkedList<BufferedImage> li : escalas_grises)
            if (li != null) {
                System.out.printf("%d imagenes en la categoria %d\n", li.size(), i++);
                for (BufferedImage img : li)
                    System.out.printf("%s\n", img.toString());

            }
            */
    }

    private int clasificaImagenGris(BufferedImage original) {
        int [] hist = new int[256];
        int ancho = original.getWidth();
        int alto = original.getHeight();

        for(int y = 0; y < alto; y++)
        {
            for(int x= 0; x < ancho;x++)
            {
                // obtenemos la informacion de cada color recorriendo los bits
                // dentro de la imagen
                int pixel = original.getRGB(x, y);
                int rojo = (pixel >> 16) & 0x000000FF;
                hist[rojo]++;
            }
        }
        return maximoHistograma(hist)/(260/10);
    }

    private int maximoHistograma(int[] hist) {
        int mayor = 0;
        int j = 0;
        for(int i = 0; i < hist.length; i++)
            if (mayor < hist[i]) {
                mayor = hist[i];
                j = i;
            }
        return  j;
    }

    public void magdifica_grises(BufferedImage magda_lado, String ubicacion,int mosaico,int blend) {
        WritableImage mitad = Filtros.magda_gris(magda_lado, escalas_grises,mosaico);
        BufferedImage nueva_magda = null;
        nueva_magda = SwingFXUtils.fromFXImage(mitad, nueva_magda);
        WritableImage blending = Filtros.blending(magda_lado, nueva_magda, blend);

        File file = new File(ubicacion);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(blending, null);
        try {
            ImageIO.write(
                    renderedImage,
                    "png",
                    file);
        } catch (Exception e) {}
    }

    private  int  clasificaColor(BufferedImage original) {
        int [] r = new int[256];
        int [] g = new int[256];
        int [] b = new int[256];

        int ancho = original.getWidth();
        int alto = original.getHeight();

        for(int y = 0; y < alto; y++)
        {
            for(int x= 0; x < ancho;x++)
            {
                // obtenemos la informacion de cada color recorriendo los bits
                // dentro de la imagen
                int pixel = original.getRGB(x, y);
                int rojo = (pixel >> 16) & 0x000000FF;
                int verde = (pixel >> 8) & 0x000000FF;
                int azul = (pixel >> 0) & 0x000000FF;

                r[rojo]++;
                g[verde]++;
                b[azul]++;
            }
        }
        return  clasificacion_color(r,g,b);

    }

    public void clasificarColores() {
        escalas_grises = new LinkedList[3];
        for (BufferedImage img: imagenes) {
            int rango = clasificaColor(img);
            if (escalas_grises[rango] == null)
                escalas_grises[rango] = new LinkedList<BufferedImage>();
            escalas_grises[rango].add(img);
        }

        int i = 1;
        for (LinkedList<BufferedImage> li : escalas_grises)
            if (li != null) {
                System.out.printf("%d imagenes en la categoria %d\n", li.size(), i++);


        }
    }

    private int clasificacion_color(int[] r, int[] g, int[] b) {
        int rojos = 0 ,verdes = 0,azules = 0;

        for (int i = 0; i < r.length; i++)
            if(r[i] > g[i] && r[i] > b[i])
                rojos++;
            else if (g[i] > b[i])
                verdes++;
            else
                azules++;

        if (rojos > azules && rojos > verdes)
            return 0;
        else if (verdes > azules)
            return 1;
        return 2;
    }

    public void magdifica_colores(BufferedImage magda_color, String ubicacion, int mosaico, int blend) {
        WritableImage mitad = Filtros.magda_color(magda_color, escalas_grises,mosaico);
        BufferedImage nueva_magda = null;
        nueva_magda = SwingFXUtils.fromFXImage(mitad, nueva_magda);
        WritableImage blending = Filtros.blending(magda_color, nueva_magda, blend);

        File file = new File(ubicacion);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(blending, null);
        try {
            ImageIO.write(
                    renderedImage,
                    "png",
                    file);
            System.err.println("Transformando :" + ubicacion);
        } catch (Exception e) {}
    }
}
