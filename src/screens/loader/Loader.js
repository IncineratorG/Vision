import React from 'react';
import useLoaderModel from './models/useLoaderModel';
import LoaderView from './views/LoaderView';

const Loader = () => {
  const model = useLoaderModel();

  return <LoaderView model={model} />;
};

export default React.memo(Loader);
