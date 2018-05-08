package cn.easy.controller;

import cn.easy.business.constant.Code;
import cn.easy.business.response.BaseResult;
import cn.easy.business.response.BaseResultFactory;
import cn.easy.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by superlee on 2017/11/3.
 */
@RestController
@RequestMapping("/test")
@Api(value = "测试", description = "用于测试是否通过", position = 9)
public class TestController {

    private static final Logger logger = Logger.getLogger(TestController.class);

    @Autowired
    private DemoService demoService;

    @ApiOperation(value = "测试name", notes = "测试name")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    BaseResult test(
            @ApiParam(value = "用户名(不能重复)", required = true) @PathVariable("name") String name) {


        List<Object> result = demoService.testSetSth(name);


        return BaseResultFactory.produceResult(Code.SUCCESS, result);
    }
}
