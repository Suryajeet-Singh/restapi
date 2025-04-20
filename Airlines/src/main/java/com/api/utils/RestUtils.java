package com.api.utils;

import com.api.reporting.ExtentReportManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.util.Map;

public class RestUtils {

    public static RequestSpecification getRequestSpecification(String baseURL, Map<String,Object> payload){
        return RestAssured.given()
                .baseUri(baseURL)
                .contentType(ContentType.JSON)
                .body(payload);

    }

    public static Response postAPIHit(String baseURL,  Map<String,Object> payload){
        RequestSpecification requestSpecification = getRequestSpecification(baseURL,payload);
        Response response = requestSpecification.post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;

    }

    private static void printRequestLogInReport(RequestSpecification requestSpecification){
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetails("EndPoint of Airlines : "+queryableRequestSpecification.getBaseUri());
        ExtentReportManager.logInfoDetails("Method of Airlines : "+queryableRequestSpecification.getMethod());
        ExtentReportManager.logJson(queryableRequestSpecification.getBody());
    }

    private static void printResponseLogInReport(Response response){
        ExtentReportManager.logInfoDetails("Status Code of Airlines : "+response.getStatusCode());
//        ExtentReportManager.logJson(response.getBody().prettyPrint());
        ExtentReportManager.logHeadersDetails(response.getHeaders().asList());
//        ExtentReportManager.logInfoDetails("Response Content Type : "+response.getContentType());
    }
}
