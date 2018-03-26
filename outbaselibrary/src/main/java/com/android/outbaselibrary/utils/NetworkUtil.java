package com.android.outbaselibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by puyafeng on 2017/3/10.
 */

public class NetworkUtil {
    private static final String TAG = "Network";

    /**
     * 接入方式：0 NONE
     */
    public static final int APN_NONE = 0;

    /**
     * 接入方式：1 CMWAP
     */
    public static final int APN_PROXY = 1;

    /**
     * 接入方式：2 CMNET
     */
    public static final int APN_DIRECT = 2;

    public enum NetworkType {
        NETWORK_UNKNOWN, NETWORK_NONE, NETWORK_WIFI, NETWORK_MOBILE, NETWORK_2G, NETWORK_3G
    }

    private static String hostIp = null;

    private static int hostPort = 0;
    /**
     * 获取接入点的相关信息：1、CMWAP，2、CMNET
     */
    public static int getAccessPointType(Context context) {

        ConnectivityManager mConnectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 区分是WIFI网络还是移动手机网络
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();

        if ((info != null) && info.isAvailable()) {
            if (StringUtil.equalsIgnoreCase(info.getTypeName(), "mobile")) {

                if (SystemUtil.hasHoneycomb()) {
                    hostIp = System.getProperty("http.proxyHost");
                    String port = System.getProperty("http.proxyPort");
                    hostPort = !TextUtils.isEmpty(port) ? NumberUtil.toInt(port) : -1;
                } else {
                    hostIp = Proxy.getHost(context);
                    hostPort = Proxy.getPort(context);
                }

                // 使用代理了
                if (!TextUtils.isEmpty(hostIp) && (hostPort != 0)) {
                    // 客户端是否使用代理
                    return APN_PROXY;
                } else {
                    return APN_DIRECT;
                }
                //            } else if (info.getTypeName().toLowerCase().equals("wifi")) {
                //                return APN_DIRECT;
            } else {
                return APN_DIRECT;
            }
        }
        return APN_NONE;
    }

    public static String getHostIp() {
        return hostIp;
    }

    public static int getHostPort() {
        return hostPort;
    }
    /**
     * 获取网络类型：-1、未知，0、无网络，1、WiFi，2、移动网络，3、2G（移动网络），4、3G（移动网络）
     */
    public static NetworkType getNetworkType(Context context) {

        NetworkType networkType = NetworkType.NETWORK_UNKNOWN;

        ConnectivityManager mConnectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 区分是WIFI网络还是移动手机网络
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();

        if ((info != null) && info.isAvailable()) {
            if (StringUtil.equalsIgnoreCase(info.getTypeName(), "wifi")) {
                networkType = NetworkType.NETWORK_WIFI;
            } else if (StringUtil.equalsIgnoreCase(info.getTypeName(), "mobile")) {
                networkType = NetworkType.NETWORK_MOBILE;
                // 然后根据TelephonyManager来获取网络类型，判断当前是2G还是3G网络
                TelephonyManager mTelephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                int netType = mTelephonyManager.getNetworkType();
                if (netType == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                    networkType = NetworkType.NETWORK_UNKNOWN;
                }
                if ((netType == TelephonyManager.NETWORK_TYPE_GPRS)
                        || (netType == TelephonyManager.NETWORK_TYPE_EDGE)) {
                    networkType = NetworkType.NETWORK_2G;
                } else {
                    networkType = NetworkType.NETWORK_3G;
                }
            }
        } else {
            networkType = NetworkType.NETWORK_NONE;
        }
        return networkType;
    }

    public static boolean isWiFi() {
        return (getNetworkType(AppContext.getContext()) == NetworkType.NETWORK_WIFI);
    }

    public static boolean isMobileNetwork() {
        return (getNetworkType(AppContext.getContext()) == NetworkType.NETWORK_MOBILE);
    }

    public static String getNetworkStringByType(NetworkType networkType) {
        String networkString = "Unknown";
        switch (networkType) {
            case NETWORK_NONE:
                networkString = "None";
                break;
            case NETWORK_WIFI:
                networkString = "WiFi";
                break;
            case NETWORK_MOBILE:
                networkString = "Mobile";
                break;
            case NETWORK_2G:
                networkString = "2G";
                break;
            case NETWORK_3G:
                networkString = "3G";
                break;
            default:
                networkString = "Unknown";
                break;
        }
        return networkString;
    }
    //是否有网络


    //是否手机移动网络

    //是否wifi

    //本地ip

    /**
     * Workaround for bug pre-Froyo, see here for more info:
     * http://android-
     * developers.blogspot.com/2011/09/androids-http-clients.html
     */
    public static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (!SystemUtil.hasFroyo()) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    public static boolean is3GOrWifi(Context context) {
        NetworkType networkType = NetworkUtil.getNetworkType(context);
        return ((networkType == NetworkType.NETWORK_3G) || (networkType == NetworkType.NETWORK_WIFI));
    }

    public static boolean hasConnection() {

        final ConnectivityManager cm = (ConnectivityManager) AppContext.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }

        return true;
    }

}
