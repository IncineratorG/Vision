import {useState, useCallback, useEffect, useReducer, useMemo} from 'react';
import AppActions from '../../../store/actions/AppActions';

const useSelectedDeviceController = (model) => {
  const {dispatch} = model;

  const checkingDeviceDialogCancelHandler = useCallback(() => {
    dispatch(
      AppActions.surveillanceIsDeviceAliveRequest.actions.cancelSendIsAliveRequest(),
    );
  }, [dispatch]);

  return {
    checkingDeviceDialogCancelHandler,
  };
};

export default useSelectedDeviceController;
