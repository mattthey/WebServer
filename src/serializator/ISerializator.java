package serializator;

public interface ISerializator<T>
{
    /**
     * Сериализация объекта в массив байт
     * @param o обект, который нужно сериализовать
     * @return массив байт, которые можно восстановить в объект
     */
    public byte[] serialize(Object o);

    /**
     * Перевод массив байт в объект
     * @param raw массив данных из которых состоит объект
     * @return объект
     */
    public T deserialize(byte[] raw);
}
