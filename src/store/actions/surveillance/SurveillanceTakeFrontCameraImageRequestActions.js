const SurveillanceTakeFrontCameraImageRequestActions = () => {
  const types = {
    SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST:
      'STFCIRA_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST',
    SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_BEGIN:
      'STFCIRA_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_BEGIN',
    SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_SENDED:
      'STFCIRA_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_SENDED',
    SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_COMPLETED:
      'STFCIRA_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_COMPLETED',
    SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_ERROR:
      'STFCIRA_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_ERROR',
    CANCEL_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST:
      'STFCIRA_CANCEL_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST',
    SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_CANCELLED:
      'STFCIRA_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_CANCELLED',
  };

  const sendTakeFrontCameraImageRequest = ({receiverDeviceName}) => {
    return {
      type: types.SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST,
      payload: {receiverDeviceName},
    };
  };

  const sendTakeFrontCameraImageRequestBegin = () => {
    return {
      type: types.SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_BEGIN,
    };
  };

  const sendTakeFrontCameraImageRequestSended = ({requestId}) => {
    return {
      type: types.SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_SENDED,
      payload: {requestId},
    };
  };

  const sendTakeFrontCameraImageRequestCompleted = ({image}) => {
    return {
      type: types.SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_COMPLETED,
      payload: {image},
    };
  };

  const sendTakeFrontCameraImageRequestError = ({code, message}) => {
    return {
      type: types.SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_ERROR,
      payload: {code, message},
    };
  };

  const cancelSendTakeFrontCameraImageRequest = () => {
    return {
      type: types.CANCEL_SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST,
    };
  };

  const sendTakeFrontCameraImageRequestCancelled = () => {
    return {
      type: types.SEND_TAKE_FRONT_CAMERA_IMAGE_REQUEST_CANCELLED,
    };
  };

  return {
    types,
    actions: {
      sendTakeFrontCameraImageRequest,
      sendTakeFrontCameraImageRequestBegin,
      sendTakeFrontCameraImageRequestSended,
      sendTakeFrontCameraImageRequestCompleted,
      sendTakeFrontCameraImageRequestError,
      cancelSendTakeFrontCameraImageRequest,
      sendTakeFrontCameraImageRequestCancelled,
    },
  };
};

export default SurveillanceTakeFrontCameraImageRequestActions;
