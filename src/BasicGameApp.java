//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv DON'T CHANGE! vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// Graphics Libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
public class BasicGameApp implements Runnable, KeyListener {

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;


    //Variable Definition Section
    //You can set their initial values too
    // Like Mario mario = new Mario(); //
    Nemo nemo;
    Kyogre kyogre;
    Bascu bascu;
    Image background;







    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor
        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game
        kyogre = new Kyogre(500,350,4,4,120,120);
        kyogre.name = "Kyogre Kyogre";
        kyogre.aliveimage = Toolkit.getDefaultToolkit().getImage("Kyogre.png");
        kyogre.deadimage = Toolkit.getDefaultToolkit().getImage("Kyogrehurt.png");

        bascu = new Bascu (300, 400, 3, 3, 110, 110 );
        bascu.image = Toolkit.getDefaultToolkit().getImage("Bascu.png");

        nemo = new Nemo(220,670,4,4,80,70);
        nemo.aliveimage = Toolkit.getDefaultToolkit().getImage("Nemo.png");
        nemo.deadimage = Toolkit.getDefaultToolkit().getImage("dead.png");

        background = Toolkit.getDefaultToolkit().getImage("aquarium.png");




    }
    // end BasicGameApp constructor

    public void moveThings() {
        //call the move() code for each object  -
        kyogre.move();
        bascu.move();
        nemo.move();

    }

    public void checkCollisions(){
        //System.out.println(tenis.hitbox);
        //System.out.println(racket.hitbox);
        if (kyogre.hitbox.intersects(bascu.hitbox) ){
           // System.out.println("You got hit with Shadow Sneak!");
            kyogre.isAlive = false;
            kyogre.dx = -kyogre.dx;
            bascu.dx = -kyogre.dx;
            bascu.dy = -kyogre.dy;
            //kyogre.dy = -kyogre.dy;
            //racket.dy= -racket.dy;
        }
        else {
            kyogre.isAlive = true;
        }
        if (nemo.hitbox.intersects(bascu.hitbox) ){
            // System.out.println("You got hit with Shadow Sneak!");
            nemo.isAlive = false;
            nemo.dx = -nemo.dx;
            bascu.dx = -nemo.dx;
            bascu.dy = -nemo.dy;

        }
        else {
            nemo.isAlive = true;
        }

    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the images
        // Signature: drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
        if (kyogre.isAlive) {
            g.drawImage(kyogre.aliveimage, kyogre.xpos, kyogre.ypos, kyogre.width, kyogre.height, null);
        }
        else {
            g.drawImage(kyogre.deadimage,kyogre.xpos, kyogre.ypos, kyogre.width, kyogre.height, null );
        }
        g.drawImage(bascu.image, bascu.xpos, bascu.ypos, bascu.width, bascu.height, null);
        if (nemo.isAlive) {
            g.drawImage(nemo.aliveimage, nemo.xpos, nemo.ypos, nemo.width, nemo.height, null);
        }
        else {
            g.drawImage(nemo.deadimage,nemo.xpos, nemo.ypos, nemo.width, nemo.height, null );
        }



        // Keep the code below at the end of render()
        g.dispose();
        bufferStrategy.show();
    }














    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv DON'T CHANGE! vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    // PSVM: This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            checkCollisions();
            render();  // paint the graphics
            pause(10); // sleep for 10 ms
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private Image getImage(String filename){
        return Toolkit.getDefaultToolkit().getImage(filename);
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        canvas.addKeyListener(this);
        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // we won't use this
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        int key = e.getKeyCode();

        if (key == 65) {
            kyogre.left = true; // a (left)
        } else if (key == 87) {
            kyogre.up = true; // w (up)
        } else if (key == 68) {
            kyogre.right = true; // d (right)
        } else if (key == 83) {
            kyogre.down = true; // s (down)
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode());
        int key = e.getKeyCode();
        
        if (key == 65) {
            kyogre.left = false; // a (left)
        } else if (key == 87) {
            kyogre.up = false; // w (up)
        } else if (key == 68) {
            kyogre.right = false; // d (right)
        } else if (key == 83) {
            kyogre.down = false; // s (down)
        }

    }
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
}
