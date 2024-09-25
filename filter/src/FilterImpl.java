public class FilterImpl implements Filter {

    public FilterImpl() {}

    @Override
    public Object apply(Object object) {
        return Integer.parseInt(object.toString());
    }
}
