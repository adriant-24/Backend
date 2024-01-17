package com.shop.onlineshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.api.client.APIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class APIService {

    @Autowired
    APIClient apiClient;

    @Autowired
    ObjectMapper objectMapper;

    HttpHeaders httpHeaders;

    @Autowired
    public APIService(){
        this.httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("admin","admin");
    }


//    public String GetEmployeePathVariable(int id) throws JsonProcessingException {
//        Map<String, String> pathVariables = new HashMap<>();
//
//        pathVariables.put("id", String.valueOf(id));
//
//        ParameterizedTypeReference<Employee> returnType = new ParameterizedTypeReference<Employee>() {
//        };
//
//        ResponseEntity<Employee> response = apiClient.callAPI("http://localhost:8081/api/employees/{id}",
//                HttpMethod.GET,
//                null,
//                pathVariables,
//                httpHeaders,
//                null,
//                MediaType.APPLICATION_JSON,
//                MediaType.APPLICATION_JSON,
//                returnType, true);
//
//        return objectMapper.writeValueAsString(response.getBody());
//    }
//
//    public String GetEmployeeQueryParam(int id) throws JsonProcessingException {
//        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
//
//        queryParams.add("id", String.valueOf(id));
//
//        ParameterizedTypeReference<Employee> returnType = new ParameterizedTypeReference<Employee>() {
//        };
//
//        HttpHeaders authHeader = new HttpHeaders();
//        authHeader.setBasicAuth("admin", "admin");
//
//        ResponseEntity<Employee> response = apiClient.callAPI("http://localhost:8081/api/employees",
//                HttpMethod.GET,
//                queryParams,
//                null,
//                authHeader,
//                null,
//                MediaType.APPLICATION_JSON,
//                MediaType.APPLICATION_JSON,
//                returnType, true);
//
//        return objectMapper.writeValueAsString(response.getBody());
//
//    }
//
//    public Employee PostEmployee(Employee employee){
//
//        ParameterizedTypeReference<Employee> returnType = new ParameterizedTypeReference<Employee>() {};
//
//        ResponseEntity<Employee> response = apiClient.callAPI("http://localhost:8081/api/employees",
//                HttpMethod.POST,
//                null,
//                null,
//                httpHeaders,
//                employee,
//                MediaType.APPLICATION_JSON,
//                MediaType.APPLICATION_JSON,
//                returnType, true);
//        return response.getBody();
//    }
//
//    public String DeleteEmployee(int id){
//        Map<String,String> pathVariables = new HashMap<>();
//        pathVariables.put("id", String.valueOf(id));
//        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
//
//        ResponseEntity<String> response = apiClient.callAPI("http://localhost:8081/api/employees/{id}",
//                HttpMethod.DELETE,
//                null,
//                pathVariables,
//                httpHeaders,
//                null,
//                MediaType.APPLICATION_JSON,
//                MediaType.APPLICATION_JSON,
//                returnType, true);
//        return response.getBody();
//    }
}
