package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.BlogDTO;
import lk.ijse.backend.entity.Blog;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.BlogRepository;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<BlogDTO> getAllBlogs() {
        return blogRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDTO getBlogById(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.map(this::convertToDTO).orElse(null);
    }

    @Override
    public BlogDTO saveBlog(BlogDTO blogDTO) {
        Blog blog = convertToEntity(blogDTO);

        // Set current date if not provided
        if (blog.getPostedDate() == null) {
            blog.setPostedDate(LocalDateTime.now());
        }

        // Set default status if not provided
        if (blog.getStatus() == null) {
            blog.setStatus("draft");
        }

        Blog saved = blogRepository.save(blog);
        return convertToDTO(saved);
    }

    @Override
    public BlogDTO updateBlog(BlogDTO blogDTO) {
        Optional<Blog> existingBlog = blogRepository.findById(blogDTO.getId());
        if (existingBlog.isPresent()) {
            Blog blog = existingBlog.get();
            blog.setTitle(blogDTO.getTitle());
            blog.setContent(blogDTO.getContent());
            blog.setCategory(blogDTO.getCategory());
            blog.setTags(blogDTO.getTags());
            blog.setStatus(blogDTO.getStatus());
            blog.setImageUrl(blogDTO.getImageUrl());

            // Update user if userId is provided and different
            if (blogDTO.getUserId() != null &&
                    (blog.getUser() == null || !blog.getUser().getId().equals(blogDTO.getUserId()))) {
                User user = userRepository.findById(blogDTO.getUserId()).orElse(null);
                blog.setUser(user);
            }

            Blog updated = blogRepository.save(blog);
            return convertToDTO(updated);
        }
        return null;
    }

    @Override
    public String deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return "Blog deleted successfully!";
        } else {
            return "Blog not found!";
        }
    }

    @Override
    public List<BlogDTO> getBlogsByCategory(String category) {
        return blogRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BlogDTO> getBlogsByStatus(String status) {
        return blogRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BlogDTO> searchBlogs(String keyword) {
        return blogRepository.searchBlogs(keyword)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // === Helpers ===
    private BlogDTO convertToDTO(Blog blog) {
        return BlogDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .postedDate(blog.getPostedDate() != null ? blog.getPostedDate().format(formatter) : null)
                .userId(blog.getUser() != null ? blog.getUser().getId() : null)
                .authorName(blog.getAuthorName())
                .category(blog.getCategory())
                .tags(blog.getTags())
                .status(blog.getStatus())
                .imageUrl(blog.getImageUrl())
                .build();
    }

    private Blog convertToEntity(BlogDTO dto) {
        User user = null;
        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId()).orElse(null);
        }

        LocalDateTime postedDate = null;
        if (dto.getPostedDate() != null) {
            postedDate = LocalDateTime.parse(dto.getPostedDate(), formatter);
        }

        return Blog.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .postedDate(postedDate)
                .user(user)
                .category(dto.getCategory())
                .tags(dto.getTags())
                .status(dto.getStatus())
                .imageUrl(dto.getImageUrl())
                .build();
    }
}