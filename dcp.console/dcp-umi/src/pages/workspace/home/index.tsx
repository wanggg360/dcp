import React from 'react';
import { useIntl, useModel } from 'umi';
import Footer from '@/components/Footer';
import styles from './index.less';


const Home: React.FC = () => {
  const { initialState, setInitialState } = useModel('@@initialState');
  const intl = useIntl();

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <p> 这个是home页 </p>
      </div>
      <Footer />
    </div>
  );
};

export default Home;
