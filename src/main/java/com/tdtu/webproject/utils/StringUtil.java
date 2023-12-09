package com.tdtu.webproject.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Optional;

public class StringUtil {
    public static String convertBigDecimalToString(BigDecimal number) {
        return Optional.ofNullable(number).isPresent() ?
                String.valueOf(number) : null;
    }

    public static boolean isNotNullOrEmptyString(String string) {
        return Optional.ofNullable(string).isPresent() && !string.isEmpty() && !string.isBlank();
    }

    public static String getLocalIPAddress() {
        Logger logger = LogManager.getLogger(StringUtil.class); // Replace YourClass with the actual class name

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            logger.error("An error occurred while obtaining the local IP address.", e);
        }
        throw new RuntimeException("No network adapters with an IPv4 address in the system!");
    }
}
