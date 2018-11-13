import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class School {
	private String name;
	private ArrayList<Student> students;
	private ArrayList<Professor> professors;
	private ArrayList<Course> courses;
	Scanner sc = new Scanner(System.in);
	
	/*private boolean validateName(String sentence) {
		if (!sentence.matches("[a-zA-Z]+")) {
			System.out.println();
			System.out.println("Error: Student name should only contain letters, please try again!");
			return false;
		}
		return true;
	}*/
	
	/*private boolean validateNRIC(String nric) {
		if (nric.length() != 9) {
			System.out.println();
			System.out.println("Error: NRIC should contain 9 letters");
			return false;
		}
		
		if (!Character.isLetter(nric.charAt(0)) || !Character.isLetter(nric.charAt(nric.length() - 1))) {
			System.out.println();
			System.out.println("Error: NRIC should begin and and with an alphabet");
			return false;
		}
		return true;
	} */
	
	/*private boolean validateEmail(String email) {
		String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		Matcher matcher;
		matcher = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE).matcher(email);
		
		if (!matcher.matches()) {
			System.out.println();
			System.out.println("Error: Please enter a valid email address");
			return false;
		}
		
		return true;
	} */
	
	/*private boolean validateCourseCode(String code) {
		if (code.length() != 6) {
			System.out.println();
			System.out.println("Error: Course Code should contain 6 characters");
			return false;
		}
		
		if (!(Character.isLetter(code.charAt(0)) && (Character.isLetter(code.charAt(1))))) {
			System.out.println();
			System.out.println("Error: Course Code should begin with 2 alphabets");
			return false;
		}
		return true;
	} */
	
	public School (String name) {
		this.name = name;
		this.students = FileIO.retrieveExistingStudents();
		this.professors = FileIO.retrieveExistingProfessors();
		this.courses = FileIO.retrieveExistingCourses(this.professors);
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Student> getStudents() {
		return this.students;
	}
	
	public ArrayList<Course> getCourses() {
		return this.courses;
	}
	
	public ArrayList<Professor> getProfessors() {
		return this.professors; 
	}
	
	public Course getCourse() {
		int courseOption;
		this.printCourses();
		System.out.printf("Select a course from the list: (1 ~ %d)\n", this.courses.size());
		courseOption = sc.nextInt();
		sc.nextLine();
		
		if (courseOption < 1 || courseOption > this.courses.size()) {
			System.out.println();
			System.out.println("Error:  Please choose from the options in the list");
			System.out.println();
		} else {
			return courses.get(courseOption - 1);
		}

		return null;
	}
	
	public Course getCourse(String courseCode) {
		for (Course course:courses) {
			if (course.getCode().equals(courseCode)) {
				return course;
			}
		}
		return null;
	}
	
	public Student getStudent() {
		int studentOption;
		this.printStudents();
		System.out.printf("Select a student from the list: (1 ~ %d)\n", this.students.size());
		studentOption = sc.nextInt();
		sc.nextLine();
		
		if (studentOption < 1 || studentOption > this.students.size()) {
			System.out.println();
			System.out.println("Error:  Please choose from the options in the list");
			System.out.println();
		} else {
			return students.get(studentOption - 1);
		}
		
		return null;
	}
	
	public void addStudent() {
		boolean nameValidated = false, nricValidated = false, emailValidated = false;
		String ic, name, email;
		
		do {
			System.out.println();
			System.out.println("Enter the NRIC of the new student:");
			ic = sc.nextLine().toUpperCase();
			if (ValidateNric.checkValid(ic)) nricValidated = true;
		} while (!nricValidated);
		
		for (Student student:students) {
			if (student.getIc().equals(ic)) {
				System.out.println();
				System.out.println("Error: There already exists a student with the NRIC you entered!");
				System.out.println();
				return;
			}
		}	
		
		do {
			System.out.println();
			System.out.println("Enter the name of the new student:");
			name = sc.nextLine();
			if (ValidateName.checkValid(name)) nameValidated = true;
		} while (!nameValidated);
		
		do {
			System.out.println();
			System.out.println("Enter the email address of the new student:");
			email = sc.nextLine();
			if (ValidateEmail.checkValid(email)) emailValidated = true;
		} while (!emailValidated);
		
		Student newStudent = new Student(name, ic, email);
		this.students.add(newStudent);
		FileIO.writeNewStudent(newStudent);
		System.out.println();
		System.out.printf("Successfully added new student %s with assigned matriculation number: %s!\n", 
				newStudent.getName(), newStudent.getMatricNumber());
		this.printStudents();
	}
	
	public void addProfessor() {
		System.out.println();
		System.out.println("Enter the name of the new professor:");
		String name = sc.nextLine();
		
		System.out.println();
		System.out.println("Enter the email address of the new professor:");
		String email = sc.nextLine();
		
		Professor newProf = new Professor(name, email);
		this.professors.add(newProf);
		FileIO.writeNewProfessor(newProf);
		System.out.println();
		System.out.printf("Successfully added new professor: %s!\n", newProf.getName());
		System.out.println();
	}
	
	public void addCourse() {
		boolean courseCodeValidated = false;
		String courseCode, courseName;
		
		do {
			System.out.println();
			System.out.println("Enter the course code of the new course:");
			courseCode = sc.nextLine().toUpperCase();
			if (ValidateCourseCode.checkValid(courseCode)) courseCodeValidated = true;
		} while (!courseCodeValidated);
		
		for (Course course:courses) {
			if (course.getCode().equals(courseCode)) {
				System.out.println();
				System.out.println("Error: There already exists a course with the code you entered!");
				System.out.println();
				return;
			}
		}
		
		System.out.println();
		System.out.println("Enter the name of the new course:");
		courseName = sc.nextLine();
		
		for (Course course:courses) {
			if (course.getName().equals(name)) {
				System.out.println();
				System.out.println("Error: There already exists a course with the name you entered!");
				System.out.println();
				return;
			}
		}
		
		System.out.println();
		System.out.println("===============================================");
		System.out.println("| Select the type for the new course: (1 ~ 3) |");
		System.out.println("===============================================");
		System.out.println("| 1 | Lecture Only                            |");
		System.out.println("| 2 | Lecture and Tutorial Only               |");
		System.out.println("| 3 | Lecture , Tutorial and Lab              |");
		System.out.println("===============================================");
		int type = sc.nextInt();
		sc.nextLine();
		
		System.out.println();
		this.printProfessors();
		System.out.printf("Select a course coordinator from the list of professors: (1 ~ %d)\n", this.professors.size());
		int profIndex = sc.nextInt();
		sc.nextLine();
		Professor selectedProfessor = this.professors.get(profIndex - 1);
		
		Course newCourse = new Course(courseCode, courseName, type, selectedProfessor);
		this.courses.add(newCourse);
		FileIO.writeNewCourse(newCourse);
		System.out.println();
		System.out.printf("Successfully added new course: %s: %s!\n", newCourse.getCode(), newCourse.getName());
		this.printCourses();
	}
	
	public void registerStudentToCourse() {
		this.printStudentNames();
		System.out.printf("Select which student you\'d like to register: (1 ~ %d)\n", this.students.size());
		int studentOption = sc.nextInt();
		sc.nextLine();	
		Student selectedStudent = students.get(studentOption - 1);
		
		this.printCourses();
		System.out.printf("Select which course you\'d like to register %s to: (1 ~ %d)\n", selectedStudent.getName(), this.courses.size());
		int courseOption = sc.nextInt();
		sc.nextLine();
		Course selectedCourse = courses.get(courseOption - 1);
		
		selectedCourse.registerStudent(selectedStudent);
	}
	
	public void printStudents() {
		if (students.isEmpty()) {
			System.out.println();
			System.out.printf("There are currently no students enrolled into %s\n", this.name);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("=======================================================================");
			System.out.println("| No | Name                | Matric Number | Email                    |");
			System.out.println("=======================================================================");
			for (Student student: students) {
	    		System.out.printf("| %-3d| %-20s| %-14s| %-25s|\n", students.indexOf(student) + 1, 
	    				student.getName(), student.getMatricNumber(), student.getEmail());
	    	}
			System.out.println("=======================================================================");
			System.out.println();
		}
	}
	
	public void printProfessors() {
		if (professors.isEmpty()) {
			System.out.println();
			System.out.printf("There are currently no professors employed with %s\n", this.name);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("======================================================================");
			System.out.println("| No | Name                | Professor ID | Email                    |");
			System.out.println("======================================================================");
			for (Professor professor: professors) {
	    		System.out.printf("| %-3d| %-20s| %-13s| %-25s|\n", professors.indexOf(professor) + 1, 
	    				professor.getName(), professor.getId(), professor.getEmail());
	    	}
			System.out.println("======================================================================");
			System.out.println();
		}
	}
	
	public void printCourses() {
		if (courses.isEmpty()) {
			System.out.println();
			System.out.printf("There are no courses under %s\n", this.name);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("====================================================================");
			System.out.println("| No | Course Code | Course Name                                   |");
			System.out.println("====================================================================");
			for (Course course: courses) {
	    		System.out.printf("| %-3d| %-12s| %-46s|\n", courses.indexOf(course) + 1,
	    			course.getCode(), course.getName());
	    	}
			System.out.println("====================================================================");
			System.out.println();
		}
	}
	
	public void printStudentNames() {
		if (students.isEmpty()) {
			System.out.println();
			System.out.printf("There are currently no students enrolled into %s\n", this.name);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("============================");
			System.out.println("| No | Name                |");
			System.out.println("============================");
			for (Student student: students) {
	    		System.out.printf("| %-3d| %-20s|\n", students.indexOf(student) + 1, student.getName());
	    	}
			System.out.println("============================");
			System.out.println();
		}
	}
}
