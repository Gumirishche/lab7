import java.util.List;

public class Student {
    private List<Subject> subjects;
    private double average;

    public Student(List<Subject> subjects, double average) {
        this.subjects = subjects;
        this.average = average;
    }

    public Student(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Student() {
    }

    public Student(double average) {
        this.average = average;
    }

    public double averageTrue(){
        try {
            double sum=0;
            for (Subject subject : subjects) {
                sum = sum + Double.parseDouble(subject.getMark());
            }
            return sum/subjects.size();
        }catch (NullPointerException e){
            return 0;
        }
    }
}
