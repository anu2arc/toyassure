package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
public class ChannelListingUploadForm {
    @NotNull
    private String channelName;
    @NotNull
    private String clientName;
    private List<ChannelListingForm> channelList;
}
