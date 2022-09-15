package com.increff.dto;

import com.increff.model.data.BinData;
import com.increff.model.data.BinSkuData;
import com.increff.model.enums.ClientType;
import com.increff.model.forms.BinSkuForm;
import com.increff.model.forms.BinSkuUpdateForm;
import com.increff.pojo.BinPojo;
import com.increff.pojo.BinSkuPojo;
import com.increff.pojo.ProductPojo;
import com.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.increff.Util.BinUtil.validate;
import static com.increff.dto.DtoHelper.convert;
import static com.increff.dto.DtoHelper.convertx;

@Repository
public class BinDto {
    @Autowired
    private BinService binService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;
    public List<BinData> getAll() {
        List<BinData> binDataList=new ArrayList<>();
        List<BinPojo> binPojoList=binService.getAll();
        for(BinPojo binPojo:binPojoList){
            binDataList.add(convert(binPojo));
        }
        return binDataList;
    }
    public void create(int noOfBin) {
        binService.create(noOfBin);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<BinSkuForm> binSkuForms,long clientId) throws ApiException {
        StringBuilder error=new StringBuilder();
        List<BinSkuPojo> binSkuPojoList=new ArrayList<>();
        clientService.checkIdAndType(clientId, ClientType.CLIENT);
        for(BinSkuForm binSkuForm:binSkuForms){
            try{
                validate(binSkuForm);
                ProductPojo productPojo=productService.check(binSkuForm.getClientSkuId(),clientId);
                if(productPojo==null)
                    throw new ApiException("Product doesn't exist for given ClientSkuId");
                binService.check(binSkuForm.getBinId());
                binSkuPojoList.add(convert(binSkuForm, productPojo.getGlobalSkuId()));
            }
            catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        binService.add(binSkuPojoList);
        //todo change convert to convert type
        inventoryService.add(convertx(binSkuPojoList));
    }

    public List<BinSkuData> getAllSku() {
        List<BinSkuPojo> binSkuPojoList=binService.getAllSku();
        List<BinSkuData> binSkuDataList=new ArrayList<>();
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            binSkuDataList.add(convert(binSkuPojo));
        }
        return binSkuDataList;
    }
    @Transactional(rollbackOn = ApiException.class)
    public void update(long id, BinSkuUpdateForm binSkuUpdateForm) throws ApiException {
        BinSkuPojo binSkuPojo=binService.get(id);
        inventoryService.update(binSkuPojo.getGlobalSkuId(),binSkuPojo.getQuantity(),binSkuUpdateForm.getQuantity());
        binService.update(id, binSkuUpdateForm.getQuantity());
    }
}
