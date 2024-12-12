package com.company.jmixpm.app;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<User> getUsersNotInProject(Project project, int firstResult, int maxResult) {
        return entityManager.createQuery("SELECT u FROM pm_User u " +
                        "WHERE u.id NOT IN " +
                        "(SELECT u1.id FROM pm_User u1 " +
                        " INNER JOIN u1.projects pul " +
                        " WHERE pul.id = ?1)", User.class)
                .setParameter(1, project.getId())
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

}