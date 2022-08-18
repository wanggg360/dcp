import { useIntl } from 'umi';
import { DefaultFooter } from '@ant-design/pro-layout';

const Footer: React.FC = () => {
  const intl = useIntl();
  const copyrightMsg = intl.formatMessage({
    id: 'component.glbFooter.copyright',
    defaultMessage: '数字合规平台',
  });

  const link1Name = intl.formatMessage({
    id: 'component.glbFooter.link1.name'
  });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      copyright={`${currentYear} ${copyrightMsg}`}
      links={[
        {
          key: 'HTSC',
          title: `${link1Name}`,
          href: 'https://www.htsc.com.cn/',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
