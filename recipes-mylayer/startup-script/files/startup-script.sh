#!/bin/bash
set -euo pipefail

echo "[INFO] Unblocking Wi-Fi via rfkill"
rfkill unblock wifi || echo "[WARN] rfkill failed"

echo "[INFO] Starting wpa_supplicant..."
killall -q wpa_supplicant 2>/dev/null || true
sleep 1
wpa_supplicant -B -c /etc/wpa_supplicant.conf -i wlan0 > /var/log/wpa_supplicant.log 2>&1

for i in $(seq 1 10); do
    if iw wlan0 link | grep -q 'Connected'; then
        echo "[INFO] Wi-Fi associated after $i seconds"
        break
    fi
    sleep 1
done

if ! iw wlan0 link | grep -q 'Connected'; then
    echo "[ERROR] Failed to associate with Wi-Fi"
    exit 1
fi

echo "[INFO] Starting udhcpc..."
killall -q udhcpc 2>/dev/null || true
sleep 1
udhcpc -i wlan0 -B > /var/log/udhcpc.log 2>&1 &

for i in $(seq 1 10); do
    if ip addr show wlan0 | grep -q 'inet '; then
        echo "[INFO] Got IP on wlan0: $(ip -o -4 addr show wlan0 | awk '{print $4}')"
        break
    fi
    sleep 1
done

if ! ip addr show wlan0 | grep -q 'inet '; then
    echo "[ERROR] Failed to obtain IP"
    exit 1
fi

echo "[INFO] Syncing time with ntpd"
if [ -x /etc/init.d/ntpd ]; then
    /etc/init.d/ntpd stop || true
fi

ntpd -gq || echo "[WARN] ntpd -gq failed"
if [ -x /etc/init.d/ntpd ]; then
    /etc/init.d/ntpd start
fi

echo "[INFO] Startup networking complete"
