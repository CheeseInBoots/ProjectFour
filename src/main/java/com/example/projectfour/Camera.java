package com.example.projectfour;

public class Camera {
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

    public Camera(double a, double b, double c) {
        this.VRP = new Vector(a,b,c);
        this.VPN = VPN;
        this.VUV = VUV = new Vector(0,1,0);
        this.look_At =  new Vector(0,0,0);
        this.VRV = VRV = new Vector(1,0,0);
        setCam();
    }

    public void setCam() {

                        this.VPN = look_At.sub(VRP);
                        this.VPN.normalise();
                        this.VRV = VPN.cross(VUV);
                         this.VPN.normalise();
                         this. VUV = VRV.cross(VPN);
                        this.VUV.normalise();

        }

}
