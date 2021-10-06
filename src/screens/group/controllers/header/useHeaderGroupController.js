import {useCallback} from 'react';
import AppRoutes from '../../../../data/common/routes/AppRoutes';

const useHeaderGroupController = (model) => {
  const {navigation} = model;

  const headerRightButtonPressHandler = useCallback(() => {
    navigation.navigate(AppRoutes.Settings);
  }, [navigation]);

  return {
    headerRightButtonPressHandler,
  };
};

export default useHeaderGroupController;
