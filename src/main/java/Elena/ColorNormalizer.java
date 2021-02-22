package Elena;

import java.io.FileInputStream; 
import java.io.FileNotFoundException;  
import java.io.IOException;
import javafx.application.Application; 

import javafx.scene.Group;  
import javafx.scene.Scene; 

import javafx.scene.image.Image; 
import javafx.scene.image.ImageView; 
import javafx.scene.image.PixelReader; 
import javafx.scene.image.PixelWriter; 
import javafx.scene.image.WritableImage;
 
import javafx.scene.paint.Color; 
import javafx.stage.Stage;

import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ColorNormalizer extends Application{

   @Override 
   public void start(Stage stage) throws FileNotFoundException{
      int num = 1;
      int start = 1;
      for(int i = start; i<start+num; i++)
         normalize(i);
   }

	public void normalize(int index) throws FileNotFoundException{
      final String DIR = "C:\\Users\\erics\\Google Drive\\Fun\\Elena\\ColorNormalizer_Elena\\src\\main\\resources\\";
		Image image = new Image(new FileInputStream(DIR + index + ".png"));
		final int WIDTH = (int)image.getWidth();
      final int HEIGHT = (int)image.getHeight();
      final Color BLUE = Color.color(0.49411765, 0.6117647, 0.8862745);
      final Color WALL = Color.color(0.9764706, 0.9764706, 0.7019608);
      final Color WALL2 = Color.color(0.88235395, 0.8862745, 0.6117647);

      WritableImage wImage = new WritableImage(WIDTH, HEIGHT);
      PixelReader pixelReader = image.getPixelReader(); 
      PixelWriter writer = wImage.getPixelWriter();


      for(int y = 0; y < HEIGHT; y++) { 
         for(int x = 0; x < WIDTH; x++) { 
            //Retrieving the color of the pixel of the loaded image   
            Color color = pixelReader.getColor(x, y); 
            double blueDif = color.getBlue() - BLUE.getBlue();
            double redDif = color.getRed() - BLUE.getRed();
            double greenDif = color.getGreen() - BLUE.getGreen();
            double dif = .2;

            double blueDif2 = color.getBlue() - WALL.getBlue();
            double redDif2 = color.getRed() - WALL.getRed();
            double greenDif2 = color.getGreen() - WALL.getGreen();
            double dif2 = .3;

            //Setting the color to the writable image
            if(blueDif <= dif && blueDif >= -dif &&
            	redDif <= dif && redDif >= -dif &&
            	greenDif <= dif && greenDif >= -dif){
            	writer.setColor(x, y, BLUE);
            }
            else if(blueDif2 <= dif2 && blueDif2 >= -dif2 &&
               redDif2 <= dif2 && redDif2 >= -dif2 &&
               greenDif2 <= dif2 && greenDif2 >= -dif2){
               writer.setColor(x, y, WALL2);
            }
            else
            	writer.setColor(x, y, color);        
         }
      }
      //Setting the view for the writable image 
      ImageView imageView = new ImageView(wImage); 
      Image newimage = imageView.getImage();
      saveToFile(newimage, index);
	}

   public static void saveToFile(Image image, int index) {
      String dir = "C:\\Users\\erics\\Desktop\\";
      File outputFile = new File(dir + index + ".png");
      BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
      try {
         ImageIO.write(bImage, "png", outputFile);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
  }

  public static void main(String args[]) { 
         launch(args); 
   } 
}
