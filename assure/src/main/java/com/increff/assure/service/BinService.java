package com.increff.assure.service;

import com.increff.assure.dao.BinDao;
import com.increff.assure.dao.BinSkuDao;
import com.increff.assure.pojo.BinPojo;
import com.increff.assure.pojo.BinSkuPojo;
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
            throw new ApiException("Bin does not exist for given ID");
        return binSkuPojo;
    }

    @Transactional
    public void allocate(Long globalSkuId, long allocatedQuantity) throws ApiException {
        List<BinSkuPojo> binSkuPojoList=binSkuDao.getAllBinWithAvailableProduct(globalSkuId);
        if(binSkuPojoList==null) throw new ApiException("No valid bin found for the given globalSkuId:"+globalSkuId);
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            if(allocatedQuantity==0) break;
            long quantityToReduce=Math.min(allocatedQuantity,binSkuPojo.getQuantity());
            binSkuPojo.setQuantity(binSkuPojo.getQuantity()-quantityToReduce);
            allocatedQuantity=allocatedQuantity-quantityToReduce;
        }
        if(allocatedQuantity!=0)
            throw new ApiException("Insufficient inventory in bins");//error msg insufficient
    }
}
