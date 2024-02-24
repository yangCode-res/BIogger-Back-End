package com.example.biogger.controller;

import com.example.biogger.dao.BlogPostRepository;
import com.example.biogger.entiy.BlogPost;
import com.example.biogger.service.BlogPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.biogger.entiy.BlogPost;
@Slf4j
@RestController
@RequestMapping("/api/blogposts")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping
    public List<BlogPost> getAllBlogPosts() {
        List<BlogPost> blogPostList = blogPostService.findAllBlogPosts();
        for (BlogPost biogger : blogPostList) {

        }
        return blogPostList;
    }

    @GetMapping("/getblogpost/{articleID}")
    public BlogPost getBlogPostById(@PathVariable int articleID) {
        return blogPostService.getBlogPostById(articleID);
    }

    //保存BlogPost
    @PostMapping("/saveblogpost")
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost) {
        System.out.println(blogPost.getTitle());
        blogPost.setCreationtime(new Date(System.currentTimeMillis()));
        blogPost.setLastupdatetime(new Date(System.currentTimeMillis()));
        BlogPost savedBlogPost = blogPostRepository.save(blogPost);
        return new ResponseEntity<>(savedBlogPost, HttpStatus.CREATED);
    }

    @GetMapping("/latest")
    public List<BlogPost> getLatestBlogPosts() {
        return blogPostService.getLatest3BlogPosts();
    }

    @GetMapping("/created-in-2024")
    public List<BlogPost> getBlogPostsCreatedIn2024() {
        return blogPostService.getBlogPostsCreatedIn2024();
    }

    @GetMapping("/count")
    public Long countPostsByCategory(@RequestParam String category) {
        return blogPostService.countPostsByCategory(category);
    }

    //根据类别查询博客
    @GetMapping("/category")
    public List<BlogPost> getBlogPostsByCategory(@RequestParam String category) {
        return blogPostService.getBlogPostsByCategory(category);
    }

    @GetMapping("/subcategories/count")
    public List<Object[]> countPostsBySubcategory() {
        return blogPostService.countPostsBySubcategory();
    }

    @GetMapping("/stats")
    public Map<String, Long> getBlogPostStats() {
        Map<String, Long> stats = blogPostService.findTotalWordsAndTotalPosts();
        return stats;

    }

    @GetMapping("/latest-update")
    public String getLatestUpdateTime() {
        return blogPostService.getLatestUpdateTime();
    }

    @GetMapping("/monthlyCounts")
    public List<MonthlyBlogCount> getMonthlyBlogCounts() {
        List<Object[]> monthlyCounts = blogPostService.countPostsByMonth();
        log.info("Monthly counts: {}", monthlyCounts);
        return monthlyCounts.stream().map(count -> {
            // 提取月份和年份，这些通常直接作为整数返回
            int month = (Integer) count[0];
            int year = (Integer) count[1];

            // 安全地处理可能是BigInteger的计数
            int postCount;
            if (count[2] instanceof BigInteger) {
                postCount = ((BigInteger) count[2]).intValue();
            } else if (count[2] instanceof Long) {
                postCount = ((Long) count[2]).intValue();
            } else {
                throw new IllegalStateException("Unexpected data type for post count: " + count[2].getClass());
            }
            return new MonthlyBlogCount(month, year, postCount);
        }).collect(Collectors.toList());
    }

    @GetMapping("/subcategory/{subcategory}")
    public List<BlogPost> getBlogPostsBySubcategory(@PathVariable String subcategory) {
        return blogPostService.getBlogPostsBySubcategory(subcategory);
    }

    // 嵌套类用于简化响应
    public static class MonthlyBlogCount {
        private int month;
        private int year;
        private int count;

        public MonthlyBlogCount(int month, int year, int count) {
            this.month = month;
            this.year = year;
            this.count = count;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
// Getter和Setter
    }

    @GetMapping("/pagePost")
    public Page<BlogPost> listBlogPosts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return blogPostService.findAllByOrderByLastUpdateTimeDesc(
                new PageRequest(page, size)
        );
    }
    @GetMapping("/pageCategory")
    public Page<BlogPost> listBlogPostsByCategory(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam String category) {
        return blogPostRepository.findBlogPostByCategoryOrderByLastupdatetime(category,
                new PageRequest(page, size)
        );
    }

    @GetMapping("/search")
    public Page<BlogPost> searchPosts(@RequestParam("keyword") String keyword,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        return blogPostService.findByKeyword(keyword, new PageRequest(page,size));
    }
}