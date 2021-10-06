const SurveillanceTakeBackCameraImageRequestActions = () => {
  const types = {
    SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST:
      'STBCIRA_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST',
    SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_BEGIN:
      'STBCIRA_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_BEGIN',
    SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_SENDED:
      'STBCIRA_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_SENDED',
    SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_COMPLETED:
      'STBCIRA_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_COMPLETED',
    SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_ERROR:
      'STBCIRA_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_ERROR',
    CANCEL_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST:
      'STBCIRA_CANCEL_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST',
    SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_CANCELLED:
      'STBCIRA_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_CANCELLED',
  };

  const sendTakeBackCameraImageRequest = ({receiverDeviceName}) => {
    return {
      type: types.SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST,
      payload: {receiverDeviceName},
    };
  };

  const sendTakeBackCameraImageRequestBegin = () => {
    return {
      type: types.SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_BEGIN,
    };
  };

  const sendTakeBackCameraImageRequestSended = ({requestId}) => {
    return {
      type: types.SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_SENDED,
      payload: {requestId},
    };
  };

  const sendTakeBackCameraImageRequestCompleted = ({image}) => {
    return {
      type: types.SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_COMPLETED,
      payload: {image},
    };
  };

  const sendTakeBackCameraImageRequestError = ({code, message}) => {
    return {
      type: types.SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_ERROR,
      payload: {code, message},
    };
  };

  const cancelSendTakeBackCameraImageRequest = () => {
    return {
      type: types.CANCEL_SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST,
    };
  };

  const sendTakeBackCameraImageRequestCancelled = () => {
    return {
      type: types.SEND_TAKE_BACK_CAMERA_IMAGE_REQUEST_CANCELLED,
    };
  };

  return {
    types,
    actions: {
      sendTakeBackCameraImageRequest,
      sendTakeBackCameraImageRequestBegin,
      sendTakeBackCameraImageRequestSended,
      sendTakeBackCameraImageRequestCompleted,
      sendTakeBackCameraImageRequestError,
      cancelSendTakeBackCameraImageRequest,
      sendTakeBackCameraImageRequestCancelled,
    },
  };
};

export default SurveillanceTakeBackCameraImageRequestActions;
