public class Main {
    public static void main(String[] args) {
        String arr1[][] = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        String arr2[][] = {
                {"1", "2", "3", "4"},
                {"5", "6", "семь", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        String arr3[][] = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"},
                {"17", "18", "19", "20"}
        };

        String arr4[][] = {
                {"1", "2", "3", "4"},
                {"5", "6", "7000", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };



        try {
            System.out.println("Cумма элементов массива arr1 =:" + ArraySum.arraySum(arr1));
        } catch (ArrayExcept e) {
            e.printStackTrace();
        }


        try {
            System.out.println("Cумма элементов массива arr2 =:" + ArraySum.arraySum(arr2));
        } catch (ArrayExcept e) {
            e.printStackTrace();
        }


        try {
            System.out.println("Cумма элементов массива arr3 =:" + ArraySum.arraySum(arr3));
        } catch (ArrayExcept e) {
            e.printStackTrace();
        }


        try {
            System.out.println("Cумма элементов массива arr4 =:" + ArraySum.arraySum(arr4));
        } catch (ArrayExcept e) {
            e.printStackTrace();
        }


    }
}
