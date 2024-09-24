
public class StringBuilderCaretaker {
    private StringBuilderOriginator originator;

    private StringBuilderMomento savedStringBuilder;

    public StringBuilderCaretaker(StringBuilderOriginator originator) {
        this.originator = originator;
    }

    public void write(String text) {
        originator.addText(text);
    }

    public void doSave() {
        savedStringBuilder = originator.save();
    }

    public void undo() {
        originator.restore(savedStringBuilder);
    }

    public void print() {
        System.out.println(originator.getCurrentText());
    }



}
