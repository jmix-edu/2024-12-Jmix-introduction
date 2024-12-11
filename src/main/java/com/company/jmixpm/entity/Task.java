package com.company.jmixpm.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.data.DdlGeneration;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@DdlGeneration(value = DdlGeneration.DbScriptGenerationMode.CREATE_AND_DROP)
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

    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

    @Column(name = "ESTIMATED_EFFORTS")
    private Integer estimatedEfforts;

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