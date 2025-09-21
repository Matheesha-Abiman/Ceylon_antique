package lk.ijse.backend.repository;

import lk.ijse.backend.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByCategory(String category);
    List<Blog> findByStatus(String status);

    @Query("SELECT b FROM Blog b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    List<Blog> searchBlogs(@Param("keyword") String keyword);

    List<Blog> findByUserId(Long userId);
}