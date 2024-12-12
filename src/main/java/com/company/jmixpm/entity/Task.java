package com.company.jmixpm.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "PM_TASK", indexes = {
        @Index(name = "IDX_PM_TASK_ASSIGNEE", columnList = "ASSIGNEE_ID"),
        @Index(name = "IDX_PM_TASK_PROJECT", columnList = "PROJECT_ID")
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_PM_TASK_UNQ_NAME", columnNames = {"NAME"})
})
@Entity(name = "pm_Task")
public class Task {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    //    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @FutureOrPresent
    @Column(name = "DUE_DATE")
    private LocalDateTime dueDate;

    @JoinColumn(name = "ASSIGNEE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User assignee;

    @Column(name = "PRIORITY")
    private String priority;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

    @Positive(message = "{msg://com.company.jmixpm.entity/Task.estimatedEfforts.validation.Positive}")
    @Column(name = "ESTIMATED_EFFORTS")
    private Integer estimatedEfforts;
    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;
    @DeletedDate
    @Column(name = "DELETED_DATE")
    private OffsetDateTime deletedDate;

    public OffsetDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(OffsetDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Integer getEstimatedEfforts() {
        return estimatedEfforts;
    }

    public void setEstimatedEfforts(Integer estimatedEfforts) {
        this.estimatedEfforts = estimatedEfforts;
    }

    public TaskPriority getPriority() {
        return priority == null ? null : TaskPriority.fromId(priority);
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority == null ? null : priority.getId();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}