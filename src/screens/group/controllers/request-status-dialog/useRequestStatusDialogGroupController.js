import {useCallback} from 'react';
import GroupLocalActions from '../../store/GroupLocalActions';
import {SystemEventsHandler} from '../../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../../store/actions/AppActions';

const useRequestStatusDialogGroupController = (model) => {
  const {
    data: {
      deviceRequestTypes,
      currentRequestType,
      currentGroupName,
      currentGroupPassword,
      currentDeviceName,
      loggedIn,
      localState: {
        deviceRequestsDialog: {
          visible: deviceRequestsDialogVisible,
          selectedDeviceName: deviceRequestsDialogSelectedDeviceName,
        },
      },
    },
    navigation,
    dispatch,
    localDispatch,
  } = model;

  const requestStatusDialogCancelHandler = useCallback(() => {
    if (currentRequestType != null) {
      switch (currentRequestType) {
        case deviceRequestTypes.TAKE_BACK_CAMERA_IMAGE: {
          dispatch(
            AppActions.surveillanceTakeBackCameraImageRequest.actions.cancelSendTakeBackCameraImageRequest(),
          );
          break;
        }

        case deviceRequestTypes.TAKE_FRONT_CAMERA_IMAGE: {
          dispatch(
            AppActions.surveillanceTakeFrontCameraImageRequest.actions.cancelSendTakeFrontCameraImageRequest(),
          );
          break;
        }

        case deviceRequestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT: {
          dispatch(
            AppActions.surveillanceToggleDetectDeviceMovementRequest.actions.cancelSendToggleDetectDeviceMovementRequest(),
          );
          break;
        }

        default: {
          SystemEventsHandler.onInfo({
            info:
              'useRequestStatusDialogGroupController()->requestStatusDialogCancelHandler()->UNKNOWN_CURRENT_REQUEST_TYPE: ' +
              currentRequestType,
          });
        }
      }
    } else {
      SystemEventsHandler.onInfo({
        info: 'useRequestStatusDialogGroupController()->requestStatusDialogCancelHandler()->CURRENT_REQUEST_TYPE_IS_NULL',
      });
    }

    localDispatch(
      GroupLocalActions.actions.setRequestStatusDialogVisibility({
        visible: false,
      }),
    );
  }, [currentRequestType, deviceRequestTypes, dispatch, localDispatch]);

  return {
    // requestStatusDialogCancelHandler,
  };
};

export default useRequestStatusDialogGroupController;
