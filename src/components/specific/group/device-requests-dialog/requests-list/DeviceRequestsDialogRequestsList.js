import React, {useCallback} from 'react';
import {View, FlatList, StyleSheet} from 'react-native';
import DeviceRequestsDialogRequestsListTextItem from './item/DeviceRequestsDialogRequestsListTextItem';
import DeviceRequestsDialogRequestsListCheckboxItem from './item/DeviceRequestsDialogRequestsListCheckboxItem';

const DeviceRequestsDialogRequestsList = ({requestsList, onRequestPress}) => {
  const renderItem = useCallback(
    ({item}) => {
      const {type, recomendedComponentType, name, icon, checked} = item;

      if (recomendedComponentType === 'text') {
        return (
          <DeviceRequestsDialogRequestsListTextItem
            type={type}
            name={name}
            icon={icon}
            onPress={onRequestPress}
          />
        );
      } else {
        return (
          <DeviceRequestsDialogRequestsListCheckboxItem
            type={type}
            name={name}
            checked={checked}
            onPress={onRequestPress}
          />
        );
      }
    },
    [onRequestPress],
  );

  const keyExtractor = useCallback((item) => {
    return item.type;
  }, []);

  return (
    <View style={styles.mainContainer}>
      <FlatList
        showsVerticalScrollIndicator={false}
        data={requestsList}
        renderItem={renderItem}
        keyExtractor={keyExtractor}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
  },
});

export default React.memo(DeviceRequestsDialogRequestsList);
