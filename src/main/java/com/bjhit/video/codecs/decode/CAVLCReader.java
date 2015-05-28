package com.bjhit.video.codecs.decode;

import static com.bjhit.video.common.tools.Debug.trace;

import com.bjhit.video.codecs.H264Utils;
import com.bjhit.video.common.io.BitReader;

/**
 * 
 * @description 从缓冲区中读取上下文自适应可变长编码信息
 * @project bjhit-video
 * @author guanxianchun
 * @Create 2015-2-11 下午3:19:45
 * @version 1.0
 */
public class CAVLCReader {

    private CAVLCReader() {

    }

    public static int readNBit(BitReader bits, int n, String message)  {
        int val = bits.readNBit(n);

        trace(message, val);

        return val;
    }

    public static int readUE(BitReader bits)  {
        int cnt = 0;
        while (bits.read1Bit() == 0 && cnt < 31)
            cnt++;

        int res = 0;
        if (cnt > 0) {
            long val = bits.readNBit(cnt);

            res = (int) ((1 << cnt) - 1 + val);
        }

        return res;
    }

    public static int readUE(BitReader bits, String message)  {
        int res = readUE(bits);

        trace(message, res);

        return res;
    }

    public static int readSE(BitReader bits, String message)  {
        int val = readUE(bits);

        val = H264Utils.golomb2Signed(val);

        trace(message, val);

        return val;
    }

    public static boolean readBool(BitReader bits, String message)  {

        boolean res = bits.read1Bit() == 0 ? false : true;

        trace(message, res ? 1 : 0);

        return res;
    }

    public static int readU(BitReader bits, int i, String string)  {
        return (int) readNBit(bits, i, string);
    }

    public static int readTE(BitReader bits, int max)  {
        if (max > 1)
            return readUE(bits);
        return ~bits.read1Bit() & 0x1;
    }

    public static int readME(BitReader bits, String string)  {
        return readUE(bits, string);
    }

    public static int readZeroBitCount(BitReader bits, String message)  {
        int count = 0;
        while (bits.read1Bit() == 0 && count < 32)
            count++;

        trace(message, String.valueOf(count));

        return count;
    }

    public static boolean moreRBSPData(BitReader bits)  {
        return !(bits.remaining() < 32 && bits.checkNBit(1) == 1 && (bits.checkNBit(24) << 9) == 0);
    }
}