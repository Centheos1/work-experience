package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.SaleDao;
import com.fitnessplayground.dao.domain.MboContract;
import com.fitnessplayground.dao.domain.MboProduct;
import com.fitnessplayground.dao.domain.MboService;
import com.fitnessplayground.dao.domain.Test;
import com.fitnessplayground.dao.repository.ContractRepository;
import com.fitnessplayground.dao.repository.ProductRepository;
import com.fitnessplayground.dao.repository.ServiceRepository;
import com.fitnessplayground.dao.repository.TestRepository;
import com.fitnessplayground.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaleDaoImpl implements SaleDao {

    private static final Logger logger = LoggerFactory.getLogger(SaleDaoImpl.class);

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestRepository testRepository;

    @Override
    public MboContract saveContract(MboContract mboContract) {
        try {
//            logger.info("About to save Member: [{}]", mboContract.getName());
            mboContract = contractRepository.save(mboContract);
            logger.info("Saved Contract: [{}]", mboContract.getName());
        } catch (Exception ex) {
            String errorMsg = "Exception saving Contract: "+mboContract.getName();
            logger.error("Exception saving Contract: [{}] Error: [{}]", mboContract.getName(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return mboContract;
    }

    @Override
    public Iterable<MboContract> getFrontEndContracts() {

        return contractRepository.getFrontEndContracts();
    }

    @Override
    public MboContract getContractByMboIdAndLocation(int mboId, String locationId) {

//        logger.info("getContractByMboIdAndLocation mboId: {} : locationId: {}", mboId, locationId);
        Iterable<MboContract> allContracts = contractRepository.findAll();

        MboContract contract = null;

        for (MboContract c : allContracts) {
            if (c.getLocationId().contains(locationId) && c.getMboId() == mboId) {
                contract = c;
            }
        }
        return contract;
    }

    @Override
    public Iterable<MboContract> getAllContracts() {

        return contractRepository.findAll();
    }

    @Override
    public void deleteAllContracts(Iterable<MboContract> contracts) {
        contractRepository.delete(contracts);
    }

    @Override
    public MboService saveService(MboService mboService) {
        try {
//            logger.info("About to save MboService: [{}]", mboService.getName());
            mboService = serviceRepository.save(mboService);
            logger.info("Saved Service: [{}]", mboService.getName());
        } catch (Exception ex) {
            String errorMsg = "Exception saving MboService: "+mboService.getName();
            logger.error("Exception saving MboService: [{}] Error: [{}]", mboService.getName(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return mboService;
    }

    @Override
    public MboService getServiceByMboIdAndLocation(int mboId, String locationId) {
        logger.info("getServiceByMboIdAndLocation mboId: {} : locationId: {}", mboId, locationId);

        Iterable<MboService> allServcies = serviceRepository.findAll();

        MboService service = null;

        for (MboService s : allServcies) {
            if (s.getLocationId().contains(locationId) && s.getMboId() == mboId) {
                service = s;
            }
        }
        return service;
    }

    @Override
    public Iterable<MboService> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public MboProduct getProductByMboIdAndLocation(String mboId, String locationId) {

        Iterable<MboProduct> allProducts = productRepository.findAll();
        MboProduct product = null;

        for (MboProduct p : allProducts) {
            if (p.getLocationId().contains(locationId) && p.getMboId().equals(mboId)) {
                product = p;
            }
        }
        return product;
    }

    @Override
    public MboProduct saveProduct(MboProduct mboProduct) {
        try {
//            logger.info("About to save MboProduct: [{}]", mboProduct.getName());
            mboProduct = productRepository.save(mboProduct);
            logger.info("Saved MboProduct: [{}]", mboProduct.getName());
        } catch (Exception ex) {
            String errorMsg = "Exception saving MboProduct: "+mboProduct.getName();
            logger.error("Exception saving MboProduct: [{}] Error: [{}]", mboProduct.getName(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return mboProduct;
    }

    @Override
    public Iterable<MboProduct> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Test saveTest(Test test) {
        try {
            testRepository.save(test);
        }  catch (Exception ex) {
            String errorMsg = "Exception saving Contract: "+test.getId();
            logger.error("Exception saving Test: [{}] Error: [{}]", test.getId(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return test;
    }

    @Override
    public Iterable<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public List<Test> getUnprocessedTests() {
        return testRepository.getSaved();
    }

    @Override
    public List<Test> getPendingTests() {
        return testRepository.getPending();
    }


}
