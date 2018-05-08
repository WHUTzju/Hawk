package cn.easy.dal.repository;

import cn.easy.dal.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zhangrui on 2018/5/7.
 */
public interface AccountEntityRepo extends
        PagingAndSortingRepository<AccountEntity, String>,
        JpaSpecificationExecutor<AccountEntity> {
    AccountEntity findByPhone(String phone);
    AccountEntity findByEmail(String email);

}
