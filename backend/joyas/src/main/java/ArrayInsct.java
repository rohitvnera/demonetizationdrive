/**
 * Created by Avi on 7/24/16.
 */
import java.util.*;
public class ArrayInsct
{
    int size;
    int data[];
    void input()
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Size: ");
        size=sc.nextInt();
        data=new int[size];
        System.out.print("Enter Array: ");
        for(int i=0;i<size;i++)
        {
            data[i]=sc.nextInt();
        }
    }

    void show()
    {
        for(int i=0;i<size;i++)
        {
            System.out.print(data[i]);
        }
    }

    void intersect(ArrayInsct A)
    {
        for(int i=0;i<size;i++)
        {
            int c=0;
            for(int j=0;j<A.size;j++)
            {
                if(data[i]==A.data[j])
                    c++;
            }
            if(c>0)
                System.out.print(data[i]);
        }
    }
}