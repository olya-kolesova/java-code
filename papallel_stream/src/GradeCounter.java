import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class GradeCounter {

    record Subject(String subject, int averageGrade) {}


    public Map<String, Integer> countAverageGrades(List<Student> students) {
        return students.stream().map(Student::getGrades).flatMap(e -> e.entrySet().stream())
            .collect(groupingBy(Map.Entry::getKey, collectingAndThen(toList(), list -> {
                return list.stream().map(Map.Entry::getValue).toList();
            }))).entrySet().parallelStream().map(e -> {
                System.out.println(Thread.currentThread().getName() + " counting average for " + e.getKey());
                int average = e.getValue().stream().mapToInt(Integer::intValue).sum() / e.getValue().size();
                return new Subject(e.getKey(), average);
            }).peek(System.out::println).collect(toMap(Subject::subject, Subject::averageGrade));

    }



}
