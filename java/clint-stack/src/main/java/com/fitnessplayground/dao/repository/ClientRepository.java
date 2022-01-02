package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.MboClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ClientRepository extends CrudRepository<MboClient, Long> {

    String FIND_BY_UNIQUE_ID = "SELECT * FROM MboClient WHERE uniqueId = :uniqueId";

    String FIND_BY_UNIQUE_ID_AND_SITE_ID = "SELECT * FROM MboClient WHERE uniqueId = :uniqueId AND siteId = :siteId";

    String SEARCH_FOR_DUPLICATE_KEY_CLIENT = "SELECT * FROM MboClient WHERE accessKeyNumber = :accessKeyNumber AND status NOT IN ('Terminated','Non-Member')";

    String SEARCH_FOR_EXISTING_CLIENT = "SELECT * FROM MboClient WHERE firstName = :firstName AND lastName = :lastName AND email = :email";

    String FIND_BY_USERNAME = "SELECT * FROM MboClient WHERE userName = :username";

    String FIND_ACTIVE_CLIENTS = "SELECT * FROM MboClient WHERE status = 'Active";

    @Query(value = FIND_BY_UNIQUE_ID, nativeQuery = true)
    ArrayList<MboClient> findByUniqueId(@Param("uniqueId") long uniqueId);

    @Query(value = SEARCH_FOR_EXISTING_CLIENT, nativeQuery = true)
    List<MboClient> searchForExistingClient(
                                            @Param("firstName") String firstName,
                                            @Param("lastName") String lastName,
                                            @Param("email") String email);

    @Query(value = SEARCH_FOR_DUPLICATE_KEY_CLIENT, nativeQuery = true)
    List<MboClient> searchDuplicateKey(@Param("accessKeyNumber") String accessKeyNumber);

    @Query(value = FIND_BY_USERNAME, nativeQuery = true)
    List<MboClient> findByUsername(@Param("username") String username);

    @Query(value = FIND_BY_UNIQUE_ID_AND_SITE_ID, nativeQuery = true)
    List<MboClient> findByUniqueIdAndSiteId(@Param("uniqueId") long uniqueId, @Param("siteId") long siteId);

    @Query(value = FIND_ACTIVE_CLIENTS, nativeQuery = true)
    List<MboClient> findActiveClients();
}
