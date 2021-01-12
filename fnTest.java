import java.io.IOException;
/**
 * Class that tests the functionality of the implementation of the mergeSort method AND calculates the median runtimes
 */
public class fnTest{
  
  /**
   * main method: Creates an instance of the Sorting class, and then computes the median runtimes for four different 
   *  array sizes AND tests the mergeSort method with an input file of integers and outputs the mergeSorted integers into an output file
   */
  public static void main(String[] args) throws IOException{
    Sorting s1 = new Sorting();
    s1.runtime();
    s1.fnTest(args[0], args[1]);
  }
}