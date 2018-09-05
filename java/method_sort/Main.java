import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Student> students = new ArrayList<>();
		students.add(new Student(16, "Jack"));
		students.add(new Student(12, "Merry"));
		students.add(new Student(18, "Simuel"));

		System.out.println("정렬 전");

		for (int i = 0; i < students.size(); i++)
			System.out.println(students.get(i).toString());

		// 오름차순 정렬
		students.sort(Comparator.naturalOrder());
		/*students.sort(new Comparator<Student>() {
			@Override
			public int compare(Student arg0, Student arg1) {
				// TODO Auto-generated method stub
				int age0 = arg0.getAge();
				int age1 = arg1.getAge();
				if (age0 == age1)
					return 0;
				else if (age0 > age1)
					return 1;
				else
					return -1;
			}
		});*/

		System.out.println("오름차순 정렬");

		for (int i = 0; i < students.size(); i++)
			System.out.println(students.get(i).toString());

		// 내림차순 정렬
		students.sort(Comparator.reverseOrder());
		/*students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student arg0, Student arg1) {
                   // TODO Auto-generated method stub
                   int age0 = arg0.getAge();
                   int age1 = arg1.getAge();
                   if (age0 == age1)
                         return 0;
                   else if (age1 > age0)
                         return 1;
                   else
                         return -1;
            }
		});*/

		System.out.println("내림차순 정렬");

		for (int i = 0; i < students.size(); i++)
			System.out.println(students.get(i).toString());

	}
}