package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplication {

    private static final int NUMBER_THREADS =16;
    private static final int MATRIX_SIZE = 2000;

    public static void main(String[] args) {

        // Generate two random matrices, same size
        double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        measureTime(a,b);
    }

    /**
     * Returns the result of a sequential matrix multiplication
     * The two matrices are randomly generated
     * @param a is the first matrix
     * @param b is the second matrix
     * @return the result of the multiplication
     * */
    public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {
        double[][] resultMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        for (int i=0;i<MATRIX_SIZE;i++){
           for (int j=0;j<MATRIX_SIZE;j++){            
              resultMatrix[i][j]=0.00000;
              for (int k=0;k<MATRIX_SIZE;k++){
                 resultMatrix[i][j]+=a[i][k]*b[k][j];
              }
           }
        }
        return resultMatrix;
     }
    
    public static void measureTime(double[][] a, double[][] b){

        double sStart_time = System.nanoTime();
        double[][] c=sequentialMultiplyMatrix(a, b);
        double sEnd_time = System.nanoTime();

        double pStart_time = System.nanoTime();
        double[][] d=parallelMultiplyMatrix(a, b);
        double pEnd_time = System.nanoTime();

        System.out.println("Sequential total time: " +(sEnd_time-sStart_time)/1000000000 + " s");
        System.out.println("Parallel total time: " +(pEnd_time-pStart_time)/1000000000 + "s");
    }

    /**
     * Returns the result of a concurrent matrix multiplication
     * The two matrices are randomly generated
     * @param a is the first matrix
     * @param b is the second matrix
     * @return the result of the multiplication
     * */
    public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_THREADS);

        double[][] result_matrix = new double [MATRIX_SIZE][MATRIX_SIZE] ;

        for (int i = 0; i < MATRIX_SIZE; i++){
            for (int j = 0; j < MATRIX_SIZE; j++){
                executor.execute(new TaskClass(i,j,a,b,result_matrix));
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {

        }
        return result_matrix;
    }
    
    

    public static class TaskClass implements Runnable {
        int row;
        int column;
        double[][] a;
        double[][] b;
        double[][] result_matrix;

        public TaskClass(int row, int column, double[][] a, double[][] b, double[][] result_matrix ) {
            this.row = row;
            this.column = column;
            this.a = a;
            this.b = b;
            this.result_matrix = result_matrix;
        }
        public void run() {
            result_matrix[row][column] = 0;
            for (int i=0; i<MATRIX_SIZE; i++){
                result_matrix[row][column] += a[row][i] * b[i][column];
            }
        }
    }

    /**
     * Populates a matrix of given size with randomly generated integers between 0-10.
     * @param numRows number of rows
     * @param numCols number of cols
     * @return matrix
     */
    private static double[][] generateRandomMatrix (int numRows, int numCols) {
        double matrix[][] = new double[numRows][numCols];
        for (int row = 0 ; row < numRows ; row++ ) {
            for (int col = 0 ; col < numCols ; col++ ) {
                matrix[row][col] = (double) ((int) (Math.random() * 10.0));
            }
        }
        return matrix;
    }
}
