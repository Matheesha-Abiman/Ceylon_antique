package lk.ijse.backend.controller;

import lk.ijse.backend.dto.BlogDTO;
import lk.ijse.backend.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/getblogs")
    public ResponseEntity<List<BlogDTO>> getBlogs() {
        try {
            List<BlogDTO> blogs = blogService.getAllBlogs();
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getblog/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
        try {
            BlogDTO blog = blogService.getBlogById(id);
            if (blog != null) {
                return new ResponseEntity<>(blog, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addblog")
    public ResponseEntity<BlogDTO> saveBlog(@RequestBody BlogDTO blogDTO) {
        try {
            BlogDTO savedBlog = blogService.saveBlog(blogDTO);
            return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateblog/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable Long id, @RequestBody BlogDTO blogDTO) {
        try {
            blogDTO.setId(id);
            BlogDTO updatedBlog = blogService.updateBlog(blogDTO);
            if (updatedBlog != null) {
                return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteblog/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        try {
            String result = blogService.deleteBlog(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}