SUMMARY = "recipe to copy startup script file to rootfs \
	and set it with update-rc.d"
DESCRIPTION = "Recipe created by David Gherghita"
LICENSE = "CLOSED"

RDEPENDS:${PN} += "bash"

inherit update-rc.d extrausers

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "startup-script.sh"

PI_USER_PASSWORD ?= "raspberry"

EXTRA_USERS_PARAMS = "\
  useradd -m -s /bin/bash pi; \
  usermod -p '${@oe.utils.crypt_password(d.getVar('PI_USER_PASSWORD') or 'raspberry')}' pi; \
"



SRC_URI = "file://startup-script.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/startup-script.sh ${D}${INIT_D_DIR}
}

FILES:${PN} += "${INIT_D_DIR}/startup-script.sh"
