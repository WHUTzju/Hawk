package cn.easy.service.impl;

import cn.easy.service.DemoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by superlee on 2017/11/23.
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public List<Object> testSetSth(String param) {
//        Hermes hermes = SpringBeanFactory.getBean(Hermes.class);
//        hermes.buildTrigger("4030ca444f18b600d4f7956eb94f9622743c5147","{\"address\":\"4030ca444f18b600d4f7956eb94f9622743c5147\",\"encrypted\":\"6d3efd02b72f59dbcb3e93c4f292c82d82a4a4a30b87c7550c767383bf917b01c6f431ea0e0e22ac\",\"version\":\"1.0\",\"algo\":\"0x02\"}","123",false,false);
//        ContractInvokeRet contractInvokeRet = hermes.setSth(param);
//        List<Object> result = contractInvokeRet.getMethodRet();

        return null;
    }
}
