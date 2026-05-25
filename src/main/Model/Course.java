// NAMA     : MUTIARA Y.H. SIANTURI
// NIM      : 12S24045

package pbo.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author
 *     12S23003 Mutiara Y.H. Sianturi
 */

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String courseCode;

    @Column(name = "name", nullable = false)
    private String courseName;

    @Column(name = "semester", nullable = false)
    private int semester;

    @Column(name = "credits", nullable = false)
    private int credits;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();

    public Course() {
    }

    public Course(String courseCode, String courseName, int semester, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semester = semester;
        this.credits = credits;
    }

    public String getCode() {
        return courseCode;
    }

    public void setCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return courseName;
    }

    public void setName(String courseName) {
        this.courseName = courseName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return courseCode + "|" + courseName + "|" + semester + "|" + credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        return courseCode.equals(course.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }
}
