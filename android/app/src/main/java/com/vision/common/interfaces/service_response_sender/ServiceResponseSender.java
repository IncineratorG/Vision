package com.vision.common.interfaces.service_response_sender;


import com.vision.common.data.service_response.ServiceResponse;

public interface ServiceResponseSender {
    void sendResponse(String groupName,
                      String groupPassword,
                      String receiverDeviceName,ServiceResponse response);
}
