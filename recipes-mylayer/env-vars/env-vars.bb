SUMMARY = "Recipe to inject build environment variables into the image"
DESCRIPTION = "This recipe reads environment variables from build host and creates /etc/environment"
LICENSE = "CLOSED"


# Read the environment variables (with defaults)
DATADOG_API_KEY ?= "DUMMY_API_KEY"

SRC_URI = "file://environment"

do_install() {
    # Install global environment file with variable substitution
    install -d ${D}${sysconfdir}
    sed -e "s/__DATADOG_API_KEY__/${DATADOG_API_KEY}/g" \
        ${WORKDIR}/environment > ${D}${sysconfdir}/environment
}

FILES_${PN} = "${sysconfdir}/environment"
