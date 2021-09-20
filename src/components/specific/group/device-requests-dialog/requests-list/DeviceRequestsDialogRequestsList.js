import React, {useCallback, useEffect} from 'react';
import {View, FlatList, StyleSheet} from 'react-native';
import DeviceRequestsDialogRequestsListItem from './item/DeviceRequestsDialogRequestsListItem';
import {SystemEventsHandler} from '../../../../../utils/common/system-events-handler/SystemEventsHandler';

const DeviceRequestsDialogRequestsList = ({requestsList, onRequestPress}) => {
  const renderItem = useCallback(
    ({item}) => {
      const {type, name} = item;

      return (
        <DeviceRequestsDialogRequestsListItem
          type={type}
          name={name}
          onPress={onRequestPress}
        />
      );
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
