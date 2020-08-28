package com.sidnev.springboot.dao;

import com.sidnev.springboot.model.Role;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager ;

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void deleteRole(Role role) {
        entityManager.remove(entityManager.contains(role) ? role : entityManager.merge(role));
    }

    @Override
    public void updateRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role getRoleById(int id) {
        Query query = entityManager.createQuery("FROM Role r WHERE r.id = ?1");
        query.setParameter(1, id);
        return (Role) query.getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    public Role getRoleByName(String name) {
        Query query = entityManager.createQuery("FROM Role r WHERE r.role = ?1");
        query.setParameter(1, name);
        return (Role) query.getSingleResult();
    }
}

