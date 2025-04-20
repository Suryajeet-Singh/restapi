package com.api.AirlinesTest;
import com.api.endpoints.Endpoints;
import com.api.payload.RequestPayload;
import com.api.utils.RestUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class FirstTest {

    @Test
    public void testPostAirline() {
        Map<String, Object> payloadValue = RequestPayload.payloadForPostAsMap(
                "67543334", "Surya Lanka Airways", "Surya Lanka",
                "https://upload.wikimedia.org/logo.png", "From Surya Lanka",
                "Katunayake, Surya Lanka", "www.suryalankaaairways.com", "1995"
        );

        Response response = RestUtils.postAPIHit(Endpoints.baseURL, payloadValue);

        System.out.println("Response Body: " + response.asString());

        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not 200");
        Assert.assertTrue(response.asString().contains("Surya Lanka Airways"), "Response does not contain expected airline name");
    }
}
