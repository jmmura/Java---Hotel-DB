package be.TF.JM.mvc.repository.impl;

import be.TF.JM.mvc.models.entity.Reservation;
import be.TF.JM.mvc.repository.AbstractCrudRepository;
import be.TF.JM.mvc.repository.ReservationRepository;
import be.TF.JM.mvc.utils.EMFSharer;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.lang.*;

@Repository
public class ReservationRepositoryImpl extends AbstractCrudRepository<Reservation,Long> implements ReservationRepository{


    public ReservationRepositoryImpl(EMFSharer emfSharer) {
        super(Reservation.class, emfSharer.createEntityManager());
    }

    @Override
    public List<Reservation> getFromMonth(Month month, int year) {

        LocalDateTime premierDuMois = LocalDateTime.of(year, month.getValue(), 1, 0,0,0);
        LocalDateTime dernierDuMois = LocalDateTime.of(year, month.getValue(), 31, 23,59,59);
//
//        String qlQuer2 = "SELECT r FROM Reservation r WHERE r.start >= ?1 and r. <= ?2 ";
//        String qlQuer3 = "SELECT r FROM Reservation r WHERE r.start BETWEEN ?1 AND ?2 ";
//        TypedQuery<Reservation> typedQuery2 = entityManager.createQuery(qlQuer3,Reservation.class);
//        typedQuery2.setParameter(1,premierDuMois);
//        typedQuery2.setParameter(2,dernierDuMois);
//        //Set<Reservation> set2 = typedQuery2.getResultStream().collect(Collectors.toSet());
//        List<Reservation>  liste = typedQuery2.getResultList();


        String qlQuery = String.format(
                "SELECT r  FROM Reservation r WHERE extract(month from r.start) = extract(month from r.end) AND extract(month from r.start) = %d AND extract(year from r.start) = extract(year from r.end) AND extract(year from r.start) = %d ",month.getValue(),year);
        String qlQuery2 =
                "SELECT r  FROM Reservation r WHERE r.start < ?1 AND r.end > ?2";

        TypedQuery<Reservation> query = entityManager.createQuery(qlQuery2,Reservation.class);
        query.setParameter(1,dernierDuMois);
        query.setParameter(2,premierDuMois);
        //Set<Reservation> set = query.getResultStream().collect(Collectors.toSet());
        List<Reservation> liste = query.getResultList();

        entityManager.clear();

        //return set2;
        return liste;
    }

    @Override
    public int getBreakfastNeededForDay(LocalDate date) {
        String qlQuery = "SELECT COUNT(r)  FROM Reservation r  WHERE r.breakfastIncluded = ?2 AND ?1 BETWEEN r.start AND r.end  ";
        TypedQuery<Integer> countQuery = entityManager.createQuery(qlQuery,Integer.class);
        countQuery.setParameter(1,date);
        countQuery.setParameter(2,true);
        int  count = countQuery.getSingleResult();

        entityManager.clear();

        return count;
    }

    @Override
    public Set<Reservation> getFutureReservation() {
        String qlQuery = "  SELECT r   FROM Reservation r  WHERE r.start>now()  ";

        TypedQuery<Reservation> query = entityManager.createQuery(qlQuery,Reservation.class);
        Set<Reservation> set = query.getResultStream().collect(Collectors.toSet());
        entityManager.clear();

        return set;

    }

    @Override
    public Set<Reservation> getFutureShortReservation() {
        String qlQuery = "     SELECT r   FROM Reservation r  WHERE r.start>now()  AND r.end-r.start<7 ";

        TypedQuery<Reservation> query = entityManager.createQuery(qlQuery,Reservation.class);
        Set<Reservation> set = query.getResultStream().collect(Collectors.toSet());
        entityManager.clear();

        return set;
    }

    @Override
    public Set<Reservation> getFutureLongReservation() {
        String qlQuery = "  SELECT r     FROM Reservation r    WHERE r.start>now()      AND r.end-r.start>=7 ";

        TypedQuery<Reservation> query = entityManager.createQuery(qlQuery,Reservation.class);
        Set<Reservation> set = query.getResultStream().collect(Collectors.toSet());
        entityManager.clear();

        return set;
    }
}
