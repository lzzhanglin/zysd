package com.zysd.test;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author lzzhanglin
 * @date 2019/1/11 10:10
 */

    @RunWith(value = SpringJUnit4ClassRunner.class)
    @SpringBootTest
    @Rollback
    public class MainControllerTest {

        private static final Logger logger = LoggerFactory.getLogger(MainControllerTest.class);

        private MockMvc mockMvc;

        @Autowired
        WebApplicationContext webApplicationContext;

        @Before
        public void setUp(){
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }
        //单独测试4功能块中的controller
        @Test
        public void testShowData() throws Exception {
        MvcResult result = mockMvc.perform(get("/showData").param("pageNo", "1").param("pageSize", "10"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType("text/html;charset=UTF-8"))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

            logger.info(result.getResponse().getContentAsString());

        }

        //并发测试4功能块中的controller
    @Test
    public void concurrentTest() throws Exception {
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                //测试内容
                try {
                    MvcResult result = mockMvc.perform(get("/showData").param("pageNo", "1").param("pageSize", "10"))
                            .andExpect(status().isOk())// 模拟向testRest发送get请求
                            .andExpect(content().contentType("text/html;charset=UTF-8"))// 预期返回值的媒体类型text/plain;charset=UTF-8
                            .andReturn();// 返回执行请求的结果
                    //将收到的html页面输出
                    logger.info(result.getResponse().getContentAsString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //Rnner数组，相当于并发多少个
        int runnerCount = 50;
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            System.out.println("执行到第" + i + "个线程");
            trs[i] = runner;
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        try {
            // 开始并发执行数组里定义的内容
            mttr.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }







}
