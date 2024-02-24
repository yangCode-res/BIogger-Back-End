package com.example.biogger.dao;

import com.example.biogger.entiy.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
    BlogPost findBlogPostByArticleID(int articleID);
    @Query(value = "SELECT b FROM BlogPost b ORDER BY b.lastupdatetime DESC")
    List<BlogPost> findTop3ByLastUpdateTime(Pageable pageable);
    @Query("SELECT b FROM BlogPost b WHERE b.creationtime BETWEEN :startOfYear AND :endOfYear")
    List<BlogPost> findBlogPostsCreatedIn2024(@Param("startOfYear") Date startOfYear, @Param("endOfYear") Date endOfYear);
    @Query("SELECT COUNT(b) FROM BlogPost b WHERE b.category = :category")
    Long countByCategory(@Param("category") String category);
    List<BlogPost> findBlogPostByCategory(String category);
    @Query("SELECT b.subcategory, COUNT(b) FROM BlogPost b GROUP BY b.subcategory")
    List<Object[]> countPostsBySubcategory();
    @Query("SELECT SUM(b.wordcount) AS totalWords FROM BlogPost b")
    Long findTotalWords();
    @Query("SELECT COUNT(b) AS totalPosts FROM BlogPost b")
    Long findTotalPosts();
    @Query("SELECT MAX(b.lastupdatetime) FROM BlogPost b")
    Date findLatestUpdateTime();
    @Query(value = "SELECT MONTH(b.CreationTime) AS month, YEAR(b.CreationTime) AS year, COUNT(*) FROM blogposts b GROUP BY year, month ORDER BY year DESC, month DESC", nativeQuery = true)
    List<Object[]> countPostsByMonth();
    //根据子类别查找post
    List<BlogPost> findBlogPostsBySubcategory(String subcategory);

    @Query(value = "SELECT b FROM BlogPost b ORDER BY b.lastupdatetime DESC")
    Page<BlogPost> findAllBlogPostsByLastUpdateTime(Pageable pageable);
    Page<BlogPost> findBlogPostByCategoryOrderByLastupdatetime(String category, Pageable pageable);
    @Query("SELECT bp FROM BlogPost bp WHERE bp.title LIKE %:keyword% OR bp.summary LIKE %:keyword% OR bp.content LIKE %:keyword%")
    Page<BlogPost> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
