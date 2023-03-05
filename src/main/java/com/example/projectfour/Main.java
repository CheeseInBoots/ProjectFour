package com.example.projectfour;/*
CS-255 Getting started code for the assignment
I do not give you permission to post this code online
Do not post your solution online
Do not copy code
Do not use JavaFX functions or other libraries to do the main parts of the assignment (i.e. ray tracing steps 1-7)
All of those functions must be written by yourself
You may use libraries to achieve a better GUI
*/

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
//--module-path  "C:\Program Files\Java\javafx-sdk-19\lib"  --add-modules javafx.controls,javafx.fxml

public class Main extends Application {

    static Sphere firstSphere, secondSphere, thirdSphere, fourthSphere;
    int Width = 640;
    int Height = 640;
    double green_col = 255;
    double blue_col = 255;
    double red_col = 255;
    double xCo_ord = 0;
    double yCo_ord = 0;
    double zCo_ord = 0;

    double x, y , z;
    double u, v;
    Vector VRP;/*View reference point. The view reference point (VRP) is a 3D
        coor-dinate defining the position of the centre of the image plane. the */

    Vector VPN;
        /*View plane normal. The view plane normal (VPN) is a 3D vector
        defining the direction the camera looks in world space.*/

    Vector VUV;/*The view up vector (VUV) is a 3D vector defining the
        up direction (or y-axis) of the camera in world space*/

    Vector look_At; /* The look at (LookAt) is a 3D point defining where exactly the
        camera is pointing*/
    Vector VRV; /* is a 3D vector defining
        the direction the right (or x-axis) of the camera in world space*/

    double radiusOfSphere = 50;
    RayTracer ray;
    //radio button
    private  RadioButton sphere1Toggle, sphere2Toggle, sphere3Toggle, sphere4Toggle;
    private ToggleGroup radioToggleGroup;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Ray Tracing");
        //Setting up a new ray tracer object to raytrace spheres
        ray = new RayTracer();
        radioToggleGroup = new ToggleGroup();
        //We need 3 things to see an image
        //1. We create an image we can write to
        WritableImage image = new WritableImage(Width, Height);
        //2. We create a view of that image
        ImageView view = new ImageView(image);
        //3. Add to the pane (below)
      //  ray.setOrigin(new Vector(i - w / 2, j - h / 2, -400))
        this.VRP = new Vector(x,y,z);

        this.VUV =  new Vector(0,1,0);
        this.look_At =  new Vector(0,0,0);
        this.VRV  = new Vector(1,0,0);
        setCam();

        // Here instantiate radioButton to control the spheres
        sphere1Toggle = createNewRadioButton("Sphere1", "r1");
        sphere2Toggle = createNewRadioButton("Sphere2", "r2");
        sphere3Toggle = createNewRadioButton("Sphere3", "r3");
        sphere4Toggle = createNewRadioButton("Sphere4", "r3");
        /*the sliders for interacting the radius of each sphere's colour, radius, position */
        //Control the radius of the sphere

        Slider sphereRadiusSlider = new Slider(0, 100, 50);
        sphereRadiusSlider.setShowTickLabels(true);
        Slider r_slider = new Slider(0,255, red_col);
        r_slider.setShowTickLabels(true);
        Slider g_slider = new Slider(0, 255, green_col);
        g_slider.setShowTickLabels(true);
        Slider b_slider = new Slider(0,255, blue_col);
        b_slider.setShowTickLabels(true);
        Slider sphereXSlider = new Slider (-255, 255, 0);
        sphereXSlider.setShowTickLabels(true);
        Slider sphereYSlider = new Slider (-255, 255, 0);
        sphereYSlider.setShowTickLabels(true);
        Slider sphereZSlider = new Slider (-255, 255, 0);
        sphereZSlider.setShowTickLabels(true);

        //Control the camera of the object
        Slider cameraSliderX = new Slider(x, y,z);
        Slider cameraSliderY = new Slider(x, y,z);
        cameraSliderY.setShowTickLabels(true);
        cameraSliderY.setMin(0);
        cameraSliderY.setMax(360);
        cameraSliderY.setValue(180);

        double u, v;

        Label radiusVal = new Label("Radius");
        Label redVal = new Label("Red");
        Label greenVal = new Label("Green");
        Label blueVal = new Label("Blue");
        Label xPos = new Label("X_POS");
        Label yPos = new Label("Y_POS");
        Label zPos = new Label("Z_POS");

        Label verticaCameraLable = new Label("Azimuth");
        Label horizontalCameraLable = new Label("Altitude");

        //Instantiating hard core value for the original sphere
        Vector sphere_col = new Vector(1.0, 1.0, 1.0);
        Vector sphere4_col = new Vector(red_col, green_col, blue_col);
        Vector Centre1 = new Vector(xCo_ord - 200, yCo_ord, zCo_ord);
        Vector Centre2 = new Vector(xCo_ord, yCo_ord, zCo_ord);
        Vector Centre3 = new Vector(xCo_ord + 200, yCo_ord, zCo_ord);
        Vector Centre4 = new Vector(xCo_ord + 150, yCo_ord -150, zCo_ord-100);
        // creating new spheres
        ray.createNewSpheres(radiusOfSphere, Centre1, sphere_col);
        ray.createNewSpheres(radiusOfSphere, Centre2, sphere_col);
        ray.createNewSpheres(radiusOfSphere, Centre3, sphere_col);
        ray.createNewSpheres(radiusOfSphere, Centre4, sphere_col);
        firstSphere = ray.getSpheres().get(0);
        secondSphere = ray.getSpheres().get(1);
        thirdSphere = ray.getSpheres().get(2);
        fourthSphere = ray.getSpheres().get(3);


        sphere1Toggle.setOnAction(e -> {
            sphereRadiusSlider.setValue(50);
            r_slider.setValue(255);
            g_slider.setValue(255);
            b_slider.setValue(255);
            sphereXSlider.setValue(5);
            sphereYSlider.setValue(5);
            sphereZSlider.setValue(5);

            // here we change the radius of the sphers
            sphereRadiusSlider.setOnMouseReleased(event -> {

                radiusOfSphere = sphereRadiusSlider.getValue();
                firstSphere.setRadius(radiusOfSphere);
                Render(image);
            });
            // here we change the red value of the sphere. im not sure how to change the red value
            r_slider.setOnMouseReleased(event1 -> {
                red_col = r_slider.getValue();
                Vector col = new Vector(red_col / 255.0, firstSphere.getColor().y, firstSphere.getColor().z);
                firstSphere.setColor(col);
                Render(image);
            });
            g_slider.setOnMouseReleased(event1 -> {
                green_col = g_slider.getValue();
                Vector col = new Vector(firstSphere.getColor().x, green_col / 255.0, firstSphere.getColor().z);
                firstSphere.setColor(col);
                Render(image);

            });
            b_slider.setOnMouseReleased(event1 -> {
                blue_col = b_slider.getValue();
                Vector col = new Vector(firstSphere.getColor().x, firstSphere.getColor().y, blue_col / 255.0);
                firstSphere.setColor(col);
                Render(image);

            });
            sphereXSlider.setOnMouseReleased(event2 -> {
                xCo_ord = sphereXSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                firstSphere.setCentre(cent);
                Render(image);

            });

            sphereYSlider.setOnMouseReleased(event3 -> {
                yCo_ord = sphereYSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                firstSphere.setCentre(cent);
                Render(image);

            });
            sphereZSlider.setOnMouseReleased(event4 -> {
                zCo_ord = sphereZSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                firstSphere.setCentre(cent);
                Render(image);

            });

        });


        sphere2Toggle.setOnAction(e -> {
            sphereRadiusSlider.setValue(50);
            r_slider.setValue(255);
            g_slider.setValue(255);
            b_slider.setValue(255);
            sphereXSlider.setValue(5);
            sphereYSlider.setValue(5);
            sphereZSlider.setValue(5);

            // here we change the radius of the sphers
            sphereRadiusSlider.setValue(50);
            sphereRadiusSlider.setOnMouseReleased(event -> {
                radiusOfSphere = sphereRadiusSlider.getValue();
                secondSphere.setRadius(radiusOfSphere);
                Render(image);
            });
            // here we change the red value of the sphere. im not sure how to change the red value
            r_slider.setOnMouseReleased(event1 -> {
                red_col = r_slider.getValue();
                Vector col = new Vector(red_col / 255.0, secondSphere.getColor().y, secondSphere.getColor().z);
                secondSphere.setColor(col);
                Render(image);
            });
            g_slider.setOnMouseReleased(event1 -> {
                green_col = g_slider.getValue();
                Vector col = new Vector(secondSphere.getColor().x, green_col / 255.0, secondSphere.getColor().z);
                secondSphere.setColor(col);
                Render(image);

            });
            b_slider.setOnMouseReleased(event1 -> {
                blue_col = b_slider.getValue();
                Vector col = new Vector(secondSphere.getColor().x, secondSphere.getColor().y, blue_col / 255.0);
                secondSphere.setColor(col);
                Render(image);

            });
            sphereXSlider.setOnMouseReleased(event2 -> {
                xCo_ord = sphereXSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                secondSphere.setCentre(cent);
                Render(image);

            });

            sphereYSlider.setOnMouseReleased(event3 -> {
                yCo_ord = sphereYSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                secondSphere.setCentre(cent);
                Render(image);

            });
            sphereZSlider.setOnMouseReleased(event4 -> {
                zCo_ord = sphereZSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                secondSphere.setCentre(cent);
                Render(image);

            });

        });

        sphere4Toggle.setOnAction(e -> {

            // here we change the radius of the sphers
            sphereRadiusSlider.setValue(50);
            r_slider.setValue(255);
            g_slider.setValue(255);
            b_slider.setValue(255);
            sphereXSlider.setValue(5);
            sphereYSlider.setValue(5);
            sphereZSlider.setValue(5);
            sphereRadiusSlider.setOnMouseReleased(event -> {
                radiusOfSphere = sphereRadiusSlider.getValue();
                fourthSphere.setRadius(radiusOfSphere);
                Render(image);
            });
            // here we change the red value of the sphere. im not sure how to change the red value
            r_slider.setOnMouseReleased(event1 -> {
                red_col = r_slider.getValue();
                Vector col = new Vector(red_col / 255.0, fourthSphere.getColor().y, fourthSphere.getColor().z);
                fourthSphere.setColor(col);
                Render(image);
            });
            g_slider.setOnMouseReleased(event1 -> {
                green_col = g_slider.getValue();
                Vector col = new Vector(fourthSphere.getColor().x, green_col / 255.0, fourthSphere.getColor().z);
                fourthSphere.setColor(col);
                Render(image);

            });
            b_slider.setOnMouseReleased(event1 -> {
                blue_col = b_slider.getValue();
                Vector col = new Vector(fourthSphere.getColor().x, fourthSphere.getColor().y, blue_col / 255.0);
                fourthSphere.setColor(col);
                Render(image);

            });
            sphereXSlider.setOnMouseReleased(event2 -> {
                xCo_ord = sphereXSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                fourthSphere.setCentre(cent);
                Render(image);

            });

            sphereYSlider.setOnMouseReleased(event3 -> {
                yCo_ord = sphereYSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                fourthSphere.setCentre(cent);
                Render(image);

            });

            sphereZSlider.setOnMouseReleased(event4 -> {
                zCo_ord = sphereZSlider.getValue();
                Vector cent = new Vector(xCo_ord, yCo_ord, zCo_ord);
                fourthSphere.setCentre(cent);
                Render(image);

            });

        });

        // TODO SET THE CAMERA FOR X POSITION,FOR PART 7, UNCOMPLETED

        cameraSliderY.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                // change the orgin of the sphere
                //change the VPN
                // change the
                y = newValue.doubleValue();

                Render(image);


            }
        });

        /*The following is in case you want to interact with the image in any way
        //e.g., for user interaction, or you can find out the pixel position for debugging */
        view.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println(event.getX() + " " + event.getY());
            event.consume();
        });

        Render(image);
        GridPane root = new GridPane();
        root.setVgap(12);
        root.setHgap(12);
        GridPane.setMargin(sphere1Toggle, new Insets(50, 0, 0, 0));
        GridPane.setMargin(sphere2Toggle, new Insets(100, 0, 0, 0));
        GridPane.setMargin(sphere3Toggle, new Insets(150, 0, 0, 0));
        GridPane.setMargin(sphere4Toggle, new Insets(200, 0, 0, 0));
        //Setting up all the objects in the scene
        root.add(view, 0, 0);
        root.add(sphere1Toggle, 1, 0);
        root.add(sphere2Toggle, 1, 0);
        root.add(sphere3Toggle, 1, 0);
        root.add(sphere4Toggle, 1, 0);
        root.add(r_slider, 0, 1);
        root.add(g_slider, 0, 2);
        root.add(b_slider, 0, 3);
        root.add(sphereXSlider, 0, 4);
        root.add(sphereYSlider, 0, 5);
        root.add(sphereZSlider, 0, 6);
        root.add(sphereRadiusSlider, 0, 7);
        root.add(cameraSliderY, 0, 8);
        root.add(cameraSliderX, 0, 9);

        root.add(redVal, 1, 1);
        root.add(greenVal, 1, 2);
        root.add(blueVal, 1, 3);
        root.add(xPos, 1, 4);
        root.add(yPos, 1, 5);
        root.add(zPos, 1, 6);
        root.add(radiusVal, 1, 7);
        root.add(verticaCameraLable, 1, 8);
        root.add(horizontalCameraLable, 1, 9);


        //Display to user
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to create multiple radiobuttons to specifically slelect the spheres and control
     * its colours and the positions
     * @param name
     * @param ID
     * @return
     */

    public RadioButton createNewRadioButton(String name, String ID) {
        RadioButton radioButton = new RadioButton();
        radioButton.setText(name);
        radioButton.setId(ID);
        radioButton.setToggleGroup(radioToggleGroup);
        return radioButton;

    }
    public void setCam() {
        this.VPN = look_At.sub(VRP);
        this.VPN.normalise();
        this.VRV = VPN.cross(VUV);
        this.VPN.normalise();
        this. VUV = VRV.cross(VPN);
        this.VUV.normalise();

    }

    public void Render(WritableImage image) {
        //Get image dimensions, and declare loop variables
        int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
        PixelWriter image_writer = image.getPixelWriter();
        Vector backgroundColour = new Vector(0, 0, 0); // the  background colour
        ray.setOrigin((VRP.add(VRV.mul(u).add(VUV.mul(v)))));
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                image_writer.setColor(i, j, Color.color(backgroundColour.x, backgroundColour.y, backgroundColour.z, 1.0));

            }

        }
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                // trace the ray against the scene
                ray.setOrigin(new Vector(u, v, -400));
                //for each sphere in the, check if it has intersection
                for (Sphere sphere : ray.getSpheres()) {
                    //it doesnt interscet with the sphere so it goes in the backgorund
                    if (ray.rayInteractionsDistance(sphere) < 0) {
                        image_writer.setColor(i, j, Color.color(backgroundColour.x, backgroundColour.y, backgroundColour.z, 1.0));
                    }
                    v = ((h - j)-h/2) * 1;
                    u = (i - w/2)* 1;
                   // ray.setCamera();
                    // which is the closest sphere -the index to the sphere so far, the smallest positive t
                    Sphere closestSphere = ray.closestSphereInArray();
                    //given a sphere it will we other you hit, the smallest positive t
                    if (closestSphere != null) {
                        Vector col = ray.ambientShading(closestSphere, ray);
                        image_writer.setColor(i, j, Color.color(col.x, col.y, col.z, 1.0));
                    }

                }
            }

        } // column loop
    }// row loop


}