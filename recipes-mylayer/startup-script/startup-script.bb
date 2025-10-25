SUMMARY = "recipe to copy startup script file to rootfs \
	and set it with update-rc.d"
DESCRIPTION = "Recipe created by David Gherghita"
LICENSE = "CLOSED"

RDEPENDS:${PN} += "bash"

inherit update-rc.d extrausers

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "startup-script.sh"

PI_USER_PASSWORD_HASH ?= "\$6\$8qsrzOSpX7kI31Vx\$jUz6wZ0kpPxKayMaQpj2GSB8op56rWbm6ASQ8xtwLglRO2ATRJ.09/Sg.vmwxzylXoLZq8YUFw.yZHpmG8IhQ1"

EXTRA_USERS_PARAMS = "\
  useradd -m -s /bin/bash pi; \
  usermod -p '${PI_USER_PASSWORD_HASH}' pi; \
"



SRC_URI = "file://startup-script.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/startup-script.sh ${D}${INIT_D_DIR}
}

FILES:${PN} += "${INIT_D_DIR}/startup-script.sh"
