# make

Tiny make for Java

```java
Make make = new Make();
// foo: bar qux
make.define()
  .outputs("foo")
  .inputs("bar")
  .inputs("qux")
  .executes(() -> System.out.println("foo"));
// bar: baz
make.define()
  .outputs("bar")
  .inputs("baz")
  .executes(() -> System.out.println("bar"));
// qux: baz
make.define()
  .outputs("qux")
  .inputs("baz")
  .executes(() -> System.out.println("qux"));
// baz:
make.define()
  .outputs("baz")
  .executes(() -> System.out.println("baz"));
// make
make.execute();
```
