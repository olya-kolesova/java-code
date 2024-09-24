public class StringBuilderOriginator {
    private StringBuilder currentText;

    public StringBuilderOriginator() {
        this.currentText = new StringBuilder();
    }

    public StringBuilderMomento save() {
        return new StringBuilderMomento(currentText.toString());
    }

    public void restore(StringBuilderMomento save) {
        currentText = new StringBuilder(save.getText());
    }

    public void addText(String text) {
        currentText.append(text);
    }

    public String getCurrentText() {
        return currentText.toString();
    }
}
