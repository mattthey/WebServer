package serializator.testClass;

public class MyClass1
{
    public int intField;
    public String strField;
    private  double doubleField;
    public SubMyClass subMyClass;

    public MyClass1()
    {
        intField = 1;
        strField = "2";
        doubleField = 6.7;
        subMyClass = new SubMyClass();
    }

    public double getDoubleField()
    {
        return doubleField;
    }
}
