package com.mobileapp.repositorys.ImplClass;

import com.mobileapp.entitys.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public void AddRole(Role role){
        entityManager.persist(role);
    }
}
