public class ArraySum{

   public static int arraySum(String [][] arr) throws MyArrayDataException, MyArraySizeException {
        int sum = 0;

       if (arr.length != 4) throw new MyArraySizeException();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length;  j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                }
                    catch (Exception e) {
                    throw new MyArrayDataException(i, j);
            }
                }
            }
       return sum;
    }
}
