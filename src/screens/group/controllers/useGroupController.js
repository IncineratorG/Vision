import {useCallback} from 'react';
import {BackHandler} from 'react-native';
import {SystemEventsHandler} from '../../../utils/common/system-events-handler/SystemEventsHandler';
import AppActions from '../../../store/actions/AppActions';

const useGroupController = (model) => {
  const {
    data: {currentGroupName, currentGroupPassword, currentDeviceName, loggedIn},
    dispatch,
  } = model;

  const backButtonPressHandler = useCallback(() => {
    BackHandler.exitApp();
  }, []);

  const updateDevicesInGroupData = useCallback(() => {
    SystemEventsHandler.onInfo({
      info: 'useGroupController()->updateDevicesInGroupData()',
    });

    dispatch(
      AppActions.surveillance.actions.getDevicesInGroup({
        groupName: currentGroupName,
        groupPassword: currentGroupPassword,
        deviceName: currentDeviceName,
      }),
    );
  }, [currentGroupName, currentGroupPassword, currentDeviceName, dispatch]);

  return {
    backButtonPressHandler,
    updateDevicesInGroupData,
  };
};

export default useGroupController;
