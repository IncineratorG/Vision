import {useCallback} from 'react';
import {useNavigation} from '@react-navigation/core';
import {useDispatch} from 'react-redux';

const useMainModel = () => {
  const navigation = useNavigation();

  const dispatch = useDispatch();

  const initialText =
    "In the early hours of 13 September 2007, Flying Squad and CO19 officers attended a briefing at Leman Street police station in East London before travelling to Chandler's Ford, arriving at around 04:00. Armed officers took up position in a block of public toilets about 50 metres (160 feet) from the bank, supported by snipers in vantage points overlooking the bank. The officers concealed in the toilet block were kept informed of events through radio communication with the snipers. At around 06:00, the team received word from officers watching the gang that several members were travelling towards Chandler's Ford in a stolen vehicle.[4]\n" +
    '\n' +
    "At around 09:15, police officers observed Markland at a bus stop close to the bank. Other gang members were spotted in the vicinity shortly afterwards, some repeatedly driving past the bank in a stolen vehicle. Shortly before 10:00, Nunes arrived in a second stolen vehicle and parked opposite the bank. A few minutes later, the G4S van arrived and the guard on board began carrying cash boxes into the bank. At 10:05, Nunes approached the guard, pointing a handgun at his chest, causing him to freeze. One of the police snipers opened fire. Nunes, hit in the chest, collapsed at the guard's feet. By this time, the armed officers who had been hiding in the toilet block were running towards the bank. Meanwhile, Markland ran across to Nunes and picked up the gun. As he stood back up with the weapon, he was shot by a second police sniper. Hit in the chest, Markland collapsed; he was seen moving on the ground and the sniper fired again.[12][13]\n" +
    '\n' +
    'Police officers attempted to administer first aid as they reached the van but Markland died at the scene. Nunes was taken to Southampton General Hospital by the Hampshire & Isle of Wight Air Ambulance but died later the same day. The security guard suffered a minor injury to his hand, having been grazed by a bullet. The remaining gang members fled the area in one of the stolen vehicles';

  return {
    data: {
      initialText,
    },
    setters: {},
    dispatch,
    navigation,
  };
};

export default useMainModel;
