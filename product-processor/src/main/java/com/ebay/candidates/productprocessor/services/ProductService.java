package com.ebay.candidates.productprocessor.services;

import com.ebay.candidates.productprocessor.model.Attribute;
import com.ebay.candidates.productprocessor.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final String TITLE_VALUE = "title";

    @Value("${attribute-values.exclusion-list}")
    private List<String> illegalValues;

    public void normalizeAttributesValues(Product product){
        for (Attribute att : product.getAttributes()){
            if(!att.getName().equals(TITLE_VALUE)) {
                List<String> values = att.getValues();
                List<String> normalizedValues = new ArrayList<>();
                for (String value : values) {
                    String normalizedValue = value;
                    if ((value.length() > 1)) {
                        normalizedValue = value.substring(0, 1).toUpperCase() + value.substring(1);
                    }
                    normalizedValues.add(normalizedValue);
                }
                att.setValues(normalizedValues);
            }
        }
    }

    public void filterIllegalValues(Product product) {
        List<Attribute> filteredAttributes = new ArrayList<>();
        for (Attribute att : product.getAttributes()) {
            List<String> values = att.getValues();
            List<String> filteredValues = values.stream().filter(value -> !illegalValues.contains(value)).collect(Collectors.toList());
            if(filteredValues.size() > 0) {
                att.setValues(filteredValues);
                filteredAttributes.add(att);
            }
        }
        product.setAttributes(filteredAttributes);
    }

    public void sortAttributesByName(Product product){
        Collections.sort(product.getAttributes(), new Comparator<Attribute>() {
            public int compare(Attribute att1, Attribute att2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(att1.getName(), att2.getName());
                if (res == 0) {
                    res = att1.getName().compareTo(att2.getName());
                }
                return res;
            }
        });


    }
    private static Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
        public int compare(String str1, String str2) {
            int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
            if (res == 0) {
                res = str1.compareTo(str2);
            }
            return res;
        }
    };
}
