package com.qiqi.springboot.seed.bz1.controller;

import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeMethod
    private void BeforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
//        List<UserInfo> list = new ArrayList<>();
//        when(this.userService.findAll()).thenReturn(list);
//        ResponseEntity res = userController.findAll();
//
//        Assert.assertEquals(res.getStatusCode(), HttpStatus.OK);
//        Assert.assertNotNull(res.getBody());
    }
}