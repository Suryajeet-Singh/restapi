package com.api.payload;

import java.util.HashMap;
import java.util.Map;

public class RequestPayload {

    public static Map<String,Object> payloadForPostAsMap(String id, String name, String country, String logo,
                                                         String slogan,String head_quarter, String website,String established){
        Map<String, Object> mp = new HashMap<>();
        mp.put("_id",id);
        mp.put("name",name);
        mp.put("country",country);
        mp.put("logo",logo);
        mp.put("slogan",slogan);
        mp.put("head_quarter",head_quarter);
        mp.put("website",website);
        mp.put("established",established);
        return mp;

    }

    public static String payloadForPostAsString(String id, String name, String country, String logo,
                                                String slogan,String head_quarter, String website,String established) {
        String payload = "{\n" +
                "    \"_id\": \"" + id + "\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"country\": \""+country+"\",\n" +
                "    \"logo\": \""+logo+"\",\n" +
                "    \"slogan\": \""+slogan+"\",\n" +
                "    \"head_quaters\": \""+head_quarter+"\",\n" +
                "    \"website\": \""+website+"\",\n" +
                "    \"established\": \""+established+"\"\n" +
                "}";
        return payload;
    }


}
