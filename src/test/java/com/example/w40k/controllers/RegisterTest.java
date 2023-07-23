package com.example.w40k.controllers;

import com.example.w40k.components.JwtRequestFilter;
import com.example.w40k.components.JwtUtilities;
import com.example.w40k.repositories.ShipRepository;
import com.example.w40k.repositories.UserRepository;
import com.example.w40k.services.EmailSenderService;
import com.example.w40k.services.ShipService;
import com.example.w40k.services.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(HomeController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class RegisterTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    public BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private ShipRepository shipRepository;
    @MockBean
    private ShipService shipService;
    @MockBean
    private EmailSenderService emailSenderService;
    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtUtilities jwtUtilities;

    @Test
    void registerNewUser() throws Exception {
        String username = "username";
        String email = "username@useremail.com";
        String password = "Password1@";

        JSONObject requestJson = new JSONObject();
        requestJson.put("username", username);
        requestJson.put("password", password);
        requestJson.put("email", email);

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson.toString());

        MvcResult result = mvc.perform(request)
                .andReturn();

        System.out.println("----------" + result);
    }
}
