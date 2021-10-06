const ru = {
  AppName: 'Vision',

  RegisterDeviceInGroupDialog_title: 'Регистрация в группе',
  RegisterDeviceInGroupDialog_groupNamePlaceholder: 'Название группы',
  RegisterDeviceInGroupDialog_groupPasswordPlaceholder: 'Пароль',
  RegisterDeviceInGroupDialog_deviceNamePlaceholder: 'Имя устройства',
  RegisterDeviceInGroupDialog_registerButton: 'ЗАРЕГИСТРИРОВАТЬ',
  RegisterDeviceInGroupDialog_cancelButton: 'ОТМЕНА',

  RegisteringDeviceInGroupDialog_message: 'Регистрация устройства',
  RegisteringDeviceInGroupDialog_cancelButton: 'ОТМЕНА',

  NeedCreateGroupDialog_message: 'Группа не создана. Создать?',
  NeedCreateGroupDialog_createButton: 'СОЗДАТЬ',
  NeedCreateGroupDialog_cancelButton: 'ОТМЕНА',

  CreatingGroupWithDeviceDialog_message:
    'Создание группы и регистрация устройства',
  CreatingGroupWithDeviceDialog_cancelButton: 'ОТМЕНА',

  LoginDeviceInGroupDialog_title: 'Войти в группу',
  LoginDeviceInGroupDialog_groupNamePlaceholder: 'Название группы',
  LoginDeviceInGroupDialog_groupPasswordPlaceholder: 'Пароль',
  LoginDeviceInGroupDialog_deviceNamePlaceholder: 'Имя устройства',
  LoginDeviceInGroupDialog_loginButton: 'ВОЙТИ',
  LoginDeviceInGroupDialog_cancelButton: 'ОТМЕНА',

  LoggingDeviceInGroupDialog_message: 'Вход в группу',
  LoggingDeviceInGroupDialog_cancelButton: 'ОТМЕНА',

  AuthorisationButtons_loginText: 'Войти',
  AuthorisationButtons_loginButtonText: 'Войти',
  AuthorisationButtons_registerText: 'Регистрация',
  AuthorisationButtons_registerButtonText: 'Регистрация',

  AuthorisationInputFields_groupNameFieldPlaceholder: 'Название группы',
  AuthorisationInputFields_groupPasswordFieldPlaceholder: 'Пароль группы',
  AuthorisationInputFields_deviceNameFieldPlaceholder: 'Имя устройства',

  AuthorisationStatus_registerDeviceInGroupInProgress: 'Регистрация в группе',
  AuthorisationStatus_createGroupWithDeviceInProgress: 'Создание группы',
  AuthorisationStatus_loginDeviceInGroupInProgress: 'Вход в группу',
  AuthorisationStatus_errorEmptyGroupName: 'Укажите название группы',
  AuthorisationStatus_errorEmptyGroupPassword: 'Введите пароль от группы',
  AuthorisationStatus_errorEmptyDeviceName: 'Введите название устройства',
  AuthorisationStatus_errorGroupNotExist:
    'Указанная группа не зарегестрирована',
  AuthorisationStatus_errorGroupAlreadyExist: 'Указанная группа уже существует',
  AuthorisationStatus_errorIncorrectGroupPassword:
    'Неправилльный пароль от группы',
  AuthorisationStatus_errorDeviceNameAlreadyExist:
    'Название устройства уже занято',
  AuthorisationStatus_errorDeviceAlreadyLoggedIn: 'Устройство уже в сети',
  AuthorisationStatus_errorDeviceNameNotExist: 'Неверное имя устройства',
  AuthorisationStatus_errorFirebaseFailure: 'Не удалось связаться с сервисом',
  AuthorisationStatus_errorUnknown: 'Неизвестная ошибка',

  DeviceRequestDialog_cancelButton: 'ОТМЕНА',

  SelectedDeviceErrorDialog_cancelButton: 'ОТМЕНА',

  SelectedDeviceError_notInServiceMode:
    'Выбранное устройство не переведено в режим исполнения запросов.',
  SelectedDeviceError_deviceNotRespond: 'Выбранное устройство не отвечает.',

  DeviceRequestsDialog_title: 'Доступные запросы',
  DeviceRequestsDialog_getFrontCameraImage: 'Изображение передней камеры',
  DeviceRequestsDialog_getBackCameraImage: 'Изображение задней камеры',
  DeviceRequestsDialog_detectDeviceMovement: 'Следить за движением устройства',

  SelectedDeviceNotAvailable_message: 'Устройство не доступно.',

  CheckingSelectedDevice_message: 'Проверка доступности устройства.',

  RequestInProgressDialog_defaultMessage: 'Выполнение запроса.',
  RequestInProgressDialog_cancelButton: 'ОТМЕНА',

  RequestStatusDialog_requestInProgressMessage: 'Выполнение запроса.',
  RequestStatusDialog_requestCompletedMessage: 'Запрос выполнен.',
  RequestStatusDialog_cancelButton: 'ОТМЕНА',
  RequestStatusDialog_okButton: 'OK',
  RequestStatusDialog_viewResponseButton: 'РЕЗУЛЬТАТ',

  SettingsView_imageQualitySubheader: 'Качество изрбражений',
  SettingsView_backCameraImageQuality: 'Задняя камера',
  SettingsView_backCameraImageQualityHigh: 'Высокое',
  SettingsView_backCameraImageQualityMedium: 'Среднее',
  SettingsView_backCameraImageQualityLow: 'Низкое',
  SettingsView_backCameraImageQualityUnknown: 'Неизвестно',
  SettingsView_frontCameraImageQuality: 'Передняя камера',
  SettingsView_frontCameraImageQualityHigh: 'Высокое',
  SettingsView_frontCameraImageQualityMedium: 'Среднее',
  SettingsView_frontCameraImageQualityLow: 'Низкое',
  SettingsView_frontCameraImageQualityUnknown: 'Неизвестно',
  SettingsView_notificationsSubheader: 'Уведомления',
  SettingsView_receiveNotificationsFromCurrentGroup:
    'Получать уведомления из текущей группы',

  BackCameraImageQualityDialog_title: 'Качество изображения задней камеры',
  BackCameraImageQualityDialog_cancelButton: 'ОТМЕНА',
  BackCameraImageQualityDialog_qualityHigh: 'Высокое',
  BackCameraImageQualityDialog_qualityMedium: 'Среднее',
  BackCameraImageQualityDialog_qualityLow: 'Низкое',

  FrontCameraImageQualityDialog_title: 'Качество изображения передней камеры',
  FrontCameraImageQualityDialog_cancelButton: 'ОТМЕНА',
  FrontCameraImageQualityDialog_qualityHigh: 'Высокое',
  FrontCameraImageQualityDialog_qualityMedium: 'Среднее',
  FrontCameraImageQualityDialog_qualityLow: 'Низкое',
};

export default ru;
