import java.util.Arrays;

public class Counter {

    static final int size = 1000;
    static final int h = size / 2;


    public void countArr1() throws InterruptedException {
        float[] arr = new float[size];

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
      System.out.println("Массив arr в первоначальном виде: " + Arrays.toString(arr));
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
      System.out.println("Массив arr c новыми значениями: " + Arrays.toString(arr));
        System.out.println("Время расчета новых значений для arr, мс: " + (System.currentTimeMillis() - a));
    }

public void countArr2() throws InterruptedException {
    float[] arr = new float[size];
    float arr1[] = new float[h];
    float arr2[] = new float[h];

    for (int i = 0; i < size; i++) {
      arr[i] = 1;
    }

    System.out.println("Массив arr в первоначальном виде: " + Arrays.toString(arr));

    long b = System.currentTimeMillis();
    System.arraycopy(arr, 0, arr1, 0, h);
    System.arraycopy(arr, h, arr2, 0, h);
    System.out.println("Массив arr1: " + Arrays.toString(arr1));
    System.out.println("Массив arr2: " + Arrays.toString(arr2));
    System.out.println("Время разбивки массива arr на два: " + (System.currentTimeMillis() - b));
    System.out.println();


    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            long c = System.currentTimeMillis();
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
            System.out.println("Массив arr1 c новыми значениями: " + Arrays.toString(arr1));
            System.out.println("Время просчета массива arr1: " + (System.currentTimeMillis() - c));
            System.out.println();
        }
    });

    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            long d = System.currentTimeMillis();
            for (int i = 0; i < arr2.length; i++) {
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
            System.out.println("Массив arr2 c новыми значениями: " + Arrays.toString(arr2));
            System.out.println("Время просчета массива arr2: " + (System.currentTimeMillis() - d));
        }
    });

    try {
        t1.join();
        t2.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    t1.start();
    t2.start();

    Thread.sleep(100);

    long e = System.currentTimeMillis();
    System.arraycopy(arr1, 0, arr, 0, h);
    System.arraycopy(arr2, 0, arr, h, h);
    System.out.println("Склееный массив arr c новыми значениями: " + Arrays.toString(arr));
    System.out.println("Время склейки двух массивов обратно в arr: " + (System.currentTimeMillis() - e));
    }
    }
