package com.increff.Util;

import com.increff.model.form.BinSkuForm;
import com.increff.model.form.BinSkuUpdateForm;
import com.increff.service.ApiException;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BinUtil {
    public static void validate(BinSkuForm binSkuForm) throws ApiException {
        if(Objects.isNull(binSkuForm.getClientSkuId()) || binSkuForm.getClientSkuId().trim().equals(""))
            throw new ApiException("ClientSkuId cannot be empty");
        if(binSkuForm.getQuantity()<0) {
            throw new ApiException("Quantity cannot be negative");
        }
    }
    public static void validate(BinSkuUpdateForm binSkuUpdateForm) throws ApiException {
        if(binSkuUpdateForm.getQuantity()<0)
            throw new ApiException("Quantity cannot be negative");
    }
}
