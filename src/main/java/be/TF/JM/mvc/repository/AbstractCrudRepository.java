package be.TF.JM.mvc.repository;
import java.lang.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractCrudRepository<TENTITY, TID> implements CrudRepository<TENTITY, TID> {

    private final Class<TENTITY> entityClass;
    protected final EntityManager entityManager;

    protected AbstractCrudRepository(Class<TENTITY> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }


    @Override
    public void create(TENTITY entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);              //persist: l'ID est dans la db ? create : insert >< merge qui ne fait rien si ID déjà présent
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<TENTITY> getById(TID id) {
        TENTITY tentity = entityManager.find(entityClass,id);
        entityManager.clear();
        return Optional.ofNullable(tentity);

    }

    @Override
    public List<TENTITY> getAll() {
        String entityName = entityClass.getSimpleName();
        String qlQuery = String.format("SELECT e FROM %s e", entityName);
        TypedQuery<TENTITY> query = entityManager.createQuery(qlQuery,entityClass);
        List<TENTITY>  list = query.getResultList();
        entityManager.clear();

        return list;

    }


    @Override
    public void delete(TENTITY entity) {
        entityManager.getTransaction().begin();
        TENTITY managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(TID id) {

        Query query = entityManager.createQuery("DELETE ? FROM ? WHERE ?=?");


        entityManager.getTransaction().begin();
        TENTITY entity = entityManager.find(entityClass,id);
        entityManager.remove(entity);

        entityManager.getTransaction().commit();

    }

    @Override
    public void save(TENTITY entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public boolean existsById(TID id) {
        return entityManager.find(entityClass,id)==null;
    }
}
