public class Student implements Comparable<Student>{
	private int mAge;
	private String mName;
	
	public Student(int age, String name) {
		mAge = age;
		mName = name;
	}
	
	public int getAge() { return mAge; }
	
	public String getName() { return mName;	}
	
	public String toString() {
		return "age : " + mAge + " / name : " + mName;
	}

	@Override
	public int compareTo(Student arg0) {
		// TODO Auto-generated method stub
		int targetAge = arg0.getAge();
		if(mAge == targetAge) return 0;
		else if(mAge > targetAge) return 1;
		else return -1;
	}
}
