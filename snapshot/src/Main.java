public class Main {
    public static void main(String[] args) {

        StringBuilderCaretaker builder = new StringBuilderCaretaker(new StringBuilderOriginator());
        builder.write("Hello, ");
        builder.print();
        builder.write("World! ");
        builder.doSave();
        builder.write("Goodbye, World!");
        builder.print();
        builder.undo();
        builder.print();

    }
}