package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ChannelListingUploadForm {
    private String channelName;
    private String clientName;
    private List<ChannelListingForm> channelList;
}
