package lk.ijse.backend.service;

import lk.ijse.backend.dto.BlogDTO;

import java.util.List;

public interface BlogService {
    List<BlogDTO> getAllBlogs();
    BlogDTO getBlogById(Long id);
    BlogDTO saveBlog(BlogDTO blogDTO);
    BlogDTO updateBlog(BlogDTO blogDTO);
    String deleteBlog(Long id);
    List<BlogDTO> getBlogsByCategory(String category);
    List<BlogDTO> getBlogsByStatus(String status);
    List<BlogDTO> searchBlogs(String keyword);
}