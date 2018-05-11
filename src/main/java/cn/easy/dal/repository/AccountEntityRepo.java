package cn.easy.dal.repository;

import cn.easy.dal.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by zhangrui on 2018/5/7.
 */
public interface AccountEntityRepo extends
        PagingAndSortingRepository<AccountEntity, String>,
        JpaSpecificationExecutor<AccountEntity> {
    AccountEntity findByPhone(String phone);
    AccountEntity findByEmail(String email);

//    @Query("select new AccountEntity() from AccountEntity acc where acc.phone =: phoneMail or acc.email =:phoneMail")
    AccountEntity findByPhoneOrEmail(String phone,String mail);

}
