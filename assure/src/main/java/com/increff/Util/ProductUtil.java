package com.increff.Util;

import com.increff.model.form.ProductForm;
import com.increff.model.form.ProductUpdateForm;
import com.increff.service.ApiException;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

@Repository
public class ProductUtil {
    public static void validate(ProductForm productForm) throws ApiException {
        if(Objects.isNull(productForm.getClientSkuId()) || productForm.getClientSkuId().trim().equals(""))
            throw new ApiException("ClientSkuId cannot be empty");
        if(productForm.getClientSkuId().length()>30)
            throw new ApiException("ClientSkuId too long");
        if(Objects.isNull(productForm.getName()) || productForm.getName().trim().equals(""))
            throw new ApiException("Name cannot be empty");
        if(productForm.getName().length()>30)
            throw new ApiException("Name too long");
        if(Objects.isNull(productForm.getBrandId()) || productForm.getBrandId().trim().equals(""))
            throw new ApiException("BrandId cannot be empty");
        if(productForm.getBrandId().length()>30)
            throw new ApiException("BrandId too long");
        if(Objects.isNull(productForm.getDescription()) || productForm.getDescription().trim().equals(""))
            throw new ApiException("Description cannot be empty");
        if(productForm.getDescription().length()>60)
            throw new ApiException("Description too long");
        if(productForm.getMrp()==null)
            throw new ApiException("MRP cannot be null");
        if(productForm.getMrp()==0)
            throw new ApiException("MRP cannot be zero");
        if(productForm.getMrp()<0)
            throw new ApiException("MRP cannot be negative");
        normalize(productForm);
    }

    public static void validate(ProductUpdateForm productUpdateForm) throws ApiException {
        if(Objects.isNull(productUpdateForm.getName()) || productUpdateForm.getName().trim().equals(""))
            throw new ApiException("Name cannot be empty");
        if(productUpdateForm.getName().length()>30)
            throw new ApiException("Name too long");
        if(Objects.isNull(productUpdateForm.getBrandId()) || productUpdateForm.getBrandId().trim().equals(""))
            throw new ApiException("BrandId cannot be empty");
        if(productUpdateForm.getBrandId().length()>30)
            throw new ApiException("BrandId too long");
        if(Objects.isNull(productUpdateForm.getDescription()) || productUpdateForm.getDescription().trim().equals(""))
            throw new ApiException("Description cannot be empty");
        if(productUpdateForm.getDescription().length()>60)
            throw new ApiException("Description too long");
        if(productUpdateForm.getMrp()==null)
            throw new ApiException("MRP cannot be null");
        if(productUpdateForm.getMrp()==0)
            throw new ApiException("MRP cannot be zero");
        if(productUpdateForm.getMrp()<0)
            throw new ApiException("MRP cannot be negative");
    }
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static void normalize(ProductForm productForm){
        productForm.setClientSkuId(productForm.getClientSkuId().trim().toLowerCase());
        productForm.setName(productForm.getName().trim().toLowerCase(Locale.ROOT));
        productForm.setBrandId(productForm.getBrandId().trim().toLowerCase());
        productForm.setDescription(productForm.getDescription().trim().toLowerCase());
        productForm.setMrp(Double.valueOf(DECIMAL_FORMAT.format(productForm.getMrp())));
    }
    private static void normalize(ProductUpdateForm productUpdateForm){
        productUpdateForm.setName(productUpdateForm.getName().trim().toLowerCase(Locale.ROOT));
        productUpdateForm.setBrandId(productUpdateForm.getBrandId().trim().toLowerCase());
        productUpdateForm.setDescription(productUpdateForm.getDescription().trim().toLowerCase());
        productUpdateForm.setMrp(Double.valueOf(DECIMAL_FORMAT.format(productUpdateForm.getMrp())));
    }
}
