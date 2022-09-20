package com.increff.dto;

import com.increff.model.data.BinData;
import com.increff.model.data.BinSkuData;
import com.increff.model.enums.ClientType;
import com.increff.model.forms.BinSkuForm;
import com.increff.model.forms.BinSkuUpdateForm;
import com.increff.pojo.BinSkuPojo;
import com.increff.pojo.ProductPojo;
import com.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.increff.Util.BinUtil.validate;
import static com.increff.dto.DtoHelper.*;

@Service
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
        return convertBinPojoToBinDataList(binService.getAll());
    }
    public void create(int noOfBin) {
        binService.create(noOfBin);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<BinSkuForm> binSkuForms,long clientId) throws ApiException {
        clientService.checkIdAndType(clientId, ClientType.CLIENT);
        List<BinSkuPojo> binSkuPojoList=getPojoList(binSkuForms,clientId);
        binService.add(binSkuPojoList);
        inventoryService.add(convertBinPojoListToInventoryPojo(binSkuPojoList));
    }

    private List<BinSkuPojo> getPojoList(List<BinSkuForm> binSkuForms, long clientId) throws ApiException {
        StringBuilder error=new StringBuilder();
        List<BinSkuPojo> binSkuPojoList=new ArrayList<>();
        for(BinSkuForm binSkuForm:binSkuForms){
            try{
                validate(binSkuForm);
                ProductPojo productPojo=productService.check(binSkuForm.getClientSkuId(),clientId);
                if(productPojo==null)
                    throw new ApiException("Product doesn't exist for given ClientSkuId");
                binService.check(binSkuForm.getBinId());
                binSkuPojoList.add(convertBinSkuFormToBinSkuPojo(binSkuForm, productPojo.getGlobalSkuId()));
            } catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty()) throw new ApiException(error.toString());
        return binSkuPojoList;
    }

    public List<BinSkuData> getAllSku() {
        return convertBinSkuPojoToBinSkuDataList(binService.getAllSku());
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(long id, BinSkuUpdateForm binSkuUpdateForm) throws ApiException {
        BinSkuPojo binSkuPojo=binService.get(id);
        inventoryService.update(binSkuPojo.getGlobalSkuId(),binSkuPojo.getQuantity(),binSkuUpdateForm.getQuantity());
        binService.update(id, binSkuUpdateForm.getQuantity());
    }
}
