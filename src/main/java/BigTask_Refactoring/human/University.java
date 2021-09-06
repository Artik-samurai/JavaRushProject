package BigTask_Refactoring.human;

import java.util.ArrayList;
import java.util.List;

public class University {

    private List <Student> students = new ArrayList<>();
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public University(String name, int age) {
        super();
        this.age = age;
        this.name = name;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        //TODO:
        for (Student student:students){
            if (student.getAverageGrade() == averageGrade) {
                return student;
            }
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        //TODO:
        Student studentWithMaxAverageGrade = null;
        if (students.size() > 0){
            studentWithMaxAverageGrade = students.get(0);

            for (Student students : students){
                if (students.getAverageGrade() > studentWithMaxAverageGrade.getAverageGrade()){
                    studentWithMaxAverageGrade = students;
                }
            }
        }
        return studentWithMaxAverageGrade;
    }

    public Student getStudentWithMinAverageGrade() {
        //TODO:
        Student studentWithMinAverageGrade = null;
        if (students.size() > 0){
            studentWithMinAverageGrade = students.get(0);

            for (Student students : students){
                if (students.getAverageGrade() < studentWithMinAverageGrade.getAverageGrade()){
                    studentWithMinAverageGrade = students;
                }
            }
        }
        return studentWithMinAverageGrade;
    }

    public void expel(Student student){
        students.remove(student);
    }
}
