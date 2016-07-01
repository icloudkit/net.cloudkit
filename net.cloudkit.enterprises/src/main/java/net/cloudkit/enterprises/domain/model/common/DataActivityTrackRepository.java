package net.cloudkit.enterprises.domain.model.common;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface DataActivityTrackRepository extends PagingAndSortingRepository<DataActivityTrack, Long>, JpaSpecificationExecutor<DataActivityTrack> {

}
