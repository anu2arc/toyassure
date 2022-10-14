package com.increff.assure.client;

import com.increff.assure.service.ApiException;
import com.increff.commons.data.InvoiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ChannelClient {

    @Autowired
    private static RestTemplate restTemplate = new RestTemplate();
    private static final String serverUrl="http://localhost:9001/channel/api";
    public String getInvoice(InvoiceData invoiceData) throws ApiException {
        String orderCreateUrl = serverUrl + "/channel/invoice";
        try {
            return restTemplate.postForObject(orderCreateUrl,invoiceData, String.class);
        } catch (HttpClientErrorException ex) {
            throw new ApiException("Error from channel server: " + ex.getResponseBodyAsString());
        }
    }
}
