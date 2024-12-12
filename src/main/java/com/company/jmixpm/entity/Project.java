package com.company.jmixpm.entity;

import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.validation.ProjectLabelsSize;
import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "PM_PROJECT", indexes = {
        @Index(name = "IDX_PM_PROJECT_UNQ_NAME", columnList = "NAME", unique = true),
        @Index(name = "IDX_PM_PROJECT_MANAGER", columnList = "MANAGER_ID")
})
@Entity(name = "pm_Project")
public class Project {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @JoinColumn(name = "MANAGER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User manager;
    @Column(name = "DESCRIPTION")
    @Lob
    private String description;
    @Column(name = "STATUS")
    private String status;

    @Column(name = "DEFAULT_PROJECT", nullable = false)
    @NotNull
    private Boolean defaultProject = false;
    @Column(name = "DEFAULT_TASK_PRIORITY")
    private String defaultTaskPriority;
    @JoinTable(name = "PM_PROJECT_USER_LINK",
            joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<User> participants;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    //  @PropertyDatatype("projectLabels")
    //  @Convert(converter = ProjectLabelsConverter.class)
    @ProjectLabelsSize(min = 3, max = 5)
    @Column(name = "PROJECT_LABELS")
    private ProjectLabels projectLabels;
    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;
    @DeletedDate
    @Column(name = "DELETED_DATE")
    private OffsetDateTime deletedDate;
    @Column(name = "TOTAL_ESTIMATED_EFFORTS")
    private Integer totalEstimatedEfforts;

    public Integer getTotalEstimatedEfforts() {
        return totalEstimatedEfforts;
    }

    public void setTotalEstimatedEfforts(Integer totalEstimatedEfforts) {
        this.totalEstimatedEfforts = totalEstimatedEfforts;
    }

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

    public ProjectLabels getProjectLabels() {
        return projectLabels;
    }

    public void setProjectLabels(ProjectLabels projectLabels) {
        this.projectLabels = projectLabels;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public TaskPriority getDefaultTaskPriority() {
        return defaultTaskPriority == null ? null : TaskPriority.fromId(defaultTaskPriority);
    }

    public void setDefaultTaskPriority(TaskPriority defaultTaskPriority) {
        this.defaultTaskPriority = defaultTaskPriority == null ? null : defaultTaskPriority.getId();
    }

    public Boolean getDefaultProject() {
        return defaultProject;
    }

    public void setDefaultProject(Boolean defaultProject) {
        this.defaultProject = defaultProject;
    }

    public ProjectStatus getStatus() {
        return status == null ? null : ProjectStatus.fromId(status);
    }

    public void setStatus(ProjectStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @PostConstruct
    public void postConstruct() {
        setDefaultTaskPriority(TaskPriority.MIDDLE);
    }
}