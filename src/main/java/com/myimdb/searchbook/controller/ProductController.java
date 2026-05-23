package com.myimdb.searchbook.controller;

import com.myimdb.searchbook.model.Category;
import com.myimdb.searchbook.model.Product;
import com.myimdb.searchbook.repo.CategoryRepository;
import com.myimdb.searchbook.repo.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductController(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @GetMapping("/find-by-id/{id}")
    public Product findById(@PathVariable Long id) {
        Optional<Product> ans =  productRepository.findById(id);
        return ans.orElse(null);
    }

    @GetMapping("/find-by-title/{title}")
    public Product findByTitle(@PathVariable String title) {
        Optional<Product> ans =  productRepository.findByTitleIgnoreCase(title);
        return ans.orElse(null);
    }

    @GetMapping("/find-by-part/{title}")
    public Object findByPart(@PathVariable String title) {
        Optional<List<Product>> ans =  productRepository.findByTitleContaining(title);
        if(ans.isPresent()) {
            return ans;
        }
        else  {
            return null;
        }
    }

    @GetMapping("/find-by/{title}/{price}")
    public Object findByPriceandTitle(@PathVariable String title,
                                        @PathVariable Long price) {
        Optional<List<Product>> ans =  productRepository.findByTitleContainingAndPriceGreaterThan(title,price);
        if(ans.isPresent()) {
            System.out.println("ANS is --- "+ans.get());
            return ans;
        }
        else  {
            System.out.println("ANS is ---EMPTY()");
            return null;
        }
    }

    @PostMapping("/new")
    public Product createProduct(@RequestBody Product product)
    {
        Category category = product.getCategory();

        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());

        if (optionalCategory.isEmpty()) {
            category.setId(null);
            category = categoryRepository.save(category);
        } else {
            category = optionalCategory.get();
        }

        product.setCategory(category);
        return productRepository.save(product);
    }
}
