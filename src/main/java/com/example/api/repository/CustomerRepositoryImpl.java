package com.example.api.repository;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
@Transactional(readOnly = true)
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<Customer> findByFilter(Pageable pageable, String name, String email, String gender) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> iRoot = cq.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(name)) {
            predicates.add(cb.like(iRoot.get("name"), "%" + name + "%"));
        }

        if (nonNull(email)) {
            predicates.add(cb.like(iRoot.get("email"), "%" + email + "%"));
        }

        if (nonNull(gender)) {
            predicates.add(cb.equal(iRoot.get("gender"), gender));
        }

        cq.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<Customer> query = entityManager.createQuery(cq);
        return new PageImpl<>(query.getResultList(), pageable, query.getResultList().size());
    }

    @Override
    public Page<Customer> findByCidadeEstado(Pageable pageable, String uf, String cidade) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> iRoot = cq.from(Customer.class);
        Join<Customer, Address> address = iRoot.join("addresses", JoinType.INNER);
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(uf)) {
            predicates.add(cb.equal(address.get("uf"), uf));
        }

        if (nonNull(cidade)) {
            predicates.add(cb.equal(address.get("localidade"), cidade));
        }

        cq.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<Customer> query = entityManager.createQuery(cq);
        return new PageImpl<>(query.getResultList(), pageable, query.getResultList().size());
    }
}
