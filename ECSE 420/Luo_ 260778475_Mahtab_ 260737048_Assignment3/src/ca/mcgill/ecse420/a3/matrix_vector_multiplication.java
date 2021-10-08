package ca.mcgill.ecse420.a3;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class matrix_vector_multiplication {
  private static final int NUMBER_THREADS = 16;
  private static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_THREADS);
  private static final int Threshhold = 2000 / (int) (Math.log(NUMBER_THREADS) / Math.log(4) * 2);
  private static final Random RAND = new Random();

  public static void main(String[] args) {

    // finding the execution time of a 2000x2000 mat with a 2000 wide vector 
    
    double[][] mat = randmat(2000, 2000);
    double[] vec = randvec(2000);

    double seqTimeBeggining = System.nanoTime();
    double[] seqresultult = sequentialMult(mat, vec);
    double seqTimeEnd = System.nanoTime();


    double parStart = System.nanoTime();
    double[] resultult = parallelMultiply(mat, vec, 2000);
    double parEnd = System.nanoTime();
    System.out.println("No. of Threads: " + NUMBER_THREADS);

    System.out
        .println("\nSequential time is " + (seqTimeEnd - seqTimeBeggining) / 1000000000 + "Sec");
    System.out.println("\nParallel time is " + (parEnd - parStart) / 1000000000 + "Sec");

    double difference = (seqTimeEnd - seqTimeBeggining) - (parEnd - parStart);
    if (difference > 0) {
      System.out.print("Parallel implemention is faster by : "
          + (seqTimeEnd - seqTimeBeggining) / (parEnd - parStart));
    } else {
      System.out.println("\nSequential implemention is faster by : "
          + (parEnd - parStart) / (seqTimeEnd - seqTimeBeggining));

    }
  }

  // generate mat
  private static double[][] randmat(int row, int col) {
    double[][] mat = new double[row][col];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        mat[r][c] = (double) ((int) (RAND.nextInt(10)));
      }
    }
    return mat;
  }

  // Generate vec
  private static double[] randvec(int size) {
    double[] vec = new double[size];
    for (int i = 0; i < size; i++) {
      vec[i] = (double) ((int) (RAND.nextInt(10)));
    }
    return vec;
  }

  // Sequential multiplication for the mat

  public static double[] sequentialMult(double[][] mat, double[] vec) {
    int size = 2000;
    double[] mult = new double[size];
    for (int i = 0; i < size; i++) {
      mult[i] = 0;
      for (int j = 0; j < size; j++) {
        mult[i] += mat[i][j] * vec[j];
      }
    }
    return mult;
  }


  public static double[] parallelMultiply(double[][] mat, double[] vec, int mat_size) {
    double[] result = new double[mat_size];
    Future f1 = executor.submit(new MultiplyParallel(mat, vec, result, 0, 0, 0, 0, mat_size));
    try {
      f1.get();
      executor.shutdown();
    } catch (Exception e) {

    }
    return result;
  }

  static class MultiplyParallel implements Runnable {


    private double[][] mat;
    private double[] vec;
    private double[] result;

    private int matrix_r;
    private int matrix_c;
    private int vector_r;
    private int res_r;
    private int size;



    MultiplyParallel(double[][] mat, double[] vec, double[] result, int matrix_r, int matrix_c,
        int vector_r, int res_r, int size) {
      this.mat = mat;
      this.vec = vec;
      this.result = result;

      this.matrix_r = matrix_r;
      this.matrix_c = matrix_c;
      this.vector_r = vector_r;
      this.res_r = res_r;
      this.size = size;
    }

    @Override
    public void run() {
      int mid = size / 2;
      if (size < Threshhold) {
        for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
            result[res_r + i] += mat[matrix_r + i][matrix_c + j] * vec[vector_r + j];
          }
        }
      } else {
        // Split the matrix into 4 parts
        MultiplyParallel[] mult = new MultiplyParallel[4];
        mult[0] = new MultiplyParallel(mat, vec, result, matrix_r, matrix_c, vector_r, res_r, mid);
        mult[1] = new MultiplyParallel(mat, vec, result, matrix_r, matrix_c + mid, vector_r + mid,
            res_r, mid);
        mult[2] = new MultiplyParallel(mat, vec, result, matrix_r + mid, matrix_c, vector_r,
            res_r + mid, mid);
        mult[3] = new MultiplyParallel(mat, vec, result, matrix_r + mid, matrix_c + mid,
            vector_r + mid, res_r + mid, mid);


        // Multiply the subparts
        FutureTask[] fs1 = new FutureTask[2];
        fs1[0] = new FutureTask(new MultTask(mult[0], mult[1]), null);
        fs1[1] = new FutureTask(new MultTask(mult[2], mult[3]), null);
        for (int i = 0; i < fs1.length; ++i) {
          fs1[i].run(); //Execute 
        }
      }

    }

  }
  static class MultTask implements Runnable {


    private MultiplyParallel m1, m2;

    MultTask(MultiplyParallel m1, MultiplyParallel m2) {
      this.m1 = m1;
      this.m2 = m2;
    }

    public void run() {
      m1.run();
      m2.run();
    }

  }

}
