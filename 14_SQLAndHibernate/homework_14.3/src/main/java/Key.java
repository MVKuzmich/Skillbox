import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@EqualsAndHashCode
public class Key implements Serializable {

    public Key() {};

    public Key(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "course_id")
    private int courseId;

}
