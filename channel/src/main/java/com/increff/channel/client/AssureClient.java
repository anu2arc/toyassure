package com.increff.channel.client;

import com.increff.commons.requests.Requests;
import org.springframework.stereotype.Service;

import static com.increff.commons.requests.Requests.objectToJsonString;

@Service
public class AssureClient {

    public String post(String url, Object obj) throws Exception {
        //todo :: check base url
        return Requests.post("http://localhost:9000/toyassure/" + url, objectToJsonString(obj));
    }
}
