import {createStore, combineReducers, applyMiddleware} from 'redux';
import createSagaMiddleware from 'redux-saga';
import rootSaga from './sagas/rootSaga';
import AppReducers from './reducers/AppReducers';

const sagaMiddleware = createSagaMiddleware();

const reducers = combineReducers({
  temp: AppReducers.temp,
  auth: AppReducers.auth,
  surveillanceCommon: AppReducers.surveillanceCommon,
  surveillanceIsDeviceAliveRequest:
    AppReducers.surveillanceIsDeviceAliveRequest,
});

const store = createStore(reducers, applyMiddleware(sagaMiddleware));

sagaMiddleware.run(rootSaga);

export default store;
