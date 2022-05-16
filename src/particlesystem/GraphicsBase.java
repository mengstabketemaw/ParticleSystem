package particlesystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 *
 * @author Ketemaw
 */
public abstract class GraphicsBase extends Application{
    protected String title;
    protected int width;
    protected int height;
    protected int frame;
    protected GraphicsContext gc;
    protected BorderPane root;
    protected Paint background = Color.BLACK;
    
    private Timeline timeline = new Timeline();
    @Override public void start(Stage stage){
        
        Canvas canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        root = new BorderPane(canvas);
        setup();
        Scene scene = new Scene(root); 
        canvas.setHeight(height);
        canvas.setWidth(width);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        
        startDrawing();

        
    }
    
    abstract public void setup();
    abstract public void draw();
    
    private void startDrawing() {

        if(frame==0)return;
        timeline.stop();
        timeline.getKeyFrames().clear();
        KeyFrame keyframe = new KeyFrame(new Duration(1000/frame),e->internalDraw());
        timeline.getKeyFrames().add(keyframe);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void internalDraw() {
        gc.setFill(background);
        gc.fillRect(0, 0, width, height);
        draw();
        
    }
    
}
