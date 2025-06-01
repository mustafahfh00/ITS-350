import java.util.Arrays;

public class MergeSort
{

    public static void mergeSort (int arr[])
    {
        //divide
        if(arr.length <= 2)
        return;
        int mid = arr.length/2;
        int leftPart[]= new int[mid];
        int rightPart[]= new int[arr.length-mid];
        for (int i=0; i<mid; i++){
            leftPart[i] = arr[i];
        }
        for (int j=mid; j<arr.length; j++)
        {
            rightPart[j-mid] = arr[j];
        }
        mergeSort(leftPart);
        mergeSort(rightPart);
        //merge
        merge(arr, leftPart, rightPart);

    

    
    }
    public static void merge (int sortedA[], int left[], int right[])
    {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length)
        {
            if (left[i] <= right[j])
            {
                sortedA[k] = left[i];
                i++;
            }
            else
            {
                sortedA[k] = right[j];
                j++;
            }
            k++;
        }
        while (i < left.length)
        {
            sortedA[k] = left[i];
            i++;
            k++;
        }
        while (j < right.length)
        {
            sortedA[k] = right[j];
            j++;
            k++;
        }
    }
    public static void main (String[] args)
    {
        int arr[] = {12, 11, 13, 5, 6, 7};
        System.out.println("Before: " + Arrays.toString(arr));
       mergeSort(arr);
        
        System.out.println("After: " + Arrays.toString(arr));
        
    }
}