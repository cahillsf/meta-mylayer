SUMMARY = "Rust SPS30 metrics to Datadog"
DESCRIPTION = "Recipe created by Stephen Cahill"
LICENSE = "CLOSED"

# Add OpenSSL dependency
DEPENDS += "openssl"

# inherit cargo
inherit cargo_bin

# just for cargo_bin
# Enable network for the compile task allowing cargo to download dependencies
do_compile[network] = "1"

SRC_URI = "git://github.com/cahillsf/rpi-particulate-sensor;branch=adding-details;protocol=https"
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git/sps30-metrics"
