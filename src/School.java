import java.util.ArrayList;

public class School {
	private String name;
	private ArrayList<Student> students;
	private ArrayList<Professor> professors;
	private ArrayList<Course> courses;
	
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
		String question = String.format("Select a course from the list (1 ~ %d): ", this.courses.size());
		courseOption = Utility.readIntOption(question);
		
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
		String question = String.format("Select a student from the list: (1 ~ %d)\n", this.students.size());
		studentOption = Utility.readIntOption(question);
		
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
			ic = Utility.readStringInput("Enter the NRIC of the new student: ").toUpperCase();
			if (Validator.validateNRIC(ic)) nricValidated = true;
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
			name = Utility.readStringInput("Enter the name of the new student: ");
			if (Validator.validateName(name)) nameValidated = true;
		} while (!nameValidated);
		
		do {
			System.out.println();
			email = Utility.readStringInput("Enter the email address of the new student: ");
			if (Validator.validateEmail(email)) emailValidated = true;
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
		String name = Utility.readStringInput("Enter the name of the new professor: ");
		
		System.out.println();
		String email = Utility.readStringInput("Enter the email address of the new professor: ");
		
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
			courseCode = Utility.readStringInput("Enter the course code of the new course: ").toUpperCase();
			if (Validator.validateCourseCode(courseCode)) courseCodeValidated = true;
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
		courseName = Utility.readStringInput("Enter the name of the new course: ");
		
		for (Course course:courses) {
			if (course.getName().equals(name)) {
				System.out.println();
				System.out.println("Error: There already exists a course with the name you entered!");
				System.out.println();
				return;
			}
		}
		
		System.out.println();
		String[] courseTypeMenu = { "Lecture Only", "Lecture and Tutorial Only", "Lecture, Tutorial and Lab" };
		int type = Utility.getUserOption("Select the type for the new course: (1 ~ 3)", courseTypeMenu, false);
		
		System.out.println();
		this.printProfessors();
		String question = String.format("Select a course coordinator from the list of professors: (1 ~ %d)\n", this.professors.size());
		int profIndex = Utility.readIntOption(question);
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
		String question1 = String.format("Select which student you\'d like to register: (1 ~ %d)\n", this.students.size());
		int studentOption = Utility.readIntOption(question1);
		Student selectedStudent = students.get(studentOption - 1);
		
		this.printCourses();
		String question2 = String.format("Select which course you\'d like to register %s to: (1 ~ %d)\n", selectedStudent.getName(), this.courses.size());
		int courseOption = Utility.readIntOption(question2);
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
