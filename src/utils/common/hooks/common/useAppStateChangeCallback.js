import {useEffect, useState} from 'react';
import useAppState from './useAppState';

const useAppStateChangeCallback = ({
  callback,
  runOnAppStart,
  runOnGoingToForeground,
  runOnGoingToBackground,
}) => {
  const [appWasInForeground, setAppWasInForeground] = useState(null);

  const {appInForeground} = useAppState();

  useEffect(() => {
    if (callback) {
      if (appWasInForeground === null) {
        if (runOnAppStart) {
          callback();
        }
      } else if (appWasInForeground === false && appInForeground) {
        if (runOnGoingToForeground) {
          callback();
        }
      } else if (appWasInForeground === true && !appInForeground) {
        if (runOnGoingToBackground) {
          callback();
        }
      }
    }

    setAppWasInForeground(appInForeground);
  }, [
    appInForeground,
    appWasInForeground,
    callback,
    runOnAppStart,
    runOnGoingToForeground,
    runOnGoingToBackground,
  ]);
};

export default useAppStateChangeCallback;
