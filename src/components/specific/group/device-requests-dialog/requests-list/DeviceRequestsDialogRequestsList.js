import React, {useCallback} from 'react';
import {View, FlatList, StyleSheet} from 'react-native';
import DeviceRequestsDialogRequestsListItem from './item/DeviceRequestsDialogRequestsListItem';

const DeviceRequestsDialogRequestsList = ({requestsList, onRequestPress}) => {
  const renderItem = useCallback(({item}) => {
    return <DeviceRequestsDialogRequestsListItem />;
  }, []);

  const keyExtractor = useCallback(() => {
    return -1;
  }, []);

  return (
    <View style={styles.mainContainer}>
      <FlatList
        showsVerticalScrollIndicator={false}
        // data={categoriesList}
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
