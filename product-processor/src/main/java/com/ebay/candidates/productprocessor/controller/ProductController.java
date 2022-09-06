package com.ebay.candidates.productprocessor.controller;

import com.ebay.candidates.productprocessor.model.Attribute;
import com.ebay.candidates.productprocessor.model.Product;
import com.ebay.candidates.productprocessor.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product-processor")
public class ProductController {

  @Autowired
  private ProductService productService;

  @RequestMapping(path = "/product", method = RequestMethod.POST)
  public ResponseEntity<Product> upsert(@RequestBody Product product) {

    productService.normalizeAttributesValues(product);
    productService.filterIllegalValues(product);
    productService.sortAttributesByName(product);
    return ResponseEntity.ok(product);
  }
  @GetMapping(path = "/product/{id}")
  public ResponseEntity<Product> get(@PathVariable("id") int productId ,@RequestBody Product product) {

    return ResponseEntity.ok(product);
  }

  /*@RequestMapping(path = "/product/100", method = RequestMethod.GET)
  public ResponseEntity<Product> get(@RequestBody Product product) {

    return ResponseEntity.ok(product);
  }*/
}
