package main.java.com.javacode;

import com.javacode.Circle;
import com.javacode.Rectangle;
import com.javacode.Triangle;
import com.javacode.threedimensionalshapes.Cube;
import com.javacode.threedimensionalshapes.Sphere;
import com.javacode.threedimensionalshapes.converter.UnitConverter;
import static com.javacode.threedimensionalshapes.converter.UnitConverter.Units.INCHES;

public class Main {
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.println(circle.getArea());
        System.out.println(circle.getPerimeter());

        Triangle triangle = new Triangle(5, 5, 5);
        System.out.println(triangle.getPerimeter());
        System.out.println(triangle.getArea());

        Rectangle rectangle = new Rectangle(7, 3);
        System.out.println(rectangle.getPerimeter());
        System.out.println(rectangle.getArea());

        UnitConverter unitConverter = new UnitConverter(INCHES);
        System.out.println(unitConverter.convertFromMeters(2));

        Cube cube = new Cube(5);
        System.out.println(cube.getVolume());

        Sphere sphere = new Sphere(5);
        System.out.println(sphere.getVolume());


    }
}