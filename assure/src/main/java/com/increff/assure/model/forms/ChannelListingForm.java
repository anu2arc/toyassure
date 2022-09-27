package com.increff.assure.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChannelListingForm {
    @NotNull
    private String channelSkuId;
    @NotNull
    private String clientSkuId;
}
