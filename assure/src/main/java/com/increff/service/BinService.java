package com.increff.service;

import com.increff.dao.BinDao;
import com.increff.dao.BinSkuDao;
import com.increff.pojo.BinPojo;
import com.increff.pojo.BinSkuPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BinService {
    @Autowired
    private BinDao binDao;
    @Autowired
    private BinSkuDao binSkuDao;

    public List<BinPojo> getAll() {
        return binDao.getAll();
    }
    @Transactional
    public void create(int noOfBin) {
        for(int i=0;i<noOfBin;i++){
            BinPojo binPojo=new BinPojo();
            binDao.insert(binPojo);
        }
    }
    @Transactional
    public void add(List<BinSkuPojo> binSkuPojoList) {
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            BinSkuPojo presentPojo=binSkuDao.check(binSkuPojo.getBinId(),binSkuPojo.getGlobalSkuId());
            if(presentPojo==null)
                binSkuDao.add(binSkuPojo);
            else {
                presentPojo.setQuantity(presentPojo.getQuantity()+binSkuPojo.getQuantity());
            }
        }
    }
    public void check(long binId) throws ApiException {
        BinPojo binPojo=binDao.check(binId);
        if(binPojo==null)
            throw new ApiException("Invalid BinId:"+binId);
    }
    public List<BinSkuPojo> getAllSku() {
        return binSkuDao.getAllSku();
    }
    @Transactional
    public void update(long id, long quantity) throws ApiException {
        BinSkuPojo binSkuPojo=get(id);
        binSkuPojo.setQuantity(quantity);
    }

    public BinSkuPojo get(long id) throws ApiException {
        BinSkuPojo binSkuPojo=binSkuDao.get(id);
        if(binSkuPojo==null)
            throw new ApiException("Invalid id");
        return binSkuPojo;
    }

    @Transactional
    public void allocate(Long globalSkuId, long AllocatedQuantity) throws ApiException {
        List<BinSkuPojo> binSkuPojoList=binSkuDao.getAllBinWithAvailableProduct(globalSkuId);
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            if(AllocatedQuantity==0) break;
            long quantityToReduce=Math.min(AllocatedQuantity,binSkuPojo.getQuantity());
            binSkuPojo.setQuantity(binSkuPojo.getQuantity()-quantityToReduce);
            AllocatedQuantity=AllocatedQuantity-quantityToReduce;
        }
        if(AllocatedQuantity!=0)
            throw new ApiException("inventory missing from bin");
    }
}
