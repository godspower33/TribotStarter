package obf;
 
/*
 * Exception performing whole class analysis ignored.
 */
public final class zg {
    public static final String yL(byte[] a) {
        if (a == null) {
            return null;
        }
        byte[] arrby = new byte[a.length + 2];
        System.arraycopy(a, 0, arrby, 0, a.length);
        byte[] arrby2 = new byte[arrby.length / 3 * 4];
        int n = 0;
        int n2 = 0;
        int n3 = n;
        while (n3 < a.length) {
            byte[] arrby3 = arrby2;
            int n4 = n2;
            byte[] arrby4 = arrby2;
            arrby4[n2] = (byte) (arrby[n] >>> 2 & 63);
            arrby4[n2 + 1] = (byte) (arrby[n + 1] >>> 4 & 15 | arrby[n] << 4 & 63);
            arrby3[n4 + 2] = (byte) (arrby[n + 2] >>> 6 & 3 | arrby[n + 1] << 2 & 63);
            byte by = (byte) (arrby[n + 2] & 63);
            arrby3[n4 + 3] = by;
            n2 += 4;
            n3 = n += 3;
        }
        int n5 = n = 0;
        while (n5 < arrby2.length) {
            if (arrby2[n] < 26) {
                byte[] arrby5 = arrby2;
                arrby5[n] = (byte) (arrby5[n] + 65);
            } else if (arrby2[n] < 52) {
                byte[] arrby6 = arrby2;
                arrby6[n] = (byte) (arrby6[n] + 97 - 26);
            } else if (arrby2[n] < 62) {
                byte[] arrby7 = arrby2;
                arrby7[n] = (byte) (arrby7[n] + 48 - 52);
            } else {
                arrby2[n] = (byte) (arrby2[n] < 63 ? 43 : 47);
            }
            n5 = ++n;
        }
        int n6 = n = arrby2.length - 1;
        while (n6 > a.length * 4 / 3) {
            arrby2[n--] = 61;
            n6 = n;
        }
        return new String(arrby2);
    }
 
    public static final byte[] IL(String a) {
        if (a != null) return obf.zg.BL(a.getBytes());
        return null;
    }
 
    public static final byte[] BL(byte[] a) {
        int n;
        int n2;
        int n3 = a.length;
        byte[] arrby = a;
        while (arrby[n3 - 1] == 61) {
            --n3;
            arrby = a;
        }
        byte[] arrby2 = new byte[n3 - a.length / 4];
        int n4 = n2 = 0;
        while (n4 < a.length) {
            if (a[n2] == 61) {
                a[n2] = 0;
            } else if (a[n2] == 47) {
                a[n2] = 63;
            } else if (a[n2] == 43) {
                a[n2] = 62;
            } else if (a[n2] >= 48 && a[n2] <= 57) {
                byte[] arrby3 = a;
                arrby3[n2] = (byte) (arrby3[n2] - -4);
            } else if (a[n2] >= 97 && a[n2] <= 122) {
                byte[] arrby4 = a;
                arrby4[n2] = (byte) (arrby4[n2] - 71);
            } else if (a[n2] >= 65 && a[n2] <= 90) {
                byte[] arrby5 = a;
                arrby5[n2] = (byte) (arrby5[n2] - 65);
            }
            n4 = ++n2;
        }
        n2 = 0;
        int n5 = n = 0;
        while (n5 < arrby2.length - 2) {
            byte[] arrby6 = arrby2;
            int n6 = n;
            arrby2[n] = (byte) (a[n2] << 2 & 255 | a[n2 + 1] >>> 4 & 3);
            arrby6[n6 + 1] = (byte) (a[n2 + 1] << 4 & 255 | a[n2 + 2] >>> 2 & 15);
            byte by = (byte) (a[n2 + 2] << 6 & 255 | a[n2 + 3] & 63);
            n2 += 4;
            arrby6[n6 + 2] = by;
            n5 = n += 3;
        }
        if (n < arrby2.length) {
            arrby2[n] = (byte) (a[n2] << 2 & 255 | a[n2 + 1] >>> 4 & 3);
        }
        if (++n >= arrby2.length) return arrby2;
        arrby2[n] = (byte) (a[n2 + 1] << 4 & 255 | a[n2 + 2] >>> 2 & 15);
        return arrby2;
    }
 
    public static final String uL(String a) {
        return obf.zg.yL(a.getBytes());
    }
}