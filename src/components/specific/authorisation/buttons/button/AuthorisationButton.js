import React from 'react';
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';

const AuthorisationButton = ({text, onPress}) => {
  // '#0072e5', '#0086ea', '#0098ef', '#00a9f4'

  return (
    <TouchableOpacity onPress={onPress}>
      <View style={styles.mainContainer}>
        <Text style={styles.text}>{text}</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    height: 50,
    width: 300,
    flexDirection: 'row',
    backgroundColor: '#',
    borderRadius: 30,
    alignItems: 'center',
    justifyContent: 'center',
  },
  text: {
    fontSize: 18,
    color: 'white',
  },
});

export default React.memo(AuthorisationButton);
