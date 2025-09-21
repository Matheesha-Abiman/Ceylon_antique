package lk.ijse.backend.repository;

import lk.ijse.backend.entity.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertiseRepository extends JpaRepository<Advertise, Long> {
    List<Advertise> findByUserId(Long userId);

    @Query("SELECT a FROM Advertise a WHERE a.status = :status")
    List<Advertise> findByStatus(@Param("status") String status);

    @Query("SELECT a FROM Advertise a WHERE a.type = :type")
    List<Advertise> findByType(@Param("type") String type);

    List<Advertise> findByTitleContainingIgnoreCase(String title);
}