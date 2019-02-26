package com.qidiancamp.test.bitstamp;

import com.qidiancamp.api.bitstamp.service.BitstampAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private BitstampAccountService bitstampAccountService;

    @Test
    public void accountInfo() throws IOException {

        bitstampAccountService.getAccountInfo();
    }

}
