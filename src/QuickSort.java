import java.util.*;

public class QuickSort
{
	public static <T extends Comparable<? super T>> ArrayList<T>
		sort(ArrayList<T> al)
	{
		if(al.size() <= 1)
		{
			return al;
		}
		T pivot = al.get(al.size()-1);
		ArrayList<T> smaller = new ArrayList<T>(),
					 larger = new ArrayList<T>(),
					 same = new ArrayList<T>();
		for(T item : al)
		{
			if(item.compareTo(pivot) < 0)
			{
				smaller.add(item);
			}
			else if(item.compareTo(pivot) == 0)
			{
				same.add(item);
			}
			else if(item.compareTo(pivot) > 0)
			{
				larger.add(item);
			}
		}
		return combine(sort(smaller), same, sort(larger));
	}

	private static <T extends Comparable<? super T>> ArrayList<T>
		combine(ArrayList<T> al1, ArrayList<T> al2, ArrayList<T> al3)
	{
		ArrayList<T> combined = new ArrayList<T>();
		for(T item : al1)
		{
			combined.add(item);
		}
		for(T item : al2)
		{
			combined.add(item);
		}
		for(T item : al3)
		{
			combined.add(item);
		}
		return combined;
	}
}
