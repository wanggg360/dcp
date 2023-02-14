import React from 'react';
import { useIntl, useModel } from 'umi';
import styles from './index.less';
import { SettingOutlined } from '@ant-design/icons';


const Home: React.FC = () => {
  const { initialState, setInitialState } = useModel('@@initialState');
  const intl = useIntl();

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <p> 这个是home页 </p>
      </div>
      <SettingOutlined></SettingOutlined>
    </div>
  );
};

export default Home;
