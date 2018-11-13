import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileIO {
	public static ArrayList<Student> retrieveExistingStudents() {
		ArrayList<Student> students = new ArrayList<Student>();
		String fileLine;
		String file = "src/data/Students.txt";
		try {
			FileReader frStream = new FileReader(file);
			BufferedReader brStream = new BufferedReader(frStream);
			while ((fileLine = brStream.readLine()) != null) {
				String[] studentData = fileLine.split(";");
				String studName = studentData[0].trim();
				String studIc = studentData[1].trim();
				String studMatric = studentData[2].trim();
				String studEmail = studentData[3].trim();
				Student student = new Student(studName, studIc, studMatric, studEmail);
				students.add(student);
			}
			brStream.close();
		} catch (FileNotFoundException e){
			System.out.println("File Error: " + e.getMessage());
			File newFile = new File(file);
			try {
				newFile.createNewFile();
			} catch (IOException e1) {
				System.out.println("IO Error: " + e1.getMessage());
			}
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getMessage());
		}
		return students;	
	}
	
	public static void writeNewStudent(Student newStudent) {
		String file = "src/data/Students.txt";
		String studName = String.format("%-16s", newStudent.getName());
		String studIc = String.format("%-11s", newStudent.getIc());
		String studMatric = String.format("%-11s", newStudent.getMatricNumber());
		String studEmail = newStudent.getEmail();
		String studentData = studName + ";" + studIc + ";" + studMatric + ";" + studEmail;
		try {
			FileWriter			fwStream = new FileWriter(file, true);
			BufferedWriter		bwStream = new BufferedWriter(fwStream);
			PrintWriter			pwStream = new PrintWriter(bwStream);
			pwStream.println(studentData);
			pwStream.close();
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getMessage());
		}
	}
	
	public static ArrayList<Course> retrieveExistingCourses(ArrayList<Professor> professors) {
		ArrayList<Course> courses = new ArrayList<Course>();
		String fileLine;
		String file = "src/data/Courses.txt";
		Professor selectedProfessor = null;
		
		try {
			FileReader 		frStream = new FileReader(file);
			BufferedReader 	brStream = new BufferedReader(frStream);
			while ((fileLine = brStream.readLine()) != null) {
				String[] courseData = fileLine.split(";");
				String courseCode = courseData[0].trim();
				String courseName = courseData[1].trim();
				int courseType = Integer.parseInt(courseData[2].trim());
				String courseCoordinator = courseData[3].trim();
				int numTutLabGroups = Integer.parseInt(courseData[4].trim());
				
				for (Professor professor:professors) {
					if (courseCoordinator.equals(professor.getName())) {
						selectedProfessor = professor;
					}
				}
				
				Course student = new Course(courseCode, courseName, courseType, selectedProfessor, numTutLabGroups);
				courses.add(student);
			}
			brStream.close();
		} catch (FileNotFoundException e){
			System.out.println("Error reading the file: " + e.getMessage());
			File newFile = new File(file);
			try {
				newFile.createNewFile();
			} catch (IOException e1) {
				System.out.println("IO Error: " + e1.getMessage());
			}
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getMessage());
		}
		return courses;
	}
	
	public static void writeNewCourse(Course newCourse) {
		String file = "src/data/Courses.txt";
		String courseCode 	= String.format("%-8s", newCourse.getCode());
		String courseName 	= String.format("%-43s", newCourse.getName());
		String courseType 	= String.format("%-3d", newCourse.getType());
		String profName 	= String.format("%-19s", newCourse.getCourseCoordinator().getName());
		String numGroups 	= String.format("%s", newCourse.getNumGroups());

		String courseData = courseCode + ";" + courseName + ";" + courseType + ";" + profName + ";" + numGroups;
		try {
			FileWriter			fwStream = new FileWriter(file, true);
			BufferedWriter		bwStream = new BufferedWriter(fwStream);
			PrintWriter			pwStream = new PrintWriter(bwStream);
			pwStream.println(courseData);
			pwStream.close();
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getMessage());
		}
	}
	
	public static ArrayList<Professor> retrieveExistingProfessors() {
		ArrayList<Professor> professors = new ArrayList<Professor>();
		String fileLine;
		String file = "src/data/Professors.txt";
		try {
			FileReader frStream = new FileReader(file);
			BufferedReader brStream = new BufferedReader(frStream);
			while ((fileLine = brStream.readLine()) != null) {
				String[] profData = fileLine.split(";");
				String profName = profData[0];
				String profId = profData[1];
				String profEmail = profData[2];
				Professor professor = new Professor(profName, profId, profEmail);
				professors.add(professor);
			}
			brStream.close();
		} catch (FileNotFoundException e){
			System.out.println("File Error: " + e.getMessage());
			File newFile = new File(file);
			try {
				newFile.createNewFile();
			} catch (IOException e1) {
				System.out.println("IO Error: " + e1.getMessage());
			}
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getMessage());
		}
		return professors;	
	}
	
	public static void writeNewProfessor(Professor newProfessor) {
		String file = "src/data/Professors.txt";
		String profName = newProfessor.getName();
		String profId = newProfessor.getId();
		String profEmail = newProfessor.getEmail();
		String profData = profName + ";" + profId + ";" + profEmail;
		try {
			FileWriter			fwStream = new FileWriter(file, true);
			BufferedWriter		bwStream = new BufferedWriter(fwStream);
			PrintWriter			pwStream = new PrintWriter(bwStream);
			pwStream.println(profData);
			pwStream.close();
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getMessage());
		}
	}
}
