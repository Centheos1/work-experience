package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.*;

import java.util.List;

public interface SaleDao {

    MboContract saveContract(MboContract mboContract);

    Iterable<MboContract> getFrontEndContracts();

    MboContract getContractByMboIdAndLocation(int mboId, String locaitonId);

    Iterable<MboContract> getAllContracts();

    void deleteAllContracts(Iterable<MboContract> mboContracts);

    MboService saveService(MboService mboService);

    MboService getServiceByMboIdAndLocation(int mboId, String locationId);

    Iterable<MboService> getAllServices();

    MboProduct getProductByMboIdAndLocation(String mboId, String locationId);

    MboProduct saveProduct(MboProduct mboProduct);

    Iterable<MboProduct> getAllProducts();

    Test saveTest(Test test);

    Iterable<Test> getAllTests();

    List<Test> getUnprocessedTests();

    List<Test> getPendingTests();

}
