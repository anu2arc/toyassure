package com.increff.dto;

import com.increff.requests.Requests;
import org.springframework.stereotype.Service;
import static com.increff.requests.Requests.objectToJsonString;

@Service
public class AssureClient {

    public String post(String url, Object obj) throws Exception {
        //todo :: check base url
        return Requests.post("http://localhost:9000/toyassure/" + url, objectToJsonString(obj));
    }
}
