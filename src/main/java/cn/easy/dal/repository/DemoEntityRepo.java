package cn.easy.dal.repository;

import cn.easy.dal.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by superlee on 2017/11/23.
 * demo repo
 */
public interface DemoEntityRepo extends
        PagingAndSortingRepository<DemoEntity, String>,
        JpaSpecificationExecutor<DemoEntity> {
}
