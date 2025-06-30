SUMMARY = "recipe to copy rustup-init script"
DESCRIPTION = "Recipe created by David Gherghita"
LICENSE = "CLOSED"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "file://rustup-init.sh"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${ROOT_HOME}
    install -m 0755 ${UNPACKDIR}/rustup-init.sh ${D}${ROOT_HOME}
}

FILES:${PN} = "${ROOT_HOME}/rustup-init.sh"
