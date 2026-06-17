// Custom Exception
class InvalidShapeException extends Exception {

    public InvalidShapeException(String message) {
        super(message);
    }
}

// Abstract Shape Class
abstract class Shape {

    protected String color;
    protected boolean filled;

    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    public abstract void resize(double factor)
            throws InvalidShapeException;

    @Override
    public String toString() {
        return "Color: " + color +
               ", Filled: " + filled;
    }
}

// Circle Class
class Circle extends Shape {

    private double radius;

    public Circle(String color, boolean filled, double radius)
            throws InvalidShapeException {

        super(color, filled);

        if (radius <= 0) {
            throw new InvalidShapeException(
                    "Radius must be greater than 0");
        }

        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void resize(double factor)
            throws InvalidShapeException {

        if (factor <= 0) {
            throw new InvalidShapeException(
                    "Resize factor must be greater than 0");
        }

        radius *= factor;
    }

    @Override
    public String toString() {
        return "Circle (Radius = " + radius + ")";
    }
}

// Rectangle Class
class Rectangle extends Shape {

    private double width;
    private double height;

    public Rectangle(String color,
                     boolean filled,
                     double width,
                     double height)
            throws InvalidShapeException {

        super(color, filled);

        if (width <= 0 || height <= 0) {
            throw new InvalidShapeException(
                    "Width and Height must be greater than 0");
        }

        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void resize(double factor)
            throws InvalidShapeException {

        if (factor <= 0) {
            throw new InvalidShapeException(
                    "Resize factor must be greater than 0");
        }

        width *= factor;
        height *= factor;
    }

    @Override
    public String toString() {
        return "Rectangle (Width = " + width +
                ", Height = " + height + ")";
    }
}

// Triangle Class
class Triangle extends Shape {

    private double side1;
    private double side2;
    private double side3;

    public Triangle(String color,
                    boolean filled,
                    double side1,
                    double side2,
                    double side3)
            throws InvalidShapeException {

        super(color, filled);

        if (side1 <= 0 || side2 <= 0 || side3 <= 0) {
            throw new InvalidShapeException(
                    "All sides must be greater than 0");
        }

        // Triangle Inequality Rule
        if ((side1 + side2 <= side3) ||
            (side1 + side3 <= side2) ||
            (side2 + side3 <= side1)) {

            throw new InvalidShapeException(
                    "Triangle inequality violated");
        }

        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    @Override
    public double getPerimeter() {
        return side1 + side2 + side3;
    }

    @Override
    public double getArea() {

        double s = getPerimeter() / 2;

        return Math.sqrt(
                s * (s - side1)
                  * (s - side2)
                  * (s - side3));
    }

    @Override
    public void resize(double factor)
            throws InvalidShapeException {

        if (factor <= 0) {
            throw new InvalidShapeException(
                    "Resize factor must be greater than 0");
        }

        side1 *= factor;
        side2 *= factor;
        side3 *= factor;
    }

    @Override
    public String toString() {
        return "Triangle (" +
                side1 + ", " +
                side2 + ", " +
                side3 + ")";
    }
}

// Driver Class
public class ShapeTest {

    // Part (d)
    public static void printAreas(Shape[] shapes) {

        System.out.println("\nAreas of Shapes:");

        for (Shape s : shapes) {
            System.out.println(
                    s + " --> Area = "
                    + s.getArea());
        }
    }

    // Part (d)
    public static Shape largest(Shape[] shapes) {

        Shape largest = shapes[0];

        for (Shape s : shapes) {

            if (s.getArea() > largest.getArea()) {
                largest = s;
            }
        }

        return largest;
    }

    public static void main(String[] args) {

        try {

            // Creating valid shapes
            Circle circle =
                    new Circle("Red", true, 5);

            Rectangle rectangle =
                    new Rectangle("Blue",
                            true,
                            4,
                            6);

            Triangle triangle =
                    new Triangle("Green",
                            true,
                            3,
                            4,
                            5);

            // Store in Shape array
            Shape[] shapes = {
                    circle,
                    rectangle,
                    triangle
            };

            // Dynamic Binding Demonstration
            printAreas(shapes);

            // Find largest shape
            Shape biggest = largest(shapes);

            System.out.println(
                    "\nShape with Largest Area:");
            System.out.println(biggest);
            System.out.println(
                    "Area = " +
                    biggest.getArea());

            // Resize example
            circle.resize(2);

            System.out.println(
                    "\nAfter resizing Circle by factor 2:");
            System.out.println(circle);
            System.out.println(
                    "New Area = " +
                    circle.getArea());

            // Invalid Triangle Example
            Triangle invalidTriangle =
                    new Triangle(
                            "Black",
                            true,
                            2,
                            3,
                            10);

        } catch (InvalidShapeException e) {

            System.out.println(
                    "\nException Caught: "
                            + e.getMessage());
        }
    }
}