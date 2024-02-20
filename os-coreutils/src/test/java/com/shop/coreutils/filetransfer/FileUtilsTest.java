package com.shop.coreutils.filetransfer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileUtilsTest {

    @Test
    @Order(1)
    void whenFilePathIsGiven_thenOnlyFileNameIsReturned(){
        String path1 = "f1/f2/f3/test1.txt";
        String path2 = "test.xml";
        String path3 = "f1/f2/";

        assertEquals(FileUtils.getFileNameFromPath(path1),"test1.txt");
        assertEquals(FileUtils.getFileNameFromPath(path2),"test.xml");
        assertEquals(FileUtils.getFileNameFromPath(path3),"");
    }
}
