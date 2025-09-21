package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.ProductDTO;
import lk.ijse.backend.entity.Product;
import lk.ijse.backend.repository.ProductRepository;
import lk.ijse.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategoryName())
                .build();
    }

    private Product mapToEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .productName(productDTO.getProductName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .imageUrl(productDTO.getImageUrl())
                .categoryName(productDTO.getCategoryName())
                .build();
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::mapToDTO).orElse(null);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = mapToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            throw new RuntimeException("Product ID is required for update");
        }

        Optional<Product> existingProductOpt = productRepository.findById(productDTO.getId());
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setStock(productDTO.getStock());
            existingProduct.setImageUrl(productDTO.getImageUrl());
            existingProduct.setCategoryName(productDTO.getCategoryName());

            Product updatedProduct = productRepository.save(existingProduct);
            return mapToDTO(updatedProduct);
        }
        return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}