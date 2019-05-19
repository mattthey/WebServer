package serializator.testClass;

public class MyClass1
{
    public int intField;
    public String strField;
//    public int[] arrayField;
    private  double doubleField;
    public SubMyClass subMyClass;

    public MyClass1()
    {
        intField = 1;
        strField = "2";
//        arrayField = new int[] { 4, 5 };
        doubleField = 6.7;
        subMyClass = new SubMyClass();
    }

    public double getDoubleField()
    {
        return doubleField;
    }
}
