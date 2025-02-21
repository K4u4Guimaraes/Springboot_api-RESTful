package com.example.learnSpring.controllers.productC;

import com.example.learnSpring.dtos.productDto.ProductRecordDto;
import com.example.learnSpring.handlers.exceptions.ProductNullPointerException;
import com.example.learnSpring.handlers.exceptions.ProductPriceException;
import com.example.learnSpring.models.product.ProductModel;
import com.example.learnSpring.repositories.productR.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) throws BeansException, ProductPriceException {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto,productModel);

        if (productModel.getValue().compareTo(BigDecimal.ZERO) < 0){
            throw new ProductPriceException();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        List<ProductModel> productList = productRepository.findAll();
        if(!productList.isEmpty()){
            for (ProductModel products: productList){
                UUID id = products.getIdProduct();
                products.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);

    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id")UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);
         if (product0.isEmpty()){
             throw new ProductNullPointerException();
             //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
         }
         product0.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
         return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value= "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        ProductModel productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable (value="id") UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            throw new ProductNullPointerException();
        }
        productRepository.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }



}
