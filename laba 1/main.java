
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Student {
    private String name;
    private String group;
    private int course;
    private List<Integer> grades;

    public Student(String name, String group, int course, List<Integer> grades) {
        this.name = name;
        this.group = group;
        this.course = course;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public int getCourse() {
        return course;
    }

    public double getAverageGrade() {
        int total = 0;
        for (Integer grade : grades) {
            total += grade;
        }
        return total / (double) grades.size();
    }

    public void promoteToNextCourse() {
        course++;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Шухова Вероника", "A-101", 1, List.of(4, 5, 3)));
        students.add(new Student("Чухланцева Александра", "A-203", 2, List.of(2, 3, 4)));
        students.add(new Student("Иванова Екатерина", "A-101", 1, List.of(3, 4, 5)));
        students.add(new Student("Калаева Диана", "A-101", 1, List.of(3, 2, 3)));
        students.add(new Student("Безумова Елизавета", "A-301", 3, List.of(2, 2, 2)));

        removeAndPromoteStudents(students);
        printStudents(students, 2);
    }

    public static void removeAndPromoteStudents(List<Student> students) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getAverageGrade() < 3) {
                iterator.remove();
            } else {
                student.promoteToNextCourse();
            }
        }
    }

    public static void printStudents(List<Student> students, int course) {
        System.out.println("Students in course " + course + ":");
        for (Student student : students) {
            if (student.getCourse() == course) {
                System.out.println(student.getName());
            }
        }
    }
}