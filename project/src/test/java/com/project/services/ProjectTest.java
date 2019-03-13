package com.project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@EnableKafka
@SpringBootTest(properties = "spring.profiles.active = Test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProjectTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void testInistance() {
        assertThat(true).isTrue();
    }

}
