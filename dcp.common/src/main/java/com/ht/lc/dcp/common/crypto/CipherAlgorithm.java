package com.ht.lc.dcp.common.crypto;

import com.ht.lc.dcp.common.constants.CipherConst;
import org.springframework.util.StringUtils;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-11-10 10:20
 * @Version 1.0
 **/
public enum CipherAlgorithm {
    AES_GCM_NOPADDING_256(CipherConst.ALG_NAME_AES, CipherConst.ALG_MODE_GCM, CipherConst.ALG_PADDING_NOPADDING,
        CipherConst.SIZE_256, CipherConst.SIZE_128);

    private String algName;

    private String algMode;

    private String algPadding;

    private int keySize;

    private int ivSize;

    CipherAlgorithm(String algName, String algMode, String algPadding, int keySize, int ivSize) {
        this.algName = algName;
        this.algMode = algMode;
        this.algPadding = algPadding;
        this.keySize = keySize;
        this.ivSize = ivSize;
    }

    public String getAlgName() {
        return algName;
    }

    public String getAlgMode() {
        return algMode;
    }

    public String getAlgPadding() {
        return algPadding;
    }

    public int getKeySize() {
        return keySize;
    }

    public int getIvSize() {
        return ivSize;
    }

    public String getTransformationString() {
        StringBuilder sb = new StringBuilder(algName);
        if (StringUtils.hasText(algMode))
            sb.append("/").append(algMode);
        if (StringUtils.hasText(algPadding))
            sb.append("/").append(algPadding);
        return sb.toString();
    }
}
