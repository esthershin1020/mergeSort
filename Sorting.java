import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * Class that sorts an array of integers from smallest to largest through mergeSort
 * @author Esther Shin 
 */
public class Sorting{
  
  /**
   * Method that sorts an integer array in the increasing order of elements 
   * @param arr  the input integer array that will be sorted
   */
  public static void mergeSort(int[] arr){
    /**
     * subArraySize: stores the size of the subarrays to be merged
     */
    int subArraySize;
    
    /**
     * iterationNumber: stores the number of merging phases that have occurred 
     */
    int iterationNumber = 0;
    
    /** 
     * tempArray: stores the temporary array used for merging
     */
    int[] tempArray = new int[arr.length];
    
    /**
     * Starts at a subarray size of 1, and increases the subarray size by a power of 2 after each merging phase until 
     *  everything is sorted 
     */
    for(subArraySize = 1; subArraySize < arr.length; subArraySize = subArraySize*2){ 
      /**
       * Goes through each left and right pairs of subarrays and sorts/merges them into a sorted array
       */
      for(int index = 0; index < arr.length; index = index + subArraySize*2){
        /**
         * leftStart: stores the left-most (first) element in the left subarray 
         */
        int leftStart;
        
        /**
         * rightStart: stores the left-most (first) element in the right subarray
         */
        int rightStart; 
        
        /**
         * leftEnd: stores the right-most (last) element in the left subarray 
         */
        int leftEnd;
        
        /**
         * rightEnd: stores the right-most (last) element in the right subarray 
         */
        int rightEnd;
        
        leftStart = index;
        
        rightStart = index + subArraySize;
        
        if((leftStart + subArraySize - 1) < (arr.length - 1)){
          leftEnd = leftStart + subArraySize - 1;
        }
        else{
          leftEnd = arr.length - 1;
        }
        
        if((index + 2 * subArraySize - 1) < (arr.length - 1)){
          rightEnd = index + 2 * subArraySize - 1;
        }
        else{
          rightEnd = arr.length - 1;
        }
        
        if(iterationNumber % 2 == 0){
          merge(arr, tempArray, leftStart, leftEnd, rightStart, rightEnd);
        }
        else{
          merge(tempArray, arr, leftStart, leftEnd, rightStart, rightEnd);
        }
      }
      iterationNumber = iterationNumber + 1; 
    }
  }
  
  /**
   * Helper method used in mergeSort that merges two subarrays while sorting them in the process
   * @param arr  the array to be sorted 
   * @param temp  the temporary array into which the subarrays are to be sorted/merged
   * @param leftStart  the first element in the left subarray 
   * @param leftEnd  the last element in the left subarray 
   * @param rightStart  the first element in the right subarray 
   * @param rightEnd  the last element in the right subarray 
   */
  public static void merge(int[] arr, int[] temp, int leftStart, int leftEnd, int rightStart, int rightEnd){
    /**
     * i: stores the index of the element being compared in the left subarray pair
     */
    int i = leftStart;
    
    /**
     * j: stores the index of the element being compared in the right subarray pair
     */
    int j = rightStart;
    
    /**
     * k: stores the index into the temporary array
     */
    int k = leftStart;
    
    /**
     * As long as i is in the left subarray and j is in the right subarray, and therefore there are still elements to be 
     *  sorted through and compared, merge the left and right subarrays 
     */
    while(i <= leftEnd && j <= rightEnd){
      if(arr[i] <= arr[j]){
        temp[k] = arr[i];
        i = i + 1;
      }
      else{
        temp[k] = arr[j];
        j = j + 1;
      }
      k = k + 1;
    }
    /**
     * Copies any remaining elements of the left subarray (if there are any) into the temporary array 
     */
    while(i <= leftEnd){
      temp[k] = arr[i];
      i = i + 1;
      k = k + 1;
    }
    
    /**
     * Copies any remaining elements of the right subarray (if there are any) into the temporary array
     */
    while(j <= rightEnd){
      temp[k] = arr[j];
      j = j + 1;
      k = k + 1;
    }
    
    /**
     * Copies everything in the temporary array into the original array 
     */
    for(int index = leftStart; index <= rightEnd; index = index + 1){
      arr[index] = temp[index];
    }
  }
  
  /**
   * Helper method used in runtime that makes an integer array of random numbers
   * @param arraySize  the size of the array that is to be mergeSorted
   */
  public int[] arrayMaker(int arraySize){
    Random randNums = new Random();
    int[] randArray = new int[arraySize];
    for(int index = 0; index < randArray.length; index = index + 1){
      randArray[index] = randNums.nextInt() + 1;
    }
    return randArray;
  }
  
  /**
   * Method that computes the runtime of mergeSort three times for each different array size, find the median runtime for each 
   *  array size, and prints the median runtimes into an output file 
   */
  public void runtime() throws IOException{
    /**
     * diffArrLengths: stores the different array lengths to be tested 
     */
    int[] diffArrLengths = new int[]{250000, 500000, 1000000, 1250000};
    
    /**
     * medians: stores the median runtime for each array size 
     */
    long[] medians = new long[4];
    /**
     * Computes runtime for each different array size
     */
    for(int index = 0; index < diffArrLengths.length; index = index + 1){
      
      /** 
       * runtimes: stores the runtime of each trial for each array size 
       *  For example, stores the runtime for every one of the 3 tests on an array size 250000
       */
      long[] runtimes = new long[3];
      
      /**
       * Tests and computes the runtime 3 different times for each different array size
       */
      for(int iterationNum = 0; iterationNum <= 2; iterationNum = iterationNum + 1){
        
        /**
         * randArray: stores the array of random integers to be mergeSorted
         */
        int[] randArray = arrayMaker(diffArrLengths[index]);
        
        /**
         * startTime: stores the start time of the running time 
         */
        long startTime = System.nanoTime();
        mergeSort(randArray);
        
        /**
         * estimatedTime: stores the overall estimated running time
         */
        long estimatedTime = System.nanoTime() - startTime;
        runtimes[iterationNum] = estimatedTime;
      }
      /**
       * stores the median of every runtime triples (the median runtime for each subarray size)
       */
      long middleNum = median(runtimes);
      medians[index] = middleNum;
    }
    /**
     * writer2: writes the median runtime for each different array size in an output file called "medianRuntimes.txt"
     */
    BufferedWriter writer2 = new BufferedWriter(new FileWriter("medianRuntimes.txt"));
    
    /**
     * Goes through medians array and writes each median into a file
     */
    for(int index = 0; index < medians.length; index = index + 1){
      writer2.write("The estimated runtime median for array size " + diffArrLengths[index] + " is: " + medians[index] + "\n");
    }
    writer2.close();
  }
  
  /**
   * Helper method used in runtime in order to find the median of the 3 runtimes per different array size 
   * @param runtimes  the runtimes from which the median is to be found
   */
  public long median(long[] runtimes){
    /**
     * middeNum: stores the median 
     */
    long middleNum = 0;
    if((runtimes[0] < runtimes[1] && runtimes[0] < runtimes[2]) && (runtimes[2] > runtimes[1] && runtimes[2] > runtimes[0])){
      middleNum = runtimes[1];
    }
    if((runtimes[0] < runtimes[1] && runtimes[0] < runtimes[2]) && (runtimes[1] > runtimes[0] && runtimes[1] > runtimes[2])){
      middleNum = runtimes[2];
    }
    if((runtimes[1] < runtimes[0] && runtimes[1] < runtimes[2]) && (runtimes[2] > runtimes[0] && runtimes[2] > runtimes[1])){
      middleNum = runtimes[0];
    }
    if((runtimes[2] < runtimes[0] && runtimes[2] < runtimes[1]) && (runtimes[1] > runtimes[0] && runtimes[1] > runtimes[2])){
      middleNum = runtimes[0];
    }
    if((runtimes[1] < runtimes[2] && runtimes[1] < runtimes[0]) && (runtimes[0] > runtimes[1] && runtimes[0] > runtimes[2])){
      middleNum = runtimes[2];
    }
    if((runtimes[2] < runtimes[1] && runtimes[2] < runtimes[0]) && (runtimes[0] > runtimes[1] && runtimes[0] > runtimes[2])){
       middleNum = runtimes[1];
    }
    return middleNum;
  }

  /**
   * Method that reads the input file into a String (which is then converted into an array), then invokes the mergeSort 
   *  method and writes out the sorted array into the output file 
   */
  public void fnTest(String input_file_name, String output_file_name) throws IOException{
    /**
     * inputTestFile: the input file to be sorted 
     */
    String inputTestFile = Files.lines(Paths.get(input_file_name), StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator()));
    
    /**
     * inputOriginal: stores the array of Strings from the inputTestFile after splitting (since the split() function returns an array)
     */
    String[] inputOriginal;
    inputOriginal = inputTestFile.split("\\n");
    
    /**
     * inputInt: stores the array of integers to be mergeSorted
     */
    int[] inputInt = new int[inputOriginal.length];
    
    /**
     * Goes through the array of Strings and converts them into an array of integers so that the array of integers can now be sorted
     */
    for(int index = 0; index < inputTestFile.split("\\n").length; index = index + 1){
      inputInt[index] = Integer.parseInt(inputOriginal[index]);
    }
    mergeSort(inputInt);
    
    /**
     * writer: writes the sorted array into the output file 
     */
    BufferedWriter writer = new BufferedWriter(new FileWriter(output_file_name));
    
    /**
     * Goes through the array of sorted integers and writes each integer into the output file
     */
    for(int index = 0; index < inputInt.length; index = index + 1){
      writer.write(inputInt[index] + "\n");
    }
    writer.close();
  }
}