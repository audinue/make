import make.Make;

class Main {

    public static void main(String[] args) throws Exception {
        Make make = new Make();
        make.define()
                .outputs("foo")
                .inputs("bar")
                .inputs("qux")
                .executes(() -> System.out.println("foo"));
        make.define()
                .outputs("bar")
                .inputs("baz")
                .executes(() -> System.out.println("bar"));
        make.define()
                .outputs("qux")
                .inputs("baz")
                .executes(() -> System.out.println("qux"));
        make.define()
                .outputs("baz")
                .executes(() -> System.out.println("baz"));
        make.execute();
    }
}
