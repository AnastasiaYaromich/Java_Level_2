public class MyArrayDataException extends ArrayExcept{

    public MyArrayDataException(int a, int b) {
        super("Указан неверный тип данных в ячейке: " + a + " " + b);
    }
}

