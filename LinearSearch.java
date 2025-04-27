public class LinearSearch{

    public static void main(String[] args)
     {
        int target = 2;
       int arr[]= {1,2,3,4,5,6,7,8};
       int x = LinearSearch.LinearSearch1(arr,target);
       System.out.println(x);
       int y = LinearSearch.BinarySearch1(arr, target);
       System.out.println(y);
           }
       
           private static int LinearSearch1(int[] arr, int target) {
              
               
               for (int i=0; i<arr.length; i++)
               {
                  if(arr[i]==target)
                  {
                     return i;
                  }
                }
                return -1;
               }
               private static int BinarySearch1(int[] arr, int target) {
                
                 int left = 0;
                 int right = arr.length - 1;
                 int mid = left + right/2;
                 
                 while(left <= right)
                 {
                   
                    if(arr[mid]==target)
                    {
                        return mid;
                    }
                    else if(arr[mid]<target)
                    {
                        left = mid + 1;
                    }
                    else
                    {
                        right = mid - 1;
                    }
                 }
                 return -1;
            }
           }
