package be.TF.JM.mvc.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrudRepository<TENTITY, TID> { //paramètres de généricité


    // Create
    void create(TENTITY toInsert);

    // Read
    Optional<TENTITY> getById(TID id);          //Optional pour pouvoir gérer le cas où la méthode ne renvoit rien
    List<TENTITY> getAll();

    // Update
    void save (TENTITY entity);

    // Delete
    void delete(TENTITY entity);
    void deleteById(TID id);


    boolean existsById(TID id);


}
