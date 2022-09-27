package com.increff.assure.dto;

import com.increff.assure.model.data.BinData;
import com.increff.assure.model.data.BinSkuData;
import com.increff.assure.model.forms.BinSkuForm;
import com.increff.assure.model.forms.BinSkuUpdateForm;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.*;
import com.increff.commons.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.increff.assure.Util.BinUtil.validate;

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
        return DtoHelper.convertBinPojoToBinDataList(binService.getAll());
    }
    public void create(int noOfBin) {
        binService.create(noOfBin);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<BinSkuForm> binSkuForms,long clientId) throws ApiException {
        clientService.checkIdAndType(clientId, ClientType.CLIENT);
        List<BinSkuPojo> binSkuPojoList=getPojoList(binSkuForms,clientId);
        binService.add(binSkuPojoList);
        inventoryService.add(DtoHelper.convertBinPojoListToInventoryPojo(binSkuPojoList));
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
                binSkuPojoList.add(DtoHelper.convertBinSkuFormToBinSkuPojo(binSkuForm, productPojo.getGlobalSkuId()));
            } catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty()) throw new ApiException(error.toString());
        return binSkuPojoList;
    }

    public List<BinSkuData> getAllSku() {
        return DtoHelper.convertBinSkuPojoToBinSkuDataList(binService.getAllSku());
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(long id, BinSkuUpdateForm binSkuUpdateForm) throws ApiException {
        BinSkuPojo binSkuPojo=binService.get(id);
        inventoryService.update(binSkuPojo.getGlobalSkuId(),binSkuPojo.getQuantity(),binSkuUpdateForm.getQuantity());
        binService.update(id, binSkuUpdateForm.getQuantity());
    }
}
