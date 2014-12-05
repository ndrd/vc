package computoVisual.controladores;


import computoVisual.CargadorImagenes;
import computoVisual.filtros.Filtros;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author ndrd.thn
 */
public class VisorImagenesController implements Initializable {

    /* Tomamos un limite de 30 puntos, donde 12 seran auxliadres de los bordes para generar la malla*/
    private final int MAXIMO_PUNTOS = 15;
    private Desktop escritorio = Desktop.getDesktop();
    private CargadorImagenes cargador;
    private Canvas lienzoOriginal;
    private Canvas lienzoNuevo;
    private BufferedImage imagenNormal;
    private BufferedImage imagenNormalDos;
    private WritableImage imagenNormal2;
    private GraphicsContext contexto;
    private WritableImage tmp;

    @FXML
    private ScrollPane imagenOriginal;
    @FXML
    private ScrollPane imagenRender;
    @FXML
    private Label tituloSlider;
    @FXML
    private Slider masterSlider;
    @FXML
    private Slider s1;
    @FXML
    private Slider s2;
    @FXML
    private CheckBox rojo;
    @FXML
    private CheckBox verde;
    @FXML
    private CheckBox azul;
    @FXML
    private ProgressBar barra;
    @FXML
    private Button ok;
    @FXML
    private TextField ancho;
    @FXML
    private TextField alto;
    @FXML
    private Pane menus;
    private Group gp1 = new Group();
    private Group gp2 = new Group();


    @FXML
    private void abrirImagen() {
        BufferedImage imagen = getImagen();
        if (imagen != null) {
            this.imagenNormal = imagen;
            this.imagenNormal2 = SwingFXUtils.toFXImage(imagenNormal, this.imagenNormal2);
            this.mostrarImagenOriginal(this.imagenNormal2);
            this.mostrarNuevaImagen(this.imagenNormal2);
        }
    }

    private BufferedImage getImagen() {
        BufferedImage imagen = null;
        final FileChooser selectorArchivos = new FileChooser();
        selectorArchivos.setTitle("Selecciona la imagen");
        selectorArchivos.setInitialDirectory(new File(System.getProperty("user.home")));
        File archivo = selectorArchivos.showOpenDialog(cargador.getStage());
        try {
            imagen = ImageIO.read(archivo);
        } catch (Exception ioe) {
        }
        return imagen;

    }

    private void mostrarImagenOriginal(WritableImage imagen) {
        gp1.getChildren().clear();
        gp2.getChildren().clear();
        lienzoOriginal = new Canvas(imagen.getWidth(), imagen.getHeight());
        lienzoNuevo = new Canvas(imagen.getWidth(), imagen.getHeight());
        GraphicsContext contextoImagen = this.lienzoOriginal.getGraphicsContext2D();
        contextoImagen.drawImage(imagen, 0, 0);
        gp1.getChildren().addAll(lienzoOriginal);
        gp2.getChildren().addAll(lienzoNuevo);
        this.imagenOriginal.setContent(gp1);
        this.imagenRender.setContent(gp2);
    }

    private void mostrarNuevaImagen(WritableImage imagen) {
        gp2.getChildren().clear();
        if (imagen == null)
            return;

        this.lienzoNuevo = new Canvas(imagen.getWidth(), imagen.getHeight());
        GraphicsContext contextoImagen = this.lienzoNuevo.getGraphicsContext2D();
        contextoImagen.drawImage(imagen, 0, 0);
        gp2.getChildren().addAll(lienzoNuevo);
        this.imagenRender.setContent(gp2);

    }

    private WritableImage canalRojo(BufferedImage original) {
        return Filtros.mostrarCanales(original, true, false, false);
    }

    private WritableImage canalVerde(BufferedImage original) {
        return Filtros.mostrarCanales(original, false, true, false);
    }

    private WritableImage canalAzul(BufferedImage original) {
        return Filtros.mostrarCanales(original, false, false, true);
    }

    private WritableImage escalaGrises(BufferedImage original) {
        return Filtros.escalaGrises(original);
    }

    private WritableImage blancoNegro(BufferedImage original) {
        return Filtros.blancoNegro(original);
    }

    private WritableImage inverso(BufferedImage original) {
        return Filtros.invertirColores(original);
    }

    private WritableImage inversoGris(BufferedImage original) {
        return Filtros.inversaEscalaGrises(original);
    }

    private WritableImage desenfonquePromedio(BufferedImage original, int ancho, int alto) {
        return Filtros.convolucionPromedio2(original, ancho, alto);
    }

    private WritableImage extraUno(BufferedImage original, int ancho, int alto) {
        return Filtros.convolucionPromedio(original, ancho, alto);
    }

    private WritableImage gaussianBlur(BufferedImage original, int sigma)
    {
        return  Filtros.gaussianBlur(original, sigma);
    }

    private WritableImage highPass(BufferedImage original, int sigmaGaussian)
    {
        return Filtros.highPassFilter(original, sigmaGaussian);
    }

    private WritableImage matrizConvolucion(BufferedImage original, int [][] kernel, int bias)
    {
        return Filtros.matrizConvolucion(original,kernel,16,0);
    }

    private  WritableImage imagenHibirda(BufferedImage img1,BufferedImage img2, int s1, int s2)
    {
        return  Filtros.imagenHibirda(img1, img2, s1, s2);
    }

    private void debug(Object o) {
        System.out.println(o);
    }

    @FXML
    private void mostrarCanalRojo() {
        if (this.imagenNormal == null)
            return;
        this.tmp = canalRojo(imagenNormal);
        this.mostrarNuevaImagen(tmp);

    }

    @FXML
    private void mostrarCanalVerde() {
        if (this.imagenNormal == null)
            return;
        this.tmp = canalVerde(imagenNormal);
        this.mostrarNuevaImagen(tmp);
    }

    @FXML
    private void mostrarCanalAzul() {
        if (this.imagenNormal == null)
            return;
        this.tmp = canalAzul(imagenNormal);
        this.mostrarNuevaImagen(tmp);
    }

    @FXML
    private void mostrarEscalaGris() {
        if (this.imagenNormal == null)
            return;
        this.tmp = escalaGrises(imagenNormal);
        this.mostrarNuevaImagen(tmp);
    }

    @FXML
    private void mostrarBlancoNegro() {
        if (this.imagenNormal == null)
            return;
        this.tmp = blancoNegro(imagenNormal);
        this.mostrarNuevaImagen(tmp);
    }

    @FXML
    private void mostrarInverso() {
        if (this.imagenNormal == null)
            return;
        this.tmp = inverso(imagenNormal);
        this.mostrarNuevaImagen(tmp);
    }

    @FXML
    private void inversoGris() {
        if (this.imagenNormal == null)
            return;
        this.tmp = inversoGris(imagenNormal);
        this.mostrarNuevaImagen(tmp);
    }

    @FXML
    private void desenfoque() {
        if (this.imagenNormal == null)
            return;
        barra.setVisible(true);
        debug("Comenzo con get raster:");
        long start = Calendar.getInstance().getTimeInMillis();
        int anchoM = parseInt(this.ancho.getText(),30);
        int altoM = parseInt(this.alto.getText(), 30);
        this.tmp = this.desenfonquePromedio(imagenNormal, anchoM, altoM);
        this.mostrarNuevaImagen(tmp);
        long end = Calendar.getInstance().getTimeInMillis();
        debug((end - start));
        barra.setVisible(false);
    }

    @FXML
    private void gaussian()
    {
        barra.setVisible(true);
        if(this.imagenNormal == null)
            return;
        long start = Calendar.getInstance().getTimeInMillis();
        this.tmp = this.gaussianBlur(imagenNormal, 7);
        this.mostrarNuevaImagen(tmp);
        long end = Calendar.getInstance().getTimeInMillis();
        debug((end - start));
        barra.setVisible(false);
    }

    @FXML
    private void highPass()
    {
        barra.setVisible(true);
        if(this.imagenNormal == null)
            return;
        long start = Calendar.getInstance().getTimeInMillis();
        this.tmp = this.highPass(imagenNormal, 7);
        this.mostrarNuevaImagen(tmp);
        long end = Calendar.getInstance().getTimeInMillis();
        debug((end - start));
        barra.setVisible(false);
    }

    @FXML
    private void imagenHibrida()
    {

        if(imagenNormalDos == null)
            imagenNormalDos = getImagen();
        barra.setVisible(true);
        long start = Calendar.getInstance().getTimeInMillis();
        this.tmp = imagenHibirda(imagenNormal,imagenNormalDos,
                s1.valueProperty().intValue(), s2.valueProperty().intValue());
        this.mostrarNuevaImagen(tmp);
        long end = Calendar.getInstance().getTimeInMillis();
        debug((end - start));
        imagenNormalDos = null;
        barra.setVisible(false);

    }

    @FXML
    private void convolucion()
    {
        if(this.imagenNormal == null)
            return;
        long start = Calendar.getInstance().getTimeInMillis();
        int[][]kernel = {{1,2,1},{2,8,2},{1,2,1}};
        this.tmp = this.matrizConvolucion(imagenNormal,kernel,0);
        this.mostrarNuevaImagen(tmp);
        long end = Calendar.getInstance().getTimeInMillis();
        debug((end - start));
    }

    @FXML
    private void extraUno() {
        if (this.imagenNormal == null)
            return;
        debug("Comenzo convolucion bitwise.");
        barra.setVisible(true);
        long start = Calendar.getInstance().getTimeInMillis();
        int anchoM = parseInt(this.ancho.getText(),30);
        int altoM = parseInt(this.alto.getText(),30);
        this.tmp = this.extraUno(imagenNormal, anchoM, altoM);
        this.mostrarNuevaImagen(tmp);
        long end = Calendar.getInstance().getTimeInMillis();
        debug((end - start));
        barra.setVisible(false);
    }

    private int parseInt(String s, int def) {
       try {
            def = Integer.parseInt(s);
        } catch (Exception e) {
        }
        return def;
    }

    @FXML
    private void cambioCanal() {
        if (this.imagenNormal == null)
            return;
        this.tmp = Filtros.mostrarCanales(imagenNormal, rojo.isSelected(), verde.isSelected(), azul.isSelected());
        this.mostrarNuevaImagen(tmp);
    }
      
    @FXML
    private void blending() {

        this.tituloSlider.setText("Propocion");
        this.masterSlider.setMin(0);
        this.masterSlider.setMax(100);
        this.ok.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                int proporcion = masterSlider.valueProperty().intValue();
                mostrarNuevaImagen(Filtros.blending(imagenNormal, imagenNormalDos, proporcion));
                event.consume();
            }

        });

        if (this.imagenNormalDos == null)
            this.imagenNormalDos = this.getImagen();
        int proporcion = this.masterSlider.valueProperty().intValue();
        this.mostrarNuevaImagen(Filtros.blending(imagenNormal, imagenNormalDos, proporcion));
        // evitamos problemas con los otros fltros, la mandamos a null
        imagenNormalDos = null;
    }

    private double [][] coordenadasUno = new double[MAXIMO_PUNTOS][2];
    private double [][] coordenadasDos = new double[MAXIMO_PUNTOS][2];
     /* de 0 - 11, total de puntos que se tienen en el borde y sirven como auxilio */
    private int numeroPuntosUno = 0;
    private int numeroPuntosDos = 0;

    private boolean end = false;

    @FXML
    private void morphing()
    {
        final int RADIO = 2;
        WritableImage im2;
        imagenNormalDos = null;

        if(imagenNormalDos == null) {
            imagenNormalDos = this.getImagen();
            im2 = SwingFXUtils.toFXImage(imagenNormalDos,null);
            this.mostrarNuevaImagen(im2);
        }

        //drawGrid(gp1, imagenNormal, 7);
        //drawGrid(gp2, imagenNormalDos, 7);

        //menus.setVisible(false);
        lienzoOriginal.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if(numeroPuntosUno == numeroPuntosDos && numeroPuntosUno > 0) {
                   if(end)
                      return;
                    else
                       end = true;
                }

                int px = imagenNormal.getRGB((int) me.getX(), (int) me.getY());
                debug("(" + me.getX() + ", " + me.getY() + ") :" + "(" + ((px >> 16) & 0x000000FF) + "," + ((px >> 16) & 0x000000FF) + "," + ((px >> 16) & 0x000000FF) + ")");
                Circle point = new Circle(me.getX(), me.getY(), RADIO);

                point.setFill(javafx.scene.paint.Color.CYAN);

                if (numeroPuntosUno < coordenadasUno.length) {
                    gp1.getChildren().addAll(point);
                    coordenadasUno[numeroPuntosUno][0] = me.getX();
                    coordenadasUno[numeroPuntosUno++][1] = me.getY();
                }

                me.consume();
            }
        });

        lienzoNuevo.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if(numeroPuntosUno == numeroPuntosDos) {
                    if(end)
                        return;
                    else {
                       end = true;
                    }
                }

                debug("(" + me.getX() + ", " + me.getY() + ")");
                Circle point = new Circle(me.getX(), me.getY(), RADIO);
                point.setFill(javafx.scene.paint.Color.color(0.98,0.55,0.15));
                int px = imagenNormal.getRGB((int) me.getX(),(int) me.getY());
                //debug("(" + me.getX() + ", " + me.getY() + ") :" + "(" + ((px >> 16) & 0x000000FF) + "," + ((px >> 8) & 0x000000FF) + "," + ((px) & 0x000000FF) +")");


                if (numeroPuntosDos < coordenadasDos.length) {

                    gp2.getChildren().addAll(point);
                    coordenadasDos[numeroPuntosDos][0] =  me.getX();
                    coordenadasDos[numeroPuntosDos++][1] = me.getY();
                    //mostrarCoordenadas(coordenadasDos);
                }

                me.consume();
            }
        });

        double [][][][] kk1 = juntaPuntos(imagenNormal, coordenadasUno, gp1);
        double [][][][] kk2 = juntaPuntos(imagenNormalDos,coordenadasDos, gp2);
        tmp = Filtros.morphing(imagenNormal, imagenNormalDos, kk1, kk2, 50);
        mostrarNuevaImagen(tmp);
     }

    private double[][][][] juntaPuntos(BufferedImage img,double[][] coordenadasUno, Group gp) {

        double [][][] grid = coordenadasAuxiliares(img,7);
        for (int i = 0; i < coordenadasUno.length; i++) {
            int x = (int) (coordenadasUno[i][0]);
            int y = (int) (coordenadasUno[i][1]);
            /* Lo llevamos con el punto mas cercano */
            grid[x%7][y%7][0] = x;
            grid[x%7][y%7][1] = y;
           } 
        for (int x = 0; x < grid.length; x++)
            for (int y = 0; y < grid[0].length; y++) {
                //gp.getChildren().addAll(new Line(grid[x][y][0], grid[x][y][1], grid[x][(y+1)%7][0], grid[x][(y+1)%7][1]));
                gp.getChildren().addAll(new Circle(grid[x][y][0],grid[x][y][1], 1, javafx.scene.paint.Color.DODGERBLUE));
            }

        double [][][][] kingKong = new double[7][7][4][2];

        for (int i = 0; i < kingKong.length - 1; i++)
            for(int j = 0; j < kingKong[0].length - 1; j++) {
                kingKong[i][j][0][0] = grid[i][j][0];
                kingKong[i][j][0][1] = grid[i][j][1];

                kingKong[i][j+1][1][0] = grid[i][j][0];
                kingKong[i][j+1][1][1] = grid[i][j][1];

                kingKong[i+1][j][2][0] = grid[i][j][0];
                kingKong[i+1][j][2][1] = grid[i][j][1];

                kingKong[i+1][j+1][3][0] = grid[i][j][0];
                kingKong[i+1][j+1][3][1] = grid[i][j][1];
            }
        return kingKong;
    }


    private void drawGrid(Group gp, BufferedImage imagenNormal, int tam) {
        double [][][] grid = coordenadasAuxiliares(imagenNormal, tam);
         for (int x = 0; x < grid.length; x++)
            for (int y = 0; y < grid[0].length; y++) {
                gp.getChildren().addAll(new Circle(grid[x][y][0],grid[x][y][1], 1, javafx.scene.paint.Color.DODGERBLUE));
            }
    }

    /* Calcula los primeros puntos auxiliares para poder meterlos dentro de la imagen*/
    private double[][][] coordenadasAuxiliares(BufferedImage image, int tam) {
        int ancho = image.getWidth();
        int alto = image.getHeight();
          /* Se calculan los espacios entre los componentes*/
        int incX = ancho / tam;
        int incY = alto / tam;
        /* almacen las coordenadas que genramos a partir del tamaño de la rejilla que queremos crear */
        double [][][] resultado = new double[tam][tam][2];

        for (int x = 0; x < tam; x++) {
            for (int y = 0; y < tam; y++) {
                resultado[x][y][0] = x * incX;
                resultado[x][y][1] = y * incY;
            }
        }
        return resultado;
     }


    private void mostrarCoordenadas(double[][] d) {
        for (int i = 0; i < d.length; i++)
             System.out.println("(" + d[i][0] + ", " + d[i][1] + ")");
    }
    @FXML
    private void aumentarBrillo() {
        this.tituloSlider.setText("Brillo");
        this.masterSlider.setMin(0);
        this.masterSlider.setMax(100);
        this.ok.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                int proporcion = masterSlider.valueProperty().intValue();
                mostrarNuevaImagen(Filtros.aumentarBrillo(imagenNormal, proporcion));
                event.consume();
            }

        });

        int proporcion = this.masterSlider.valueProperty().intValue();
        this.mostrarNuevaImagen(Filtros.aumentarBrillo(imagenNormal, proporcion));
    }

    @FXML
    private void guardarImagen() {
        if (tmp == null)
            return;
        else
            imagenAarchivo();

    }


     
    private void imagenAarchivo() {
        final FileChooser selectorArchivos = new FileChooser();
        selectorArchivos.setTitle("Guardar la imagen");
        selectorArchivos.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagen", "*.png");
        selectorArchivos.getExtensionFilters().add(extFilter);
        File archivo = selectorArchivos.showSaveDialog(cargador.getStage());

        if (archivo != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(tmp, null), "png", archivo);
            } catch (IOException ioe) {
            }
        }
    }

    private void IOBarra() {
        if (barra.isVisible())
            barra.setVisible(true);
        else
            barra.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargador = new CargadorImagenes();

    }


}
