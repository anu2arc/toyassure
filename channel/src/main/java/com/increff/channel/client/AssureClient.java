package com.increff.channel.client;

import com.increff.channel.spring.ApiException;
import com.increff.commons.data.OrderData;
import com.increff.commons.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AssureClient {
    @Autowired
    private static RestTemplate restTemplate = new RestTemplate();
    private static final String serverUrl="http://localhost:9000/toyassure/api/";
    public void placeOrder(OrderForm channelOrderUploadForm) throws ApiException {
        String orderCreateUrl = serverUrl + "/order/channel";
        try {
            restTemplate.postForObject(orderCreateUrl, channelOrderUploadForm, String.class);
        } catch (HttpClientErrorException ex) {
            throw new ApiException("Error from assure server: " + ex.getResponseBodyAsString());
        }
    }

    public List<OrderData> fetchAllOrder() throws ApiException {
        String orderFetchUrl = serverUrl + "/order";
        try {
            ResponseEntity<OrderData[]> response = restTemplate.getForEntity(orderFetchUrl, OrderData[].class);
            OrderData[] orderDataList = response.getBody();
            return Arrays.asList(orderDataList);
        }
        catch (HttpClientErrorException exception){
            throw new ApiException("Error from assure server: "+exception.getResponseBodyAsString());
        }
    }
}
