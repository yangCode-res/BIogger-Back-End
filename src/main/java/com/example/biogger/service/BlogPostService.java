package com.example.biogger.service;

import com.example.biogger.dao.BlogPostRepository;
import com.example.biogger.entiy.BlogPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.PageRequest;
@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    public List<BlogPost> findAllBlogPosts() {
         return blogPostRepository.findAll();
    }
    //根据articleID获取
    public BlogPost getBlogPostById(int articleID) {
        return blogPostRepository.findBlogPostByArticleID(articleID);
    }
    //保存BlogPost
    public BlogPost saveBlogPost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }
    public List<BlogPost> getLatest3BlogPosts() {
        Pageable pageable = new PageRequest(0, 3); // 这里使用PageRequest的构造方法
        return blogPostRepository.findTop3ByLastUpdateTime(pageable);
    }
    public List<BlogPost> getBlogPostsCreatedIn2024() {
        LocalDateTime startOfYear = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(2024, 12, 31, 23, 59, 59);

        Date start = convertToDateViaSqlTimestamp(startOfYear);
        Date end = convertToDateViaSqlTimestamp(endOfYear);

        return blogPostRepository.findBlogPostsCreatedIn2024(start, end);
    }

    private Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
    public Long countPostsByCategory(String category) {
        return blogPostRepository.countByCategory(category);
    }
    //根据类别查询博客
    public List<BlogPost> getBlogPostsByCategory(String category) {
        return blogPostRepository.findBlogPostByCategory(category);
    }
    public List<Object[]> countPostsBySubcategory() {
        return blogPostRepository.countPostsBySubcategory();
    }
    public Map<String, Long> findTotalWordsAndTotalPosts() {
            Long result = blogPostRepository.findTotalWords();
            long totalPostsLong = blogPostRepository.findTotalPosts();
            Map<String, Long> statsMap = new HashMap<>();
            statsMap.put("totalWords", result);
            statsMap.put("totalPosts", totalPostsLong);
            // 现在你可以使用 totalWordsLong 和 totalPostsLong 了
            return statsMap;

    }
    public String getLatestUpdateTime() {
        Date latestUpdateTime = blogPostRepository.findLatestUpdateTime();
        if (latestUpdateTime == null) {
            return "无更新";
        }
        // 计算差异
        long diffInMillies = Math.abs(new Date().getTime() - latestUpdateTime.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return String.format("%d天前", diffInDays);
    }
    public List<Object[]> countPostsByMonth() {
        return blogPostRepository.countPostsByMonth();
    }
    //根据子类别查找
    public List<BlogPost> getBlogPostsBySubcategory(String subcategory) {
        return blogPostRepository.findBlogPostsBySubcategory(subcategory);
    }
    public Page<BlogPost> findAllByOrderByLastUpdateTimeDesc(Pageable pageable) {
        return blogPostRepository.findAllBlogPostsByLastUpdateTime(pageable);
    }
    public Page<BlogPost> findBlogPostByCategoryOrderByLastupdatetime(String subcategory, Pageable pageable) {
        return blogPostRepository.findBlogPostByCategoryOrderByLastupdatetime(subcategory, pageable);
    }
    public Page<BlogPost> findByKeyword(String keyword, Pageable pageable) {
        return blogPostRepository.findByKeyword(keyword, pageable);
    }
}
