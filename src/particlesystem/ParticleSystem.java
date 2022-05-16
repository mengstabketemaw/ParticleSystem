package particlesystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class ParticleSystem extends GraphicsBase{

    Emitter emitter = new Emitter();
    int psize=100;
    ArrayList<Particle> particles = new ArrayList<Particle>();
    Random rand = new Random();
    double Pduration =200;
    String shape="Circle";
    Color color = Color.RED;
    double particleSize=3;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void setup() {
        title = "ParticelSystem";
        width = 800;
        height= 600;
        frame = 30;
        amGUI();
        gc.getCanvas().setOnMouseDragged(e->{
             for(int i = 0;i<psize;i++){
                
                double xDir = rand.nextDouble() * 3 - 1;
                double yDir = rand.nextDouble() + 1;
                Particle particle = new Particle(Pduration,e.getX(),e.getY(),xDir,yDir);
                particles.add(particle);
            }
            ;});

    }

    @Override
    public void draw() {
        emitter.emitte(gc);
    }
    
    
    class Emitter{

        public void emitte(GraphicsContext gc){
            
            for(Particle particle : particles){
                particle.update();
                particle.draw(gc);
            }
            
            particles.removeIf(e->e.duration==0);
        }
    }
    
    class Particle{
        double duration;
        double x,y,xDir,yDir;
        
        Particle(double du,double x,double y,double xDir,double yDir){
            duration = du;
            this.x = x;
            this.y = y;
            this.xDir = xDir;
            this.yDir = yDir;
          }
        public void update() {
        x+=xDir;
        y+=yDir;
        duration--;
        }

        
        public void draw(GraphicsContext gc) {
           
            gc.setFill(Color.color(color.getRed(),color.getGreen(),color.getBlue(), duration/Pduration));
           
            if(shape.startsWith("Circ")){
            gc.fillOval(x, y, particleSize, particleSize);
            }
            else if(shape.startsWith("Rec")){
            gc.fillRect(x, y, particleSize, particleSize);
            }
            else if(shape.startsWith("Tri")){
            gc.fillPolygon(new double[]{x,x-particleSize,x+particleSize},new double[]{y,y+particleSize,y+particleSize}, 3);
            }
        }
        
    }
    
    // am Gui
    public void amGUI(){
    Slider size = new Slider(1,20,3);
    Slider dur = new Slider(10,1000,200);
    Slider partsize = new Slider(10,1000,100);
         size.valueProperty().addListener((q,w,r)->{
         particleSize = r.doubleValue();
     });
         partsize.valueProperty().addListener((q,w,r)->{
         psize = r.intValue();
     });

     dur.valueProperty().addListener((q,w,r)->{
         for(Particle particle :particles)
             particle.duration=r.intValue();
         Pduration = r.intValue();
         
     });
        
    ColorPicker picker = new ColorPicker();
            picker.setValue(Color.RED);
            picker.valueProperty().addListener(e->{
            color = picker.getValue();
            });
    ColorPicker backpicker = new ColorPicker();
            backpicker.setValue(Color.BLACK);
            backpicker.valueProperty().addListener(e->{
            background = backpicker.getValue();
            });
    ToggleGroup tg = new ToggleGroup();
    RadioButton circle = new RadioButton("Circle");
    RadioButton rectangle = new RadioButton("Rectangle");
    RadioButton triangle = new RadioButton("Triangle");
    circle.setSelected(true);
    circle.setToggleGroup(tg);
    rectangle.setToggleGroup(tg);
    triangle.setToggleGroup(tg);
    
            tg.selectedToggleProperty().addListener(e->{
            shape = ((RadioButton)tg.getSelectedToggle()).getText();
            });
    
    GridPane pane = new GridPane();
    
    pane.add(new Label("Duration"),0, 0);pane.add(dur,1, 0);
    pane.add(new Label("Size"),0, 1);pane.add(size,1, 1);
    pane.add(new Label("Particles"),0, 2);pane.add(partsize,1, 2);
    pane.add(new VBox(25,new Label("Shape"),circle,triangle,rectangle),1, 3);
    pane.add(picker,1, 4); pane.add(new Label("Color"),0, 4);
    pane.add(backpicker,1, 5); pane.add(new Label("BackColor"),0, 5);
    pane.setVgap(30);
    
    root.setRight(pane);
    
    }
    
    
    
    
}
