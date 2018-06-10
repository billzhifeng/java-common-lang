package com.github.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.java.common.base.ReturnCodeEnum;
import com.github.java.common.utils.JavaAssert;

/**
 * demo
 * 
 * @author wangzhifeng
 * @date 2018年2月8日 上午11:28:10
 */
public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);

    /**
     * 使用用例demo
     * 
     * @param args
     */
    public static void main(String[] args) {
        DemoResp resp = null;
        try {
            if (true) {
                RuntimeException e = new RuntimeException("");
                DemoException de = new DemoException();
                de.setStackTrace(e.getStackTrace());
                throw e;
            }

            //偶数成功,奇数失败
            resp = api(1);

            if (!resp.isSuccess()) {
                System.out.println("返回失败,code=" + resp.getReturnCode());
            } else {
                System.out.println("返回成功");
            }

            //偶数成功,奇数失败
            resp = api(2);

            if (!resp.isSuccess()) {
                System.out.println("返回失败,code=" + resp.getReturnCode());
            } else {
                System.out.println("返回成功");
            }

        } catch (DemoException e) {
            //log.error 未知异常
            logger.error("", e);
        }

        try {
            JavaAssert.isNum("000", "电话号码不为纯数字", DemoException.class);

            JavaAssert.hasText(null, DemoReturnCodeEnum.PARAM_VALIDATE_ERROR, "内容不能为空", DemoException.class);

            Object repayRequest = null;
            JavaAssert.notNull(repayRequest, ReturnCodeEnum.REQUEST_ARGUMENTS_ILLEGAL, "req不能为空!", DemoException.class);
        } catch (DemoException e) {
            System.out.println("DemoException" + e.getReturnMsg());
        } catch (Exception e) {
            System.out.println("Exception");
        } catch (Throwable e) {
            System.out.println("Throwable");
        }
    }

    /**
     * 模拟服务端,提供 param 必填
     * 
     * @param param
     * @return
     */
    public static DemoResp api(int count) {
        DemoResp resp = null;
        try {
            JavaAssert.isTrue(count > 0, DemoReturnCodeEnum.PARAM_VALIDATE_ERROR, "参数为空", count, DemoException.class);

            if (count % 2 == 0) {
                resp = DemoResp.buildSuccessResp(DemoResp.class);
            } else {
                resp = DemoResp.buildFailResp("xx业务执行失败xxx", DemoResp.class);
            }
            //log.info() 返回信息
            return resp;
        } catch (DemoException e) {
            //log.warn 自定义异常
            return DemoResp.buildFailResp(e.getReturnMsg(), DemoResp.class);
        } catch (Exception e) {
            //log.error 未知异常
            return DemoResp.buildFailResp("xx业务失败", DemoResp.class);
        }
    }
}
