FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# WiFi credentials - set these in local.conf or site.conf
WIFI_SSID ?= "TODO"
WIFI_PASSWORD ?= "TODO"

# Substitute WiFi credentials into the configuration file
do_install:append() {
    config_file="${D}${sysconfdir}/wpa_supplicant.conf"
    if [ -f "$config_file" ]; then
        sed -i "s|ssid=\"TODO\"|ssid=\"${WIFI_SSID}\"|g" "$config_file"
        sed -i "s|psk=\"TODO\"|psk=\"${WIFI_PASSWORD}\"|g" "$config_file"
    fi
}
