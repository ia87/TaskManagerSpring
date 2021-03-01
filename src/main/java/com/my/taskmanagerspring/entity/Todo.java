package com.my.taskmanagerspring.entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author Ramesh Fadatare
 *
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue    (strategy=GenerationType.SEQUENCE,
            generator="todo_seq")
    @SequenceGenerator (name="todos_seq",
            sequenceName="SEQ_TODO",
            allocationSize=5)
    private long id;

    @Size(min = 5, message = "Enter at least 5 Characters...")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime started;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime finished;

    public Todo() {
        super();
    }

    @ManyToOne(fetch=FetchType.LAZY
//            ,cascade=CascadeType.ALL
    )
    @JoinColumn (name="user_id")
    @ToString.Exclude
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    public Todo(@Size(min = 10, message = "Enter at least 5 Characters...") String description, LocalDate targetDate, User user) {
        this.description = description;
        this.targetDate = targetDate;
        this.user = user;
    }
}